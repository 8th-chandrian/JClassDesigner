/*
 * 
 */
package jdcapp.data;

import java.util.ArrayList;
import javafx.geometry.Point2D;

/**
 * This class represents a connector from one class to another, and is an instance
 * variable in any CustomClassWrapper object. It extends an ArrayList of Point2D objects,
 * which map out all the different points in the connector, and contains a String
 * instance variable which determines which type of connector it is.
 * 
 * The options for the connectorType instance variable are as follows:
 *      - arrow: this class implements/extends the indicated class
 *      - diamond: this class is composed of the indicated class (as an instance variable)
 *      - feathered: this class has the indicated class as an argument or return type for
 *        one of its methods
 * 
 * Note on connector types: arrow takes precedence over all, and diamond takes precedence 
 *                          over feathered
 * 
 * Note on diamond connectors: Diamond should be attached to the connecting class (the
 *                             one which contains this arraylist) not the class being
 *                             connected to
 *
 * @author Noah
 */
public class ConnectorArrayList extends ArrayList<Point2D>{
    
    public static final String ARROW_CONNECTOR = "arrow";
    public static final String DIAMOND_CONNECTOR = "diamond";
    public static final String FEATHERED_CONNECTOR = "feathered";
    
    //The type of connector which this ArrayList represents.
    //Options: diamond, feathered, arrow
    private String connectorType;
    
    public ConnectorArrayList(){
        super();
        connectorType = ARROW_CONNECTOR;
    }
    
    public ConnectorArrayList(String connectorType){
        super();
        this.connectorType = connectorType;
    }
    
    public ConnectorArrayList(ArrayList<Point2D> points, String connectorType){
        super(points);
        this.connectorType = connectorType;
    }
    
    public String getConnectorType(){
        return connectorType;
    }
    
    public void setConnectorType(String connectorType){
        if(connectorType.equals(DIAMOND_CONNECTOR) || connectorType.equals(ARROW_CONNECTOR) 
                || connectorType.equals(FEATHERED_CONNECTOR))
            this.connectorType = connectorType;
    }
}
