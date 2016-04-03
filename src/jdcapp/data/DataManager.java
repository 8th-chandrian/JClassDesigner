/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import jdcapp.JDCApp;

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
    
    //The class currently selected
    CustomClass selectedClass;
    
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
    }
}
