package people;

import java.util.LinkedList;

public class Group {
	
	private String name;
	private LinkedList<Person> people;
	private int numOfPeople;
	
	public Group(String name, LinkedList<Person> people){
		this.name = name;
		this.people = people;
		this.numOfPeople = people.size();
	}
	
	public String getName() {
		return name;
	}
	
	public LinkedList<Person> getPeople(){
		return people;
	}
	
	public int getNumOfPeople() {
		return numOfPeople;
	}


}
