/*
 * 
 */
package jdcapp.controller;

import jdcapp.JDCApp;
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
        
        //Set data object in wrapper class and Text object being displayed to new text
        dataManager.getSelectedClass().getData().setClassName(text);
        dataManager.getSelectedClass().getNameText().setText(text);
    }

    public void handlePackageNameTextEdited(String text) {
        DataManager dataManager = app.getDataManager();
        dataManager.getSelectedClass().getData().setPackageName(text);
    }
    
}
