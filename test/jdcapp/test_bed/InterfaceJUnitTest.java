/*
 * 
 */
package jdcapp.test_bed;

import java.io.File;
import java.io.IOException;
import jdcapp.JDCApp;
import jdcapp.data.DataManager;
import jdcapp.file.FileManager;
import static jdcapp.test_bed.TestLoad.FILE_PATH;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Noah
 */
public class InterfaceJUnitTest {
    
    private ThreadExampleDesign design;
    private DataManager dataManager;
    
    public InterfaceJUnitTest() {
    }
    
    @Before
    public void setUp(){
        System.out.println("* DesignJUnitTest: @BeforeClass method");
        
        design = new ThreadExampleDesign();
        dataManager = new DataManager(new JDCApp());
        FileManager fileManager = new FileManager();
        
        //Save the data
        try{
            File saveFile = new File(FILE_PATH);
            fileManager.saveData(design.getData(), saveFile.getPath());
        } catch (IOException e){
            e.printStackTrace();
        }
        
        //Load the data
        try{
            fileManager.loadData(dataManager, FILE_PATH);
        } catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    @Test
    public void verifyClassLocation(){
        System.out.println("* DesignJUnitTest: verifyClassLocation()");
        
        //Check the first class's position
        assertTrue(200 == dataManager.getClasses().get(0).getStartX());
        assertTrue(200 == dataManager.getClasses().get(0).getStartY());
        //TODO: possibly check more class locations
    }
    
    @Test
    public void verifyVariables(){
        System.out.println("* DesignJUnitTest: verifyVariable()");
        
        //Check the second class's first variable
        assertEquals("app", dataManager.getClasses().get(1).getData().getVariables().get(0).getVarName());
        assertEquals("ThreadExample", dataManager.getClasses().get(1).getData().getVariables().get(0).getVarType());
        assertEquals("private", dataManager.getClasses().get(1).getData().getVariables().get(0).getAccess());
        assertFalse(dataManager.getClasses().get(1).getData().getVariables().get(0).isStatic());
        //TODO: possibly check more variables
    }
    
    @Test
    public void verifyMethods(){
        System.out.println("* DesignJUnitTest: verifyMethods()");
        
        //Check the arguments of the third class's first method
        assertEquals("initApp : ThreadExample", dataManager.getClasses().get(2).getData().getMethods().get(0).getArguments().get(0));
        assertTrue(1 == dataManager.getClasses().get(2).getData().getMethods().get(0).getArguments().size());
        
        //Check the arguments of the first class's first method
        assertEquals("primaryStage : Stage", dataManager.getClasses().get(0).getData().getMethods().get(0).getArguments().get(0));
        assertTrue(1 == dataManager.getClasses().get(0).getData().getMethods().get(0).getArguments().size());
    }
    
    @Test
    public void verifyConnections(){
        System.out.println("* DesignJUnitTest: verifyConnections()");
        
        //Check the three points of the "Application" connection in the first class
        assertTrue(200 == dataManager.getClasses().get(0).getConnections().get("Application").get(0).getX());
        assertTrue(250 == dataManager.getClasses().get(0).getConnections().get("Application").get(0).getY());
        assertTrue(200 == dataManager.getClasses().get(0).getConnections().get("Application").get(1).getX());
        assertTrue(400 == dataManager.getClasses().get(0).getConnections().get("Application").get(1).getY());
        assertTrue(200 == dataManager.getClasses().get(0).getConnections().get("Application").get(2).getX());
        assertTrue(500 == dataManager.getClasses().get(0).getConnections().get("Application").get(2).getY());
    }
}
