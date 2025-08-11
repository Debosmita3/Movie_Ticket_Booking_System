package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import user.Customer;

public class Booking {

    private String bookingId;
    private Customer customer;
    private Show show;
    private List<Seat> seats;
    private Payment payment;
    private Ticket ticket;
    private String status;

    public Booking(String bookingId,Customer customer,Show show,List<Seat> seats,Payment payment){
        this.bookingId=bookingId;
        this.customer=customer;
        this.show=show;
        this.seats=seats;
        this.payment=payment;
        this.ticket=null;
    }

    public String getBookingId(){
        return bookingId;
    }

    public Customer getCustomer(){
        return customer;
    }

    public Show getShow(){
        return show;
    }

    public List<Seat> getSeats(){
        return seats;
    }

    public Payment getPayment(){
        return payment;
    }

    public Ticket getTicket(){
        return ticket;
    }

    public String getStatus(){
        return status;
    }

    public void confirmBooking(){
        for(Seat seat:seats){
            seat.bookSeat();
        }
        String ticketId="TICK" + bookingId;
        ticket=new Ticket(ticketId,this);
        payment.pay();
        this.status="Booked";
    }

    public void cancelBooking(){
        this.status="Cancelled";
        for(Seat seat:seats){
            if(seat!=null)seat.setAvailable();
        }
    }

    public String getBookingSummary(){
        List<Integer> seatNumbers=new ArrayList<>();
        for(Seat seat:seats)seatNumbers.add(seat.getSeatNumber());
        String bookingSummary="";
        bookingSummary+=("Booking ID     : "+bookingId);
        bookingSummary+=("\nCustomer       : "+customer.getName());
        bookingSummary+=("\nMovie          : "+show.getMovie().getTitle());
        bookingSummary+=("\nShow ID        : "+show.getShowId());
        bookingSummary+=("\nShow Time      : "+show.getTime());
        bookingSummary+=("\nSeat Number    : "+seatNumbers);
        bookingSummary+=("\nAmount Paid    : "+payment.getAmount()+" via "+payment.paymentMode());
        bookingSummary+=("\nPayment Status : "+payment.getStatus());
        bookingSummary+=("\nTicket ID      : "+ticket.getTicketId());
        bookingSummary+=("\nBooking Status : "+status);
        return bookingSummary;
    }

    public void saveSummaryToFile(){
        String filenamme=bookingId+".txt";
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(filenamme))) {
            writer.write(getBookingSummary());
            System.out.println("Booking Summary saved to file '"+filenamme+"'\n");
        } catch (IOException e) {
            System.err.println("Error saving booking summary: "+e.getMessage()+"\n");
        }
    }

}
