package stomt4j;

import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Stomt;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class CreateAnonymStomtWithImage {

	private String random = null;
	private String expected = null;
	private String stomt = null;
	private boolean positive = false;
	private String target_id = null;
	private String text = null;
	private URL url = null;
	private File img = null;
	private URL imgUrl = null;
	private StomtClient client = new StomtClient(StomtClientTest.appid);

	@Test
	public void createAnonymStomtMinimalFalse_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalFalse_File()");

		random = StomtClientTest.getRandomString();
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);

		Stomt stomtObject = client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositive_File()");

		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);

		Stomt stomtObject = client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createAnonymStomtWithUrl_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtWithUrl_File()");
		

		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);
		url = new URL("http://stomt.com");
		
		Stomt stomtObject = client.createAnonymStomtWithImage(
				positive, target_id, text, url, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive_Url()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositive_Url()");

		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL("http://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");

		Stomt stomtObject = client.createAnonymStomtWithImage(positive, target_id, text, "stomt", imgUrl);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtMinimalPositiveWithUrl_Url()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositiveWithUrl_Url()");

		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL("http://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");
		url = new URL("http://stomt.com");


		Stomt stomtObject = client.createAnonymStomtWithImage(positive, target_id, text, url, "stomt", imgUrl);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive_Base64()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositive_Base64()");

		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);

		Stomt stomtObject = client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", StomtClientTest.fileToBase64(img));
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createAnonymStomtWithUrl_Base64()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtWithUrl_Base64()");

		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		url = new URL("http://stomt.com");
		img = StomtClientTest.generateFile(random);

		Stomt stomtObject = client.createAnonymStomtWithImage(positive, target_id, text, url, "stomt", StomtClientTest.fileToBase64(img));
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}

}
