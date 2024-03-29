package user;

/**
 * Represents a staff member.
 * 
 * @author Josh Hammer
 * 
 * @version 3/2/2014
 */
public class AuctionCentralStaff extends AbstractUser {

  /**
   * Initializes the auctioncentralstaff.
   * 
   * @param the_username
   *          the intended username
   * @param the_password
   *          the intended password
   * @param the_first_name
   *          the intended first name
   * @param the_last_name
   *          the intended last name
   */
  public AuctionCentralStaff(final String the_username, final String the_password,
      final String the_first_name, final String the_last_name) {
    super(the_username, the_password, the_first_name, the_last_name);
  }
}
