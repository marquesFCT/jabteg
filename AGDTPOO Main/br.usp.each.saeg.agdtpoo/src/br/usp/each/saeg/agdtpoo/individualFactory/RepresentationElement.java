
package br.usp.each.saeg.agdtpoo.individualFactory;

public class RepresentationElement {
    private int _position;
    private String _leftSide;
    private String _rigthside;
    private String _instanceName;
    
    public void setInstanceName(String value)
    {
        this._instanceName = value;
    }
               
    public String getInstanceName()
    {
        return this._instanceName;
    }
    
    public String getLeftSide()
    {
        if (this._leftSide == null)
            return "";
        else      
            return this._leftSide;
    }
    
    public String getRigthSide()
    {
        if (this._rigthside == null)
            return "";
        else        
            return this._rigthside;
    }
    
    public void setLeftSide(String value)
    {        
        this._leftSide = value;
    }
    
    public void setRightside(String value)
    {
        this._rigthside = value;
    }
    
    public int getPosition()
    {
        return this._position;
    }
    
    public void setPosition(int value)
    {
        this._position = value;
    }
    
    public void concat(RepresentationElement newElement)
    {
        String rightSide;
        String leftSide;
        
        rightSide = newElement.getRigthSide();
        leftSide = newElement.getLeftSide();
        
        if (this._rigthside == null)
            this._rigthside = "";
        
        if (this._leftSide == null)
            this._leftSide = "";
        
        if (!rightSide.equals(""))
            this._rigthside += ", " + rightSide;
        if (!leftSide.equals(""))            
            this._leftSide += ":" + leftSide;                
    }
}
