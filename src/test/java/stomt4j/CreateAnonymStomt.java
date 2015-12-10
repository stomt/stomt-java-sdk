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
public class CreateAnonymStomt {

	private String expected;
	private String stomt;
	private boolean positive;
	private String target_id;
	private String text;
	private URL url;
	
	@Test
	public void createAnonymStomtMinimalFalse() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: Create minimal i-wish-Stomt - accept.");
		
		String random = getRandomString();
		
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
				

		
		Stomt stomtObject = StomtClientTest.client.createAnonymStomt(positive, target_id, text);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, target=Target [id=stomt-java, displayname=Stomt Java, category=Category [id=software, displayname=Software], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/YQhY/origin/YQhYFmNBsYfTjvZFg6DskobhPxBiacNfek1RNPom_origin.png, width=42, height=42], stomt=, profile=], verified=false], highlights=, creator=, url=null, agreed=]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: Create minimal i-like-Stomt - accept.");
		
		String random = getRandomString();
		
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
				

		
		Stomt stomtObject = StomtClientTest.client.createAnonymStomt(positive, target_id, text);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, target=Target [id=stomt-java, displayname=Stomt Java, category=Category [id=software, displayname=Software], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/YQhY/origin/YQhYFmNBsYfTjvZFg6DskobhPxBiacNfek1RNPom_origin.png, width=42, height=42], stomt=, profile=], verified=false], highlights=, creator=, url=null, agreed=]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtWithUrl() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: Create Stomt with url - accept.");
		
		String random = getRandomString();
		
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		url = new URL("http://www.stomt.com");
				

		
		Stomt stomtObject = StomtClientTest.client.createAnonymStomt(positive, target_id, text, url);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, target=Target [id=stomt-java, displayname=Stomt Java, category=Category [id=software, displayname=Software], images=Images [avatar=Image [url=https://test.cdn.stomt.com/uploads/YQhY/origin/YQhYFmNBsYfTjvZFg6DskobhPxBiacNfek1RNPom_origin.png, width=42, height=42], stomt=, profile=], verified=false], highlights=, creator=, url=" + url + ", agreed=]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	

//	@Test(expected=StomtException.class)
//	public void createAnonymStomtWithWrongUrl() throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: Create Stomt with wrong url - StomtException.");
//		
//		String random = getRandomString();
//		
//		positive = false;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		url = new URL("www.stomt.com");
//				
//		System.out.println("Expect: No valid URL!");
//		System.out.print("Get: ");
//		
//		StomtClientTest.client.createAnonymStomt(positive, target_id, text, url);
//	}
	
	private String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}

}
