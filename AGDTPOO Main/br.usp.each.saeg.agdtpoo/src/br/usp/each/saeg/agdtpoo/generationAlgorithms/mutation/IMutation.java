
package br.usp.each.saeg.agdtpoo.generationAlgorithms.mutation;

import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;

public interface IMutation {
    Individual mutate(Individual target);
    
    void setInputDomain(InputPrimitiveDomain inputDomain);
    
    boolean isExecutedWithSuccess();
}
