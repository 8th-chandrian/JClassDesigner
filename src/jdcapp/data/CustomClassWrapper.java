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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Contains a reference to a CustomClass object as data, and holds all requisite
 * values needed to generate the display for that CustomClass object in the workspace.
 * @author Noah
 */
public class CustomClassWrapper extends Group{

    static final double DEFAULT_WRAPPING_WIDTH = 200;
    
    //The height of the pixels, to be used when setting the text size and calculating the size of the 
    //encompassing rectangles
    static double pixelHeight = 12;
    
    //The text font, to be used with toDisplay (lets us alter the font size when zooming)
    static Font textFont = Font.font("sans-serif", FontWeight.NORMAL, pixelHeight);
    
    //The data contained within this wrapper class
    private CustomClass data;

    //The starting locations from which the CustomClass will be drawn
    private double startX;
    private double startY;
    
    //The width and height of the CustomClass
    private double width;
    private double height;
    
    //The wrapping width, to be used with the toDisplay method (can be changed when class is resized)
    private double wrappingWidth;
    
    //The HashMap containing lists of points on the lines connecting this class and its associated classes
    //Note: The String used for hashing contains the connected class's name
    private HashMap<String, ConnectorArrayList> connections;
    
    public CustomClassWrapper(double initX, double initY){
        super();
        data = new CustomClass();
        startX = initX;
        startY = initY;
        wrappingWidth = DEFAULT_WRAPPING_WIDTH;
        connections = new HashMap<>();
        toDisplay();
    }
    
    /**
     * Constructs requisite text objects and rectangles for the display of the CustomClass
     * object in the workspace.
     * 
     * TODO: Clean up this method and make it more adaptable (it will need serious editing to 
     * support resizing and abstract classes/interfaces)
     */
    public void toDisplay(){
        //Call clear first, to remove any old display objects from the Group
        super.getChildren().clear();
        
        Rectangle outline = new Rectangle();
        Text nameText = new Text();
        Text varsText = new Text();
        Text methodsText = new Text();
        
        //Set the id of the Text objects so that the text is formatted by the CSS
        nameText.setFont(textFont);
        varsText.setFont(textFont);
        methodsText.setFont(textFont);
        
        //Create the name text display
        //TODO: Edit this slightly to take into account interface displaying
        nameText.setText(data.getClassName());
        nameText.setX(startX);
        nameText.setY(startY + pixelHeight);
        nameText.setWrappingWidth(wrappingWidth);
        int numLinesName = 2;
        
        //Create the rectangle surrounding the name text
        Rectangle nameOutline = new Rectangle(startX, startY, wrappingWidth, 
                (numLinesName) * pixelHeight);
        nameOutline.setStroke(Color.BLACK);
        nameOutline.setStrokeWidth(1);
        nameOutline.setFill(Color.WHITE);
        
        //Create the variable text display
        String vars = "";
        int numLinesVars = 2;
        if(!data.getVariables().isEmpty()){
            numLinesVars = 1;
            //vars = "";
            for(CustomVar v : data.getVariables()){
                vars += v.getVarName() + "\n";
                numLinesVars++;
            }
        }
        varsText.setText(vars);
        varsText.setX(startX);
        varsText.setY(startY + ((numLinesName + 1) * pixelHeight));
        varsText.setWrappingWidth(wrappingWidth);
        
        //Create the rectangle surrounding the variable text
        Rectangle varsOutline = new Rectangle(startX, startY + (numLinesName * pixelHeight), 
                wrappingWidth, (numLinesVars) * pixelHeight);
        varsOutline.setStroke(Color.BLACK);
        varsOutline.setStrokeWidth(1);
        varsOutline.setFill(Color.WHITE);
        
        //Create the method text display
        String methods = "";
        int numLinesMethods = 2;
        if(!data.getMethods().isEmpty()){
            numLinesMethods = 1;
            //methods = "";
            for(CustomMethod m : data.getMethods()){
                methods += m.getMethodName() + "\n";
                numLinesMethods++;
            }
        }
        methodsText.setText(methods);
        methodsText.setX(startX);
        methodsText.setY(startY + ((numLinesName + numLinesVars + 1) * pixelHeight));
        methodsText.setWrappingWidth(wrappingWidth);
        
        //Create the rectangle surrounding the method text
        Rectangle methodsOutline = new Rectangle(startX, startY + ((numLinesName + numLinesVars) * pixelHeight), 
                wrappingWidth, (numLinesMethods) * pixelHeight);
        methodsOutline.setStroke(Color.BLACK);
        methodsOutline.setStrokeWidth(1);
        methodsOutline.setFill(Color.WHITE);
        
        //Create the overlaying rectangle
        outline.setX(startX);
        outline.setY(startY);
        outline.setWidth(wrappingWidth);
        outline.setHeight((numLinesName + numLinesVars + numLinesMethods) * pixelHeight);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(1);
        outline.setFill(Color.WHITE);
        
        //Set the width and height of the CustomClass to the width and height of the overlaying rectangle
        setWidth(wrappingWidth);
        setHeight((numLinesName + numLinesVars + numLinesMethods) * pixelHeight);
        
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
    
    public Text getNameText(){
        return (Text)super.getChildren().get(4);
    }
    
    public Text getVarsText(){
        return (Text)super.getChildren().get(5);
    }
    
    public Text getMethodsText(){
        return (Text)super.getChildren().get(6);
    }
    
    public CustomClass getData(){
        return data;
    }
    
    public void setData(CustomClass c){
        data = c;
    }
    
    public double getStartX() { return startX; }

    public void setStartX(double startX) { this.startX = startX; }

    public double getStartY() { return startY; }

    public void setStartY(double startY) { this.startY = startY; }

    public double getWidth() { return width; }

    public void setWidth(double width) { this.width = width; }

    public double getHeight() { return height; }

    public void setHeight(double height) { this.height = height; }

    public HashMap<String, ConnectorArrayList> getConnections() { return connections; }

    public void setConnections(HashMap<String, ConnectorArrayList> connections) { this.connections = connections; }

    public double getWrappingWidth() { return wrappingWidth; }
    
    public void setWrappingWidth(double w) { wrappingWidth = w; }
    
    //Static getters and setters
    public static double getPixelHeight(){
        return pixelHeight;
    }
    
    public static void setPixelHeight(double ph){
        pixelHeight = ph;
    }
    
    public static Font getFont(){
        return textFont;
    }
    
    public static void setFont(Font f){
        textFont = f;
    }
    
    //TODO: Finish coding this class
}
