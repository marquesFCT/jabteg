
package fitnessAlgorithms;

import br.usp.each.saeg.agdtpoo.entity.GenerationCoverage;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.FitnessException;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.fitness.IFitness;
import java.util.Arrays;
import java.util.List;

public class SimilarityFitness implements IFitness {
   
    @Override
    public double computeFitness(GenerationRequirement requirement, GenerationCoverage coverage) throws FitnessException {
        
        double[] fitnesses = null; 
        String[] path = null;
        
        // Captura os requisitos necessarios para atingir o alvo
        path = requirement.getCoveragePaths();
        fitnesses = new double[path.length]; 
        
        for (int i = 0; i < path.length; i++) {
            // Calcula a fitness de cada possivel caminho para cobrir um requisito
            fitnesses[i] = getFitness(path[i], coverage.getExecutionPath());
            
            // Se um dos requisitos for igual a 1, então já retorna este valor e 
            // não precisa executar os próximos passos
            if (fitnesses[i] == 1)
                return 1;
        }
                
        if (fitnesses.length == 1)
            return fitnesses[0];
       
        // Identifica qual a melhor fitness para atingir um item
        double maxFitness = 0;
        for (double fitness : fitnesses) {
            if (fitness > maxFitness)
                maxFitness = fitness;
        }
        
        return maxFitness;
    }

    private double getFitness(String target, String realized)
    {
        if (realized.contains(target))
           return 1;
        
        // Requisitos que devem ser cobertos
        String[] requirementsTarget = getRequirements(target);
        
        // Requisitos que foram cobertos
        String[] requirementsRealized = getRequirements(realized);
        
        // Esta variável acumula a quantidade de itens que pertencem 
        // ao caminho desejado e que foram cobertos
        double countOfCorrectRequirements = 0;
        
        // Converte o array de alvos para LIST, por que este objeto da suporte
        // ao método CONTAINS. 
        List<String> realizedList = Arrays.asList(requirementsRealized);
                
        // Identifica quantos nós que deveriam ser cobertos foram executados.
        for (String requirement : requirementsTarget) {
            if (realizedList.contains(requirement))
                countOfCorrectRequirements++;
        }
                
        // Divide a quantidade de itens cobertos pela quantidade de itens necessária
        double returnValue = countOfCorrectRequirements / (requirementsTarget.length * 1.0);
        
        if (returnValue == 1)
            return 1;
        
        return returnValue;
    }
    
    private String[] getRequirements(String requirement)
    {
        // Remoção do primeiro hifen
        if (requirement.startsWith("-"))
            requirement = requirement.substring(1);
        // Remoção do ultimo hifen
        if (requirement.endsWith("-"))
            requirement = requirement.substring(0, requirement.length() - 1);
        
        String[] requirementsTarget = requirement.split("-");
        
        return requirementsTarget;
    }
    
    @Override
    public String getFitnessName() {
        return "Similarity Fitness";
    }

    @Override
    public void setRequirements(GenerationRequirement[] requirements) {
        // Apenas para implementar a interface
    }    
    
    @Override
    public void setCurrentPopulation(Individual[] individuals) {
        // Apenas para implementar a interface
    }  
}
