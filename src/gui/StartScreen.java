package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Ryan Junor
 *
 */
public class StartScreen extends Application {

	/**
	 * holds the x value for the size of the window
	 */
	private double x;
	/**
	 * holds the y value for the size of the window
	 */
	private double y;
	/**
	 * The menu object which displays the menu window
	 */
	private Menu menu = new Menu();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		
		
		GUIFunctions func = new GUIFunctions();

		GridPane grid = new GridPane();

		grid.setHgap(20);
		grid.setVgap(20);

		grid.setAlignment(Pos.CENTER);

		Button btn = new Button("Start!");
		btn.setId("btn");
		btn.setPickOnBounds(false);

		btn.setOnAction(event -> {
			x = (primaryStage.getWidth() - 16);
			y = (primaryStage.getHeight() - 39);

			System.out.println(x);
			System.out.println(y);
			menu.startProgram(primaryStage, menu, x, y, func);
		});

		grid.add(btn, 0, 0, 1, 1);

		grid.setId("grid");

		func.changeButn(btn);

		Scene scene = new Scene(grid, 885, 500);

		scene.getStylesheets().add("/stylesheets/openingScreen.css");

		func.createWindows(primaryStage, scene);
	}

}
