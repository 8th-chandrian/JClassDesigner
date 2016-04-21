/*
 * 
 */
package jdcapp.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jdcapp.JDCApp;
import jdcapp.controller.ComponentController;
import jdcapp.controller.EditController;
import jdcapp.controller.FileController;
import jdcapp.controller.ViewController;
import jdcapp.controller.WorkspaceController;
import jdcapp.data.CustomClassWrapper;
import jdcapp.data.DataManager;
import jdcapp.data.JDCAppState;
import jdcapp.file.FileManager;
import static jdcapp.settings.AppPropertyType.ADD_CLASS_ICON;
import static jdcapp.settings.AppPropertyType.ADD_CLASS_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ADD_ELEMENT_ICON;
import static jdcapp.settings.AppPropertyType.ADD_INTERFACE_ICON;
import static jdcapp.settings.AppPropertyType.ADD_INTERFACE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ADD_METHOD_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ADD_VARIABLE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.APP_LOGO;
import static jdcapp.settings.AppPropertyType.CLASS_NAME_LABEL;
import static jdcapp.settings.AppPropertyType.CODE_EXPORT_ICON;
import static jdcapp.settings.AppPropertyType.CODE_EXPORT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.EXIT_ICON;
import static jdcapp.settings.AppPropertyType.EXIT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.GRID_TOGGLE_LABEL;
import static jdcapp.settings.AppPropertyType.LOAD_ICON;
import static jdcapp.settings.AppPropertyType.LOAD_TOOLTIP;
import static jdcapp.settings.AppPropertyType.METHOD_LABEL;
import static jdcapp.settings.AppPropertyType.NEW_ICON;
import static jdcapp.settings.AppPropertyType.NEW_TOOLTIP;
import static jdcapp.settings.AppPropertyType.PACKAGE_NAME_LABEL;
import static jdcapp.settings.AppPropertyType.PARENT_NAME_LABEL;
import static jdcapp.settings.AppPropertyType.PHOTO_EXPORT_ICON;
import static jdcapp.settings.AppPropertyType.PHOTO_EXPORT_TOOLTIP;
import static jdcapp.settings.AppPropertyType.REDO_ICON;
import static jdcapp.settings.AppPropertyType.REDO_TOOLTIP;
import static jdcapp.settings.AppPropertyType.REMOVE_ELEMENT_ICON;
import static jdcapp.settings.AppPropertyType.REMOVE_ICON;
import static jdcapp.settings.AppPropertyType.REMOVE_METHOD_TOOLTIP;
import static jdcapp.settings.AppPropertyType.REMOVE_TOOLTIP;
import static jdcapp.settings.AppPropertyType.REMOVE_VARIABLE_TOOLTIP;
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
import static jdcapp.settings.AppPropertyType.VARIABLE_LABEL;
import static jdcapp.settings.AppPropertyType.ZOOM_IN_ICON;
import static jdcapp.settings.AppPropertyType.ZOOM_IN_TOOLTIP;
import static jdcapp.settings.AppPropertyType.ZOOM_OUT_ICON;
import static jdcapp.settings.AppPropertyType.ZOOM_OUT_TOOLTIP;
import static jdcapp.settings.AppStartupConstants.FILE_PROTOCOL;
import static jdcapp.settings.AppStartupConstants.PATH_IMAGES;
import org.controlsfx.control.CheckComboBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Noah
 */
public class WorkspaceManager {
    
    static final String TOP_TOOLBAR_CLASS = "top_toolbar";
    static final String TOP_SUB_TOOLBAR_CLASS = "top_sub_toolbar";
    static final String TOP_VIEW_TOOLBAR_CLASS = "top_view_toolbar";
    static final String TOP_BUTTON_LARGE_CLASS = "top_button_large";
    static final String TOP_BUTTON_SMALL_CLASS = "top_button_small";
    static final String TOP_CHECK_VBOX_CLASS = "top_check_vbox";
    static final String TOP_EXPORT_VBOX_CLASS = "top_check_vbox";
    static final String RIGHT_GRIDPANE_CLASS = "right_gridpane";
    static final String RIGHT_VBOX_CLASS = "right_vbox";
    static final String COMPONENT_BUTTON_FLOWPANE_CLASS = "component_button_flowpane";
    static final String COMPONENT_BUTTON_CLASS = "component_button";
    static final String LARGE_LABEL_CLASS = "large_label";
    static final String WORKSPACE_CLASS = "workspace";
    static final String WORKSPACE_SCROLL_PANE_CLASS = "workspace_scroll_pane";
    static final String TABLE_VIEW_CLASS = "table_view";
    
    static final double DEFAULT_WIDTH = 2000;
    static final double DEFAULT_HEIGHT = 1000;
    
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
    FlowPane appToolbarPane;
    
    //The three subtoolbars in the top toolbar
    HBox fileToolbarPane;
    HBox editToolbarPane;
    HBox viewToolbarPane;
    
    //The subpanes for the two export buttons and the two toggle buttons (with HBoxes for toggle button labels)
    VBox exportPane;
    VBox checkPane;
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
    CheckBox gridCheck;
    CheckBox snapCheck;
    Label gridLabel;
    Label snapLabel;
    
    //The right side toolbar of the app
    VBox componentToolbarPane;
    
    //The grid for holding class, package, and parent data
    GridPane infoGrid;
    
    //The label and control for class
    Label classNameLabel;
    TextField classNameText;
    
    //The label and control for package
    Label packageNameLabel;
    TextField packageNameText;
    
    //The label and control for parent
    Label parentNameLabel;
    CheckComboBox parentComboBox; //TODO: Figure out if this is how you want to implement parent selection
    
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
    ScrollPane canvasScrollPane;
    boolean canvasActivated;
    
    //The effect to be used on the selected CustomClass
    Effect highlightedEffect;
    
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
        canvasActivated = false;
        canvas = new Pane();
        canvasScrollPane = new ScrollPane(canvas);
        
        //Initializes the toolbars and various controls=
        initTopToolbar();
        initRightToolbar();
        
        //Initializes the event handling for the top and right toolbars, as well as the workspace
        initTopToolbarHandlers();
        initRightToolbarHandlers();
        
        //Starts the window
        initWindow();
        
        //Initializes highlightedEffect
        DropShadow dropShadowEffect = new DropShadow();
	dropShadowEffect.setOffsetX(0.0f);
	dropShadowEffect.setOffsetY(0.0f);
	dropShadowEffect.setSpread(1.0);
	dropShadowEffect.setColor(Color.YELLOW);
	dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
	dropShadowEffect.setRadius(3);
	highlightedEffect = dropShadowEffect;
    }
    
    /**
     * Accessor for the app's primary stage's scene
     * @return 
     *      the primary scene
     */
    public Scene getPrimaryScene(){ return primaryScene; }
    
    /**
     * Accessor method for getting this application's window,
     * which is the primary stage within which the full GUI will be placed.
     * 
     * @return This application's primary stage (i.e. window).
     */    
    public Stage getWindow() { return primaryStage; }
    
    /**
     * Calls methods to initalize the various buttons and controls in the top toolbar.
     * Note that initTopToolbar does not set up any event handling, this is done by
     * initHandlers method. When this method exits, the top toolbar will be fully initialized
     * and added to appPane.
     */
    private void initTopToolbar(){
        appToolbarPane = new FlowPane();
        
        initFileToolbar();
        initEditToolbar();
        initViewToolbar();
        
        appToolbarPane.getChildren().add(fileToolbarPane);
        appToolbarPane.getChildren().add(editToolbarPane);
        appToolbarPane.getChildren().add(viewToolbarPane);
    }
    
    private void initRightToolbar(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        componentToolbarPane = new VBox();
        infoGrid = new GridPane();
        infoGrid.setGridLinesVisible(false);
        
        //Initialize the class, package, and parent labels
        classNameLabel = new Label(props.getProperty(CLASS_NAME_LABEL.toString()));
        packageNameLabel = new Label(props.getProperty(PACKAGE_NAME_LABEL.toString()));
        parentNameLabel = new Label(props.getProperty(PARENT_NAME_LABEL.toString()));
        
        //Initialize the class and package text fields
        //NOTE: the text fields will be disabled until a new class/interface is made or an old one is selected
        classNameText = new TextField();
        classNameText.setDisable(true);
        packageNameText = new TextField();
        packageNameText.setDisable(true);
        
        parentComboBox = new CheckComboBox();
        //TODO: Set parentComboBox to display a list of potential parent classes
        
        //Populate infoGrid with our class, package, and parent labels and controls
        infoGrid.add(classNameLabel, 0, 0);
        infoGrid.add(classNameText, 1, 0);
        infoGrid.add(packageNameLabel, 0, 1);
        infoGrid.add(packageNameText, 1, 1);
        infoGrid.add(parentNameLabel, 0, 2);
        infoGrid.add(parentComboBox, 1, 2);
        
        componentToolbarPane.getChildren().add(infoGrid);
        
        //Initialize the variable controls and tableview
        variablePane = new FlowPane();
        variableLabel = new Label(props.getProperty(VARIABLE_LABEL.toString()));
        variablePane.getChildren().add(variableLabel);
        addVariable = initChildButton(variablePane, ADD_ELEMENT_ICON.toString(), ADD_VARIABLE_TOOLTIP.toString(), true);
        removeVariable = initChildButton(variablePane, REMOVE_ELEMENT_ICON.toString(), REMOVE_VARIABLE_TOOLTIP.toString(), true);
        
        componentToolbarPane.getChildren().add(variablePane);
        
        variableScrollPane = new ScrollPane();
        variableTableView = new TableView();
        variableScrollPane.setContent(variableTableView);
        
        componentToolbarPane.getChildren().add(variableScrollPane);
        
        //Initialize the method controls and tableview
        methodPane = new FlowPane();
        methodLabel = new Label(props.getProperty(METHOD_LABEL.toString()));
        methodPane.getChildren().add(methodLabel);
        addMethod = initChildButton(methodPane, ADD_ELEMENT_ICON.toString(), ADD_METHOD_TOOLTIP.toString(), true);
        removeMethod = initChildButton(methodPane, REMOVE_ELEMENT_ICON.toString(), REMOVE_METHOD_TOOLTIP.toString(), true);
        
        componentToolbarPane.getChildren().add(methodPane);
        
        methodScrollPane = new ScrollPane();
        methodTableView = new TableView();
        methodScrollPane.setContent(methodTableView);
        
        componentToolbarPane.getChildren().add(methodScrollPane);
        
    }
    
    /**
     * Initializes fileToolbarPane and all buttons that it will contain.
     */
    private void initFileToolbar(){
        fileToolbarPane = new HBox();
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
        editToolbarPane = new HBox();
        
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
        
        viewToolbarPane = new HBox();
        gridPane = new HBox();
        snapPane = new HBox();
        checkPane = new VBox();
        
        //Initialize the zoom buttons and add them to viewToolbarPane
        zoomInButton = initChildButton(viewToolbarPane, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), true);
        zoomOutButton = initChildButton(viewToolbarPane, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), true);
        
        //Initialize the grid toggle button and its label
        gridCheck = new CheckBox();
        gridCheck.setDisable(true);
        gridLabel = new Label(props.getProperty(GRID_TOGGLE_LABEL.toString()));
        gridPane.getChildren().add(gridCheck);
        gridPane.getChildren().add(gridLabel);
        
        //Initialize the snap toggle button and its label
        snapCheck = new CheckBox();
        snapCheck.setDisable(true);
        snapLabel = new Label(props.getProperty(SNAP_TOGGLE_LABEL.toString()));
        snapPane.getChildren().add(snapCheck);
        snapPane.getChildren().add(snapLabel);
        
        //Add gridPane and snapPane to the overall togglePane, then add togglePane to viewToolbarPane
        checkPane.getChildren().add(gridPane);
        checkPane.getChildren().add(snapPane);
        viewToolbarPane.getChildren().add(checkPane);
    }
    
    /**
     * Initializes all the event handling in the top toolbar and workspace
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
        
        //Set up the handlers for the edit toolbar using EditController
        editController = new EditController(app);
        selectButton.setOnAction(e -> {
            editController.handleSelectRequest();
        });
        resizeButton.setOnAction(e -> {
            editController.handleResizeRequest();
        });
        addClassButton.setOnAction(e -> {
            editController.handleAddClassRequest();
        });
        addInterfaceButton.setOnAction(e -> {
            editController.handleAddInterfaceRequest();
        });
        removeButton.setOnAction(e -> {
           editController.handleRemoveRequest(); 
        });
        undoButton.setOnAction(e -> {
           editController.handleUndoRequest(); 
        });
        redoButton.setOnAction(e -> {
            editController.handleRedoRequest();
        });
        
        //Set up the handlers for the view toolbar using ViewController
        viewController = new ViewController(app);
        zoomInButton.setOnAction(e -> {
            viewController.handleZoomInRequest();
        });
        zoomOutButton.setOnAction(e -> {
            viewController.handleZoomOutRequest();
        });
        gridCheck.setOnAction(e -> {
            viewController.handleGridCheckRequest(gridCheck.isSelected());
        });
        snapCheck.setOnAction(e -> {
            viewController.handleSnapCheckRequest(snapCheck.isSelected());
        });
        
        //Set up the canvas mouse event handlers using WorkspaceController
        workspaceController = new WorkspaceController(app);
        canvas.setOnMouseEntered(e -> {
            workspaceController.handleMouseEntered();
        });
        canvas.setOnMouseExited(e -> {
            workspaceController.handleMouseExited();
        });
        canvas.setOnMousePressed(e -> {
            workspaceController.handleMousePressed(e.getX(), e.getY());
        });
        canvas.setOnMouseDragged(e -> {
            workspaceController.handleMouseDragged(e.getX(), e.getY());
        });
        canvas.setOnMouseReleased(e -> {
            workspaceController.handleMouseReleased(e.getX(), e.getY());
        });
    }
    
    /**
     * Initializes all the event handling in the right toolbar
     */
    private void initRightToolbarHandlers(){     
        //Set up the handlers for the component toolbar using ComponentController
        componentController = new ComponentController(app);
        
        classNameText.textProperty().addListener(e -> {
            componentController.handleClassNameTextEdited(classNameText.getText());
        });
        packageNameText.textProperty().addListener(e -> {
            componentController.handlePackageNameTextEdited(packageNameText.getText());
        });
        
        //TODO: Finish adding handlers for other events
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
        appPane.setTop(appToolbarPane);
        appPane.setRight(componentToolbarPane);
        appPane.setCenter(canvasScrollPane);
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
        
        //Initialize style for top toolbar
        appToolbarPane.getStyleClass().add(TOP_TOOLBAR_CLASS);
        fileToolbarPane.getStyleClass().add(TOP_SUB_TOOLBAR_CLASS);
        editToolbarPane.getStyleClass().add(TOP_SUB_TOOLBAR_CLASS);
        viewToolbarPane.getStyleClass().add(TOP_VIEW_TOOLBAR_CLASS);
        
        //Initialize style for large top buttons
        newButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        loadButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        saveButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        saveAsButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        exitButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        selectButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        resizeButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        addClassButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        addInterfaceButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        removeButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        undoButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        redoButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        zoomInButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        zoomOutButton.getStyleClass().add(TOP_BUTTON_LARGE_CLASS);
        
        //Initialize style for small top buttons and checkboxes
        codeExportButton.getStyleClass().add(TOP_BUTTON_SMALL_CLASS);
        photoExportButton.getStyleClass().add(TOP_BUTTON_SMALL_CLASS);
        exportPane.getStyleClass().add(TOP_EXPORT_VBOX_CLASS);
        checkPane.getStyleClass().add(TOP_CHECK_VBOX_CLASS);
        
        //Initialize style for right toolbar
        infoGrid.getStyleClass().add(RIGHT_GRIDPANE_CLASS);
        classNameLabel.getStyleClass().add(LARGE_LABEL_CLASS);
        componentToolbarPane.getStyleClass().add(RIGHT_VBOX_CLASS);
        variablePane.getStyleClass().add(COMPONENT_BUTTON_FLOWPANE_CLASS);
        methodPane.getStyleClass().add(COMPONENT_BUTTON_FLOWPANE_CLASS);
        addVariable.getStyleClass().add(COMPONENT_BUTTON_CLASS);
        removeVariable.getStyleClass().add(COMPONENT_BUTTON_CLASS);
        addMethod.getStyleClass().add(COMPONENT_BUTTON_CLASS);
        removeMethod.getStyleClass().add(COMPONENT_BUTTON_CLASS);
        
        //Add first row to variable table and method table, and add horizontal scrolling to scroll panes
        variableScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        variableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        methodScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        methodScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        //TODO: FIX STYLE FOR THIS ASAP
        variableTableView.getColumns().add(new TableColumn("Name"));
        variableTableView.getColumns().add(new TableColumn("Type"));
        variableTableView.getColumns().add(new TableColumn("Static"));
        variableTableView.getColumns().add(new TableColumn("Access"));
        variableTableView.getStyleClass().add(TABLE_VIEW_CLASS);
        
        methodTableView.getColumns().add(new TableColumn("Name"));
        methodTableView.getColumns().add(new TableColumn("Type"));
        methodTableView.getColumns().add(new TableColumn("Static"));
        methodTableView.getColumns().add(new TableColumn("Access"));
        methodTableView.getStyleClass().add(TABLE_VIEW_CLASS);
        
        //Initialize style for the canvas
        canvas.getStyleClass().add(WORKSPACE_CLASS);
        canvas.setMinSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        canvasScrollPane.getStyleClass().add(WORKSPACE_SCROLL_PANE_CLASS);
        canvasScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        canvasScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }
    
    /**
     * Activates the canvas when a new file is created or a file is loaded for editing
     */
    public void activateCanvas(){
        canvasActivated = true;
    }
    
    /**
     * Indicates whether or not the canvas is activated
     * @return 
     */
    public boolean isCanvasActivated(){
        return canvasActivated;
    }
    
    /**
     * Activates the workspace controls so that they can be used to edit the newly-loaded or created
     * file. This method is called by handleNewRequest() and handleLoadRequest().
     */
    public void activateWorkspaceControls(){
        photoExportButton.setDisable(false);
        codeExportButton.setDisable(false);
        selectButton.setDisable(false);
        addClassButton.setDisable(false);
        addInterfaceButton.setDisable(false);
        zoomInButton.setDisable(false);
        zoomOutButton.setDisable(false);
        gridCheck.setDisable(false);
        snapCheck.setDisable(false);
    }

    public void updateFileToolbarControls(boolean saved) {
        saveButton.setDisable(saved);
        saveAsButton.setDisable(false);
        //TODO: Finish coding method
    }
    
    /**
     * Updates the edit toolbar controls so that proper buttons are enabled/disabled
     */
    public void updateEditToolbarControls(){
        DataManager dataManager = app.getDataManager();
        
        if(dataManager.getState() == JDCAppState.SELECTING && dataManager.getSelectedClass() != null){
            selectButton.setDisable(true);
            resizeButton.setDisable(false);
            removeButton.setDisable(false);
        }
        else if(dataManager.getState() == JDCAppState.SELECTING){
            selectButton.setDisable(true);
            resizeButton.setDisable(true);
            removeButton.setDisable(true);
        }
        else if(dataManager.getState() == JDCAppState.RESIZING_NOTHING){
            selectButton.setDisable(false);
            resizeButton.setDisable(true);
            removeButton.setDisable(false);
        }
        
        //TODO: Possibly finish adding cases (if there are any more cases to be handled)
    }
    
    public Pane getCanvas(){
        return canvas;
    }

    /**
     * To be called when loading all CustomClassWrapper objects into canvas
     */
    public void reloadWorkspace() {
        DataManager dataManager = app.getDataManager();
        canvas.getChildren().clear();
        
        for(CustomClassWrapper c : dataManager.getClasses()){
            c.toDisplay();
            if(c == dataManager.getSelectedClass()){
                //Sets the outline rectangle to highlighted
                c.getOutlineRectangle().setEffect(highlightedEffect);
            }
            else{
                //Makes sure that only one CustomClass is highlighted at a time
                c.getOutlineRectangle().setEffect(null);
            }
            canvas.getChildren().add(c);
        }
    }
    
    /**
     * To be called after any change is made to the selected class.
     * Note: this method has the added effect of moving the selected class to the back of
     * the arraylist/the front of the canvas
     * 
     * Note: this method ensures that text displayed as red (error text) remains red
     */
    public void reloadSelectedClass(){
        DataManager dataManager = app.getDataManager();
        boolean isRed = false;
        if(dataManager.getSelectedClass().getNameText().getFill().equals(Color.RED))
            isRed = true;

        //Remove the selected class from the canvas and the arraylist of classes
        canvas.getChildren().remove(dataManager.getSelectedClass());
        dataManager.getClasses().remove(dataManager.getSelectedClass());
        
        //Reload the display data in CustomClassWrapper and add back into canvas and arraylist
        dataManager.getSelectedClass().toDisplay();
        dataManager.getSelectedClass().getOutlineRectangle().setEffect(highlightedEffect);
        if(isRed)
            dataManager.getSelectedClass().getNameText().setFill(Color.RED);
        canvas.getChildren().add(dataManager.getSelectedClass());
        dataManager.getClasses().add(dataManager.getSelectedClass());
    }
    
    /**
     * To be called just before selecting a new class.
     */
    public void unhighlightSelectedClass(){
        DataManager dataManager = app.getDataManager();
        dataManager.getSelectedClass().getOutlineRectangle().setEffect(null);
    }
    
    /**
     * To be called just after selecting a new class.
     */
    public void highlightSelectedClass(){
        DataManager dataManager = app.getDataManager();
        dataManager.getSelectedClass().getOutlineRectangle().setEffect(highlightedEffect);
    }
    
    /**
     * To be called just after selecting a new class.
     */
    public void loadSelectedClassData(){
        DataManager dataManager = app.getDataManager();
        
        classNameText.setText(dataManager.getSelectedClass().getData().getClassName());
        packageNameText.setText(dataManager.getSelectedClass().getData().getPackageName());
        
        classNameText.setDisable(false);
        packageNameText.setDisable(false);
    }
    
    /**
     * To be called just after selecting a new class.
     */
    public void wipeSelectedClassData(){
        DataManager dataManager = app.getDataManager();
        
        classNameText.setText("");
        packageNameText.setText("");
        
        classNameText.setDisable(true);
        packageNameText.setDisable(true);
    }
    
    //TODO: Finish adding methods
    
    
}
