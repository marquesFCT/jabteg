
package br.usp.each.saeg.agdtpoo.generationAlgorithms.mutation;

import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.InputPrimitiveDomain;
import java.util.ArrayList;
import java.util.Random;

public class MutationMachine {
    private Individual[] _population;
    private InputPrimitiveDomain _inputDomain; 
    private ArrayList<IMutation> _mutations;
    
    public MutationMachine()
    {                
        // Inicializa a lista de algoritmos de mutação
        initializeMutationTechniques();
    }
    
    public MutationMachine(Individual[] population)
    {
        this.setPopulation(population);
        
        // Inicializa a lista de algoritmos de mutação
        initializeMutationTechniques();
    }
    
    public Individual[] mutate() throws MutationException 
    {   
        if (this._population == null)
            return null;
        
        // Lista de itens que serao retornado apos a crossover
        ArrayList<Individual> crossOverIndividuals = null; 
                
        try            
        {
            // Executa cossover
            crossOverIndividuals = executeCrossover(this._population);
        }
        catch(Exception e)
        {
            // Erro durante o crossover
            throw new MutationException(e.getMessage());
        }
                      
        // Lista de itens que serao retornado apos a mutacao
        ArrayList<Individual> mutationIndividuals = new ArrayList<Individual>();   
                             
        try     
        {        
            // Executa mutacao
            mutationIndividuals = executeMutation(this._population);
        }
        catch(Exception e)
        {
            // Erro durante a mutacao
            throw new MutationException(e.getMessage());
        }
        
        // Adicionar os individuos resultantes de crossover na lista da nova populacao
        for (Individual crossoverIndividual : crossOverIndividuals) {
            mutationIndividuals.add(crossoverIndividual);
        }
        
        return mutationIndividuals.toArray(new Individual[mutationIndividuals.size()]);
    }
    
    private ArrayList<Individual> executeMutation(Individual[] population)
    {
        ArrayList<Individual> individuals = new ArrayList<Individual>();
        
        // Varre item a item da populacao atual a fim de gerar uma nova populacao
        for (Individual initialIndividual : population) {

            IMutation technique = null;
            Individual mutation = null;

            // Seleciona uma tecnica de mutacao
            technique = getRandomIMutation();

            // Passa para a tecnica de mutacao os valor de dicas 
            // para o tipo primitivo
            technique.setInputDomain(this._inputDomain);

            // Executa a mutacao
            mutation = technique.mutate(initialIndividual);

            // Adiciona o individuo a uma nova lista da populacao
            individuals.add(mutation);
        }
        
        return individuals;
    }
    
    private ArrayList<Individual> executeCrossover(Individual[] population)
    {
        int limitOfItens = population.length;
        int quantityOfNewItens = limitOfItens / 4;
        ArrayList<Individual> newIndividuals = new ArrayList<Individual>();
                
        for (int i = 0; i < quantityOfNewItens; i++) {
            
            Random rnd = new Random();
            
            Individual i1 = population[rnd.nextInt(limitOfItens)];
            Individual i2 = population[rnd.nextInt(limitOfItens)];
            
            CrossoverMachine crossMachine = new CrossoverMachine();
            crossMachine.crossover(i1, i2);
            
            if (!crossMachine.executedWithSuccess())
                i--;
            else
            {
                newIndividuals.add(crossMachine.getIndividual1());
                newIndividuals.add(crossMachine.getIndividual2());
            }            
        }
        
        return newIndividuals;
    }
    
    private IMutation getRandomIMutation()
    {
        Random rand = new Random();     
        // Seleciona uma tecnica de mutacao aleatoriamente
        int selectedMethod = rand.nextInt(this._mutations.size());
        
        IMutation mutation = this._mutations.get(selectedMethod);
        
        return mutation;
    }
    
    // Inicializa a lista de mutantes com os mutantes existentes no framework
    private void initializeMutationTechniques()
    {
        this._mutations = new ArrayList<IMutation>();
        
        this._mutations.add(new InputValueMutation());
        this._mutations.add(new RemoveMethodMutation());
        this._mutations.add(new IncludeMethodMutation());
    }
    
    public void setInputDomain(InputPrimitiveDomain inputDomain)
    {
        this._inputDomain = inputDomain;
    }
    
    public void setPopulation(Individual[] population)
    {
        this._population = population;
    }
   
    public Individual[] getPopulation()
    {
        return this._population;
    }
}
