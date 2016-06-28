
package br.jabuti.agdtpoo.model;

import java.util.ArrayList;

public class InstrumentClass {
    private int _id;
    private String _name;
    private ArrayList<InstrumentMethod> _methods;

    public InstrumentClass()
    {
        this._methods = new ArrayList<InstrumentMethod>();                
    }
        
    public InstrumentMethod[] getMethods()
    {
        return this._methods.toArray(new InstrumentMethod[this._methods.size()]);
    }
    
    public void addMethod(InstrumentMethod method)
    {
        this._methods.add(method);
    }
    
    public int getId()
    {
        return this._id;
    }
    
    public void setId(int id)
    {
        this._id = id;
    }
        
    public String getName()
    {
        return this._name;
    }
    
    public void setName(String name)
    {
        this._name = name;
    }
}
