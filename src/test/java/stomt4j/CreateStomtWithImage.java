package stomt4j;

import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Stomt;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class CreateStomtWithImage {

	private String random;
	private String expected;
	private String stomt;
	private boolean positive;
	private String target_id;
	private String text;
	private URL url;
	private File img;
	private URL imgUrl;
	private StomtClient client;

	@Test
	public void createStomtMinimalFalse_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalFalse_File()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", img);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createStomtMinimalPositive_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositive_File()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", img);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createStomtWithUrl_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtWithUrl_File()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);
		url = new URL("http://stomt.com");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, url, "stomt", img);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=" + stomtObject.getUrl() + ", agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createStomtMinimalPositive_Url()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositive_Url()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL(StomtClientTest.sample_image_url);

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", imgUrl);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtMinimalPositiveWithUrl_Url()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositiveWithUrl_Url()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL(StomtClientTest.sample_image_url);
		url = new URL("http://stomt.com");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, url, "stomt", imgUrl);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=" + stomtObject.getUrl() + ", agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtMinimalPositive_Base64()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositive_Base64()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", StomtClientTest.fileToBase64(img));
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}
	
	@Test
	public void createStomtWithUrl_Base64()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtWithUrl_Base64()");

		client = new StomtClient(StomtClientTest.appid);
		random = StomtClientTest.getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = StomtClientTest.generateFile(random);
		url = new URL("http://stomt.com");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, url, "stomt", StomtClientTest.fileToBase64(img));
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=" + stomtObject.getId() + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=" + stomtObject.getUrl() + ", agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
		StomtClientTest.deleteFile(img);
	}

}
