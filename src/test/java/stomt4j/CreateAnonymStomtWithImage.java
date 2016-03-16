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

	@Test
	public void createAnonymStomtMinimalFalse_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalFalse_File()");

		random = getRandomString();

		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");

		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositive_File()");

		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");

		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtWithUrl_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtWithUrl_File()");

		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");
		url = new URL("http://stomt.com");

		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(
				positive, target_id, text, url, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtMinimalPositive_Url()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalPositive_Url()");

		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");

		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(positive, target_id, text, "stomt", imgUrl);
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

		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		imgUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");
		url = new URL("http://stomt.com");


		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(positive, target_id, text, url, "stomt", imgUrl);
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

		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");

		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", FileToBase64(img));
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
	@Test
	public void createAnonymStomtWithUrl_Base64()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtWithUrl_Base64()");

		random = getRandomString();
		positive = true;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = new File("/home/chris/Pictures/javaTEST/test.gif");
		url = new URL("http://stomt.com");

		Stomt stomtObject = StomtClientTest.client.createAnonymStomtWithImage(
				positive, target_id, text, url, "stomt", FileToBase64(img));
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
		
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
