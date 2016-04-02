/*
 * 
 */
package jdcapp.controller;

import java.io.File;
import jdcapp.JDCApp;

/**
 *
 * @author Noah
 */
public class FileController {
    
    //The parent application
    JDCApp app;
    
    //Keeps track of whether or not the file has been saved
    boolean saved;
    
    //The file that is currently open and being worked on
    File currentWorkFile;
    
    public FileController(JDCApp initApp){
        //There is no current work file yet
        app = initApp;
        saved = true;
    }

    public void handleNewRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleLoadRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleSaveRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleSaveAsRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handlePhotoExportRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleCodeExportRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void handleExitRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
