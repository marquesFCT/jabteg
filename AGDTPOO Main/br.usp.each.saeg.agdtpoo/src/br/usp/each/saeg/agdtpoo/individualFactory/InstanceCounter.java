
package br.usp.each.saeg.agdtpoo.individualFactory;

public class InstanceCounter {
    private String _typeName;
    private int _count;
    
    public InstanceCounter()
    {
        this._count = 0;
    }
    
    public void setTypeName(String typeName)
    {
        _typeName = typeName;
    }
    
    public String getTypeName()
    {
        return _typeName;
    }
    
    public int getCount()
    {
        return _count;
    }
    
    public void addNewOccurrence()
    {
        _count ++;
    }
}
