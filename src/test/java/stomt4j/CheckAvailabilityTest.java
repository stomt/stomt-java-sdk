package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Before;
import org.junit.Test;

public class CheckAvailabilityTest {

	String property;
	String value;
	boolean expected;
	boolean target;
	String randomAsString;
	
	@Before
	public void setUp() throws Exception {
		double random = Math.random() * 100000;
		randomAsString = Integer.toString((int) random);
		reset();
	}

	@Test
	public void checkAvailabilityAvailable() throws ParseException, IOException, StomtException {
		System.out.println("Check Availability - available.");
		
		property = "username";
		value = "test" + randomAsString;
		expected = true;
		target = false;
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.checkAvailability(property, value);
		System.out.println("Get: " + target);
		
		assertTrue(expected == target);
		reset();
	}
	
	@Test
	public void checkAvailabilityUnavailable() throws ParseException, IOException, StomtException {
		System.out.println("Check Availability - unavailable.");
		
		property = "username";
		value = "test";
		expected = false;
		target = true;
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.checkAvailability(property, value);
		System.out.println("Get: " + target);
		
		assertTrue(expected == target);
		reset();
	}
	
	@Test(expected=StomtException.class)
	public void checkAvailabilityIllegal() throws ParseException, IOException, StomtException {
		System.out.println("Check Availability - Bad Request");
		
		property = "usernam";
		value = "test";
		String expected = "Status = 400 - Message = Test not available.";

		System.out.println("Expect: " + expected);
		
		String target = Boolean.toString(StomtClientTest.client.checkAvailability(property, value));
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
		reset();
	}
	
	private void reset() {
		property = null;
		value = null;
	}

}
