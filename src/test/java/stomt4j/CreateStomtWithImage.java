package stomt4j;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.ParseException;
import org.junit.Test;

import stomt4j.entities.Stomt;

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
		random = getRandomString();
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", img);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtMinimalPositive_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositive_File()");

		client = new StomtClient(StomtClientTest.appid);
		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", img);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtWithUrl_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtWithUrl_File()");

		client = new StomtClient(StomtClientTest.appid);
		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");
		url = new URL("http://stomt.com");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, url, "stomt", img);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=" + stomtObject.getUrl() + ", agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtMinimalPositive_Url()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtMinimalPositive_Url()");

		client = new StomtClient(StomtClientTest.appid);
		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", imgUrl);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
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
		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");
		url = new URL("http://stomt.com");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, url, "stomt", imgUrl);
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
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
		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/string.png");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, "stomt", FileToBase64(img));
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=, agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createStomtWithUrl_Base64()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createStomtWithUrl_Base64()");

		client = new StomtClient(StomtClientTest.appid);
		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/string.png");
		url = new URL("http://stomt.com");

		client.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Stomt stomtObject = client.createStomtWithImage(
				positive, target_id, text, url, "stomt", FileToBase64(img));
		
		client.logout();
		
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=" + stomtObject.getCreator() + ", url=" + stomtObject.getUrl() + ", agreed=" + stomtObject.getAgreed() + "]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}


	private String FileToBase64(File data) throws StomtException {
		String imageDataString = null;
		
        try {
        	// Reading an Image file from file system
            FileInputStream fileInputStreamReader = new FileInputStream(data);
            byte[] bytes = new byte[(int)data.length()];
            fileInputStreamReader.read(bytes);
            // Converting Image byte array into Base64 String
            imageDataString = new String(Base64.encodeBase64(bytes));
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
           throw new StomtException(e.toString());
        } catch (IOException e) {
            throw new StomtException(e.toString());
        }
        return imageDataString;
	}

	private String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}

}