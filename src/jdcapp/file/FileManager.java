/*
 * 
 */
package jdcapp.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import jdcapp.data.CustomBox;
import jdcapp.data.CustomClass;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.CustomImport;
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
    static final String JSON_CUSTOM_CLASS = "custom_class";
    
    static final String JSON_CLASS_NAME = "class_name";
    static final String JSON_PACKAGE_NAME = "package_name";
    static final String JSON_INTERFACE_VALUE = "interface_value";
    static final String JSON_ABSTRACT_VALUE = "abstract_value";
    static final String JSON_EXTENDED_CLASS = "extended_class";
    static final String JSON_IMPLEMENTED_CLASSES = "implemented_classes";
    static final String JSON_VARIABLES = "variables";
    static final String JSON_METHODS = "methods";
    
    static final String JSON_FONT_NAME = "font_name";
    static final String JSON_PIXEL_HEIGHT = "pixel_height";
    static final String JSON_MAX_PIXEL_WIDTH = "max_pixel_width";
    static final String JSON_CLASS_ARRAY = "class_array";
    
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
    
    public static final String JAVA_FILE_EXTENSION = ".java";
    
    
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
        
        JsonArrayBuilder classArrayBuilder = Json.createArrayBuilder();
        
        String fontName = CustomClassWrapper.getFont().getName();
        double pixelHeight = CustomClassWrapper.getPixelHeight();
        double maxPixelWidth = CustomClassWrapper.getMaxPixelWidth();
        
        for(CustomBox c : dataManager.getClasses()){
            
            //Get all instance variables from the CustomClassWrapper object
            double startX = c.getStartX();
            double startY = c.getStartY();
            JsonObject customClassJson;
            if(c instanceof CustomClassWrapper)
                customClassJson = makeCustomClassJsonObject(((CustomClassWrapper)c).getData());
            else
                customClassJson = makeCustomImportJsonObject((CustomImport)c);
            
            //Then build the JsonObject to save
            JsonObject wrapperJson = Json.createObjectBuilder()
                    .add(JSON_START_X, startX)
                    .add(JSON_START_Y, startY)
                    .add(JSON_CUSTOM_CLASS, customClassJson)
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
                .add(JSON_MAX_PIXEL_WIDTH, maxPixelWidth)
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

	//INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath + "json");
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath + "json");
	pw.write(prettyPrinted);
	pw.close();
    }
    
    /**
     * Helper method which converts a CustomImport to a JsonObject
     * @param c
     *      The CustomImport to be converted
     * @return 
     *      The CustomImport in JsonObject format
     */
    private JsonObject makeCustomImportJsonObject(CustomImport c){
        String className = c.getImportName();
        String packageName = c.getPackageName();
        
        JsonObject customImportJson = Json.createObjectBuilder()
                .add(JSON_CLASS_NAME, className)
                .add(JSON_PACKAGE_NAME, packageName)
                .build();
        return customImportJson;
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
        String extendedClass = c.getExtendedClass();
        JsonArray implementedClasses = makeImplementedClassesJsonArray(c.getImplementedClasses());
        JsonArray variables = makeVariablesJsonArray(c.getVariables());
        JsonArray methods = makeMethodsJsonArray(c.getMethods());
        
        JsonObject customClassJson = Json.createObjectBuilder()
                .add(JSON_CLASS_NAME, className)
                .add(JSON_PACKAGE_NAME, packageName)
                .add(JSON_INTERFACE_VALUE, interfaceValue)
                .add(JSON_ABSTRACT_VALUE, abstractValue)
                .add(JSON_EXTENDED_CLASS, extendedClass)
                .add(JSON_IMPLEMENTED_CLASSES, implementedClasses)
                .add(JSON_VARIABLES, variables)
                .add(JSON_METHODS, methods)
                .build();
        
        return customClassJson;
    }

    /**
     * Helper method which converts an ArrayList of strings representing implemented classes
     * to a JsonArray
     * 
     * @param implementedClasses
     *      The ArrayList to be converted
     * @return 
     *      The ArrayList in JsonArray format
     */
    private JsonArray makeImplementedClassesJsonArray(ArrayList<String> implementedClasses) {
        JsonArrayBuilder classArrayBuilder = Json.createArrayBuilder();
        
        for(String implementedString : implementedClasses){
            classArrayBuilder.add(implementedString);
        }
        
        JsonArray implementedClassArrayJson = classArrayBuilder.build();
        return implementedClassArrayJson;
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
        double maxPixelWidth = getDataAsDouble(json, JSON_MAX_PIXEL_WIDTH);
        
	CustomClassWrapper.setFont(textFont);
        CustomClassWrapper.setPixelHeight(pixelHeight);
        CustomClassWrapper.setMaxPixelWidth(maxPixelWidth);
	
	// Load the array of CustomClassWrappers
	JsonArray jsonClassArray = json.getJsonArray(JSON_CLASS_ARRAY);
	for (int i = 0; i < jsonClassArray.size(); i++) {
	    JsonObject jsonCustomBox = jsonClassArray.getJsonObject(i);
	    CustomBox c = loadCustomBox(jsonCustomBox);
	    dataManager.getClasses().add(c);
	}
    }
    
    private CustomBox loadCustomBox(JsonObject j){
        //Load in X and Y values and instantiate the CustomClassWrapper
        double startX = getDataAsDouble(j, JSON_START_X);
        double startY = getDataAsDouble(j, JSON_START_Y);
        if(j.getJsonObject(JSON_CUSTOM_CLASS).containsKey(JSON_INTERFACE_VALUE)){
            CustomClassWrapper c = new CustomClassWrapper(startX, startY);
            //Load in CustomClass data
            c.setData(loadCustomClass(j.getJsonObject(JSON_CUSTOM_CLASS)));
            return c;
        }
        else{
            CustomImport c = new CustomImport(startX, startY, j.getJsonObject(JSON_CUSTOM_CLASS).getString(JSON_CLASS_NAME));
            c.setPackageName(j.getJsonObject(JSON_CUSTOM_CLASS).getString(JSON_PACKAGE_NAME));
            return c;
        }
    }
    
    private CustomClass loadCustomClass(JsonObject j){
        CustomClass c = new CustomClass();
        c.setClassName(j.getString(JSON_CLASS_NAME));
        c.setPackageName(j.getString(JSON_PACKAGE_NAME));
        c.setInterfaceValue(j.getBoolean(JSON_INTERFACE_VALUE));
        c.setAbstractValue(j.getBoolean(JSON_ABSTRACT_VALUE));
        c.setExtendedClass(j.getString(JSON_EXTENDED_CLASS));
        
        c.setImplementedClasses(loadImplementedClasses(j.getJsonArray(JSON_IMPLEMENTED_CLASSES)));
        c.setVariables(loadVariables(j.getJsonArray(JSON_VARIABLES)));
        c.setMethods(loadMethods(j.getJsonArray(JSON_METHODS)));
        
        return c;
    }
    
    private ArrayList<String> loadImplementedClasses(JsonArray j){
        ArrayList<String> implementedClasses = new ArrayList<>();
        
        for(int i = 0; i < j.size(); i++){
            String classString = j.getString(i);
            implementedClasses.add(classString);
        }
        
        return implementedClasses;
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
    
//    /**
//     * Exports the current design to code. 
//     * @param dataManager
//     * @param filePath
//     * @return 
//     *       0 if the code was exported correctly.
//     *      -1 if there was an error creating the package directories.
//     *      -2 if there was an error creating the java files.
//     */
//    public int exportCode(DataManager dataManager, String filePath){
//        ArrayList<CustomClassWrapper> classes = dataManager.getClasses();
//        
//        //First, create all necessary packages to put our java files in.
//        //Get every package contained in the current design.
//        ArrayList<String> packages = getAllPackages(classes);
//        
//        //Then make all the package directories
//        boolean makePackagesReturn = makePackageDirectories(packages, filePath);
//        if(!makePackagesReturn)
//            return ERROR_CREATING_DIRECTORIES;
//        
//        
//        //Then put all the java files in the packages
//        boolean makeJavaFilesReturn = makeJavaFiles(classes, filePath);
//        if(!makeJavaFilesReturn)
//            return ERROR_CREATING_JAVA_FILES;
//        
//        return 0;
//    }
//    
//    /**
//     * Helper method which extracts an ArrayList of packages from an ArrayList of 
//     * CustomClassWrappers.
//     * 
//     * @param classes
//     * @return 
//     *      The ArrayList of packages
//     */
//    private ArrayList<String> getAllPackages(ArrayList<CustomClassWrapper> classes){
//        ArrayList<String> packages = new ArrayList<>();
//        for(CustomClassWrapper c : classes){
//            String packageName = c.getData().getPackageName();
//            
//            //If packageName is invalid, set packageName to the default package name
//            if(packageName.equals("") || packageName == null)
//                packageName = c.getData().DEFAULT_PACKAGE_NAME;
//            
//            if(!packages.contains(packageName)){
//                packages.add(packageName);
//            }
//        }
//        return packages;
//    }
//    
//    /**
//     * Helper method to make the package directories.
//     * @param packages
//     * @return 
//     *      True if the directories were created successfully, false otherwise.
//     */
//    private boolean makePackageDirectories(ArrayList<String> packages, String filePath){
//
//        //Sort the package strings based on how many subdirectories they have
//        //Ex: "jdcapp.data" has 1 more subdirectory than "jdcapp"
//        Collections.sort(packages, new Comparator<String>(){
//            @Override
//            public int compare(String s1, String s2){
//                int s1Subdirectories = StringUtils.countMatches(s1, ".");
//                int s2Subdirectories = StringUtils.countMatches(s2, ".");
//                if(s1Subdirectories > s2Subdirectories)
//                    return 1;
//                else if(s1Subdirectories == s2Subdirectories)
//                    return 0;
//                else
//                    return -1;
//            }
//        });
//        
//        //Now make the directories. Because of the sorting, they should be made in
//        //order so that no subdirectory is made without its parent directory already
//        //existing.
//        for(String packageName : packages){
//            packageName = packageName.replace('.', '/');
//            String filePathPackage = filePath + "/" + packageName;
//                        
//            File packageFile = new File(filePathPackage);
//            boolean success = packageFile.mkdirs();
//            if(!success){
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    /**
//     * Helper method to make all the .java files and put them in their respective directories.
//     * @param classes
//     * @param filePath
//     * @return 
//     *      True if the files were created successfully, false otherwise.
//     */
//    private boolean makeJavaFiles(ArrayList<CustomClassWrapper> classes, String filePath){
//        
//        //Loop through all classes in the ArrayList
//        for(CustomClassWrapper c : classes){
//            String classFilePath = getJavaFilePath(c.getData(), filePath);
//            String classFileText = processClass(c.getData(), classes);
//            if(classFileText == null)
//                return false;
//            
//            //Export the imports, method header, variables, and methods to a new file with address classFilePath
//            try{
//                File f = new File(classFilePath);
//                PrintWriter out = new PrintWriter(classFilePath);
//                out.println(classFileText);
//                out.close();
//            } catch (FileNotFoundException e){
//                e.printStackTrace();
//                return false;
//            } 
//        }
//
//        return true;
//    }
//    
//    private String processClass(CustomClass c, ArrayList<CustomClassWrapper> classes){
//        //As we traverse our arrays of CustomMethods and CustomVariables, we will
//        //add strings to this ArrayList to account for any classes that might need
//        //to be imported
//        ArrayList<String> imports = new ArrayList<>();
//        
//        //Get the class header
//        String classHeaderText = generateClassHeader(c, classes);
//        String packageName = c.getPackageName();
//
//        //The methods and variables ArrayLists will hold our processed methods and variables,
//        //ready to be written to our java file
//        ArrayList<String> methods = new ArrayList<>();
//        ArrayList<String> variables = new ArrayList<>();
//        
//        //Convert the methods to strings
//        for(CustomMethod m : c.getMethods()){
//            if(!checkMethod(m, c.isInterface()))
//                return null;
//            methods.add(processMethod(m, c.isInterface()));
//            
//            //Get imports from methods
//            ArrayList<String> importTextArray = getMethodImports(m, classes);
//            //Add imports if they are not already in the ArrayList of imports
//            if(importTextArray != null){
//                for(String s : importTextArray){
//                    if(!imports.contains(s))
//                        imports.add(s);
//                }
//            }
//        }
//        
//        //Use this method to add in all methods inherited from parents WITHIN THE DESIGN
//        methods.addAll(processInheritedMethods(c, classes));
//
//        //Convert the variables to strings
//        if(!c.isInterface()){
//            for(CustomVar v : c.getVariables()){
//                if(!checkVariable(v))
//                    return null;
//                variables.add(processVariable(v));
//            }
//        }
//        
//        //Get imports from variables
//        for(CustomVar v : c.getVariables()){
//            ArrayList<String> importTextArray = getVariableImports(v, classes);
//            //Add imports if they are not already in the ArrayList of imports
//            if(importTextArray != null){
//                for(String s : importTextArray){
//                    if(!imports.contains(s))
//                        imports.add(s);
//                }
//            }
//        }
//        
//        for(String p : c.getParents()){
//            ArrayList<String> importTextArray = getParentImports(p, classes);
//            //Add imports if they are not already in the ArrayList of imports
//            if(importTextArray != null){
//                for(String s : importTextArray){
//                    if(!imports.contains(s))
//                        imports.add(s);
//                }
//            }
//        }
//        
//        //Construct our string to return
//        String toReturn = "";
//        toReturn += "package " + packageName + ";\n\n";
//        
//        for(String importString : imports){
//            toReturn += "import " + importString + ";\n";
//        }
//        
//        toReturn += "\n" + classHeaderText + "\n";
//        for(String v : variables){
//            if(!v.equals(""))
//                toReturn += "\n" + v;
//        }
//        
//        toReturn += "\n";
//        
//        for(String m : methods){
//            if(!m.equals(""))
//                toReturn += "\n" + m;
//        }
//        
//        toReturn += "\n}";
//        
//        return toReturn;
//    }
//    
//    /**
//     * Generates the header for a given class and returns it as a String.
//     * @param c
//     * @return 
//     */
//    private String generateClassHeader(CustomClass c, ArrayList<CustomClassWrapper> classes){
//        String classHeader = "public ";
//        if(c.isInterface())
//            classHeader += "interface ";
//        else if(c.isAbstract())
//            classHeader += "abstract class ";
//        else
//            classHeader += "class ";
//        classHeader += c.getClassName() + " ";
//        
//        String extended = "";
//        
//        //Check if any of the parents are abstract classes. If one is, set it to the extended string
//        //It will be the only class extended from the current class. All others will be implemented.
//        for(CustomClassWrapper wrapper : classes){
//            for(String parent : c.getParents()){
//                if(wrapper.getData().getClassName().equals(parent) && !wrapper.getData().isInterface()){
//                    extended = parent;
//                    break;
//                }
//            }
//            break;
//        }
//        
//        if(!extended.equals(""))
//            classHeader += "extends " + extended + " ";
//        else{
//            //NOTE: THIS IS HARDCODED SO THAT EXPORTED CODE WILL COMPILE
//            if(c.getParents().size() > 0 && !c.getParents().get(0).equals("") && !c.getParents().get(0).equals("EventHandler")){
//                classHeader += "extends " + c.getParents().get(0) + " ";
//                extended = c.getParents().get(0);
//            }
//        }
//        
//        for(String parent : c.getParents()){
//            if(!parent.equals(extended))
//                classHeader += "implements " + parent + " ";
//        }
//        
//        classHeader += "{";
//        return classHeader;
//    }
//    
//    /**
//     * Helper method that iterates through a class's parents. If the parents are contained
//     * within the design, adds an @Override method for each of the parents' methods which need to
//     * be overridden.
//     * @param c
//     * @param classes
//     * @return 
//     */
//    private ArrayList<String> processInheritedMethods(CustomClass c, ArrayList<CustomClassWrapper> classes){
//        ArrayList<String> processedOverrideMethods = new ArrayList<>();
//        
//        for(CustomClassWrapper wrapper : classes){
//            for(String parent : c.getParents()){
//                if(wrapper.getData().getClassName().equals(parent) && !wrapper.getData().isInterface()){
//                    ArrayList<CustomMethod> abstractMethods = getAbstractMethods(wrapper.getData().getMethods());
//                    for(CustomMethod m : abstractMethods){
//                        CustomMethod cloneMethod = m.clone();
//                        cloneMethod.setAbstractValue(false);
//                        String processed = "\t@Override\n" + processMethod(cloneMethod, c.isInterface());
//                        processedOverrideMethods.add(processed);
//                    }
//                }
//                else if(wrapper.getData().getClassName().equals(parent)){
//                    for(CustomMethod m : wrapper.getData().getMethods()){
//                        CustomMethod cloneMethod = m.clone();
//                        cloneMethod.setAbstractValue(false);
//                        String processed = "\t@Override\n" + processMethod(cloneMethod, c.isInterface());
//                        processedOverrideMethods.add(processed);
//                    }
//                }
//            }
//        }
//        
//        return processedOverrideMethods;
//    }
//    
//    /**
//     * Helper method to get all abstract methods in an ArrayList of CustomMethods.
//     * @param methods
//     * @return 
//     */
//    private ArrayList<CustomMethod> getAbstractMethods(ArrayList<CustomMethod> methods){
//        ArrayList<CustomMethod> abstractMethods = new ArrayList<>();
//        for(CustomMethod m : methods){
//            if(m.isAbstract())
//                abstractMethods.add(m);
//        }
//        return abstractMethods;
//    }
//    
//    /**
//     * Helper method which processes a CustomMethod object and returns skeleton code
//     * for that method as a String.
//     * @param m
//     * @param isInterface
//     * @return 
//     */
//    private String processMethod(CustomMethod m, boolean isInterface){
//        String processedMethod = "";
//        
//        //If method is an interface, it must be either static or abstract
//        if(isInterface){
//            
//            processedMethod += "\t";
//            
//            //If m is static, method should start with "static" modifier
//            //Interface methods are public by default, so we can exclude the public access modifier
//            if(m.isStatic()){
//                processedMethod += "static ";
//            }
//            
//            processedMethod += m.getReturnType() + " ";
//            processedMethod += m.getMethodName() + " (";
//            if(!m.hasNoArguments()){
//                for(String arg : m.getArguments()){
//                    String[] splitArg = arg.split(" . ");
//                    processedMethod  = processedMethod + splitArg[1] + " " + splitArg[0] + ", ";
//                }
//                processedMethod = processedMethod.substring(0, processedMethod.length() - 2);
//            }
//            
//            processedMethod += ")";
//            
//            //If m is static, need a return statement. If m is abstract, don't even need brackets.
//            if(m.isStatic()){
//                processedMethod += "{\n";
//                processedMethod += "\t" + getReturnStatement(m);
//                processedMethod += "\n\t}";
//            }else if(m.isAbstract()){
//                processedMethod += ";";
//            }
//            
//        }
//        else if(m.isAbstract()){
//            processedMethod += "\t" + m.getAccess() + " ";
//            if(m.isStatic())
//                processedMethod += "static ";
//            processedMethod += "abstract ";
//            if(!m.isConstructor())
//                processedMethod += m.getReturnType() + " ";
//            
//            processedMethod += m.getMethodName() + " (";
//            if(!m.hasNoArguments()){
//                for(String arg : m.getArguments()){
//                    String[] splitArg = arg.split(" . ");
//                    processedMethod  = processedMethod + splitArg[1] + " " + splitArg[0] + ", ";
//                }
//                processedMethod = processedMethod.substring(0, processedMethod.length() - 2);
//            }
//            
//            processedMethod += ");";
//        }
//        else{
//            processedMethod += "\t" + m.getAccess() + " ";
//            if(m.isStatic())
//                processedMethod += "static ";
//            if(!m.isConstructor())
//                processedMethod += m.getReturnType() + " ";
//            
//            processedMethod += m.getMethodName() + " (";
//            if(!m.hasNoArguments()){
//                for(String arg : m.getArguments()){
//                    String[] splitArg = arg.split(" . ");
//                    processedMethod  = processedMethod + splitArg[1] + " " + splitArg[0] + ", ";
//                }
//                processedMethod = processedMethod.substring(0, processedMethod.length() - 2);
//            }
//            processedMethod += "){\n";
//            if(!m.isConstructor())
//                processedMethod += "\t" + getReturnStatement(m);
//            processedMethod += "\n\t}";
//        }
//        return processedMethod;
//    }
//    
//    /**
//     * Helper method which takes in a CustomVar object and converts it to a formatted
//     * String instantiation of it.
//     * @param v
//     * @return 
//     */
//    private String processVariable(CustomVar v){
//        String processedVariable = "\t";
//        
//        processedVariable += v.getAccess() + " ";
//        if(v.isStatic())
//            processedVariable += "static ";
//        processedVariable += v.getVarType() + " ";
//        processedVariable += v.getVarName() + ";";
//        
//        return processedVariable;
//    }
//    
//    /**
//     * Helper method to get a formatted return statement for a method stub.
//     * @param m
//     * @return 
//     */
//    private String getReturnStatement(CustomMethod m){
//        String returnStatement = "";
//        if(m.getReturnType().equals(CustomMethod.BOOLEAN_RETURN_TYPE))
//            returnStatement += "\treturn true;";
//        else if(m.getReturnType().equals(CustomMethod.CHAR_RETURN_TYPE))
//            returnStatement += "\treturn 'a';";
//        else if(m.getReturnType().equals(CustomMethod.INT_RETURN_TYPE))
//            returnStatement += "\treturn 0;";
//        else if(m.getReturnType().equals(CustomMethod.FLOAT_RETURN_TYPE))
//            returnStatement += "\treturn 0;";
//        else if(m.getReturnType().equals(CustomMethod.BYTE_RETURN_TYPE))
//            returnStatement += "\treturn 0;";
//        else if(m.getReturnType().equals(CustomMethod.LONG_RETURN_TYPE))
//            returnStatement += "\treturn 0;";
//        else if(m.getReturnType().equals(CustomMethod.DOUBLE_RETURN_TYPE))
//            returnStatement += "\treturn 0;";
//        else if(m.getReturnType().equals(CustomMethod.SHORT_RETURN_TYPE))
//            returnStatement += "\treturn 0;";
//        else if(m.getReturnType().equals(CustomMethod.VOID_RETURN_TYPE))
//            returnStatement += "\treturn;";
//        else
//            returnStatement += "\treturn null;";
//        return returnStatement;
//    }
//    
//    /**
//     * Helper method to check whether or not the method is valid. Checks to make sure
//     * the method name is not null or the empty string, and to make sure the method is
//     * abstract or static if the class is an interface.
//     * @param m
//     * @param isInterface
//     * @return 
//     */
//    private boolean checkMethod(CustomMethod m, boolean isInterface){
//        //If the class is an interface and the method is not abstract or static, 
//        //or is a constructor, or is not public return false
//        if(isInterface && !((m.isAbstract() || m.isStatic()) && !m.isConstructor() && m.getAccess().equals(CustomMethod.PUBLIC_METHOD_ACCESS)))
//            return false;
//        if(m.getMethodName() == null || m.getMethodName().equals(""))
//            return false;
//        return true;
//    }
//    
//    /**
//     * Helper method to check whether or not the variable is valid. Checks to make sure
//     * the variable name is not null or the empty string.
//     * 
//     * Note: variables will not be added to the class if it is an interface.
//     * @param m
//     * @param isInterface
//     * @return 
//     */
//    private boolean checkVariable(CustomVar v){
//        //If the variable name is invalid, return false
//        if(v.getVarName() == null || v.getVarName().equals(""))
//            return false;
//        return true;
//    }
//    
//    /**
//     * Helper method to get the properly-formatted file path of a .java file from
//     * its CustomClassWrapper object and the directory file path.
//     * @param c
//     * @param filePath
//     * @return 
//     *      The properly-formatted file path
//     */
//    private String getJavaFilePath(CustomClass c, String filePath){
//        String javaFilePath = filePath + "/";
//        
//        //Get the path to the directory in which the file will be put
//        String packagePath = c.getPackageName().replace('.', '/');
//        javaFilePath += packagePath + "/";
//        
//        javaFilePath = javaFilePath + c.getClassName() + JAVA_FILE_EXTENSION;
//        return javaFilePath;
//    }
//
//    private ArrayList<String> getMethodImports(CustomMethod m, ArrayList<CustomClassWrapper> classes) {
//        ArrayList<String> imports = new ArrayList<>();
//        
//        //Get an array of strings representing the types to check against the list of classes
//        ArrayList<String> typesToCheck = new ArrayList<>();
//        
//        //First get the types from the return type (could be multiple if return type is an arraylist)
//        //When adding, first check to be sure that typesToCheck does not already contain the type being added
//        ArrayList<String> converted = convertToTypes(m.getReturnType());
//        for(String type : converted){
//            if(!typesToCheck.contains(type))
//                typesToCheck.add(type);
//        }
//        
//        //Then do the same for all the arguments
//        if(!m.hasNoArguments()){
//            for(String uncheckedType : m.getArguments()){
//                String[] splitUncheckedType = uncheckedType.split(" : ");
//                String typeToConvert = splitUncheckedType[1];
//                converted = convertToTypes(typeToConvert);
//                for(String type : converted){
//                    if(!typesToCheck.contains(type))
//                        typesToCheck.add(type);
//                }
//            }
//        }
//        
//        //Get any necessary design imports by comparing typesToCheck to the CustomClasses in the design
//        imports.addAll(checkAgainstDesignClasses(typesToCheck, classes));
//        
//        //Get any necessary api imports by comparing typesToCheck to the packages in Package.getPackages()
//        imports.addAll(checkAgainstAPIClasses(typesToCheck));
//        
//        return imports;
//    }
//
//    private ArrayList<String> getVariableImports(CustomVar v, ArrayList<CustomClassWrapper> classes) {
//        ArrayList<String> imports = new ArrayList<>();
//        String varType = v.getVarType();
//        ArrayList<String> typesToCheck = convertToTypes(varType);
//        
//        //Get any necessary design imports by comparing typesToCheck to the CustomClasses in the design
//        imports.addAll(checkAgainstDesignClasses(typesToCheck, classes));
//        
//        //Get any necessary api imports by comparing typesToCheck to the packages in Package.getPackages()
//        imports.addAll(checkAgainstAPIClasses(typesToCheck));
//        
//        return imports;
//    }
//    
//    private ArrayList<String> getParentImports(String s, ArrayList<CustomClassWrapper> classes) {
//        ArrayList<String> imports = new ArrayList<>();
//        ArrayList<String> parentTypes = convertToTypes(s);
//        
//        //Get any necessary design imports by comparing typesToCheck to the CustomClasses in the design
//        imports.addAll(checkAgainstDesignClasses(parentTypes, classes));
//        
//        //Get any necessary api imports by comparing typesToCheck to the packages in Package.getPackages()
//        imports.addAll(checkAgainstAPIClasses(parentTypes));
//        
//        return imports;
//    }
//    
//    /**
//     * Gets an ArrayList of type strings from a given string
//     * @param type
//     * @return
//     *      The ArrayList (note that it may contain duplicate elements or may be empty)
//     */
//    private ArrayList<String> convertToTypes(String type){
//        ArrayList<String> typesReturn = new ArrayList<>();
//        if(type.equals(CustomMethod.BOOLEAN_RETURN_TYPE) || type.equals(CustomMethod.BYTE_RETURN_TYPE) || 
//                type.equals(CustomMethod.CHAR_RETURN_TYPE) || type.equals(CustomMethod.DOUBLE_RETURN_TYPE) || 
//                type.equals(CustomMethod.FLOAT_RETURN_TYPE) || type.equals(CustomMethod.INT_RETURN_TYPE) ||
//                type.equals(CustomMethod.LONG_RETURN_TYPE) || type.equals(CustomMethod.SHORT_RETURN_TYPE) || 
//                type.equalsIgnoreCase(CustomMethod.VOID_RETURN_TYPE) || type.equals(CustomMethod.CONSTRUCTOR_RETURN_TYPE) || 
//                type.equals("String"))
//            return typesReturn;
//        
//        //If type is like an arraylist or hashtable, get outer type and inner type
//        if(type.contains("<") && type.contains(">")){
//            typesReturn.add(type.substring(0, type.indexOf("<")));
//            String innerType = type.substring(type.indexOf("<") + 1, type.lastIndexOf(">"));
//            
//            //Split the inner type on the ", " regex in case the outer type has more than one
//            //inner type. Then recursively call convertToTypes on all inner types in case they
//            //too hold inner types of their own. (This is probably unnecessary, but fuck it)
//            String[] innerTypeArray = innerType.split(", ");
//            for(String s : innerTypeArray){
//                typesReturn.addAll(convertToTypes(s));
//            }
//        }
//        //Otherwise just add the type
//        else{
//            typesReturn.add(type);
//        }
//        return typesReturn;
//    }
//    
//    /*Test method
//    public void printPackages(){
//        Package[] packages = Package.getPackages();
//        for(int i = 0; i < packages.length; i++)
//            System.out.println(packages[i].getName());
//    }*/
//
//    private ArrayList<String> checkAgainstDesignClasses(ArrayList<String> typesToCheck, ArrayList<CustomClassWrapper> classes) {
//        ArrayList<String> imports = new ArrayList<>();
//        
//        for(CustomClassWrapper c : classes){
//            for(String type : typesToCheck){
//                if(c.getData().getClassName().equals(type)){
//                    String newImport = c.getData().getPackageName() + "." + type;
//                    imports.add(newImport);
//                }
//            }
//        }
//        
//        return imports;
//    }
//
//    private ArrayList<String> checkAgainstAPIClasses(ArrayList<String> typesToCheck) {
//        ArrayList<String> imports = new ArrayList<>();
//        
//        Package[] packages = Package.getPackages();
//        for(Package p : packages){
//            for(String type : typesToCheck){
//                try{
//                    Class.forName(p.getName() + "." + type);
//                    imports.add(p.getName() + "." + type); 
//                } catch(ClassNotFoundException e){}
//            }
//        }
//        
//        //NOTE: THIS IS HARDCODED SO THAT EXPORTED CODE WILL COMPILE
//        for(String type : typesToCheck){
//            try{
//                Class.forName("javafx.concurrent" + "." + type);
//                imports.add("javafx.concurrent" + "." + type); 
//            } catch(ClassNotFoundException e){}
//        }
//        
//        for(String i : imports){
//            if(i.contains("com.sun.") || i.contains("org.w3c."))
//                imports.remove(i);
//        }
//        
//        return imports;
//    }
//    
}
