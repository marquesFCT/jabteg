
package br.usp.each.saeg.agdtpoo.generationAlgorithms;


import br.usp.each.saeg.agdtpoo.entity.Individual;
import java.util.ArrayList;

// Classe que executa a seleção de indivíduos dentro de uma população
public class IndividualsSelector {
    
    // Este processo precisa ser refinado...
    // Hoje o algoritmo esta selecionando os indivíduos que possuem a fitness 
    // acima da média, talvez este processo não seja o ideal, preciso estudar 
    // qual a melhor ação a ser tomada aqui...
        
    public static Individual[] selectIndividuals(Individual[] originalIndividuals)
    {
        if (originalIndividuals.length == 0)
            return new Individual[0];
        
        double average = 0;
        double mount = 0;
        ArrayList<Individual> result = null;
        
        for (Individual individual : originalIndividuals) {
            mount += individual.getFitness();
        }
        
        // Calculo da media
        average = mount / originalIndividuals.length;
        // Soh 97% da media eh valido, para que nao aconteca o problema 
        // de arredondamento, perda de precissao e selecao de nenhum item
        average *= 0.97;
        
        result = new ArrayList<Individual>();

        for (Individual individual : originalIndividuals) {
            
            if (individual.getFitness() >= average)
                result.add(individual);
                
        }
        
        return result.toArray(new Individual[result.size()]);
    }
}
