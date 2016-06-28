/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaticgenerationprototipe01;

/**
 *
 * @author Fernando
 */
public class Individual {
    private BranchNode[] coverage;
    private double fitness;
    
    public void setFitnesss(double value)
    {
        this.fitness = value;                
    }
            
    public double getFitness()
    {
        return this.fitness;
    }
    
    public void setCoverage(BranchNode[] value)
    {
        this.coverage = value;
    }
    
    public BranchNode[] getCoverage()
    {
        return this.coverage;
    }
}
