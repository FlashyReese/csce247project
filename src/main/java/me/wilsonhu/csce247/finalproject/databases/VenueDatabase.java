package me.wilsonhu.csce247.finalproject.databases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.wilsonhu.csce247.finalproject.objects.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class VenueDatabase extends SavableDatabase implements Database<Venue>{

    private HashSet<Venue> venues = new HashSet<>();

    @Override
    public void add(Venue object) {
        if(!containsVenue(object)){
            getVenues().add(object);
        }else{
            Venue existingVenue = findVenue(object);
            for (Theater theater : object.getTheaters()){
                if(!existingVenue.containsTheater(theater)){
                    existingVenue.addTheater(theater);
                }else{
                    Theater existingTheater = existingVenue.findTheater(theater);
                    for (Event event: theater.getEvents()){
                        if (!existingTheater.containsEvent(event)){
                            existingTheater.addEvent(event);
                        }
                    }
                    existingVenue.addTheater(existingTheater);
                }
            }
            getVenues().add(existingVenue);
        }
        saveVenues();
    }

    @Override
    public boolean remove(Venue object) {
        boolean check = getVenues().remove(object);
        if(check){
            saveVenues();
        }
        return check;
    }

    public Venue findVenue(Venue venue){
        for (Venue venue1: getVenues()){
            if (venue1.equals(venue))
                return venue1;
        }
        return null;
    }

    public boolean containsVenue(Venue venue){
        for (Venue v: getVenues()){
            if (v.equals(venue))
                return true;
        }
        return false;
    }

    public Venue findByName(String name){
        for (Venue venue : getVenues()){
            if (venue.getName().equalsIgnoreCase(name)){
                return venue;
            }
        }
        return null;
    }

    public Venue findByLocation(String location){
        for (Venue venue : getVenues()){
            if (venue.getLocation().equalsIgnoreCase(location)){
                return venue;
            }
        }
        return null;
    }

    public void search(String title) {
        printFormat(getVenue(title), getTheater(title), getEvent(title));
    }

    public void filterByGenre(Genre genre) {
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(event.getGenre() == genre){
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    public void filterByVenue(String venueName) {
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(venue.getName().equalsIgnoreCase(venueName)){
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    public void filterByRating(int rating) {
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(event.getRating() == rating){
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    public Event getEvent(String title) {
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(event.getTitle().equalsIgnoreCase(title)){
                        return event;
                    }
                }
            }
        }
        return null;
    }

    public Theater getTheater(String title){
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(event.getTitle().equalsIgnoreCase(title)){
                        return theater;
                    }
                }
            }
        }
        return null;
    }

    public Venue getVenue(String title){
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(event.getTitle().equalsIgnoreCase(title)){
                        return venue;
                    }
                }
            }
        }
        return null;
    }

    public void filterByEventType(Type type) {
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    if(event.getType() == type){
                        printFormat(venue, theater, event);
                    }
                }
            }
        }
    }

    public void printFormat(Venue venue, Theater theater, Event event){
        StringBuilder builder = new StringBuilder();
        builder.append("Venue Name: ").append(venue.getName()).append("\n");
        builder.append("Venue Location: ").append(venue.getLocation()).append("\n");
        builder.append("Theater Room: ").append(theater.getRoom()).append("\n");
        builder.append("Theater Capacity: ").append(theater.getCapacity()).append("\n");
        builder.append("Theater Handicap: ").append(theater.isHandicap()).append("\n");
        builder.append("Event Title: ").append(event.getTitle()).append("\n");
        builder.append("Event Date: ").append(event.getDate()).append("\n");
        builder.append("Event Description: ").append(event.getDescription()).append("\n");
        builder.append("Event Genre: ").append(event.getGenre().toString()).append("\n");
        builder.append("Event Price: ").append(event.getPrice()).append("\n");
        builder.append("Event Type: ").append(event.getType().toString()).append("\n");
        builder.append("Event Rating: ").append(event.getRating()).append("\n");
        builder.append("\n\n");
        System.out.println(builder.toString());
    }

    public void printAll() {
        for (Venue venue : getVenues()){
            for (Theater theater : venue.getTheaters()){
                for (Event event: theater.getEvents()){
                    System.out.println(event);
                }
            }
        }
    }

    public void loadVenues(){
        if (!new File(VENUE_FILE_NAME).exists()) {
            try {
                Files.createDirectories(Paths.get(VENUE_FILE_NAME).getParent());
                Files.createFile(Paths.get(VENUE_FILE_NAME));
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveVenues();
        }
        setVenues(read(VENUE_FILE_NAME, new TypeToken<HashSet<Venue>>(){}.getType()));
    }

    public void saveVenues(){
        write(getVenues(), VENUE_FILE_NAME);
    }

    public HashSet<Venue> getVenues() {
        return venues;
    }

    public void setVenues(HashSet<Venue> venues) {
        this.venues = venues;
    }
}
