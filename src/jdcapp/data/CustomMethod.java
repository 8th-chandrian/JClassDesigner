/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;

/**
 *
 * @author Noah
 */
public class CustomMethod {
    
    static final String DEFAULT_METHOD_NAME = "defaultMethod";
    static final String DEFAULT_RETURN_TYPE = "DefaultType";
    static final boolean DEFAULT_METHOD_STATIC_VALUE = false;
    static final boolean DEFAULT_METHOD_ABSTRACT_VALUE = false;
    
    public static final String PRIVATE_METHOD_ACCESS = "private";
    public static final String PUBLIC_METHOD_ACCESS = "public";
    public static final String PROTECTED_METHOD_ACCESS = "protected";
    
    public static final String VOID_RETURN_TYPE = "void";
    public static final String CONSTRUCTOR_RETURN_TYPE = "";
    
    public static final String BYTE_RETURN_TYPE = "byte";
    public static final String SHORT_RETURN_TYPE = "short";
    public static final String INT_RETURN_TYPE = "int";
    public static final String LONG_RETURN_TYPE = "long";
    public static final String FLOAT_RETURN_TYPE = "float";
    public static final String DOUBLE_RETURN_TYPE = "double";
    public static final String BOOLEAN_RETURN_TYPE = "boolean";
    public static final String CHAR_RETURN_TYPE = "char";
    
    
    //Method name and return type
    private String methodName;
    private String returnType;
    
    //Whether or not the method is static/abstract
    private boolean staticValue;
    private boolean abstractValue;
    
    //The level of access the method has
    //TODO: possibly replace this with an enum containing public, private, protected, etc.
    private String access;
    
    //The list of method arguments
    //Arguments will be formatted as argument_name + " : " + argument_type
    private ArrayList<String> arguments;
    
    public CustomMethod(){
        methodName = DEFAULT_METHOD_NAME;
        returnType = DEFAULT_RETURN_TYPE;
        staticValue = DEFAULT_METHOD_STATIC_VALUE;
        abstractValue = DEFAULT_METHOD_ABSTRACT_VALUE;
        access = PRIVATE_METHOD_ACCESS;
        arguments = new ArrayList<>();
    }
    
    /**
     * Overloaded constructor, TO BE USED FOR TESTING PURPOSES ONLY
     * @param name
     * @param type
     * @param isStatic
     * @param isAbstract
     * @param access
     * @param arguments 
     */
    public CustomMethod(String name, String type, boolean isStatic, boolean isAbstract, 
            String access, ArrayList<String> arguments){
        methodName = name;
        returnType = type;
        staticValue = isStatic;
        abstractValue = isAbstract;
        this.access = access;
        this.arguments = arguments;
    }
    
    public String getMethodName(){
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the staticValue
     */
    public boolean isStatic() {
        return staticValue;
    }

    /**
     * @param staticValue the staticValue to set
     */
    public void setStaticValue(boolean staticValue) {
        this.staticValue = staticValue;
    }

    /**
     * @return the abstractValue
     */
    public boolean isAbstract() {
        return abstractValue;
    }

    /**
     * @param abstractValue the abstractValue to set
     */
    public void setAbstractValue(boolean abstractValue) {
        this.abstractValue = abstractValue;
    }

    /**
     * @return the access
     */
    public String getAccess() {
        return access;
    }

    /**
     * @param access the access to set
     */
    public void setAccess(String access) {
        if(access.equals(PUBLIC_METHOD_ACCESS) || access.equals(PROTECTED_METHOD_ACCESS)
                || access.equals(PRIVATE_METHOD_ACCESS))
            this.access = access;
    }

    /**
     * @return the arguments
     */
    public ArrayList<String> getArguments() {
        return arguments;
    }

    /**
     * @param arguments the arguments to set
     */
    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }
    
    /**
     * Checks whether or not the method is a constructor.
     * @return 
     *      If the return type is null or an empty string, the method must be a
     *      constructor, so return true. Otherwise return false.
     */
    public boolean isConstructor(){
        if(returnType.equals(CONSTRUCTOR_RETURN_TYPE) || returnType == null)
            return true;
        return false;
    }
    
    /**
     * Checks whether or not the method has no arguments
     * @return 
     *      If the size of the arguments ArrayList is 1 and the only argument is an
     *      empty string or null, or if the arguments ArrayList is empty, return true.
     *      Otherwise return false.
     */
    public boolean hasNoArguments(){
        if((arguments.size() == 1 && (arguments.get(0).equals("") || arguments.get(0) == null)) 
                || arguments.isEmpty())
            return true;
        return false;
    }
    
    @Override
    public CustomMethod clone(){
        CustomMethod m = new CustomMethod(methodName, returnType, staticValue, abstractValue, access, arguments);
        return m;
    }
}
