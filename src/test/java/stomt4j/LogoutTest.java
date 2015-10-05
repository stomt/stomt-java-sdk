package stomt4j;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;

public class LogoutTest {

	public StomtClient stomtClient;

	
	// TODO: Wann wird hier false zurückgegeben ?????
	
	
	@Test
	public void logoutAccept() throws ParseException, IOException, StomtException {
		System.out.println("Logout - Accept.");
		
		stomtClient = new StomtClient(StomtClientTest.appid);
		stomtClient.login("test", "test");
		
		boolean expected = true;
	
		System.out.println("Expect: " + expected);
		
		boolean target = stomtClient.logout();
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
	}
	
	// TODO: Erwarte 400er bekomme aber 403er - Postman gibts richtig aus
	
	@Test(expected=StomtException.class)
	public void logoutIllegal() throws ParseException, IOException, StomtException {
		System.out.println("Logout - Fail: Bad Request - No Accesstoken");
		
		stomtClient = new StomtClient(StomtClientTest.appid);

		String expected = "Status = 400 - Message = Bad request: accesstoken does not exist.";

		System.out.println("Expect: " + expected);
		
		String target = Boolean.toString(stomtClient.logout());
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);		
	}
	
	@Test(expected=StomtException.class)
	public void logoutInvalidAccesstoken() throws ParseException, IOException, StomtException {
		System.out.println("Logout - Fail: Invalid Accesstoken");
		
		stomtClient = new StomtClient(StomtClientTest.appid);
		stomtClient.getAuthorization().setAccesstoken("32r3253253253453");
		

		String expected = "Status = 403 - Message = Forbidden: Invalid access token.";

		System.out.println("Expect: " + expected);
		
		String target = Boolean.toString(stomtClient.logout());
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
	}

}
