package user;

import exception.BookingNotFoundException;
import exception.InvalidMembershipException;
import exception.SeatUnavailableException;
import interfaces.DiscountStrategy;
import java.time.LocalTime;
import java.util.*;
import model.Booking;
import model.Movie;
import model.Payment;
import model.Seat;
import model.Show;
import strategy.PremiumDiscount;
import strategy.RegularDiscount;

public class Customer extends User{
    
    private String membershipTier;
    private HashMap<String,Booking> bookings;
    private List<String> notifications=new ArrayList<>();

    public Customer(int id,String name,String email,String membershipTier){
        super(id,name,email);
        this.membershipTier=membershipTier;
        this.bookings=new HashMap<>();
    }

    public void setMembershipTier(String membershipTier){
        this.membershipTier=membershipTier;
    }
    public String getMembershipTier(){
        return membershipTier;
    }

    public void viewBookingHistory(){
        if(bookings.isEmpty()){
            System.out.println("You have no bookings yet. Book a ticket.\n");
            return;
        }
        System.out.println("Here's all your bookings:");
        int i=1;
        for (Map.Entry<String, Booking> booking : bookings.entrySet()) {
            System.out.println(i+".\n"+booking.getValue().getBookingSummary()+"\n");
            i++;
        }
    }

    public DiscountStrategy getDiscountStrategy() throws InvalidMembershipException{
        String membershipType=this.membershipTier.toLowerCase();
        switch (membershipType) {
        case "regular":
            return new RegularDiscount();
        case "premium":
            return new PremiumDiscount();
        default:
            throw new InvalidMembershipException("Membership Tier '" + membershipType + "' is invalid.");
        }
    }

    @Override
    public void viewMenu(HashMap<String,Movie> moviesList,HashMap<String,Booking> bookingsList){
        System.out.println("\n----------------------------------------------- Welcome to the Customer Page -----------------------------------------------\n");
        System.out.println("Hello, "+super.getName()+"\n");
        System.out.println("\nChoose your action:");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1. View All Movie & Available Shows");
        System.out.println("2. Book Tickets");
        System.out.println("3. View My Bookings");
        System.out.println("4. Cancel a Booking");
        System.out.println("5. Print Ticket");
        System.out.println("6. Logout");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
        String choice;
        while (true) { 
            System.out.print("Enter your choice (1/2/3/4/5/6): ");
            choice = sc.nextLine();
            switch(choice){
                case "1":
                    System.out.println("\nView All Movie & Available Shows\n");
                    viewMoviesAndShows(moviesList,bookingsList);
                    break;
                case "2":
                    System.out.println("\nBook Tickets\n");
                    bookTicket(moviesList, bookingsList);
                    break;
                case "3":
                    System.out.println("\nView My Bookings\n");
                    viewBookingHistory();
                    break;
                case "4":
                    System.out.println("\nCancel a Booking\n");
                    cancelBooking(bookingsList);
                    break;
                case "5":
                    System.out.println("\nPrint Ticket\n");
                    getTicket(bookingsList);
                    break;
                case "6":
                    System.out.println("\nThank you!\nExiting Customer Page.\n");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------\n");
                    System.out.println("Returning to the main menu.\n");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please enter a valid option (1/2/3/4/5/6).");
                    break;
            }
        }
    }

    public void viewMoviesAndShows(HashMap<String,Movie> moviesList,HashMap<String,Booking> bookingsList){
        if(moviesList.isEmpty()){
            System.out.println("No Movies To Show.\n");
            return;
        }
        System.out.println("Here's all the Movies and the Shows:");
        int i=1;
        for(Map.Entry<String,Movie> movie:moviesList.entrySet()){
            Movie m=movie.getValue();
            System.out.println(i+". "+m+"\n Shows:");
            if(m.getAllShows().isEmpty()){
                System.out.println("No Shows Yet.\n");
            }else{
                for(Show show:m.getAllShows()){
                    System.out.println("• "+show);
                }
                System.out.println();
            }
        }
        System.out.print("\nDo you want to book tickets now? ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("no")) {
        }else if(confirm.equalsIgnoreCase("yes")){
            bookTicket(moviesList, bookingsList);
        }else{
            System.out.println("Invalid Option.\n");
        }
    }

    public void bookTicket(HashMap<String,Movie> moviesList,HashMap<String,Booking> bookingsList){
        if(moviesList.isEmpty()){
            System.out.println("No movies available at the moment.\n");
            return;
        }
        System.out.println("Here's all the Movies:");
        int i=1;
        for(Map.Entry<String,Movie> movie:moviesList.entrySet()){
            Movie m=movie.getValue();
            System.out.println(i+". "+m);
        }
        System.out.print("\nEnter Movie ID to book from: ");
        String movieId=sc.nextLine();
        if(!moviesList.containsKey(movieId)){
            System.out.println("This movie doesn't exist. Try again.\n");
            return;
        }
        Movie movie=moviesList.get(movieId);
        List<Show> shows=movie.getAllShows();
        if(shows.isEmpty()){
            System.out.println("No shows are currently scheduled for this movie. Checkout other movies.\n");
            return;
        }
        shows.sort(Comparator.comparing(show -> LocalTime.parse(show.getTime())));
        System.out.println("Here's all the shows of this movie:");
        for(Show show:shows){
            System.out.println("• "+show);
        }
        System.out.print("\nEnter Show ID you want to book: ");
        String showId=sc.nextLine();
        Show show=movie.getShow(showId);
        if(show==null){
            System.out.println("This show is unavailable. Try again.\n");
            return;
        }
        System.out.println("Here's the seat occupancy:");
        List<Seat> seats=show.getSeats();
        for(Seat seat:seats){
            System.out.println("Seat "+seat.getSeatNumber()+": "+(seat.isAvailable()?"Available":"Booked"));
        }
        System.out.print("\nHow many seats do you want to book? ");
        int noOfSeatsNeeded=sc.nextInt();
        sc.nextLine();
        List<Seat> availableSeats=show.getAvailableSeats();
        try{
            if(noOfSeatsNeeded>availableSeats.size()){
                throw new SeatUnavailableException("Only "+availableSeats.size()+" seats are available. Try again choosing another show.\n");
            }
        }catch(SeatUnavailableException e){
            System.out.println("Booking Failed: " + e.getMessage());
            return;
        }
        List<Integer> seatNumbers=new ArrayList<>();
        List<Seat> mySeats=new ArrayList<>();
        i=1;
        while(i<=noOfSeatsNeeded){
            System.out.print("Enter seat "+i+" of "+noOfSeatsNeeded+": ");
            int no=sc.nextInt();
            sc.nextLine();
            Seat seat=show.findSeatByNumber(no);
            if(!seat.isAvailable()){
                System.out.println("This seat is not available. Choose another.");
            }else if(seatNumbers.contains(no)){
                System.out.println("Seat already taken. Choose another.");
            }
            else{
                seatNumbers.add(no);
                mySeats.add(show.findSeatByNumber(no));
                i++;
            }
        }
        System.out.print("Confirm booking for seats "+seatNumbers+" for movie '"+movie.getTitle()+"'' at "+show.getTime()+"? (yes/no) ");
        String confirm=sc.nextLine();
        if(!confirm.equalsIgnoreCase("yes")){
            System.out.println("Booking process cancelled. Returning to main menu.\n");
            return;
        }
        System.out.println("\nEach ticket price:             Rupees "+show.getPrice());
        System.out.println("Total Amount:                     Rupees "+show.getPrice()*noOfSeatsNeeded);
        DiscountStrategy discount;
        double totalAmount;
        try {
            discount = getDiscountStrategy();
            totalAmount = discount.applyDiscount(show.getPrice() * noOfSeatsNeeded);
        } catch (InvalidMembershipException e) {
            System.out.println("Discount could not be applied: " + e.getMessage());
            System.out.println("Proceeding without discount.\n");
            totalAmount = show.getPrice() * noOfSeatsNeeded;
        }
        System.out.println("Discount available:               Rupees "+(show.getPrice()*noOfSeatsNeeded-totalAmount));
        System.out.println("Amount to be paid after discount: Rupees "+totalAmount+"\n");
        System.out.print("Do you want to process payment? (yes/no) ");
        confirm=sc.nextLine();
        if(!confirm.equalsIgnoreCase("yes")){
            System.out.println("Payment cancelled. Returning to main menu.\n");
            return;
        }
        System.out.print("Enter Payment Mode (cash/upi) : ");
        String paymentMode=sc.nextLine();
        Payment payment=new Payment(totalAmount, paymentMode);
        String bookingId="BKG"+movieId+showId+super.getId()+(bookingsList.size()+1);
        Booking booking=new Booking(bookingId, this, show, mySeats, payment);
        booking.confirmBooking();
        this.bookings.put(bookingId, booking);
        bookingsList.put(bookingId, booking);
        System.out.println("Booking Done\n");
        System.out.println(booking.getBookingSummary());
        System.out.print("Do you want to print booking details? (yes/no) ");
        confirm=sc.nextLine();
        if(confirm.equalsIgnoreCase("yes")){
            booking.saveSummaryToFile();
            System.out.println("Booking Details Saved.\n");
        }
    }

    public void getTicket(HashMap<String,Booking> bookingsList){
        System.out.println("To get ticket details, enter the following:");
        System.out.print("Booking ID: ");
        String bookingId=sc.nextLine();
        try{
            if(!bookingsList.containsKey(bookingId)){
                throw new BookingNotFoundException("Invalid Booking ID.\n");
            }
            Booking booking=bookingsList.get(bookingId);
            booking.getTicket().printTicket();
        }catch(BookingNotFoundException e){
            System.out.println("Couldn't Print Ticket: "+e.getMessage());
        }
    }

    public void cancelBooking(HashMap<String,Booking> bookingsList){
        if(bookings.isEmpty()){
            System.err.println("No Bookings to Cancel.\n");
            return;
        }
        System.out.println("Here's all your bookings:");
        int i=1;
        for (Map.Entry<String, Booking> booking : bookings.entrySet()) {
            System.out.println(i+".\n"+booking.getValue().getBookingSummary()+"\n");
            i++;
        }
        System.out.println("To cancel a booking, enter the following: ");
        System.out.print("Booking ID: ");
        String bookingId=sc.nextLine();
        try{
            if(!bookings.containsKey(bookingId)){
                throw new BookingNotFoundException("Invalid booking ID. Try Again.\n");
            }
            System.out.print("Are you sure you want to cancel booking? (yes/no) ");
            String confirm=sc.nextLine();
            if(confirm.equalsIgnoreCase("no")){
                System.out.println("Cancellation stopped. Returning to main menu.\n");
                return;
            }
            if(!confirm.equalsIgnoreCase("yes")){
                System.out.println("Invalid Entry. Try Again.\n");
                return;
            }
            Booking booking=bookings.get(bookingId);
            bookingsList.remove(bookingId);
            booking.cancelBooking();
            System.out.println("Booking of Show '"+booking.getShow().getShowId()+"' of Movie '"+booking.getShow().getMovie().getTitle()+"' is cancelled.\nRefund of Rupees "+booking.getPayment().getAmount()+" will be credited shortly.\n");
        }catch(BookingNotFoundException e){
            System.out.println("Cancellation cancelled: "+e.getMessage());
        }
    }

}
