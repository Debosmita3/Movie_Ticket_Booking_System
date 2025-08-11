package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show {

    private final String showId;
    private final Movie movie;
    private String time;
    private double price;
    private HashMap<Integer,Seat> seats;

    public Show(String showId,Movie movie,String time,double price){
        this.showId = showId;
        this.movie = movie;
        this.time = time;
        this.price=price;
        this.seats = new HashMap<>();
    }

    public String getShowId(){
        return showId;
    }

    public Movie getMovie(){
        return movie;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price=price;
    }

    public List<Seat> getAvailableSeats(){
        List<Seat> availableSeats=new ArrayList<>();
        for(Map.Entry<Integer,Seat> seat:seats.entrySet()){
            if(seat.getValue().isAvailable())availableSeats.add(seat.getValue());
        }
        if(availableSeats.isEmpty()){
            System.out.println("No Available Seats.\n");
            return null;
        }
        return availableSeats;
    }

    public Seat findSeatByNumber(int seatNumber){
        return seats.get(seatNumber);
    }

    public void setSeats(int noOfSeats){
        for(int i=1;i<=noOfSeats;i++)seats.put(i,new Seat(i));
    }
    
    public List<Seat> getSeats(){
        List<Seat> seatsList=new ArrayList<>();
        for(Map.Entry<Integer,Seat> seat:seats.entrySet()){
            seatsList.add(seat.getValue());
        }
        return seatsList;
    }

    @Override
    public String toString() {
        return "Show ID : " + showId +
             "   Time : " + time +
             "   Total Seats: "+seats.size()+
             "   Price of each seat: "+price+
             "   Available Seats : " + getAvailableSeats().size();
    }


}
