
package br.usp.each.saeg.agdtpoo.entity;

public class GUIGenerationCustomParameter {
    
    private Object _defaultValue;
    
    private String _legend;
    
    private PrimitiveType _type;
    
    private Object _value;

    private String _settterMethodSignature;

    public String getSettterMethodSignature() {
        return _settterMethodSignature;
    }

    public void setSettterMethodSignature(String settterMethodSignature) {
        this._settterMethodSignature = settterMethodSignature;
    }
    
    public String getSetterMethodName()
    {
        String returnValue = this.getSettterMethodSignature();
        
        int index = returnValue.indexOf("(");
        
        returnValue = returnValue.substring(0, index);
        
        return returnValue;
    }
    
    public Object getDefaultValue() {
        return _defaultValue;
    }

    public void setDefaultValue(Object _defaultValue) {
        this._defaultValue = _defaultValue;
    }

    public String getLegend() {
        return _legend;
    }

    public void setLegend(String _legend) {
        this._legend = _legend;
    }

    public PrimitiveType getType() {
        return _type;
    }

    public void setType(PrimitiveType _type) {
        this._type = _type;
    }

    public Object getValue() {
        return _value;
    }

    public void setValue(Object _value) {
        this._value = _value;
    }
}
