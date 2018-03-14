package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import people.Group;
import people.Person;

class CreateSanta {

	/**
	 * the number of participants in the secret santa group
	 */
	private int num; // the number of participants in the secret santa
	/**
	 * counter to keep track of what person the details are being entered for
	 */
	private int count = 0;
	/**
	 * holds the x value for the size of the window
	 */
	private double x1;
	/**
	 * holds the y value for the size of the window
	 */
	private double y1;
	/**
	 * the name of the group
	 */
	private String gName;
	/**
	 * the collection of secret santa groups
	 */

	private String nameRegEx = "[\\W&&[\\S]]";
	private String nameLengthRegEx = "\\w{2,}";

	private static ArrayList<Group> groups = new ArrayList<>();

	/**
	 * @param primaryStage
	 *            the main window of the program
	 * @param menu
	 *            the object for creating the menu window
	 * @param x
	 *            holds the x value for the size of the window
	 * @param y
	 *            holds the y value for the size of the window
	 * @param func
	 *            the object for performing common functions
	 * @throws FileNotFoundException
	 */
	void createWindow(Stage primaryStage, Menu menu, double x, double y, GUIFunctions func)
			throws FileNotFoundException {

		GridPane grid = new GridPane();
		grid.setId("grid");

		grid.setHgap(50);
		grid.setVgap(20);

		grid.setAlignment(Pos.CENTER);

		Label label = new Label("Please enter a group name");
		label.setId("title");

		HBox hbGroup = new HBox();
		hbGroup.getChildren().add(label);
		hbGroup.setAlignment(Pos.CENTER);

		TextField text = new TextField();
		text.setPromptText("Group Name");
		text.setId("text");

		text.lengthProperty().addListener((observable, oldNum, newNum) -> {

			if (newNum.intValue() > oldNum.intValue()) {
				// Check if the new name is greater than 15
				if (text.getText().length() >= 15) {

					// if it's 16th character then just setText to previous
					// one
					text.setText(text.getText().substring(0, 15));
				}

			}
		});
		Label label2 = new Label("How many people are in your secret santa?");
		label2.setId("title");

		HBox hb1 = new HBox();
		hb1.getChildren().add(label2);
		hb1.setAlignment(Pos.CENTER);

		TextField text2 = new TextField();
		text2.setPromptText("Max 50");
		text2.setId("text");

		Button btn = new Button("Submit");
		btn.setId("btn");
		btn.setPickOnBounds(false);

		text2.setOnAction(event -> btn.fire());

		btn.setOnAction(event -> {
			try {
				num = Integer.parseInt(text2.getText().trim());
				gName = text.getText();
				LinkedList<Person> group = new LinkedList<>();

				if (gName.trim().length() > 2) {

					File file = new File("src/files/" + gName + ".txt");

					if (file.exists()) {

						func.createPopup(primaryStage, "\nError: Group already exists",
								"Please enter a different group name");

					}

					else if (num < 2 || num > 50) {

						func.createPopup(primaryStage, "\nError: Invalid Number",
								"Please enter a number between 2 and 50");

					} else {

						PrintWriter out = new PrintWriter("src/files/" + gName + ".txt");

						ArrayList<String> used = new ArrayList<>();

						addUsers(primaryStage, num, out, group, gName, func, used);
					}

				} else {

					func.createPopup(primaryStage, "\nError: Invalid Group Name",
							"Please enter a name with at least 2 characters");

				}

			} catch (Exception e) {

				System.out.println(e);

				func.createPopup(primaryStage, "\nError: Invalid Number", "Please enter a number between 2 and 50");
				text2.clear();

			}

		});

		text.setOnAction(event -> btn.fire());

		Button btnBack = new Button("Back");
		btnBack.setId("btn");
		btnBack.setPickOnBounds(false);
		func.changeButn(btnBack);

		btnBack.setOnAction(event -> {
			x1 = (primaryStage.getWidth() - 16);
			y1 = (primaryStage.getHeight() - 39);

			menu.startProgram(primaryStage, menu, x1, y1, func);
		});

		HBox hb = new HBox(25);
		hb.getChildren().add(btn);
		hb.getChildren().add(btnBack);
		hb.setAlignment(Pos.CENTER);

		func.changeButn(btn);

		grid.add(hbGroup, 0, 0, 3, 1);
		grid.add(text, 0, 1, 3, 1);
		grid.add(hb1, 0, 2, 3, 1);
		grid.add(text2, 0, 3, 3, 1);
		grid.add(hb, 0, 4, 3, 1);

		Scene scene = new Scene(grid, x, y);
		btn.requestFocus();
		scene.getStylesheets().add("/stylesheets/createSanta.css");

		func.createWindows(primaryStage, scene);

	}

	/**
	 * @param stage2
	 *            the main window of the program
	 * @param num
	 *            the number of people in the secret santa group
	 * @param out
	 *            the printWriter to write the usernames and passwords to
	 * @param group
	 *            the colletions of people in the group being created
	 * @param gName
	 *            the name of the group being created
	 * @param func
	 *            an instance of the object for performing common functions
	 * @throws FileNotFoundException
	 */
	void addUsers(Stage primaryStage, int num, PrintWriter out, LinkedList<Person> group, String gName,
			GUIFunctions func, ArrayList<String> used) throws FileNotFoundException {

		GridPane grid = new GridPane();

		grid.setId("grid");

		grid.setHgap(50);
		grid.setVgap(20);

		grid.setAlignment(Pos.CENTER);

		Label label = new Label("Please enter a name and a password for person " + (count + 1));
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

			boolean validName = true;

			Pattern p1 = Pattern.compile(nameRegEx);
			Matcher m1 = p1.matcher(textName.getText().trim());

			Pattern p2 = Pattern.compile(nameLengthRegEx);
			Matcher m2 = p2.matcher(textName.getText().trim());

			// Not allowing special characters

			if (m2.find()) {

				if (!m1.find()) {

					if (textPass.getText().trim().length() > 3) {

						for (String s : used) {

							if (textName.getText().trim().compareToIgnoreCase(s) == 0) {

								validName = false;
							}

						}

						if (validName == true) {

							count++;

							used.add(textName.getText().trim());

							out.println(textName.getText().trim());
							out.println(textPass.getText());
							out.println("");

							Person p = new Person(textName.getText().trim(), textPass.getText(), gName);
							group.add(p);

							if (count > 0) {
								textName.requestFocus();
							}

							if (count < num) {
								try {
									addUsers(primaryStage, num, out, group, gName, func, used);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {

								x1 = (primaryStage.getWidth() - 16);
								y1 = (primaryStage.getHeight() - 39);

								out.close();

								Group g = new Group(gName, group);
								groups.add(g);

								Menu menu = new Menu();
								menu.startProgram(primaryStage, menu, x1, y1, func);
								usersAdded(primaryStage, func);

								System.out.println(group);

							}
						} else {

							func.createPopup(primaryStage, "\nError: Username already taken",
									"Please enter a different username");

						}

					} else {

						func.createPopup(primaryStage, "\nError: Invalid Password",
								"Please enter a password with more than 3 characters");

					}

				} else {

					func.createPopup(primaryStage, "\nError: Invalid Name",
							"Please enter a name with no special characters");
				}

			} else {

				func.createPopup(primaryStage, "\nError: Invalid Name",
						"Please enter a name with more than two characters");
			}
		});

		textName.setOnAction(event -> btn.fire());

		textPass.setOnAction(event -> btn.fire());

		HBox hb = new HBox();
		hb.getChildren().add(btn);
		hb.setAlignment(Pos.CENTER);

		grid.add(label, 0, 0, 3, 1);
		grid.add(textName, 0, 1, 3, 1);
		grid.add(textPass, 0, 2, 3, 1);
		grid.add(hb, 0, 4, 3, 1);

		Scene scene = new Scene(grid, (primaryStage.getWidth() - 16), (primaryStage.getHeight() - 39));
		btn.requestFocus();
		if (count > 0) {
			textName.requestFocus();
		}

		scene.getStylesheets().add("/stylesheets/createSanta.css");

		func.createWindows(primaryStage, scene);
	}

	/**
	 * @param mainStage
	 *            the main window of the program
	 * @param func
	 *            an instance of the object for performing common functions
	 */
	void usersAdded(Stage primaryStage, GUIFunctions func) {

		func.createPopup(primaryStage, "\nSecret Santa Group Created", "Participants have been added");

	}

	/**
	 * @return the collection of secret santa groups
	 */
	public ArrayList<Group> getGroups() {

		return groups;
	}

	/**
	 * @return the number of people in the current group
	 */
	public int getNum() {

		return num;
	}

}
