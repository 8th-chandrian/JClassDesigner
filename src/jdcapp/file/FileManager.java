/*
 * 
 */
package jdcapp.file;

import java.math.BigDecimal;
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
    
    static final String JSON_VAR_NAME = "var_name";
    static final String JSON_VAR_TYPE = "var_type";
    static final String JSON_VAR_STATIC_VALUE = "var_static_value";
    static final String JSON_VAR_ACCESS = "var_access";
    
    static final String JSON_METHOD_NAME = "method_name";
    static final String JSON_METHOD_RETURN = "method_return";
    static final String JSON_METHOD_STATIC_VALUE = "method_static_value";
    static final String JSON_METHOD_ABSTRACT_VALUE = "method_abstract_value";
    static final String JSON_METHOD_ACCESS = "method_access";
    static final String JSON_METHOD_ARGUMENTS = "method_arguments";
    
    
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
     * 
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

    /**
     * Helper method which converts an ArrayList of strings representing parent classes
     * to a JsonArray
     * 
     * @param parents
     *      The ArrayList to be converted
     * @return 
     *      The ArrayList in JsonArray format
     */
    private JsonArray makeParentsJsonArray(ArrayList<String> parents) {
        JsonArrayBuilder parentArrayBuilder = Json.createArrayBuilder();
        
        for(String parentString : parents){
            parentArrayBuilder.add(parentString);
        }
        
        JsonArray parentArrayJson = parentArrayBuilder.build();
        return parentArrayJson;
    }

    /**
     * Helper method which converts an ArrayList of CustomVar objects to a JsonArray
     * 
     * @param variables
     *      The ArrayList to be converted
     * @return 
     *      The ArrayList in JsonArray format
     */
    private JsonArray makeVariablesJsonArray(ArrayList<CustomVar> variables) {
        JsonArrayBuilder variableArrayBuilder = Json.createArrayBuilder();
        
        for(CustomVar v : variables){
            JsonObject variableJson = makeCustomVariableJsonObject(v);
            variableArrayBuilder.add(variableJson);
        }
        
        JsonArray variableArrayJson = variableArrayBuilder.build();
        return variableArrayJson;
    }
    
    /**
     * Helper method which converts a CustomVar object to a JsonObject
     * 
     * @param v
     *      The CustomVar object to be converted
     * @return 
     *      The CustomVar object in JsonObject format
     */
    private JsonObject makeCustomVariableJsonObject(CustomVar v) {
        String name = v.getVarName();
        String type = v.getVarType();
        boolean staticValue = v.isStatic();
        String access = v.getAccess();
        
        JsonObject customVarJsonObject = Json.createObjectBuilder()
                .add(JSON_VAR_NAME, name)
                .add(JSON_VAR_TYPE, type)
                .add(JSON_VAR_STATIC_VALUE, staticValue)
                .add(JSON_VAR_ACCESS, access)
                .build();
        return customVarJsonObject;
    }

    /**
     * Helper method which converts an ArrayList of CustomVar objects to a JsonArray
     * 
     * @param methods
     *      The ArrayList to be converted
     * @return 
     *      The ArrayList in JsonArray format
     */
    private JsonArray makeMethodsJsonArray(ArrayList<CustomMethod> methods) {
        JsonArrayBuilder methodArrayBuilder = Json.createArrayBuilder();
        
        for(CustomMethod m : methods){
            JsonObject methodJson = makeCustomMethodJsonObject(m);
            methodArrayBuilder.add(methodJson);
        }
        
        JsonArray methodArrayJson = methodArrayBuilder.build();
        return methodArrayJson;
    }

    /**
     * Helper method which converts a CustomMethod object to a JsonObject
     * 
     * @param m
     *      The CustomMethod object to be converted
     * @return 
     *      The CustomMethod object in JsonObject format
     */
    private JsonObject makeCustomMethodJsonObject(CustomMethod m) {
        String name = m.getMethodName();
        String returnType = m.getReturnType();
        boolean staticValue = m.isStatic();
        boolean abstractValue = m.isAbstract();
        String access = m.getAccess();
        
        JsonArrayBuilder argsArrayBuilder = Json.createArrayBuilder();
        for(String arg : m.getArguments()){
            argsArrayBuilder.add(arg);
        }
        JsonArray argsArrayJson = argsArrayBuilder.build();
        
        JsonObject customMethodJsonObject = Json.createObjectBuilder()
                .add(JSON_METHOD_NAME, name)
                .add(JSON_METHOD_RETURN, returnType)
                .add(JSON_METHOD_STATIC_VALUE, staticValue)
                .add(JSON_METHOD_ABSTRACT_VALUE, abstractValue)
                .add(JSON_METHOD_ACCESS, access)
                .add(JSON_METHOD_ARGUMENTS, argsArrayJson)
                .build();
        return customMethodJsonObject;
    }
}
