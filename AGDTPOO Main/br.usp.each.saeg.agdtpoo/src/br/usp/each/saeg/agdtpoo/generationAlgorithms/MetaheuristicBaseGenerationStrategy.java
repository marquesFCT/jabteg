
package br.usp.each.saeg.agdtpoo.generationAlgorithms;

import br.usp.each.saeg.agdtpoo.bytecodeFoundation.BytecodeReader;
import br.usp.each.saeg.agdtpoo.entity.GenerationCoverage;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.ReflectionClass;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationToolException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.FitnessException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.mutation.MutationException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.mutation.MutationMachine;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import br.usp.each.saeg.agdtpoo.randomFeature.RandomValueFactory;
import br.usp.each.saeg.agdtpoo.util.GenerationTrace;
import java.util.ArrayList;
import java.util.List;


public abstract class MetaheuristicBaseGenerationStrategy 
                        extends BaseGenerationStrategy 
                        implements IStrategyPrimitiveTips, IStrategyFitness, IStrategySelection {
    
    protected InputPrimitiveDomain _inputDomain;
    protected IFitness _fitness;
    protected ISelection _selection;  
    protected int _maxNumberOfAttempts = 2;
    
    // Iniciar a geração automática de dados de teste
   
    // Iniciar a geração automática de dados de teste
    @Override
    public Individual[] generateData() throws TestStrategyException
    {         
        // Zera o contador de iterações
        GenerationTrace.write("Starting test data generation.");
        this.InteractionCount = 0;
        
        List<Individual> solution = new ArrayList<Individual>();
        
        // Processo de inicialização         
        initializeGeneration();

        try                
        {
            // Geração da população inicial de indivíduos
            this.CurrentPopulation = generateInitialPopulation();

            // Executa os indivíduos 
            executeIndividuals();

            // Identificação dos ramos/nós que devem ser cobertos
            this.Targets = identifyRequirements();

            // Atualizar os indivíduos
            updateIndividuals();

            for (int i = 0; i < this.Targets.length; i++)
            {   
                int attemptNumber = 0;
                this.CurrentTarget = this.Targets[i];

                if (this.CurrentTarget.isCovered())
                    continue;
                    
                do
                {
                    // Itera o contador de iterações
                    this.InteractionCount++;
                    
                    attemptNumber++;
                    
                    if (verifyIfRequirementHasBeenCovered(this.CurrentTarget))                
                    {
                        this.CurrentTarget.isCovered(true);

                        // Adiciona o indivíduo que cobriu o requisito como solução
                        solution.add(this.currentSolutionToRequirement);                        
                    }
                    else
                    {
                        this.CurrentTarget.isCovered(false);
                        
                        // Calcula a fitness dos indivíduos
                        computeFitness(this.CurrentPopulation, CurrentTarget);
                                                                       
                        // Executa mutação sobre a população atual de indivíduos
                        this.CurrentPopulation = executeMutation(this.CurrentPopulation);

                        // Verificar porque a população atinge o tamanho ZERO
                        if (this.CurrentPopulation.length == 0)
                            this.CurrentPopulation = generateInitialPopulation();
                        
                        // Executar indivíduos
                        executeIndividuals();
                                                                        
                        // Atualizar os indivíduos
                        updateIndividuals();
                    }
                }
                while(!this.CurrentTarget.isCovered() && 
                      attemptNumber < this._maxNumberOfAttempts);
            }
        }                                
        catch(TestDataGenerationToolException e)
        {
            GenerationTrace.write("TestDataGenerationToolException: " + 
                                  e.getMessage());
            throw new TestStrategyException(e);
        }
        catch(Exception e)
        {
            throw new TestStrategyException(e);
        }

        // Processo de finalização 
        finalizeGeneration();

        return solution.toArray(new Individual[solution.size()]);
    }    
            
    public void setNumberMaxOfAttemps(int numberMaxOfAttempts)
    {
        this._maxNumberOfAttempts = numberMaxOfAttempts;
    }
            
    @Override
    public void setInputDomain(InputPrimitiveDomain inputDomain) {
        
        // Atribui os dados primitivos que guiarão a geração dos dados de teste
        this._inputDomain = inputDomain;    
    }

    @Override
    protected void finalizeGeneration()
    {
        GenerationTrace.write("Finalizing generation.");
    }

    @Override
    protected Individual[] generateInitialPopulation() 
                    throws TestDataGenerationToolException {
        
        GenerationTrace.write("Generating initial population.");
        
        // Lê os dados do bytecode
        BytecodeReader reader = new BytecodeReader(this.JarPath, this.ClassUnderTest);
        
        ReflectionClass rc = reader.getReflectionClass();
        
        // Gera indivíduos aleatórios
        Individual[] individuals = 
                RandomValueFactory.getNewPopulation(this.PopulationSize, 
                                                    rc, 
                                                    this.MethodUnderTest, 
                                                    this._inputDomain);
      
        return individuals;          
    }

    @Override
    protected void initializeGeneration()
    {
        GenerationTrace.write("Initializing generation.");
    }

    @Override
    public void setFitness(IFitness fitness) {
        
        this._fitness = fitness;
    }
    
    @Override
    public IFitness getFitness() {
        
        return this._fitness;
    }

    @Override
    public void setSelectionMode(ISelection selection) {
                
        this._selection = selection;
    }
    
    @Override
    public ISelection getSelectionMode() {
                
        return this._selection;
    }
        
    protected Individual[] executeMutation(Individual[] population) 
                                                throws MutationException
    {    
        GenerationTrace.write("Stating mutation.");
        
        // Filtra a população
        if (this._selection != null)
            population = this._selection
                             .selectIndividualsToMutation(population);
        
        if (population.length == 0)
        {            
            return population;
        }
                
        // Cria maquina de mutacao        
        MutationMachine mm = new MutationMachine(population);
        
        // Atribui dados de geracao aleatoria dirigida
        mm.setInputDomain(this._inputDomain);
       
        // Executa mutacao dos individuos
        Individual[] returnValue = mm.mutate();
                
        // Retorna a nova populacao
        return returnValue;
    }
    
    protected void computeFitness(Individual[] individuals, 
                                  GenerationRequirement requirement) throws FitnessException
    {
        for (Individual individual : individuals) {
            
            double fitness = 0;
            
            fitness = computeFitness(individual, requirement);
            
            individual.setFitnesss(fitness);
        }
    }
               
    protected double computeFitness(Individual individual, 
                                    GenerationRequirement target) throws FitnessException
    {
        double fitness = 0;
        GenerationCoverage currentCoverage = null;
        
        currentCoverage = individual.getCoverage();
        
        // Atribuir os requisitos a fitness
        this._fitness.setRequirements(this.Targets);
        
        // Atribuir a população atual a fitness
        this._fitness.setCurrentPopulation(this.CurrentPopulation);
    
        // Calcula a fitness do individuo
        fitness = this._fitness.computeFitness(target, currentCoverage);
                
        return fitness;
    }
    
    @Override
    public abstract String getGenerationStrategyName();    
}
