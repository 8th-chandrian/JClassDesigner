/*
 * 
 */
package jdcapp.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jdcapp.data.ConnectorArrayList;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomMethod;
import jdcapp.data.CustomVar;
import jdcapp.data.DataManager;
import static jdcapp.settings.AppPropertyType.WORK_FILE_EXT;
import org.apache.commons.lang3.StringUtils;
import properties_manager.PropertiesManager;

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
    static final String JSON_CONNECTIONS = "connections";
    
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
    static final String JSON_POINT_ARRAY = "point_array";
    static final String JSON_CONNECTOR_TYPE = "connector_type";
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
    
    
    public static final int CODE_EXPORTED_SUCCESSFULLY = 0;
    public static final int ERROR_CREATING_DIRECTORIES = -1;
    public static final int ERROR_CREATING_JAVA_FILES = -2;
    
    
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    //
    //          CLASSES USED FOR SAVING DATA
    //
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    
    
    /**
     * Saves the data in the DataManager to a .json file at the given path.
     * @param dataManager
     *      The DataManager to save from
     * @param filePath 
     *      The file path to save to
     */
    public void saveData(DataManager dataManager, String filePath) throws IOException{
        
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
            JsonArray connectionsHashMapJsonArray = makeHashMapJsonObject(c.getConnections());
            
            //Then build the JsonObject to save
            JsonObject wrapperJson = Json.createObjectBuilder()
                    .add(JSON_START_X, startX)
                    .add(JSON_START_Y, startY)
                    .add(JSON_WIDTH, width)
                    .add(JSON_HEIGHT, height)
                    .add(JSON_WRAPPING_WIDTH, wrappingWidth)
                    .add(JSON_CUSTOM_CLASS, customClassJson)
                    .add(JSON_CONNECTIONS, connectionsHashMapJsonArray)
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
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath + PropertiesManager.getPropertiesManager().getProperty(WORK_FILE_EXT));
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath + PropertiesManager.getPropertiesManager().getProperty(WORK_FILE_EXT));
	pw.write(prettyPrinted);
	pw.close();
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
     * Helper method which converts a HashMap of Strings (representing connected classes) 
     * mapped to ArrayLists of Point2D objects (representing the points connecting the current class
     * and its given connected class) to a JsonArray
     * 
     * @param points
     *      The HashMap to be converted
     * @return 
     *      The converted map in JsonArray form
     */
    private JsonArray makeHashMapJsonObject(HashMap<String, ConnectorArrayList> points) {
        
        //JsonObjects containing paired keys and ArrayLists of points will go in this array
        JsonArrayBuilder hashArrayBuilder = Json.createArrayBuilder();
        
        //Get an iterator of all the keys (connected class names) in the HashMap
        Iterator<String> keyIterator = points.keySet().iterator();
        while(keyIterator.hasNext()){
            
            //Get the key
            String key = (String)keyIterator.next();
            
            //Get the ArrayList associated with that key and convert to a JsonArray
            ArrayList<Point2D> pointArray = points.get(key);
            JsonArray pointJsonArray = makePointJsonArray(pointArray);
            String connectorType = ((ConnectorArrayList) pointArray).getConnectorType();
            
            //Create the object containing the paired key and array
            JsonObject hashPairJson = Json.createObjectBuilder()
                    .add(JSON_KEY, key)
                    .add(JSON_POINT_ARRAY, pointJsonArray)
                    .add(JSON_CONNECTOR_TYPE, connectorType)
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
    
    
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    //
    //          CLASSES USED FOR LOADING DATA
    //
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    
    /**
     * Loads the data from the given filePath String into the dataManager specified
     * @param dataManager
     *      The DataManager that the data will be loaded into
     * @param filePath
     *      The location of the .json file to load from
     * @throws IOException 
     *      Thrown if loadJSONFile encounters an error
     */
    public void loadData(DataManager dataManager, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	dataManager.reset();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
	
	// Load the font data
	String fontName = json.getString(JSON_FONT_NAME);
        double pixelHeight = getDataAsDouble(json, JSON_PIXEL_HEIGHT);
        Font textFont = Font.font(fontName, FontWeight.NORMAL, pixelHeight);
        
	CustomClassWrapper.setFont(textFont);
        CustomClassWrapper.setPixelHeight(pixelHeight);
	
	// Load the array of CustomClassWrappers
	JsonArray jsonClassArray = json.getJsonArray(JSON_CLASS_ARRAY);
	for (int i = 0; i < jsonClassArray.size(); i++) {
	    JsonObject jsonCustomClassWrapper = jsonClassArray.getJsonObject(i);
	    CustomClassWrapper c = loadCustomClassWrapper(jsonCustomClassWrapper);
	    dataManager.getClasses().add(c);
	}
    }
    
    private CustomClassWrapper loadCustomClassWrapper(JsonObject j){
        //Load in X and Y values and instantiate the CustomClassWrapper
        double startX = getDataAsDouble(j, JSON_START_X);
        double startY = getDataAsDouble(j, JSON_START_Y);
        CustomClassWrapper c = new CustomClassWrapper(startX, startY);
        
        //Load in width, height, and wrapping width
        c.setWidth(getDataAsDouble(j, JSON_WIDTH));
        c.setHeight(getDataAsDouble(j, JSON_HEIGHT));
        c.setWrappingWidth(getDataAsDouble(j, JSON_WRAPPING_WIDTH));
        
        //Load in CustomClass data
        c.setData(loadCustomClass(j.getJsonObject(JSON_CUSTOM_CLASS)));
        
        //Load in HashMap of connections
        c.setConnections(loadConnections(j.getJsonArray(JSON_CONNECTIONS)));
        return c;
    }
    
    private CustomClass loadCustomClass(JsonObject j){
        CustomClass c = new CustomClass();
        c.setClassName(j.getString(JSON_CLASS_NAME));
        c.setPackageName(j.getString(JSON_PACKAGE_NAME));
        c.setInterfaceValue(j.getBoolean(JSON_INTERFACE_VALUE));
        c.setAbstractValue(j.getBoolean(JSON_ABSTRACT_VALUE));
        
        c.setParents(loadParents(j.getJsonArray(JSON_PARENTS)));
        c.setVariables(loadVariables(j.getJsonArray(JSON_VARIABLES)));
        c.setMethods(loadMethods(j.getJsonArray(JSON_METHODS)));
        
        return c;
    }
    
    private ArrayList<String> loadParents(JsonArray j){
        ArrayList<String> parents = new ArrayList<>();
        
        for(int i = 0; i < j.size(); i++){
            String parentString = j.getString(i);
            parents.add(parentString);
        }
        
        return parents;
    } 
    
    private ArrayList<CustomVar> loadVariables(JsonArray j){
        ArrayList<CustomVar> vars = new ArrayList<>();
        
        for(int i = 0; i < j.size(); i++){
            JsonObject varJson = j.getJsonObject(i);
            CustomVar v = loadCustomVar(varJson);
            vars.add(v);
        }
        
        return vars;
    }
    
    private CustomVar loadCustomVar(JsonObject varJson){
        CustomVar var = new CustomVar();
        
        var.setVarName(varJson.getString(JSON_VAR_NAME));
        var.setVarType(varJson.getString(JSON_VAR_TYPE));
        var.setAccess(varJson.getString(JSON_VAR_ACCESS));
        var.setStaticValue(varJson.getBoolean(JSON_VAR_STATIC_VALUE));
        
        return var;
    }
    
    private ArrayList<CustomMethod> loadMethods(JsonArray j){
        ArrayList<CustomMethod> methods = new ArrayList<>();
        
        for(int i = 0; i < j.size(); i++){
            JsonObject methodJson = j.getJsonObject(i);
            CustomMethod m = loadCustomMethod(methodJson);
            methods.add(m);
        }
        
        return methods;
    }
    
    private CustomMethod loadCustomMethod(JsonObject methodJson){
        CustomMethod m = new CustomMethod();
        
        m.setMethodName(methodJson.getString(JSON_METHOD_NAME));
        m.setReturnType(methodJson.getString(JSON_METHOD_RETURN));
        m.setAccess(methodJson.getString(JSON_METHOD_ACCESS));
        m.setAbstractValue(methodJson.getBoolean(JSON_METHOD_ABSTRACT_VALUE));
        m.setStaticValue(methodJson.getBoolean(JSON_METHOD_STATIC_VALUE));
        
        JsonArray argsJson = methodJson.getJsonArray(JSON_METHOD_ARGUMENTS);
        ArrayList<String> args = new ArrayList<>();
        for(int i = 0; i < argsJson.size(); i++){
            String arg = argsJson.getString(i);
            args.add(arg);
        }
        
        m.setArguments(args);
        return m;
    }
    
    private HashMap<String, ConnectorArrayList> loadConnections(JsonArray j){
        HashMap<String, ConnectorArrayList> connections = new HashMap<>();
        
        for(int i = 0; i < j.size(); i++){
            JsonObject connectionJson = j.getJsonObject(i);
            String key = connectionJson.getString(JSON_KEY);
            
            JsonArray pointsArrayJson = connectionJson.getJsonArray(JSON_POINT_ARRAY);
            String connectorType = connectionJson.getString(JSON_CONNECTOR_TYPE);
            ConnectorArrayList connectionPoints = new ConnectorArrayList(connectorType);
            for(int k = 0; k < pointsArrayJson.size(); k++){
                double pointX = getDataAsDouble(pointsArrayJson.getJsonObject(k), JSON_POINT_X);
                double pointY = getDataAsDouble(pointsArrayJson.getJsonObject(k), JSON_POINT_Y);
                Point2D point = new Point2D(pointX, pointY);
                connectionPoints.add(point);
            }
            
            connections.put(key, connectionPoints);
        }
        
        return connections;
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    private double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    //
    //          CLASSES USED FOR EXPORTING DATA AS CODE
    //
    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    
    /**
     * Exports the current design to code. 
     * @param dataManager
     * @param filePath
     * @return 
     *       0 if the code was exported correctly.
     *      -1 if there was an error creating the package directories.
     *      -2 if there was an error creating the java files.
     */
    public int exportCode(DataManager dataManager, String filePath){
        ArrayList<CustomClassWrapper> classes = dataManager.getClasses();
        
        //First, create all necessary packages to put our java files in.
        //Get every package contained in the current design.
        ArrayList<String> packages = getAllPackages(classes);
        
        //Then make all the package directories
        boolean makePackagesReturn = makePackageDirectories(packages, filePath);
        if(!makePackagesReturn)
            return ERROR_CREATING_DIRECTORIES;
        
        //Then put all the java files in the packages
        boolean makeJavaFilesReturn = makeJavaFiles(classes, filePath);
        if(!makeJavaFilesReturn)
            return ERROR_CREATING_JAVA_FILES;
    }
    
    /**
     * Helper method which extracts an ArrayList of packages from an ArrayList of 
     * CustomClassWrappers.
     * 
     * @param classes
     * @return 
     *      The ArrayList of packages
     */
    private ArrayList<String> getAllPackages(ArrayList<CustomClassWrapper> classes){
        ArrayList<String> packages = new ArrayList<>();
        for(CustomClassWrapper c : classes){
            String packageName = c.getData().getPackageName();
            
            //If packageName is invalid, set packageName to the default package name
            if(packageName.equals("") || packageName.equals(null))
                packageName = c.getData().DEFAULT_PACKAGE_NAME;
            
            if(!packages.contains(packageName)){
                packages.add(packageName);
            }
        }
        return packages;
    }
    
    /**
     * Helper method to make the package directories.
     * @param packages
     * @return 
     *      True if the directories were created successfully, false otherwise.
     */
    private boolean makePackageDirectories(ArrayList<String> packages, String filePath){
        
        //Sort the package strings based on how many subdirectories they have
        //Ex: "jdcapp.data" has 1 more subdirectory than "jdcapp"
        Collections.sort(packages, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                int s1Subdirectories = StringUtils.countMatches(s1, ".");
                int s2Subdirectories = StringUtils.countMatches(s1, ".");
                if(s1Subdirectories > s2Subdirectories)
                    return 1;
                else if(s1Subdirectories == s2Subdirectories)
                    return 0;
                else
                    return -1;
            }
        });
        
        //Now make the directories. Because of the sorting, they should be made in
        //order so that no subdirectory is made without its parent directory already
        //existing.
        for(String packageName : packages){
            packageName = packageName.replaceAll(".", "/");
            String filePathPackage = filePath + "/" + packageName;
            
            boolean success = (new File(filePathPackage)).mkdirs();
            if(!success){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Helper method to make all the .java files and put them in their respective
     * directories.
     * @param classes
     * @param filePath
     * @return 
     *      True if the files were created successfully, false otherwise.
     */
    private boolean makeJavaFiles(ArrayList<CustomClassWrapper> classes, String filePath){
        for(CustomClassWrapper c : classes){
            String classFilePath = getJavaFilePath(c.getData(), filePath);
            
            //As we traverse our arrays of CustomMethods and CustomVariables, we will
            //add strings to this ArrayList to account for any classes that might need
            //to be imported
            ArrayList<String> imports = new ArrayList<>();
            
            //The methods and variables ArrayLists will hold our processed methods and variables,
            //ready to be written to our java file
            ArrayList<String> methods = new ArrayList<>();
            ArrayList<String> variables = new ArrayList<>();
            
            //Process the methods
            for(CustomMethod m : c.getData().getMethods()){
                if(!checkMethod(m))
                    return false;
                methods.add(processMethod(m));
                ArrayList<String> importTextArray = getMethodImports(m);
                if(importTextArray != null)
                    imports.addAll(importTextArray);
            }
            
            //Process the variables
            for(CustomVar v : c.getData().getVariables()){
                if(!checkVariable(v))
                    return false;
                variables.add(processVariable(v));
                ArrayList<String> importTextArray = getVariableImports(v);
                if(importTextArray != null)
                    imports.addAll(importTextArray);
            }
            
            //Export the imports, variables, and methods to a new file with address classFilePath
        }
    }
    
    /**
     * Helper method to get the properly-formatted file path of a .java file from
     * its CustomClassWrapper object and the directory file path.
     * @param c
     * @param filePath
     * @return 
     *      The properly-formatted file path
     */
    private String getJavaFilePath(CustomClass c, String filePath){
        String javaFilePath = filePath;
        
        //Get the path to the directory in which the file will be put
        String packagePath = c.getPackageName().replaceAll(".", "/");
        javaFilePath += packagePath;
        
        javaFilePath = javaFilePath + c.getClassName() + ".java";
        return javaFilePath;
    }
}
