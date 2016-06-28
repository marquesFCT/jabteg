
package selectionAlgorithms;

import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.selection.ISelection;
import java.util.Random;

public class RouletteSelection  implements ISelection {

    @Override
    public Individual[] selectIndividualsToMutation(Individual[] population) {

        double sumFitness = 0;        
        int quantityOfSelecteds = population.length / 2;        
        Individual[] newPopulation = new Individual[quantityOfSelecteds];
                        
        for (Individual individual : population) {
            sumFitness += individual.getFitness();
        }
               
        for (int i = 0; i < quantityOfSelecteds; i++) {
           
            Individual selected = null;
            Random rand = new Random();
            double rouletteValue = (double) (rand.nextDouble() * sumFitness);
            
            double rouletteSum = 0;
            for (Individual individual : population) {
                rouletteSum += individual.getFitness();
                
                if (rouletteSum >= rouletteValue)
                {
                    selected = individual;
                    break;
                }
            }
            
            newPopulation[i] = selected;
        }
        
        return newPopulation;        
    }
    
    @Override
    public String getSelectionName() {
        return "Roulette Selection";
    }
    
}
