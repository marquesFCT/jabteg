
package br.usp.each.saeg.agdtpoo;

import br.usp.each.saeg.agdtpoo.bytecodeFoundation.BytecodeReader;
import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.*;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import br.usp.each.saeg.agdtpoo.individualFactory.*;
import br.usp.each.saeg.agdtpoo.util.GenerationTrace;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDataFacade {
        
    // Define o trace de execução
    public static void appendTraceMessage(String trace)
    {
        GenerationTrace.write(trace);
    }
    
    // Retorna o trace de execução 
    public static String getTrace()
    {
        return GenerationTrace.getTrace();
    }
    
    // Retorna dados da classe sob teste
    public static ReflectionClass getClassMembers(String jarPath, String classUnderTest)
    {
        BytecodeReader reader = new BytecodeReader(jarPath, classUnderTest);
        
        ReflectionClass rc = reader.getReflectionClass();
        
        return rc;
    }
    
    // Executa a geração automática de dados a partir de uma estratégia de geração,
    // o caminho de um arquivo JAR, o nome de uma classe sob teste e um método sob teste
    public static TestGenerationResult execute(BaseGenerationStrategy testStrategy,
                                               String jarPath,
                                               String classUnderTest,
                                               String methodUnderTest,
                                               int initialPopulationSize) throws TestStrategyException 
    {
        TestGenerationResult returnValue = new TestGenerationResult();

        try
        {
            // Limpa o trace de execucao
            GenerationTrace.clear();

            // Início da geração de dados de teste
            Date startRandomElements = new Date();

            Individual[] result;

            // Inicializando atributos da classe geradora de dados de teste
            testStrategy.initialize(jarPath, initialPopulationSize, classUnderTest, methodUnderTest);

            result = testStrategy.generateData();

            appendTraceMessage("Execution finalized with success.");
            
            // Fim da geração de dados de teste
            Date endRandomElements = new Date();

            // Diferença de tempo da execução
            double diffRandomElements = (endRandomElements.getTime() - startRandomElements.getTime()) / 1000D;

            returnValue.setCountOfExecutions(testStrategy.getCountOfExecutions());
            returnValue.setClassUnderTest(classUnderTest);
            returnValue.setMethodUnderTest(methodUnderTest);
            returnValue.setGenerationStrategy(testStrategy.getGenerationStrategyName());
            returnValue.setCountOfInteractions(testStrategy.getCountOfInteractions());
            returnValue.setIndividuals(result);
            returnValue.setRequirements(testStrategy.getRequirements());
            returnValue.setDuration(diffRandomElements);
            returnValue.setFinalDate(endRandomElements);
            returnValue.setInitialDate(startRandomElements);

            if (testStrategy instanceof IStrategyFitness)
                returnValue.setFitness(((IStrategyFitness)testStrategy).getFitness().getFitnessName());

            if (testStrategy instanceof IStrategySelection)
                returnValue.setSelectionTechique(((IStrategySelection)testStrategy).getSelectionMode().getSelectionName());        
        }
        catch(Exception ex)
        {
            appendTraceMessage(ex.getMessage());
            appendTraceMessage(ex.getLocalizedMessage());
                        
            throw new TestStrategyException(ex);
        }            
        
        return returnValue;
    }    
    
    // Retorna a representação de Tonella de um conjunto de indivíduos
    public static String[] getTonellaRepresentation(Individual[] individuals)
    {
        TonellaFactory tFac = new TonellaFactory();
        String[] tonellaStrings = tFac.getRepresentation(individuals);
        
        return tonellaStrings;
    }
    
    // Retorna métodos unitários de um grupo de indivíduos passados por parâmetro
    public static String[] getUnitTestMethods(Individual[] individuals)
    {
        UnitTestFactory tFac = new UnitTestFactory();
        String[] unitTestString = tFac.getRepresentation(individuals);
        
        return unitTestString;
    }
        
    // Retorna uma classe JUnit com os métodos unitários de um grupo de invivíduos
    public static String getUnitTestClass(Individual[] individuals)
    {
        UnitTestFactory tFac = new UnitTestFactory();
        String unitTestString = tFac.getUnitTestClass(individuals);
        
        return unitTestString;
    }
    
    // Retorna uma estratégia de geração de dados de teste registrada no framework
    public static BaseGenerationStrategy getGenerationStrategy(String label)
    {
        BaseGenerationStrategy[] strategies = null;
        
        strategies = getGenerationStrategies();
        
        for (BaseGenerationStrategy commomGenerationStrategy : strategies) {
            if (commomGenerationStrategy.getGenerationStrategyName().equals(label))
            {
                return commomGenerationStrategy;
            }
        }
        
        return null;
    }
    
    // Retorna todas as estratégia de geração de dados de teste registradas no framework
    public static BaseGenerationStrategy[] getGenerationStrategies()
    {
        List<BaseGenerationStrategy> strategies = new ArrayList<BaseGenerationStrategy>();
        
        //strategies.add(new RandomGenerationStrategy());
        //strategies.add(new EvolutionaryGenerationStrategy());
        //strategies.add(new HillClimbingGenerationStrategy());
        //strategies.add(new SimulatedAnnealingGenerationStrategy());
                
        BaseGenerationStrategy[] arrayStrategies = new BaseGenerationStrategy[strategies.size()];
        
        return strategies.toArray(arrayStrategies);
    }
       
    // Retorna uma estratégia de geração de dados de teste registrada no framework
    public static IFitness getFitness(String label)
    {
        return getFitness(null, "", label);
    }
    
    // Retorna uma estratégia de geração de dados de teste registrada no framework
    public static IFitness getFitness(TestDataGenerationResource resource, String testTechnique, String label)
    {
        IFitness[] fitnesses = null;
                       
        if (resource == null)
            fitnesses = getFitnesses();
        else
        {
            for (BaseGenerationStrategy baseGenerationStrategy : resource.getStrategies()) {
                if (testTechnique.equals(baseGenerationStrategy.getGenerationStrategyName()))
                {
                    fitnesses = ((IGUIGenerationStrategy)((Object)baseGenerationStrategy)).getSupportedFitnesses();
                    break;
                }
            }
        }
        
        if (fitnesses != null)
        {
            for (IFitness fitness : fitnesses) {
                if (fitness.getFitnessName().equals(label))
                {
                    return fitness;
                }
            }
        }
        
        return null;
    }
    
    // Retorna uma estratégia de seleção de dados de teste registrada no framework
    public static ISelection getSelection(String label)
    {
        return getSelection(null, "", label);
    }
    
    // Retorna uma estratégia de seleção de dados de teste registrada no framework
    public static ISelection getSelection(TestDataGenerationResource resource, String testTechnique, String label)
    {
        ISelection[] selections = null;
        
        if (resource == null)
            selections = getSelectionTechniques();
        else
        {
            for (BaseGenerationStrategy baseGenerationStrategy : resource.getStrategies()) {
                if (testTechnique.equals(baseGenerationStrategy.getGenerationStrategyName()))
                {
                    selections = ((IGUIGenerationStrategy)((Object)baseGenerationStrategy)).getSupportedSelections();
                    break;
                }
            }
        }
        
        if (selections != null)
        {
            for (ISelection iSelection : selections) {
                if (iSelection.getSelectionName().equals(label))
                {
                    return iSelection;
                }
            }
        }
        
        return null;
    }

    // Retorna todas as estratégia de seleção de dados de teste registradas no framework
    public static ISelection[] getSelectionTechniques()
    {
        return null;
    }  
    
    // Retorna todas as estratégia de geração de dados de teste registradas no framework
    public static IFitness[] getFitnesses()
    {
        return null;
    }    
    
    // Cria um arquivo JUnit com todos os indivíduos passados por parâmetro
    public static boolean createUnitTestFile(String unitTestFile, Individual[] individuals)
    {
        boolean success = false;
        String unitTestCode = getUnitTestClass(individuals);
        
        try
        {
            File newFile = new File(unitTestFile);
            
            if (newFile.exists())
                newFile.delete();
            
            newFile.createNewFile();
            
            BufferedWriter out = new BufferedWriter(new FileWriter(unitTestFile));
            out.write(unitTestCode);
            out.close();
            
            success = true;
        }
        catch(IOException e)
        {
            appendTraceMessage("createUnitTestFile: " + e.getMessage());
        }
        catch(Exception e)
        {
            appendTraceMessage("createUnitTestFile: " + e.getMessage());
        }
        
        return success;
    }
}
