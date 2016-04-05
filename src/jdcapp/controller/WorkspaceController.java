/*
 * 
 */
package jdcapp.controller;

import jdcapp.JDCApp;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.DataManager;
import jdcapp.data.JDCAppState;
import jdcapp.gui.WorkspaceManager;

/**
 *
 * @author Noah
 */
public class WorkspaceController {

    //The parent app
    JDCApp app;
    
    //Set when a mouse is pressed, referenced when it is dragged, and reset to -1 when it is released
    //TODO: FIGURE OUT A BETTER WAY TO DO THIS
    private double startXLocation;
    private double startYLocation;
    private double origXLocation;
    private double origYLocation;
    
    public WorkspaceController(JDCApp initApp) {
        app = initApp;
        startXLocation = -1;
        startYLocation = -1;
        origXLocation = -1;
        origYLocation = -1;
    }

    public void handleMouseEntered() {
        //TODO: Code event handler
    }

    public void handleMouseExited() {
        //TODO: Code event handler
    }

    //TODO: put mouse pointer change in here
    public void handleMousePressed(double x, double y) {
        DataManager dataManager = app.getDataManager();
        
        if(dataManager.getState() == JDCAppState.SELECTING){
            CustomClassWrapper c = dataManager.selectTopClass(x, y);
            if(c != null){
                dataManager.setState(JDCAppState.DRAGGING_CLASS);
                startXLocation = x;
                startYLocation = y;
                origXLocation = dataManager.getSelectedClass().getStartX();
                origYLocation = dataManager.getSelectedClass().getStartY();
            }
            else{
                dataManager.setState(JDCAppState.DRAGGING_NOTHING);
            }
        }
    }

    public void handleMouseDragged(double x, double y) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Set the starting x and y values of the selected class, then reload it into canvas
        if(dataManager.getState() == JDCAppState.DRAGGING_CLASS){
            dataManager.getSelectedClass().setStartX(origXLocation + (x - startXLocation));
            dataManager.getSelectedClass().setStartY(origYLocation + (y - startYLocation));
            workspaceManager.reloadSelectedClass();
        }
        else if(dataManager.getState() == JDCAppState.RESIZING_CLASS){
            //TODO: Code this case
        }
    }

    public void handleMouseReleased(double x, double y) {
        DataManager dataManager = app.getDataManager();
        if(dataManager.getState() == JDCAppState.DRAGGING_CLASS){
            startXLocation = -1;
            startYLocation = -1;
            origXLocation = -1;
            origYLocation = -1;
            dataManager.setState(JDCAppState.SELECTING);
        }
        else if(dataManager.getState() == JDCAppState.DRAGGING_NOTHING){
            dataManager.setState(JDCAppState.SELECTING);
        }
    }
    
}
