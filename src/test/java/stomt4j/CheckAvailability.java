package stomt4j;

import org.apache.http.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class CheckAvailability {

	private static String randomAsString;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		double random = Math.random() * 100000;
		randomAsString = Integer.toString((int) random);
	}

	@Test
	public void checkAvailabilityAvailable() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: checkAvailabilityAvailable() - available.");
		
		String property = "username";
		String value = "test" + randomAsString;
		boolean expected = true;
		boolean target = false;
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.checkAvailability(property, value);
		System.out.println("Get: " + target);
		
		assertTrue(expected == target);
	}
	
	@Test
	public void checkAvailabilityUnavailable() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: checkAvailabilityUnavailable() - test not available.");
		
		String property = "username";
		String value = "test";
		boolean expected = false;
		boolean target = true;
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.checkAvailability(property, value);
		System.out.println("Get: " + target);
		
		assertTrue(expected == target);
	}
	
	@Test(expected=StomtException.class)
	public void checkAvailabilityIllegal() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: checkAvailabilityIllegal() - Bad Request");
		
		// Wrong property -> Bad request.
		String property = "usernam";
		String value = "test";
		String expected = "\"Test not available.\"";

		System.out.println("Expect: " + expected);
		System.out.print("Get: ");

		String target = Boolean.toString(StomtClientTest.client.checkAvailability(property, value));
		
		assertEquals(expected, target);
	}
}
