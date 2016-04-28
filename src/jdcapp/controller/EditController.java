/*
 * 
 */
package jdcapp.controller;

import jdcapp.JDCApp;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.DataManager;
import jdcapp.data.JDCAppState;
import jdcapp.gui.WorkspaceManager;

/**
 *
 * @author Noah
 */
public class EditController {
    
    //TODO: change these values once finished testing
    public static final double defaultX = 300;
    public static final double defaultY = 300;
    
    //The parent application
    JDCApp app;
    
    public EditController(JDCApp initApp){
        app = initApp;
    }

    public void handleSelectRequest() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        dataManager.setState(JDCAppState.SELECTING);
        workspaceManager.updateEditToolbarControls();
    }

    public void handleResizeRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleAddClassRequest() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Create a new custom class and set its position to the default x and y values given
        CustomClassWrapper newClass = new CustomClassWrapper(defaultX, defaultY);
        
        //Add the default class to the arraylist of classes
        dataManager.getClasses().add(newClass);
        
        //Select the newly-created class
        dataManager.setState(JDCAppState.SELECTING);
        if(dataManager.getSelectedClass() != null){
            workspaceManager.unhighlightSelectedClass();
        }
        dataManager.setSelectedClass(newClass);
        
        //Load the data from the selected class and enable the component controls
        workspaceManager.loadSelectedClassData();
        
        //Update the edit toolbar controls to reflect the selection, and reload the workspace so that the class is visible
        workspaceManager.updateEditToolbarControls();
        workspaceManager.reloadSelectedClass();
        dataManager.checkCombinations();
    }

    public void handleAddInterfaceRequest() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Create a new custom class and set its position to the default x and y values given
        //Set the interface value to true (default is false)
        CustomClassWrapper newClass = new CustomClassWrapper(defaultX, defaultY);
        newClass.getData().setInterfaceValue(true);
        
        //Add the default class to the arraylist of classes
        dataManager.getClasses().add(newClass);
        
        //Select the newly-created class
        dataManager.setState(JDCAppState.SELECTING);
        if(dataManager.getSelectedClass() != null){
            workspaceManager.unhighlightSelectedClass();
        }
        dataManager.setSelectedClass(newClass);
        
        //Load the data from the selected class and enable the component controls
        workspaceManager.loadSelectedClassData();
        
        //Update the edit toolbar controls to reflect the selection, and reload the workspace so that the class is visible
        workspaceManager.updateEditToolbarControls();
        workspaceManager.reloadSelectedClass();
        dataManager.checkCombinations();
    }

    public void handleRemoveRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleUndoRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleRedoRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
