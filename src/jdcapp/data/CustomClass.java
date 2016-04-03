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

/**
 *
 * @author Noah
 */
public class CustomClass{
    
    static final String DEFAULT_CLASS_NAME = "DefaultClass";
    static final String DEFAULT_PACKAGE_NAME = "default";
    static final double DEFAULT_CLASS_WIDTH = 100;
    static final double DEFAULT_CLASS_HEIGHT = 50;
    
    //The class's name and the name of its package
    private String className;
    private String packageName;
    
    //The HashMap of the class's parents, using their names as keys for the HashMap
    private HashMap<String, CustomClass> parents;
    
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
    
    //The HashMap containing lists of points on the lines connecting this class and its parents
    private HashMap<String, ArrayList<Point2D>> points;
    
    /**
     * Default constructor.
     */
    public CustomClass(double initX, double initY){
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;
        parents = new HashMap<>();
        variables = new ArrayList<>();
        methods = new ArrayList<>();
        startX = initX;
        startY = initY;
        width = DEFAULT_CLASS_WIDTH;
        height = DEFAULT_CLASS_HEIGHT;
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

    public HashMap<String, CustomClass> getParents() {
        return parents;
    }

    public void setParents(HashMap<String, CustomClass> parents) {
        this.parents = parents;
    }

    public ArrayList<CustomVar> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<CustomVar> variables) {
        this.variables = variables;
    }

    public ArrayList<CustomMethod> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<CustomMethod> methods) {
        this.methods = methods;
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
}
