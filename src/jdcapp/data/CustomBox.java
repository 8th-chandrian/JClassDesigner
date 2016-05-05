/*
 * 
 */
package jdcapp.data;

import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import jdcapp.gui.WorkspaceManager;

/**
 *
 * @author Noah
 */
public abstract class CustomBox {
    
    double startX;
    double startY;
    
    double dragX;
    double dragY;
    
    double gridX;
    double gridY;
    
    public CustomBox(double initX, double initY){
        startX = initX;
        startY = initY;
        dragX = -1;
        dragY = -1;
        gridX = -1;
        gridY = -1;
    }
    
    public void initDrag(double initDragX, double initDragY){
        dragX = initDragX;
        dragY = initDragY;
    }
    
    public void drag(double x, double y){
        startX = startX + (x - dragX);
        startY = startY + (y - dragY);
        dragX = x;
        dragY = y;
    }
    
    public void endDrag(){
        dragX = -1;
        dragY = -1;
    }
    
    public void initDragSnapped(){
        gridX = startX;
        gridY = startY;
    }
    
    public void dragSnapped(double x, double y){
        int gridshift = WorkspaceManager.GRID_BOX_SIZE;
        gridX = gridX + (x - dragX);
        gridY = gridY + (y - dragY);
        
        if(gridX % gridshift > (gridshift / 2))
            startX = gridX + (gridshift - (gridX % gridshift));
        else
            startX = gridX - (gridX % gridshift);
        
        if(gridY % gridshift > (gridshift / 2))
            startY = gridY + (gridshift - (gridY % gridshift));
        else
            startY = gridY - (gridY % gridshift);
        
        dragX = x;
        dragY = y;
    }
    
    public void endDragSnapped(){
        gridX = -1;
        gridY = -1;
    }
    
    public abstract double getStartX();
    public abstract double getStartY();
    public abstract void toDisplay();
    public abstract Group getDisplay();
    public abstract Text getNameText();
    public abstract void highlight(Effect e);
    public abstract Shape getOutlineShape();
}
