package me.wilsonhu.csce247.finalproject.objects;

import java.util.ArrayList;

public class Venue {
	private String location;
	private String name;
	private ArrayList<Theater> theaters;

	public Venue(String location, String name) {
		this.setLocation(location);
		this.setName(name);
		this.theaters = new ArrayList<>();
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the theaters
	 */
	public ArrayList<Theater> getTheaters() {
		return theaters;
	}

	/**
	 * @param theaters the theaters to set
	 */
	public void setTheaters(ArrayList<Theater> theaters) {
		this.theaters = theaters;
	}

	public void addTheater(Theater theater){
		getTheaters().add(theater);
	}

	public boolean containsTheater(Theater theater){
		for (Theater t : theaters){
			if(t.equals(theater))
				return true;
		}
		return false;
	}

	public Theater findTheater(Theater theater1){
		for (Theater theater : getTheaters()){
			if (theater.equals(theater1))
				return theater;
		}
		return null;
	}

	public Theater findByRoom(int room){
		for (Theater theater : getTheaters()){
			if (theater.getRoom() == room)
				return theater;
		}
		return null;
	}

	public Theater findByHandicap(boolean isHandicap){
		for (Theater theater : getTheaters()){
			if (theater.isHandicap() == isHandicap)
				return theater;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Venue venue = (Venue) obj;
		return venue.getName().equalsIgnoreCase(getName()) && venue.getLocation().equalsIgnoreCase(getLocation());
	}
}
