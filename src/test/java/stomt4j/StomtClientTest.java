package stomt4j;

import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;

public class StomtClientTest {
	
	// Set stomt application id
	String appid = "stzmtmrEU3Kp42XJbX2eYUsYE";

	// Create StomtClient
	StomtClient client = new StomtClient(appid);

	@Test
	public void registerAnUserTest() {
		double random = Math.random() * 100000;
		String randomAsString = Integer.toString((int) random);

		String username = "test" + randomAsString;
		String email = username + "@iwas.de";
		String target = null;
		String expectTarget = "Target [id=" + username + ", images=null, ownedTargets=null, email=null, name=null, lang=null, influencestatus=null, roles=null, category=Category [id=users, displayname=Users], amIFollowing=false, details=null, type=null, stomts=null, stats=null, displayname=" + username +", follows=null, follower=null, influencelevel=0, memberSince=null]";
 		System.out.println(expectTarget);
		System.out.println("Register an user:");

		try {
			target = client.registerAnUser(username, email, username, username).toString();
			System.out.println(target);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (StomtException e) {
			e.printStackTrace();
		}
		
		// System.out.println(client.registerAnUser("test", "test", "test", "test"));
		
		if (!target.equals(expectTarget)) {
			fail("Error: Unexpected Target variables!");
		}
		
		System.out.println("Register an User: Checked!" + target);
	}

}
