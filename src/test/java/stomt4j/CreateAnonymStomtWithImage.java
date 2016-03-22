package stomt4j;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Stomt;
import stomt4j.FileAsString;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
	StomtClient client = new StomtClient(StomtClientTest.appid);
	
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	

	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}


	@Test
	public void createAnonymStomtMinimalFalse_File()
			throws ParseException, IOException, StomtException {
		System.out.println("-> TEST: createAnonymStomtMinimalFalse_File()");

		random = getRandomString();
		
		String sourceUri = "https://lh4.googleusercontent.com/-zyqYSm9pAlw/AAAAAAAAAAI/AAAAAAAAAcU/XNhjrASxTaA/photo.jpg";
		
	
		
		
	
			
			String destinationFile = "image.jpg";

			saveImage(sourceUri, destinationFile);
		

	

		
		
		
		File file = new File(destinationFile);
//		URL link = new URL(sourceUri);
//		
//	
//		Image image = ImageIO.read(link);
//		
//		   BufferedImage bi = toBufferedImage(image);
//		    File outputfile = new File("saved.png");
//		   ImageIO.write(bi, "png", outputfile);
		
		
	
		positive = false;
		target_id = "stomt-java";
		text = "Java-SDK test " + random;
		img = file;

		Stomt stomtObject = client.createAnonymStomtWithImage(
				positive, target_id, text, "stomt", img);
		stomt = stomtObject.toString();
		
		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
		
		System.out.println("Expect: " + expected);
		System.out.println("Get: " + stomt);

		assertEquals(expected, stomt);
	}
	
//	@Test
//	public void createAnonymStomtMinimalPositive_File()
//			throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createAnonymStomtMinimalPositive_File()");
//
//		random = getRandomString();
//		positive = true;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		img = generateFile();
//
//		Stomt stomtObject = client.createAnonymStomtWithImage(
//				positive, target_id, text, "stomt", img);
//		stomt = stomtObject.toString();
//		
//		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
//				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
//				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
//		
//		System.out.println("Expect: " + expected);
//		System.out.println("Get: " + stomt);
//
//		assertEquals(expected, stomt);
//	}
//	
//	@Test
//	public void createAnonymStomtWithUrl_File()
//			throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createAnonymStomtWithUrl_File()");
//		
//
//		random = getRandomString();
//		positive = true;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		img = generateFile();
//		url = new URL("http://stomt.com");
//		
//		Stomt stomtObject = client.createAnonymStomtWithImage(
//				positive, target_id, text, url, "stomt", img);
//		stomt = stomtObject.toString();
//		
//		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
//				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
//				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
//		
//		System.out.println("Expect: " + expected);
//		System.out.println("Get: " + stomt);
//
//		assertEquals(expected, stomt);
//	}
//	
//	@Test
//	public void createAnonymStomtMinimalPositive_Url()
//			throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createAnonymStomtMinimalPositive_Url()");
//
//		random = getRandomString();
//		positive = true;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		imgUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");
//
//		Stomt stomtObject = client.createAnonymStomtWithImage(positive, target_id, text, "stomt", imgUrl);
//		stomt = stomtObject.toString();
//		
//		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
//				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
//				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
//		
//		System.out.println("Expect: " + expected);
//		System.out.println("Get: " + stomt);
//
//		assertEquals(expected, stomt);
//	}
//	
//	@Test
//	public void createAnonymStomtMinimalPositiveWithUrl_Url()
//			throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createAnonymStomtMinimalPositiveWithUrl_Url()");
//
//		random = getRandomString();
//		positive = true;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		imgUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/7/75/Internet1.jpg");
//		url = new URL("http://stomt.com");
//
//
//		Stomt stomtObject = client.createAnonymStomtWithImage(positive, target_id, text, url, "stomt", imgUrl);
//		stomt = stomtObject.toString();
//		
//		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
//				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
//				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
//		
//		System.out.println("Expect: " + expected);
//		System.out.println("Get: " + stomt);
//
//		assertEquals(expected, stomt);
//	}
//	
//	@Test
//	public void createAnonymStomtMinimalPositive_Base64()
//			throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createAnonymStomtMinimalPositive_Base64()");
//
//		random = getRandomString();
//		positive = true;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		img = generateFile();
//
//		Stomt stomtObject = client.createAnonymStomtWithImage(
//				positive, target_id, text, "stomt", FileToBase64(img));
//		stomt = stomtObject.toString();
//		
//		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
//				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
//				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=, agreed=]";
//		
//		System.out.println("Expect: " + expected);
//		System.out.println("Get: " + stomt);
//
//		assertEquals(expected, stomt);
//	}
//	
//	@Test
//	public void createAnonymStomtWithUrl_Base64()
//			throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: createAnonymStomtWithUrl_Base64()");
//
//		random = getRandomString();
//		positive = true;
//		target_id = "stomt-java";
//		text = "Java-SDK test " + random;
//		img = generateFile();
//		url = new URL("http://stomt.com");
//
//		Stomt stomtObject = client.createAnonymStomtWithImage(
//				positive, target_id, text, url, "stomt", FileToBase64(img));
//		stomt = stomtObject.toString();
//		
//		expected = "Stomt [id=java-sdk-test-" + random + ", positive=" + positive + ", text=Java-SDK test " + random + ", images=" + stomtObject.getImages() + ", "
//				+ "lang=en, created_at=" + stomtObject.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=true, "
//				+ "target=" + stomtObject.getTarget() + ", highlights=, creator=, url=" + stomtObject.getUrl() + ", agreed=]";
//		
//		System.out.println("Expect: " + expected);
//		System.out.println("Get: " + stomt);
//
//		assertEquals(expected, stomt);
//	}
//	
//	
//	private File generateFile() throws StomtException {
//	
//		byte[] imageArray = Base64.decodeBase64(FileAsString.imageString);
//		BufferedImage image = null;
//		ByteArrayInputStream bis = null;
//		File outputfile = null;
//		
//		try {
//			bis = new ByteArrayInputStream(imageArray);
//			image = ImageIO.read(bis);
//			bis.close();
//			outputfile = new File("image.jpg");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return outputfile;
//		
//	}
//
//
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
