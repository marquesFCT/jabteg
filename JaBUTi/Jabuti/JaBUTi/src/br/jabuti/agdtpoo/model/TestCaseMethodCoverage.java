
package br.jabuti.agdtpoo.model;

import java.util.HashSet;

public class TestCaseMethodCoverage {
    private String methodName;
    private HashSet<TestCaseCoverage> coverages;

    public TestCaseMethodCoverage()
    {
        this.coverages = new HashSet<TestCaseCoverage>();                
    }
    
    public TestCaseCoverage[] getCoverage()
    {
        TestCaseCoverage[] array = new TestCaseCoverage[this.coverages.size()];
        return this.coverages.toArray(array);
    }
    
    public void AddTestCaseMethodCoverage(TestCaseCoverage coverage)
    {
        if (this.coverages == null)
            this.coverages = new HashSet<TestCaseCoverage>();
        
        this.coverages.add(coverage);
    }
            
    public void SetMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    public String GetMethodName()
    {
        return this.methodName;
    }
}
