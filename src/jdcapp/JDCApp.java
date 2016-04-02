/*
 * 
 */
package jdcapp;

import java.net.URL;
import javafx.application.Application;
import javafx.stage.Stage;
import jdcapp.data.DataManager;
import jdcapp.file.FileManager;
import jdcapp.gui.AppMessageDialogSingleton;
import jdcapp.gui.AppYesNoCancelDialogSingleton;
import jdcapp.gui.WorkspaceManager;
import static jdcapp.settings.AppPropertyType.APP_CSS;
import static jdcapp.settings.AppPropertyType.APP_PATH_CSS;
import static jdcapp.settings.AppPropertyType.APP_TITLE;
import static jdcapp.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_MESSAGE;
import static jdcapp.settings.AppPropertyType.PROPERTIES_LOAD_ERROR_TITLE;
import static jdcapp.settings.AppStartupConstants.JDC_APP_PROPERTIES_FILE_NAME;
import static jdcapp.settings.AppStartupConstants.PATH_DATA;
import static jdcapp.settings.AppStartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;

/**
 *
 * @author Noah
 */
public class JDCApp extends Application {
    
    //The DataManager class contains all the data for the JDCApp
    DataManager data;
    
    //The FileManager class handles saving and loading .json files
    FileManager file;
    
    //The WorkspaceManager class deals with all GUI implementation
    WorkspaceManager workspace;
    
    //Component accessor methods
    public DataManager getDataManager() { return data; }
    public FileManager getFileManager() { return file; }
    public WorkspaceManager getWorkspaceManager() { return workspace; }
    
    @Override
    public void start(Stage primaryStage) {
        	// LET'S START BY INITIALIZING OUR DIALOGS
	AppMessageDialogSingleton messageDialog = AppMessageDialogSingleton.getSingleton();
	messageDialog.init(primaryStage);
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
	yesNoDialog.init(primaryStage);
	
	PropertiesManager props = PropertiesManager.getPropertiesManager();

	try {
	    //Load JDCApp properties
	    boolean success = loadProperties(JDC_APP_PROPERTIES_FILE_NAME);
	    
	    if (success) {
		String appTitle = props.getProperty(APP_TITLE);

		//TODO: Code DataManager and FileManager
                file = new FileManager();
		data = new DataManager(JDCApp.this);

		//TODO: Code WorkspaceManager
		workspace = new WorkspaceManager(primaryStage, appTitle, JDCApp.this);
		
		//Initialize the style
		initStylesheet();
                //TODO: Code initStyle method in WorkspaceManager
		workspace.initStyle();
	    } 
	}catch (Exception e/*IOException ioe*/) {
                e.printStackTrace();
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     *
     * @param propertiesFileName The XML file containing properties to be
     * loaded in order to initialize the UI.
     * 
     * @return true if the properties file was loaded successfully, false
     * otherwise.
     */
    public boolean loadProperties(String propertiesFileName) {
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	try {
	    // LOAD THE SETTINGS FOR STARTING THE APP
	    props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
	    props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
	    return true;
	} catch (InvalidXMLFileFormatException ixmlffe) {
	    // SOMETHING WENT WRONG INITIALIZING THE XML FILE
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(PROPERTIES_LOAD_ERROR_TITLE), props.getProperty(PROPERTIES_LOAD_ERROR_MESSAGE));
	    return false;
	}
    }
    
    /**
     * This function sets up the stylesheet to be used for specifying all
     * style for this application. Note that it does not attach CSS style
     * classes to controls, that must be done separately.
     */
    public void initStylesheet() {
	//Select the stylesheet
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String stylesheet = props.getProperty(APP_PATH_CSS);
	stylesheet += props.getProperty(APP_CSS);
	URL stylesheetURL = getClass().getResource(stylesheet);
	String stylesheetPath = stylesheetURL.toExternalForm();
        
        //TODO: Write getPrimaryScene method in WorkspaceManager
	getWorkspaceManager().getPrimaryScene().getStylesheets().add(stylesheetPath);	
    }
    
}
