
package br.usp.each.saeg.agdtpoo.entity;

public class MethodPrimitiveDomain {
    private String _methodName;
    private InputPrimitiveDomain _primitiveDomain;        
    // Dicas de geracao para o metodo sob teste
    private PrimitiveRandomTip[] _testMethodTips;
        
    public void setInputPrimitiveDomain(InputPrimitiveDomain inputDomain)
    {
        _primitiveDomain = inputDomain;
    }
        
    public String getMethodName() {
        return _methodName;
    }

    public void setMethodName(String methodName) {
        this._methodName = methodName;
    }
        
    public void setMethodPrimitiveRandomTips(PrimitiveRandomTip[] tips)
    {
        this._testMethodTips = tips;
    }
    
    public PrimitiveRandomTip getMethodPrimitiveRandomTips(int index)
    {
        if (_testMethodTips == null)
            return _primitiveDomain.getIntermediateMethodPrimitiveRandomTip();
        
        if (index >= this._testMethodTips.length)
            return _primitiveDomain.getIntermediateMethodPrimitiveRandomTip();
        
        PrimitiveRandomTip randomTip = this._testMethodTips[index];
        
        if (randomTip == null)
            return _primitiveDomain.getIntermediateMethodPrimitiveRandomTip();
        
        return randomTip;
    }     
    
}
