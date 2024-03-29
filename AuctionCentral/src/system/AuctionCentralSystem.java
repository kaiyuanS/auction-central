package system;

import auction.Auction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JOptionPane;

import user.NonProfitUser;
import user.User;

/**
 * This class is the core system of the Auction Central System.
 * @author Kaiyuan Shi
 * @version Win. 2014
 */
public class AuctionCentralSystem implements Observer {
 
  /**
   * Path to the user input/output data file.
   */
  private static final String USER_NAMES_PATH = "data/usernames.txt";
 
  /**
   * Space character.
   */
  private static final String SPACE = " ";
 
  /**
   * The list of users in the system.
   */
  private List<User> my_users;
  
  /**
   * The current user who use the system.
   */
  private User my_current_user;
  
  /**
   * A list of current auction in the system.
   */
  private List<Auction> my_auction;
  
  /**
   * A list of past auction in the system.
   */
  private List<Auction> my_past_auction;
 
  /**
   * The constructor, initialized the system.
   * in check-in5 manually set the current user is a non-profit user
   * 
   * @throws IOException if their is a problem loading or saving the file.
   */
  public AuctionCentralSystem() throws IOException {
    
    my_auction = new ArrayList<Auction>();
    my_past_auction = new ArrayList<Auction>();
    my_users = new ArrayList<User>();
    
    loadingData();
    
    //*************************************
    //for demo and test
    /*
    /*for (int i = 0; i < 15; i++) {
      
      //my_users.add(new Bidder("bidder0", "password0", "bob", "dole"));
      
      //add 15 non-profit users
      my_users.add(new NonProfitUser("username" + i, "password" + i, "first name" + i,
          "last name" + i, "non-profit" + i));
      
      //add 15 auctions form 2014-4-1, 3, 5, 7, 9......29
      //from 9:00 everyday, duration 2 hours
      final Calendar current = Calendar.getInstance();
      current.set(2014, 3, 1 + (i * 2), 9, 0);
      
      final Auction demo_auction = new Auction("auction name" + i, "contact person" + i,
          "phone number" + i, "intake person" + i, current, 2, "comments" + i);
      
      my_auction.add(demo_auction);
    }
    
    //add 8 auctions form 2014-5-1, 3, 5, 7, 9......17
    //from 9:00 everyday, duration 2 hours
    for (int i = 0; i < 8; i++) {
      final Calendar current = Calendar.getInstance();
      current.set(2014, 4, 1 + (i * 2), 9, 0);
      final Auction demo_auction = new Auction("auction name" + (i + 16),
          "contact person" + (i + 16), "phone number" + (i + 16),
          "intake person" + (i + 16), current, 2, "comments" + (i + 16));
      my_auction.add(demo_auction);
    }
    
    //add last auction on 2014-4-30 used to test BR #3
    final Calendar current_4_30 = Calendar.getInstance();
    current_4_30.set(2014, 3, 30, 9, 0);
    final Auction demo_auction_4_30 = new Auction("auction name 4/30", "contact person4/30",
        "phone number 4/30", "intake person 4/30", current_4_30, 2, "comments 4/30");
    my_auction.add(demo_auction_4_30);
    //add one auction on the last user used to test BR #5
    ((NonProfitUser) my_users.get(14)).getAuction().add(demo_auction_4_30);
    
    my_current_user = my_users.get(0);*/
    //for demo and test*/
    //************************************
    
    
  }
  
  /**
   * Loads the user.
   * @param an_user_list - a list of users.
   */
  public void loadUser(final List<User> an_user_list) {
    my_users = an_user_list;
  }
  
  /**
   * Load the auction.
   * @param an_auction_list - a list of auctions.
   */
  public void loadAuction(final List<Auction> an_auction_list) {
    my_auction = an_auction_list;
    refreshAuction();
  }
  
  
  
  /**
   * This method load all the data after starting the system.
   * 
   * @throws IOException if their is a problem loading or saving the file.
   */
  private void loadingData() throws IOException {
    DataLoader.loadData(this);
  }
  
  /**
   * This method save all the data before closing the system.
   */
  public void savingData() {
    
    my_auction.addAll(my_past_auction);
    DataSaver.saveData(my_users, my_auction);
    
  }
  
  /**
   * This method get the List of the current Auction.
   * @return a list of the current Auction
   * <dt><b>Postconditions:</b><dd>
   * All of the past auction would be removed.
   */
  public List<Auction> getAuctionList() {
    return my_auction;
  }
  
  /**
   * This method get the List of the past Auctions.
   * @return a list of the past Auction
   * @author Keith Lueneburg
   */
  public List<Auction> getPastAuctionList() {
    return my_past_auction;
  }
  
  /**
   * This method returns the current user.
   * @return the current user
   */
  public User getCurrentUser() {
    return my_current_user;
  }
   
  /**
   * This method add a new Auction to the auction list.
   * 
   * <dt><b>Preconditions:</b> The auction is in compliance with all business rules. <dd>
   * <dt><b>Postconditions:</b> The auction is added to the system. <dd>
   * 
   * @param an_auction the new auction would be added to the list
   * @return null if the new auction was successfully added, else the error message
   */
  public String addAuction(final Auction an_auction) {
    String error_message = null;
    
    if (an_auction == null) {
      error_message = "input Auction is null!";
      return error_message;
    } else if (!(my_current_user instanceof NonProfitUser)) {
      error_message = "current user is not a non-profit user!"; // not right user
      return error_message;
    }
    
    //refreshAuction();
    
    //Test Business Rule #1, 2, 3, 4
    error_message = AuctionDateTester.getSolution(an_auction, my_auction);
    
    //Test Business Rule #5
    final List<Auction> current_user_list = new ArrayList<Auction>();
    
    for (Auction each: my_auction) {
      if ((my_current_user.getFirstName() + SPACE + my_current_user.getLastName()).equals(
          each.getContactPerson())) {
        current_user_list.add(each);
      }
    }
    
    for (Auction each: my_past_auction) {
      if ((my_current_user.getFirstName() + SPACE + my_current_user.getLastName()).equals(
          each.getContactPerson())) {
        current_user_list.add(each);
      }
    }
    
    for (Auction each: current_user_list) {
      if (each.getAuctionDate().get(Calendar.YEAR)
          == an_auction.getAuctionDate().get(Calendar.YEAR)) {
        error_message = "No more than one auction per year"
            + " per Non-profit organization can be scheduled"; //two auction in same year
      }
    }
    
    if (error_message == null) {
      my_auction.add(an_auction);
    }
    
    
    return error_message;
  }
  
  /**
   * This method refresh the auction list, remove all the auctions before or on today.
   * <dt><b>Postconditions:</b><dd>
   * All Auctions in the list are in the future
   */
  private void refreshAuction() {

    final Calendar today = Calendar.getInstance();
    final List<Auction> remove_auction = new ArrayList<Auction>();
    
    for (Auction each: my_auction) {
      final Calendar auction_end = Calendar.getInstance();
      final Calendar auction_date = each.getAuctionDate();
      final int auction_year = auction_date.get(Calendar.YEAR);
      final int auction_month = auction_date.get(Calendar.MONTH);
      final int auction_day = auction_date.get(Calendar.DATE);
      final int auction_hour = auction_date.get(Calendar.HOUR_OF_DAY);
      auction_end.set(auction_year, auction_month, auction_day,
          auction_hour + each.getAuctionDuration(), 0, 0);
      
      
      if (today.compareTo(auction_end) > 0) { 
        
        final Auction past = each;
        my_past_auction.add(past);
        remove_auction.add(each);
      }
    }
    
    my_auction.removeAll(remove_auction);
  }
  
  /**
   * Just for my Junit test, DO NOT USE!!!
   * @param an_index - the index of the user.
   */
  public void setUser(final int an_index) {
    my_current_user = my_users.get(an_index);
  }

  /**
   * Adds a user to the list of users.
   * @param a_user the user to be added to the list of users
   */
  public void addUser(final User a_user) {
    my_users.add(a_user);
  }
  
  /**
   * Checks to see if a given username exists in the system.
   * 
   * @param the_username The username to check.
   * 
   * @return true if the user name is valid.
   */
  public User isValidUser(final String the_username) {
    User valid_user = null;
        
    for (User u : my_users) {
      if (u.getUsername().equals(the_username)) {
        valid_user = u;
        break;
      }
    }
    
    return valid_user;
  }
  
  /**
   * Used to delete an auction.
   * @param an_auction - the auction to delete.
   * @return is_success - if it was deleted.
   */
  public boolean deleteAuction(final Auction an_auction) {
    Auction delete_auction = null;
    boolean is_success = false;
    for (Auction each: my_auction) {
      if (each.getAuctionName().equals(an_auction.getAuctionName())) {
        delete_auction = each;
        is_success = true;
        break;
      }
    }
    my_auction.remove(delete_auction);
    return is_success;
  }
  
  /**
   * Checks to see if a given username exists in the system.
   * 
   * @param the_username The username to check.
   * 
   * @return true if the user name is valid.
   */
  public static boolean isValidUserStatic(final String the_username) {
    boolean is_valid = false;
    Scanner sc = null;
    
    try {
      sc = new Scanner(new File(USER_NAMES_PATH));
    
      // Check username argument against user name list from disk.
      while (sc.hasNext()) {
        if (sc.next().equals(the_username)) {
          is_valid = true;
          break;
        }
      }
      
    } catch (final FileNotFoundException fnfe) {
      // Show an error message and exit if the file is not found.
      JOptionPane.showMessageDialog(null,
          "Database not found. Please check with your system administrator", 
          "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    } finally {
      if (sc != null) {
        sc.close();
      }
    }
    return is_valid;
  }

  @Override
  public void update(final Observable the_observable, final Object the_arg) {
    if (the_arg instanceof User) {
      my_current_user = (User) the_arg;
    }
  }
  
  /**
   * Returns true if there is a user with the same first AND last name.
   * 
   * @param the_first_name First name to check.
   * @param the_last_name Last name to check.
   * @return Whether the name is a duplicate
   * 
   * @author Keith Lueneburg
   */
  public boolean duplicateFirstLastName(final String the_first_name,
      final String the_last_name) {
    boolean is_duplicate = false;
    
    for (User u : my_users) {
      if (u.getFirstName().equals(the_first_name) && u.getLastName().equals(the_last_name)) {
        is_duplicate = true;
        break;
      }
    }
    return is_duplicate;
  }
  
}
