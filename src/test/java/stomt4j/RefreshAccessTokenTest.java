package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.ParseException;
import org.junit.Test;

public class RefreshAccessTokenTest {

	public StomtClient stomtClient;

	@Test
	public void refreshAccessTokenAccept() throws ParseException, IOException, StomtException {
		System.out.println("RefreshAccesstoken - Accept.");
		
//		stomtClient = new StomtClient(StomtClientTest.appid);
//		stomtClient.login("test", "test");
//		
//		boolean expected = true;
//	
//		System.out.println("Expect: " + expected);
//		
//		boolean target = stomtClient.logout();
//		System.out.println("Get: " + target);
//		
//		assertEquals(expected, target);
	}

}
