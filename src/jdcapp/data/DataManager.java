/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
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
    
    //The ArrayList containing data on all connections
    ArrayList<CustomConnection> connections;
    
    //The ArrayList containing temporary parents that have been added by the user.
    //These can be selected as implemented or extended classes. If they are, new
    //CustomImport objects are created and added to the classes array, and they are
    //removed from tempParents. Note that tempParents is deleted every time the 
    //program is closed.
    ArrayList<String> tempParents;
    
    //The class currently selected
    CustomBox selectedClass;
    
    //The point currently selected and its parent connection (these variables should
    //only be altered at the same time)
    CustomPoint selectedPoint;
    CustomConnection selectedConnection;
    
    //The state of the application
    JDCAppState state;
    
    //Whether or not the code could currently be exported
    boolean isExportable;
    
    public DataManager(JDCApp init){
        app = init;
        selectedClass = null;
        selectedPoint = null;
        selectedConnection = null;
        classes = new ArrayList<>();
        connections = new ArrayList<>();
        tempParents = new ArrayList<>();
        isExportable = false;
        //TODO: Finish coding constructor (is this all we need here?)
    }
    
    
    //TODO: FINISH CODING CLASS

    public void reset() {
        //Initialize all variables except app and selectedClass
        classes = new ArrayList<>();
        connections = new ArrayList<>();
        tempParents = new ArrayList<>();
        selectedClass = null;
        selectedPoint = null;
        selectedConnection = null;
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
        
        //If point is currently highlighted, unhighlight it
        if(selectedPoint != null){
            workspaceManager.unhighlightSelectedPoint();
            selectedPoint = null;
            selectedConnection = null;
        }
        
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
            if(c.getOutlineShape().contains(x, y))
                return c;
        }
        return null;
    }
    
    public CustomPoint selectTopPoint(double x, double y){
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        CustomPoint toSelect = null;
        CustomConnection containsPoint = null; 
        connection_loop: for(CustomConnection c : connections){
            for(CustomPoint p : c.getPoints()){
                if(p.getOutlineShape().contains(x, y)){
                    toSelect = p;
                    containsPoint = c;
                    break connection_loop;
                }   
            }
        }
        
        //Don't need to do anything if no point was clicked on
        if(toSelect == null){
            return null;
        }
        
        //Don't need to do anything if the point clicked on is already selected
        if(toSelect == selectedPoint)
            return selectedPoint;
        
        //If the point clicked on is not already selected, and another class is, deselect
        //the selected class
        if(selectedClass != null){
            workspaceManager.unhighlightSelectedClass();
            workspaceManager.wipeSelectedClassData();
            workspaceManager.wipeTableData();
            selectedClass = null;
        }
        
        //If another point is currently selected, deselect it
        if(selectedPoint != null){
            workspaceManager.unhighlightSelectedPoint();
        }
        selectedPoint = toSelect;
        selectedConnection = containsPoint;
        workspaceManager.highlightSelectedPoint();
        return toSelect;
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
     * Checks classes for nameToCheck and returns the CustomBox with the matching name.
     * @param nameToCheck
     * @return 
     */
    public CustomBox getClassByName(String nameToCheck){
        for(CustomBox c : classes){
            if(c instanceof CustomClassWrapper){
                if(((CustomClassWrapper)c).getData().getClassName().equals(nameToCheck))
                    return c;
            }
            else{
                if(((CustomImport)c).getImportName().equals(nameToCheck))
                    return c;
            }
        }
        return null;
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
    
    /**
     * Checks all CustomConnection objects in connections to find one with the matching fromClass and toClass.
     * If one is found, method returns true, otherwise method returns false.
     * @param fromClass
     * @param toClass 
     * @return boolean
     */
    public boolean checkConnectionPair(String fromClass, String toClass){
        for(CustomConnection c : connections){
            if(c.getFromClass().equals(fromClass) && c.getToClass().equals(toClass))
                return true;
        }
        return false;
    }
    
    /**
     * Generates a new connection between the two given CustomBoxes
     * @param fromClass
     * @param toClass 
     * @param connectionType
     */
    public void generateConnection(CustomBox fromClass, CustomBox toClass, String connectionType){
        CustomConnection newConnection = new CustomConnection(fromClass, toClass, connectionType);
        connections.add(newConnection);
    }
    
    /**
     * Gets all CustomConnections whose fromClass strings match the name of the CustomBox parameter.
     * @param b
     * @return 
     */
    public ArrayList<CustomConnection> getFromConnections(CustomBox b){
        String toSearch;
        ArrayList<CustomConnection> fromConnections = new ArrayList<>();
        if(b instanceof CustomClassWrapper)
            toSearch = ((CustomClassWrapper) b).getData().getClassName();
        else
            toSearch = ((CustomImport) b).getImportName();
        
        for(CustomConnection c : connections){
            if(c.getFromClass().equals(toSearch))
                fromConnections.add(c);
        }
        return fromConnections;
    }
    
    /**
     * Gets all CustomConnections whose toClass strings match the name of the CustomBox parameter.
     * @param b
     * @return 
     */
    public ArrayList<CustomConnection> getToConnections(CustomBox b){
        String toSearch;
        ArrayList<CustomConnection> toConnections = new ArrayList<>();
        if(b instanceof CustomClassWrapper)
            toSearch = ((CustomClassWrapper) b).getData().getClassName();
        else
            toSearch = ((CustomImport) b).getImportName();
        
        for(CustomConnection c : connections){
            if(c.getToClass().equals(toSearch))
                toConnections.add(c);
        }
        return toConnections;
    }
    
    /**
     * Checks whether the given class name has any connections leading to or from it.
     * @param classToCheck
     * @return 
     */
    public boolean hasConnectionsAssociated(String classToCheck){
        for(CustomConnection c : connections){
            if(c.getFromClass().equals(classToCheck) || c.getToClass().equals(classToCheck))
                return true;
        }
        return false;
    }
    
    /**
     * Calls initDrag on every point associated with the given CustomBox, using the
     * given x and y parameters.
     * @param b
     * @param initX
     * @param initY 
     */
    public void initDragOnConnections(CustomBox b, double initX, double initY){
        ArrayList<CustomConnection> fromConnections = getFromConnections(b);
        ArrayList<CustomConnection> toConnections = getToConnections(b);
        
        for(CustomConnection f : fromConnections){
            f.getFirstPoint().initDrag(initX, initY);
        }
        for(CustomConnection t : toConnections){
            t.getLastPoint().initDrag(initX, initY);
        }
    }
    
    /**
     * Calls drag on every point associated with the given CustomBox, using the
     * given x and y parameters.
     * @param b
     * @param x
     * @param y 
     */
    public void dragConnections(CustomBox b, double x, double y){
        ArrayList<CustomConnection> fromConnections = getFromConnections(b);
        ArrayList<CustomConnection> toConnections = getToConnections(b);
        
        for(CustomConnection f : fromConnections){
            f.getFirstPoint().drag(x, y);
            f.toDisplay();
        }
        for(CustomConnection t : toConnections){
            t.getLastPoint().drag(x, y);
            t.toDisplay();
        }
    }
    
    /**
     * Calls drag on every point associated with the given CustomBox, using the
     * given x and y parameters.
     * @param b
     */
    public void endDragOnConnections(CustomBox b){
        ArrayList<CustomConnection> fromConnections = getFromConnections(b);
        ArrayList<CustomConnection> toConnections = getToConnections(b);
        
        for(CustomConnection f : fromConnections){
            f.getFirstPoint().endDrag();
        }
        for(CustomConnection t : toConnections){
            t.getLastPoint().endDrag();
        }
    }
    
    public CustomConnection getConnection(String fromClass, String toClass){
        for(CustomConnection c : connections){
            if(c.getFromClass().equals(fromClass) && c.getToClass().equals(toClass))
                return c;
        }
        return null;
    }
    
    /**
     * Checks whether or not the string is used in more than one place in a CustomClass.
     * If it is, return true, else return false. This method is used in ComponentController
     * to determine whether or not a connection should be removed.
     * @param c
     * @param toCheck
     * @return 
     */
    public boolean isUsed(CustomClassWrapper c, String toCheck) {
        int useCount = 0;
        CustomClass custom = c.getData();
        if(custom.getExtendedClass().equals(toCheck))
            useCount++;
        for(String implemented : custom.getImplementedClasses()){
            if(implemented.equals(toCheck))
                useCount++;
        }
        for(CustomVar v : custom.getVariables()){
            if(v.getVarType().equals(toCheck))
                useCount++;
        }
        for(CustomMethod m : custom.getMethods()){
            if(m.getReturnType().equals(toCheck))
                useCount++;
            for(String arg : m.getArguments()){
                String[] argArray = arg.split(" : ");
                if(argArray[1].equals(toCheck))
                    useCount++;
            }
        }
        if(useCount > 0)
            return true;
        return false;
    }
    
    /**
     * Called when a implemented/extended class, variable, or method is removed/added but the
     * associated connection already exists/continues to exist. Sets the associated connection's arrow
     * type to its new value based on the values in the class.
     * @param c
     * @param toCheck 
     */
    public void reloadArrowType(CustomClassWrapper c, String toCheck){
        CustomConnection toReload = getConnection(c.getData().getClassName(), toCheck);
        CustomClass data = c.getData();
        String arrowType = CustomConnection.DEFAULT_POINT_TYPE;
        
        for(CustomMethod m : data.getMethods()){
            if(m.getReturnType().equals(toCheck))
                arrowType = CustomConnection.FEATHERED_ARROW_POINT_TYPE;
            for(String arg : m.getArguments()){
                String[] argArray = arg.split(" : ");
                if(argArray[1].equals(toCheck))
                    arrowType = CustomConnection.FEATHERED_ARROW_POINT_TYPE;
            }
        }
        
        for(CustomVar v : data.getVariables()){
            if(v.getVarType().equals(toCheck))
                arrowType = CustomConnection.DIAMOND_POINT_TYPE;
        }
        
        if(data.getExtendedClass().equals(toCheck))
            arrowType = CustomConnection.ARROW_POINT_TYPE;
        for(String i : data.getImplementedClasses()){
            if(i.equals(toCheck))
                arrowType = CustomConnection.ARROW_POINT_TYPE;
        }
        
        toReload.setArrowType(arrowType);
        toReload.getLastPoint().setPointType(arrowType);
    }
        
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
    
    public void setSelectedPoint(CustomPoint p){
        selectedPoint = p;
    }
        
    public CustomPoint getSelectedPoint(){
        return selectedPoint;
    }
    
    public void setSelectedConnection(CustomConnection c){
        selectedConnection = c;
    }
    
    public CustomConnection getSelectedConnection(){
        return selectedConnection;
    }
    
    public ArrayList<CustomConnection> getConnections(){
        return connections;
    }
}
