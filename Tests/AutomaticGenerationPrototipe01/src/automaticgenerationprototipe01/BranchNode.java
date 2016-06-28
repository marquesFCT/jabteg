/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automaticgenerationprototipe01;

/**
 *
 * @author Fernando
 */
public class BranchNode {
    private boolean isCovered;
    
    public void isCovered(boolean value)
    {
        this.isCovered = value;
    }
    
    public boolean isCovered()
    {
        return this.isCovered;
    }
}
