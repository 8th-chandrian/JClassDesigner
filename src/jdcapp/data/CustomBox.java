/*
 * 
 */
package jdcapp.data;

import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Noah
 */
public abstract class CustomBox {
    
    double startX;
    double startY;
    
    double dragX;
    double dragY;
    
    public CustomBox(double initX, double initY){
        startX = initX;
        startY = initY;
        dragX = -1;
        dragY = -1;
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
    
    public abstract double getStartX();
    public abstract double getStartY();
    public abstract void toDisplay();
    public abstract Group getDisplay();
    public abstract Text getNameText();
    public abstract void highlight(Effect e);
    public abstract Shape getOutlineShape();
}
