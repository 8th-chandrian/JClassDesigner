/*
 * 
 */
package jdcapp.data;

import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import static jdcapp.data.CustomVar.PRIVATE_VAR_ACCESS;
import static jdcapp.data.CustomVar.PUBLIC_VAR_ACCESS;

/**
 * Contains a reference to a CustomClass object as data, and holds all requisite
 * values needed to generate the display for that CustomClass object in the workspace.
 * @author Noah
 */
public class CustomClassWrapper extends CustomBox{
    
    //public static final double DEFAULT_WRAPPING_WIDTH = 200;
    
    public static final String INTERFACE_TEXT_HEADER = "<<interface>>";
    public static final String ABSTRACT_TEXT_HEADER = "<<abstract>>";
    
    //Variables used for setting font size and calculating rectangle width and height.
    //These variables are only changed when zooming, and are reset every time a design
    //is created or loaded, along with zoom.
    static double pixelHeight = 12;
    static double spacingOffset = 1;
    static double maxPixelWidth = 8;
    
    //The text font, to be used with toDisplay (lets us alter the font size when zooming)
    static Font textFont = Font.font("sans-serif", FontWeight.NORMAL, pixelHeight);
    
    //The data contained within this wrapper class
    private CustomClass data;
    
    //The group holding the text objects and rectangles for displaying the class
    private Group display;
    
    //The width and height of the CustomClass
    private double width;
    private double height;
    
    //The maximum number of characters in any line in the display
    private double maxCharacters;

    public CustomClassWrapper(double initX, double initY){
        super(initX, initY);
        data = new CustomClass();
        display = new Group();
        toDisplay();
    }
    
    /**
     * Constructs requisite text objects and rectangles for the display of the CustomClass
     * object in the workspace.
     */
    @Override
    public void toDisplay(){
        //Call clear first, to remove any old display objects from the Group
        display.getChildren().clear();
        double displayX = startX + 3;
        
        Rectangle outline = new Rectangle();
        double lineOffset = 1.5;
        display.getChildren().add(outline);
        
        //Create the name text display
        if(data.isInterface()){
            Text interfaceText = new Text();
            interfaceText.setFont(textFont);
            interfaceText.setText(INTERFACE_TEXT_HEADER);
            interfaceText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            interfaceText.setX(displayX);
            lineOffset++;
            
            Text nameText = new Text();
            nameText.setFont(textFont);
            nameText.setText(data.getClassName());
            nameText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            nameText.setX(displayX);
            lineOffset++;
            
            display.getChildren().add(interfaceText);
            display.getChildren().add(nameText);
            maxCharacters = Math.max(INTERFACE_TEXT_HEADER.length(), data.getClassName().length());
        }
        else if(data.isAbstract()){
            Text abstractText = new Text();
            abstractText.setFont(textFont);
            abstractText.setText(ABSTRACT_TEXT_HEADER);
            abstractText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            abstractText.setX(displayX);
            lineOffset++;
            
            Text nameText = new Text();
            nameText.setFont(textFont);
            nameText.setText(data.getClassName());
            nameText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            nameText.setX(displayX);
            lineOffset++;
            
            display.getChildren().add(abstractText);
            display.getChildren().add(nameText);
            maxCharacters = Math.max(INTERFACE_TEXT_HEADER.length(), data.getClassName().length());
        }
        else{
            Text voidText = new Text();
            Text nameText = new Text();
            nameText.setFont(textFont);
            nameText.setText(data.getClassName());
            nameText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            nameText.setX(displayX);
            lineOffset++;
            
            display.getChildren().add(voidText);
            display.getChildren().add(nameText);
            maxCharacters = data.getClassName().length();
        }
        lineOffset += 1.5;
        
        //Create the vars text display
        for(CustomVar v : data.getVariables()){
            String varsString = "";
            //Add bullets for different access types
            if(v.getAccess().equals(PRIVATE_VAR_ACCESS)){
                varsString += "-";
            }
            else if(v.getAccess().equals(PUBLIC_VAR_ACCESS)){
                varsString += "+";
            }
            else{
                varsString += "#";
            }
            
            //Add static indicator if variable is static
            if(v.isStatic())
                varsString += "$ ";
            
            varsString += v.getVarName() + " : " + v.getVarType();
            Text varsText = new Text();
            varsText.setFont(textFont);
            varsText.setText(varsString);
            varsText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            varsText.setX(displayX);
            lineOffset++;
            
            display.getChildren().add(varsText);
            maxCharacters = Math.max(maxCharacters, varsString.length());
        }
        lineOffset += 1.5;
        
        //Create the methods text display
        for(CustomMethod m : data.getMethods()){
            String methodString = "";
            //Add bullets for different access types
            if(m.getAccess().equals(PRIVATE_VAR_ACCESS)){
                methodString += "-";
            }
            else if(m.getAccess().equals(PUBLIC_VAR_ACCESS)){
                methodString += "+";
            }
            else{
                methodString += "#";
            }
            
            //Add static indicator if variable is static
            if(m.isStatic())
                methodString += "$ ";
            
            methodString += m.getMethodName() + "(";
            for(String arg : m.getArguments()){
                methodString += arg + ", ";
            }
            if(!m.getArguments().isEmpty())
                methodString = methodString.substring(0, methodString.length() - 2);
            methodString += ")";
            if(!m.isConstructor())
                methodString += " : " + m.getReturnType();
            
            Text methodsText = new Text();
            methodsText.setFont(textFont);
            methodsText.setText(methodString);
            methodsText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
            methodsText.setX(displayX);
            lineOffset++;
            
            display.getChildren().add(methodsText);
            maxCharacters = Math.max(maxCharacters, methodString.length());
        }
        lineOffset += .5;
        
        //Create the overlaying rectangle
        outline.setX(startX);
        outline.setY(startY);
        outline.setWidth(maxCharacters * maxPixelWidth);
        outline.setHeight(lineOffset * (pixelHeight + spacingOffset));
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(1);
        outline.setFill(Color.WHITE);
        
        //Set the width and height of the CustomClass to the width and height of the overlaying rectangle
        width = maxCharacters * maxPixelWidth;
        height = (lineOffset) * (pixelHeight + spacingOffset);
    }
    
    @Override
    public void highlight(Effect e) {
        display.getChildren().get(0).setEffect(e);
    }
    
    @Override
    public Shape getOutlineShape(){
        return (Rectangle)display.getChildren().get(0);
    }
 
    @Override
    public Group getDisplay(){ return display; }
    
    @Override
    public Text getNameText(){
        return (Text)display.getChildren().get(2);
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
    
    public static double getMaxPixelWidth(){
        return maxPixelWidth;
    }
    
    public static void setMaxPixelWidth(double p){
        maxPixelWidth = p;
    }

    
    //TODO: Finish coding this class
}
