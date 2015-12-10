package stomt4j;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
@RunWith(Suite.class)
@SuiteClasses({ LoginTest.class, LogoutTest.class, CheckAvailabilityTest.class, ForgotPasswordTest.class, CreateAnonymStomt.class, CreateStomtTest.class })
public class StomtClientTest {
	
	// Set stomt application id
	public static final String appid = "stzmtmrEU3Kp42XJbX2eYUsYE";
	
	// This StomtClient is used for methods where login is not required.
	public static StomtClient client = new StomtClient(appid);
	
	// Login name and password for tests
	public static final String usernamePassword = "test";
	
}