
package br.usp.each.saeg.agdtpoo.entity;

import java.util.ArrayList;

public class ReflectionClass {
    private String _name;
    private String _package;
    private ArrayList<ReflectionConstructor> _constructors;
    private ArrayList<ReflectionMethod> _methods;
    
    public ReflectionClass()
    {
        this._constructors = new ArrayList<ReflectionConstructor>();
        this._methods = new ArrayList<ReflectionMethod>();        
    }
    
    public void setClassName(String value)
    {
        this._name = value;
        
        if (value != null)
            setPackage(_name);
    }
    
    private void setPackage(String className)
    {
        this._package = "";
        
        if (!className.contains("."))
            return;
        
        this._package = className.substring(0, className.lastIndexOf(".")).trim();
    }
    
    public String getPackage()
    {
        return this._package;
    }
    
    public String getClassName()
    {
        return this._name;
    }
    
    public void addConstructor(ReflectionConstructor value)
    {
        this._constructors.add(value);
    }
    
    public void addMethod(ReflectionMethod value)
    {
        this._methods.add(value);
    }
    
    public ReflectionConstructor[] getConstructors()
    {
        return this._constructors.toArray(new ReflectionConstructor[0]);
    }
    
    public ReflectionMethod[] getMethods()
    {
        return this._methods.toArray(new ReflectionMethod[0]);
    }
    
    @Override
    public ReflectionClass clone()
    {
       ReflectionClass clone = new ReflectionClass();
       
        for (ReflectionConstructor reflectionConstructor : this._constructors) {
            clone.addConstructor(reflectionConstructor.clone());
        }
              
        for (ReflectionMethod reflectionMethod : this._methods) {
            clone.addMethod(reflectionMethod.clone());
        }
        
       clone._name = this._name;
       
       return clone;
    }
}
