/*
 * 
 */
package jdcapp.gui;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdcapp.data.CustomMethod;
import static jdcapp.settings.AppPropertyType.ARGUMENTS_DISPLAY_TITLE;
import properties_manager.PropertiesManager;

/**
 *
 * @author Noah
 */
public class ArgumentsDisplaySingleton extends Stage{
    
    //TODO: FINISH CODING THIS CLASS
    //MOST OF THIS CLASS IS JUST A COPY OF AppYesNoCancelDialogueSingleton
    
    // HERE'S THE SINGLETON
    static ArgumentsDisplaySingleton singleton;
    
    // GUI CONTROLS FOR OUR DIALOG
    VBox messagePane;
    Scene messageScene;
    Button addArgButton;
    Button okayButton;
    Button cancelButton;
    ArrayList<String> oldArgs;
    ArrayList<String> newArgs;
    ArrayList<Label> argsLabels;
    ArrayList<TextField> argsTextFields;
    
    ScrollPane textScrollPane;
    VBox textVBox;
    
    String selection;
    boolean acceptable;
    
    // CONSTANT CHOICES
    public static final String OKAY = "OK";
    public static final String CANCEL = "Cancel";
    public static final String ADD_ARGUMENT = "Add Argument";
    public static final double DEFAULT_WIDTH = 300;
    public static final double DEFAULT_HEIGHT = 300;
    
    /**
     * Note that the constructor is private since it follows
     * the singleton design pattern.
     * 
     * @param primaryStage The owner of this modal dialog.
     */
    private ArgumentsDisplaySingleton() {}
    
    /**
     * The static accessor method for this singleton.
     * 
     * @return The singleton object for this type.
     */
    public static ArgumentsDisplaySingleton getSingleton() {
	if (singleton == null)
	    singleton = new ArgumentsDisplaySingleton();
	return singleton;
    }
	
    /**
     * This method initializes the singleton for use.
     * 
     * @param primaryStage The window above which this
     * dialog will be centered.
     */
    public void init(Stage primaryStage) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        //Initialize arrays
        argsLabels = new ArrayList<>();
        argsTextFields = new ArrayList<>();
        oldArgs = new ArrayList<>();
        newArgs = new ArrayList<>();
        
        textVBox = new VBox();
        textVBox.setSpacing(5);
        textScrollPane = new ScrollPane(textVBox);

        //BUTTONS
        okayButton = new Button(OKAY);
        cancelButton = new Button(CANCEL);
        addArgButton = new Button(ADD_ARGUMENT);
	
	// MAKE THE EVENT HANDLER FOR THESE BUTTONS
        EventHandler okayHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            ArgumentsDisplaySingleton.this.selection = sourceButton.getText();
            newArgs.clear();
            for(TextField t : argsTextFields){
                if(!(t.getText().equals("") || t.getText().isEmpty()))
                    newArgs.add(t.getText());
            }
            ArgumentsDisplaySingleton.this.hide();
        };
        
        // MAKE THE EVENT HANDLER FOR THESE BUTTONS
        EventHandler cancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            ArgumentsDisplaySingleton.this.selection = sourceButton.getText();
            ArgumentsDisplaySingleton.this.hide();
        };
        
        EventHandler addArgHandler = (e -> {
            argsLabels.add(new Label("Arg" + argsLabels.size()));
            TextField newText = new TextField("");
            newText.setOnKeyPressed(ke -> {
                if(ke.getCode().equals(KeyCode.ENTER)){
                    checkText(newText);
                    addArgButton.requestFocus();
                }
            });
            newText.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> change, Boolean oldFocus, Boolean newFocus){
                    if(!newFocus)
                        checkText(newText);
                }
            });
            argsTextFields.add(newText);
            updateSingleton();
        });
        
	// AND THEN REGISTER THEM TO RESPOND TO INTERACTIONS
        okayButton.setOnAction(okayHandler);
        cancelButton.setOnAction(cancelHandler);
        addArgButton.setOnAction(addArgHandler);

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(okayButton);
        buttonBox.getChildren().add(cancelButton);
        buttonBox.setSpacing(10);
        
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setFillWidth(true);
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(addArgButton);
        messagePane.getChildren().add(textScrollPane);
        messagePane.getChildren().add(buttonBox);
        
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);

        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        this.setScene(messageScene);
        this.setWidth(DEFAULT_WIDTH);
        this.setHeight(DEFAULT_HEIGHT);
        acceptable = true;
        okayButton.setDisable(false);
    }

    /**
     * Accessor method for getting the old list of arguments.
     * @return 
     */
    public ArrayList<String> getOldArgs() {
        return oldArgs;
    }
    
    /**
     * Accessor method for getting the old list of arguments.
     * @return 
     */
    public ArrayList<String> getNewArgs() {
        return newArgs;
    }
 
    /**
     * This method loads the old arguments into the text fields,
     * then pops open the dialog.
     * 
     * @param m
     */
    public void show(CustomMethod m) {
	// Clear out any old data
        argsLabels.clear();
        argsTextFields.clear();
        textVBox.getChildren().clear();
        
        // Populate the oldArgs, argsLabels, and argsTextFields arrays
	for(int i = 0; i < m.getArguments().size(); i++){
            String s = m.getArguments().get(i);
            oldArgs.add(s);
            argsLabels.add(new Label("Arg" + i));
            
            TextField temp = new TextField(s);
            temp.setOnKeyPressed(e -> {
                if(e.getCode().equals(KeyCode.ENTER)){
                    checkText(temp);
                    addArgButton.requestFocus();
                }
            });
            temp.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> change, Boolean oldFocus, Boolean newFocus){
                    if(!newFocus)
                        checkText(temp);
                }
            });
            argsTextFields.add(temp);
        }
        int numArgs = m.getArguments().size();
        while(numArgs < 5){
            String s = "";
            argsLabels.add(new Label("Arg" + numArgs));
            
            TextField temp = new TextField(s);
            temp.setOnKeyPressed(e -> {
                if(e.getCode().equals(KeyCode.ENTER)){
                    checkText(temp);
                    addArgButton.requestFocus();
                }
            });
            temp.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> change, Boolean oldFocus, Boolean newFocus){
                    if(!newFocus)
                        checkText(temp);
                }
            });
            argsTextFields.add(temp);
            numArgs++;
        }
	
        for(int i = 0; i < argsLabels.size(); i++){
            HBox temp = new HBox();
            temp.getChildren().add(argsLabels.get(i));
            temp.getChildren().add(argsTextFields.get(i));
            temp.setSpacing(10);
            textVBox.getChildren().add(temp);
        }
        
        setTitle(PropertiesManager.getPropertiesManager().getProperty(ARGUMENTS_DISPLAY_TITLE.toString()));
	
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }
    
    public void updateSingleton(){
        HBox temp = new HBox();
        temp.getChildren().add(argsLabels.get(argsLabels.size() - 1));
        temp.getChildren().add(argsTextFields.get(argsTextFields.size() - 1));
        temp.setSpacing(10);
        textVBox.getChildren().add(temp);
    }
    
    public void checkText(TextField t){
        if(!(t.getText().contains(" : ") || t.getText().equals("") || t.getText().isEmpty())){
            t.setStyle("-fx-text-fill: red");
            acceptable = false;
            okayButton.setDisable(true);
        }
        else{
            t.setStyle("-fx-text-fill: black");
            checkAllArgs();
        }
    }
    
    public void checkAllArgs(){
        boolean okay = true;
        for(TextField t : argsTextFields){
            if(!(t.getText().contains(" : ") || t.getText().equals("") || t.getText().isEmpty())){
                okay = false;
            }
        }
        if(okay){
            acceptable = true;
            okayButton.setDisable(false);
        }
    }
}
