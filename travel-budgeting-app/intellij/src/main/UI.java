package main;
import java.util.*;
import java.lang.Math;
import java.time.*;

public class UI {
    /*
    
    User user;
    Controller controller;

    public UI (User u) {
        this.user = u;
        this.controller = new Controller(this.user);
    }
    
    // During login, we will get the user data and initialize it in the UI class after

    */

    Controller controller = new Controller(Main.user);
    Scanner scan = new Scanner(System.in);
    String response;

    void play() {

        while(true){
            try{
                System.out.println("Hello! Welcome to spendies, an app that helps you manage your traveling budgets.");
                System.out.println("Please enter your name.");
                response = scan.nextLine();
                controller.editUser(response, true);
                break;
            }
            catch(Exception e){}
        }

        while (true) {
            try {
                System.out.println("Hey, " + controller.getName() + "! Would you like to start a new trip (enter a) or manage your current trip (b)?");
                response = scan.nextLine();
                if (response.equals("a")) {
                    System.out.println("You may manage the trip's overall budget, dates, and location.");
                    System.out.println("Please enter your trip's total budget in USD (like 5.99).");

                    controller.editTrip(Math.floor(Double.parseDouble(scan.nextLine())*100)/100);

                    while(true) {
                        System.out.println("Please enter your trip's start date (Year-Month-Day). (Leave blank for today)");

                        LocalDate startDate;
                        String startDateStr = scan.nextLine();
                        if (startDateStr.equals("")) {
                            startDate = LocalDate.now();
                        } else {
                            startDate = LocalDate.parse(startDateStr);
                        }

                        System.out.println("Please enter your trip's end date (Year-Month-Day). (Leave blank for today)");

                        LocalDate endDate;
                        String endDateStr = scan.nextLine();
                        if (endDateStr.equals("")) {
                            endDate = LocalDate.now();
                        } else {
                            endDate = LocalDate.parse(endDateStr);
                        }

                        if (startDate.isAfter(endDate)) {
                            System.out.println("You did not enter a valid end date.");
                        }
                        else {
                            controller.editTrip(startDate,true);
                            controller.editTrip(endDate,false);
                            break;
                        }
                    }

                    System.out.println("Please enter your trip's location.");
                    controller.editTrip(scan.nextLine());

                    System.out.println("Your information has been saved.");


                } else if (response.equals("b")) {
                    while(true){
                        System.out.println("Your current trip is shared with " + controller.getCurrentTrip().getTripParticipants().size() + " friend(s): " + controller.getCurrentTrip().getTripParticipants());
                        System.out.println("Would you like to add friends to your current trip (y/n)?");
                        response = scan.nextLine();
                        if (response.equals("y")) {
                            System.out.println("Which friend would you like to add to your current trip?");
                            System.out.println(controller.getFriends());
                            String friendName = scan.nextLine();

                            User friend = controller.searchFriends(friendName, 2);


                            if (friend==null) {
                                System.out.println(friendName + " doesn't exist in your friends list.");
                            }
                            else{
                                boolean friendIsAdded = controller.getCurrentTrip().addParticipant(friend);
                                if (friendIsAdded) {
                                    System.out.println("You have added " + friendName + " to your current trip.");
                                }
                                else{
                                    System.out.println(friendName + " already has access to your current trip.");
                                }
                            }
                        }
                        else{
                            break;
                        }
                    }
                    System.out.println("You may manage the trip's overall budget, dates, and location.");

                    System.out.println("Your trip's current overall budget is $" + String.format("%.2f", controller.getCurrentTrip().getBudget()) + ".");
                    System.out.println("Please enter your trip's new overall budget in USD (like 5.99) or enter 's' to keep it the same.");
                    response = scan.nextLine();
                    if (!response.equals("s")) {
                        controller.editTrip(Double.parseDouble(response));
                    }

                    while(true) {
                        System.out.println("Your trip's current start date is " + controller.getCurrentTrip().getTripStart() + ".");
                        System.out.println("Please enter your trip's new start date (Year-Month-Day) or enter 's' to keep it the same.");

                        LocalDate startDate;
                        String startDateStr = scan.nextLine();
                        if (!startDateStr.equals("s")) {
                            startDate = LocalDate.parse(startDateStr);
                        }
                        else{
                            startDate = controller.getCurrentTrip().getTripStart();
                        }

                        System.out.println("Your trip's current end date is " + controller.getCurrentTrip().getTripEnd() + ".");
                        System.out.println("Please enter your trip's new end date (Year-Month-Day) or enter 's' to keep it the same.");

                        LocalDate endDate;
                        String endDateStr = scan.nextLine();
                        if (!endDateStr.equals("s")) {
                            endDate = LocalDate.parse(endDateStr);
                        }
                        else{
                            endDate = controller.getCurrentTrip().getTripEnd();
                        }

                        if (startDate.isAfter(endDate)) {
                            System.out.println("Invalid end date.");
                        }
                        else {
                            controller.editTrip(startDate, true);
                            controller.editTrip(endDate, false);
                            break;
                        }
                    }

                    System.out.println("Your trip's current location is " + controller.getCurrentTrip().getTripLocation() + ".");
                    System.out.println("Please enter your trip's location or enter 's' to keep it the same.");
                    response = scan.nextLine();
                    if (!response.equals("s")) {
                        controller.editTrip(response);
                    }

                    System.out.println("Your information has been saved.");
                }

                System.out.println("Enter 'q' to quit or anything else to stay.");
                response = scan.nextLine();
                if (response.equals("q")) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("You did not enter a valid input.");
            }
        }
    }
}
