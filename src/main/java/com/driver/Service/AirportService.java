package com.driver.Service;

import com.driver.Repository.AirportRepository;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
//    @Autowired
//    AirportRepository airportRepository;
    AirportRepository airportRepository=new AirportRepository();
    public boolean addFlight(Integer flightId, Flight flight){
        airportRepository.addFlight(flightId,flight);
        return true;
    }
    public boolean addPassenger(Integer passengerId, Passenger passenger){
        airportRepository.addPassenger(passengerId,passenger);
        return true;
    }

    public void addAirport(String airportName, Airport airport) {
        airportRepository.addAirport(airportName,airport);
    }

    public String getLargestAirportName() {
        String name=airportRepository.getLargestAirportName();
        return name;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int ans=airportRepository.getNumberOfPeopleOn(date,airportName);
        return ans;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        String ans=airportRepository.getAirportNameFromFlightId(flightId);
        return ans;
    }

    public int calculateFlightFare(Integer flightId) {
        int ans=0;
        int noOfPeople=airportRepository.getNumberOfPeopleOnFlight(flightId);
            ans=noOfPeople*50+ 3000;
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        int noOfPeople=airportRepository.getNumberOfPeopleOnFlight(flightId);
        int extraFair=(noOfPeople*(noOfPeople+1))/2;
        int ans=extraFair*50 + noOfPeople*3000;
        return ans;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int ans=airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
        return ans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans= airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
        return ans;
    }

    public String cancelATicket(Integer flightId,Integer passengerId) {
        boolean flag=airportRepository.cancelATicket(flightId,passengerId);
        if(!flag) return "FAILURE";

        return "SUCCESS";
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        boolean flag=airportRepository.bookATicket(flightId,passengerId);
        if(!flag)return "FAILURE";
        return "SUCCESS";
    }
}
