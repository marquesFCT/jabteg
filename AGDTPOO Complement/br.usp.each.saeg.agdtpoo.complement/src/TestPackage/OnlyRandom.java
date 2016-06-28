
package TestPackage;

import br.usp.each.saeg.agdtpoo.TestDataFacade;
import br.usp.each.saeg.agdtpoo.bytecodeFoundation.BytecodeReader;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.GenerationCoverage;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.IGUIGenerationStrategy;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveType;
import br.usp.each.saeg.agdtpoo.entity.ReflectionClass;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationToolException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.BaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.IStrategyPrimitiveTips;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.TestStrategyException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import br.usp.each.saeg.agdtpoo.randomFeature.RandomValueFactory;

public class OnlyRandom extends BaseGenerationStrategy implements IStrategyPrimitiveTips, IGUIGenerationStrategy 
{
    private InputPrimitiveDomain _inputDomain;
    private int _testValue;
    
    @Override
    public EnumCriterion[] getSupportedCriterions() {
        return new EnumCriterion[] 
                { 
                    EnumCriterion.AllNodesEi, 
                    EnumCriterion.AllNodesEd,
                    EnumCriterion.AllEdgesEi,
                    EnumCriterion.AllEdgesEd
                };
    }

    @Override
    public GUIGenerationCustomParameter[] getCustomParameters()
    {
        GUIGenerationCustomParameter param1 = new GUIGenerationCustomParameter();
        
        param1.setDefaultValue(10);
        param1.setLegend("TESTE");
        param1.setSettterMethodSignature("setTestValue(int)");
        param1.setType(PrimitiveType.Integer);
                
        return new GUIGenerationCustomParameter[] { param1 };
    }
    
    @Override
    public IFitness[] getSupportedFitnesses() {
        return null;
    }

    @Override
    public ISelection[] getSupportedSelections() {
        return null;
    }
      
    public void setTestValue(int testValue)
    {
        this._testValue = testValue;
    }
            
    // Passo 00 - Iniciar a geração automática de dados de teste
    @Override
    public Individual[] generateData() throws TestStrategyException
    {                           
        // Zera a contagem de iterações
        this.InteractionCount = 0;

        // Processo de inicialização 
        initializeGeneration();

        try                
        {
            for (int j = 0; j < 5; j++) {
                
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
                    if (this.Targets[i].isCovered())
                        continue;

                    // Itera o contador de iterações
                    this.InteractionCount++;

                    this.CurrentTarget = this.Targets[i];

                    if (verifyIfRequirementHasBeenCovered(this.CurrentTarget))
                    {
                        this.CurrentTarget.isCovered(true);
                    }
                    else
                    {
                        this.CurrentTarget.isCovered(false);
                    }                    
                }
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

        // Retorna a população atual
        return this.CurrentPopulation;
    }
        
    @Override
    public void setInputDomain(InputPrimitiveDomain domain) {
        
        // Atribui os dados primitivos que guiarão a geração dos dados de teste
        this._inputDomain = domain;
    
    }

    @Override
    protected void executeIndividuals() throws TestDataGenerationToolException {

        TestDataFacade.createUnitTestFile(junitFileJavaPath, this.CurrentPopulation);                            
        
        // Compila os indivíduos
        this._testGenerationTool.compileIndividuals();
        
        this.ExecutionCount++;
    }
    
    @Override
    protected void updateIndividuals() throws TestDataGenerationToolException
    {
        if (this._testGenerationTool != null)
        {           
            GenerationCoverage[] coverages = this._testGenerationTool.getCoverages(this._criterion);

            int index = 0;
            for (Individual individual : this.CurrentPopulation) {            
                
                individual.setCoverage(coverages[index]);
                index++;
                
            }
        }
    }

    @Override
    protected void finalizeGeneration() {
        // Por enquanto não é utilizado
    }

    @Override
    protected Individual[] generateInitialPopulation()
    {
        // Lê os dados do bytecode
        BytecodeReader reader = new BytecodeReader(this.JarPath, this.ClassUnderTest);
        
        ReflectionClass rc = reader.getReflectionClass();
        
        // Gera indivíduos aleatórios
        Individual[] individuals = RandomValueFactory.getNewPopulation(this.PopulationSize, rc, this.MethodUnderTest, this._inputDomain);
      
        return individuals;        
    }

    @Override
    protected GenerationRequirement[] identifyRequirements() throws TestDataGenerationToolException {

        GenerationRequirement[] returnValue = this._testGenerationTool.getRequirements(this._criterion);
        
        return returnValue;
    }

    @Override
    protected void initializeGeneration() {
        // Método implementado apenas para adequação a interface
        // Talvez seja interessante remover este método da classe base
    }
    
    @Override
    public String getGenerationStrategyName()
    {
        return "Only random";
    }
}
