package com.driver.Repository;

import com.driver.Service.AirportService;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@Repository
public class AirportRepository {
    HashMap<Integer,Flight> flightDb=new HashMap<>();
    HashMap<Integer,Passenger> passengerDb=new HashMap<>();
    HashMap<String,Airport> airportDb=new HashMap<>();//airportName to airport
    HashMap<Integer, HashSet<Integer>> flightToPassengerDb=new HashMap<>();// flightId to passengerIds
    public void addFlight(Integer flightId, Flight flight) {
        if(!flightDb.containsKey(flightId)){
            flightDb.put(flightId,flight);
        }
    }

    public void addPassenger(Integer passengerId, Passenger passenger) {
        if(!passengerDb.containsKey(passengerId)){
            passengerDb.put(passengerId,passenger);
        }
    }

    public void addAirport(String airportName, Airport airport) {
        if(!airportDb.containsKey(airportName)){
            airportDb.put(airportName,airport);
        }
    }

    public String getLargestAirportName() {
        String ans="";
        int largest=0;
        for(String key:airportDb.keySet()){
            int noOfTerminals=airportDb.get(key).getNoOfTerminals();
            if(noOfTerminals>largest){
                largest=noOfTerminals;
                ans=airportDb.get(key).getAirportName();
            }
            else if(noOfTerminals==largest){
                String name=airportDb.get(key).getAirportName();
                if(ans.compareTo(name)>0){
                    ans=name;
                }
            }
        }
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        Airport airport=airportDb.get(airportName);
        City city=airport.getCity();// got to know about city where this particular airport is
        if(airport==null)return 0;
        int ans=0;
        for(Flight flight:flightDb.values()){
            if(flight.getFlightDate().equals(date)){
                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){
                    int flightId=flight.getFlightId();
                    try{
                        ans+=flightToPassengerDb.get(flightId).size();
                    }
                    catch (Exception e){
                        ans+=0;
                    }
                }
            }
        }
        return ans;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        String ans="";
       City city[]=City.values();
       // get to the city name of airport from flightId
        City cityName=null;
        for(Flight flight: flightDb.values()){
            if(flight.getFlightId()==flightId){
                cityName=flight.getFromCity();
            }
        }

        // then we will find airport name

           for(Airport airport:airportDb.values()){
               if(airport.getCity().equals(cityName)){
                   ans=airport.getAirportName();
               }
           }

        return ans;
    }
    public int getNumberOfPeopleOnFlight(Integer flightId) {
        int noOfPeople=flightToPassengerDb.get(flightId).size();
        return noOfPeople;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count=0;
        for(HashSet<Integer> passenger:flightToPassengerDb.values()){
            if(passenger.contains(passengerId))count++;
        }
        return count;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans=(double)Integer.MAX_VALUE;
        boolean flag=false;
        for(Flight flight:flightDb.values()){
            if(flight.getToCity().equals(toCity) && flight.getFromCity().equals(fromCity)){
                ans=Math.min(ans,flight.getDuration());
                flag=true;
            }
        }
        if(!flag)return -1;
        return ans;
    }

    public boolean cancelATicket(Integer flightId, Integer passengerId) {
        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId
        if(!flightToPassengerDb.containsKey(flightId))return false;
        else if(flightToPassengerDb.get(flightId)==null)return false;
        else if(!flightToPassengerDb.get(flightId).contains(passengerId))return false;
        flightToPassengerDb.get(flightId).remove(passengerId);

        return true;
    }

    public boolean bookATicket(Integer flightId, Integer passengerId) {
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"
        if(!passengerDb.containsKey(passengerId))return false;// not sure to keep .. lets see what question says

        if(!flightDb.containsKey(flightId))return false;

        int flightCapacity=flightDb.get(flightId).getMaxCapacity();

        if(flightToPassengerDb.get(flightId)==null){
            flightToPassengerDb.put(flightId,new HashSet<>());
            flightToPassengerDb.get(flightId).add(passengerId);
            return true;
        }

        int bookedTicket=flightToPassengerDb.get(flightId).size();

        if(flightCapacity<=bookedTicket)return false;
        else if(flightToPassengerDb.get(flightId).contains(passengerId))return false;// if already booked by passenger

        flightToPassengerDb.get(flightId).add(passengerId);
        return true;

    }
}
