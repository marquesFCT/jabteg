
package br.jabuti.agdtpoo.control;

import br.jabuti.agdtpoo.model.ContextTestDataGeneration;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.TestGenerationResult;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.IStrategyPrimitiveTips;
import br.usp.each.saeg.agdtpoo.TestDataFacade;
import br.usp.each.saeg.agdtpoo.entity.GUIGenerationCustomParameter;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationToolException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.BaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.IStrategyFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.IStrategySelection;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.MetaheuristicBaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.TestStrategyException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import java.lang.reflect.Method;

public class TestCaseController {
    
    private TestGenerationResult _result;
    private String _trace;
    
    public TestGenerationResult getTestResult()
    {
        return this._result;
    }
    
    public String getTrace()
    {
        return TestDataFacade.getTrace();
    }
    
    public TestCaseController()
    {        
    }
        
    public void executeTest(ContextTestDataGeneration context) throws Throwable
    {
        try
        {           
            executeTestCoverage(context);    
        }
        catch(java.lang.Throwable ex)
        {
            TestDataFacade.appendTraceMessage("Error: " + ex.getMessage());
        }
    }
    
    public String getUnitTestResult()
    {
        if (this._result == null)
            return "";
        
        String result = "";
        
        Individual[] individuals = this._result.getIndividuals();
        
        result = TestDataFacade.getUnitTestClass(individuals);
        
        return result;
    }
    
    public static void setParameters(BaseGenerationStrategy strategy, GUIGenerationCustomParameter[] customParameters) throws Throwable
    {
        if (customParameters == null)
            return;
        
        Method[] methods = strategy.getClass().getMethods();
                
        for (GUIGenerationCustomParameter customParameter : customParameters) {
            
            String methodName = customParameter.getSetterMethodName();
            Method selectedMethod = null;
            
            for (Method method : methods) {
                
                if (method.getName().equals(methodName))
                {
                    selectedMethod = method;
                    break;
                }
            }
            
            if (selectedMethod == null)
                throw new TestDataGenerationToolException("Setter method " + methodName + " not found.");
            
            Object parameterValue = customParameter.getValue();            
            selectedMethod.invoke(strategy, parameterValue);
        }
    }
    
    private void executeTestCoverage(ContextTestDataGeneration context) throws Throwable
    {
        TestDataGenerationResource resouce = context.getResource();
        
        this._result = null;
        
        TestDataFacade.appendTraceMessage("Execute test start");
        TestDataFacade.appendTraceMessage("Strategy selected: " + context.getTechniqueName());
        
        String junitFileJavaPath = "c:\\temp\\AutomaticTest.java";
        
        TestDataFacade.appendTraceMessage("JUnit Java file at:" + junitFileJavaPath);
        
        BaseGenerationStrategy strategy = null;
        
        if (resouce == null)
            strategy = TestDataFacade.getGenerationStrategy(context.getTechniqueName());
        else
            strategy = resouce.getStrategy(context.getTechniqueName());
        strategy.setInitialPopulationSize(context.getInitialPopulationSize());     
        strategy.setCriterion(context.getCriterion());       
        
        // Se existirem parametros customizados entao eh preciso passar
        // estes parametros para a estrategia de teste
        if (context.getCustomParameters() != null && resouce != null)
        {
            setParameters(strategy, context.getCustomParameters());                    
        }
                        
        if (strategy == null)
        {
            TestDataFacade.appendTraceMessage("Strategy null");
            return;
        }
        
        if (strategy instanceof IStrategyPrimitiveTips)
        {
            ((IStrategyPrimitiveTips)strategy).setInputDomain(context.getInputDomain());
        }       
        
        if (strategy instanceof MetaheuristicBaseGenerationStrategy)
        {
            ((MetaheuristicBaseGenerationStrategy)strategy).setNumberMaxOfAttemps(context.getInputDomain().getIntermediateMethodPrimitiveRandomTip().getMaxCountOfInteractions());
        }
                
        if (!context.getFitnessName().equals("")) {
            if (strategy instanceof IStrategyFitness)
            {
                IFitness fitness = null;

                if (resouce == null)
                    fitness = TestDataFacade.getFitness(context.getFitnessName());
                else
                    fitness = TestDataFacade.getFitness(resouce, context.getTechniqueName(), context.getFitnessName());

                ((IStrategyFitness)strategy).setFitness(fitness);
            }
        }
             
        if (!context.getSelectionName().equals("")) {
            if (strategy instanceof IStrategySelection)
            {
                ISelection selection = null;

                if (resouce == null)
                    selection = TestDataFacade.getSelection(context.getSelectionName());
                else
                    selection = TestDataFacade.getSelection(resouce, context.getTechniqueName(), context.getSelectionName());

                ((IStrategySelection)strategy).setSelectionMode(selection);
            }
        }
        
        JabutiTestDataGenerationTool generationTool = new JabutiTestDataGenerationTool(context.getJbtProject(), context.getClassUnderTest(), context.getMethodUnderTest());
        
        generationTool.setClassUnderTest(context.getClassUnderTest());
        generationTool.setDependenciesPath(context.getDependenciesPath());
        generationTool.setJUnitPath(context.getJunitPath());
        generationTool.setJUnitTestClassFile(junitFileJavaPath);
        generationTool.setJarPath(context.getJarPath());
        generationTool.setMethodUnderTest(context.getMethodUnderTest());
                
        strategy.setTestDataGenerationTool(generationTool);
                
        try
        {
            this._result = TestDataFacade.execute(strategy, context.getJarPath(), context.getClassUnderTest(), context.getMethodUnderTest(), context.getInitialPopulationSize());

            GenerationRequirement[] uncoveredRequirements = this._result.getUncoveredRequirements();

            TestDataFacade.appendTraceMessage("Requisitos de teste");
            String requirements = "-";
            for (GenerationRequirement generationRequirement : this._result.getRequirements()) {
                requirements += generationRequirement.getIdRequirement() + "-";
            }
            TestDataFacade.appendTraceMessage(requirements);

            TestDataFacade.appendTraceMessage("Cobertura dos indivíduos");
            for (Individual individual : this._result.getIndividuals()) {
                TestDataFacade.appendTraceMessage(individual.getCoverage().getExecutionPath());
            }

            TestDataFacade.appendTraceMessage("Requisitos não cobertos");
            requirements = "-";
            for (GenerationRequirement generationRequirement : this._result.getUncoveredRequirements()) {
                requirements += generationRequirement.getIdRequirement() + "-";
            }
            TestDataFacade.appendTraceMessage(requirements);
            
        }
        catch(TestStrategyException e)
        {
            TestDataFacade.appendTraceMessage(e.getMessage());
        }        
    }    
}
