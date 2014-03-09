package system;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import user.AbstractUser;
import user.AuctionCentralStaff;
import user.Bidder;
import auction.Auction;
import auction.Bid;
import auction.Item;
import bidding.Address;
import bidding.CreditCard;

/**
 * This class save all the system data to some txt files
 * @author Kaiyuan Shi
 *
 */
final class DataSaver {

  private static List<AbstractUser> my_user_list = new ArrayList<AbstractUser>();
  private static List<Auction> my_auction_list = new ArrayList<Auction>();
  private static List<Item> my_item_list = new ArrayList<Item>();
  private static List<Bid> my_bid_list = new ArrayList<Bid>();
  private static List<Address> my_address_list = new ArrayList<Address>();
  private static List<CreditCard> my_card_list = new ArrayList<CreditCard>();
  
  private static PrintWriter my_user_writer = null;
  private static PrintWriter my_auction_writer = null;
  private static PrintWriter my_item_writer = null;
  private static PrintWriter my_bid_writer = null;
  private static PrintWriter my_address_writer = null;
  private static PrintWriter my_card_writer = null;

  public static void saveData(final List<AbstractUser> a_user_list,
      final List<Auction> an_auction_list) {
    my_user_list = a_user_list;
    my_auction_list = an_auction_list;
    
    try {
      my_user_writer = new PrintWriter(new FileOutputStream("output/user.txt"));
      my_auction_writer = new PrintWriter(new FileOutputStream("output/auction.txt"));
      my_item_writer = new PrintWriter(new FileOutputStream("output/item.txt"));
      my_bid_writer = new PrintWriter(new FileOutputStream("output/bid.txt"));
      my_address_writer = new PrintWriter(new FileOutputStream("output/address.txt"));
      my_card_writer = new PrintWriter(new FileOutputStream("output/card.txt"));
    } catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage());
    }
    
    
    outputUserLists();
    outputAuctionLists();
    outputItemLists();
    outputBidLists();
    outputCardLists();
    outputAddressLists();
    
    my_user_writer.close();
    my_auction_writer.close();
    my_item_writer.close();
    my_bid_writer.close();
    my_address_writer.close();
    my_card_writer.close();
  }
  
  //output user.txt
  private static void outputUserLists() {
    for (AbstractUser each: my_user_list) {
      
      String output = "";
      if (each instanceof Bidder) {
        
        output += "Bidder`";
        printUser(output, each, my_user_writer);
        
        //card
        output += my_card_list.size() + "`";
        my_card_list.add(((Bidder) each).getCard());
        
        //address
        output += my_address_list.size() + "`";
        my_address_list.add(((Bidder) each).getAddress());
        
        //bids
        for (Bid each_bid: ((Bidder) each).getBids()) {
          output += my_bid_list.size() + "`";
          my_bid_list.add(each_bid);
        }
        
      } else if (each instanceof AuctionCentralStaff) {
        output += "AuctionCentralStaff`";
        printUser(output, each, my_user_writer);
      } else {
        output += "NonProfitUser`";
        printUser(output, each, my_user_writer);
      }
      my_user_writer.println(output);
    }
    
  }
  
  //return out the basic message of a user
  private static void printUser(String a_output, AbstractUser a_user, PrintWriter a_writer) {
    String username = a_user.getUsername();
    String password = a_user.getPassword();
    String first_name = a_user.getFirstName();
    String last_name = a_user.getLastName();
    a_output += (username+ "`" + password + "`");
    a_output += (first_name+ "`" + last_name + "`");
  }
  
  //output auction.txt
  private static void outputAuctionLists() {
    
    for (Auction each: my_auction_list) {
      String output = "";
      
      output += each.getAuctionName() + "`";
      output += each.getContactPerson() + "`";
      output += each.getContactPhone() + "`";
      output += each.getIntakePerson() + "`";
      
      Calendar auction_date = each.getAuctionDate();  
      output += auction_date.get(Calendar.MONTH) + "/";
      output += auction_date.get(Calendar.DAY_OF_MONTH) + "/";
      output += auction_date.get(Calendar.YEAR) + "/";
      output += auction_date.get(Calendar.HOUR_OF_DAY) + "`";
      
      output += each.getAuctionDuration() + "`";
      output += each.getComments() + "`";
      
      for (Item each_item: each.getItems()) {
        output += my_item_list.size() + "`";
        my_item_list.add(each_item);
      }
      
      
      my_auction_writer.println(output);
    }
    
  }
  
  //output item.txt
  private static void outputItemLists() {
    
    for (Item each: my_item_list) {
      String output = "";
      
      output += each.getItemNumber() + "`";
      output += each.getItemName() + "`";
      output += each.getItemQuantity() + "`";
      output += each.getStartingBid() + "`";
      output += each.getDonor() + "`";
      output += each.getSize() + "`";
      output += each.getStorage() + "`";
      output += each.getCondition() + "`";
      output += each.getComments() + "`";
      output += each.getPhotoLocation() + "`";
      output += each.getSellingPrice() + "`";
      
      for (Bid each_bid: each.getBids()) {
        output += my_bid_list.size() + "`";
        my_bid_list.add(each_bid);
      }
      
      my_item_writer.println(output);
    }
    
  }
  
  //output bid.txt
  private static void outputBidLists() {
    for (Bid each: my_bid_list) {
      String output = "";
      
      output += each.getItemName() + "`";
      output += each.getPrice() + "`";
      output += each.getBidderName() + "`";
      
      Calendar bid_time = each.getBidTime();
      output += bid_time.get(Calendar.MONTH) + "/";
      output += bid_time.get(Calendar.DAY_OF_MONTH) + "/";
      output += bid_time.get(Calendar.YEAR) + "/";
      output += bid_time.get(Calendar.HOUR_OF_DAY) + "/";
      output += bid_time.get(Calendar.MINUTE) + "`";
      
      output += my_card_list.size() + "`";
      my_card_list.add(each.getPayment());
      
      my_bid_writer.println(output);
    }
  }
  
  //output card.txt
  private static void  outputCardLists() {
    
    for (CreditCard each: my_card_list) {
      String output = "";
      
      output += each.getCardNum() + "`";
      
      Calendar exp_date = each.getExpDate();
      output += exp_date.get(Calendar.MONTH) + "/";
      output += exp_date.get(Calendar.YEAR) + "`";
      
      output += each.getCSC() + "`";
      output += each.getCardHolder() + "`";
      
      output += my_address_list.size() + "`";
      my_address_list.add(each.getAddress());
      
      output += each.getBank() + "`";
      
      my_card_writer.println(output);
    }
    
  }
  
  //output address.txt
  private static void outputAddressLists() {
    for (Address each: my_address_list) {
      String output = "";
      
      output += each.getMyStreet() + "`";
      output += each.getMyApt() + "`";
      output += each.getMyCity() + "`";
      output += each.getMyState() + "`";
      output += each.getMyZip() + "`";
      
      my_address_writer.println(output);
    }
    
    
  }
  
  
}
