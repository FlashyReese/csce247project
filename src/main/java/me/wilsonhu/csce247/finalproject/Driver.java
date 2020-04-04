package me.wilsonhu.csce247.finalproject;

import me.wilsonhu.csce247.finalproject.databases.UserDatabase;
import me.wilsonhu.csce247.finalproject.databases.VenueDatabase;
import me.wilsonhu.csce247.finalproject.objects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Driver {

    private VenueDatabase venues;
    private UserDatabase users;
    private User currentUser;
    private BufferedReader bufferedReader;

    public Driver() {
        venues = new VenueDatabase();
        users = new UserDatabase();
        venues.loadVenues();
        users.loadUsers();
        users.loadAdmins();
        currentUser = new User("default");
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() throws IOException {
        boolean quit = false;
        System.out.println("Welcome to Paradise Showtimes! " + "\nHere are your options: ");
        while (!quit) {
            options();
            int option = Integer.parseInt(bufferedReader.readLine());
            quit = command(option);
        }
    }

    public void options() {
        System.out.println("0. Quit" + "\n1. Sign In" + "\n2. Register" + "\n3. Search" + "\n4. View All Events"
                + "\n5. Purchase Ticket");
        if (currentUser instanceof RegisteredUser) {
            System.out.println("\n6. Write Review");
        }
        if (currentUser instanceof Admin) {
            System.out.println("\n6. Add Admin");
        }
    }

    public boolean command(int option) throws IOException {
        if (option == 0) {
            return true;
        } else if (option == 1) {
            signIn();
        } else if (option == 2) {
            register();
        } else if (option == 3) {
            search();
        } else if (option == 4) {
            viewAllEvents();
        } else if (option == 5) {
            System.out.println("Enter title of event: ");
            String title = bufferedReader.readLine();
            if (venues.getEvent(title) == null) {
                System.out.println("Event does not exist or title was wrong");
                return false;
            }
            purchaseTicket(title);
        } else if (option == 6) {
            if (currentUser instanceof RegisteredUser) {
                System.out.println("Enter title of event: ");
                String title = bufferedReader.readLine();
                System.out.println("Enter short review: ");
                String review = bufferedReader.readLine();
                System.out.println("Enter whole numbr rating from 1-5: ");
                int rating = Integer.parseInt(bufferedReader.readLine());
                writeReview(rating, review, title);
            }
            if (currentUser instanceof Admin) {
                addAdmin();
            }
        } else {
            return true;
        }
        return false;
    }

    public void signIn() throws IOException {
        System.out.println("Enter username: ");
        String username = bufferedReader.readLine();
        if (users.hasUser(username)) {
            System.out.println("Enter password: ");
            String password = bufferedReader.readLine();
            User user = users.get(username);
            // casting to registeduser
            if (user instanceof RegisteredUser) {
                if (((RegisteredUser) user).getPassword().equals(password)) {
                    currentUser = user;
                    System.out.println("Login successful");
                    return;
                }
            }

            // casting to admin
            if (user instanceof Admin) {
                if (((Admin) user).getPassword().equals(password)) {
                    currentUser = user;
                    System.out.println("Login successful");
                }
            }
        } else {
            System.out.println("User not found");
        }
    }

    public void register() throws IOException {
        System.out.println("To register, enter username: ");
        String username = bufferedReader.readLine();

        System.out.println("Now set a password: ");
        String password = bufferedReader.readLine();

        System.out.println("Enter the number corresponding to your status: "
                + "\n1. Military\n2. Employee\n3. Teacher\n4. Student\n5. Senior\n6. None");
        int stat = Integer.parseInt(bufferedReader.readLine());
        Status status = null;
        switch (stat) {
            case 1:
                status = Status.MILITARY;
                break;
            case 2:
                status = Status.EMPLOYEE;
                break;
            case 3:
                status = Status.TEACHER;
                break;
            case 4:
                status = Status.STUDENT;
                break;
            case 5:
                status = Status.SENIOR;
                break;
            default:
                status = Status.NONE;
                break;
        }
        RegisteredUser user = new RegisteredUser(username, password, status);
        users.addUser(username, password, status);
        currentUser = user;
        System.out.println("Great! You're registered, and you are also signed in.");
    }

    public void addAdmin() throws IOException {
        System.out.println("Enter username for admin: ");
        String username = bufferedReader.readLine();

        System.out.println("Now set a password: ");
        String password = bufferedReader.readLine();

        Admin admin = new Admin(username, password);
        users.add(admin);
        System.out.println("Admin created");
    }

    public void search() throws IOException {
        System.out.println("Enter a number t search for events: " + "\n1.Search title" + "\n2.Filter by Genre"
                + "\n3.Filter by Venue" + "\n4.Filter by Rating" + "\n5.Filter by Event Type");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                System.out.println("Enter title: ");
                String title = bufferedReader.readLine();
                venues.search(title);
                break;
            case 2:
                Genre genre = chooseGenre();
                venues.filterByGenre(genre);
                break;
            case 3:
                System.out.println("Enter name of venue: ");
                String name = bufferedReader.readLine();
                venues.filterByVenue(name);
                break;
            case 4:
                System.out.println("Enter a whole number rating from 1-5: ");
                int rating = Integer.parseInt(bufferedReader.readLine());
                venues.filterByRating(rating);
                break;
            case 5:
                Type type = chooseType();
                venues.filterByEventType(type);
                break;

        }
    }

    private Type chooseType() throws IOException {
        System.out.println("Choose an event type to filter by: " + "\n1. Movie" + "\n2. Concert" + "\n3. Play");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                return Type.MOVIE;
            case 2:
                return Type.CONCERT;
            case 3:
                return Type.PLAY;
            default:
                return null;
        }
    }

    private Genre chooseGenre() throws IOException {
        System.out.println("Choose a genre to filter by: " + "\n1. Comedy" + "\n2. Horror" + "\n3. Thriller"
                + "\n4. Romance" + "\n5. Indie" + "\n6. Family" + "\n7. Action" + "\n8. Rap" + "\n9. Rock" + "\n10. Pop"
                + "\n11. Country" + "\n12. Latin" + "\n13. EDM" + "\n14. RNB" + "\n15. Classic" + "\n16. Opera");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                return Genre.COMEDY;
            case 2:
                return Genre.HORROR;
            case 3:
                return Genre.THRILLER;
            case 4:
                return Genre.ROMANCE;
            case 5:
                return Genre.INDIE;
            case 6:
                return Genre.FAMILY;
            case 7:
                return Genre.ACTION;
            case 8:
                return Genre.RAP;
            case 9:
                return Genre.ROCK;
            case 10:
                return Genre.POP;
            case 11:
                return Genre.COUNTRY;
            case 12:
                return Genre.LATIN;
            case 13:
                return Genre.EDM;
            case 14:
                return Genre.RNB;
            case 15:
                return Genre.CLASSIC;
            case 16:
                return Genre.OPERA;
            default:
                return null;
        }

    }

    public void viewAllEvents() {
        venues.printAll();
    }

    public void viewSeating(String title) {

    }

    public void purchaseTicket(String title) throws IOException {
        System.out.println("Are you sure you want to buy a ticket for (Y/N): ");
        Event e = venues.getEvent(title);
        System.out.println(e.toString());
        String ans = bufferedReader.readLine();
        if (ans.equals("N"))
            return;
        orderTicket(title);
    }

    public void orderTicket(String title) throws IOException {
        System.out.println("Great! Ticket purchased. Would you like: " + "\n1. Physical ticket" + "\n2. Online ticket"
                + "\n3. Both");
        int option = Integer.parseInt(bufferedReader.readLine());
        switch (option) {
            case 1:
                generatePhysicalTicket(title);
                break;
            case 2:
                generateOnlineTicket(title);
                break;
            case 3:
                generatePhysicalTicket(title);
                generateOnlineTicket(title);
                break;
        }
    }

    public void generatePhysicalTicket(String title) {
        try {
            PrintWriter file = new PrintWriter(title + "_ticket_receipt.txt", "UTF-8");
            file.print(venues.getEvent(title).ticketString());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateOnlineTicket(String title) {
        Event e = venues.getEvent(title);
        System.out.println(
                "Here is your online receipt. Reminder, receipts save automatically in history for registered users.");
        System.out.println(e.ticketString());
        if (currentUser instanceof RegisteredUser)
            ((RegisteredUser) currentUser).addTicket(e.ticketString());

    }

    public void writeReview(int rating, String review, String title) {
        Event e = venues.getEvent(title);
        e.addReview(new Review(rating, review));
    }
}
