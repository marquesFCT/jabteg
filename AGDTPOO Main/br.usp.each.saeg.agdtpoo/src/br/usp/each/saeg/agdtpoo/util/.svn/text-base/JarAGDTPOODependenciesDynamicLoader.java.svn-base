
package br.usp.each.saeg.agdtpoo.util;

import br.usp.each.saeg.agdtpoo.bytecodeFoundation.JarClassLoaderUtil;
import br.usp.each.saeg.agdtpoo.entity.ByteCodeFoundationException;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;

public class JarAGDTPOODependenciesDynamicLoader {
    
    public TestDataGenerationResource getTestDataGenerationResource(String jarPath) throws ByteCodeFoundationException
    {
        JarClassLoaderUtil loader = new JarClassLoaderUtil();
        
        return loader.getTestDataGenerationResource(jarPath);
    }
}
