
package testTechniques;

import fitnessAlgorithms.IneditismFitness;
import fitnessAlgorithms.SimilarityFitness;
import selectionAlgorithms.TournamentSelection;
import selectionAlgorithms.ElitismSelection;
import selectionAlgorithms.AverageSelection;
import selectionAlgorithms.RouletteSelection;
import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.mutation.InputValueMutation;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.*;
import br.usp.each.saeg.agdtpoo.util.GenerationTrace;
import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealingGenerationStrategy 
        extends MetaheuristicBaseGenerationStrategy 
        implements IGUIGenerationStrategy
{        
    private int _maxAttempts;
    private double _initialTemperature;
    
    @Override
    public GUIGenerationCustomParameter[] getCustomParameters() {
        GUIGenerationCustomParameter paramMaxAttempts = 
                            new GUIGenerationCustomParameter();
        paramMaxAttempts.setDefaultValue(15);
        paramMaxAttempts.setLegend("Max Attempts: ");
        paramMaxAttempts.setType(PrimitiveType.Integer);
        paramMaxAttempts.setSettterMethodSignature("setMaxAttempts(int)");
        
        GUIGenerationCustomParameter paramInitialTemperature = 
                            new GUIGenerationCustomParameter();
        paramInitialTemperature.setDefaultValue(5.0);
        paramInitialTemperature.setLegend("Initial Temperature: ");
        paramInitialTemperature.setType(PrimitiveType.Double);
        paramInitialTemperature.setSettterMethodSignature("setInitialTemperature(double)");
                        
        GUIGenerationCustomParameter[] parameters = 
                            new GUIGenerationCustomParameter[] 
                                    {   paramMaxAttempts, 
                                        paramInitialTemperature };        
        return parameters;
    }

    @Override
    public EnumCriterion[] getSupportedCriterions() {
        EnumCriterion[] criterions = 
                new EnumCriterion[]{ EnumCriterion.AllNodesEi, 
                                     EnumCriterion.AllEdgesEi, 
                                     EnumCriterion.AllPotUsesEi, 
                                     EnumCriterion.AllUsesEi,
                                     EnumCriterion.AllNodesEd, 
                                     EnumCriterion.AllEdgesEd, 
                                     EnumCriterion.AllPotUsesEd, 
                                     EnumCriterion.AllUsesEd };       
        return criterions;
    }

    @Override
    public IFitness[] getSupportedFitnesses() {
        return new IFitness[] { new SimilarityFitness(), 
                                new IneditismFitness() };
    }

    @Override
    public ISelection[] getSupportedSelections() {
        return new ISelection[]  { new ElitismSelection(),
                                   new TournamentSelection(),
                                   new AverageSelection(),
                                   new RouletteSelection()};
    }
   
    public void setMaxAttempts(int value) {
        this._maxAttempts = value;
    }
            
    public void setInitialTemperature(double value) {
        this._initialTemperature = value;
    }
        
    // Iniciar a geração automática de dados de teste
    @Override
    public Individual[] generateData() throws TestStrategyException {         
        // Zera o contador de iterações
        GenerationTrace.write("Starting test data generation.");
        this.InteractionCount = 0;
        
        List<Individual> solution = new ArrayList<Individual>();
        
        // Processo de inicialização 
        GenerationTrace.write("Initializing generation.");
        initializeGeneration();

        try                
        {
            // Geração da população inicial de indivíduos 
            GenerationTrace.write("Generating initial population.");
            this.CurrentPopulation = generateInitialPopulation();

            // Executa os indivíduos             
            executeIndividuals();

            // Identificação dos ramos/nós que devem ser cobertos
            this.Targets = identifyRequirements();

            // Atualizar os indivíduos
            updateIndividuals();

            for (int i = 0; i < this.Targets.length; i++) {   
                int attemptNumber = 0;
                this.CurrentTarget = this.Targets[i];

                if (this.CurrentTarget.isCovered())
                    continue;
                
                do {
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
                                                  
                        // Bloco de execução do Hill Climbing.
                        GenerationTrace.write("Starting Simulated Annealing.");
                        this.CurrentPopulation = executeSimulatedAnnealing(this.CurrentPopulation, CurrentTarget);
                        
                        // Verifica se o requisito foi coberto
                        if (verifyIfRequirementHasBeenCovered(this.CurrentTarget))                
                        {
                            this.CurrentTarget.isCovered(true);

                            // Adiciona o indivíduo que cobriu o requisito como solução
                            solution.add(this.currentSolutionToRequirement);                        
                        }
                        else
                        {
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
                }
                while(!this.CurrentTarget.isCovered() && attemptNumber < this._maxNumberOfAttempts);
            }
        }                                
        catch(TestDataGenerationToolException e) {
            GenerationTrace.write("TestDataGenerationToolException: " + e.getMessage());
            throw new TestStrategyException(e);
        }
        catch(Exception e) {
            throw new TestStrategyException(e);
        }

        // Processo de finalização 
        finalizeGeneration();

        return solution.toArray(new Individual[solution.size()]);
    }    

    private Individual changeIndividual(Individual individual)  {
        InputValueMutation mutationExecutor = new InputValueMutation();
        
        mutationExecutor.setInputDomain(this._inputDomain);
        
        Individual result = mutationExecutor.mutate(individual);
        
        return result;
    }
            
    @Override
    public String getGenerationStrategyName() {
        return "Simulated annealing strategy";
    }
    
    private double getEnergy(Individual individual) {
        double energy = 0;
                
        energy = individual.getFitness();
        
        return energy;
    }
    
    private Individual[] executeSimulatedAnnealing(Individual[] population, 
                                                   GenerationRequirement currentRequirement) 
                    throws TestDataGenerationToolException, FitnessException {
        int MAXATTEMPTS = this._maxAttempts;
        double INITIALTEMPERATURE = this._initialTemperature;
        
        if (population == null || currentRequirement == null)
            return null;
        
        if (population.length == 0)
            return null;
        
        for (int i = 0; i < population.length; i++) {
           
            // Inicializa com a temperatura inicial
            double currentTemperature = INITIALTEMPERATURE;
            
            int index = 0;
            while(currentTemperature > 0 && index < MAXATTEMPTS)
            {     
                try
                {
                    // Identifica o indivíduo dentro da população
                    Individual individual = population[i];

                    // Modifica o individuo gerando algo novo
                    Individual newIndividual = changeIndividual(individual);

                    // Cria novo array com um indivíduo
                    Individual[] newPopulation = new Individual[] { newIndividual };

                    // Executa os indivíduos
                    executeIndividuals(newPopulation);

                    // Atualiza os indivíduos com suas coberturas
                    newPopulation = updateIndividuals(newPopulation);

                    // Atualiza a variável local com os dados da execução e da cobertura
                    newIndividual = newPopulation[0];

                    // Calcula a fitness do indivíduo
                    double fitness = computeFitness(newIndividual, currentRequirement);

                    // Atribui a fitness ao indivíduo
                    newIndividual.setFitnesss(fitness);

                    // Se a fitness do novo indivíduo é melhor, então ele 
                    // substituirá o indivíduo antigo
                    if (individual.getFitness() < newIndividual.getFitness())            
                    {
                        population[i] = newIndividual;       

                        if (newIndividual.getFitness() == 1)
                            return population;
                    }

                    // Verifica a energia gasta com o novo indivíduo
                    double energy = getEnergy(newIndividual);

                    // Atualiza a temperatura
                    currentTemperature -= energy; 

                    // Acrescenta uma iteração
                    index++;
                }catch(Exception ex)
                {
                   String message = ex.getMessage();
                }
            }
                        
            // Itera o contador de iterações
            this.InteractionCount++;
        }

        return population;
    }    
}
