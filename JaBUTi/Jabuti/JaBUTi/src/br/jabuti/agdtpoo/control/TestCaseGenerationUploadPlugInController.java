
package br.jabuti.agdtpoo.control;

import br.jabuti.agdtpoo.model.TestDataGenerationPlugIn;
import br.jabuti.agdtpoo.control.data.TestCaseGenerationUploadPlugInData;
import br.usp.each.saeg.agdtpoo.entity.ByteCodeFoundationException;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationResource;
import br.usp.each.saeg.agdtpoo.util.JarAGDTPOODependenciesDynamicLoader;

public class TestCaseGenerationUploadPlugInController {
    
    public static boolean verifyValidPlugIn(TestDataGenerationPlugIn plugIn)
    {
        return TestCaseGenerationUploadPlugInData.verifyValidPlugIn(plugIn.JarPath);
    }
    
    public static boolean savePlugIn(TestDataGenerationPlugIn plugIn)
    {
        return TestCaseGenerationUploadPlugInData.savePlugIn(plugIn);
    }
    
    public static TestDataGenerationPlugIn[] getPlugIns()
    {
        return TestCaseGenerationUploadPlugInData.getPlugIns();
    }
    
    public static TestDataGenerationResource getResources(TestDataGenerationPlugIn plugIn) throws ByteCodeFoundationException
    {
        JarAGDTPOODependenciesDynamicLoader loader = new JarAGDTPOODependenciesDynamicLoader();
                
        TestDataGenerationResource returnValue  = loader.getTestDataGenerationResource(plugIn.JarPath);
        
        returnValue.setPlugInName(plugIn.Name);
        
        return returnValue;
    }
}
