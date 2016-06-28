
package br.usp.each.saeg.agdtpoo.bytecodeFoundation;

import br.usp.each.saeg.agdtpoo.util.LogWriter;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

// Nesta classe serão definidos os frameworks externos de leitura de arquivos Jar
// qualquer dependência de framework externo deve ser feita aqui.
public class JarReader {
    public static Class GetClassUsingJarPath(String jarPath, String className)
    {
        Class returnValue = null;
        
        try
        {
            File file = new File(jarPath);
            URL url = file.toURL();
            URL[] urls = new URL[]{ url };
            
            ClassLoader cl = new URLClassLoader(urls);
            
            returnValue = cl.loadClass(className);          
        }
        catch(Exception e) {
            LogWriter.write(e);
        }
        
        return returnValue;
    }
}
