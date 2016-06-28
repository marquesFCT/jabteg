
package br.jabuti.agdtpoo.control.data;

import br.jabuti.agdtpoo.model.TestDataGenerationPlugIn;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class TestCaseGenerationUploadPlugInData {
    
    private static String FOLDER_PATH = "TestDataGen";
    private static String DOC_PATH = FOLDER_PATH + File.separator + "testData.xml";
    
    public static boolean verifyValidPlugIn(String jarPath)
    {
        // Verifica se é um arquivo .Jar
        if (!jarPath.toLowerCase().endsWith(".jar"))
        {
            return false;
        }
        
        // Cria instância do arquivo em uso
        File jarFile = new File(jarPath);       
        
        // Verifica se o arquivo realmente existe
        if (!jarFile.exists())
        {
            return false;
        }
       
        // Se tudo estiver de acordo, retorna true
        return true;
    }
    
    public static TestDataGenerationPlugIn[] getPlugIns()
    {
        TestDataGenerationPlugIn[] plugIns = null; 
                
        try
        {            
            createFileIfNonExists();
                        
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(DOC_PATH));
        
            doc.getDocumentElement().normalize();
            
            NodeList listOfPlugins = doc.getElementsByTagName("plugIn");
            int plugInsCount = listOfPlugins.getLength();
                  
            plugIns = new TestDataGenerationPlugIn[plugInsCount];
            
            for (int i = 0; i < plugInsCount; i++) {
                
                Node nodePlugIn = listOfPlugins.item(i);
                if (nodePlugIn.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element plugInElement = (Element)nodePlugIn;
                    
                    TestDataGenerationPlugIn plugIn = new TestDataGenerationPlugIn();
                    
                    plugIn.Name = plugInElement.getAttribute("name");
                    plugIn.JarPath = plugInElement.getAttribute("binary");
                    
                    plugIns[i] = plugIn;
                }
            }            
        }
        catch (Exception e)
        {
            return null;
        }
        
        return plugIns;
    }
    
    public static boolean savePlugIn(TestDataGenerationPlugIn newPlugIn)
    {
        try
        {            
            boolean fileCreated = createFileIfNonExists();
                        
            boolean binaryCopied = (fileCreated)? copyBinary(newPlugIn) : false;
            
            boolean plugInSaved = (binaryCopied)? saveDataXml(newPlugIn) : false;
            
            return (fileCreated && binaryCopied && plugInSaved);            
        }
        catch (Exception e)
        {
            return false;
        }
        
    }
    
    private static boolean copyBinary(TestDataGenerationPlugIn newPlugIn)
    {        
        String jarPath = newPlugIn.JarPath;        
        
        File original = new File(jarPath);
        File copy = new File(FOLDER_PATH + File.separator + original.getName());
        
        try
        {            
            if (copy.exists())
                copy.delete();            
            
            InputStream in = new FileInputStream(original);
        
            OutputStream out = new FileOutputStream(copy);
            
            byte[] buf = new byte[1024];
            int len;
            
            while ((len = in.read(buf)) > 0) {
                
                out.write(buf, 0 ,len);
                
            }           
            in.close();
            out.close();
                        
        }
        catch (Exception ex)
        {
            return false;
        }
        
        return true;
    }
    
    private static boolean saveDataXml(TestDataGenerationPlugIn newPlugIn)
    {
        try
        { 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(DOC_PATH);
        
            Element rootNode = (Element)doc.getElementsByTagName("plugIns").item(0);
                        
            Element plugInElement = doc.createElement("plugIn");
            
            String jarName = (new File(newPlugIn.JarPath)).getName();
            
            plugInElement.setAttribute("name", newPlugIn.Name);
            plugInElement.setAttribute("binary", FOLDER_PATH + File.separator + jarName);
                       
            rootNode.appendChild(plugInElement);
                        
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            StreamResult result = new StreamResult(new File(DOC_PATH));
            
            transformer.transform(source, result);
            
            return true;            
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    private static boolean createFileIfNonExists()
    {
        try            
        {            
            File directory = new File(FOLDER_PATH);
            
            if (!directory.exists())
            {
                directory.mkdir();
            }
             
            File xmlFile = new File(DOC_PATH);

            if (!xmlFile.exists())
            {
                if (xmlFile.createNewFile())
                {
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                    Document doc = docBuilder.newDocument();
                                
                    Element rootNode = doc.createElement("plugIns");
            
                    doc.appendChild(rootNode);
                                
                    TransformerFactory transFactory = TransformerFactory.newInstance();
                    Transformer transformer = transFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);

                    StreamResult result = new StreamResult(new File(DOC_PATH));

                    transformer.transform(source, result);

                    return true;
                }
            }
            
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
