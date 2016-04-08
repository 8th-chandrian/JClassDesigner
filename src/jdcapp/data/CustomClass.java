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
    
    static final String DEFAULT_CLASS_NAME = "";
    static final String DEFAULT_PACKAGE_NAME = "";
    
    //The class's name and the name of its package
    private String className;
    private String packageName;
    
    //The HashMap of the class's parents, using their names as keys for the HashMap
    private ArrayList<CustomClass> parents;
    
    //The lists of all the variables and methods contained within the class
    private ArrayList<CustomVar> variables;
    private ArrayList<CustomMethod> methods;
    
    /**
     * Default constructor.
     */
    public CustomClass(){
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;
        parents = new ArrayList<>();
        variables = new ArrayList<>();
        methods = new ArrayList<>();
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

}
