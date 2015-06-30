package stomt4j;

/*
 * To do:
 * 
 * 	- Nur von Stomt genutzte StatusCodes implementieren
 */

/**
 * @author Christoph Weidemeyer - christoph.weidemeyer at gmx.de
 */
public interface HttpVariables {
	
	  public static final String root = "https://test.rest.stomt.com";
	  
	  public static final String authentication = "/authentication";
	  public static final String checkAvailability = "/checkAvailability";
	  public static final String forgotPassword = "/forgotpassword";
	  public static final String resetPassword = "/resetpassword";
	  public static final String login = "/session";
	  public static final String check = "/check";
	  public static final String refresh = "/refresh";
	  public static final String contentType = "application/json";

}