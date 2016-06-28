
package br.usp.each.saeg.agdtpoo.generationAlgorithms;

import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;

public interface IStrategyFitness {
    void setFitness(IFitness fitness);
    
    IFitness getFitness();
}
