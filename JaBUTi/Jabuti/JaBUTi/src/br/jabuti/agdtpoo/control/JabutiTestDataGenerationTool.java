
package br.jabuti.agdtpoo.control;

import br.jabuti.graph.CFG;
import br.jabuti.agdtpoo.model.InstrumentClass;
import br.jabuti.agdtpoo.model.InstrumentMethod;
import br.jabuti.agdtpoo.model.RequirementMap;
import br.jabuti.agdtpoo.model.TestCaseMethod;
import br.jabuti.agdtpoo.model.TestCaseMethods;
import br.jabuti.agdtpoo.model.UtilBehaviors;
import br.jabuti.junitexec.JUnitJabutiCore;
import br.jabuti.lookup.Program;
import br.jabuti.probe.ProberInstrum;
import br.jabuti.project.JabutiProject;
import br.jabuti.project.TestSet;
import br.usp.each.saeg.agdtpoo.entity.EnumCriterion;
import br.usp.each.saeg.agdtpoo.entity.GenerationCoverage;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.ITestDataGenerationTool;
import br.usp.each.saeg.agdtpoo.entity.TestDataGenerationToolException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;

public class JabutiTestDataGenerationTool implements ITestDataGenerationTool {
    
    private String _classUnderTest;
    private String _methodUnderTest;
    private String _junitPath;
    private String _jarPath;
    private String _junitTestClassFile;
    private String _dependenciesPath;
    private JabutiProject _project;
    private String _trace;
    private InstrumentMethod instrumentMethodUnderTest = null;        
    private InstrumentClass[] instrumentedClassesInfo;
    private Set<String> ts;
    
    public JabutiTestDataGenerationTool(JabutiProject project, String classUnderTest, String methodUnderTest)
    {
        this._project = project;
        setClassUnderTest(classUnderTest);;
        setMethodUnderTest(methodUnderTest);
        
        this._junitPath = "";
        this._jarPath = "";
        this._junitTestClassFile = "";
        this._dependenciesPath = "";
        
    }
           
    public void setDependenciesPath(String dependenciesPath)
    {
        this._dependenciesPath = dependenciesPath;
    }
    
    public void setJarPath(String jarPath)
    {
        this._jarPath = jarPath;
    }
    
    public String getJUnitTestClassFile()
    {
        return this._junitTestClassFile;
    }
    
    public void setJUnitTestClassFile(String testClassFile)
    {
        this._junitTestClassFile = testClassFile;
    }
    
    public void setJUnitPath(String junitPath)
    {
        this._junitPath = junitPath;
    }
    
    public void setMethodUnderTest(String methodUnderTest)
    {
        this._methodUnderTest = methodUnderTest;
    }
    
    public void setClassUnderTest(String classUnderTest)
    {
        this._classUnderTest = classUnderTest;
    }
    
    private void appendTrace(String message)
    {
        this._trace += "\n" + message;
    }
   
    @Override
    public void compileIndividuals() throws TestDataGenerationToolException
    {
        try
        {
            executeTest();
        }
        catch(Throwable ex)
        {
            throw new TestDataGenerationToolException(ex);
        }
    }
    
    private void executeTest() throws Throwable
    {
        String traceFilePath;
        String instrumentedJarFileName = "";
        String pathJavaC = "";
        String message= "";
        String className = "";
        String projectName = null;
	boolean cutOption = false;
        String jUnitTestClass = this._junitTestClassFile.substring(0, this._junitTestClassFile.lastIndexOf("\\")); 
        ts = new HashSet<String>();
                
        // Filtrando o nome da classe que contem os testes unitarios
        className = this._junitTestClassFile.replace(".java", "");
        className = className.substring(className.lastIndexOf("\\") + 1);
        
        // Caminhos validos que possuem fontes
        String dependenciesPath = this._junitPath + File.pathSeparator + 
                                  jUnitTestClass + File.pathSeparator +
                                  this._dependenciesPath + File.pathSeparator;
                
        // Capturar arquivo de instrumentação
        instrumentedJarFileName = this._project.getInstrumentedJarFileName();   
        
        // Atribuir caminho do arquivo de trace
        traceFilePath = this._project.getTraceFileName();
        
        // Criar arquivo instrumentado
        if (instrumentedClassesInfo == null)
        {
            runInstrumenting(this._jarPath,
                             instrumentedJarFileName,
                             this._classUnderTest,
                             traceFilePath);    
        }
        
        // Get javac path
        pathJavaC = getJavaCPath();     
        
        // Compilar a classe JUnit proposta
        if (!compileJUnitClass(pathJavaC, 
                               this._junitTestClassFile,
                               dependenciesPath))
        {
            throw new TestDataGenerationToolException("Not possible to compile JUnit file. ");
        }
        
 
        // Criacao do arquivo de log
        File logFile = createLogFile();
        
        // Coleta de dados
        HashMap<String, String> hm = new HashMap<String, String>();
        PrintStream ps = System.out;
        try {
                logFile.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(logFile);
                ps = new PrintStream(fos);
                // Aqui são capturados os métodos do arquivo JUnit
                ts = JUnitJabutiCore.runCollecting(dependenciesPath, className, ps).keySet();
                fos.close();
        } catch (Exception e1) {
                e1.printStackTrace(ps);
        } finally {
                ps.close();
        }
        FileInputStream is;
	try {
            is = new FileInputStream(logFile);

            while (is.available() > 0) {
		byte[] b = new byte[is.available()];
                is.read(b);
		message += new String(b);
            }
        } catch (Exception e1) {
            throw new TestDataGenerationToolException(e1);
	}
            
        if (!message.startsWith("JUnit/JaBUTi Integrator: Collector Mode"))
        {
            throw new TestDataGenerationToolException("Error during JUnit compiling. " + message);
        }

        // Apagar o arquivo de trace que já existe para não confundir com os resultados anteriores.
        File traceFile = new File(traceFilePath);
        if (traceFile.exists())
            traceFile.delete();
        
        // Rodando a instrumentação sobre o arquivo de testes unitários   
        ps = System.out;
	try {
            logFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(logFile);
            ps = new PrintStream(fos);

            org.junit.runner.Result x = JUnitJabutiCore.runInstrumentingWithResult(instrumentedJarFileName + File.pathSeparator + 
                                                                                   this._junitPath + File.pathSeparator +
                                                                                   jUnitTestClass + File.pathSeparator,                             
                                                                                   className, this._project.getTraceFileName(), ts, ps);
            fos.close();
	} catch (Exception e1) {
            e1.printStackTrace(ps);
	} finally {
            ps.close();
	}
	is = null;
	try {
            is = new FileInputStream(logFile);

            while (is.available() > 0) {
		byte[] b = new byte[is.available()];
		is.read(b);
		message += new String(b);
            }
	} catch (Exception e1) {
            e1.printStackTrace();
            
            message += e1.getMessage();
	}
                           
        if (!message.startsWith("JUnit/JaBUTi Integrator: Collector Mode"))
        {
            throw new TestDataGenerationToolException("Error during instrumenting. " + message);
        }
        
        // Captura do arquivo de trace
        String traceFileName = this._project.getTraceFileName();        
	
        traceFile = new File(traceFileName);
       
        if (!traceFile.exists()) {
            throw new TestDataGenerationToolException("Trace file not exist.");
        }
                               
        instrumentMethodUnderTest = getMethodUnderTest(this._classUnderTest, this._methodUnderTest);                  
    }
    
    @Override //TODO: Preciso ajustar este método...
    public GenerationRequirement[] getRequirements(EnumCriterion criterion) throws TestDataGenerationToolException
    {   
        if (instrumentMethodUnderTest == null)
            compileIndividuals();
        
        GenerationRequirement[] requirements = new GenerationRequirement[0];
        
        if (criterion == EnumCriterion.AllNodesEi)
            requirements = instrumentMethodUnderTest.getRequirements(criterion);
        else
        {
            RequirementMap reqMap = RequirementMap.getInstance();
              
            if (reqMap.getMethodsStructure().length == 0)
                TestSet.executePartialTestCoverage(this._project, this._classUnderTest, this._methodUnderTest, String.valueOf(instrumentMethodUnderTest.getId()));
            
            if (reqMap.getMethodsStructure().length > 0)
                requirements = (reqMap.getMethodsStructure()[0]).getRequirements(criterion);            
        }
                    
        return requirements;
    }
    
    @Override
    public GenerationCoverage getCoverage(EnumCriterion criterion)
    {         
        GenerationCoverage testMethodCoverage = null;
        TestCaseMethods testCases = TestSet.executeTestCoverage(this._project, this._classUnderTest, this._methodUnderTest, String.valueOf(instrumentMethodUnderTest.getId()));
         
        for (TestCaseMethod tcm : testCases) {       
            testMethodCoverage = tcm.getGenerationCoverage(criterion);
        }
        
        return testMethodCoverage;
    } 
    
    @Override
    public GenerationCoverage[] getCoverages(EnumCriterion criterion)
    {         
        GenerationCoverage[] testMethodCoverage = null;
        TestCaseMethods testCases = TestSet.executeTestCoverage(this._project, this._classUnderTest, this._methodUnderTest, String.valueOf(instrumentMethodUnderTest.getId()));
         
        testMethodCoverage = new GenerationCoverage[testCases.size()];
        int index = 0;
        for (TestCaseMethod tcm : testCases) {       
            testMethodCoverage[index] = tcm.getGenerationCoverage(criterion);
            index++;
        }
        
        return testMethodCoverage;
    }    
    
    @Override
    public String getExecutionTrace()
    {
        return this._trace;
    }
    
    /**************************************************************************/
    
    public void runInstrumenting(String jarFile, String outName, String className, String traceFile) throws Throwable
    {
        File theFile = new File(jarFile);
        
        if (!theFile.isFile()) // verifica se existe
	{
            System.out.println("File " + theFile.getName()
                               + " not found");
            System.exit(0);
	}
        
        String toAvoid = null;
        
        Program program = new Program(new ZipFile(jarFile), true, toAvoid);
        
        String[] classes = program.getClasses();
        HashSet hs = new HashSet();
        
        for (int i = 0; i < classes.length; i++) {
		hs.add(classes[i]);
	}
       
        ProberInstrum.instrumentProgram(program, hs, CFG.NO_CALL_NODE, className, outName, false, traceFile);        
        
        instrumentedClassesInfo = ProberInstrum.getInstrumentedClassesInfo();        
    }
    
    private String getJavaCPath()
    {
        String JAVAC = "";
        String s = System.getProperty("java.home");
	if (s.endsWith("jre"))
            s = s.substring(0, s.length() - 3);

	if (!s.endsWith(File.separator))
            s += File.separator;

	JAVAC = "javac";
	JAVAC = s + "bin" + File.separator + JAVAC;
               
        if (!JAVAC.toLowerCase().endsWith(".exe"))
            JAVAC+= ".exe";
            
        return JAVAC;
    }
    
    // Compilar arquivo JUnit
    public boolean compileJUnitClass(String javacPath, String classPath, String sourcePath)
    {        
        boolean executeWithSucess = true;
        String mensagem = "";
        ProcessBuilder pb = new ProcessBuilder(javacPath, 
                                               "-cp", sourcePath,
                                               classPath);
	
        pb.redirectErrorStream(true);
	Process pr = null;
        
        try
        {
            pr = pb.start();

            InputStream stderr = pr.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
        
            while ((line = br.readLine()) != null) {
                mensagem += line + "\n";
                executeWithSucess = false;
            }
        }
        catch (IOException e) {
            
            mensagem = "Error executin compileJUnitClass: " + e.getMessage();
            executeWithSucess = false;
        }
        
        appendTrace(mensagem);
        
        return executeWithSucess;
    }    
        
    private File createLogFile()
    {
        File temp = null;
        
        try {
                temp = File.createTempFile("log-junit-jabuti", ".log");
                 appendTrace("Log file created: " + temp.getPath());
        } catch (IOException e) {
                appendTrace("Error creating log file: " + e.getMessage());
        }
        
        return temp;
    }    
            
    public InstrumentMethod getMethodUnderTest(String classUnderTest, String methodUnderTest)
    {
        if (this.instrumentedClassesInfo == null)
            return null;
        
        InstrumentMethod returnValue = null;
        
        for (InstrumentClass instrumentClass : instrumentedClassesInfo) {
            if (!instrumentClass.getName().equals(classUnderTest))
                continue;
                
            for (InstrumentMethod instrumentMethod : instrumentClass.getMethods()) {
                String signature = instrumentMethod.getName();
                signature = UtilBehaviors.getMethodSignature(signature);
                
                if (!signature.equals(methodUnderTest))
                    continue;
                
                returnValue = instrumentMethod;
                break;
            }
        }
        
        if (returnValue != null)
        {
            // Varrer os requisitos a fim de encontrar os possíveis requisitos pais de
            // cada requisito de teste descoberto durante a execução.
            for (GenerationRequirement requirement : returnValue.getRequirements()) {                
                String currentId = requirement.getIdRequirement();
                
                for (GenerationRequirement possibleParent : returnValue.getRequirements()) {
                    if (possibleParent.getIdRequirement().equals(currentId))
                        continue;
                    
                    for (String nextRequirement : possibleParent.getNextRequirements()) {
                        if (nextRequirement.equals(currentId))
                        {
                            requirement.addParentRequirement(possibleParent);
                            break;
                        }
                    }
                }                
            }           
        }
        
        return returnValue;
    }
}
