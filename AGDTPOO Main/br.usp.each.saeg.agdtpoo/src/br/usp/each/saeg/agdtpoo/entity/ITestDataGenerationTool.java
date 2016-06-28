
package br.usp.each.saeg.agdtpoo.entity;

public interface ITestDataGenerationTool {
    GenerationRequirement[] getRequirements(EnumCriterion criterion) throws TestDataGenerationToolException;
    
    GenerationCoverage getCoverage(EnumCriterion criterion) throws TestDataGenerationToolException;
    
    GenerationCoverage[] getCoverages(EnumCriterion criterion) throws TestDataGenerationToolException;
    
    String getExecutionTrace();
    
    void compileIndividuals() throws TestDataGenerationToolException;
}
