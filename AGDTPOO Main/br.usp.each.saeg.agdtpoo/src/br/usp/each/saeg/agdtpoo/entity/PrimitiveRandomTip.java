
package br.usp.each.saeg.agdtpoo.entity;

public class PrimitiveRandomTip {
    private String[] _possibleStrings;
    private char[] _possibleChar;
    private int _minimalValueInt;
    private int _maximalValueInt;
    private double _minimalValueDouble;
    private double _maximalValueDouble;
    private double _minimalValueFloat;
    private double _maximalValueFloat; 
    private long _minimalValueLong;
    private long _maximalValueLong;   
    private short _minimalValueShort;
    private short _maximalValueShort;
    private int _objectGenerationMaxDepth;
    private int _maxCountOfInteractions;

    public PrimitiveRandomTip()
    {
        this._possibleStrings = null;
        this._possibleChar = null;
        this._minimalValueInt = 0;
        this._maximalValueInt = 100;
        this._minimalValueDouble = 0;
        this._maximalValueDouble = 100;
        this._minimalValueFloat = 0;
        this._maximalValueFloat = 100;
        this._minimalValueLong = 0;
        this._maximalValueLong = 100;   
        this._minimalValueShort = 0;
        this._maximalValueShort = 100;
        this._objectGenerationMaxDepth = 2;
        this._maxCountOfInteractions = 2;
    }
    
    public int getMaxCountOfInteractions()
    {
        return this._maxCountOfInteractions;
    }
    
    public void setMaxCountOfInteractions(int value)
    {
        this._maxCountOfInteractions = value;
    }
    
    public short getMinimalValueShort() {
        return _minimalValueShort;
    }

    public void setMinimalValueShort(short minimalValueShort) {
        this._minimalValueShort = minimalValueShort;
    }
    
    public short getMaximalValueShort() {
        return _maximalValueShort;
    }

    public void setMaximalValueShort(short maximalValueShort) {
        this._maximalValueShort = maximalValueShort;
    }
    
    public long getMinimalValueLong() {
        return _minimalValueLong;
    }

    public void setMinimalValueLong(long minimalValueLong) {
        this._minimalValueLong = minimalValueLong;
    }
    
    public long getMaximalValueLong() {
        return _maximalValueLong;
    }

    public void setMaximalValueLong(long maximalValueLong) {
        this._maximalValueLong = maximalValueLong;
    }    
    
    public double getMaximalValueFloat() {
        return _maximalValueFloat;
    }

    public void setMaximalValueFloat(double maximalValueFloat) {
        this._maximalValueFloat = maximalValueFloat;
    }

    public double getMaximalValueDouble() {
        return _maximalValueDouble;
    }

    public void setMaximalValueDouble(double maximalValueDouble) {
        this._maximalValueDouble = maximalValueDouble;
    }

    public int getMaximalValueInt() {
        return _maximalValueInt;
    }

    public void setMaximalValueInt(int maximalValueInt) {
        this._maximalValueInt = maximalValueInt;
    }

    public double getMinimalValueFloat() {
        return _minimalValueFloat;
    }

    public void setMinimalValueFloat(double minimalValueFloat) {
        this._minimalValueFloat = minimalValueFloat;
    }

    public double getMinimalValueDouble() {
        return _minimalValueDouble;
    }

    public void setMinimalValueDouble(double minimalValueDouble) {
        this._minimalValueDouble = minimalValueDouble;
    }

    public int getMinimalValueInt() {
        return _minimalValueInt;
    }

    public void setMinimalValueInt(int minimalValueInt) {
        this._minimalValueInt = minimalValueInt;
    }

    public char[] getPossibleChar() {
        return _possibleChar;
    }

    public void setPossibleChar(char[] possibleChar) {
        this._possibleChar = possibleChar;
    }

    public String[] getPossibleStrings() {
        return _possibleStrings;
    }

    public void setPossibleStrings(String[] possibleStrings) {
        this._possibleStrings = possibleStrings;
    }

    public int getObjectGenerationMaxDepth() {
        return _objectGenerationMaxDepth;
    }

    public void setObjectGenerationMaxDepth(int objectGenerationMaxDepth) {
        this._objectGenerationMaxDepth = objectGenerationMaxDepth;
    }
}
