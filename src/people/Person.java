package people;

import java.util.ArrayList;

public class Person {

	private String groupName;
	private String username;
	private String password;
	private Person match;
	private ArrayList<Person> exceptions = new ArrayList<>();

	public Person() {

	}

	public Person(String username, String password, String groupName) {
		this.username = username;
		this.password = password;
		this.groupName = groupName;


	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Person> getPeople() {
		return exceptions;
	}

	public void setPeople(ArrayList<Person> people) {
		this.exceptions = people;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setMatch(Person p) {
		
		match = p;
	}
	
	public Person getMatch() {
		
		return match;
	}

	public String toString() {

		return "\n\nUsername: " + this.username + 
				"\nGroup Name: " +this.groupName;

	}

}
