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
		System.out.println("-> TEST: createAnonymStomtMinimalFalse()");
		
		String random = getRandomString();
		
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
				

		
		Stomt stomtObject = StomtClientTest.client.createAnonymStomt(positive, target_id, text);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +
				", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, target=Target [id=stomt-java, displayname=stomt-java, "
				+ "category=Category [id=targets, displayname=Targets],"
				+ " images=" + stomtObject.getTarget().getImages()  + ", verified=false], highlights=, creator=, url=, agreed=]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositive()");
		
		String random = getRandomString();
		
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
				

		
		Stomt stomtObject = StomtClientTest.client.createAnonymStomt(positive, target_id, text);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +
				", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, target=Target [id=stomt-java, displayname=stomt-java, "
				+ "category=Category [id=targets, displayname=Targets],"
				+ " images=" + stomtObject.getTarget().getImages()  + ", verified=false], highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtWithUrl() throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtWithUrl()");
		
		String random = getRandomString();
		
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		url = new URL("http://www.stomt.com");
				

		
		Stomt stomtObject = StomtClientTest.client.createAnonymStomt(positive, target_id, text, url);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " +  random + ", images=, lang=en, created_at=" + stomtObject.getCreated_at() +
				", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, target=Target [id=stomt-java, displayname=stomt-java, "
				+ "category=Category [id=targets, displayname=Targets],"
				+ " images=" + stomtObject.getTarget().getImages()  + ", verified=false], highlights=, creator=, url=" + url + ", agreed=]";

		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);
		
		assertEquals(expected, stomt);
	}

	private String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}

}
