/*
 * 
 */
package jdcapp.controller;

import java.io.File;
import java.io.IOException;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import jdcapp.JDCApp;
import jdcapp.data.DataManager;
import jdcapp.file.FileManager;
import jdcapp.gui.AppMessageDialogSingleton;
import jdcapp.gui.AppYesNoCancelDialogSingleton;
import static jdcapp.settings.AppPropertyType.EXPORT_CODE_TITLE;
import static jdcapp.settings.AppPropertyType.EXPORT_ERROR_MESSAGE_DIRECTORIES;
import static jdcapp.settings.AppPropertyType.EXPORT_ERROR_MESSAGE_FILES;
import static jdcapp.settings.AppPropertyType.EXPORT_ERROR_MESSAGE_UNKNOWN;
import static jdcapp.settings.AppPropertyType.EXPORT_ERROR_TITLE;
import static jdcapp.settings.AppPropertyType.EXPORT_SUCCESS_MESSAGE;
import static jdcapp.settings.AppPropertyType.EXPORT_SUCCESS_TITLE;
import static jdcapp.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static jdcapp.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static jdcapp.settings.AppPropertyType.LOAD_WORK_TITLE;
import static jdcapp.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static jdcapp.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import static jdcapp.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static jdcapp.settings.AppPropertyType.NEW_ERROR_TITLE;
import static jdcapp.settings.AppPropertyType.SAVE_COMPLETED_MESSAGE;
import static jdcapp.settings.AppPropertyType.SAVE_COMPLETED_TITLE;
import static jdcapp.settings.AppPropertyType.SAVE_ERROR_MESSAGE;
import static jdcapp.settings.AppPropertyType.SAVE_ERROR_TITLE;
import static jdcapp.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static jdcapp.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import static jdcapp.settings.AppPropertyType.SAVE_WORK_TITLE;
import static jdcapp.settings.AppPropertyType.WORK_FILE_EXT;
import static jdcapp.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static jdcapp.settings.AppStartupConstants.PATH_WORK;
import properties_manager.PropertiesManager;

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
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave();
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                app.getDataManager().reset();        

		// LOAD ALL THE DATA INTO THE WORKSPACE
		app.getWorkspaceManager().reloadWorkspace();	

		// MAKE SURE THE CANVAS AND OTHER CONTROLS ARE ACTIVATED
                app.getWorkspaceManager().activateCanvas();
                app.getWorkspaceManager().activateWorkspaceControls();
		
		// WORK IS NOT SAVED
                saved = false;
		currentWorkFile = null;

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                app.getWorkspaceManager().updateFileToolbarControls(saved);

                // TELL THE USER NEW WORK IS UNDERWAY
		dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
	    dialog.show(props.getProperty(NEW_ERROR_TITLE), props.getProperty(NEW_ERROR_MESSAGE));
        }
    }

    public void handleLoadRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A Course
            if (continueToOpen) {
                // GO AHEAD AND PROCEED LOADING A Course
                promptToOpen();
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }

    public void handleSaveRequest() {
        // WE'LL NEED THIS TO GET CUSTOM STUFF
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
	    // MAYBE WE ALREADY KNOW THE FILE
	    if (currentWorkFile != null) {
		saveWork(currentWorkFile);
	    }
	    // OTHERWISE WE NEED TO PROMPT THE USER
	    else {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getWorkspaceManager().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		}
	    }
        } catch (IOException ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAVE_ERROR_TITLE), props.getProperty(SAVE_ERROR_MESSAGE));
        }
    }

    public void handleSaveAsRequest() {
        // WE'LL NEED THIS TO GET CUSTOM STUFF
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
	    // PROMPT THE USER FOR A FILE NAME
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

            File selectedFile = fc.showSaveDialog(app.getWorkspaceManager().getWindow());
            if (selectedFile != null) {
                saveWork(selectedFile);
            }
        } catch (IOException ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAVE_ERROR_TITLE), props.getProperty(SAVE_ERROR_MESSAGE));
        }
    }

    public void handlePhotoExportRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleCodeExportRequest() {
        
        // WE'LL NEED THIS TO GET CUSTOM STUFF
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager dataManager = app.getDataManager();
        FileManager fileManager = app.getFileManager();
        
        // PROMPT THE USER FOR A FILE NAME
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(PATH_WORK));
        dc.setTitle(props.getProperty(EXPORT_CODE_TITLE));

        File selectedDirectory = dc.showDialog(app.getWorkspaceManager().getWindow());
        if (selectedDirectory != null) {
            int result = fileManager.exportCode(dataManager, selectedDirectory.getPath());
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            if(result == FileManager.CODE_EXPORTED_SUCCESSFULLY)
                dialog.show(props.getProperty(EXPORT_SUCCESS_TITLE), props.getProperty(EXPORT_SUCCESS_MESSAGE));
            else if(result == FileManager.ERROR_CREATING_DIRECTORIES)
                dialog.show(props.getProperty(EXPORT_ERROR_TITLE), props.getProperty(EXPORT_ERROR_MESSAGE_DIRECTORIES));
            else if(result == FileManager.ERROR_CREATING_JAVA_FILES)
                dialog.show(props.getProperty(EXPORT_ERROR_TITLE), props.getProperty(EXPORT_ERROR_MESSAGE_FILES));
            else
                dialog.show(props.getProperty(EXPORT_ERROR_TITLE), props.getProperty(EXPORT_ERROR_MESSAGE_UNKNOWN));
        }
    }
    
    public void handleExitRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating new
     * work, or opening another file. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is returned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave() throws IOException {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// CHECK TO SEE IF THE CURRENT WORK HAS
	// BEEN SAVED AT LEAST ONCE
	
        // PROMPT THE USER TO SAVE UNSAVED WORK
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(SAVE_UNSAVED_WORK_TITLE), props.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            // SAVE THE DATA FILE
            DataManager dataManager = app.getDataManager();
	    
	    if (currentWorkFile == null) {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getWorkspaceManager().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		    saved = true;
		}
	    }
	    else {
		saveWork(currentWorkFile);
		saved = true;
	    }
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }
    
    // HELPER METHOD FOR SAVING WORK
    private void saveWork(File selectedFile) throws IOException {
	// SAVE IT TO A FILE
	app.getFileManager().saveData(app.getDataManager(), selectedFile.getPath());
	
	// MARK IT AS SAVED
	currentWorkFile = selectedFile;
	saved = true;
	
	// TELL THE USER THE FILE HAS BEEN SAVED
	AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show(props.getProperty(SAVE_COMPLETED_TITLE),props.getProperty(SAVE_COMPLETED_MESSAGE));
		    
	// AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
	// THE APPROPRIATE CONTROLS
	app.getWorkspaceManager().updateFileToolbarControls(saved);	
    }
    
    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen() {
	// WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(app.getWorkspaceManager().getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                DataManager dataManager = app.getDataManager();
		FileManager fileManager = app.getFileManager();
                fileManager.loadData(dataManager, selectedFile.getAbsolutePath());
                app.getWorkspaceManager().reloadWorkspace();

		// MAKE SURE THE WORKSPACE IS ACTIVATED
		//app.getWorkspaceManager().activateWorkspace(app.getGUI().getAppPane());
                saved = true;
                app.getWorkspaceManager().activateWorkspaceControls();
                app.getWorkspaceManager().updateFileToolbarControls(saved);
                app.getWorkspaceManager().wipeSelectedClassData();
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
}
