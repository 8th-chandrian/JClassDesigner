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
            workspaceManager.reloadSelectedClass();

            //Checks to ensure that no other classes have that same name/package combination
            dataManager.checkCombinations();
        }
    }

    public void handlePackageNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
          
        //Set package name to text
        if(dataManager.getSelectedClass() != null){
            dataManager.getSelectedClass().getData().setPackageName(text);
            
            //Checks to ensure that no other classes have that same name/package combination
            dataManager.checkCombinations();
        }
    }
    
}
