/*
 * 
 */
package jdcapp.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;
import jdcapp.data.DataManager;

/**
 *
 * @author Noah
 */
public class FileManager {
    
    static final String JSON_START_X = "start_x";
    static final String JSON_START_Y = "start_y";
    static final String JSON_WIDTH = "width";
    static final String JSON_HEIGHT = "height";
    static final String JSON_WRAPPING_WIDTH = "wrapping_width";
    static final String JSON_CUSTOM_CLASS = "custom_class";
    static final String JSON_POINTS_HASH_MAP = "points_hash_map";
    
    static final String JSON_CLASS_NAME = "class_name";
    static final String JSON_PACKAGE_NAME = "package_name";
    static final String JSON_INTERFACE_VALUE = "interface_value";
    static final String JSON_ABSTRACT_VALUE = "abstract_value";
    static final String JSON_PARENTS = "parents";
    static final String JSON_VARIABLES = "variables";
    static final String JSON_METHODS = "methods";
    
    static final String JSON_FONT_NAME = "font_name";
    static final String JSON_PIXEL_HEIGHT = "pixel_height";
    static final String JSON_CLASS_ARRAY = "class_array";
    
    static final String JSON_KEY = "key";
    static final String JSON_POINT_ARRAY = "points_array";
    static final String JSON_POINT_X = "point_x";
    static final String JSON_POINT_Y = "point_y";
    
    /**
     * Saves the data in the DataManager to a .json file at the given path.
     * @param dataManager
     *      The DataManager to save from
     * @param path 
     *      The file path to save to
     */
    public void saveData(DataManager dataManager, String path) {
        
        //Get the font name and pixel height, which will be needed to recreate the 
        //static variables in CustomClassWrapper
        String fontName = CustomClassWrapper.getFont().getName();
        double pixelHeight = CustomClassWrapper.getPixelHeight();
        
        JsonArrayBuilder classArrayBuilder = Json.createArrayBuilder();
        for(CustomClassWrapper c : dataManager.getClasses()){
            
            //Get all instance variables from the CustomClassWrapper object
            double startX = c.getStartX();
            double startY = c.getStartY();
            double width = c.getWidth();
            double height = c.getHeight();
            double wrappingWidth = c.getWrappingWidth();
            JsonObject customClassJson = makeCustomClassJsonObject(c.getData());
            JsonArray pointsHashMapJsonArray = makeHashMapJsonObject(c.getPoints());
            
            //Then build the JsonObject to save
            JsonObject wrapperJson = Json.createObjectBuilder()
                    .add(JSON_START_X, startX)
                    .add(JSON_START_Y, startY)
                    .add(JSON_WIDTH, width)
                    .add(JSON_HEIGHT, height)
                    .add(JSON_WRAPPING_WIDTH, wrappingWidth)
                    .add(JSON_CUSTOM_CLASS, customClassJson)
                    .add(JSON_POINTS_HASH_MAP, pointsHashMapJsonArray)
                    .build();
            
            //Add it to the array of classes
            classArrayBuilder.add(wrapperJson);
        }
        
        //Build the array of classes
        JsonArray classArray = classArrayBuilder.build();
        
        //Create the final JsonObject containing the entire DataManager
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_FONT_NAME, fontName)
                .add(JSON_PIXEL_HEIGHT, pixelHeight)
                .add(JSON_CLASS_ARRAY, classArray)
                .build();
    }
    
    /**
     * Helper method which converts a CustomClass to a JsonObject
     * @param c
     *      The CustomClass to be converted
     * @return 
     *      The CustomClass in JsonObject format
     */
    private JsonObject makeCustomClassJsonObject(CustomClass c){
        String className = c.getClassName();
        String packageName = c.getPackageName();
        boolean interfaceValue = c.isInterface();
        boolean abstractValue = c.isAbstract();
        JsonArray parents = makeParentsJsonArray(c.getParents());
        JsonArray variables = makeVariablesJsonArray(c.getVariables());
        JsonArray methods = makeMethodsJsonArray(c.getMethods());
        
        JsonObject customClassJson = Json.createObjectBuilder()
                .add(JSON_CLASS_NAME, className)
                .add(JSON_PACKAGE_NAME, packageName)
                .add(JSON_INTERFACE_VALUE, interfaceValue)
                .add(JSON_ABSTRACT_VALUE, abstractValue)
                .add(JSON_PARENTS, parents)
                .add(JSON_VARIABLES, variables)
                .add(JSON_METHODS, methods)
                .build();
        
        return customClassJson;
    }

    /**
     * Helper method which converts a HashMap of Strings (representing parents) mapped to 
     * ArrayLists of Point2D objects (representing the points connecting the current class
     * and its given parent) to a JsonArray
     * 
     * @param points
     *      The HashMap to be converted
     * @return 
     *      The converted map in JsonArray form
     */
    private JsonArray makeHashMapJsonObject(HashMap<String, ArrayList<Point2D>> points) {
        
        //JsonObjects containing paired keys and ArrayLists of points will go in this array
        JsonArrayBuilder hashArrayBuilder = Json.createArrayBuilder();
        
        //Get an iterator of all the keys (parent strings) in the HashMap
        Iterator keyIterator = points.keySet().iterator();
        while(keyIterator.hasNext()){
            
            //Get the key
            String key = (String)keyIterator.next();
            
            //Get the ArrayList associated with that key and convert to a JsonArray
            ArrayList<Point2D> pointArray = points.get(key);
            JsonArray pointJsonArray = makePointJsonArray(pointArray);
            
            //Create the object containing the paired key and array
            JsonObject hashPairJson = Json.createObjectBuilder()
                    .add(JSON_KEY, key)
                    .add(JSON_POINT_ARRAY, pointJsonArray)
                    .build();
            
            //Add that object to the array builder
            hashArrayBuilder.add(hashPairJson);
        }
        
        JsonArray hashArray = hashArrayBuilder.build();
        return hashArray;
    }
    
    /**
     * Helper method which converts an ArrayList of Point2D objects to a JsonArray
     * @param pointArray
     *      The ArrayList to be converted
     * @return 
     *      The ArrayList in JsonArray format
     */
    private JsonArray makePointJsonArray(ArrayList<Point2D> pointArray) {
        JsonArrayBuilder pointArrayBuilder = Json.createArrayBuilder();
        
        //Iterate through every point in the array
        for(Point2D p : pointArray){
            
            //Get the x and y coordinates of the point and add them to a new JsonObject
            double pointX = p.getX();
            double pointY = p.getY();
            JsonObject pointJson = Json.createObjectBuilder()
                    .add(JSON_POINT_X, pointX)
                    .add(JSON_POINT_Y, pointY)
                    .build();
            
            pointArrayBuilder.add(pointJson);
        }
        
        JsonArray pointArrayJson = pointArrayBuilder.build();
        return pointArrayJson;
    }

    private JsonArray makeParentsJsonArray(ArrayList<String> parents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private JsonArray makeVariablesJsonArray(ArrayList<CustomVar> variables) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private JsonArray makeMethodsJsonArray(ArrayList<CustomMethod> methods) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
