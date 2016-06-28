
package br.usp.each.saeg.agdtpoo.generationAlgorithms.selection;

import br.usp.each.saeg.agdtpoo.entity.Individual;

public interface ISelection {
    
    Individual[] selectIndividualsToMutation(Individual[] population);
    
    String getSelectionName();
}
