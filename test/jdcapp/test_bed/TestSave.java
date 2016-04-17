/*
 * 
 */
package jdcapp.test_bed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;

/**
 *
 * @author Noah
 */
public class TestSave {
    
    public static void main(String[] args){
        
        //Hard-code the creation of various classes from the ThreadExample program
        
        //Code for the creation of the ThreadExample class
        CustomClassWrapper threadExample = new CustomClassWrapper(200, 200, false);
        threadExample.getData().setClassName("ThreadExample");
        
        CustomVar startText = new CustomVar("START_TEXT", "String", true, "public");
        CustomVar pauseText = new CustomVar("PAUSE_TEXT", "String", true, "public");
        CustomVar window = new CustomVar("window", "Stage", false, "private");
        CustomVar appPane = new CustomVar("appPane", "BorderPane", false, "private");
        CustomVar topPane = new CustomVar("topPane", "FlowPane", false, "private");
        CustomVar startButton = new CustomVar("startButton", "Button", false, "private");
        CustomVar pauseButton = new CustomVar("pauseButton", "Button", false, "private");
        CustomVar scrollPane = new CustomVar("scrollPane", "ScrollPane", false, "private");
        CustomVar textArea = new CustomVar("textArea", "TextArea", false, "private");
        CustomVar dateThread = new CustomVar("dateThread", "Thread", false, "private");
        CustomVar dateTask = new CustomVar("dateTask", "Task", false, "private");
        CustomVar counterThread = new CustomVar("counterThread", "Thread", false, "private");
        CustomVar counterTask = new CustomVar("counterTask", "Task", false, "private");
        CustomVar work = new CustomVar("work", "boolean", false, "private");
        
        ArrayList<CustomVar> threadExampleVars = (ArrayList<CustomVar>)Arrays.asList(startText,
            pauseText, window, appPane, topPane, startButton, pauseButton, scrollPane, textArea,
            dateThread, dateTask, counterThread, counterTask, work);
        
        threadExample.getData().setVariables(threadExampleVars);
        
        CustomMethod start = new CustomMethod("start", "void", false, false, 
                "public", (ArrayList<String>)Arrays.asList("primaryStage : Stage"));
        CustomMethod startWork = new CustomMethod("startWork", "void", false, false, 
                "public", (ArrayList<String>)Arrays.asList(""));
        CustomMethod doWork = new CustomMethod("doWork", "boolean", false, false, 
                "public", (ArrayList<String>)Arrays.asList(""));
        CustomMethod appendText = new CustomMethod("appendText", "void", false, false, 
                "public", (ArrayList<String>)Arrays.asList("textToAppend : String"));
        CustomMethod sleep = new CustomMethod("sleep", "void", false, false, 
                "public", (ArrayList<String>)Arrays.asList("timeToSleep : int"));
        CustomMethod initLayout = new CustomMethod("initLayout", "void", false, false, 
                "private", (ArrayList<String>)Arrays.asList(""));
        CustomMethod initHandlers = new CustomMethod("initHandlers", "void", false, false, 
                "private", (ArrayList<String>)Arrays.asList(""));
        CustomMethod initWindow = new CustomMethod("initWindow", "void", false, false, 
                "private", (ArrayList<String>)Arrays.asList("initPrimaryStage : Stage"));
        CustomMethod initThreads = new CustomMethod("initThreads", "void", false, false, 
                "private", (ArrayList<String>)Arrays.asList(""));
        CustomMethod main = new CustomMethod("main", "void", true, false, 
                "public", (ArrayList<String>)Arrays.asList("args : String[]"));
        
        ArrayList<CustomMethod> threadExampleMethods = (ArrayList<CustomMethod>)Arrays.asList(start,
                startWork, doWork, appendText, sleep, initLayout, initHandlers, initWindow, 
                initThreads, main);
        
        threadExample.getData().setMethods(threadExampleMethods);
    }
    
}
