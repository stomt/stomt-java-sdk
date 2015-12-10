package stomt4j;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class ForgotPasswordTest {
	
	@Test
	public void forgotPasswordValid() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: forgotPasswordValid() - Valid Request.");
		
		String usernameOrEmail = "test";
		boolean expected = true;
		
		System.out.println("Expect: " + expected);
		
		boolean target = StomtClientTest.client.forgotPassword(usernameOrEmail);
		
		System.out.println("Get: " + target);
		
		assertTrue(expected == target);
	}
	
	@Test(expected=StomtException.class)
	public void forgotPasswordUnvalid() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: forgotPasswordUnvalid() - Username does not exist.");
		
		String usernameOrEmail = "test123456789123456789";
		
		System.out.println("Expect: Status = 401 - Message = Username does not exist.");
		System.out.print("Get: ");

		StomtClientTest.client.forgotPassword(usernameOrEmail);
	}

}
