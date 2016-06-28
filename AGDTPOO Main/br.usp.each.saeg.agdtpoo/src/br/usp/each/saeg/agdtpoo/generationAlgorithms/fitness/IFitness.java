
package br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness;

import br.usp.each.saeg.agdtpoo.entity.*;

public interface IFitness {
    
    String getFitnessName();
    
    void setRequirements(GenerationRequirement[] requirements);
    
    void setCurrentPopulation(Individual[] individuals);
    
    double computeFitness(GenerationRequirement requirement, GenerationCoverage coverage) throws FitnessException;
    
}