/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

/**
 *
 * @author Noah
 */
public class CustomClass{
    
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
    
    
    //TODO: FINISH CODING CLASS
}
