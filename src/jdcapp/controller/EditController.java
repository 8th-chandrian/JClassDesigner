/*
 * 
 */
package jdcapp.controller;

import java.util.ArrayList;
import jdcapp.JDCApp;
import jdcapp.data.CustomBox;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomConnection;
import jdcapp.data.CustomImport;
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
    int numDefaultClassesAdded;
    
    public EditController(JDCApp initApp){
        app = initApp;
        numDefaultClassesAdded = 0;
    }

    public void handleSelectRequest() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        dataManager.setState(JDCAppState.SELECTING);
        workspaceManager.updateEditToolbarControls();
    }

    public void handleResizeRequest() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        dataManager.setState(JDCAppState.RESIZING);
        workspaceManager.updateEditToolbarControls();
    }

    public void handleAddClassRequest() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Create a new custom class and set its position to the default x and y values given
        CustomClassWrapper newClass = new CustomClassWrapper(defaultX, defaultY, CustomClass.DEFAULT_CLASS_NAME + numDefaultClassesAdded);
        numDefaultClassesAdded++;
        
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
        CustomClassWrapper newClass = new CustomClassWrapper(defaultX, defaultY, CustomClass.DEFAULT_CLASS_NAME + numDefaultClassesAdded);
        numDefaultClassesAdded++;
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
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        if(dataManager.getSelectedClass() instanceof CustomClassWrapper){
            for(CustomConnection c : dataManager.getFromConnections(dataManager.getSelectedClass())){
                dataManager.getConnections().remove(c);
            }
        }
        for(CustomConnection c : dataManager.getToConnections(dataManager.getSelectedClass())){
            dataManager.getConnections().remove(c);
        }
        dataManager.getClasses().remove(dataManager.getSelectedClass());
        dataManager.setSelectedClass(null);
        
        //Remove any imports which no longer have connections associated with them
        ArrayList<CustomBox> toRemove = new ArrayList<>();
        for(CustomBox b : dataManager.getClasses()){
            if(b instanceof CustomImport){
                String check = ((CustomImport) b).getImportName();
                if(!dataManager.hasConnectionsAssociated(check)){
                    toRemove.add(b);
                }
            }
        }
        for(CustomBox b : toRemove){
            dataManager.getClasses().remove(b);
        }
        
        //Reload the workspace and wipe out any right toolbar data associated with the removed class
        workspaceManager.reloadWorkspace();
        workspaceManager.wipeSelectedClassData();
        workspaceManager.wipeTableData();
        workspaceManager.updateEditToolbarControls();
    }

    public void handleUndoRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleRedoRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
