/*
 * 
 */
package jdcapp.controller;

import javafx.scene.shape.Rectangle;
import jdcapp.JDCApp;
import jdcapp.data.CustomBox;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomConnection;
import jdcapp.data.CustomPoint;
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
    
    public WorkspaceController(JDCApp initApp) {
        app = initApp;
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
            CustomPoint p = dataManager.selectTopPoint(x, y);
            if(p != null){
                if(app.getWorkspaceManager().getSnapToGrid())
                    dataManager.getSelectedPoint().initDragSnapped();
                dataManager.getSelectedPoint().initDrag(x, y);
                dataManager.setState(JDCAppState.DRAGGING_POINT);
            }
            else{
                CustomBox c = dataManager.selectTopClass(x, y);
                if(c != null){
                    if(app.getWorkspaceManager().getSnapToGrid()){
                        dataManager.getSelectedClass().initDragSnapped();
                        dataManager.initDragSnappedOnConnections(dataManager.getSelectedClass(), x, y);
                    }
                    dataManager.getSelectedClass().initDrag(x, y);
                    dataManager.initDragOnConnections(dataManager.getSelectedClass(), x, y);
                    dataManager.setState(JDCAppState.DRAGGING_CLASS);
                    app.getWorkspaceManager().moveSelectedClassForward();
                }
                else{
                    dataManager.setState(JDCAppState.DRAGGING_NOTHING);
                }
            }
        }
        else if(dataManager.getState() == JDCAppState.RESIZING){
            CustomBox c = dataManager.resizeTopClass(x, y);
            if(c != null){
                dataManager.getResizedClass().initResize(x, y);
                dataManager.setState(JDCAppState.RESIZING_CLASS);
            }
        }
        app.getWorkspaceManager().updateEditToolbarControls();
    }

    public void handleMouseDragged(double x, double y) {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Set the starting x and y values of the selected class, then reload it into canvas
        if(dataManager.getState() == JDCAppState.DRAGGING_CLASS){
            if(workspaceManager.getSnapToGrid()){
                dataManager.getSelectedClass().dragSnapped(x, y);
                dataManager.dragConnectionsSnapped(dataManager.getSelectedClass(), x, y);
            }
            else{
                dataManager.getSelectedClass().drag(x, y);
                dataManager.dragConnections(dataManager.getSelectedClass(), x, y);
            }
            workspaceManager.reloadSelectedClass();
        }
        else if(dataManager.getState() == JDCAppState.DRAGGING_POINT){
            if(workspaceManager.getSnapToGrid()){
                if(dataManager.getSelectedPoint() == dataManager.getSelectedConnection().getFirstPoint())
                    dataManager.getSelectedPoint().dragSnappedEnd(x, y, 
                            (Rectangle)dataManager.getClassByName(dataManager.getSelectedConnection().getFromClass()).getOutlineShape());
                else if(dataManager.getSelectedPoint() == dataManager.getSelectedConnection().getLastPoint())
                    dataManager.getSelectedPoint().dragSnappedEnd(x, y, 
                            (Rectangle)dataManager.getClassByName(dataManager.getSelectedConnection().getToClass()).getOutlineShape());
                else
                    dataManager.getSelectedPoint().dragSnapped(x, y);
                workspaceManager.reloadSelectedConnection();
            }
            else{
                if(dataManager.getSelectedPoint() == dataManager.getSelectedConnection().getFirstPoint())
                    dataManager.getSelectedPoint().dragEnd(x, y, 
                            (Rectangle)dataManager.getClassByName(dataManager.getSelectedConnection().getFromClass()).getOutlineShape());
                else if(dataManager.getSelectedPoint() == dataManager.getSelectedConnection().getLastPoint())
                    dataManager.getSelectedPoint().dragEnd(x, y, 
                            (Rectangle)dataManager.getClassByName(dataManager.getSelectedConnection().getToClass()).getOutlineShape());
                else
                    dataManager.getSelectedPoint().drag(x, y);
                workspaceManager.reloadSelectedConnection();
            }
        }
        else if(dataManager.getState() == JDCAppState.RESIZING_CLASS){
            dataManager.getResizedClass().resize(x, y);
            workspaceManager.reloadWorkspace();
        }
    }

    public void handleMouseReleased(double x, double y) {
        DataManager dataManager = app.getDataManager();
        if(dataManager.getState() == JDCAppState.DRAGGING_CLASS){
            if(app.getWorkspaceManager().getSnapToGrid())
                dataManager.getSelectedClass().endDragSnapped();
            dataManager.getSelectedClass().endDrag();
            dataManager.endDragOnConnections(dataManager.getSelectedClass());
            dataManager.setState(JDCAppState.SELECTING);
        }
        else if(dataManager.getState() == JDCAppState.DRAGGING_POINT){
            if(app.getWorkspaceManager().getSnapToGrid())
                dataManager.getSelectedPoint().endDragSnapped();
            dataManager.getSelectedPoint().endDrag();
            dataManager.setState(JDCAppState.SELECTING);
        }
        else if(dataManager.getState() == JDCAppState.DRAGGING_NOTHING){
            dataManager.setState(JDCAppState.SELECTING);
        }
        else if(dataManager.getState() == JDCAppState.RESIZING_CLASS){
            dataManager.getResizedClass().endResize();
            dataManager.setResizedClass(null);
            dataManager.setState(JDCAppState.RESIZING);
        }
        else if(dataManager.getState() == JDCAppState.RESIZING_NOTHING){
            dataManager.setState(JDCAppState.RESIZING);
        }
    }

    public void handleAddPointBeforeSelected() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Get the selected point and the point before the selected point
        CustomPoint selectedPoint = dataManager.getSelectedPoint();
        int selectedPointIndex = dataManager.getSelectedConnection().getPoints().indexOf(selectedPoint);
        CustomPoint beforePoint = dataManager.getSelectedConnection().getPoints().get(selectedPointIndex - 1);
        
        //Create the new point halfway between the two
        CustomPoint newPoint = new CustomPoint((selectedPoint.getStartX() + beforePoint.getStartX()) / 2, 
                (selectedPoint.getStartY() + beforePoint.getStartY()) / 2, CustomConnection.DEFAULT_POINT_TYPE, true);
        
        //Add the new point at the index of the currently selected point. The selected point and any following it
        //will have their indices incremented by 1.
        dataManager.getSelectedConnection().getPoints().add(selectedPointIndex, newPoint);
        workspaceManager.reloadWorkspace();
    }

    public void handleAddPointAfterSelected() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        //Get the selected point and the point before the selected point
        CustomPoint selectedPoint = dataManager.getSelectedPoint();
        int selectedPointIndex = dataManager.getSelectedConnection().getPoints().indexOf(selectedPoint);
        CustomPoint afterPoint = dataManager.getSelectedConnection().getPoints().get(selectedPointIndex + 1);
        
        //Create the new point halfway between the two
        CustomPoint newPoint = new CustomPoint((selectedPoint.getStartX() + afterPoint.getStartX()) / 2, 
                (selectedPoint.getStartY() + afterPoint.getStartY()) / 2, CustomConnection.DEFAULT_POINT_TYPE, true);
        
        //Add the new point at the index of the point after the currently selected point. That point and any following it
        //will have their indices incremented by 1.
        dataManager.getSelectedConnection().getPoints().add(selectedPointIndex + 1, newPoint);
        workspaceManager.reloadWorkspace();
    }

    public void handleRemovePoint() {
        DataManager dataManager = app.getDataManager();
        WorkspaceManager workspaceManager = app.getWorkspaceManager();
        
        dataManager.getSelectedConnection().getPoints().remove(dataManager.getSelectedPoint());
        workspaceManager.unhighlightSelectedPoint();
        dataManager.setSelectedPoint(null);
        workspaceManager.reloadWorkspace();
    }
}
