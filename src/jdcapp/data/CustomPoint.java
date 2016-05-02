/*
 * 
 */
package jdcapp.data;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Noah
 */
public class CustomPoint extends CustomBox{
    
    public static final double DEFAULT_POINT_SIDE = 5;
    
    //The type of point. Can be either square, diamond, arrow, or feathered arrow
    private String pointType;
    
    //The first and last points in any CustomConnection cannot be removed. All others can be.
    private boolean isRemovable;
    
    //The Group of all display elements in this point
    private Group display;
    
    public CustomPoint(double initX, double initY, String pointType, boolean isRemovable){
        super(initX, initY);
        this.isRemovable = isRemovable;
        this.pointType = pointType;
        display = new Group();
        toDisplay();
    }
    
    @Override
    public void toDisplay(){
        display.getChildren().clear();
        
        //TODO: Alter this method to support all different arrow types
        Rectangle pointBox = new Rectangle();
        pointBox.setHeight(DEFAULT_POINT_SIDE);
        pointBox.setWidth(DEFAULT_POINT_SIDE);
        pointBox.setX(startX);
        pointBox.setY(startY);
        pointBox.setFill(Color.TRANSPARENT);
        pointBox.setStroke(Color.BLACK);
        pointBox.setStrokeWidth(1);
        
        display.getChildren().add(pointBox);
    }
    
    @Override
    public Group getDisplay(){
        return display;
    }
    
    @Override
    public double getStartX(){
        return startX;
    }
    
    @Override
    public double getStartY(){
        return startY;
    }
    
    //This class returns null, as points have no text
    @Override
    public Text getNameText(){
        return null;
    }
    
    @Override
    public void highlight(Effect e){
        display.getChildren().get(0).setEffect(e);
    }
    
    @Override
    public Shape getOutlineShape(){
        return (Shape)display.getChildren().get(0);
    }
    
    public String getPointType(){
        return pointType;
    }
    
    public boolean getIsRemovable(){
        return isRemovable;
    }
}
