/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import jdcapp.JDCApp;
import static jdcapp.data.JDCAppState.SELECTING;
import jdcapp.gui.WorkspaceManager;

/**
 *
 * @author Noah
 */
public class DataManager {
    
    //The parent app
    JDCApp app;
    
    //The HashMap containing all the classes, mapped to their class names
    HashMap<String, CustomClass> classes;
    
    //The ArrayList containing all the class names, for use in iterating through the HashMap
    ArrayList<String> classNames;
    
    //The name of the class currently selected
    String selectedClass;
    
    //The state of the application
    JDCAppState state;
    
    public DataManager(JDCApp init){
        app = init;
        selectedClass = null;
        
        //TODO: Finish coding constructor (is this all we need here?)
    }
    
    
    //TODO: FINISH CODING CLASS

    public void reset() {
        //Initialize all variables except app and selectedClass
        classes = new HashMap<>();
        classNames = new ArrayList<>();
        selectedClass = null;
        state = SELECTING;
    }
    
    public void setState(JDCAppState newState){
        state = newState;
    }
    
    public JDCAppState getState(){
        return state;
    }
    
    public HashMap<String, CustomClass> getClasses(){
        return classes;
    }
    
    public ArrayList<String> getClassNames(){
        return classNames;
    }
    
    public void setSelectedClass(String s){
        selectedClass = s;
    }
    
    public String getSelectedClass(){
        return selectedClass;
    }
}
