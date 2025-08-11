package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ticket {

    private String ticketId;
    private Booking booking;
    private LocalDateTime issuedTime;

    public Ticket(String ticketId,Booking booking){
        this.ticketId=ticketId;
        this.booking=booking;
        this.issuedTime=LocalDateTime.now();
    }

    public String getTicketId(){
        return this.ticketId;
    }

    public Booking getBooking(){
        return this.booking;
    }

    public LocalDateTime getIssuedTime(){
        return this.issuedTime;
    }

    public String getTicketDetails(){
        List<Integer> seatNumbers=new ArrayList<>();
        for(Seat seat:booking.getSeats())seatNumbers.add(seat.getSeatNumber());
        String ticketDetails="";
        ticketDetails+=("Ticket ID     : "+ticketId);
        ticketDetails+=("\nCustomer Name : "+booking.getCustomer().getName());
        ticketDetails+=("\nMovie         : "+booking.getShow().getMovie().getTitle());
        ticketDetails+=("\nShow ID       : "+booking.getShow().getShowId());
        ticketDetails+=("\nShow Time     : "+booking.getShow().getTime());
        ticketDetails+=("\nSeat Number   : "+seatNumbers);
        ticketDetails+=("\nBooking ID    : "+booking.getBookingId());
        ticketDetails+=("\nAmount Paid   : ₹"+booking.getPayment().getAmount());
        ticketDetails+=("\nIssued On     : "+issuedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm\n")));
        return ticketDetails;
    }

    public void printTicket(){
        String ticket="";
        ticket+=("========================================\n");
        ticket+=("                MOVIE TICKET\n");
        ticket+=("========================================\n");
        ticket+=(getTicketDetails());
        ticket+=("========================================\n");
        ticket+=("           Enjoy your movie!\n");
        ticket+=("========================================\n");
        String filenamme=ticketId+".txt";
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(filenamme))) {
            writer.write(ticket);
            System.out.println("Ticket saved to file '"+filenamme+"'\n");
        } catch (IOException e) {
            System.err.println("Error printing ticket: "+e.getMessage()+"\n");
        }
    }

}
