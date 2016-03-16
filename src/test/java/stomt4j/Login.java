package stomt4j;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;

import stomt4j.entities.Target;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Login {
		
	@Test
	public void loginAccept() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: loginAccept() - accept.");
		
		Target target;
		String expected;
		
		target = StomtClientTest.client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		expected = "Target [id=" + StomtClientTest.usernamePassword + ", displayname=" + StomtClientTest.usernamePassword + ", category=Category [id=users, displayname=Users], images="
				+ "Images [avatar=" + target.getImages().getAvatar() + ", stomt=, profile=" + target.getImages().getProfile() + "], verified=false]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + target.toString());
		
		assertEquals(expected, target.toString());
	}

	@Test(expected = StomtException.class)
	public void loginFailWrongPassword() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: loginFailWrongPassword() - fail: Wrong Password.");
		
		String target;
		String expected = "\"Wrong password.\"";
    
		System.out.println("Expect: " + expected);
		System.out.print("Get: ");

		target = StomtClientTest.client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword + "123").toString();

		assertEquals(expected, target);
	}	
}
