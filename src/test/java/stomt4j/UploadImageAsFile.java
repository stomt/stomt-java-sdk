package stomt4j;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;


public class UploadImageAsFile {
	
	@Test
	public void uploadAvatarAsFile() throws ParseException, IOException, StomtException {

		File avatar = new File("/home/chris/Pictures/javaTEST/test-user.png");
		
		System.out.println("-> TEST: uploadAvatarAsFile()");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		System.out.println("BIS HIER");
		
		Image get = stomtClient.uploadImage("avatar", avatar);
		
		
		System.out.println("get: " + get);
		
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

		System.out.println("Get: " + get.toString());
		
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
	
	
	
	
	
	
	
//	@Test(expected=StomtException.class)
//	public void uploadAsFileWrongDirectory() throws ParseException, IOException, StomtException {
//
//		File img = new File("");
//		
//		System.out.println("-> TEST: uploadAsFileNoBase64() - StomtException");
//		
//		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
//				
//		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
//
//		stomtClient.uploadImage("avatar", img);
//	}



}
