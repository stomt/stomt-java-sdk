package stomt4j;

// TODO: Implement Stomt Statuscodes

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public interface HttpVariables {
	
	  public static final String root = "https://test.rest.stomt.com";
	  
	  public static final String authentication = "/authentication";
	  public static final String checkAvailability = "/checkAvailability";
	  public static final String suggestedUsernames = "/suggestedUsernames";
	  public static final String forgotPassword = "/forgotpassword";
	  public static final String resetPassword = "/resetpassword";
	  public static final String login = "/session";
	  public static final String check = "/check";
	  public static final String refresh = "/refresh";
	  public static final String stomts = "/stomts";
	  public static final String feeds = "/feeds/";
	  public static final String contentType = "application/json";
	  public static final String images = "/images";
}