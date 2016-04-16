/*
 * 
 */
package jdcapp.data;

/**
 *
 * @author Noah
 */
public class CustomVar {
    
    static final String DEFAULT_VAR_NAME = "defaultVar";
    static final String DEFAULT_VAR_TYPE = "DefaultType";
    static final boolean DEFAULT_VAR_STATIC_VALUE = false;
    static final String DEFAULT_VAR_ACCESS = "private";
    
    //The variable name and class type
    private String varName;
    private String varType;
    
    //Whether or not the variable is static
    private boolean staticValue;
    
    //The level of access the variable has
    //TODO: possibly replace this with an enum containing public, private, protected, etc.
    private String access;
    
    public CustomVar(){
        varName = DEFAULT_VAR_NAME;
        varType = DEFAULT_VAR_TYPE;
        staticValue = DEFAULT_VAR_STATIC_VALUE;
        access = DEFAULT_VAR_ACCESS;
    }
    
    /**
     * @return the varName
     */
    public String getVarName(){
        return varName;
    }

    /**
     * @param varName the varName to set
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }

    /**
     * @return the varType
     */
    public String getVarType() {
        return varType;
    }

    /**
     * @param varType the varType to set
     */
    public void setVarType(String varType) {
        this.varType = varType;
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
    
}
