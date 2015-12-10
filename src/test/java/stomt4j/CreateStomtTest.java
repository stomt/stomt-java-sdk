package stomt4j;

import static org.junit.Assert.*;
import java.io.IOException;
import java.net.URL;

import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Stomt;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class CreateStomtTest {
		
	@Test
	public void createStomtMinimalFalse() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalFalse() - accept (I wish).");
		
		String random = getRandomString();
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
		boolean positive = false;
		String target_id = "stomt-java";
		String text = "Java-SDK test " + random;
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = stomtClient.createStomt(positive, target_id, text);
		String stomt = stomtObject.toString();
		
		stomtClient.logout();
		
		String expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, target=Target [id=stomt-java, displayname=Stomt Java, category=Category [id=software, displayname=Software], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/YQhY/origin/YQhYFmNBsYfTjvZFg6DskobhPxBiacNfek1RNPom_origin.png, width=42, height=42], stomt=, profile=], verified=false], highlights=, creator=" + stomtObject.getCreator().toString() +", url=null, agreed=" + stomtObject.getAgreed().toString() + "]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtMinimalPositive() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositive() - accept (I like).");
		
		String random = getRandomString();
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
		boolean positive = true;
		String target_id = "stomt-java";
		String text = "Java-SDK test " + random;
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		Stomt stomtObject = stomtClient.createStomt(positive, target_id, text);
		String stomt = stomtObject.toString();
		
		stomtClient.logout();
		
		String expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, target=Target [id=stomt-java, displayname=Stomt Java, category=Category [id=software, displayname=Software], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/YQhY/origin/YQhYFmNBsYfTjvZFg6DskobhPxBiacNfek1RNPom_origin.png, width=42, height=42], stomt=, profile=], verified=false], highlights=, creator=" + stomtObject.getCreator().toString() +", url=null, agreed=" + stomtObject.getAgreed().toString() + "]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtWithUrl() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtWithUrl() - accept.");
		
		String random = getRandomString();
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
		boolean positive = false;
		String target_id = "stomt-java";
		String text = "Java-SDK test " + random;
		URL url = new URL("http://www.stomt.com");
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		Stomt stomtObject = stomtClient.createStomt(positive, target_id, text, url);
		String stomt = stomtObject.toString();
		
		stomtClient.logout();
		
		String expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, target=Target [id=stomt-java, displayname=Stomt Java, category=Category [id=software, displayname=Software], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/YQhY/origin/YQhYFmNBsYfTjvZFg6DskobhPxBiacNfek1RNPom_origin.png, width=42, height=42], stomt=, profile=], verified=false], highlights=, creator=" + stomtObject.getCreator().toString() +", url=" + stomtObject.getUrl() + ", agreed=" + stomtObject.getAgreed().toString() + "]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}

//	@Test(expected=StomtException.class)
//	public void createStomtWithWrongUrl() throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createStomtWithWrongUrl() - StomtException");
//		
//		String random = getRandomString();
//
//		boolean positive = false;
//		String target_id = "stomt-java";
//		String text = "Java-SDK test " + random;
//		String url = "stomt";
//				
//		System.out.println("Expect: No valid URL!");
//		System.out.print("Get: ");
//		
//		StomtClientTest.client.createStomt(positive, target_id, text, url);
//	}

	
	private String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}


}
