package stomt4j;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * TestSuite for Stomt Java SDK.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
@RunWith(Suite.class)
@SuiteClasses({ Login.class, Logout.class, CheckAvailability.class, ForgotPassword.class, CreateAnonymStomt.class,
	CreateAnonymStomtWithImage.class, CreateStomt.class, CreateStomtWithImage.class, UploadImage.class,
	UploadImageAsFile.class, UploadImageViaUrl.class, SuggestUsernames.class })
public class StomtClientTest {
	
	// Set stomt application id
	public static final String appid = "stzmtmrEU3Kp42XJbX2eYUsYE";
	
	// This StomtClient is used for methods where login is not required.
	public static StomtClient client = new StomtClient(appid);
	
	// Login name and password for tests
	public static final String usernamePassword = "test";
	
	// The test target - all stomts are directed to 
	public static final String target_id = "stomt-java";
	
	// The präfix for the required parameter text
	public static final String textMessage = "Java-SDK test ";
	
}