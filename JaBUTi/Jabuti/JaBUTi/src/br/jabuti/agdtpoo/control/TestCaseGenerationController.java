
package br.jabuti.agdtpoo.control;

import br.usp.each.saeg.agdtpoo.TestDataFacade;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.IGUIGenerationStrategy;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.BaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;

public class TestCaseGenerationController {
    
    public static String[] getAllCriterions()
    {
        return new String[] { "All-Nodes-ei", 
                              "All-Nodes-ed", 
                              "All-Edges-ei", 
                              "All-Edges-ed",
                              "All-Uses-ei",  
                              "All-Uses-ed", 
                              "All-Pot-Uses-ei", 
                              "All-Pot-Uses-ed" };
    }
    
    public static String[] getCriterions(IGUIGenerationStrategy strategy)
    {        
        String[] returnValue = new String[strategy.getSupportedCriterions().length];
        
        for (int i = 0; i < returnValue.length; i++) {
            returnValue[i] = getCriterionName(strategy.getSupportedCriterions()[i]);
        }
        
        return returnValue;
    }
    
    private static String getCriterionName(EnumCriterion criterion)
    {
        if (criterion == EnumCriterion.AllEdgesEd)
            return "All-Edges-ed";
        else if (criterion == EnumCriterion.AllEdgesEi)
            return "All-Edges-ei";
        else if (criterion == EnumCriterion.AllNodesEd)
            return "All-Nodes-ed";
        else if (criterion == EnumCriterion.AllNodesEi)
            return "All-Nodes-ei";
        else if (criterion == EnumCriterion.AllPotUsesEd)
            return "All-Pot-Uses-ed";
        else if (criterion == EnumCriterion.AllPotUsesEi)
            return "All-Pot-Uses-ei";
        else if (criterion == EnumCriterion.AllUsesEd)
            return "All-Uses-ed";
        else if (criterion == EnumCriterion.AllUsesEi)
            return "All-Uses-ei";        
        
        return "";
    }
    
    public static String[] getGenerationStrategiesLabel()
    {
        return getGenerationStrategiesLabel(null);
    }
    
    public static String[] getGenerationStrategiesLabel(TestDataGenerationResource resource)
    {
        String[] returnValue = null;
        BaseGenerationStrategy[] strategies = null;
        
        if (resource == null)
            strategies = TestDataFacade.getGenerationStrategies();
        else
            strategies = resource.getStrategies();            
        
        returnValue = new String[strategies.length];
        
        for (int i = 0; i < strategies.length; i++) {
            returnValue[i] = strategies[i].getGenerationStrategyName();
        }
        
        return returnValue; 
    }
    
    public static String[] getGenerationSelectionLabel()
    {
        return getGenerationSelectionLabel((TestDataGenerationResource)null);        
    }
    
        public static String[] getGenerationSelectionLabel(IGUIGenerationStrategy strategy)
    {
        String[] returnValue = null;
        ISelection[] selections = null;
        
        selections = strategy.getSupportedSelections();
                        
        if (selections == null || selections.length == 0)
            return new String[] { "" };
                
        returnValue = new String[selections.length];
        
        for (int i = 0; i < selections.length; i++) {
            returnValue[i] = selections[i].getSelectionName();
        }
        
        return returnValue; 
    }
    
    public static String[] getGenerationSelectionLabel(TestDataGenerationResource resource)
    {
        String[] returnValue = null;
        ISelection[] selections = null;
        
        if (resource == null)
            selections = TestDataFacade.getSelectionTechniques();
        else
            selections = resource.getSelections();
        
        returnValue = new String[selections.length];
        
        for (int i = 0; i < selections.length; i++) {
            returnValue[i] = selections[i].getSelectionName();
        }
        
        return returnValue; 
    }
    
    public static String[] getGenerationFitnessesLabel()
    {
        return getGenerationFitnessesLabel((TestDataGenerationResource)null);
    }
    
    public static String[] getGenerationFitnessesLabel(IGUIGenerationStrategy strategy)
    {
        String[] returnValue = null;
        IFitness[] fitnesses = null;
                
        fitnesses = strategy.getSupportedFitnesses();
        
        if (fitnesses == null || fitnesses.length == 0)
            return new String[] { "" };
        
        returnValue = new String[fitnesses.length];
        
        for (int i = 0; i < fitnesses.length; i++) {
            returnValue[i] = fitnesses[i].getFitnessName();
        }
        
        return returnValue; 
    }
    
    public static String[] getGenerationFitnessesLabel(TestDataGenerationResource resource)
    {
        String[] returnValue = null;
        IFitness[] fitnesses = null;
        
        if (resource == null)
            fitnesses = TestDataFacade.getFitnesses();
        else
            fitnesses = resource.getFitnesses();
                    
        returnValue = new String[fitnesses.length];
        
        for (int i = 0; i < fitnesses.length; i++) {
            returnValue[i] = fitnesses[i].getFitnessName();
        }
        
        return returnValue; 
    }
}
