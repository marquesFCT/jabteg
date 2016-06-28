
package br.usp.each.saeg.agdtpoo.entity;

public class ReflectionConstructor extends ReflectionMethod  {
    public ReflectionConstructor()
    {
        super();
        
        this.setReturnType(Void.TYPE);
    }
    
    @Override
    public ReflectionConstructor clone()
    {               
        ReflectionConstructor clone = new ReflectionConstructor();
        
        for (ReflectionParameter reflectionParameter : this.getParameters()) {
            clone.addParameter(reflectionParameter.clone());
        }
        
        clone.setName(this.getName());
        
        clone.setReturnType(Void.TYPE);
        
        return clone;
    }
}
