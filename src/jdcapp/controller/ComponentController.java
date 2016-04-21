/*
 * 
 */
package jdcapp.controller;

import javafx.scene.paint.Color;
import jdcapp.JDCApp;
import jdcapp.data.DataManager;
import jdcapp.gui.WorkspaceManager;
import static jdcapp.settings.AppPropertyType.INVALID_COMBINATION_ERROR_MESSAGE;
import properties_manager.PropertiesManager;


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
            dataManager.getSelectedClass().getNameText().setText(text);

            //TODO: This is a very clumsy solution with O(n^2) complexity, fix this when you have time
            dataManager.checkCombinations();
        }
    }

    public void handlePackageNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
          
        //Set package name to text
        if(dataManager.getSelectedClass() != null){
            dataManager.getSelectedClass().getData().setPackageName(text);
            
            dataManager.checkCombinations();
        }
    }
    
}
