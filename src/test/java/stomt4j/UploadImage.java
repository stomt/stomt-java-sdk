package stomt4j;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;

public class UploadImage {

	@Test
	public void uploadAvatarWithString() throws ParseException, IOException, StomtException {

		System.out.println("-> TEST: uploadAvatarWithString()");

		File img = new File("/home/chris/Pictures/javaTEST/test-user.png");
		String myAvatar = fileToBase64(img);
				
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);

		Image get = stomtClient.uploadImage("avatar", myAvatar);
		
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
	
	@Test(expected=StomtException.class)
	public void uploadImageWrongContext() throws ParseException, IOException, StomtException {

		System.out.println("-> TEST: uploadImageWrongContext() - StomtException");
		
		File avatar = new File("/home/chris/Pictures/javaTEST/test-user.png");
		String myAvatar = fileToBase64(avatar);
				
		StomtClient stomtClient = new StomtClient(StomtClientTest.appid);		
				
		stomtClient.login(StomtClientTest.usernamePassword, StomtClientTest.usernamePassword);
		
		System.out.println("Expect: The attribut context is invalid!!");
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
	
	/**
	 * Encodes File data into base64 string.
	 * @param data The file.
	 * @return The file as base64 string.
	 * @throws StomtException
	 */
	private String fileToBase64(File data) throws StomtException {
		String imageDataString = null;
		
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(data);
            byte[] bytes = new byte[(int)data.length()];
            fileInputStreamReader.read(bytes);
            imageDataString = new String(Base64.encodeBase64(bytes));
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageDataString;
	}
}
