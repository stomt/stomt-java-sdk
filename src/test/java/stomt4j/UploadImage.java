package stomt4j;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.ParseException;
import org.junit.Test;
import stomt4j.entities.Image;
import stomt4j.entities.ImageContext;

public class UploadImage {
	
	String random = null;
	String sourceUri = "http://pixabay.com/static/uploads/photo/2012/04/26/19/43/profile-42914_960_720.png";
	File img = null;

	@Test
	public void uploadAvatarWithString() throws ParseException, IOException, StomtException {

		System.out.println("-> TEST: uploadAvatarWithString()");

		random = getRandomString();
		File img = generateFile(random);
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

		System.out.println("Expect: " + expect.toString());
		System.out.println("Get: " + get.toString());
		
		assertEquals(get.toString(), expect.toString());
		deleteFile(img);
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
