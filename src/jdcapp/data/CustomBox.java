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
    
    double width;
    double height;
    
    public CustomBox(double initX, double initY){
        startX = initX;
        startY = initY;
        dragX = -1;
        dragY = -1;
        gridX = -1;
        gridY = -1;
        width = -1;
        height = -1;
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
        dragX = -1;
        dragY = -1;
    }
    
    public void initResize(double x, double y){
        dragX = x;
        dragY = y;
        gridX = width;
        gridY = height;
    }
    
    public void resize(double x, double y){
        int gridshift = WorkspaceManager.GRID_BOX_SIZE;
        gridX = gridX + (x - dragX);
        gridY = gridY + (y - dragY);
        
        if(gridX % gridshift > (gridshift / 2))
            width = gridX + (gridshift - (gridX % gridshift));
        else
            width = gridX - (gridX % gridshift);
        
        if(gridY % gridshift > (gridshift / 2))
            height = gridY + (gridshift - (gridY % gridshift));
        else
            height = gridY - (gridY % gridshift);
        
        dragX = x;
        dragY = y;
    }
    
    public void endResize(){
        dragX = -1;
        dragY = -1;
        gridX = -1;
        gridY = -1;
    }
    
    public double getWidth(){
        return width;
    }
    
    public void setWidth(double w){
        width = w;
    }
    
    public double getHeight(){
        return height;
    }
    
    public void setHeight(double h){
        height = h;
    }
    
    public double getStartX(){
        return startX;
    }
    
    public void setStartX (double x){
        startX = x;
    }
    
    public double getStartY(){
        return startY;
    }
    
    public void setStartY(double y){
        startY = y;
    }
    
    public abstract void toDisplay();
    public abstract Group getDisplay();
    public abstract Text getNameText();
    public abstract void highlight(Effect e);
    public abstract Shape getOutlineShape();
}
