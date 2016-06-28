

package br.usp.each.saeg.agdtpoo.bytecodeFoundation;

import br.usp.each.saeg.agdtpoo.entity.JavaClass;
import br.usp.each.saeg.agdtpoo.entity.ReflectionClass;
import br.usp.each.saeg.agdtpoo.util.LogWriter;

// "java.lang.String"
public class BytecodeReader {
       
    private Class _classUnderTeste;
    private String _targetClassName;
    private String _assemblyPath;
    private ReflectionClass _refClass;
    private JavaClass _currentJavaClass;
        
    public BytecodeReader(String assemblyPath, String className)
    {
        // Atribuir o caminho do arquivo .jar
        this._assemblyPath = assemblyPath;   
        
        // Atribuir o nome da class Java sob teste
        this._targetClassName = className;
    }
    
    public ReflectionClass getReflectionClass()
    {
        ReflectionClass returnValue;
        
        if (this._currentJavaClass == null)
            this._currentJavaClass = getJavaClass();
        
        returnValue = ClassParser.parse(this._currentJavaClass);
        
        return returnValue;
    }           
            
    // Carregar classe sob teste
    public JavaClass getJavaClass()
    {        
        JavaClass returnValue;        
        
        if (this._currentJavaClass != null)
            return this._currentJavaClass;
        
        // Criando instância de um JavaClass
        returnValue = new JavaClass();
        
        try
        {
            // Carrega via reflection a classe sob teste
            this._classUnderTeste = JarReader.GetClassUsingJarPath(this._assemblyPath, this._targetClassName);
            
            // Converter o tipo Class para JavaClass
            returnValue = JavaClassParser.Parse(this._classUnderTeste);
                        
            // Registrar dependencias de tipos em memoria
            BytecodeRegister reg = new BytecodeRegister(this._assemblyPath);
            reg.RegisterSubTypes(returnValue);
        }
        catch(Exception e) {
            LogWriter.write(e);
        }
        
        this._currentJavaClass = returnValue;
        
        return returnValue;
    }
    

}
