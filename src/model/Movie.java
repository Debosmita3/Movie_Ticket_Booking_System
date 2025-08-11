package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie {

    private String movieId;
    private String title;
    private int durationInMinutes;
    private String genre;
    private HashMap<String,Show> shows;

    public Movie(String movieId,String title,int durationInMinutes,String genre){
        this.movieId=movieId;
        this.title=title;
        this.durationInMinutes=durationInMinutes;
        this.genre=genre;
        this.shows=new HashMap<>();
    }

    public void setMovieId(String movieId){
        this.movieId=movieId;
    }

    public String getMovieId(){
        return movieId;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public void setDurationInMinutes(int durationInMinutes){
        this.durationInMinutes=durationInMinutes;
    }

    public int getDurationInMinutes(){
        return durationInMinutes;
    }

    public void setGenre(String genre){
        this.genre=genre;
    }

    public String getGenre(){
        return genre;
    }

    public boolean addShow(Show show){
        if(shows.containsKey(show.getShowId())){
            System.out.println("\nThis show already exists.\n");
            return false;
        }
        shows.put(show.getShowId(),show);
        return true;
    }

    public boolean deleteShow(String showId){
        if(!shows.containsKey(showId)){
            System.out.println("\nThis show doesn't exist.\n");
            return false;
        }
        shows.remove(showId);
        return true;
    }

    public Show getShow(String showId){
        if(!shows.containsKey(showId)){
            System.out.println("\nThis show doesn't exist.\n");
            return null;
        }
        return shows.get(showId);
    }

    public List<Show> getAllShows(){
        List<Show> showsPresent=new ArrayList<>();
        for(Map.Entry<String,Show> show:shows.entrySet())showsPresent.add(show.getValue());
        return showsPresent;
    }

    @Override
    public String toString(){
        String movieString="Movie ID: "+movieId+"   Title: "+title+"   Genre: "+genre+"   Duration: "+durationInMinutes+" mins   No. of Shows: "+shows.size();
        return movieString;
    }

}
