
package br.usp.each.saeg.agdtpoo.entity;

public class TestIndividual {
    private ReflectionConstructor _constructor;
    private ReflectionMethod[] _methods;
    private ReflectionMethod _methodUnderTest;
    private BytecodeType _byteType;

    public TestIndividual(BytecodeType byteType)
    {
        _byteType = byteType;
    }
        
    public BytecodeType getBytecodeType()
    {
        return this._byteType;
    }
    
    public void setMethodUnderTest(ReflectionMethod value)
    {
        this._methodUnderTest = value;
    }
    
    public void setConstructor(ReflectionConstructor value)
    {
        this._constructor = value;
    }
    
    public void setMethods(ReflectionMethod[] value)
    {
        this._methods = value.clone();
    }
    
    public ReflectionConstructor getConstructor()
    {
        return this._constructor;
    }
    
    public ReflectionMethod getMethodUnderTest()
    {
        return this._methodUnderTest;
    }   
    
    public ReflectionMethod[] getMethods()
    {
        return this._methods;
    }    
    
    public TestIndividual clone()
    {
        TestIndividual clone = new TestIndividual(this.getBytecodeType());
        
        if (this.getConstructor() != null)
            clone.setConstructor(this.getConstructor().clone());
        
        if (this.getMethodUnderTest() != null)
            clone.setMethodUnderTest(this.getMethodUnderTest().clone());
        
        if (this.getMethods() != null)
            clone.setMethods(this.getMethods().clone());
        
        return clone;
    }
}
