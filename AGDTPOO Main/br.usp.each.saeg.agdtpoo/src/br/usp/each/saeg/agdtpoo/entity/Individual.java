
package br.usp.each.saeg.agdtpoo.entity;

public class Individual implements Comparable<Individual> {
    private TestIndividual _individual;
    private GenerationCoverage coverage;
    private double fitness;
    
    public void setIndividual(TestIndividual individual)
    {
        this._individual = individual;
    }
    
    public TestIndividual getTestIndividual()
    {
        return this._individual;
    }
    
    public void setFitnesss(double value)
    {
        this.fitness = value;                
    }
            
    public double getFitness()
    {
        return this.fitness;
    }
    
    public void setCoverage(GenerationCoverage value)
    {
        this.coverage = value;
    }
    
    public GenerationCoverage getCoverage()
    {
        return this.coverage;
    }
            
    @Override
    public int compareTo(Individual o) {
        double myfitness = getFitness();
        double compareFitness = o.getFitness();
        
        if (myfitness == compareFitness)
            return 0;
        else if (myfitness < compareFitness)
            return - 1;

        return 1;
    }
}
