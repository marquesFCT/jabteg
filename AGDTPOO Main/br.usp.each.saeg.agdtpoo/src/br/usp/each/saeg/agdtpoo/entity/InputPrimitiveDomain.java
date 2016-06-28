
package br.usp.each.saeg.agdtpoo.entity;

import java.util.ArrayList;

public class InputPrimitiveDomain {
    
    // Dicas de geracao para os metodos intermediarios
    private PrimitiveRandomTip _intermediateMethodTips;
        
    // Dicas de geração para os métodos
    private ArrayList<MethodPrimitiveDomain> _methodsDomain;
    
    public InputPrimitiveDomain()
    {
        _methodsDomain = new ArrayList<MethodPrimitiveDomain>();
    }
        
    public void setIntermediateMethodPrimitiveRandomTip(PrimitiveRandomTip tip)
    {
        this._intermediateMethodTips = tip;
    }
    
    public PrimitiveRandomTip getIntermediateMethodPrimitiveRandomTip()
    {
        return this._intermediateMethodTips;
    }
        
    public void addMethodDomain(MethodPrimitiveDomain methodDomain)
    {
        if (methodDomain != null)
            methodDomain.setInputPrimitiveDomain(this);
        this._methodsDomain.add(methodDomain);
    }   
     
    public MethodPrimitiveDomain getMethodDomain(String methodName)
    {
        if (this._methodsDomain == null)
            return null;
        
        for (MethodPrimitiveDomain methodPrimitiveDomain : this._methodsDomain) {
            if (methodPrimitiveDomain.getMethodName().equals(methodName))
                return methodPrimitiveDomain;
        }
        
        return null;
    }
    
    public MethodPrimitiveDomain getOrCreateMethodDomain(ReflectionMethod method)
    {
        return  getOrCreateMethodDomain(method.getSignature());
    }    
    
    public MethodPrimitiveDomain getOrCreateMethodDomain(String methodName)
    {
        MethodPrimitiveDomain mpd = getMethodDomain(methodName);
        
        if (mpd == null)
        {
            mpd = new MethodPrimitiveDomain();
            mpd.setMethodName(methodName);
            addMethodDomain(mpd);            
        }
        
        return mpd;        
    }    
}
