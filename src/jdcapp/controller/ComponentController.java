/*
 * 
 */
package jdcapp.controller;

import java.util.ArrayList;
import jdcapp.JDCApp;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomConnection;
import jdcapp.data.CustomImport;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;
import jdcapp.data.DataManager;
import jdcapp.gui.WorkspaceManager;


/**
 *
 * @author Noah
 */

public class ComponentController {
    
    JDCApp app;
    
    public static final double DEFAULT_IMPORT_OFFSET = 15;
    
    public ComponentController(JDCApp initApp) {
        app = initApp;
    }

    public void handleClassNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Set class name to text, display class name
        if(dataManager.getSelectedClass() != null){
            String oldText = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
            
            //Ensure that all connections are updated with the new from/to class name
            ArrayList<CustomConnection> fromConnections = dataManager.getFromConnections(dataManager.getSelectedClass());
            ArrayList<CustomConnection> toConnections = dataManager.getToConnections(dataManager.getSelectedClass());
            for(CustomConnection c : fromConnections){
                c.setFromClass(text);
            }
            for(CustomConnection c : toConnections){
                c.setToClass(text);
            }
            
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setClassName(text);
            workspaceManager.reloadSelectedClass();
            workspaceManager.reloadSelectedClassData();
            //Checks to ensure that no other classes have that same name/package combination
            dataManager.checkCombinations();
        }
    }

    public void handlePackageNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
          
        //Set package name to text
        if(dataManager.getSelectedClass() != null){
            if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setPackageName(text);
            else
                ((CustomImport)dataManager.getSelectedClass()).setPackageName(text);
            
            //Checks to ensure that no other classes have that same name/package combination
            dataManager.checkCombinations();
        }
    }
    
    public void handleNewParentAdded(String text) {
        app.getDataManager().getTempParents().add(text);
        app.getWorkspaceManager().reloadSelectedClassData();
    }

    public void handleInterfaceChecked(boolean selected, String classToImplement) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        if(selected){
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().add(classToImplement);
            //Check to see if classToImplement exists as a class in classes. If not, create
            //it and create a new connection linking it and the selected class as well.
            if(!dataManager.hasName(classToImplement)){
                dataManager.getTempParents().remove(classToImplement);
                CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + DEFAULT_IMPORT_OFFSET, 
                        dataManager.getSelectedClass().getStartY() + DEFAULT_IMPORT_OFFSET, classToImplement);
                dataManager.getClasses().add(newImport);
                dataManager.generateConnection(dataManager.getSelectedClass(), newImport, CustomConnection.ARROW_POINT_TYPE);
                dataManager.checkCombinations();
            }
            else{
                //Check connections to see if one already exists for the string pair. If so, do nothing. If not, create one.
                String selectedClassName;
                if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                    selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                else
                    selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                if(!dataManager.checkConnectionPair(selectedClassName, classToImplement)){
                    dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(classToImplement), 
                            CustomConnection.ARROW_POINT_TYPE);
                }
                else
                    dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), classToImplement);
            }
        }
        else{
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().remove(classToImplement);
            
            //Remove class to implement connection
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), classToImplement))
                removeConnectionAndClass(classToImplement);
            else
                dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), classToImplement);
        }
        workspaceManager.reloadWorkspace();
    }

    public void handleExtendedClassSelected(String extendedClass) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        String oldExtendedClass = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getExtendedClass();
        if(!oldExtendedClass.equals(extendedClass)){
            if(!oldExtendedClass.equals(CustomClass.DEFAULT_EXTENDED_CLASS)){
                ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setExtendedClass(CustomClass.DEFAULT_EXTENDED_CLASS);
                if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldExtendedClass))
                    removeConnectionAndClass(oldExtendedClass);
                else
                    dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), oldExtendedClass);
            }
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setExtendedClass(extendedClass);
            if(!dataManager.hasName(extendedClass)){
                dataManager.getTempParents().remove(extendedClass);
                CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + DEFAULT_IMPORT_OFFSET, 
                        dataManager.getSelectedClass().getStartY() + DEFAULT_IMPORT_OFFSET, extendedClass);
                dataManager.getClasses().add(newImport);
                dataManager.generateConnection(dataManager.getSelectedClass(), newImport, CustomConnection.ARROW_POINT_TYPE);
                workspaceManager.reloadWorkspace();
                dataManager.checkCombinations();
            }
            else{
                //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                String selectedClassName;
                if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                    selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                else
                    selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                if(!dataManager.checkConnectionPair(selectedClassName, extendedClass)){
                    dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(extendedClass), 
                            CustomConnection.ARROW_POINT_TYPE);
                }
                else
                    dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), extendedClass);
                workspaceManager.reloadWorkspace();
            }
        }
    }

    public void handleRemoveAllInterfaces() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ArrayList<String> oldImplementedClasses = new ArrayList<>();
        for(String s : ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses()){
            oldImplementedClasses.add(s);
        }
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().removeAll(
                ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses());
        
        //Remove all old implemented class connections
        for(String implementedClass : oldImplementedClasses){
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), implementedClass))
                removeConnectionAndClass(implementedClass);
            else
                dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), implementedClass);
        }

        workspaceManager.reloadWorkspace();
        workspaceManager.wipeSelectedClassData();
        workspaceManager.reloadSelectedClassData();
    }

    public void handleRemoveExtendedClass() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        String oldExtendedClass = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getExtendedClass();
        if(!oldExtendedClass.equals(CustomClass.DEFAULT_EXTENDED_CLASS)){
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setExtendedClass(CustomClass.DEFAULT_EXTENDED_CLASS);
            
            //Remove old extended class connection
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldExtendedClass))
                removeConnectionAndClass(oldExtendedClass);
            else
                dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), oldExtendedClass);
        }
        
        workspaceManager.reloadWorkspace();
        workspaceManager.wipeSelectedClassData();
        workspaceManager.reloadSelectedClassData();
    }

    public void handleAddVariable() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getVariables().add(new CustomVar());
        workspaceManager.reloadWorkspace();
        workspaceManager.loadVariableData();
        //TODO: Insert code here to set all variables with default data fields to red text
        //This method should be similar to checkCombinations method in DataManager
    }

    public void handleRemoveVariable(CustomVar customVar) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getVariables().remove(customVar);
        
        //Remove variable type connection
        if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), customVar.getVarType()))
            removeConnectionAndClass(customVar.getVarType());
        else
            dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), customVar.getVarType());
            
        workspaceManager.reloadWorkspace();
        workspaceManager.loadVariableData();
    }

    public void handleAddMethod() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getMethods().add(new CustomMethod());
        workspaceManager.reloadWorkspace();
        workspaceManager.loadMethodData();
        //TODO: Insert code here to set all methods with default data fields to red text
        //This method should be similar to checkCombinations method in DataManager
    }

    public void handleRemoveMethod(CustomMethod customMethod) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getMethods().remove(customMethod);
        
        //Remove return type connection
        if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), customMethod.getReturnType()))
            removeConnectionAndClass(customMethod.getReturnType());
        else
            dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), customMethod.getReturnType());
        
        //Remove all arguments
        for(String arg : customMethod.getArguments()){
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), arg))
                removeConnectionAndClass(arg);
            else
                dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), arg);
        }
        workspaceManager.reloadWorkspace();
        workspaceManager.loadMethodData();
    }

    public void handleVarTypeChange(CustomVar customVar, String newValue, String oldValue) {
        DataManager dataManager = app.getDataManager();
        if(!oldValue.equals(newValue)){
            customVar.setVarType(newValue);
            if(!isPrimitive(newValue)){
                //Generate connection and possibly class as well for new value
                if(!dataManager.hasName(newValue)){
                    CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + DEFAULT_IMPORT_OFFSET, 
                            dataManager.getSelectedClass().getStartY() + DEFAULT_IMPORT_OFFSET, newValue);
                    dataManager.getClasses().add(newImport);
                    dataManager.generateConnection(dataManager.getSelectedClass(), newImport, CustomConnection.DIAMOND_POINT_TYPE);
                }
                else{
                    //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                    String selectedClassName;
                    if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                        selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                    else
                        selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                    if(!dataManager.checkConnectionPair(selectedClassName, newValue)){
                        dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(newValue), 
                                CustomConnection.DIAMOND_POINT_TYPE);
                    }
                    else
                        dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), newValue);
                }
            }
            //Remove connection associated with old value
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldValue))
                removeConnectionAndClass(oldValue);
            else
                dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), oldValue);
            app.getWorkspaceManager().reloadWorkspace();
            dataManager.checkCombinations();
        }
        app.getWorkspaceManager().loadSelectedClassData();
    }

    public void handleMethodTypeChange(CustomMethod customMethod, String newValue, String oldValue) {
        DataManager dataManager = app.getDataManager();
        if(!oldValue.equals(newValue)){
            customMethod.setReturnType(newValue);
            //Create new connection and possibly class for new value
            if(!isPrimitive(newValue)){
                if(!dataManager.hasName(newValue)){
                    CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + DEFAULT_IMPORT_OFFSET, 
                            dataManager.getSelectedClass().getStartY() + DEFAULT_IMPORT_OFFSET, newValue);
                    dataManager.getClasses().add(newImport);
                    dataManager.generateConnection(dataManager.getSelectedClass(), newImport, CustomConnection.FEATHERED_ARROW_POINT_TYPE);
                }
                else{
                    //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                    String selectedClassName;
                    if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                        selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                    else
                        selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                    if(!dataManager.checkConnectionPair(selectedClassName, newValue)){
                        dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(newValue), 
                                CustomConnection.FEATHERED_ARROW_POINT_TYPE);
                    }
                    else
                        dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), newValue);
                }
            }
            //Remove connection associated with old value
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldValue))
                removeConnectionAndClass(oldValue);
            else
                dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), oldValue);
            app.getWorkspaceManager().reloadWorkspace();
            dataManager.checkCombinations();
        }
        app.getWorkspaceManager().loadSelectedClassData();
    }
    
    public void handleArgsChange(CustomMethod customMethod, ArrayList<String> oldArgs, ArrayList<String> newArgs) {
        DataManager dataManager = app.getDataManager();
        if(!oldArgs.equals(newArgs)){
            customMethod.setArguments(newArgs);
            
            //Generate all connections and classes necessary for new arguments
            for(String arg : newArgs){
                String[] argArray = arg.split(" : ");
                if(!isPrimitive(argArray[1])){
                    if(!dataManager.hasName(argArray[1])){
                        CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + DEFAULT_IMPORT_OFFSET, 
                                dataManager.getSelectedClass().getStartY() + DEFAULT_IMPORT_OFFSET, argArray[1]);
                        dataManager.getClasses().add(newImport);
                        dataManager.generateConnection(dataManager.getSelectedClass(), newImport, CustomConnection.FEATHERED_ARROW_POINT_TYPE);
                    }
                    else{
                        //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                        String selectedClassName;
                        if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                            selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                        else
                            selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                        if(!dataManager.checkConnectionPair(selectedClassName, argArray[1])){
                            dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(argArray[1]), 
                                    CustomConnection.FEATHERED_ARROW_POINT_TYPE);
                        }
                        else
                            dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), argArray[1]);
                    }
                }
            }
            //Remove all connections associated with old arguments
            for(String arg : oldArgs){
                String[] oldArgArray = arg.split(" : ");
                if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldArgArray[1]))
                    removeConnectionAndClass(oldArgArray[1]);
                else
                    dataManager.reloadArrowType((CustomClassWrapper)dataManager.getSelectedClass(), oldArgArray[1]);
            }
        }
        app.getWorkspaceManager().reloadWorkspace();
        dataManager.checkCombinations();
        app.getWorkspaceManager().loadSelectedClassData();
    }
    
    /**
     * Helper method for removing connections between selected class and class specified
     * by toRemove string, and removing toRemove class as well if no other connections
     * link to it.
     * @param toRemove 
     */
    private void removeConnectionAndClass(String toRemove){
        DataManager dataManager = app.getDataManager();
        //Remove the connection between the selected class and the old extended class
        dataManager.getConnections().remove(dataManager.getConnection(
                ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName(), toRemove));
        //If the old extended class now has no connections associated with it and 
        //is not a custom class, remove it entirely
        if(!dataManager.hasConnectionsAssociated(toRemove) && (dataManager.getClassByName(toRemove) instanceof CustomImport))
            dataManager.getClasses().remove(dataManager.getClassByName(toRemove));
    }
    
    /**
     * Determines whether or not a string is a primitive data type.
     * @param s
     * @return 
     */
    private boolean isPrimitive(String s){
        if(s.equals(CustomMethod.BOOLEAN_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.CHAR_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.INT_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.FLOAT_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.BYTE_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.LONG_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.DOUBLE_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.SHORT_RETURN_TYPE))
            return true;
        else if(s.equals(CustomMethod.VOID_RETURN_TYPE))
            return true;
        else if(s.equals("String"))
            return true;
        else
            return false;
    }
}
