package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.apache.http.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stomt4j.entities.Stomt;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class CreateStomt {
	
	private StomtClient client;
	private String random;
	private String expected;
	private boolean positive;
	private String text;
	private URL url;
	private Stomt stomtObject;
	private String stomt;

	@Before
	public void setUp() throws ParseException, IOException, StomtException {
		// resset
		client = null;
		expected = null;
		stomt = null;
		positive = false;
		text = null;
		url = null;
		random = null;
		stomtObject = null;

		// create client and login
		client = new StomtClient(StomtClientTest.appid);
		client.login(StomtClientTest.usernamePassword,
				StomtClientTest.usernamePassword);
	}

	@After
	public void tearDown() throws ParseException, IOException, StomtException {
		// logout
		client.logout();
	}
		
		@Test
		public void createStomtNegative() throws ParseException, IOException, StomtException {
			System.out.println("-> TEST: createStomtNegative()");
			
			random = getRandomString();
			positive = false;
			text = StomtClientTest.textMessage + random;
					
			
			stomtObject = client.createStomt(positive, StomtClientTest.target_id, text);
			stomt = stomtObject.toString();
			
			expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ","
					+ " images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, "
					+ "labels=, agreements=, anonym=false, target=Target [id=stomt-java, displayname=stomt-java, "
					+ "category=Category [id=targets, displayname=Targets], images=" + stomtObject.getTarget().getImages() 
					+ ", verified=false], highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=AgreedEntity [positive=true]]";

			System.out.println("Expect: " + expected);
			System.out.println("Get: " + stomt);
			
			assertEquals(expected, stomt);
		}


		@Test
		public void createStomtPositive() throws ParseException, IOException, StomtException {
			System.out.println("-> TEST: createStomtPositive()");
			
			random = getRandomString();
			positive = true;
			text = StomtClientTest.textMessage + random;
					

			stomtObject = client.createStomt(positive, StomtClientTest.target_id, text);
			stomt = stomtObject.toString();
			
			expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ","
					+ " images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, "
					+ "labels=, agreements=, anonym=false, target=Target [id=stomt-java, displayname=stomt-java, "
					+ "category=Category [id=targets, displayname=Targets], images=" + stomtObject.getTarget().getImages() 
					+ ", verified=false], highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=AgreedEntity [positive=true]]";
			
			System.out.println("Expect: " + expected);
			System.out.println("Get: " + stomt);
			
			assertEquals(expected, stomt);
		}
		
		@Test
		public void createStomtUrl() throws ParseException, IOException, StomtException {
			System.out.println("-> TEST: createStomtUrl()");
			
			random = getRandomString();
			text = StomtClientTest.textMessage + random;
			url = new URL("http://www.stomt.com");
					
			stomtObject = client.createStomt(positive, StomtClientTest.target_id, text, url);
			stomt = stomtObject.toString();
			
			expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ","
					+ " images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, "
					+ "labels=, agreements=, anonym=false, target=Target [id=stomt-java, displayname=stomt-java, "
					+ "category=Category [id=targets, displayname=Targets], images=" + stomtObject.getTarget().getImages() 
					+ ", verified=false], highlights=, creator=" + stomtObject.getCreator() + ", url=" + stomtObject.getUrl() + ", agreed=AgreedEntity [positive=true]]";
			
			System.out.println("Expect: " + expected);
			System.out.println("Get: " + stomt);
			
			assertEquals(expected, stomt);
		}
		
		
		
		// Required parameter target_id is null
		@Test(expected=StomtException.class)
		public void createStomtNoTarget() throws ParseException, IOException, StomtException {
			System.out.println("-> TEST: createStomtNoTarget() - StomtException.");

			url = new URL("http://www.stomt.com");
					
			System.out.println("Expect: Target ID is required!");
			System.out.print("Get: ");
			
			client.createStomt(positive, null, StomtClientTest.textMessage, url);
		}
		
		// Required parameter text is null
		@Test(expected=StomtException.class)
		public void createStomtNoText() throws ParseException, IOException, StomtException {
			System.out.println("-> TEST: createStomtNoText() - StomtException.");
			
			url = new URL("http://www.stomt.com");
					
			System.out.println("Expect: Text is required!");
			System.out.print("Get: ");
			
			client.createStomt(positive, StomtClientTest.target_id, null, url);
		}
		
		private String getRandomString() {
			double random = Math.random() * 100000;
			return Integer.toString((int) random);
		}

}