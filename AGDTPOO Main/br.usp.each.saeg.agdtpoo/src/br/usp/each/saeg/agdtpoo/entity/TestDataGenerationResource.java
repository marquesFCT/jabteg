
package br.usp.each.saeg.agdtpoo.entity;

import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.BaseGenerationStrategy;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import java.util.ArrayList;

public class TestDataGenerationResource {

    public TestDataGenerationResource()
    {
        this._fitness = new ArrayList<IFitness>();
        this._selections = new ArrayList<ISelection>();
        this._testStrategies = new ArrayList<BaseGenerationStrategy>();
    }
    
    private String _pluginName;
    
    private ArrayList<IFitness> _fitness;
    
    private ArrayList<ISelection> _selections;
    
    private ArrayList<BaseGenerationStrategy> _testStrategies;
    
    public String getPlugInName()
    {
        return this._pluginName;
    }
    
    public void setPlugInName(String name)
    {
        this._pluginName = name;
    }
    
    public void addNewFitness(IFitness fitness)
    {
        this._fitness.add(fitness);
    }
    
    public void addNewSelection(ISelection selection)
    {
        this._selections.add(selection);
    }
    
    public void addNewTestStrategy(BaseGenerationStrategy testStrategy)
    {
        this._testStrategies.add(testStrategy);
    }
    
    public IFitness[] getFitnesses()
    {
        return this._fitness.toArray(new IFitness[this._fitness.size()]);
    }
    
    public ISelection[] getSelections()
    {
        return this._selections.toArray(new ISelection[this._selections.size()]);
    }
    
    public BaseGenerationStrategy[] getStrategies()
    {
        return this._testStrategies.toArray(new BaseGenerationStrategy[this._testStrategies.size()]);
    }       
    
    public BaseGenerationStrategy getStrategy(String name)
    {
        for (BaseGenerationStrategy baseGenerationStrategy : this._testStrategies) {
            if (baseGenerationStrategy.getGenerationStrategyName().equals(name))
            {
                return baseGenerationStrategy;
            }
        }
        
        return null;
    }
}
