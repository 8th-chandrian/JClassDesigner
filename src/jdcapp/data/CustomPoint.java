/*
 * 
 */
package jdcapp.data;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Noah
 */
public class CustomPoint extends CustomBox{
    
    public static final double DEFAULT_POINT_SIDE = 5;
    public static final double DIAMOND_DISTRIBUTION = 4;
    
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
        
        if(pointType.equals(CustomConnection.DIAMOND_POINT_TYPE)){
            Polygon diamond = makeDiamond();
            display.getChildren().add(diamond);
        }
        else if(pointType.equals(CustomConnection.ARROW_POINT_TYPE)){
            Polygon arrow = makeArrow();
            display.getChildren().add(arrow);
        }
        else if(pointType.equals(CustomConnection.FEATHERED_ARROW_POINT_TYPE)){
            Polygon invisibleArrow = makeArrow();
            invisibleArrow.setFill(Color.GAINSBORO);
            invisibleArrow.setStroke(Color.TRANSPARENT);
            display.getChildren().add(invisibleArrow);
            Polyline featheredArrow = makeFeatheredArrow();
            display.getChildren().add(featheredArrow);
        }
        else{
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
    }
    
    public Polygon makeDiamond(){
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[] {
            startX, startY,
            startX - 6, startY + 4,
            startX - 12, startY,
            startX - 6, startY - 4
        });
        diamond.setFill(Color.BLACK);
        diamond.setStroke(Color.BLACK);
        diamond.setStrokeWidth(1);
        return diamond;
    }
    
    public Polygon makeArrow(){
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[] {
           startX, startY,
           startX - 8, startY + 4,
           startX - 8, startY - 4
        });
        arrow.setFill(Color.BLACK);
        arrow.setStroke(Color.BLACK);
        arrow.setStrokeWidth(1);
        return arrow;
    }
    
    public Polyline makeFeatheredArrow(){
        Polyline featheredArrow = new Polyline();
        featheredArrow.getPoints().addAll(new Double[] {
           startX - 8, startY + 4,
           startX, startY,
           startX - 8, startY - 4
        });
        featheredArrow.setFill(Color.TRANSPARENT);
        featheredArrow.setStroke(Color.BLACK);
        featheredArrow.setStrokeWidth(1);
        return featheredArrow;
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
    
    public void reloadEndPoint(Rectangle oldBox, Rectangle newBox){
        if(startX == oldBox.getX() + oldBox.getWidth())
            startX = newBox.getX() + newBox.getWidth();
        if(startY == oldBox.getY() + oldBox.getHeight())
            startY = newBox.getY() + newBox.getHeight();
    }
    
    /**
     * Used when dragging a point attached to a CustomClass display rectangle.
     * Keeps point locked to rectangle edge.
     * @param x
     * @param y
     * @param r 
     */
    public void dragEnd(double x, double y, Rectangle r){
        if((startX == r.getX() || startX == r.getX() + r.getWidth()) && (startY == r.getY() || startY == r.getY() + r.getHeight())){
            if(Math.abs(x - dragX) > Math.abs(y - dragY)){
                if(startX + (x - dragX) < r.getX())
                    startX = r.getX();
                else if(startX + (x - dragX) > r.getX() + r.getWidth())
                    startX = r.getX() + r.getWidth();
                else
                    startX = startX + (x - dragX);
            }
            else{
                if(startY + (y - dragY) < r.getY())
                    startY = r.getY();
                else if(startY + (y - dragY) > r.getY() + r.getHeight())
                    startY = r.getY() + r.getHeight();
                else
                    startY = startY + (y - dragY);
            }
        }
        else if(startX == r.getX() || startX == r.getX() + r.getWidth()){
            if(startY < r.getY())
                startY = r.getY();
            else if(startY > r.getY() + r.getHeight())
                startY = r.getY() + r.getHeight();
            else
                startY = startY + (y - dragY);
        }
        else if(startY == r.getY() || startY == r.getY() + r.getHeight()){
            if(startX < r.getX())
                startX = r.getX();
            else if(startX > r.getX() + r.getWidth())
                startX = r.getX() + r.getWidth();
            else
                startX = startX + (x - dragX);
        }
        dragX = x;
        dragY = y;
    }
}
