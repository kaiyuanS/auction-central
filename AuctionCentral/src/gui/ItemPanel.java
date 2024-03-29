package gui;

import auction.Auction;
import auction.Bid;
import auction.Item;
import bidding.CreditCard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import system.AuctionCentralSystem;
import user.AuctionCentralStaff;
import user.Bidder;
import user.Guest;
import user.User;

/**
 * Display panel for an auction Item.
 * 
 * @author Josh Hammer
 * @author Kevin Alexander
 * @version 3/13/2014
 *
 */
@SuppressWarnings("serial")
public class ItemPanel extends JPanel {

  /**
   * Separator character between hour and minutes (i.e. 12:00).
   */
  private static final String TIME_SEPARATOR = ":";

  /**
   * Separator character between date items (year, month, day).
   */
  private static final String DATE_SEPARATOR = "/";

  /**
   * Title text for the error pop up windows.
   */
  private static final String ERROR_MESSAGE_WINDOW_TITLE = "Error";

  /**
   * Columns to initialize the comments text field with.
   */
  private static final int COMMENTS_FIELD_COLUMNS = 20;

  /** The default height of the panel. */
  private static final int DEFAULT_HEIGHT = 680;

  /** The default width of the panel. */
  private static final int DEFAULT_WIDTH = 824;

  /** The default size for this JPanel. */
  private static final Dimension DEFAULT_SIZE = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  
  /** The new line character. */
  private static final String NEW_LINE = "\n";

  /** The default font for the labels. */
  private static final String ARIAL = "Arial";

  /** The number 10. */
  private static final int TEN = 10;

  /** The default font size for the instruction labels. */
  private static final int INSTRUCTION_FONT_SIZE = 12;

  /** The default font size for the instruction labels. */
  private static final int MAIN_FONT_SIZE = 24;

  /** The default box height. */
  private static final int BOX_HEIGHT = 45;

  /** The default box height for input spacing. */
  private static final int BOX_HEIGHT_TWO = 35;

  /** The default box width. */
  private static final int BOX_WIDTH = 5;

  /** The default border, a black line. */
  private static final Border BLACK_LINE = BorderFactory.createLineBorder(Color.BLACK, 3);

  /** The label that shows the item's name. */
  private static final JLabel ITEM_TITLE = new JLabel("Auction Inventory Item");
  
  /**
   * System reserved character. Should not be used in text.
   */
  private static String SYSTEM_RESERVED = "`";

  /**
   * A reference to the main application frame.
   */
  private ApplicationFrame my_app_frame;

  /** The auction to display in the panel. */
  private final Auction my_auction;

  /** The item to display in the panel. */
  private final Item my_item;

  /** The label that shows the item's number. */
  private int my_number;
  
  /** The selling price of the item.*/
  private  double my_price;
  
  /**The donor of the item.*/
  private  String my_donor;
  
  /** The quantity of the item.*/
  private  int my_quantity;
  
  /** The minimum bid.*/
  private double my_minimum_bid;
  
  /** The size of the item.*/
  private String my_size;
  
  /** The storage of the item.*/
  private String my_storage;
  
  /** The condition of the item.*/
  private  String my_condition;
  
  /** The comments on the item.*/
  private  String my_comment;

  /** The name of the item. */
  private String my_name;
  
  // The JLabels for Item Information - Kevin
  
  /**
   * Number label.
   */
  private JLabel my_num_lab = new JLabel();
  
  /**
   * Item number label.
   */
  private final JLabel my_inum_lab = new JLabel("Item #");
  
  /**
   * Sell price label.
   */
  private final JLabel my_sellprice_lab = new JLabel("Selling Price:");
  
  /**
   * Auction name label.
   */
  private final JLabel my_aname_lab = new JLabel("Auction Name:");
  
  /**
   * Item name label.
   */
  private final JLabel my_iname_lab = new JLabel("Item Name:");
  
  /**
   * Quantity label.
   */
  private final JLabel my_qty_lab = new JLabel("QTY:");
  
  /**
   * Minimum bid label.
   */
  private final JLabel my_minbid_lab = new JLabel("Min. Bid:");
  
  /**
   * Donor label.
   */
  private final JLabel my_donor_lab = new JLabel("Donor:");
  
  /**
   * Size label.
   */
  private final JLabel my_size_lab = new JLabel("Size:");
  
  /**
   * Storage label.
   */
  private final JLabel my_storage_lab = new JLabel("Storage:");
  
  /**
   * Condition label.
   */
  private final JLabel my_cond_lab = new JLabel("Condition:");
  
  /**
   * Comments label.
   */
  private final JLabel my_comm_lab = new JLabel("Comments:");

  /**
   * Panel containing item's picture.
   */
  private final JPanel my_pic = new JPanel();

  /** The button used to save the item information. */
  private final JButton my_save_button = new JButton("Save");
  
  /** The button used to go back. */
  private final JButton my_back_button = new JButton("Back");
  
  /**
   * Button to place a bid on the item.
   */
  private final JButton my_bid_button = new JButton("Bid");
  
  /** The button used to unseal the bid. */
  private final JButton my_unseal_button = new JButton("Unseal");
  
  /**
   * Text field for auction name.
   */
  private JFormattedTextField my_auction_name_input = new JFormattedTextField();
  
  /**
   * Text field for item number.
   */
  private JFormattedTextField my_item_number_input = new JFormattedTextField();
  
  /** The name of the item input. */
  private JFormattedTextField my_item_name_input = new JFormattedTextField();

  /**The price of the item.*/
  private JFormattedTextField my_price_input = new JFormattedTextField();

  /**The quantity of the item.*/
  private JFormattedTextField my_quantity_input = new JFormattedTextField();

  /**The minimum bid price of the item.*/
  private JFormattedTextField my_minimum_bid_input = new JFormattedTextField();

  /**The name of the donor of the item.*/
  private JFormattedTextField my_donor_input = new JFormattedTextField();

  /**The size of the item.*/
  private JFormattedTextField my_size_input = new JFormattedTextField();

  /**The storage of the item.*/
  private JFormattedTextField my_storage_input = new JFormattedTextField();

  /**The condition of the item.*/
  private JFormattedTextField my_condition_input = new JFormattedTextField();

  /**The comments on the item.*/
  //private JFormattedTextField my_comment_input = new JFormattedTextField();
  
  private JTextField my_comment_input = new JTextField(COMMENTS_FIELD_COLUMNS);

  /** The auction central system. */
  private final AuctionCentralSystem my_system;

  /**
   * 
   * @param the_item The item to display panel for.
   * @param the_frame The frame the panel belongs to.
   * @param the_auction The auction the item belongs to.
   * @param an_editable Whether or not the item fields will be editable initially.
   * @param a_user The user logged into the system.
   */
  public ItemPanel(final Item the_item, final ApplicationFrame the_frame,
      final Auction the_auction, 
      final boolean an_editable, final User a_user) {
    super(new BorderLayout());
    
    my_app_frame = the_frame;
    my_system = my_app_frame.getSystem();
    my_auction = the_auction;
    my_item = the_item;
    
    //final String temp = "" + my_item.getItemNumber();
    
    createItem();
    setupSaveButton();
    setupBidButton();
    setupBackButton();
    setupUnsealButton();
    configurePanel(a_user);
    createItemLabels();
    allowEdits(an_editable);
  }

  /**
   * Configure the panel based on the current user.
   * 
   * @param a_user The user logged into the system.
   */
  private void configurePanel(final User a_user) {
    setButtonVisibility(a_user);
    setPreferredSize(DEFAULT_SIZE);
    setBorder(BLACK_LINE);
    setFocusable(true);
    createItem();
  }

  /**
   * Allows the inputs to be editable fields. 
   * @param the_edit_flag - true if editable.
   */
  private void allowEdits(final boolean the_edit_flag) {
    my_auction_name_input.setEditable(false);
    my_item_number_input.setEnabled(false);
    my_item_name_input.setEditable(the_edit_flag);
    my_donor_input.setEditable(the_edit_flag);
    my_size_input.setEditable(the_edit_flag);
    my_price_input.setEditable(the_edit_flag);
    my_quantity_input.setEditable(the_edit_flag);
    my_minimum_bid_input.setEditable(the_edit_flag);
    my_storage_input.setEditable(the_edit_flag);
    my_condition_input.setEditable(the_edit_flag);
    //my_comment_input.setEditable(the_edit_flag);
    
    if (my_system.getCurrentUser() instanceof Bidder) {
      my_comment_input.setEditable(the_edit_flag); 
    } else {
      my_comment_input.setEditable(the_edit_flag);
    }
  }
  
  /**
   * Creates the auction used for editing.
   */
  private void createItem() {
    /*if (my_auction.getItemCount() == 0) { // first item
      my_number = 1;
    } else if (my_item.getItemNumber() == 0) { // new item
      my_number = my_auction.getItemCount() + 1;
      my_item.setItemQuantity(1);
      my_item.setItemNumber(my_number);
      my_quantity = 1;
    } else { // not a new number
      my_number = my_item.getItemNumber();
      my_quantity = my_item.getItemQuantity();
    }*/ //old
    
    //new 
    if (my_item.getItemNumber() == 0) { // new item
      my_number = my_auction.getItemCount() + 1;
      my_item.setItemNumber(my_number);
    } else {
      my_number = my_item.getItemNumber(); 
      my_quantity = my_item.getItemQuantity();
    }
    
    my_name = my_item.getItemName();
    my_donor = my_item.getDonor();
    my_size = my_item.getSize();
    my_price = my_item.getSellingPrice();
    
    my_minimum_bid = my_item.getStartingBid();
    my_storage = my_item.getStorage();
    my_condition = my_item.getCondition();
    my_comment = my_item.getComments();
    initializeInput();
  }

  /**
   * Initializes the input entries.
   */
  private void initializeInput() {
    my_num_lab.setText(String.valueOf(my_number));
    my_auction_name_input.setText(my_auction.getAuctionName());
    my_item_number_input.setText("" + my_number);
    my_item_name_input.setText(my_name);
    my_donor_input.setText(my_donor);
    my_size_input.setText(my_size);
    my_price_input.setText("" + my_price);
    my_quantity_input.setText("" + my_quantity);
    my_minimum_bid_input.setText("" + my_minimum_bid);
    my_storage_input.setText(my_storage);
    my_condition_input.setText(my_condition);
    my_comment_input.setText(my_comment);
    
    allowEdits(false);
  }
  
  /**
   * Sets up the save button.
   */
  private void setupSaveButton() {
    my_save_button.setMnemonic(KeyEvent.VK_S);
    my_save_button.setToolTipText("Save the Item Data");
    my_save_button.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        
        if (my_item_name_input.getText().length() == 0 
            || my_quantity_input.getText().length() == 0
            || my_minimum_bid_input.getText().length() == 0
            || my_donor_input.getText().length() == 0
            || my_size_input.getText().length() == 0
            || my_storage_input.getText().length() == 0
            || my_condition_input.getText().length() == 0) {     
          JOptionPane.showMessageDialog(null, 
              "Fields cannot be blank.", 
               ERROR_MESSAGE_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
        } else if (my_item_name_input.getText().contains(SYSTEM_RESERVED)
            || my_quantity_input.getText().contains(SYSTEM_RESERVED)
            || my_minimum_bid_input.getText().contains(SYSTEM_RESERVED)
            || my_donor_input.getText().contains(SYSTEM_RESERVED)
            || my_size_input.getText().contains(SYSTEM_RESERVED)
            || my_storage_input.getText().contains(SYSTEM_RESERVED)
            || my_condition_input.getText().contains(SYSTEM_RESERVED)
            || my_comment_input.getText().contains(SYSTEM_RESERVED)) {     
          JOptionPane.showMessageDialog(null, 
              "Invalid Character: " + SYSTEM_RESERVED, 
               ERROR_MESSAGE_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
        } else {
          try {
            
            //System.out.println(my_item_number_input.getText());
            //System.out.println(my_minimum_bid_input.getText());
            
            final int item_quantity = Integer.parseInt(my_quantity_input.getText());
            final double item_price = Double.parseDouble(my_minimum_bid_input.getText());
            
            saveItem();
            
            if (item_quantity <= 0) {
              JOptionPane.showMessageDialog(null, 
                  "item number must be positive.", 
                   ERROR_MESSAGE_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
            } else if (item_price <= 0) {
              JOptionPane.showMessageDialog(null, 
                  "item price must be positive.", 
                   ERROR_MESSAGE_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
            } else if (!my_auction.getItems().contains(my_item)) {
              //System.out.println("new item #: " + my_item.getItemNumber());
              my_auction.addItem(my_item);
              my_app_frame.showInventory(my_auction);
            } else {
              my_app_frame.showInventory(my_auction);
            }
            
          } catch (final NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, 
                "Invalid number format.", 
                 ERROR_MESSAGE_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });
  }
  
  /**
   * Sets up the bid button.
   */
  private void setupBidButton() {
    my_bid_button.setMnemonic(KeyEvent.VK_I);
    my_bid_button.setToolTipText("Bid on an item");
    my_bid_button.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        boolean has_bid = false;
        boolean success_bid = false;
        final Bidder bidder = (Bidder) my_app_frame.getSystem().getCurrentUser();
        double bid_price = 0.0;
        for (Bid bid : bidder.getBids()) {
          if (my_item.getBids().contains(bid)) {
            has_bid = true;
            break;
          }
        }
        if (has_bid) {
          JOptionPane.showMessageDialog(null, "You already have a bid for that item!",
              "Bid Already Made", JOptionPane.WARNING_MESSAGE);
        } else {
          do {
            String bet_string =
                JOptionPane.showInputDialog(null, "Bid price: ", "$0.00");
            
            try {
              
              if (bet_string.length() != 0 && bet_string.charAt(0) == '$') {
                bet_string = bet_string.substring(1);
              }
              
              bid_price = Double.parseDouble(bet_string);
              
              //final Item the_item = my_item_list.get(my_index);
              if (bid_price >= my_item.getStartingBid()) {
                success_bid = true;
                final Bid temp_bid = new Bid(my_item.getItemName(), bid_price, 
                    bidder.getUsername(),
                    Calendar.getInstance(), bidder.getCard());
                
                bidder.addBid(temp_bid);
                my_item.addBid(temp_bid);
              } else if (!success_bid) {
                JOptionPane.showMessageDialog(null, "Bid Too Low!", "Bid Too Low",
                    JOptionPane.WARNING_MESSAGE);
              }
            } catch (final NumberFormatException e) {
              JOptionPane.showMessageDialog(null, "Wrong input!", "ERROR", 
                  JOptionPane.WARNING_MESSAGE);
            } catch (final NullPointerException e) {
              success_bid = true;
            }
              
            //final Item the_item = my_item_list.get(my_index);
            
          } while (!success_bid);
        }
      }
    });
  }
  
  /**
   * Sets up the back button.
   */
  private void setupBackButton() {
    my_back_button.setMnemonic(KeyEvent.VK_B);
    my_back_button.setToolTipText("Go Back to the Inventory Screen");
    my_back_button.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        my_app_frame.showInventory(my_auction);
      }
    });
  }
  
  /**
   * Sets up the unseal bids button.
   */
  private void setupUnsealButton() {
    my_unseal_button.setMnemonic(KeyEvent.VK_U);
    my_unseal_button.setToolTipText("Unseal the bids of this item");
    my_unseal_button.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        
        final Calendar unseal_time = (Calendar) my_auction.getAuctionDate().clone();
        unseal_time.set(Calendar.HOUR_OF_DAY, 
            unseal_time.get(Calendar.HOUR_OF_DAY) + my_auction.getAuctionDuration());
        final Calendar current = Calendar.getInstance();
        
        if (current.compareTo(unseal_time) <= 0) {
          //not the time
          String message;
          message = "You can not unseal the bid unitl ";
          message += (unseal_time.get(Calendar.MONTH) + 1) + DATE_SEPARATOR;
          message += unseal_time.get(Calendar.DAY_OF_MONTH) + DATE_SEPARATOR;
          message += unseal_time.get(Calendar.YEAR) + " ";
          message += unseal_time.get(Calendar.HOUR_OF_DAY) + TIME_SEPARATOR;
          message += String.format("%02d", unseal_time.get(Calendar.MINUTE)) + "!";
          JOptionPane.showMessageDialog(null, message);
        } else {
          
          final Bid win_bid = my_item.unsealBid();
          String message;
          if (win_bid == null) {
            //no win bid
            message = "No one bid for this item.";
          } else {
            
            //show win bid
            final CreditCard win_card = win_bid.getPayment();
            final Calendar card_exp = win_card.getExpDate();
            String card_exp_str = (card_exp.get(Calendar.MONTH) + 1) + DATE_SEPARATOR;
            card_exp_str += card_exp.get(Calendar.YEAR);
            
            message = "Winner: " + win_bid.getBidderName() + NEW_LINE;
            message += "Price: " + win_bid.getPrice() + NEW_LINE;
            message += "Bid time: " + (win_bid.getBidTime().get(Calendar.MONTH) + 1) 
                + DATE_SEPARATOR;
            message += win_bid.getBidTime().get(Calendar.DAY_OF_MONTH) + DATE_SEPARATOR;
            message += win_bid.getBidTime().get(Calendar.YEAR) + "/ ";
            message += win_bid.getBidTime().get(Calendar.HOUR_OF_DAY) + TIME_SEPARATOR;
            message += win_bid.getBidTime().get(Calendar.MINUTE) + NEW_LINE;
            message += "Credit Card #: " + win_card.getCardNum() + NEW_LINE;
            message += "Exp Date: " + card_exp_str + NEW_LINE;
            message += "Card CSC: " + win_card.getCSC();
            
          }
          
          JOptionPane.showMessageDialog(null, message);
        }  
        
      }
    });
  }
  
  /**
   * Sets button visibility to false if they should not be available to the user
   * type that is logged in.
   * 
   * @param a_user
   *          The user that determines what buttons are available.
   */
  private void setButtonVisibility(final User a_user) {
    if (!(a_user instanceof Bidder)) {
      my_bid_button.setVisible(false);
    }
    
    if (a_user instanceof Bidder || a_user instanceof Guest) {
      my_save_button.setEnabled(false);
      //TODO my_comment_input.setEditable(false);
    }
        
    if (!(a_user instanceof AuctionCentralStaff)) {
      my_unseal_button.setVisible(false);
    }
  }
  
  /**
   * Create the item text labels.
   */
  private void createItemLabels() {
    
    //final JPanel center = new JPanel(new BorderLayout());
    final JPanel west = new JPanel();
    west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
    final JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
    final JPanel east = new JPanel(new GridLayout(0, 1));
    final JPanel south = new JPanel(new BorderLayout());
    final JPanel southcomment = new JPanel(new FlowLayout());
    final JPanel southset = new JPanel(new FlowLayout());
    
    ITEM_TITLE.setFont(new Font(ARIAL, Font.BOLD, MAIN_FONT_SIZE));
    my_inum_lab.setFont(new Font(ARIAL, Font.BOLD, INSTRUCTION_FONT_SIZE));
    my_aname_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_sellprice_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_iname_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_qty_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_minbid_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_donor_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_size_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_storage_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_cond_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    my_comm_lab.setFont(new Font(ARIAL, Font.PLAIN, INSTRUCTION_FONT_SIZE));
    
    north.add(ITEM_TITLE, BorderLayout.CENTER);
    
    final JPanel east_flow = new JPanel(new FlowLayout());
    east_flow.add(my_inum_lab);
    east_flow.add(my_num_lab);
    east.add(east_flow);
    
    east.add(my_pic);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, TEN)));
    
    west.add(my_aname_lab);
    
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_iname_lab);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_qty_lab);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_minbid_lab);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_donor_lab);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_size_lab);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_storage_lab);
    west.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT + TEN)));
    west.add(my_cond_lab);
    
    
    final JPanel text_input = new JPanel();
    text_input.setLayout(new BoxLayout(text_input, BoxLayout.Y_AXIS));
    text_input.add(my_auction_name_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_item_name_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_quantity_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_minimum_bid_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_donor_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_size_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_storage_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
    text_input.add(my_condition_input);
    text_input.add(Box.createRigidArea(new Dimension(BOX_WIDTH, BOX_HEIGHT_TWO)));
        
    southcomment.add(my_comm_lab);
    
    southcomment.add(my_comment_input);
    
    southcomment.add(my_save_button);
    
    south.add(southcomment, BorderLayout.CENTER);
        
    if (my_app_frame.getSystem().getCurrentUser() instanceof Bidder) {
      southset.add(my_bid_button);
    }
    
    //southset.add(my_bid_button, BorderLayout.CENTER);
    southset.add(my_back_button, BorderLayout.WEST);
    southset.add(my_unseal_button, BorderLayout.WEST);
    
    south.add(southset, BorderLayout.SOUTH);
    
    add(north, BorderLayout.NORTH);
    add(east, BorderLayout.EAST);
    add(west, BorderLayout.WEST);
    add(text_input, BorderLayout.CENTER);
    add(south, BorderLayout.SOUTH);
    
  }
  
  /**
   * Saves the item to the system.
   */
  private void saveItem() {
    
    my_item.setItemName(my_item_name_input.getText().trim());
    my_item.setItemQuantity(Integer.parseInt(my_quantity_input.getText().trim()));
    my_item.setStartingBid(Double.parseDouble(my_minimum_bid_input.getText().trim()));
    my_item.setDonor(my_donor_input.getText().trim());
    my_item.setSize(my_size_input.getText().trim());
    my_item.setStorage(my_storage_input.getText().trim());
    my_item.setCondition(my_condition_input.getText().trim());
    my_item.setComments(my_comment_input.getText().trim());
    my_item.setSellingPrice(0.0);
  }
}