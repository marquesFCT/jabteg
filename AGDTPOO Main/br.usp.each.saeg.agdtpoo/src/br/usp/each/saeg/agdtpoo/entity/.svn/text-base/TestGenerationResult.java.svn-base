
package br.usp.each.saeg.agdtpoo.entity;

import java.util.ArrayList;
import java.util.Date;

public class TestGenerationResult {
    private GenerationRequirement[] _requirements;
    private Individual[] _individuals;
    private int _countOfInteractions;
    private int _countOfExecutions;
    private Date _initial;
    private Date _end;
    private double _duration;
    private String _selectionTechique;
    private String _fitness;
    private String _classUnderTest;
    private String _methodUnderTest;
    private String _generationStrategy;

    public TestGenerationResult()
    {
        this._selectionTechique = "";
        this._fitness = "";
        this._classUnderTest = "";
        this._methodUnderTest = "";
        this._generationStrategy = "";
    }
    
    public String getClassUnderTest() {
        return _classUnderTest;
    }

    public void setClassUnderTest(String _classUnderTest) {
        this._classUnderTest = _classUnderTest;
    }

    public String getFitness() {
        return _fitness;
    }

    public void setFitness(String _fitness) {
        this._fitness = _fitness;
    }

    public String getGenerationStrategy() {
        return _generationStrategy;
    }

    public void setGenerationStrategy(String _generationStrategy) {
        this._generationStrategy = _generationStrategy;
    }

    public String getMethodUnderTest() {
        return _methodUnderTest;
    }

    public void setMethodUnderTest(String _methodUnderTest) {
        this._methodUnderTest = _methodUnderTest;
    }

    public String getSelectionTechique() {
        return _selectionTechique;
    }

    public void setSelectionTechique(String _selectionTechique) {
        this._selectionTechique = _selectionTechique;
    }
    
    public void setInitialDate(Date date)
    {
        this._initial = date;
    }
    
    public void setFinalDate(Date date)
    {
        this._end = date;
    }
    
    public void setDuration(double duration)
    {
        this._duration = duration;
    }
    
    public Date getInitialDate()
    {
        return this._initial;
    }
    
    public Date getFinalDate()
    {
        return this._end;
    }
    
    public double getDuration()
    {
        return this._duration;
    }
    
    public void setCountOfExecutions(int countOfExecutions)
    {
        this._countOfExecutions = countOfExecutions;
    }
    
    public int getCountOfExecutions()
    {
        return this._countOfExecutions;
    }
    
    public void setCountOfInteractions(int countOfInteractions)
    {
        this._countOfInteractions = countOfInteractions;
    }
    
    public int getCountOfInteractions()
    {
        return this._countOfInteractions;
    }
    
    public void setRequirements(GenerationRequirement[] requirements)
    {
        this._requirements = requirements;
    }
    
    public int getCountOfRequirements()
    {
        if (this._requirements == null)
            return 0;
        
        return this._requirements.length;
    }
    
    public int getCountOfCoveredRequirements()
    {
        if (this._requirements == null)
            return 0;
        
        int count = 0;
        
        for (GenerationRequirement generationRequirement : _requirements) {
            if (generationRequirement.isCovered())
                count++;
        }
        
        return count;
    }
    
    public int getCountOfUncoveredRequirements()
    {
        if (this._requirements == null)
            return 0;
        
        int countTotal = getCountOfRequirements();
        int countCovered = getCountOfCoveredRequirements();
        
        return countTotal - countCovered;
    }
    
    public GenerationRequirement[] getRequirements()
    {
        return this._requirements;
    }
    
    public void setIndividuals(Individual[] individuals)
    {
        this._individuals = individuals;
    }
    
    public Individual[] getIndividuals()
    {
        return this._individuals;
    }
    
    public GenerationRequirement[] getUncoveredRequirements()
    {
        ArrayList<GenerationRequirement> uncoveredRequirements = new ArrayList<GenerationRequirement>();
        ArrayList<GenerationRequirement> coveredRequirements = new ArrayList<GenerationRequirement>();
        
        for (Individual individual : getIndividuals()) {
            String coverage = individual.getCoverage().getExecutionPath();
            
            for (GenerationRequirement generationRequirement : getRequirements()) {
                
                if (coveredRequirements.size() == getRequirements().length)
                    break;
                
                if (coverage.contains("-" + generationRequirement.getIdRequirement() + "-"))
                {
                    if (!coveredRequirements.contains(generationRequirement))
                        coveredRequirements.add(generationRequirement);
                }                
            }
        }
        
        for (GenerationRequirement generationRequirement : getRequirements()) {
            if (!coveredRequirements.contains(generationRequirement))
                uncoveredRequirements.add(generationRequirement);
        }
                
        return uncoveredRequirements.toArray(new GenerationRequirement[uncoveredRequirements.size()]);
    }
}
