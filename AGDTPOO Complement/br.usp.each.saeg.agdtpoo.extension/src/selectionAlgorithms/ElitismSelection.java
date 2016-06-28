
package selectionAlgorithms;

import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import java.util.Arrays;

public class ElitismSelection  implements ISelection {

    @Override
    public Individual[] selectIndividualsToMutation(Individual[] population) {
                
        int quantityOfSelecteds = population.length / 2;
        
        Arrays.sort(population);
        
        Individual[] newPopulation = new Individual[quantityOfSelecteds];
        
        for (int i = 0; i < quantityOfSelecteds; i++) {
            newPopulation[i] = population[i];
        }
        
        return newPopulation;        
    }
    
    @Override
    public String getSelectionName() {
        return "Elitism Selection";
    }
    
}
