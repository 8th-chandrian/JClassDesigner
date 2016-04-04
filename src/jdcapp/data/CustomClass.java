/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Noah
 */
public class CustomClass{
    
    static final String DEFAULT_CLASS_NAME = "DefaultClass";
    static final String DEFAULT_PACKAGE_NAME = "default";
    static final double DEFAULT_WRAPPING_WIDTH = 200;
    public static final String DISPLAY_TEXT_CSS_ID = "display_text";
    static double TEXT_LINE_PIXEL_HEIGHT = 15;
    
    //The class's name and the name of its package
    private String className;
    private String packageName;
    
    //The HashMap of the class's parents, using their names as keys for the HashMap
    private ArrayList<CustomClass> parents;
    
    //The lists of all the variables and methods contained within the class
    private ArrayList<CustomVar> variables;
    private ArrayList<CustomMethod> methods;
    
    //The maximum number of arguments found in the methods contained within this class
    //TODO: figure out if this is a good way to handle the table generation
    private int maxArgs;
    
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
    
    /**
     * Default constructor.
     */
    public CustomClass(double initX, double initY){
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;
        parents = new ArrayList<>();
        variables = new ArrayList<>();
        methods = new ArrayList<>();
        startX = initX;
        startY = initY;
        wrappingWidth = DEFAULT_WRAPPING_WIDTH;
    }
    
    /**
     * Returns an array containing four outlining rectangles and three Text objects.
     * One contains the method name, one contains variables, and one contains methods.
     * @param c
     *      The CustomClass being converted to display formatting.
     * @return 
     *      The array of nodes. Index 0 contains the overall outline rectangle, indices 1, 3, and 5 contain
     *      the Text objects, and indices 2, 4, and 6 contain outline rectangles.
     */
    public static Node[] toDisplay(CustomClass c){
        Node[] displayClass = new Node[7];
        
        Rectangle outline = new Rectangle();
        Text nameText = new Text();
        Text varsText = new Text();
        Text methodsText = new Text();
        
        //Set the id of the Text objects so that the text is formatted by the CSS
        nameText.setId(DISPLAY_TEXT_CSS_ID);
        varsText.setId(DISPLAY_TEXT_CSS_ID);
        methodsText.setId(DISPLAY_TEXT_CSS_ID);
        
        //Create the new 
        nameText.setText(c.getClassName() + "\n");
        nameText.setX(c.getStartX());
        nameText.setY(c.getStartY() + TEXT_LINE_PIXEL_HEIGHT);
        nameText.setWrappingWidth(c.getWrappingWidth());
        int numLinesName = 2;
        
        Rectangle nameOutline = new Rectangle(c.getStartX(), c.getStartY(), c.getWrappingWidth(), 
                (numLinesName) * TEXT_LINE_PIXEL_HEIGHT);
        nameOutline.setStroke(Color.BLACK);
        nameOutline.setStrokeWidth(1);
        nameOutline.setFill(Color.TRANSPARENT);
        
        displayClass[1] = nameOutline;
        displayClass[4] = nameText;
        
        String vars = " \n";
        int numLinesVars = 2;
        if(!c.getVariables().isEmpty()){
            numLinesVars = 1;
            vars = "";
            for(CustomVar v : c.getVariables()){
                vars += v.getVarName() + "\n";
                numLinesVars++;
            }
        }
        varsText.setText(vars);
        varsText.setX(c.getStartX());
        varsText.setY(c.getStartY() + ((numLinesName + 1) * TEXT_LINE_PIXEL_HEIGHT));
        varsText.setWrappingWidth(c.getWrappingWidth());
        
        Rectangle varsOutline = new Rectangle(c.getStartX(), c.getStartY() + (numLinesName * TEXT_LINE_PIXEL_HEIGHT), 
                c.getWrappingWidth(), (numLinesVars) * TEXT_LINE_PIXEL_HEIGHT);
        varsOutline.setStroke(Color.BLACK);
        varsOutline.setStrokeWidth(1);
        varsOutline.setFill(Color.TRANSPARENT);
        
        displayClass[2] = varsOutline;
        displayClass[5] = varsText;
        
        String methods = " \n";
        int numLinesMethods = 2;
        if(!c.getMethods().isEmpty()){
            numLinesMethods = 1;
            methods = "";
            for(CustomMethod m : c.getMethods()){
                methods += m.getMethodName() + "\n";
                numLinesMethods++;
            }
        }
        methodsText.setText(methods);
        methodsText.setX(c.getStartX());
        methodsText.setY(c.getStartY() + ((numLinesName + numLinesVars + 1) * TEXT_LINE_PIXEL_HEIGHT));
        methodsText.setWrappingWidth(c.getWrappingWidth());
        
        Rectangle methodsOutline = new Rectangle(c.getStartX(), c.getStartY() + ((numLinesName + numLinesVars) * TEXT_LINE_PIXEL_HEIGHT), 
                c.getWrappingWidth(), (numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        methodsOutline.setStroke(Color.BLACK);
        methodsOutline.setStrokeWidth(1);
        methodsOutline.setFill(Color.TRANSPARENT);
        
        displayClass[3] = methodsOutline;
        displayClass[6] = methodsText;
        
        outline.setX(c.getStartX());
        outline.setY(c.getStartY());
        outline.setWidth(c.getWrappingWidth());
        outline.setHeight((numLinesName + numLinesVars + numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        outline.setStroke(Color.BLACK);
        outline.setStrokeWidth(1);
        outline.setFill(Color.TRANSPARENT);
        
        displayClass[0] = outline;
        
        c.setWidth(c.getWrappingWidth());
        c.setHeight((numLinesName + numLinesVars + numLinesMethods) * TEXT_LINE_PIXEL_HEIGHT);
        
        return displayClass;
    }
    
    public String getClassName(){
        return className;
    }
    
    public void setClassName(String className){
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public ArrayList<CustomClass> getParents() {
        return parents;
    }

    public ArrayList<CustomVar> getVariables() {
        return variables;
    }

    public ArrayList<CustomMethod> getMethods() {
        return methods;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public HashMap<String, ArrayList<Point2D>> getPoints() {
        return points;
    }

    public void setPoints(HashMap<String, ArrayList<Point2D>> points) {
        this.points = points;
    }
    
    //TODO: Finish coding this class

    private double getWrappingWidth() {
        return wrappingWidth;
    }
    
    public void setWrappingWidth(double w){
        wrappingWidth = w;
    }
}
