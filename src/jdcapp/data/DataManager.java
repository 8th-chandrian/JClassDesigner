/*
 * 
 */
package jdcapp.data;

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
    
    //The class currently selected
    CustomClass selectedClass;
    
    public DataManager(JDCApp init){
        app = init;
        classes = new HashMap<String, CustomClass>();
        selectedClass = null;
        
        //TODO: Finish coding constructor (is this all we need here?)
        
    }
    
    
    //TODO: FINISH CODING CLASS
}
