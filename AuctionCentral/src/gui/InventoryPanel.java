package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import user.User;
import auction.Auction;
import auction.Item;

/**
 * 
 * @author Kaiyuan Shi
 *
 */
public class InventoryPanel extends JPanel {

  List<Item> my_item_list;
  
  /**
   * 
   */
  private static final int DEFAULT_WIDTH = 824;
  /**
   * 
   */
  private static final int DEFAULT_HEIGHT = 680;
  /**
   * 
   */
  private static final int INSET_SIZE = 20;
  /**
   * The JList component that holds the auctions.
   */
  private JList my_jlist;
  /**
   * A scrollable view of the list.
   */
  private JScrollPane my_scrollpane;
  /**
   * The index of the auction that is selected.
   */
  private int my_index;
  /**
   * A panel that will hold the buttons.
   */
  private JPanel my_button_panel;
  /**
   * A panel that will hold the labels.
   */
  private JPanel my_label_panel;
  /**
   * A reference to the main application frame.
   */
  private ApplicationFrame my_app_frame;
  
  private Auction my_auction;
  
  private User my_user;
  
  public InventoryPanel(final ApplicationFrame the_jframe, final Auction an_auction, User a_user) {
    super();
    
    my_auction = an_auction;
    my_item_list = an_auction.getItems();
    my_app_frame = the_jframe;
    my_button_panel = new JPanel();
    my_label_panel = new JPanel();
    my_user = a_user;
    
    configPanel();
    configLabels();
    configPanels();
    configButtons();
  }
  
  /**
  * This method configures the AuctionListPanel to its preferred size and sets its
  * layout.
  */
   public void configPanel() {
     this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
     this.setLayout(new BorderLayout());
   }
   
   /**
    * This method configures the JLabels that are positioned above the list of 
    * Auctions.
    */
   public void configLabels() {
     final GridLayout g_layout = new GridLayout();
     g_layout.setColumns(2);
     g_layout.setRows(2);
     my_label_panel.setLayout(g_layout);
     my_label_panel.add(new JLabel(""));
     my_label_panel.add(new JLabel(""));
     my_label_panel.add(new JLabel("     Inventory:"));
     my_label_panel.add(new JLabel(""));
   }
   /**
    * This method configures the JPanels to their desired location in the 
    * AuctionListPanel.
    */
   public void configPanels() {
     my_button_panel.setLayout(new FlowLayout());
     
     this.add(new ListPanel(), BorderLayout.CENTER);
     
     this.add(my_button_panel, BorderLayout.SOUTH);
     this.add(my_label_panel, BorderLayout.NORTH);
   }
   /**
    * This method creates the JButtons with their desired names and sets 
    * ActionListeners to them that will correctly do what is wanted.
    */
   public void configButtons() {
     final JButton edit_button = new JButton("Edit");
     edit_button.addActionListener(new ActionListener() {
       public void actionPerformed(final ActionEvent an_event) {
         if (my_index >= 0) {
           
           boolean editable = false;
           String user_name = my_user.getFirstName() + " ";
           user_name += my_user.getLastName();
           
           if (user_name.equals(my_auction.getContactPerson())) {
             editable = true;
           }
           
           my_app_frame.showItem(my_item_list.get(my_index), my_auction, editable);
           
         }
       }
     });
     
     final JButton create_button = new JButton("Create");
     create_button.addActionListener(new ActionListener() {
       public void actionPerformed(final ActionEvent an_event) {
           my_app_frame.showItem(new Item(), my_auction, true);
       }
     });
     my_button_panel.add(edit_button);
     my_button_panel.add(create_button);
   }
   
   /**
    * This is the specific panel that will hold the list of Auctions.
    * @author Kaiyuan Shi
    * @version 3/8/2014
    */
   private class ListPanel extends JPanel {
     /**
      * The GridBagLayout that is used for the list.
      */
     private final GridBagLayout my_layout;
     /**
      * The GridBagLayoutConstraints that is used for the layout.
      */
     private final GridBagConstraints my_layout_constraints;
     /**
      * This is the constructor for the ListPanel. It configures the 
      * ListPanel to the desired specifications.
      */
     public ListPanel() {
       
       super();
       
       my_layout = new GridBagLayout();
       my_layout_constraints = new GridBagConstraints();
       setLayout(my_layout);
       my_jlist = new JList<Item>();
       my_scrollpane = new JScrollPane();
       my_scrollpane = new JScrollPane(my_jlist, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
           JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       
       configLayout();
       
       configList();
       
       add(my_scrollpane);
       
     }
     /**
      * This method configures the constraints of the layout and sets them to the layout.
      */
     private void configLayout() {
       
       my_layout_constraints.gridx = 0;
       my_layout_constraints.gridy = 0;
       my_layout_constraints.fill = GridBagConstraints.BOTH;
       my_layout_constraints.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
       my_layout_constraints.anchor = GridBagConstraints.CENTER;
       my_layout_constraints.weightx = 1.0;
       my_layout_constraints.weighty = 1.0;
       my_layout.setConstraints(my_scrollpane, my_layout_constraints);
     }
     /**
      * This method configures the JList by adding the Auctions to it.
      */
     private void configList() {
       
       my_jlist.setListData( my_item_list.toArray());
       my_jlist.setCellRenderer(new MyCellRenderer());
     }
   }
   
   private class MyCellRenderer extends JPanel implements ListCellRenderer {
     
     
     /**
      * This is the default grid size for the list.
      */
     private static final int GRID_SIZE = 3;
     /**
      * This is the label for the auction name.
      */
     private final JLabel my_item_number;
     /**
      * This is the label for the name of the Auction Central staff member that
      * added the auction.
      */
     private final JLabel my_item_name;
     /**
      * This is the label for the date of the auction.
      */
     private final JLabel my_item_quantity;
     
     /**
      * 
      */
     MyCellRenderer() {
       my_item_number = new JLabel();
       my_item_name = new JLabel();
       my_item_quantity = new JLabel();
       configRenderer();
     }
     /**
      * 
      */
     private void configRenderer() {
       setLayout(new GridLayout(1, GRID_SIZE));
       
       my_item_number.setOpaque(true);
       my_item_name.setOpaque(true);
       my_item_quantity.setOpaque(true);
       add(my_item_number);
       add(my_item_name);
       add(my_item_quantity);
     }
     

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, 
        boolean isSelected, boolean cellHasFocus) {
      int item_number = ((Item) value).getItemNumber();
      String item_name = ((Item) value).getItemName();
      int item_quantity = ((Item) value).getItemQuantity();
      
      my_item_number.setText(Integer.valueOf(item_number).toString());
      my_item_name.setText(item_name);
      my_item_quantity.setText(Integer.valueOf(item_quantity).toString());
      
      if (isSelected) {
              
        my_item_number.setBackground(list.getSelectionBackground());
        my_item_name.setBackground(list.getSelectionBackground());
        my_item_quantity.setBackground(list.getSelectionBackground());
        
        my_item_number.setForeground(list.getSelectionForeground());
        my_item_name.setForeground(list.getSelectionForeground());
        my_item_quantity.setForeground(list.getSelectionForeground());
        
        my_index = index;
        
      } else {
        
        my_item_number.setBackground(list.getBackground());
        my_item_number.setForeground(list.getForeground());
        my_item_name.setBackground(list.getBackground());
        my_item_name.setForeground(list.getForeground());
        my_item_quantity.setBackground(list.getBackground());
        my_item_quantity.setForeground(list.getForeground());
        
      }
            
      setEnabled(list.isEnabled());
      setFont(list.getFont());
      
      return this;

    }
     
   }


}
