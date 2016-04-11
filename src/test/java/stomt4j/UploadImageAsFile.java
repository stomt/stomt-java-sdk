package stomt4j;

import org.apache.http.ParseException;
import org.junit.After;
import org.junit.Test;
import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class UploadImageAsFile {
	
	private String random = null;
	private File img = null;

	@After
	public void tearDown() throws ParseException, IOException, StomtException {
		// delete File image
		StomtClientTest.deleteFile(img);
	}

	@Test
	public void uploadAvatarAsFile() throws ParseException, IOException, StomtException {

		random = StomtClientTest.getRandomString();
		img = StomtClientTest.generateFile(random);
		
		System.out.println("-> TEST: uploadAvatarAsFile()");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		Image get = stomtClient.uploadImage("avatar", img);
				
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

}
