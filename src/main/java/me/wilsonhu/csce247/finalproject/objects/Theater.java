package me.wilsonhu.csce247.finalproject.objects;

import java.util.HashSet;

public class Theater {
	private int room;
	private boolean handicap;
	private int capacity;
	private HashSet<Event> events;

	public Theater(int room, boolean handicap) {
		this.setRoom(room);
		this.setHandicap(handicap);
		this.setEvents(new HashSet<>());
		this.setCapacity(100);//Make sure this can be sqrt
	}

	/**
	 * @return the room
	 */
	public int getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(int room) {
		this.room = room;
	}

	/**
	 * @return the handicap
	 */
	public boolean isHandicap() {
		return handicap;
	}

	/**
	 * @param handicap the handicap to set
	 */
	public void setHandicap(boolean handicap) {
		this.handicap = handicap;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the seats to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public HashSet<Event> getEvents(){
		return events;
	}

	public void setEvents(HashSet<Event> events){
		this.events = events;
	}

	public boolean containsEvent(Event event){
		for (Event e : getEvents()){
			if(e.equals(event))
				return true;
		}
		return false;
	}

	public void addEvent(Event event){
		getEvents().add(event);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Theater theater = (Theater) obj;
		return room == theater.room && handicap == theater.handicap;
	}

	public Event findEvent(Event event) {
		for (Event event1: getEvents()){
			if (event.equals(event1))
				return event;
		}
		return null;
	}
}
