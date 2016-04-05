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

    //The ArrayList containing all class objects created
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
    
    /**
     * Gets the class clicked on (if it exists) and selects that class.
     * @param x
     *      The x coordinate of the mouse click.
     * @param y
     *      The y coordinate of the mouse click.
     * @return 
     *      The class clicked on, or null if no class contains the coordinates
     *      of the mouse click.
     */
    public CustomClassWrapper selectTopClass(double x, double y){
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        CustomClassWrapper c = getTopClass(x, y);
        
        //Don't need to do anything if no class was clicked on
        if(c == null)
            return null;
        
        //Don't need to do anything if class clicked on is selected already
        if(c == selectedClass)
            return selectedClass;
        
        //If class clicked on is not selected and another class is, unhighlight currently-selected class
        if(selectedClass != null){
            workspaceManager.unhighlightSelectedClass();
        }
        selectedClass = c;
        
        //Load in data from the selected class to the component toolbar and highlight it in the workspace
        workspaceManager.loadSelectedClassData();
        workspaceManager.highlightSelectedClass();
        return c;
    }
    
    /**
     * Helper method for selectTopClass, gets the top class which contains the 
     * coordinates of the mouse click.
     * @param x
     *      The x coordinate of the mouse click.
     * @param y
     *      The y coordinate of the mouse click.
     * @return 
     *      The class clicked on, or null if no class contains the coordinates
     *      of the mouse click.
     */
    public CustomClassWrapper getTopClass(double x, double y){
        for(int i = classes.size() - 1; i >= 0; i--){
            CustomClassWrapper c = classes.get(i);
            if(c.getOutlineRectangle().contains(x, y))
                return c;
        }
        return null;
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
