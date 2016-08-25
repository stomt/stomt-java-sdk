package stomt4j;

import org.apache.http.ParseException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DeleteStomt {

	private StomtClient client = null;

	@Test
	public void deleteStomt_true() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: deleteStomt_true()");
		// create client and login
		client = new StomtClient(StomtClientTest.appid);
		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		// create sample stomt
		String random = getRandomString();
		client.createStomt(true, StomtClientTest.target_id, StomtClientTest.textMessage + random);
		
		String stomt_id = "java-sdk-test-" + random;
		boolean response = client.deleteStomt(stomt_id);
		
		System.out.println("Expect: true");
		System.out.println("Get: " + response);
		
		assertEquals(response, true);
	}

	@Test(expected=StomtException.class)
	public void deleteStomt_logout() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: deleteStomt_logout()");
		
		System.out.println("Expect: Forbidden Dude! This stomt is not yours!");
		System.out.print("Get: ");
		
		StomtClientTest.client.deleteStomt(StomtClientTest.stomt_id);
	}
	
	@Test(expected=StomtException.class)
	public void deleteStomt_notOwner() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: deleteStomt_notOwner()");
		
		// create client and login
		client = new StomtClient(StomtClientTest.appid);
		client.login("test1","test1");
				
		System.out.println("Expect: Forbidden Dude! This stomt is not yours!");
		System.out.print("Get: ");
		
		client.deleteStomt(StomtClientTest.stomt_id);
	}

	private String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}

}
