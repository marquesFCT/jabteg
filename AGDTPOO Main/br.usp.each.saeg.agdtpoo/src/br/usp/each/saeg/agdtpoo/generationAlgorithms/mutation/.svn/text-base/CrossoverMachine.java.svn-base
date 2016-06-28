
package br.usp.each.saeg.agdtpoo.generationAlgorithms.mutation;

import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.entity.ReflectionMethod;
import br.usp.each.saeg.agdtpoo.entity.TestIndividual;
import java.util.ArrayList;
import java.util.Random;

public class CrossoverMachine {
    
    private Individual newIndividual1;
    private Individual newIndividual2;
    private boolean _executedWithSuccess;
    
    public Individual getIndividual1()
    {
        return this.newIndividual1;
    }
    
    public Individual getIndividual2()
    {
        return this.newIndividual2;
    }
    
    public boolean executedWithSuccess()
    {
        return this._executedWithSuccess;   
    }
    
    // Executa a mescla entre indivíduos
    public void crossover(Individual parent1, Individual parent2)
    {
        this._executedWithSuccess = false;
        
        if (parent1 == parent2)
            return;
                
        this.newIndividual1 = new Individual();
        this.newIndividual2 = new Individual();
        
        TestIndividual ti1 = new TestIndividual(parent1.getTestIndividual().getBytecodeType());
        TestIndividual ti2 = new TestIndividual(parent2.getTestIndividual().getBytecodeType());
        
        // Cópia do método sob teste
        ti1.setMethodUnderTest(parent1.getTestIndividual().getMethodUnderTest().clone());
        ti2.setMethodUnderTest(parent2.getTestIndividual().getMethodUnderTest().clone());
        
        // Troca de contrutores
        ti1.setConstructor(parent2.getTestIndividual().getConstructor().clone());
        ti2.setConstructor(parent1.getTestIndividual().getConstructor().clone());
        
        // Faz a mescla de métodos entre os indivíduos
        ti1.setMethods(crossoverIntermediateMethods(parent1.getTestIndividual(), parent2.getTestIndividual()));
        ti2.setMethods(crossoverIntermediateMethods(parent2.getTestIndividual(), parent1.getTestIndividual()));        
        
        // Atribuição dos indivíduos
        this.newIndividual1.setIndividual(ti1);
        this.newIndividual2.setIndividual(ti2);
        
        this._executedWithSuccess = true;
    } 
    
    // Executa a mescla de métodos intermediários
    private ReflectionMethod[] crossoverIntermediateMethods(TestIndividual target, TestIndividual copy)
    {
        int methodsToCopy = 0;
        ReflectionMethod[] intermediateMethods = copy.getMethods();
        ArrayList<ReflectionMethod> newIntermediateMethods = new  ArrayList<ReflectionMethod>();
        
        if (intermediateMethods == null || intermediateMethods.length == 0)     
            return new ReflectionMethod[0];
        
        for (ReflectionMethod reflectionMethod : target.getMethods()) {
            newIntermediateMethods.add(reflectionMethod);
        }
               
        Random rnd = new Random();
        int quantityOfMethodsToCopy = rnd.nextInt(intermediateMethods.length);
        
        for (int i = 0; i < quantityOfMethodsToCopy; i++) {
            
            int nextCopy = rnd.nextInt(intermediateMethods.length);
            
            if (nextCopy >= 0 && nextCopy < intermediateMethods.length)
                newIntermediateMethods.add(intermediateMethods[nextCopy].clone());
            
        }
        
        return newIntermediateMethods.toArray(new ReflectionMethod[newIntermediateMethods.size()]);
    }
}
