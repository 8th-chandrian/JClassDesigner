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
    static final String DEFAULT_METHOD_ACCESS = "private";
    
    
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
    private ArrayList<String> arguments;
    
    public CustomMethod(){
        methodName = DEFAULT_METHOD_NAME;
        returnType = DEFAULT_RETURN_TYPE;
        staticValue = DEFAULT_METHOD_STATIC_VALUE;
        abstractValue = DEFAULT_METHOD_ABSTRACT_VALUE;
        access = DEFAULT_METHOD_ACCESS;
        arguments = new ArrayList<>();
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
    
}
