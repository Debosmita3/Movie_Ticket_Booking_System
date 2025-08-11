package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Booking;
import model.Movie;
import model.Seat;
import model.Show;

public class Admin extends User{

    public Admin(int id,String name,String email){
        super(id,name,email);
    }

    @Override
    public void viewMenu(HashMap<String,Movie> moviesList,HashMap<String,Booking> bookingsList){
        System.out.println("\n----------------------------------------------- Welcome to the Admin Page -----------------------------------------------\n");
        System.out.println("Here you can manage movies, shows, and bookings.\n");
        System.out.println("\nChoose your action:");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1. Add Movie");
        System.out.println("2. Edit Movie");
        System.out.println("3. Delete Movie");
        System.out.println("4. Add Show to Movie");
        System.out.println("5. Delete Show");
        System.out.println("6. View All Movies");
        System.out.println("7. View Shows of a Movie");
        System.out.println("8. View All Bookings");
        System.out.println("9. View Bookings by Customer");
        System.out.println("10. View Show Occupancy");
        System.out.println("11. Logout");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
        String choice;
        while (true) { 
            System.out.print("Enter your choice (1/2/3/4/5/6/7/8/9/10/11): ");
            choice = sc.nextLine();
            switch(choice){
                case "1":
                    System.out.println("\nAdd movie\n");
                    addMovie(moviesList);
                    break;
                case "2":
                    System.out.println("\nEdit Movie\n");
                    editMovieDetails(moviesList);
                    break;
                case "3":
                    System.out.println("\nDelete Movie\n");
                    deleteMovie(moviesList);
                    break;
                case "4":
                    System.out.println("\nAdd Show to Movie\n");
                    addShowToMovie(moviesList);
                    break;
                case "5":
                    System.out.println("\nDelete Show\n");
                    deleteShowFromMovie(moviesList);
                    break;
                case "6":
                    System.out.println("\nView All Movies\n");
                    viewAllMovies(moviesList);
                    break;
                case "7":
                    System.out.println("\nView Shows of a Movie\n");
                    viewShowsOfMovie(moviesList);
                    break;
                case "8":
                    System.out.println("\nView All Bookings\n");
                    viewAllBooking(bookingsList);
                    break;
                case "9":
                    System.out.println("\nView Bookings by Customer\n");
                    viewBookingByCustomer(bookingsList);
                    break;
                case "10":
                    System.out.println("\nView Show Occupancy\n");
                    viewShowOccupancy(moviesList);
                    break;
                case "11":
                    System.out.println("\nThank you Admin!\nExiting Admin Page.\n");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
                    System.out.println("Returning to the main menu.\n");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please enter a valid option (1/2/3/4/5/6/7/8/9/10/11).");
                    break;
            }
        }
    }

    // Movie Operations

    public void addMovie(HashMap<String, Movie> moviesList){
        System.out.println("Enter details of the movie to add:");
        String movieId,title,genre;
        int durationInMinutes;
        System.out.print("Movie ID: ");
        movieId=sc.nextLine().trim();
        if(moviesList.containsKey(movieId)){
            System.out.println("\nThis movie already exits.\n");
            return;
        }
        System.out.print("Movie Title: ");
        title=sc.nextLine().trim();
        System.out.print("Movie Duration in Minutes: ");
        durationInMinutes=sc.nextInt();
        sc.nextLine();
        System.out.print("Movie Genre: ");
        genre=sc.nextLine().trim();
        Movie newMovie=new Movie(movieId,title,durationInMinutes,genre);
        moviesList.put(movieId,newMovie);
        System.out.println("\nAdded a movie '"+newMovie.getTitle()+"'\n");
    }

    public void editMovieDetails(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies Yet.\n");
            return;
        }
        System.out.println("To edit movie, enter the following: ");
        System.out.print("Movie ID: ");
        String movieId=sc.nextLine().trim();
        if(!moviesList.containsKey(movieId)){
            System.out.println("\nThis movie doesn't exist. Add movie.\n");
            return;
        }
        Movie movie=moviesList.get(movieId);
        System.out.print("What do you want to edit? Title? Duration? Genre? ");
        String choose=sc.nextLine().trim().toLowerCase();
        switch (choose) {
            case "title":
                System.out.print("Enter New Title: ");
                String title=sc.nextLine().trim();
                movie.setTitle(title);
                System.out.println("\nMovie Title Changed.\n");
                break;
            case "duration":
                System.out.print("Enter New Duration in mins: ");
                int duration=sc.nextInt();
                sc.nextLine();
                movie.setDurationInMinutes(duration);
                System.out.println("\nMovie Duration Changed.\n");
                break;
            case "genre":
                System.out.print("Enter New Genre: ");
                String genre=sc.nextLine().trim();
                movie.setGenre(genre);
                System.out.println("\nMovie Genre Changed.\n");
                break;
            default:
                System.out.println("\nError. Try Again.\n");
                break;
        }
    }

    public void deleteMovie(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies Yet.\n");
            return;
        }
        System.out.println("To delete movie, enter the following: ");
        System.out.print("Movie ID: ");
        String movieId=sc.nextLine().trim();
        if(!moviesList.containsKey(movieId)){
            System.out.println("\nThis movie doesn't exist.\n");
            return;
        }
        System.out.print("Are you sure you want to delete this movie? (yes/no): ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("\nCancelled Deletion.\n");
            return;
        }
        String title=moviesList.get(movieId).getTitle();
        moviesList.remove(movieId);
        System.out.println("\nMovie Deleted.\n");
    }

    public void viewAllMovies(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies To Show. Add movies.\n");
            return;
        }
        System.out.println("Here's all the Movies:");
        int i=1;
        for(Map.Entry<String,Movie> movie:moviesList.entrySet()){
            Movie m=movie.getValue();
            System.out.println(i+". "+m+"\n");
        }
    }

    // Show Operations

    public void addShowToMovie(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies Yet.\n");
            return;
        }
        System.out.println("To add a show, enter the following: ");
        System.out.print("Movie ID: ");
        String movieId=sc.nextLine().trim();
        Movie movie=moviesList.get(movieId);
        if(movie==null){
            System.out.println("\nThis movie doesn't exist. Add the movie.\n");
            return;
        }
        System.out.print("Enter Show ID: ");
        String showId=sc.nextLine();
        showId=showId.trim();
        System.out.print("Enter Time of the Show: ");
        String time=sc.nextLine();
        System.out.print("Enter Price of Seat: ");
        double price=sc.nextDouble();
        sc.nextLine();
        Show show=new Show(showId,movie,time,price);
        System.out.print("Enter No. of Seats: ");
        int noOfSeats=sc.nextInt();
        sc.nextLine();
        show.setSeats(noOfSeats);
        if(!movie.addShow(show)){
            return;
        }
        System.out.println("\nAdded show to movie '"+movie.getTitle()+"'\n");
    }

    public void deleteShowFromMovie(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies and Shows.\n");
            return;
        }
        System.out.println("To delete a show, enter the following: ");
        System.out.print("Movie ID: ");
        String movieId=sc.nextLine().trim();
        Movie movie=moviesList.get(movieId);
        if(movie==null){
            System.out.println("\nThis movie doesn't exist. Add movie.\n");
            return;
        }
        System.out.print("Show ID: ");
        String showId=sc.nextLine().trim();
        Show show=movie.getShow(showId);
        if(show==null){
            System.out.println("This show doesn't exist.\n");
            return;
        }
        System.out.print("Are you sure you want to delete this show? (yes/no): ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("\nCancelled Deletion.\n");
            return;
        }
        if(!movie.deleteShow(showId))return;
        System.out.println("\nShow Deleted.\n");
    }

    public void viewShowsOfMovie(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies and Shows.\n");
            return;
        }
        System.out.println("To view all shows of a movie, enter the following: ");
        System.out.print("Movie ID: ");
        String movieId=sc.nextLine().trim();
        Movie movie=moviesList.get(movieId);
        if(movie==null){
            System.out.println("\nThis movie doesn't exist. Add movie.\n");
            return;
        }
        List<Show> shows=movie.getAllShows();
        if(shows.isEmpty()){
            System.out.println("\nNo Show Available. Add a Show.\n");
            return;
        }
        System.out.println("Here's the list of shows of movie '"+movie.getTitle()+"'");
        int i=1;
        for(Show show:shows){
            System.out.println(i+". "+show);
            i++;
        }
        System.out.println();
    }

    // Booking Operations

    public void viewAllBooking(HashMap<String,Booking> bookingsList){
        if(bookingsList.isEmpty()){
            System.out.println("No Bookings Yet.\n");
            return;
        }
        System.out.println("Here's all the Bookings:");
        int i=1;
        for(Map.Entry<String,Booking> booking:bookingsList.entrySet()){
            System.out.println(i+".\n"+booking.getValue().getBookingSummary()+"\n");
            i++;
        }
    }

    public void viewBookingByCustomer(HashMap<String,Booking> bookingsList){
        if(bookingsList.isEmpty()){
            System.out.println("No Bookings Yet.\n");
            return;
        }
        System.out.println("To view bookings of a customer, enter the following: ");
        System.out.print("Customer ID: ");
        int id=sc.nextInt();
        sc.nextLine();
        List<Booking> customerBookings=new ArrayList<>();
        for(Map.Entry<String, Booking> booking:bookingsList.entrySet()){
            if(booking.getValue().getCustomer().getId()==id){
                customerBookings.add(booking.getValue());
            }
        }
        if(customerBookings.isEmpty()){
            System.out.println("\nThis Customer doesn't have any bookings yet.\n");
            return;
        }
        System.out.println("Here's the list of bookings of this customer: ");
        int i=1;
        for(Booking booking:customerBookings){
            System.out.println(i+".\n"+booking.getBookingSummary()+"\n");
            i++;
        }
    }

    // Reporting Operations

    public void viewShowOccupancy(HashMap<String,Movie> moviesList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies and Shows.\n");
            return;
        }
        System.out.println("To view seat occupancy of a show, enter the following:");
        System.out.print("Movie ID: ");
        String movieId=sc.nextLine();
        if(!moviesList.containsKey(movieId)){
            System.out.println("This movie doesn't exist. Add movie.\n");
            return;
        }
        Movie movie=moviesList.get(movieId);
        if(movie.getAllShows().isEmpty()){
            System.out.println("This movie doesn't have any show yet. Add show.\n");
            return;
        }
        System.out.print("Show ID: ");
        String showId=sc.nextLine();
        Show show=movie.getShow(showId);
        if(show==null)return;
        System.out.println("\nShow Occupany:\nMovie: "+movie.getTitle()
                            +"\nShow Time: "+show.getTime()
                            +"\nTotal Seats: "+show.getSeats().size()
                            +"\nBooked Seats: "+(show.getSeats().size()-show.getAvailableSeats().size())
                            +"\nAvailable Seats: "+show.getAvailableSeats().size()
                            +"\nSeat Status:");
        int i=1;
        List<Seat> seats=show.getSeats();
        for(Seat seat:seats){
            System.out.println("Seat "+i+": "+(seat.isAvailable()?"Available":"Booked"));
            i++;
        }
        System.out.println();
    }

}
