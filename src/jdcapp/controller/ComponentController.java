/*
 * 
 */
package jdcapp.controller;

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
        
        if(selected){
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().add(classToImplement);
            //TODO: Insert code here to generate a line connecting selected class and classToImplement
            //and possibly generate a box for classToImplement as well (search ArrayList of temporary class
            //names to see if a box needs to be generated and added to classes ArrayList)
        }
        else{
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().remove(classToImplement);
            //TODO: Insert code here to remove the line connecting selected class and classToImplement
            //and possibly remove generated box for classToImplement as well, if classToImplement has no
            //other connections to it (search ArrayList of connections to check)
        }
    }

    public void handleExtendedClassSelected(String extendedClass) {
        DataManager dataManager = app.getDataManager();
        
        if(!((CustomClassWrapper)dataManager.getSelectedClass()).getData().getExtendedClass().equals(extendedClass)){
            if(!((CustomClassWrapper)dataManager.getSelectedClass()).getData().getExtendedClass().equals(CustomClass.DEFAULT_EXTENDED_CLASS)){
                //TODO: Insert code here to remove the line connecting selected class and old extended class
                //and possibly remove generated box for classToImplement as well, if old extended class has no
                //other connections to it (search ArrayList of connections to check)
            }
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setExtendedClass(extendedClass);
            //TODO: Insert code here to add a line connecting selected class and extendedClass
            //and possibly generate a box for extendedClass as well (search ArrayList of temporary class
            //names to see if a box needs to be generated and added to classes ArrayList)
        }
    }

    public void handleRemoveAllInterfaces() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        ((CustomClassWrapper)dataManager.getSelectedClass()).getData().getImplementedClasses().clear();
        //TODO: Insert code here to remove all interface connections to selected class
        //and possibly remove generated boxes as well
        
        workspaceManager.wipeSelectedClassData();
        workspaceManager.reloadSelectedClassData();
    }

    public void handleRemoveExtendedClass() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        if(!((CustomClassWrapper)dataManager.getSelectedClass()).getData().getExtendedClass().equals(CustomClass.DEFAULT_EXTENDED_CLASS)){
            ((CustomClassWrapper)dataManager.getSelectedClass()).getData().setExtendedClass(CustomClass.DEFAULT_EXTENDED_CLASS);
            //TODO: Insert code here to remove the line connecting selected class and old extended class
            //and possibly remove generated box as well (search ArrayList of connections to check)
        }
        
        workspaceManager.wipeSelectedClassData();
        workspaceManager.reloadSelectedClassData();
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
        workspaceManager.reloadSelectedClass();
        workspaceManager.loadVariableData();
        //TODO: Insert code here to check removed variable and remove any lines associated with
        //it, and possibly remove generated box as well
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
        workspaceManager.reloadSelectedClass();
        workspaceManager.loadMethodData();
        //TODO: Insert code here to check removed method and remove any lines associated with
        //it, and possibly remove generated boxes as well
    }
    
}
