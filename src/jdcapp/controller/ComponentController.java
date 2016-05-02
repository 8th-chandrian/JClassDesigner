/*
 * 
 */
package jdcapp.controller;

import java.util.ArrayList;
import jdcapp.JDCApp;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
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
    
    public ComponentController(JDCApp initApp) {
        app = initApp;
    }

    public void handleClassNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Set class name to text, display class name
        if(dataManager.getSelectedClass() != null){
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
                CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + 5, 
                        dataManager.getSelectedClass().getStartY() + 5, classToImplement);
                dataManager.getClasses().add(newImport);
                dataManager.generateConnection(dataManager.getSelectedClass(), newImport);
                workspaceManager.reloadWorkspace();
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
                    dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(classToImplement));
                    workspaceManager.reloadWorkspace();
                }
            }
        }
        else{
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().remove(classToImplement);
            
            //Remove class to implement connection
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), classToImplement))
                removeConnectionAndClass(classToImplement);
            workspaceManager.reloadWorkspace();
        }
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
            }
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setExtendedClass(extendedClass);
            if(!dataManager.hasName(extendedClass)){
                dataManager.getTempParents().remove(extendedClass);
                CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + 5, 
                        dataManager.getSelectedClass().getStartY() + 5, extendedClass);
                dataManager.getClasses().add(newImport);
                dataManager.generateConnection(dataManager.getSelectedClass(), newImport);
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
                    dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(extendedClass));
                    workspaceManager.reloadWorkspace();
                }
            }
        }
    }

    public void handleRemoveAllInterfaces() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ArrayList<String> oldImplementedClasses = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses();
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().clear();
        
        //Remove all old implemented class connections
        for(String implementedClass : oldImplementedClasses){
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), implementedClass))
                removeConnectionAndClass(implementedClass);
        }

        workspaceManager.wipeSelectedClassData();
        workspaceManager.reloadSelectedClassData();
        workspaceManager.reloadWorkspace();
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
        }
        
        workspaceManager.wipeSelectedClassData();
        workspaceManager.reloadSelectedClassData();
        workspaceManager.reloadWorkspace();
    }

    public void handleAddVariable() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getVariables().add(new CustomVar());
        workspaceManager.reloadSelectedClass();
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
            
        workspaceManager.reloadWorkspace();
        workspaceManager.loadVariableData();
    }

    public void handleAddMethod() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getMethods().add(new CustomMethod());
        workspaceManager.reloadSelectedClass();
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
        
        //Remove all arguments
        for(String arg : customMethod.getArguments()){
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), arg))
                removeConnectionAndClass(arg);
        }
        workspaceManager.reloadWorkspace();
        workspaceManager.loadMethodData();
    }

    public void handleVarTypeChange(CustomVar customVar, String newValue, String oldValue) {
        DataManager dataManager = app.getDataManager();
        if(!oldValue.equals(newValue)){
            customVar.setVarType(newValue);
            //Generate connection and possibly class as well for new value
            if(!dataManager.hasName(newValue)){
                CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + 5, 
                        dataManager.getSelectedClass().getStartY() + 5, newValue);
                dataManager.getClasses().add(newImport);
                dataManager.generateConnection(dataManager.getSelectedClass(), newImport);
            }
            else{
                //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                String selectedClassName;
                if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                    selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                else
                    selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                if(!dataManager.checkConnectionPair(selectedClassName, newValue)){
                    dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(newValue));
                }
            }
            //Remove connection associated with old value
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldValue))
                removeConnectionAndClass(oldValue);
            app.getWorkspaceManager().reloadWorkspace();
            dataManager.checkCombinations();
        }
    }

    public void handleMethodTypeChange(CustomMethod customMethod, String newValue, String oldValue) {
        DataManager dataManager = app.getDataManager();
        if(!oldValue.equals(newValue)){
            customMethod.setReturnType(newValue);
            //Create new connection and possibly class for new value
            if(!dataManager.hasName(newValue)){
                CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + 5, 
                        dataManager.getSelectedClass().getStartY() + 5, newValue);
                dataManager.getClasses().add(newImport);
                dataManager.generateConnection(dataManager.getSelectedClass(), newImport);
            }
            else{
                //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                String selectedClassName;
                if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                    selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                else
                    selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                if(!dataManager.checkConnectionPair(selectedClassName, newValue)){
                    dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(newValue));
                }
            }
            //Remove connection associated with old value
            if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), oldValue))
                removeConnectionAndClass(oldValue);
            app.getWorkspaceManager().reloadWorkspace();
            dataManager.checkCombinations();
        }
    }
    
    public void handleArgsChange(CustomMethod customMethod, ArrayList<String> oldArgs, ArrayList<String> newArgs) {
        DataManager dataManager = app.getDataManager();
        if(!oldArgs.equals(newArgs)){
            customMethod.setArguments(newArgs);
            
            //Generate all connections and classes necessary for new arguments
            for(String arg : newArgs){
                String[] argArray = arg.split(" : ");
                if(!dataManager.hasName(argArray[1])){
                    CustomImport newImport = new CustomImport(dataManager.getSelectedClass().getStartX() + 5, 
                            dataManager.getSelectedClass().getStartY() + 5, argArray[1]);
                    dataManager.getClasses().add(newImport);
                }
                else{
                    //Check connections to see if one already exists for string pair. If so, do nothing. If not, create one.
                    String selectedClassName;
                    if(dataManager.getSelectedClass() instanceof CustomClassWrapper)
                        selectedClassName = ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getClassName();
                    else
                        selectedClassName = ((CustomImport)dataManager.getSelectedClass()).getImportName();

                    if(!dataManager.checkConnectionPair(selectedClassName, arg)){
                        dataManager.generateConnection(dataManager.getSelectedClass(), dataManager.getClassByName(arg));
                        app.getWorkspaceManager().reloadWorkspace();
                    }
                }
            }
            //Remove all connections associated with old arguments
            for(String arg : oldArgs){
                if(!dataManager.isUsed((CustomClassWrapper)dataManager.getSelectedClass(), arg))
                    removeConnectionAndClass(arg);
            }
            app.getWorkspaceManager().reloadWorkspace();
            dataManager.checkCombinations();
        }
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
}
