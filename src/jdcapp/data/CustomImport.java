/*
 * 
 */
package jdcapp.data;

import javafx.scene.Group;
import javafx.scene.effect.Effect;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Noah
 */
public class CustomImport extends CustomBox{
    
    private String importName;
    private String packageName;
    
    private double width;
    private double height;
    
    private Group display;
    
    public CustomImport(double initX, double initY, String importName){
        super(initX, initY);
        this.importName = importName;
        display = new Group();
        toDisplay();
    }
    
    @Override
    public void toDisplay() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void highlight(Effect e) {
        display.getChildren().get(0).setEffect(e);
    }
    
    @Override
    public Rectangle getOutlineRectangle(){
        return (Rectangle)display.getChildren().get(0);
    }
    
    public String getPackageName(){
        return packageName;
    }
    
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }
    
}
