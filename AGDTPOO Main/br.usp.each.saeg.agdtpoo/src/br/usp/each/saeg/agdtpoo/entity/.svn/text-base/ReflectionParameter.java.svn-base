
package br.usp.each.saeg.agdtpoo.entity;

import br.usp.each.saeg.agdtpoo.util.Features;
import java.lang.reflect.Type;

public class ReflectionParameter {
    private Type _parameterType;
    private Object _value;
    private boolean _isArray;
    private boolean _isAbstract;
    private Class _componentType;
    
    public void setComponentType(Class componentType)
    {
        this._componentType = componentType;
    }
    
    public Class getComponentType()
    {
        return this._componentType;
    }
    
    public void isAbstract(boolean isAbstract)
    {
        this._isAbstract = isAbstract;
    }
    
    public boolean isAbstract()
    {
        return this._isAbstract;
    }
    
    public void isArray(boolean isArray)
    {
        this._isArray = isArray;
    }
    
    public boolean isArray()
    {
        return this._isArray;
    }
    
    public Object getParameterValue()
    {
        return this._value;
    }
    
    public void setParameterValue(Object value)
    {
        this._value = value;
    }
    
    public Type getParameterType()
    {
        return this._parameterType;
    }
   
    public void setParameterType(Type value)
    {        
        this._parameterType = value;
    }
    
    public boolean isString()
    {
        if (this._parameterType != null)
            return Features.isString(this.getParameterType());
        else
            return false; 
    }
    
    public boolean isPrimitive()
    {
        if (this._parameterType != null)
            return Features.isPrimitive(this.getParameterType());
        else
            return true;           
    }
    
    @Override
    public ReflectionParameter clone()
    {
        ReflectionParameter clone = new ReflectionParameter();
        
        clone._parameterType = this._parameterType;
        clone._value = this._value;
        clone._componentType = this._componentType;
        clone._isAbstract = this._isAbstract;
        clone._isArray = this._isArray;
            
        return clone;
    }
}
