package main;

import java.util.HashMap;
import java.util.Scanner;
import model.Booking;
import model.Movie;
import user.Admin;
import user.Customer;
import user.User;

public class MovieApp {

    public HashMap<String,Movie> moviesList;
    public HashMap<String,Booking> bookingsList;
    public HashMap<Integer,Admin> admins;
    public HashMap<Integer,Customer> customers;

    MovieApp(Scanner sc){

        moviesList=new HashMap<>();
        bookingsList=new HashMap<>();
        admins=new HashMap<>();
        customers=new HashMap<>();
        int id;
        String name,email,membershipTier;
        User user;

        System.out.println("============================================== Movie Ticket Booking System ==============================================");
        System.out.println("\nWelcome to the Movie Ticket Booking System!\n");
        System.out.println("You can book tickets for movies, view available shows, and manage your bookings.\n");
        System.out.println("Please select your role from the menu to get started.");
        System.out.println("=========================================================================================================================");
        System.out.println("1. Admin");
        System.out.println("2. Customer"); 
        System.out.println("3. Exit");
        System.out.println("=========================================================================================================================\n");

        String role;
        while(true){
            System.out.print("Select your role (1/2/3): ");
            role=sc.nextLine();

            switch (role) {
                case "1":
                    System.out.println("Hello Admin");
                    System.out.println("Enter Details to Login:\n");
                    System.out.print("Admin ID: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    if(admins.containsKey(id)){
                        user=admins.get(id);
                    }
                    else{
                        System.out.print("Name: ");
                        name=sc.nextLine();
                        System.out.print("Email: ");
                        email=sc.nextLine();
                        user=new Admin(id,name,email);
                        admins.put(id,(Admin)user);
                    }
                    user.viewMenu(moviesList,bookingsList);
                    break;
                case "2":
                    System.out.println("Hello Customer");
                    System.out.println("Enter Details to Login:\n");
                    System.out.print("Customer ID: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    if(customers.containsKey(id)){
                        user=customers.get(id);
                    }else{
                        System.out.print("Name: ");
                        name=sc.nextLine();
                        System.out.print("Email: ");
                        email=sc.nextLine();
                        System.out.print("MembershipTier (Regular/Premium/Null) : ");
                        membershipTier=sc.nextLine();
                        user=new Customer(id,name,email,membershipTier);
                        customers.put(id,(Customer)user);
                    }
                    user.viewMenu(moviesList,bookingsList);
                    break;
                case "3":
                    System.out.println("Exiting the application.\nThank you for using the Movie Ticket Booking System!\n");
                    System.out.println("=========================================================================================================================\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid role. Please enter 1 for Admin, 2 for Customer, or 3 to Exit.");
                    break;
            }
        }

    }

    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        new MovieApp(scanner);
    }
}
