/*
 * 
 */
package jdcapp.data;

import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Noah
 */
public class CustomImport extends CustomBox{
    
    public static final String DEFAULT_PACKAGE_NAME = "default_package";
    
    private String importName;
    private String packageName;
    
    //Variables used for setting font size and calculating rectangle width and height.
    //These variables are only changed when zooming, and are reset every time a design
    //is created or loaded, along with zoom.
    static double pixelHeight = 12;
    static double spacingOffset = 1;
    static double maxPixelWidth = 8;
    
    //The text font, to be used with toDisplay (lets us alter the font size when zooming)
    static Font textFont = Font.font("sans-serif", FontWeight.NORMAL, pixelHeight);
    
    private double width;
    private double height;
    
    private Group display;
    
    public CustomImport(double initX, double initY, String importName){
        super(initX, initY);
        this.importName = importName;
        packageName = DEFAULT_PACKAGE_NAME;
        display = new Group();
        toDisplay();
    }
    
    @Override
    public void toDisplay() {
        //Call clear first, to remove any old display objects from the Group
        display.getChildren().clear();
        double displayX = startX + 3;
        
        Rectangle outline = new Rectangle();
        double lineOffset = 1.5;
        display.getChildren().add(outline);
        
        Text nameText = new Text();
        nameText.setFont(textFont);
        nameText.setText(importName);
        nameText.setY(startY + lineOffset * (pixelHeight + spacingOffset));
        nameText.setX(displayX);
        
        display.getChildren().add(nameText);
        
        //Create the overlaying rectangle
        outline.setX(startX);
        outline.setY(startY);
        outline.setWidth(importName.length() * maxPixelWidth);
        outline.setHeight(lineOffset * (pixelHeight + spacingOffset));
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(1);
        outline.setFill(Color.WHITE);
        
        width = importName.length() * maxPixelWidth;
        height = (lineOffset) * (pixelHeight + spacingOffset);
    }

    @Override
    public void highlight(Effect e) {
        display.getChildren().get(0).setEffect(e);
    }
    
    @Override
    public Rectangle getOutlineRectangle(){
        return (Rectangle)display.getChildren().get(0);
    }
    
    @Override
    public Group getDisplay(){
        return display;
    }
    
    @Override
    public Text getNameText(){
        return (Text)display.getChildren().get(1);
    }
    
    @Override
    public double getStartX(){
        return startX;
    }
    
    @Override
    public double getStartY(){
        return startY;
    }
    
    public String getPackageName(){
        return packageName;
    }
    
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }
    
    public String getImportName(){
        return importName;
    }
    
    public void setImportName(String i){
        importName = i;
    }
}
