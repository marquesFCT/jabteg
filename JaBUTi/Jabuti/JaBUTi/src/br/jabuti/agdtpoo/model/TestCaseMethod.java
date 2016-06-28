
package br.jabuti.agdtpoo.model;

import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GenerationCoverage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TestCaseMethod {
    private int id;
    private String name;
    private String label;
    private String methodUnderTestCoverage;
    private HashSet<TestCaseMethodCoverage> coverages;
    private Map<EnumCriterion, Object[]> criterionsCoverage;

    public TestCaseMethod()
    {
        this.coverages = new HashSet<TestCaseMethodCoverage>();   
        this.criterionsCoverage = new HashMap<EnumCriterion, Object[]>();
    }
    
    public void setMethodUnderTestCoverage(String methodUnderTestCoverage)
    {
        this.methodUnderTestCoverage = methodUnderTestCoverage;
    }
    
    public GenerationCoverage getGenerationCoverage(EnumCriterion criterion)
    {
        GenerationCoverage returnValue = new GenerationCoverage();
        Object[] objCoverage = new Object[0];
        
        if (!criterionsCoverage.containsKey(criterion))
            returnValue.setExecutionPath("");
        else
        {   
            String methodCoverage = "";
            objCoverage = criterionsCoverage.get(criterion);
            
            for (Object object : objCoverage) {
                methodCoverage += object.toString() + "-";
            }
        
            methodCoverage = "-" + methodCoverage;
        
            returnValue.setExecutionPath(methodCoverage);
        }
        
        return returnValue;
    }
    
    // Apagar o método abaixo
    public GenerationCoverage getGenerationCoverage()
    {
        GenerationCoverage returnValue = new GenerationCoverage();
        
        returnValue.setExecutionPath(getMethodUnderTestCoverage());
        
        return returnValue;
    }
    
    private String getMethodUnderTestCoverage()
    {
        return this.methodUnderTestCoverage;
    }
    
    public TestCaseMethodCoverage[] getCoverage()
    {
        TestCaseMethodCoverage[] array = new TestCaseMethodCoverage[this.coverages.size()];
        return this.coverages.toArray(array);
    }
    
    public void AddTestCaseMethodCoverage(TestCaseMethodCoverage coverage)
    {
        if (this.coverages == null)
            this.coverages = new HashSet<TestCaseMethodCoverage>();
        
        this.coverages.add(coverage);
    }
    
    public void AddCriterionCoverage(EnumCriterion criterion, Object[] coverage)
    {       
        this.criterionsCoverage.put(criterion, coverage);
    }
        
    public void SetId(int id)
    {
        this.id = id;
    }
    
    public int GetId()
    {
        return this.id;
    }
    
    public void SetName(String name)
    {
        this.name = name;
    }
    
    public String GetName()
    {
        return this.name;
    }    
    
    public void SetLabel(String label)
    {
        this.label = label;
    }
    
    public String GetLabel()
    {
        return this.label;
    }  
}
