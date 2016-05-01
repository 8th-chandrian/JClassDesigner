/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
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
    ArrayList<CustomBox> classes;
    
    //The ArrayList containing temporary parents that have been added by the user.
    //These can be selected as implemented or extended classes. If they are, new
    //CustomImport objects are created and added to the classes array, and they are
    //removed from tempParents. Note that tempParents is deleted every time the 
    //program is closed.
    ArrayList<String> tempParents;
    
    //The class currently selected
    CustomBox selectedClass;
    
    //The state of the application
    JDCAppState state;
    
    //Whether or not the code could currently be exported
    boolean isExportable;
    
    public DataManager(JDCApp init){
        app = init;
        selectedClass = null;
        classes = new ArrayList<>();
        tempParents = new ArrayList<>();
        isExportable = false;
        //TODO: Finish coding constructor (is this all we need here?)
    }
    
    
    //TODO: FINISH CODING CLASS

    public void reset() {
        //Initialize all variables except app and selectedClass
        classes = new ArrayList<>();
        tempParents = new ArrayList<>();
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
    public CustomBox selectTopClass(double x, double y){
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        CustomBox c = getTopClass(x, y);
        
        //Don't need to do anything if no class was clicked on
        if(c == null)
            return null;
        
        //Don't need to do anything if class clicked on is selected already
        if(c == selectedClass)
            return selectedClass;
        
        //If class clicked on is not selected and another class is, unhighlight currently-selected class
        if(selectedClass != null){
            workspaceManager.unhighlightSelectedClass();
            workspaceManager.wipeSelectedClassData();
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
    public CustomBox getTopClass(double x, double y){
        for(int i = classes.size() - 1; i >= 0; i--){
            CustomBox c = classes.get(i);
            if(c.getOutlineRectangle().contains(x, y))
                return c;
        }
        return null;
    }
    
    /**
     * Checks classes to see if any CustomBox has the same name as nameToCheck
     * @param nameToCheck
     * @return 
     */
    public boolean hasName(String nameToCheck){
        for(CustomBox c : classes){
            if(c instanceof CustomClassWrapper){
                if(((CustomClassWrapper)c).getData().getClassName().equals(nameToCheck))
                    return true;
            }
            else{
                if(((CustomImport)c).getImportName().equals(nameToCheck))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * Checks whether or not all combinations of names and packages in the design
     * are unique. If they are, sets isExportable to true, otherwise sets it to
     * false. Updates the codeExportButton in the file toolbar to be enabled or 
     * disabled accordingly.
     */
    public void checkCombinations(){
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        int tally = 0;
        for(CustomBox cb : classes){
            if(cb instanceof CustomClassWrapper){
                CustomClassWrapper c = (CustomClassWrapper) cb;
                if(isCombinationUnique(c.getData().getClassName(), c.getData().getPackageName())){
                    c.getNameText().setFill(Color.BLACK);
                    tally++;
                }
                else
                    c.getNameText().setFill(Color.RED);
            }
            else if(cb instanceof CustomImport){
                if(((CustomImport)cb).getPackageName().equals(CustomImport.DEFAULT_PACKAGE_NAME))
                    ((CustomImport)cb).getNameText().setFill(Color.RED);
                else{
                    ((CustomImport)cb).getNameText().setFill(Color.BLACK);
                    tally++;
                }
            }
        }
        if(tally == classes.size())
            isExportable = true;
        else
            isExportable = false;
        workspaceManager.updateCodeExportButton(isExportable);
    }
    
    /**
     * Checks if one combination of class and package is unique in among all the classes and packages
     * @param className
     * @param packageName
     * @return 
     *      True if the combination is unique, false otherwise
     */
    public boolean isCombinationUnique(String className, String packageName){
        int numOccurances = 0;
        if(className.equals(CustomClass.DEFAULT_CLASS_NAME) || packageName.equals(CustomClass.DEFAULT_PACKAGE_NAME))
                return false;
        for(CustomBox cb : classes){
            if(cb instanceof CustomClassWrapper){
                CustomClassWrapper c = (CustomClassWrapper) cb;
                if(c.getData().getClassName().equals(className) && c.getData().getPackageName().equals(packageName))
                    numOccurances++;
            }
        }
        if(numOccurances > 1)
            return false;
        else
            return true;
    }
    
    //TODO: Make more classes here to check whether or not variables/methods are not default
    //Use isExportable and updateCodeExportButton() to set text to red/black and enable/disable button.
    
        
    public void setState(JDCAppState newState){
        state = newState;
    }
    
    public JDCAppState getState(){
        return state;
    }
    
    public ArrayList<CustomBox> getClasses(){
        return classes;
    }
    
    public ArrayList<String> getTempParents(){
        return tempParents;
    }
    
    public void setSelectedClass(CustomClassWrapper c){
        selectedClass = c;
    }
    
    public CustomBox getSelectedClass(){
        return selectedClass;
    }
}
