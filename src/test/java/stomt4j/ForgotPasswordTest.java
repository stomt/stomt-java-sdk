package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Before;
import org.junit.Test;

public class ForgotPasswordTest {
	
	String usernameOrEmail;
	boolean expected;
	boolean target;
	

	@Before
	public void setUp() throws Exception {
		reset();
	}

	@Test
	public void forgotPasswordValid() throws ParseException, IOException, StomtException {
		System.out.println("Forgot Password - Valid Request.");
		usernameOrEmail = "test";
		expected = true;
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.forgotPassword(usernameOrEmail);
		System.out.println("Get: " + target);
		
		assertTrue(expected == target);
		reset();
	}
	
	@Test(expected=StomtException.class)
	public void forgotPasswordUnvalid() throws ParseException, IOException, StomtException {
		System.out.println("Forgot Password - Username does not exist.");
		usernameOrEmail = "test123456789123456789";
		String expected = "Status = 401 - Message = Username does not exist.";
		
		System.out.println("Expect: " + expected);
		
		String target = Boolean.toString(StomtClientTest.client.forgotPassword(usernameOrEmail));
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
		reset();
	}
	
	private void reset() {
		usernameOrEmail = null;
		expected = false;
		target = false;
	}

}
