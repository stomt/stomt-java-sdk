package stomt4j;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LoginTest.class, RegisterAnUserTest.class, CheckAvailabilityTest.class, ForgotPasswordTest.class })
public class StomtClientTest {
	
	// Set stomt application id
	public static String appid = "stzmtmrEU3Kp42XJbX2eYUsYE";
	public static StomtClient client = new StomtClient(appid);
	
}