
package br.usp.each.saeg.agdtpoo.entity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReflectionMethod {
    private String _methodSignature;
    private String _name;
    private Type _returnValue;    
    private ArrayList<ReflectionParameter> _parameters;
    private boolean _isStatic;
    private ReflectionClass _class;
    
    public ReflectionClass getReflectionClass()
    {
        return this._class;
    }
    
    public void setReflectionClass(ReflectionClass value)
    {
        this._class = value;
    }
    
    public ReflectionParameter[] getParameters()
    {       
        return this._parameters.toArray(new ReflectionParameter[0]);
    }
    
    public void setStatic(boolean value)
    {
        this._isStatic = value;
    }
    
    public boolean isStatic()
    {
        return this._isStatic;
    }
    
    public String getName()
    {
        return this._name;
    }
    
    public void setName(String name)
    {
        this._name = name;
    }
        
    public String getSignature()            
    {
        return this._methodSignature;
    }
    
    public void setSignature(String signature)
    {
        this._methodSignature = signature;
    }
    
    public void addParameter(ReflectionParameter parameter)
    {
        this._parameters.add(parameter.clone());
    }
    
    public Type getReturnType()
    {
        return this._returnValue;
    }
    
    public void setReturnType(Type value)
    {
        this._returnValue = value;
    }
    
    public ReflectionMethod()
    {
        this._parameters = new ArrayList<ReflectionParameter>();                
    }
    
    @Override
    public ReflectionMethod clone()
    {
        ReflectionMethod clone = new ReflectionMethod();
        
        clone.setReflectionClass(this.getReflectionClass());
        clone.setStatic(this.isStatic());
        clone.setName(this.getName());
        clone.setReturnType(this.getReturnType());
        clone.setSignature(this.getSignature());
        for (ReflectionParameter reflectionParameter : this._parameters) {
            clone.addParameter(reflectionParameter.clone());
        }
        
        return clone;
    }
}
