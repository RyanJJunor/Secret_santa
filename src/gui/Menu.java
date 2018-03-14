package gui;

import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * @author rjj00003
 *
 */
class Menu {

	/**
	 * holds the x value for the size of the window
	 */
	private double x1;
	/**
	 * holds the y value for the size of the window
	 */
	private double y1;

	/**
	 * creates the display for the menu window
	 * 
	 * @param primaryStage the main window
	 */
	void startProgram(Stage primaryStage, Menu menu, double x, double y, GUIFunctions func){

		
		CreateSanta create = new CreateSanta();
		RunSanta rs = new RunSanta();
		
		
		GridPane grid1 = new GridPane();
		grid1.setId("grid");

		grid1.setHgap(50);
		grid1.setVgap(20);

		grid1.setAlignment(Pos.CENTER);

		
		//creating the button for creating a new secret santa group
		Button newSecret = new Button("Create a new secret santa");
		newSecret.setId("btn");
		newSecret.setPickOnBounds(false);
		func.changeButn(newSecret);
		newSecret.setOnAction(event -> {
			x1 = (primaryStage.getWidth() - 16);
			y1 = (primaryStage.getHeight() - 39);
			try {
				

				create.createWindow(primaryStage, menu, x1, y1, func);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		//Creating the button for running a secret santa
		Button runSecret = new Button("Run your secret santa");
		runSecret.setId("btn");
		runSecret.setPickOnBounds(false);
		func.changeButn(runSecret);
		runSecret.setOnAction(event -> {
			
			if(!create.getGroups().isEmpty()) {
			rs.createWindow(primaryStage, menu, func, create.getGroups());
			}
			else {
	            
	            func.createPopup(primaryStage, "\nError:", "No groups found");
				
			}
				
		});

		//creating a button to view a secret santa
		Button viewSecret = new Button("See who you got");
		viewSecret.setId("btn");
		viewSecret.setPickOnBounds(false);
		func.changeButn(viewSecret);
		viewSecret.setOnAction(event -> {
			
			if(rs.getRan() == true) {
				ViewSanta vs = new ViewSanta();
				vs.createWindow(primaryStage, menu, func, create.getGroups());
				}
				else {
		            
		            func.createPopup(primaryStage, "\nError:", "No matches have been generated");
					
				}
			
		
		});

		grid1.addRow(0, newSecret, runSecret, viewSecret);

		Scene scene = new Scene(grid1, x, y);
		scene.getStylesheets().add("/stylesheets/menu.css");

		func.createWindows(primaryStage, scene);
	}

}
