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
    
    //The ArrayList containing all the class names, for use in iterating through the HashMap
    ArrayList<CustomClassWrapper> classes;
    
    //The class currently selected
    CustomClassWrapper selectedClass;
    
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
        classes = new ArrayList<>();
        selectedClass = null;
        state = SELECTING;
    }
    
    public void setState(JDCAppState newState){
        state = newState;
    }
    
    public JDCAppState getState(){
        return state;
    }
    
    public ArrayList<CustomClassWrapper> getClasses(){
        return classes;
    }
    
    public void setSelectedClass(CustomClassWrapper c){
        selectedClass = c;
    }
    
    public CustomClassWrapper getSelectedClass(){
        return selectedClass;
    }
}
