/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Contains a reference to a CustomClass object as data, and holds all requisite
 * values needed to generate the display for that CustomClass object in the workspace.
 * @author Noah
 */
public class CustomClassWrapper extends Group{

    public static final String DISPLAY_TEXT_CSS_ID = "display_text";
    static final double DEFAULT_WRAPPING_WIDTH = 200;
    static double TEXT_LINE_PIXEL_HEIGHT = 12;
    
    //The data contained within this wrapper class
    private CustomClass data;

    //The starting locations from which the CustomClass will be drawn
    private double startX;
    private double startY;
    
    //The width and height of the CustomClass
    private double width;
    private double height;
    
    //The wrapping width, to be used with the toDisplay method (will be set when class is resized)
    private double wrappingWidth;
    
    //The HashMap containing lists of points on the lines connecting this class and its parents
    private HashMap<String, ArrayList<Point2D>> points;
    
    public CustomClassWrapper(double initX, double initY){
        super();
        data = new CustomClass();
        startX = initX;
        startY = initY;
        wrappingWidth = DEFAULT_WRAPPING_WIDTH;
        toDisplay();
    }
    
    /**
     * Constructs requisite text objects and rectangles for the display of the CustomClass
     * object in the workspace.
     */
    public void toDisplay(){
        //Call clear first, to remove any old display objects from the Group
        super.getChildren().clear();
        
        Rectangle outline = new Rectangle();
        Text nameText = new Text();
        Text varsText = new Text();
        Text methodsText = new Text();
        
        //Set the id of the Text objects so that the text is formatted by the CSS
        nameText.setId(DISPLAY_TEXT_CSS_ID);
        varsText.setId(DISPLAY_TEXT_CSS_ID);
        methodsText.setId(DISPLAY_TEXT_CSS_ID);
        
        //Create the name text display
        nameText.setText(data.getClassName() + "\n");
        nameText.setX(getStartX());
        nameText.setY(getStartY() + TEXT_LINE_PIXEL_HEIGHT);
        nameText.setWrappingWidth(getWrappingWidth());
        int numLinesName = 2;
        
        //Create the rectangle surrounding the name text
        Rectangle nameOutline = new Rectangle(getStartX(), getStartY(), getWrappingWidth(), 
                (numLinesName) * TEXT_LINE_PIXEL_HEIGHT);
        nameOutline.setStroke(Color.BLACK);
        nameOutline.setStrokeWidth(1);
        nameOutline.setFill(Color.WHITE);
        
        //Add the text and rectangle to the array of nodes
        //displayClass[1] = nameOutline;
        //displayClass[4] = nameText;
        
        //Create the variable text display
        String vars = " \n";
        int numLinesVars = 2;
        if(!data.getVariables().isEmpty()){
            numLinesVars = 1;
            vars = "";
            for(CustomVar v : data.getVariables()){
                vars += v.getVarName() + "\n";
                numLinesVars++;
            }
        }
        varsText.setText(vars);
        varsText.setX(getStartX());
        varsText.setY(getStartY() + ((numLinesName + 1) * TEXT_LINE_PIXEL_HEIGHT));
        varsText.setWrappingWidth(getWrappingWidth());
        
        //Create the rectangle surrounding the variable text
        Rectangle varsOutline = new Rectangle(getStartX(), getStartY() + (numLinesName * TEXT_LINE_PIXEL_HEIGHT), 
                getWrappingWidth(), (numLinesVars) * TEXT_LINE_PIXEL_HEIGHT);
        varsOutline.setStroke(Color.BLACK);
        varsOutline.setStrokeWidth(1);
        varsOutline.setFill(Color.WHITE);
        
        //Add the text and rectangle to the array of nodes
        //displayClass[2] = varsOutline;
        //displayClass[5] = varsText;
        
        //Create the method text display
        String methods = " \n";
        int numLinesMethods = 2;
        if(!data.getMethods().isEmpty()){
            numLinesMethods = 1;
            methods = "";
            for(CustomMethod m : data.getMethods()){
                methods += m.getMethodName() + "\n";
                numLinesMethods++;
            }
        }
        methodsText.setText(methods);
        methodsText.setX(getStartX());
        methodsText.setY(getStartY() + ((numLinesName + numLinesVars + 1) * TEXT_LINE_PIXEL_HEIGHT));
        methodsText.setWrappingWidth(getWrappingWidth());
        
        //Create the rectangle surrounding the method text
        Rectangle methodsOutline = new Rectangle(getStartX(), getStartY() + ((numLinesName + numLinesVars) * TEXT_LINE_PIXEL_HEIGHT), 
                getWrappingWidth(), (numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        methodsOutline.setStroke(Color.BLACK);
        methodsOutline.setStrokeWidth(1);
        methodsOutline.setFill(Color.WHITE);
        
        //Add the text and rectangle to the array of nodes
        //displayClass[3] = methodsOutline;
        //displayClass[6] = methodsText;
        
        //Create the overlaying rectangle
        outline.setX(getStartX());
        outline.setY(getStartY());
        outline.setWidth(getWrappingWidth());
        outline.setHeight((numLinesName + numLinesVars + numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(1);
        outline.setFill(Color.WHITE);
        
        //Add the overlaying rectangle to the array of nodes as index 0
        //displayClass[0] = outline;
        
        //Set the width and height of the CustomClass to the width and height of the overlaying rectangle
        setWidth(getWrappingWidth());
        setHeight((numLinesName + numLinesVars + numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        
        super.getChildren().add(outline);
        super.getChildren().add(nameOutline);
        super.getChildren().add(varsOutline);
        super.getChildren().add(methodsOutline);
        super.getChildren().add(nameText);
        super.getChildren().add(varsText);
        super.getChildren().add(methodsText);
    }
    
    public Rectangle getOutlineRectangle(){
        return (Rectangle)super.getChildren().get(0);
    }
    
    public double getStartX() { return startX; }

    public void setStartX(double startX) { this.startX = startX; }

    public double getStartY() { return startY; }

    public void setStartY(double startY) { this.startY = startY; }

    public double getWidth() { return width; }

    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }

    public void setHeight(double height) { this.height = height; }

    public HashMap<String, ArrayList<Point2D>> getPoints() {
        return points;
    }

    public void setPoints(HashMap<String, ArrayList<Point2D>> points) {
        this.points = points;
    }

    private double getWrappingWidth() {
        return wrappingWidth;
    }
    
    public void setWrappingWidth(double w){
        wrappingWidth = w;
    }
    
        
    //TODO: Finish coding this class
}
