package stomt4j;

import org.apache.http.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Logout {

	@Test
	public void logoutAccept() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: logoutAccept() - Accept.");
	
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);
		boolean expected = true;
		boolean target = false;
		
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		target = stomtClient.logout();
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
	}
	
	@Test(expected=StomtException.class)
	public void logoutIllegal() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: logoutIllegal() - Fail: Bad Request - No Accesstoken");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);
		
		System.out.println("Expect: \"User is not logged in - no accesstoken.\"");
		System.out.print("Get: ");

		stomtClient.logout();
	}
	
	@Test(expected=StomtException.class)
	public void logoutSessionInvalid() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: logoutSessionInvalid() - Fail: Invalid Session.");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);
		stomtClient.getAuthorization().setAccesstoken("xAuLF8P2e0qrYSqDdIIByEpWvePpTNBlvCpuWJmO");

		System.out.println("Expect: \"Forbidden: Session invalid. Request a new access-token via login or refresh token.\"");
		System.out.print("Get: ");

		stomtClient.logout();
	}

	@Test(expected=StomtException.class)
	public void logoutInvalidFormat() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: logoutInvalidFormat() - Fail: Invalid accesstoken.");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);
		stomtClient.getAuthorization().setAccesstoken("1234123123213");

		System.out.println("Expect: \"Forbidden: Invalid access token.\"");
		System.out.print("Get: ");

		stomtClient.logout();
	}
}
