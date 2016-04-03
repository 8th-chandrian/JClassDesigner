/*
 * 
 */
package jdcapp.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jdcapp.JDCApp;
import jdcapp.controller.ComponentController;
import jdcapp.controller.EditController;
import jdcapp.controller.FileController;
import jdcapp.controller.ViewController;
import jdcapp.controller.WorkspaceController;
import jdcapp.file.FileManager;
import static jdcapp.settings.AppPropertyType.ADD_CLASS_ICON;
import static jdcapp.settings.AppPropertyType.ADD_CLASS_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ADD_INTERFACE_ICON;
import static jdcapp.settings.AppPropertyType.ADD_INTERFACE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.APP_LOGO;
import static jdcapp.settings.AppPropertyType.CODE_EXPORT_ICON;
import static jdcapp.settings.AppPropertyType.CODE_EXPORT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.EXIT_ICON;
import static jdcapp.settings.AppPropertyType.EXIT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.GRID_TOGGLE_LABEL;
import static jdcapp.settings.AppPropertyType.LOAD_ICON;
import static jdcapp.settings.AppPropertyType.LOAD_TOOLTIP;
import static jdcapp.settings.AppPropertyType.NEW_ICON;
import static jdcapp.settings.AppPropertyType.NEW_TOOLTIP;
import static jdcapp.settings.AppPropertyType.PHOTO_EXPORT_ICON;
import static jdcapp.settings.AppPropertyType.PHOTO_EXPORT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.REDO_ICON;
import static jdcapp.settings.AppPropertyType.REDO_TOOLTIP;
import static jdcapp.settings.AppPropertyType.REMOVE_ICON;
import static jdcapp.settings.AppPropertyType.REMOVE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.RESIZE_ICON;
import static jdcapp.settings.AppPropertyType.RESIZE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.SAVE_AS_ICON;
import static jdcapp.settings.AppPropertyType.SAVE_AS_TOOLTIP;
import static jdcapp.settings.AppPropertyType.SAVE_ICON;
import static jdcapp.settings.AppPropertyType.SAVE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.SELECT_ICON;
import static jdcapp.settings.AppPropertyType.SELECT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.SNAP_TOGGLE_LABEL;
import static jdcapp.settings.AppPropertyType.UNDO_ICON;
import static jdcapp.settings.AppPropertyType.UNDO_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ZOOM_IN_ICON;
import static jdcapp.settings.AppPropertyType.ZOOM_IN_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ZOOM_OUT_ICON;
import static jdcapp.settings.AppPropertyType.ZOOM_OUT_TOOLTIP;
import static jdcapp.settings.AppStartupConstants.FILE_PROTOCOL;
import static jdcapp.settings.AppStartupConstants.PATH_IMAGES;
import properties_manager.PropertiesManager;

/**
 *
 * @author Noah
 */
public class WorkspaceManager {
    
    //The parent app and app name
    JDCApp app;
    String appTitle;
    
    FileManager fileManager;
    
    //The primary stage and scene for the GUI
    Stage primaryStage;
    Scene primaryScene;
    
    //The overall pane of the app
    BorderPane appPane;
    
    //The top toolbar of the app
    HBox appToolbarPane;
    
    //The three subtoolbars in the top toolbar
    FlowPane fileToolbarPane;
    FlowPane editToolbarPane;
    FlowPane viewToolbarPane;
    
    //The subpanes for the two export buttons and the two toggle buttons (with HBoxes for toggle button labels)
    VBox exportPane;
    VBox togglePane;
    HBox gridPane;
    HBox snapPane;
    
    //The buttons for the file toolbar
    Button newButton;
    Button loadButton;
    Button saveButton;
    Button saveAsButton;
    Button photoExportButton;
    Button codeExportButton;
    Button exitButton;
    
    //The buttons for the edit toolbar
    Button selectButton;
    Button resizeButton;
    Button addClassButton;
    Button addInterfaceButton;
    Button removeButton;
    Button undoButton;
    Button redoButton;
    
    //The buttons and labels for the view toolbar
    Button zoomInButton;
    Button zoomOutButton;
    ToggleButton gridToggle;
    ToggleButton snapToggle;
    Label gridLabel;
    Label snapLabel;
    
    //The right side toolbar of the app
    VBox componentToolbarPane;
    
    //The control, label, and pane for class
    FlowPane classPane;
    Label classNameLabel;
    TextField classNameText;
    
    //The control, label, and pane for package
    FlowPane packagePane;
    Label packageNameLabel;
    TextField packageNameText;
    
    //The control, label, and pane for parent
    FlowPane parentPane;
    Label parentNameLabel;
    ListView parentListView; //TODO: Figure out if this is how you want to implement parent selection
    
    //The controls, labels, and pane for variables
    FlowPane variablePane;
    Label variableLabel;
    Button addVariable;
    Button removeVariable;
    ScrollPane variableScrollPane;
    TableView variableTableView;
    
    //The controlss, labels, and pane for methods
    FlowPane methodPane;
    Label methodLabel;
    Button addMethod;
    Button removeMethod;
    ScrollPane methodScrollPane;
    TableView methodTableView;
    
    //The controllers
    FileController fileController;
    EditController editController;
    ViewController viewController;
    ComponentController componentController;
    WorkspaceController workspaceController;
    
    //The dialogues
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    //The render variables (our classes are rendered on canvas)
    Pane canvas;
    boolean canvasActivated;
    
    /**
     * The constructor. Sets up the GUI in its entirety.
     * @param initPrimaryStage
     *      The app's primary stage, on which the GUI is displayed.
     * @param initAppTitle
     *      The app's title, displayed at the top of the window.
     * @param initApp 
     *      The JDCApp instance that this class is being constructed within.
     */
    public WorkspaceManager(Stage initPrimaryStage, String initAppTitle, JDCApp initApp){
        primaryStage = initPrimaryStage;
        appTitle = initAppTitle;
        app = initApp;
        
        //Initializes the toolbars and various controls=
        initTopToolbar();
        initRightToolbar();
        
        //Initializes the event handling for the top and right toolbars.
        initTopToolbarHandlers();
        initRightToolbarHandlers();
        
        //Starts the window
        initWindow();
    }
    
    /**
     * Accessor for the app's primary stage's scene
     * @return 
     *      the primary scene
     */
    public Scene getPrimaryScene(){ return primaryScene; }
    
    /**
     * Calls methods to initalize the various buttons and controls in the top toolbar.
     * Note that initTopToolbar does not set up any event handling, this is done by
     * initHandlers method. When this method exits, the top toolbar will be fully initialized
     * and added to appPane.
     */
    private void initTopToolbar(){
        appToolbarPane = new HBox();
        
        initFileToolbar();
        initEditToolbar();
        initViewToolbar();
        
        appToolbarPane.getChildren().add(fileToolbarPane);
        appToolbarPane.getChildren().add(editToolbarPane);
        appToolbarPane.getChildren().add(viewToolbarPane);
    }
    
    private void initRightToolbar(){
        //TODO: CODE METHOD
    }
    
    /**
     * Initializes fileToolbarPane and all buttons that it will contain.
     */
    private void initFileToolbar(){
        fileToolbarPane = new FlowPane();
        exportPane = new VBox();
        
        //Initialize the file toolbar buttons and add them to fileToolbarPane
        newButton = initChildButton(fileToolbarPane, NEW_ICON.toString(), NEW_TOOLTIP.toString(), false);
        loadButton = initChildButton(fileToolbarPane, LOAD_ICON.toString(), LOAD_TOOLTIP.toString(), false);
        saveButton = initChildButton(fileToolbarPane, SAVE_ICON.toString(), SAVE_TOOLTIP.toString(), true);
        saveAsButton = initChildButton(fileToolbarPane, SAVE_AS_ICON.toString(), SAVE_AS_TOOLTIP.toString(), true);
        photoExportButton = initChildButton(exportPane, PHOTO_EXPORT_ICON.toString(), PHOTO_EXPORT_TOOLTIP.toString(), true);
        codeExportButton = initChildButton(exportPane, CODE_EXPORT_ICON.toString(), CODE_EXPORT_TOOLTIP.toString(), true);
        //Add the exportPane VBox containing the export buttons to fileToolbarPane
        fileToolbarPane.getChildren().add(exportPane);
        //Add the exit button to fileToolbarPane
        exitButton = initChildButton(fileToolbarPane, EXIT_ICON.toString(), EXIT_TOOLTIP.toString(), false);
    }
    
    /**
     * Initializes editToolbarPane and all buttons that it will contain.
     */
    private void initEditToolbar(){
        editToolbarPane = new FlowPane();
        
        //Initialize the edit toolbar buttons and add them to editToolbarPane
        selectButton = initChildButton(editToolbarPane, SELECT_ICON.toString(), SELECT_TOOLTIP.toString(), true);
        resizeButton = initChildButton(editToolbarPane, RESIZE_ICON.toString(), RESIZE_TOOLTIP.toString(), true);
        addClassButton = initChildButton(editToolbarPane, ADD_CLASS_ICON.toString(), ADD_CLASS_TOOLTIP.toString(), true);
        addInterfaceButton = initChildButton(editToolbarPane, ADD_INTERFACE_ICON.toString(), ADD_INTERFACE_TOOLTIP.toString(), true);
        removeButton = initChildButton(editToolbarPane, REMOVE_ICON.toString(), REMOVE_TOOLTIP.toString(), true);
        undoButton = initChildButton(editToolbarPane, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), true);
        redoButton = initChildButton(editToolbarPane, REDO_ICON.toString(), REDO_TOOLTIP.toString(), true);
        
    }
    
    /**
     * Initializes viewToolbarPane and all buttons that it will contain.
     */
    private void initViewToolbar(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        viewToolbarPane = new FlowPane();
        gridPane = new HBox();
        snapPane = new HBox();
        togglePane = new VBox();
        
        //Initialize the zoom buttons and add them to viewToolbarPane
        zoomInButton = initChildButton(viewToolbarPane, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), true);
        zoomOutButton = initChildButton(viewToolbarPane, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        
        //Initialize the grid toggle button and its label
        gridToggle = new ToggleButton();
        gridLabel = new Label(props.getProperty(GRID_TOGGLE_LABEL.toString()));
        gridPane.getChildren().add(gridToggle);
        gridPane.getChildren().add(gridLabel);
        
        //Initialize the snap toggle button and its label
        snapToggle = new ToggleButton();
        snapLabel = new Label(props.getProperty(SNAP_TOGGLE_LABEL.toString()));
        snapPane.getChildren().add(snapToggle);
        snapPane.getChildren().add(snapLabel);
        
        //Add gridPane and snapPane to the overall togglePane, then add togglePane to viewToolbarPane
        togglePane.getChildren().add(gridPane);
        togglePane.getChildren().add(snapPane);
        viewToolbarPane.getChildren().add(togglePane);
    }
    
    /**
     * Initializes all the event handling in the top toolbar
     */
    private void initTopToolbarHandlers(){
        //Set up the handlers for the file toolbar using FileController
        fileController = new FileController(app);
        newButton.setOnAction(e -> {
            fileController.handleNewRequest();
        });
        loadButton.setOnAction(e -> {
            fileController.handleLoadRequest();
        });
        saveButton.setOnAction(e -> {
            fileController.handleSaveRequest();
        });
        saveAsButton.setOnAction(e -> {
            fileController.handleSaveAsRequest();
        });
        photoExportButton.setOnAction(e -> {
           fileController.handlePhotoExportRequest(); 
        });
        codeExportButton.setOnAction(e -> {
           fileController.handleCodeExportRequest(); 
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest();
        });
        
        //TODO: Set up handlers for the edit and view toolbars
    }
    
    /**
     * Initializes all the event handling in the right toolbar
     */
    private void initRightToolbarHandlers(){
        //TODO: Finish coding method
    }
    
    // Initialize the window (ie stage) putting all the controls
    // there except the canvas, which will be added the first
    // time a new file is created or loaded
    private void initWindow() {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(appTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBARS, AND CREATE AND ADD THE CANVAS
        appPane = new BorderPane();
        canvas = new Pane();
        appPane.setTop(appToolbarPane);
        appPane.setRight(componentToolbarPane);
        appPane.setCenter(canvas);
        primaryScene = new Scene(appPane);
        
        // SET THE APP ICON
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
        primaryStage.getIcons().add(new Image(appIcon));

        // NOW TIE THE SCENE TO THE WINDOW AND OPEN THE WINDOW
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    
    /**
     * This is a public helper method for initializing a simple button with
     * an icon and tooltip and placing it into a toolbar.
     * 
     * @param toolbar Toolbar pane into which to place this button.
     * 
     * @param icon Icon image file name for the button.
     * 
     * @param tooltip Tooltip to appear when the user mouses over the button.
     * 
     * @param disabled true if the button is to start off disabled, false otherwise.
     * 
     * @return A constructed, fully initialized button placed into its appropriate
     * pane container.
     */
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
    public void initStyle(){
        //TODO: CODE METHOD
    }
    
    //TODO: Finish adding methods
}
