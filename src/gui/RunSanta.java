package gui;

import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import people.Group;

/**
 * @author rjj00003
 *
 */
class RunSanta {

	private double x = 0;
	private double y = 0;
	private static boolean ran = false;

	/**
	 * @param primaryStage
	 *            the main window of the program
	 * @param func
	 *            The object to perform common functions
	 * @param groups
	 *            the collection of secret santa groups
	 */
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

			cb.getItems().add(groups.get(count).getName());

		}

		Button btnSubmit = new Button("Submit");
		btnSubmit.setId("btn");
		btnSubmit.setPickOnBounds(false);

		btnSubmit.setOnAction(event -> {
			for (Group group : groups) {
				if (cb.getValue() == group.getName()) {

					if (group.getPeople().getFirst().getMatch() != null) {

						if (func.createPopup(primaryStage, "\nSecret Santa Already Generated",
								"Matches have already been assigned for this group\nGenerate again?",
								AlertType.CONFIRMATION) == ButtonType.OK)
							;
						else
							break;
					}

					match(group, primaryStage, func);

					x = (primaryStage.getWidth() - 16);
					y = (primaryStage.getHeight() - 39);

					menu.startProgram(primaryStage, menu, x, y, func);
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

		scene.getStylesheets().add("/stylesheets/runSanta.css");

		func.createWindows(primaryStage, scene);
	}

	void match(Group group, Stage primaryStage, GUIFunctions func) {

		ArrayList<Integer> done = new ArrayList<>();

		Random rand = new Random();
		int num;

		int size = group.getPeople().size();

		for (int count = 0; count < size; count++) {

			do {
				num = rand.nextInt(group.getPeople().size());

				if ((count == (group.getPeople().size() - 1)) && (!done.contains(count))) {

					count = 0;
					done.clear();

				}
			} while (num == count || done.contains(num));

			done.add(num);
			group.getPeople().get(count).setMatch(group.getPeople().get(num));

		}

		func.createPopup(primaryStage, "\nSecret Santa Created!", "Matches have been generated!");
		ran = true;

	}
	
public boolean getRan() {
		
		return ran;
	}
}
