/*
 * 
 */
package jdcapp.test_bed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import javafx.geometry.Point2D;
import jdcapp.JDCApp;
import jdcapp.data.ConnectorArrayList;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;
import jdcapp.data.DataManager;

/**
 *
 * @author Noah
 */
public class TestSave {
    
    public static void main(String[] args){
        
        //Hard-code the creation of various classes from the ThreadExample program
        
        DataManager testData = new DataManager(new JDCApp());
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the ThreadExample class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper threadExample = new CustomClassWrapper(200, 200, false);
        threadExample.getData().setClassName("ThreadExample");
        
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
        
        ArrayList<CustomVar> threadExampleVars = (ArrayList<CustomVar>)Arrays.asList(startText,
            pauseText, window, appPane, topPane, startButton, pauseButton, scrollPane, textArea,
            dateThread, dateTask, counterThread, counterTask, work);
        
        threadExample.getData().setVariables(threadExampleVars);
        
        CustomMethod start = new CustomMethod("start", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList("primaryStage : Stage"));
        CustomMethod startWork = new CustomMethod("startWork", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList(""));
        CustomMethod doWork = new CustomMethod("doWork", "boolean", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList(""));
        CustomMethod appendText = new CustomMethod("appendText", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList("textToAppend : String"));
        CustomMethod sleep = new CustomMethod("sleep", "void", false, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList("timeToSleep : int"));
        CustomMethod initLayout = new CustomMethod("initLayout", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, (ArrayList<String>)Arrays.asList(""));
        CustomMethod initHandlers = new CustomMethod("initHandlers", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, (ArrayList<String>)Arrays.asList(""));
        CustomMethod initWindow = new CustomMethod("initWindow", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, (ArrayList<String>)Arrays.asList("initPrimaryStage : Stage"));
        CustomMethod initThreads = new CustomMethod("initThreads", "void", false, false, 
                CustomMethod.PRIVATE_METHOD_ACCESS, (ArrayList<String>)Arrays.asList(""));
        CustomMethod main = new CustomMethod("main", "void", true, false, 
                CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList("args : String[]"));
        
        ArrayList<CustomMethod> threadExampleMethods = (ArrayList<CustomMethod>)Arrays.asList(start,
                startWork, doWork, appendText, sleep, initLayout, initHandlers, initWindow, 
                initThreads, main);
        
        threadExample.getData().setMethods(threadExampleMethods);
        
        ArrayList<String> threadExampleParents = (ArrayList<String>)Arrays.asList("Application");
        threadExample.getData().setParents(threadExampleParents);
        
        //Point2D values are arbitrary and only created for testing purposes
        HashMap<String, ConnectorArrayList> threadExampleConnections = new HashMap<>();
        
        //NOTE: There would be many more connections than just the two listed here, but
        //more are not necessary for testing purposes so they have been excluded
        ConnectorArrayList applicationConnector = (ConnectorArrayList)Arrays.asList(new Point2D(200, 250), new Point2D(200, 400));
        applicationConnector.setConnectorType(ConnectorArrayList.ARROW_CONNECTOR);
        threadExampleConnections.put("Application", applicationConnector);
        ConnectorArrayList counterTaskConnector = (ConnectorArrayList)Arrays.asList(new Point2D(300, 400), new Point2D(300, 500));
        applicationConnector.setConnectorType(ConnectorArrayList.DIAMOND_CONNECTOR);
        threadExampleConnections.put("CounterTask", counterTaskConnector);
        threadExample.setConnections(threadExampleConnections);
        
        //////////////////////////////////////////////////////////////////////
        //  Code for the creation of the CounterTask class
        //////////////////////////////////////////////////////////////////////
        
        CustomClassWrapper counterTaskExample = new CustomClassWrapper(150, 300, false);
        counterTaskExample.getData().setClassName("CounterTask");
        
        CustomVar app = new CustomVar("app", "ThreadExample", false, CustomVar.PRIVATE_VAR_ACCESS);
        CustomVar counter = new CustomVar("counter", "int", false, CustomVar.PRIVATE_VAR_ACCESS);
        
        CustomMethod counterTaskConstructor = new CustomMethod("CounterTask", "", false, false,
            CustomMethod.PUBLIC_METHOD_ACCESS, (ArrayList<String>)Arrays.asList("initApp : ThreadExample"));
        CustomMethod counterTaskCall = new CustomMethod("call", "void", false, false,
            CustomMethod.PROTECTED_METHOD_ACCESS, (ArrayList<String>)Arrays.asList(""));
    }
    
}
