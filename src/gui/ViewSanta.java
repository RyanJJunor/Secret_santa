package gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import people.Group;
import people.Person;


class ViewSanta {

	private double x = 0;
	private double y = 0;

	void createWindow(Stage primaryStage, Menu menu, GUIFunctions func, ArrayList<Group> groups) {

		GridPane grid = new GridPane();
		grid.setId("grid");

		grid.setHgap(50);
		grid.setVgap(30);

		grid.setAlignment(Pos.CENTER);

		Label label = new Label("Please select your group");
		HBox labelBox = new HBox(0);
		labelBox.setAlignment(Pos.CENTER);
		labelBox.getChildren().add(label);

		ComboBox<String> cb = new ComboBox<>();
		cb.setId("comboBox");
		cb.setVisibleRowCount(5);

		HBox cbBox = new HBox();
		cbBox.setAlignment(Pos.CENTER);
		cbBox.getChildren().add(cb);

		for (int count = 0; count < groups.size(); count++) {

			if (groups.get(count).getPeople().getFirst().getMatch() != null) {

				cb.getItems().add(groups.get(count).getName());
			}
		}

		Button btnSubmit = new Button("Submit");
		btnSubmit.setId("btn");
		btnSubmit.setPickOnBounds(false);

		btnSubmit.setOnAction(event -> {

			for (Group group : groups) {
				if (cb.getValue() == group.getName()) {

					logIn(primaryStage, func, menu, group);
					
					break;
				}
			}
			
			

		});

		Button btnBack = new Button("Back");
		btnBack.setId("btn");
		btnBack.setPickOnBounds(false);

		btnBack.setOnAction(event -> {

			x = (primaryStage.getWidth() - 16);
			y = (primaryStage.getHeight() - 39);

			menu.startProgram(primaryStage, menu, x, y, func);

		});

		HBox btnBox = new HBox(25);
		btnBox.setAlignment(Pos.CENTER);
		btnBox.getChildren().addAll(btnSubmit, btnBack);

		func.changeButn(btnSubmit);
		func.changeButn(btnBack);

		grid.add(labelBox, 0, 0);
		grid.add(cbBox, 0, 1);
		grid.add(btnBox, 0, 2);

		Scene scene = new Scene(grid, (primaryStage.getWidth() - 16), (primaryStage.getHeight() - 39));

		scene.getStylesheets().add("/stylesheets/viewSanta.css");

		func.createWindows(primaryStage, scene);
	}

	void logIn(Stage primaryStage, GUIFunctions func, Menu menu, Group group ) {

		GridPane grid = new GridPane();

		grid.setId("grid");

		grid.setHgap(50);
		grid.setVgap(20);

		grid.setAlignment(Pos.CENTER);

		Label label = new Label("Please enter your name and password");
		label.setId("title");

		TextField textName = new TextField();
		textName.setPromptText("Username");
		textName.setId("text");

		textName.lengthProperty().addListener((observable, oldNum, newNum) -> {

			if (newNum.intValue() > oldNum.intValue()) {
				// Check if the new name is greater than 15
				if (textName.getText().length() >= 15) {

					// if it's 16th character then just setText to previous
					// one
					textName.setText(textName.getText().substring(0, 15));
				}

			}

		});

		PasswordField textPass = new PasswordField();
		textPass.setPromptText("Password");
		textPass.setId("text");

		Button btn = new Button("Submit");
		btn.setId("btn");
		btn.setPickOnBounds(false);
		func.changeButn(btn);

		btn.setOnAction(event -> {
			
			boolean found = false;
			
			//Scanner read = new Scanner("src/files/" + group.getName() + ".txt");
			Scanner read = null;
			try {
				read = new Scanner(Paths.get("src/files/" + group.getName() + ".txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			do {
				

				if((read.nextLine().matches(textName.getText().trim())) && (read.nextLine().matches(textPass.getText()))) {
					
					int count = 0;
					
					for(Person p : group.getPeople()) {
						if(textName.getText().trim().matches(p.getUsername())) {
							
							func.createPopup(primaryStage, "\nYour match is: " 
							+group.getPeople().get(count).getMatch().getUsername(), "", true);
							found = true;
							read.reset();
							break;
						}
						else {
							count++;
						}
						
						
					}
					
						break;	
				}

					}while(read.hasNext());
			
			if(found == false) {
				
				func.createPopup(primaryStage, "\nIncorrect username or password", "You have entered an incorrect username or password, please try again"
						);
				
			}
			read.close();


		});

		textName.setOnAction(event -> btn.fire());

		textPass.setOnAction(event -> btn.fire());
		
		Button btnBack = new Button("Back");
		btnBack.setId("btn");
		btnBack.setPickOnBounds(false);
		func.changeButn(btnBack);

		btnBack.setOnAction(event -> {
			
			x = (primaryStage.getWidth() - 16);
			y = (primaryStage.getHeight() - 39);
			
			menu.startProgram(primaryStage, menu, x, y, func);

		});
		
		HBox textHB = new HBox();
		textHB.getChildren().addAll(label);
		textHB.setAlignment(Pos.CENTER);

		HBox hb = new HBox(25);
		hb.getChildren().addAll(btn, btnBack);
		hb.setAlignment(Pos.CENTER);

		grid.add(textHB, 0, 0, 3, 1);
		grid.add(textName, 0, 1, 3, 1);
		grid.add(textPass, 0, 2, 3, 1);
		grid.add(hb, 0, 4, 3, 1);

		Scene scene = new Scene(grid, (primaryStage.getWidth() - 16), (primaryStage.getHeight() - 39));
		btn.requestFocus();

		scene.getStylesheets().add("/stylesheets/viewSantaMatch.css");

		func.createWindows(primaryStage, scene);
	}
}
