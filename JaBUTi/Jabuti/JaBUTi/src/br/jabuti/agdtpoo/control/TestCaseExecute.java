/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.jabuti.agdtpoo.control;

import br.jabuti.graph.CFG;
import br.jabuti.gui.JabutiGUI;
import br.jabuti.agdtpoo.model.InstrumentClass;
import br.jabuti.agdtpoo.model.InstrumentMethod;
import br.jabuti.agdtpoo.model.TestCaseMethod;
import br.jabuti.agdtpoo.model.TestCaseMethods;
import br.jabuti.agdtpoo.model.UtilBehaviors;
import br.jabuti.junitexec.JUnitJabutiCore;
import br.jabuti.lookup.Program;
import br.jabuti.probe.ProberInstrum;
import java.io.File;
import br.jabuti.project.JabutiProject;
import br.jabuti.project.TestSet;
import br.jabuti.util.ToolConstants;
import br.usp.each.saeg.agdtpoo.entity.GenerationRequirement;
import br.usp.each.saeg.agdtpoo.entity.Individual;
import br.usp.each.saeg.agdtpoo.generationAlgorithms.IStrategyPrimitiveTips;
import br.usp.each.saeg.agdtpoo.TestDataFacade;
import br.usp.each.saeg.agdtpoo.entity.PrimitiveRandomTip;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

public class TestCaseExecute {
    
    private InstrumentClass[] instrumentedClassesInfo;
    private Set<String> ts;
    private String _trace;
    
    public String getTrace()
    {
        return this._trace;
    }
    
    private void appendTrace(String trace)
    {
        _trace += "\n" + trace;
    }
    
    // Código retirado da JabutiGUI
    private void createInstrumentedJarFile(JabutiProject prj)
    {
        String fileName = prj.getInstrumentedJarFileName();

        boolean noMain = false;
        int op = JOptionPane
        .showConfirmDialog(
                        JabutiGUI.mainWindow(),
                        "Has the base class \"" + prj.getBaseClass() + "\" a main method?",
                        "Warning", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.NO_OPTION) {
                noMain = true;
        }

        try {
                File jarFile = new File(fileName);
                if (jarFile.exists()) {
                        // default icon, custom title
                        op = JOptionPane.showConfirmDialog(null,
                                        "Instrumented JAR File " + fileName
                                                        + " already exists.\n" + "Overwrite it?",
                                        "Question", JOptionPane.YES_NO_OPTION);

                        if (op == JOptionPane.YES_OPTION) {
                                boolean deleted = jarFile.delete();
                                if (deleted)
                                        System.out.println("JAR file " + fileName
                                                        + " deleted successfuly!!!");
                                else
                                        System.out.println("JAR file " + fileName
                                                        + " NOT deleted");
                        } else
                                return;
                }
        } catch (Exception e) {
                ToolConstants.reportException(e, ToolConstants.STDERR);
        }

        String[] args = { "-p", prj.getProjectFileName(), "-o", fileName,
                        prj.getBaseClass(), (noMain)? "-nomain": "" };

        for (int i = 0; i < args.length; i++) {
                System.out.println("Arg " + i + " " + args[i]);
        }

        if (br.jabuti.probe.ProberInstrum.instrumentProject(prj, prj
                        .getBaseClass(), fileName, noMain)) {
                JOptionPane.showMessageDialog(null, "File " + fileName
                                + " created successfully.", "Information",
                                JOptionPane.INFORMATION_MESSAGE);
        } else {
                JOptionPane.showMessageDialog(null, "File " + fileName
                                + " not created successfully.", "Error",
                                JOptionPane.ERROR_MESSAGE);
        }
    }
        
    // Verifica se o arquivo de instrumentação existe ou não.
    private void createInstrumentedJarFile(JabutiProject jbtProject, String filePath)
    {
        File appF = new File(filePath);
        if (appF.exists()) {			
            appF.delete();
        }
        
        createInstrumentedJarFile(jbtProject);	
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

	//ProberInstrum.instrumentProgram(program, hs, CFG.NO_CALL_NODE, outName);        
        ProberInstrum.instrumentProgram(program, hs, CFG.NO_CALL_NODE, className, outName, false, traceFile);        
        
        instrumentedClassesInfo = ProberInstrum.getInstrumentedClassesInfo();
    }
    

    
    public InstrumentMethod getMethodUnderTest(String classUnderTest, String methodUnderTest)
    {
        if (this.instrumentedClassesInfo == null)
            return null;
        
        for (InstrumentClass instrumentClass : instrumentedClassesInfo) {
            if (!instrumentClass.getName().equals(classUnderTest))
                continue;
                
            for (InstrumentMethod instrumentMethod : instrumentClass.getMethods()) {
                String signature = instrumentMethod.getName();
                signature = UtilBehaviors.getMethodSignature(signature);
                
                if (!signature.equals(methodUnderTest))
                    continue;
                
                return instrumentMethod;
            }
        }
        
        return null;
    }
    
    public void ExecuteTest(JabutiProject jbtProject, String classUnderTest, String methodUnderTest, String jUnitPath, String jUnitTestClassJava, String jarPath, String caminhoDependecias) throws Throwable
    {        
        String traceFilePath;
        String instrumentedJarFileName = "";
        String pathJavaC = "";
        String message= "";
        String className = "";
        String projectName = null;
	boolean cutOption = false;
        String jUnitTestClass = jUnitTestClassJava.substring(0, jUnitTestClassJava.lastIndexOf("\\")); 
        ts = new HashSet<String>();
        
        // Filtrando o nome da classe que contem os testes unitarios
        className = jUnitTestClassJava.replace(".java", "");
        className = className.substring(className.lastIndexOf("\\") + 1);
                
        // Caminhos validos que possuem fontes
        String dependenciesPath = //caminhoFontes + File.pathSeparator +
			   jUnitPath + File.pathSeparator + 
                           jUnitTestClass + File.pathSeparator +
                           caminhoDependecias + File.pathSeparator;
        
        // Capturar arquivo de instrumentação
        instrumentedJarFileName = jbtProject.getInstrumentedJarFileName();   
        
        // Atribuir caminho do arquivo de trace
        traceFilePath = jbtProject.getTraceFileName();
        
        // Criar arquivo instrumentado
        runInstrumenting(jarPath,
                         instrumentedJarFileName,
                         classUnderTest,
                         traceFilePath);
        
        // Get javac path
        pathJavaC = getJavaCPath();        
        // Compilar a classe JUnit proposta
        if (!compileJUnitClass(pathJavaC, 
                              jUnitTestClassJava,
                              dependenciesPath))
        {
            message += "Não foi possível compilar o arquino formato JUnit."; 
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
            e1.printStackTrace();
            
            message += e1.getMessage();
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
                                                                                   jUnitPath + File.pathSeparator +
                                                                                   jUnitTestClass + File.pathSeparator,                             
                                                                                   className, jbtProject.getTraceFileName(), ts, ps);
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
         
        // Captura do arquivo de trace
        String traceFileName = jbtProject.getTraceFileName();        
	
        traceFile = new File(traceFileName);
       
        if (!traceFile.exists()) {
            message += "O arquivo de trace não existe.";
            return;
        }
               
        InstrumentMethod instrumentMethodUnderTest = null;
        boolean traceLoaded = false;
        
        instrumentMethodUnderTest = getMethodUnderTest(classUnderTest, methodUnderTest);
        
        TestCaseMethods testCases = TestSet.executeTestCoverage(jbtProject, classUnderTest, methodUnderTest, String.valueOf(instrumentMethodUnderTest.getId()));
                    
        appendTrace("Method Coverage - " + instrumentMethodUnderTest.getNodesConcat());                 
                
        ArrayList<String> coveredNodes = new ArrayList<String>();
        if (testCases != null)
        {
            for (TestCaseMethod tcm : testCases) {                
                String testMethodCoverage = tcm.getGenerationCoverage().getExecutionPath();
                
                if (coveredNodes.size() != instrumentMethodUnderTest.getRequirements().length)
                {
                    for (GenerationRequirement targetNode : instrumentMethodUnderTest.getRequirements()) {
                        if (testMethodCoverage.contains("-" + targetNode + "-"))
                        {
                            if (!coveredNodes.contains(targetNode.getIdRequirement()))
                            {
                                coveredNodes.add(targetNode.getIdRequirement());
                            }
                        }
                    }
                }
                
                appendTrace("Method - " + tcm.GetName() + " - " + tcm.GetLabel() + " - " + testMethodCoverage);                 
            }
        }
        
        appendTrace("\n\nNós não cobertos:");
        for (GenerationRequirement targetNode : instrumentMethodUnderTest.getRequirements()) {
            if (!coveredNodes.contains(targetNode.getIdRequirement()))
            {
                appendTrace(targetNode.getIdRequirement());
            }
        }
        
        
    }
}
