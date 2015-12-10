package stomt4j;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class LoginTest {
		
	@Test
	public void loginAccept() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: loginAccept() - accept.");
		
		String username;
		String target;
		String expected = "Target [id=test, displayname=test, category=Category [id=users, displayname=Users], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/VFKB/s42x42/VFKBSlYCbwgouk7I5eP0aBtjcjUISAO9FnhSDuTE_s42x42.png, width=42, height=42], stomt=, profile=], verified=false]";
		
		System.out.println("Expect: " + expected);
		
		target = StomtClientTest.client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword).toString();

		System.out.println("Get: " + target);
		
		assertEquals(expected, target);
	}

	// TODO: Rewrite test case - user get banned for to many wrong logins.
//	@Test(expected = StomtException.class)
//	public void loginFailWrongPassword() throws ParseException, IOException, StomtException {
//		System.out.println("->TEST: loginFailWrongPassword() - fail: Wrong Password.");
//		
//		String username;
//		String target;
//		String expected = "Status = 403 - Message = Wrong password.";
//    
//		System.out.println("Expect: " + expected);
//		
//		target = StomtClientTest.client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword + "123").toString();
//		System.out.println("Get: " + target);
//
//		assertEquals(expected, target);
//	}	
}
