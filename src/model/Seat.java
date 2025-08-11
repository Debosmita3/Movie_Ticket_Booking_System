package model;

public class Seat {
    
    private final int seatNumber;
    private boolean isBooked;

    public Seat(int seatNumber){
        this.seatNumber=seatNumber;
        this.isBooked=false;
    }

    public boolean isAvailable(){
        return !isBooked;
    }

    public int getSeatNumber(){
        return seatNumber;
    }
    
    public void bookSeat(){
        isBooked=true;
    }

    public void setAvailable(){
        isBooked=false;
    }

}
