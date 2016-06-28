
package br.usp.each.saeg.agdtpoo.entity;

import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;

public interface IGUIGenerationStrategy {
    IFitness[] getSupportedFitnesses();
    
    ISelection[] getSupportedSelections();
    
    EnumCriterion[] getSupportedCriterions();
    
    GUIGenerationCustomParameter[] getCustomParameters();
}
