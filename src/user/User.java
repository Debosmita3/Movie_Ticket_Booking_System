package user;

import java.util.HashMap;
import java.util.Scanner;
import model.Booking;
import model.Movie;

public class User{
    
    private int id;
    private String name;
    private String email;
    protected Scanner sc;

    protected User(int id,String name,String email){
        this.id=id;
        this.name=name;
        this.email=email;
        this.sc=new Scanner(System.in);
    }

    public void setName(String name){
        this.name=name;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public void viewMenu(HashMap<String,Movie> moviesList,HashMap<String,Booking> bookingsList){}

}
