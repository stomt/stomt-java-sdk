package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.apache.http.ParseException;
import org.junit.Test;

import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;

public class UploadImageViaUrl {


	@Test
	public void uploadAvatarWithUrl() throws ParseException, IOException, StomtException {
		
		URL url = new URL("http://img3.wikia.nocookie.net/__cb20120915054011/fmabyond/images/0/0b/Blank_avatar_240x240.gif");
		
		System.out.println("-> TEST: uploadAvatarWithUrl()");
		
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Image get = stomtClient.uploadImageViaUrl("avatar", url);
		
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
	
	// TODO: Backend Error - Response is HTML and not JSON! and MalformEx in Java cause no try catch in test case?!
//	@Test(expected=StomtException.class)
//	public void uploadAvatarWrongUrl() throws ParseException, IOException, StomtException {
//
//		URL url = "www.google.de";
//		
//		System.out.println("-> TEST: uploadAvatarWrongUrl() - StomtException");
//		
//		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
//				
//		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
//
//		stomtClient.uploadImageViaUrl("avatar", url);
//		
//	}
}
