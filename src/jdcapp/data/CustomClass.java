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
    private ObservableList<CustomVar> variables;
    private ObservableList<CustomMethod> methods;
    
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
    
    //Compares variables based on their names. Used to sort variables in alphabetical order.
    static final Comparator<CustomVar> varComparator = new Comparator() {
        @Override
        public int compare(Object c1, Object c2) {
            return (((CustomVar)c1).getVarName()).compareTo(((CustomVar)c2).getVarName());
        }
    };
    
    //Compares methods based on their names. Used to sort methods in alphabetical order.
    static final Comparator<CustomMethod> methodComparator = new Comparator() {
        @Override
        public int compare(Object c1, Object c2) {
            return (((CustomMethod)c1).getMethodName()).compareTo(((CustomMethod)c2).getMethodName());
        }
    };
    
    /**
     * Default constructor.
     */
    public CustomClass(double initX, double initY){
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;
        parents = new HashMap<>();
        variables = variables.sorted(varComparator);
        methods = methods.sorted(methodComparator);
        startX = initX;
        startY = initY;
        width = DEFAULT_CLASS_WIDTH;
        height = DEFAULT_CLASS_HEIGHT;
    }
    //TODO: FINISH CODING CLASS
}
