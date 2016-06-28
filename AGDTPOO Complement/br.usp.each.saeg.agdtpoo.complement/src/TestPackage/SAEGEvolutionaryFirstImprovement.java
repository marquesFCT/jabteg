
package TestPackage;

import java.util.List;
import java.util.ArrayList;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.IGUIGenerationStrategy;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationToolException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import fitnessAlgorithms.IneditismFitness;
import fitnessAlgorithms.SimilarityFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.MetaheuristicBaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.TestStrategyException;
import selectionAlgorithms.ElitismSelection;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;

public class SAEGEvolutionaryFirstImprovement extends MetaheuristicBaseGenerationStrategy implements IGUIGenerationStrategy
{
    @Override
    public EnumCriterion[] getSupportedCriterions() {
        return new EnumCriterion[] { EnumCriterion.AllNodesEi, EnumCriterion.AllNodesEd };
    }
    
    @Override
    public GUIGenerationCustomParameter[] getCustomParameters()
    {
        return null;
    }
    
    @Override
    public IFitness[] getSupportedFitnesses() {
        return new IFitness[] { new SimilarityFitness(), new IneditismFitness() };
    }

    @Override
    public ISelection[] getSupportedSelections() {
        return new ISelection[]  { new ElitismSelection() };
    }
    // Iniciar a geração automática de dados de teste
    @Override
    public Individual[] generateData() throws TestStrategyException
    {         
        // Zera o contador de iterações
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
                this.CurrentTarget = this.Targets[i];
                            
                // Itera o contador de iterações
                this.InteractionCount++;
                    
                if (verifyIfRequirementHasBeenCovered(this.CurrentTarget))                
                {
                    this.CurrentTarget.isCovered(true);

                    // Adiciona o indivíduo que cobriu o requisito como solução
                    solution.add(this.currentSolutionToRequirement);   
                }
            }            
            
            for (int i = 0; i < this.Targets.length; i++)
            {  
                if (this.Targets[i].isCovered())
                    continue;

                int attemptNumber = 0;
                this.CurrentTarget = this.Targets[i];

                // Executa mutação sobre a população atual de indivíduos
                this.CurrentPopulation = executeMutation(this.CurrentPopulation); 

                // Verificar porque a população atinge o tamanho ZERO
                if (this.CurrentPopulation.length == 0)
                    this.CurrentPopulation = generateInitialPopulation();

                // Executar indivíduos
                executeIndividuals();

                // Atualizar os indivíduos
                updateIndividuals();

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
                while(!this.CurrentTarget.isCovered() && attemptNumber < this._maxNumberOfAttempts);
            }
            
        }                                
        catch(TestDataGenerationToolException e)
        {
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

    @Override
    protected void finalizeGeneration() {
        // Ainda não implementado
    }

    @Override
    protected void initializeGeneration() {
        // Ainda não implementado
    }
    
    @Override
    public String getGenerationStrategyName()
    {
        return "SAEG Evolutionary strategy - First Improvement";
    }
}
