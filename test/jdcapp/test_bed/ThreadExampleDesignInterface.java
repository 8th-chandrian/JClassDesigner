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
public class ThreadExampleDesignInterface {
    
    private DataManager testData;
    private static final String filePath = "./work/DesignSaveTestInterface.";
    
    public ThreadExampleDesignInterface(){
        
        //Hard-code the creation of various classes from the ThreadExample program
        
        testData = new DataManager(new JDCApp());
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the ThreadExample class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper threadExample = new CustomClassWrapper(200, 200);
        threadExample.getData().setClassName("ThreadExample");
        threadExample.getData().setPackageName("example");
        
        CustomVar startText = new CustomVar("START_TEXT", "String", true, CustomVar.PUBLIC_VAR_ACCESS);
        CustomVar pauseText = new CustomVar("PAUSE_TEXT", "String", true, CustomVar.PUBLIC_VAR_ACCESS);
        CustomVar window = new CustomVar("window", "Stage", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar appPane = new CustomVar("appPane", "BorderPane", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar topPane = new CustomVar("topPane", "FlowPane", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar startButton = new CustomVar("startButton", "Button", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar pauseButton = new CustomVar("pauseButton", "Button", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar scrollPane = new CustomVar("scrollPane", "ScrollPane", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar textArea = new CustomVar("textArea", "TextArea", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar dateThread = new CustomVar("dateThread", "Thread", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar dateTask = new CustomVar("dateTask", "Task", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar counterThread = new CustomVar("counterThread", "Thread", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar counterTask = new CustomVar("counterTask", "Task", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar work = new CustomVar("work", "boolean", false, CustomVar.PRIVATE_VAR_ACCESS);
        
        ArrayList<CustomVar> threadExampleVars = new ArrayList<CustomVar>(Arrays.asList(startText,
            pauseText, window, appPane, topPane, startButton, pauseButton, scrollPane, textArea,
            dateThread, dateTask, counterThread, counterTask, work));
        
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
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the CounterTask class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper counterTaskExample = new CustomClassWrapper(150, 300);
        counterTaskExample.getData().setClassName("CounterTask");
        counterTaskExample.getData().setPackageName("example.task");
        
        CustomVar app = new CustomVar("app", "ThreadExample", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar counter = new CustomVar("counter", "int", false, CustomVar.PRIVATE_VAR_ACCESS);
        ArrayList<CustomVar> counterTaskExampleVars = new ArrayList<CustomVar>( Arrays.asList(app, counter));
        
        counterTaskExample.getData().setVariables(counterTaskExampleVars);
        
        CustomMethod counterTaskConstructor = new CustomMethod("CounterTask", "", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("initApp : ThreadExample")));
        CustomMethod counterTaskCall = new CustomMethod("call", "Void", false, false,
            CustomMethod.PROTECTED_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        ArrayList<CustomMethod> counterTaskExampleMethods = new ArrayList<CustomMethod>( Arrays.asList(counterTaskConstructor,
                counterTaskCall));
        counterTaskExample.getData().setMethods(counterTaskExampleMethods);
        
        ArrayList<String> counterTaskExampleImplementedClasses = new ArrayList<>(Arrays.asList("CounterTaskInterface"));
        counterTaskExample.getData().setImplementedClasses(counterTaskExampleImplementedClasses);
        counterTaskExample.getData().setExtendedClass("Task<Void>");
        
        testData.getClasses().add(counterTaskExample);
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the DateTask class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper dateTaskExample = new CustomClassWrapper(290, 650);
        dateTaskExample.getData().setClassName("DateTask");
        dateTaskExample.getData().setPackageName("example.task");
        
        CustomVar appDate = new CustomVar("app", "ThreadExample", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar nowDate = new CustomVar("now", "Date", false, CustomVar.PRIVATE_VAR_ACCESS);
        ArrayList<CustomVar> dateTaskExampleVars = new ArrayList<CustomVar>( Arrays.asList(appDate, nowDate));
        
        dateTaskExample.getData().setVariables(dateTaskExampleVars);
        
        CustomMethod dateTaskConstructor = new CustomMethod("DateTask", "", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("initApp : ThreadExample")));
        CustomMethod dateTaskCall = new CustomMethod("call", "Void", false, false,
            CustomMethod.PROTECTED_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        ArrayList<CustomMethod> dateTaskExampleMethods = new ArrayList<CustomMethod>( Arrays.asList(dateTaskConstructor,
                dateTaskCall));
        dateTaskExample.getData().setMethods(dateTaskExampleMethods);
        
        ArrayList<String> dateTaskExampleImplementedClasses = new ArrayList<>();
        dateTaskExample.getData().setImplementedClasses(dateTaskExampleImplementedClasses);
        dateTaskExample.getData().setExtendedClass("Task<Void>");
        
        testData.getClasses().add(dateTaskExample);
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the PauseHandler class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper pauseHandlerExample = new CustomClassWrapper(580, 220);
        pauseHandlerExample.getData().setClassName("PauseHandler");
        pauseHandlerExample.getData().setPackageName("example.handler");
        
        CustomVar appPause = new CustomVar("app", "ThreadExample", false, CustomVar.PRIVATE_VAR_ACCESS);
        ArrayList<CustomVar> pauseHandlerExampleVars = new ArrayList<CustomVar>( Arrays.asList(appPause));
        
        pauseHandlerExample.getData().setVariables(pauseHandlerExampleVars);
        
        CustomMethod pauseHandlerConstructor = new CustomMethod("PauseHandler", "", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("initApp : ThreadExample")));
        CustomMethod pauseHandlerHandle = new CustomMethod("handle", "void", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("event : Event")));
        ArrayList<CustomMethod> pauseHandlerExampleMethods = new ArrayList<CustomMethod>( Arrays.asList(pauseHandlerConstructor,
                pauseHandlerHandle));
        
        pauseHandlerExample.getData().setMethods(pauseHandlerExampleMethods);
        
        ArrayList<String> pauseHandlerExampleImplementedClasses = new ArrayList<String>(Arrays.asList("EventHandler"));
        pauseHandlerExample.getData().setImplementedClasses(pauseHandlerExampleImplementedClasses);
        pauseHandlerExample.getData().setExtendedClass("");
        
        testData.getClasses().add(pauseHandlerExample);
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the StartHandler class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper startHandlerExample = new CustomClassWrapper(580, 220);
        startHandlerExample.getData().setClassName("StartHandler");
        startHandlerExample.getData().setPackageName("example.handler");
        
        CustomVar appStart = new CustomVar("app", "ThreadExample", false, CustomVar.PRIVATE_VAR_ACCESS);
        ArrayList<CustomVar> startHandlerExampleVars = new ArrayList<CustomVar>( Arrays.asList(appStart));
        
        startHandlerExample.getData().setVariables(startHandlerExampleVars);
        
        CustomMethod startHandlerConstructor = new CustomMethod("StartHandler", "", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("initApp : ThreadExample")));
        CustomMethod startHandlerHandle = new CustomMethod("handle", "void", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("event : Event")));
        ArrayList<CustomMethod> startHandlerExampleMethods = new ArrayList<CustomMethod>( Arrays.asList(startHandlerConstructor,
                startHandlerHandle));
        startHandlerExample.getData().setMethods(startHandlerExampleMethods);
        
        ArrayList<String> startHandlerExampleImplementedClasses = new ArrayList<String>( Arrays.asList("EventHandler"));
        startHandlerExample.getData().setImplementedClasses(startHandlerExampleImplementedClasses);
        startHandlerExample.getData().setExtendedClass("");
        
        testData.getClasses().add(startHandlerExample);
        
        //////////////////////////////////////////////////////////////////////////////////////////////////
        //  Code for the creation of the CounterTaskInterface interface
        //////////////////////////////////////////////////////////////////////////////////////////////////

        CustomClassWrapper counterTaskExampleInterface = new CustomClassWrapper(150, 300);
        counterTaskExampleInterface.getData().setClassName("CounterTaskInterface");
        counterTaskExampleInterface.getData().setPackageName("example.counter");
        counterTaskExampleInterface.getData().setInterfaceValue(true);

        CustomVar appInterface = new CustomVar("APP", "ThreadExample", true, CustomVar.PUBLIC_VAR_ACCESS);
        CustomVar counterInterface = new CustomVar("COUNTER", "int", true, CustomVar.PUBLIC_VAR_ACCESS);
        ArrayList<CustomVar> counterTaskExampleVarsInterface = new ArrayList<CustomVar>( Arrays.asList(appInterface, counterInterface));

        counterTaskExampleInterface.getData().setVariables(counterTaskExampleVarsInterface);

        CustomMethod counterTaskCallInterface = new CustomMethod("callInterface", "void", false, true,
            CustomMethod.PUBLIC_METHOD_ACCESS, new ArrayList<String>(Arrays.asList("")));
        ArrayList<CustomMethod> counterTaskExampleMethodsInterface = new ArrayList<CustomMethod>( Arrays.asList(counterTaskCallInterface));

        counterTaskExampleInterface.getData().setMethods(counterTaskExampleMethodsInterface);

        ArrayList<String> counterTaskExampleImplementedClassesInterface = new ArrayList<String>(Arrays.asList(""));
        counterTaskExampleInterface.getData().setImplementedClasses(counterTaskExampleImplementedClassesInterface);
        counterTaskExampleInterface.getData().setExtendedClass("");

        testData.getClasses().add(counterTaskExampleInterface);
    } 
    
    public DataManager getData(){
        return testData;
    }
    
    public String getFilePath(){
        return filePath;
    }
}
