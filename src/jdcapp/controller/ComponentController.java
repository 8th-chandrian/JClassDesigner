/*
 * 
 */
package jdcapp.controller;

import jdcapp.JDCApp;
import jdcapp.data.CustomClass;
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
            dataManager.getSelectedClass().getData().setClassName(text);
            workspaceManager.reloadSelectedClass();

            //Checks to ensure that no other classes have that same name/package combination
            dataManager.checkCombinations();
        }
    }

    public void handlePackageNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
          
        //Set package name to text
        if(dataManager.getSelectedClass() != null){
            dataManager.getSelectedClass().getData().setPackageName(text);
            
            //Checks to ensure that no other classes have that same name/package combination
            dataManager.checkCombinations();
        }
    }

    public void handleInterfaceChecked(boolean selected, String classToImplement) {
        DataManager dataManager = app.getDataManager();
        
        if(selected){
            dataManager.getSelectedClass().getData().getImplementedClasses().add(classToImplement);
            //TODO: Insert code here to generate a line connecting selected class and classToImplement
            //and possibly generate a box for classToImplement as well (search ArrayList of temporary class
            //names to see if a box needs to be generated and added to classes ArrayList)
        }
        else{
            dataManager.getSelectedClass().getData().getImplementedClasses().remove(classToImplement);
            //TODO: Insert code here to remove the line connecting selected class and classToImplement
            //and possibly remove generated box for classToImplement as well, if classToImplement has no
            //other connections to it (search ArrayList of connections to check)
        }
    }

    public void handleExtendedClassSelected(String extendedClass) {
        DataManager dataManager = app.getDataManager();
        
        if(!dataManager.getSelectedClass().getData().getExtendedClass().equals(extendedClass)){
            if(!dataManager.getSelectedClass().getData().getExtendedClass().equals(CustomClass.DEFAULT_EXTENDED_CLASS)){
                //TODO: Insert code here to remove the line connecting selected class and old extended class
                //and possibly remove generated box for classToImplement as well, if old extended class has no
                //other connections to it (search ArrayList of connections to check)
            }
            dataManager.getSelectedClass().getData().setExtendedClass(extendedClass);
            //TODO: Insert code here to add a line connecting selected class and extendedClass
            //and possibly generate a box for extendedClass as well (search ArrayList of temporary class
            //names to see if a box needs to be generated and added to classes ArrayList)
        }
    }

    public void handleRemoveAllInterfaces() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        dataManager.getSelectedClass().getData().getImplementedClasses().clear();
        //TODO: Insert code here to remove all interface connections to selected class
        //and possibly remove generated boxes as well
        
        workspaceManager.wipeSelectedClassData();
        workspaceManager.loadSelectedClassData();
    }

    public void handleRemoveExtendedClass() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        if(!dataManager.getSelectedClass().getData().getExtendedClass().equals(CustomClass.DEFAULT_EXTENDED_CLASS)){
            dataManager.getSelectedClass().getData().setExtendedClass(CustomClass.DEFAULT_EXTENDED_CLASS);
            //TODO: Insert code here to remove the line connecting selected class and old extended class
            //and possibly remove generated box as well (search ArrayList of connections to check)
        }
        
        workspaceManager.wipeSelectedClassData();
        workspaceManager.loadSelectedClassData();
    }
    
}
