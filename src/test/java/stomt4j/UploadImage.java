package stomt4j;

import org.apache.http.ParseException;
import org.junit.After;
import org.junit.Test;
import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UploadImage {
	
	private String random = null;
	private File img = null;

	@After
	public void tearDown() throws ParseException, IOException, StomtException {
		// delete File image
		if (img != null) {
			StomtClientTest.deleteFile(img);
		}
	}

	@Test
	public void uploadAvatarWithString() throws ParseException, IOException, StomtException {

		System.out.println("-> TEST: uploadAvatarWithString()");

		random = StomtClientTest.getRandomString();
		img = StomtClientTest.generateFile(random);
		String myAvatar = StomtClientTest.fileToBase64(img);
				
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Image get = stomtClient.uploadImage("avatar", myAvatar);
		
		stomtClient.logout();

		Image expect = new Image();
		
		expect.setContext(ImageContext.avatar.toString());

		if (get.getUrl() == null || get.getUrl().equals("")) {
			throw new StomtException("URL: Response unexpected!");
		} else {
			expect.setUrl(get.getUrl());
		}
		
		if (get.getName() == null || get.getName().equals("")) {
			throw new StomtException("NAME: Response unexpected!");
		} else {
			expect.setName(get.getName());
		}
		
		expect.setThumb(null);

		System.out.println("Expect: " + expect.toString());
		System.out.println("Get: " + get.toString());
		
		assertEquals(get.toString(), expect.toString());
	}
	
	@Test(expected=StomtException.class)
	public void uploadImageWrongContext() throws ParseException, IOException, StomtException {

		System.out.println("-> TEST: uploadImageWrongContext() - StomtException");

		random = StomtClientTest.getRandomString();
		img = StomtClientTest.generateFile(random);
		String myAvatar = StomtClientTest.fileToBase64(img);
				
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		System.out.println("Expect: The attribute context is invalid!!");
		System.out.print("Get: ");

		stomtClient.uploadImage("ABC", myAvatar);
	}
	
	@Test(expected=StomtException.class)
	public void uploadImageNotBase64() throws ParseException, IOException, StomtException {

		System.out.println("-> TEST: uploadImageNotBase64() - StomtException");

		String myAvatar = "abc";		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		System.out.println("Expect: The image is not Base64 encoded!");
		System.out.print("Get: ");

		stomtClient.uploadImage("avatar", myAvatar);
	}

}
