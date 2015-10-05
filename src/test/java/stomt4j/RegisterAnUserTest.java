package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Before;
import org.junit.Test;

public class RegisterAnUserTest {
	
	String randomAsString;
	String username;
	String email;
	String target;
	String expected;
	
	
	@Before
	public void setUp() throws Exception {
		double random = Math.random() * 100000;
		randomAsString = Integer.toString((int) random);
		reset();
	}

	@Test
	public void registerAnUserAccept() throws ParseException, IOException, StomtException {
		System.out.println("Register an user - accept.");

		username = "test" + randomAsString;
		email = username + "@iwas.de";
		expected = "Target [id=" + username
				+ ", images=null, ownedTargets=null, email=null, name=null, lang=null, influencestatus=null, roles=null, category=Category [id=users, displayname=Users], amIFollowing=false, details=null, type=null, stomts=null, stats=null, displayname="
				+ username + ", follows=null, follower=null, influencelevel=0, memberSince=null]";

		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.registerAnUser(username, email, username, username).toString();
		
		System.out.println("Get: " + target);

		// try {
		// target = client.registerAnUser(username, email, username,
		// username).toString();
		// System.out.println(target);
		// } catch (ParseException e) {
		// System.out.print("ParseException: ");
		// e.printStackTrace();
		// } catch (IOException e) {
		// System.out.print("IOException: ");
		// e.printStackTrace();
		// } catch (StomtException e) {
		// System.out.print("StomtException: ");
		// e.printStackTrace();
		// }
		assertEquals(expected, target);
		reset();
	}

	// Username already exists
	@Test(expected = StomtException.class)
	public void registerAnUserFailExist() throws ParseException, IOException, StomtException {
		System.out.println("Register an user - fail: Username already exists.");

		username = "test";
		email = username + "@iwas.de";
		expected = "Status = 409 - Message = Username already exists.";

		System.out.println("Expect: " + expected);

		target = StomtClientTest.client.registerAnUser(username, email, username, username).toString();
		System.out.println("Get: " + target);

		assertEquals(expected, target);
		reset();
	}

	// Wrong parameters
	@Test(expected = StomtException.class)
	public void registerAnUserFailIllegal() throws ParseException, IOException, StomtException {
		System.out.println("Register an user - fail: Bad Request.");

		email = username + "@iwas.de";
		expected = "Status = 400 - Message = Bad request: displayname does not exist.";

		System.out.println("Expect: " + expected);

		target = StomtClientTest.client.registerAnUser(username, email, username, username).toString();
		System.out.println("Get: " + target);

		assertEquals(expected, target);
		reset();
	}
	
	private void reset() {
		username = null;
		email = null;
		target = null;
		expected = null;
	}

}
