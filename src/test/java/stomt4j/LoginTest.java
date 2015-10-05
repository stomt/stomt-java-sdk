package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
	
	String username;
	String target;
	String expected;
	
	// Evtl. BeforeClass
	@Before
	public void setUp() throws Exception {
		reset();
	}

	@Test
	public void loginAccept() throws ParseException, IOException, StomtException {
		System.out.println("Login - accept.");
		
		username = "test";
		expected = "Target [id=test, images=null, ownedTargets=null, email=null, name=null, lang=null, influencestatus=null, roles=null, category=Category [id=users, displayname=Users], amIFollowing=false, details=null, type=null, stomts=null, stats=null, displayname=test, follows=null, follower=null, influencelevel=0, memberSince=null]";
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.login(username, username).toString();
		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
		reset();
	}

	@Test(expected = StomtException.class)
	public void loginFailWrongPassword() throws ParseException, IOException, StomtException {
		System.out.println("Login - fail: Wrong Password.");
		
		username = "test";
		expected = "Status = 403 - Message = Wrong password.";
    
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.login(username, username + "123").toString();
		System.out.println("Get: " + target);

		assertEquals(expected, target);
		reset();
	}	

	private void reset() {
		username = null;
		target = null;
		expected = null;
	}
}
