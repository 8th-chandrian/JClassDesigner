/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;

/**
 *
 * @author Noah
 */
public class CustomClass{
    
    public static final String DEFAULT_CLASS_NAME = "DefaultClass";
    public static final String DEFAULT_PACKAGE_NAME = "default_package";
    public static final String DEFAULT_EXTENDED_CLASS = "";
    
    //The class's name and the name of its package
    private String className;
    private String packageName;
    
    //Keeps track of whether or not this is an interface
    private boolean interfaceValue;
    
    private boolean abstractValue;
    
    //The ArrayList of the class's implemented parents (Strings are the parent names)
    private ArrayList<String> implementedClasses;
    private String extendedClass;
    
    //The lists of all the variables and methods contained within the class
    private ArrayList<CustomVar> variables;
    private ArrayList<CustomMethod> methods;
    
    /**
     * Default constructor.
     */
    public CustomClass(){
        interfaceValue = false;
        abstractValue = false;
        className = DEFAULT_CLASS_NAME;
        packageName = DEFAULT_PACKAGE_NAME;
        implementedClasses = new ArrayList<>();
        extendedClass = DEFAULT_EXTENDED_CLASS;
        variables = new ArrayList<>();
        methods = new ArrayList<>();
    }
    
    public CustomClass(String name){
        interfaceValue = false;
        abstractValue = false;
        className = name;
        packageName = DEFAULT_PACKAGE_NAME;
        implementedClasses = new ArrayList<>();
        extendedClass = DEFAULT_EXTENDED_CLASS;
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

    public ArrayList<String> getImplementedClasses() {
        return implementedClasses;
    }
    
    public void setImplementedClasses(ArrayList<String> implementedClasses){
        this.implementedClasses = implementedClasses;
    }
    
    public String getExtendedClass(){
        return extendedClass;
    }
    
    public void setExtendedClass(String extendedClass){
        this.extendedClass = extendedClass;
    }

    public ArrayList<CustomVar> getVariables() {
        return variables;
    }
    
    public void setVariables(ArrayList<CustomVar> variables){
        this.variables = variables;
    }

    public ArrayList<CustomMethod> getMethods() {
        return methods;
    }
    
    public void setMethods(ArrayList<CustomMethod> methods){
        this.methods = methods;
    }

    public boolean isInterface(){
        return interfaceValue;
    }
    
    public boolean isAbstract(){
        return abstractValue;
    }
    
    public void setAbstractValue(boolean abstractValue){
        this.abstractValue = abstractValue;
    }
    
    public void setInterfaceValue(boolean interfaceValue){
        this.interfaceValue = interfaceValue;
    }
    
    public void updateAbstractValue(){
        for(CustomMethod m : methods){
            if(m.isAbstract()){
                abstractValue = true;
                return;
            }   
        }
        abstractValue = false;
    }
}
