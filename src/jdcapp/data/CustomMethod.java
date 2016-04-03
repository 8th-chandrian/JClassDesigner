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
    //TODO: FINISH CODING CLASS
}
