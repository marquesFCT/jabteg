 
package br.usp.each.saeg.agdtpoo;

import br.usp.each.saeg.agdtpoo.entity.*;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.util.Date;

public class program {
    public static void main(String[] args) {
        
        String jarFile = "C:\\Java Dev\\Tests\\JavaLibraryTeste\\dist\\JavaLibraryTeste.jar";
        String className = "Carro";
        String methodUnderTest = "acelerar(float)";
        String[] unitTestStrings;
        String unitTestClass;
        PrimitiveRandomTip primitiveTips;
        
        primitiveTips = new PrimitiveRandomTip();        
          
        primitiveTips.setMinimalValueLong(116);
        primitiveTips.setMaximalValueLong(120);
        
        primitiveTips.setMinimalValueDouble(50000);
        primitiveTips.setMaximalValueDouble(60000);
        
        primitiveTips.setMinimalValueFloat(10);
        primitiveTips.setMaximalValueFloat(250);
               
        primitiveTips.setMinimalValueInt(2005);
        primitiveTips.setMaximalValueInt(2012);
                
        primitiveTips.setPossibleStrings(new String[] { "AAA-5555", "BBB-2222", "CCC-3333", "Prata", "Vermelho", "Preto" });
        primitiveTips.setPossibleChar(new char[] {'X','Y','Z'});
        primitiveTips.setObjectGenerationMaxDepth(4);
                
        InputPrimitiveDomain domain = new InputPrimitiveDomain();
        domain.setIntermediateMethodPrimitiveRandomTip(primitiveTips);
        
        //RandomGenerationStrategy rndStrategy = new RandomGenerationStrategy();
        //rndStrategy.setInputDomain(domain);
        //rndStrategy.setPopulationSize(10);
                        
        // Executar rotina de geração de dados de teste      
        Date startRandomElements = new Date();
        
        TestGenerationResult result = null;
        
        try
        {        
            result = null;//TestDataFacade.execute(rndStrategy, jarFile, className, methodUnderTest, 10);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        Date endRandomElements = new Date();
        
        // Imprimir a representação de Tonella
        //PrintTonellaRepresentation(result);
        
        // Imprimir métodos de teste unitários
        //PrintUnitTestMethods(result);
        
        // Imprimir classe de testes unitários
        // PrintUnitTestClass(result);
        
        // Criar arquivo de testes unitários
        CreateUnitTestFile(result.getIndividuals());
        
        // Imprimir representação de Tonella e Teste Unitário
        //PrintTonnelaRepresentationAndUnitTest(result);
        
        double diffRandomElements = (endRandomElements.getTime() - startRandomElements.getTime()) / 1000D;
        
        System.out.println();
        System.out.println("Duração da execução: " + String.valueOf(diffRandomElements));        
    }
    
    private static void PrintTonnelaRepresentationAndUnitTest(Individual[] individuals)
    {
        String[] tonellaStrings = TestDataFacade.getTonellaRepresentation(individuals);  
        String[] unitTestStrings = TestDataFacade.getUnitTestMethods(individuals); 
        
        for (int i = 0; i < individuals.length; i++) {
            System.out.println();
            
            System.out.println(tonellaStrings[i]);
            System.out.println(unitTestStrings[i]);
        }
    }
    
    private static void PrintTonellaRepresentation(Individual[] individuals)
    {
       String[] tonellaStrings = TestDataFacade.getTonellaRepresentation(individuals);  
        
        for (String tString : tonellaStrings) {
            System.out.println(tString);
        } 
    }    
    
    private static void PrintUnitTestMethods(Individual[] individcals)
    {
        String[] unitTestStrings = TestDataFacade.getUnitTestMethods(individcals);  
        
        for (String tString : unitTestStrings) {
            System.out.println(tString);
        }
    }
    
    private static void PrintUnitTestClass(Individual[] individuals)
    {
        String unitTestClass = TestDataFacade.getUnitTestClass(individuals);
        System.out.println(unitTestClass);
    }   
    
    private static void CreateUnitTestFile(Individual[] individuals)
    {
        TestDataFacade.createUnitTestFile("c:\\temp\\teste.java", individuals);
    } 
}
