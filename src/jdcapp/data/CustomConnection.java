/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 *
 * @author Noah
 */
public class CustomConnection {
    
    public static final String ARROW_POINT_TYPE = "arrow";
    public static final String DIAMOND_POINT_TYPE = "diamond";
    public static final String FEATHERED_ARROW_POINT_TYPE = "feathered arrow";
    public static final String DEFAULT_POINT_TYPE = "square";
    
    //The classes on either end of the connection. The fromClass will be the custom
    //class creating the connection, and the toClass will be the custom/imported
    //class that the connection is being linked to.
    private String fromClass;
    private String toClass;
    
    //The ArrayList of points in the connection
    private ArrayList<CustomPoint> points;
    
    //The arrow type (can be one of the three point types, but not the default one)
    private String arrowType;
    
    //The Group containing all the display elements of the connection
    private Group display;
    
    public CustomConnection(CustomBox fromClass, CustomBox toClass, String arrowType){
        //Initialize the fromClass string
        if(fromClass instanceof CustomClassWrapper)
            this.fromClass = ((CustomClassWrapper) fromClass).getData().getClassName();
        else
            this.fromClass = ((CustomImport) fromClass).getImportName();
        
        //Initialize the toClass string
        if(toClass instanceof CustomClassWrapper)
            this.toClass = ((CustomClassWrapper) toClass).getData().getClassName();
        else
            this.toClass = ((CustomImport) toClass).getImportName();
        
        //Initialize the array of points, and add the default two points to it
        points = new ArrayList<>();
        this.arrowType = arrowType;
        CustomPoint fromPoint = new CustomPoint(fromClass.getStartX(), fromClass.getStartY(), DEFAULT_POINT_TYPE, false);
        CustomPoint toPoint = new CustomPoint(toClass.getStartX(), toClass.getStartY(), DEFAULT_POINT_TYPE, false);
        points.add(fromPoint);
        points.add(toPoint);
        
        //Initialize the display and add all the renderable elements to it
        display = new Group();
        toDisplay();
    }
    
    public CustomConnection(String fromClass, String toClass, String arrowType, ArrayList<CustomPoint> points){
        this.fromClass = fromClass;
        this.toClass = toClass;
        this.arrowType = arrowType;
        this.points = points;
        display = new Group();
        toDisplay();
    }
    
    public void toDisplay(){
        display.getChildren().clear();
        
        double prevX = -1;
        double prevY = -1;
        for(CustomPoint p : points){
            p.toDisplay();
            display.getChildren().add(p.getDisplay());
            if(prevX >= 0 && prevY >= 0){
                Line newLine = new Line(prevX, prevY, p.getStartX(), p.getStartY());
                display.getChildren().add(newLine);
            }
            prevX = p.getStartX();
            prevY = p.getStartY();
        }
    }
    
    public String getFromClass(){
        return fromClass;
    }
    
    public String getToClass(){
        return toClass;
    }
    
    public Group getDisplay(){
        return display;
    }
    
    public CustomPoint getFirstPoint(){
        return points.get(0);
    }
    
    public CustomPoint getLastPoint(){
        return points.get(points.size() - 1);
    }
    
    public ArrayList<CustomPoint> getPoints(){
        return points;
    }
    
    public String getArrowType(){
        return arrowType;
    }
}
