/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jabuti.agdtpoo.control;

import br.jabuti.agdtpoo.model.*;

public class TestCaseMethodController {
    
    public static TestCaseMethod getTestCaseMethodFromLabel(TestCaseMethods methodsJUnit, String label)
    {
        TestCaseMethod returnValue = null;
        
        for (TestCaseMethod tcm : methodsJUnit) {
            if (tcm.GetLabel().equals(label))
                returnValue = tcm;
        }
        
        return returnValue;
    }
    
}
