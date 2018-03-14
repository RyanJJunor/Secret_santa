package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author rjj00003
 *
 */
class GUIFunctions {

	 /**
	 * @param btn the button to add a hover effect to
	 */
	void changeButn(Button btn) {

		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setId("btnEnter");
			}
		});

		btn.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				btn.setId("btn");
			}
		});

	}

	 /**
	 * @param primaryStage the window to edit
	 * @param scene the current scene for the window
	 */
	void createWindows(Stage primaryStage, Scene scene) {

		primaryStage.setTitle("Secret Santa Generator");
		primaryStage.getIcons().add(new Image("/pictures/secretsanta.png"));
		primaryStage.setScene(scene);
		primaryStage.setOpacity(1);
		primaryStage.show();
	}
	 
	 /**
	 * @param mainStage the owner of the pop up
	 * @param headerText The message to be displayed in the header of the popup
	 * @param contentText The message to be displayed in the content of the popup
	 */
	void createPopup(Stage mainStage, String headerText, String contentText) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(mainStage);
			
			alert.setTitle("Secret Santa");
			alert.setHeaderText(headerText);
			alert.setContentText(contentText);
			alert.show();
	 }
	
	void createPopup(Stage mainStage, String headerText, String contentText, boolean id) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(mainStage);
		
		alert.setTitle("Secret Santa");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		
		DialogPane a = alert.getDialogPane();
		
		a.setId("match");
		alert.show();
 }
	
	
	ButtonType createPopup(Stage mainStage, String headerText, String contentText, AlertType type) {
		
		ButtonType result;
		Alert alert = new Alert(type);
		alert.initOwner(mainStage);
		
		alert.setTitle("Secret Santa");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
		result = alert.getResult();
		
		return result;
 }
	
	

}
