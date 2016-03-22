package stomt4j;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.http.ParseException;
import org.junit.After;
import org.junit.Test;
import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;


public class UploadImageAsFile {
	
	String random = null;
	String sourceUri = "https://pixabay.com/static/uploads/photo/2012/04/26/19/43/profile-42914_960_720.png";
	File img = null;
	
	@After
	public void tearDown() throws ParseException, IOException, StomtException {
		// delete File image
		deleteFile(img);
	}

	@Test
	public void uploadAvatarAsFile() throws ParseException, IOException, StomtException {

		random = getRandomString();
		img = generateFile(random);
		
		System.out.println("-> TEST: uploadAvatarAsFile()");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		Image get = stomtClient.uploadImage("avatar", img);
				
		stomtClient.logout();

		Image expect = new Image();
		
		expect.setContext(ImageContext.avatar.toString());
		
		if (get.getUrl() == null || get.getUrl() == "") {
			throw new StomtException("URL: Response unexpected!");
		} else {
			expect.setUrl(get.getUrl());
		}
		
		if (get.getName() == null || get.getName() == "") {
			throw new StomtException("NAME: Response unexpected!");
		} else {
			expect.setName(get.getName());
		}
		
		expect.setThumb(null);

		System.out.println("Expect: " + expect.asString());
		System.out.println("Get: " + get.asString());
		
		assertEquals(get.toString(), expect.toString());
	}
	
	// TODO: Backend does not support cover image yet
//	@Test
//	public void uploadCoverAsFile() throws ParseException, IOException, StomtException {
//
//		File cover = new File("/home/chris/Pictures/javaTEST/Java.jpg");
//		
//		System.out.println("-> TEST: uploadAvatarAsFile()");
//		
//		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
//				
//		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
//
//		Image get = stomtClient.uploadImage("upload-cover-test", "cover", cover);
//		
//		stomtClient.logout();
//
//		Image expect = new Image();
//		
//		expect.setContext(ImageContext.cover.toString());
//	
//	if (get.getUrl() == null || get.getUrl() == "") {
//		throw new StomtException("URL: Response unexpected!");
//	} else {
//		expect.setUrl(get.getUrl());
//	}
//	
//	if (get.getName() == null || get.getName() == "") {
//		throw new StomtException("NAME: Response unexpected!");
//	} else {
//		expect.setName(get.getName());
//	}
//	
//		expect.setThumb(null);
//
//		System.out.println("Get: " + get.toString());
//		
//		assertEquals(get.toString(), expect.toString());
//	}	

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
	
	public void deleteFile(File toDelete) {
		if (toDelete.exists()) {
			toDelete.delete();
		}
	}

	private File generateFile(String random) throws StomtException, IOException {
	
		String destinationFile = "image" + random + ".png";
		saveImage(sourceUri, destinationFile);

		return new File(destinationFile);
	}
	
	private String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}

}
