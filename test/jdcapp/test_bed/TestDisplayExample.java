/*
 * 
 */
package jdcapp.test_bed;

import java.util.ArrayList;
import java.util.Arrays;
import jdcapp.JDCApp;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;
import jdcapp.data.DataManager;

/**
 *
 * @author Noah
 */
public class TestDisplayExample {
    
    private DataManager testData;
    private static final String filePath = "./work/TestDisplayExample.";
    
    public TestDisplayExample(){
        
        //Hard-code the creation of various classes from the ThreadExample program
        
        testData = new DataManager(new JDCApp());
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the ThreadExample class
        //////////////////////////////////////////////////////////////////////
        
        
        CustomClassWrapper threadExample = new CustomClassWrapper(200, 200);
        threadExample.getData().setClassName("6 Variables");
        threadExample.getData().setPackageName("example");
        
        CustomVar startText = new CustomVar("START_TEXT", "String", true, CustomVar.PUBLIC_VAR_ACCESS);
        CustomVar pauseText = new CustomVar("PAUSE_TEXT", "String", true, CustomVar.PUBLIC_VAR_ACCESS);
        CustomVar window = new CustomVar("window", "Stage", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar appPane = new CustomVar("appPane", "BorderPane", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar topPane = new CustomVar("topPane", "FlowPane", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar startButton = new CustomVar("startButton", "Button", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar pauseButton = new CustomVar("pauseButton", "Button", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar scrollPane = new CustomVar("scrollPane", "ScrollPane", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar textArea = new CustomVar("textArea", "TextArea", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar dateThread = new CustomVar("dateThread", "Thread", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar dateTask = new CustomVar("dateTask", "Task", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar counterThread = new CustomVar("counterThread", "Thread", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar counterTask = new CustomVar("counterTask", "Task", false, CustomVar.PRIVATE_VAR_ACCESS);
//        CustomVar work = new CustomVar("work", "boolean", false, CustomVar.PRIVATE_VAR_ACCESS);
        
        ArrayList<CustomVar> threadExampleVars = new ArrayList<CustomVar>(Arrays.asList(startText,
            pauseText, window, appPane, topPane, startButton));
        
        threadExample.getData().setVariables(threadExampleVars);
        
        CustomMethod start = new CustomMethod("start", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("primaryStage : Stage")));
        CustomMethod startWork = new CustomMethod("startWork", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        CustomMethod doWork = new CustomMethod("doWork", "boolean", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        CustomMethod appendText = new CustomMethod("appendText", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("textToAppend : String")));
        CustomMethod sleep = new CustomMethod("sleep", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("timeToSleep : int")));
        CustomMethod initLayout = new CustomMethod("initLayout", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        CustomMethod initHandlers = new CustomMethod("initHandlers", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        CustomMethod initWindow = new CustomMethod("initWindow", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("initPrimaryStage : Stage")));
        CustomMethod initThreads = new CustomMethod("initThreads", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        CustomMethod main = new CustomMethod("main", "void", true, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("args : String[]")));
        
        ArrayList<CustomMethod> threadExampleMethods = new ArrayList<CustomMethod>(Arrays.asList(start,
                startWork, doWork, appendText, sleep, initLayout, initHandlers, initWindow, 
                initThreads, main));
        threadExample.getData().setMethods(threadExampleMethods);
        
        String threadExampleExtendedClass = "Application";
        ArrayList<String> threadExampleImplementedClasses = new ArrayList<>();
        threadExample.getData().setExtendedClass(threadExampleExtendedClass);
        threadExample.getData().setImplementedClasses(threadExampleImplementedClasses);
        
        testData.getClasses().add(threadExample);
        
      
    } 
    
    public DataManager getData(){
        return testData;
    }
    
    public String getFilePath(){
        return filePath;
    }
}
