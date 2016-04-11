package stomt4j;

import org.apache.commons.codec.binary.Base64;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.io.*;
import java.net.URL;

/**
 * TestSuite for Stomt Java SDK.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
@RunWith(Suite.class)
@SuiteClasses({ Login.class, Logout.class, CheckAvailability.class, ForgotPassword.class, CreateAnonymStomt.class,
	CreateAnonymStomtWithImage.class, CreateStomt.class, CreateStomtWithImage.class, UploadImage.class,
	UploadImageAsFile.class, UploadImageViaUrl.class, SuggestUsernames.class, ReadStomt.class, DeleteStomt.class })
public class StomtClientTest {
	
	// Set stomt application id
	public static final String appid = "stzmtmrEU3Kp42XJbX2eYUsYE";
	
	// This StomtClient is used for methods where login is not required.
	public static StomtClient client = new StomtClient(appid);
	
	// Login name and password for tests
	public static final String usernamePassword = "test";
	
	// The test target - all stomts are directed to 
	public static final String target_id = "stomt-java";
	
	// The präfix for the required parameter text
	public static final String textMessage = "Java-SDK test ";
	
	// A single stomt_id and text which exists
	public static final String stomt_id = "java-sdk-test-81637";	// stomt_id
	public static final String stomt = "java-sdk-test-81637 ";		// text
	public static final String sample_image_url = "http://pixabay.com/static/uploads/photo/2012/04/26/19/43/profile-42914_960_720.png";



	/**
	 * Encodes File data into base64 string.
	 * @param data The file.
	 * @return The file as base64 string.
	 * @throws StomtException
	 */
	public static String fileToBase64(File data) throws StomtException {
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

	public static void deleteFile(File toDelete) {
		if (toDelete.exists()) {
			toDelete.delete();
		}
	}

	public static File generateFile(String random) throws StomtException, IOException {

		String destinationFile = "image" + random + ".png";
		saveImage(StomtClientTest.sample_image_url, destinationFile);

		return new File(destinationFile);
	}

	public static String getRandomString() {
		double random = Math.random() * 100000;
		return Integer.toString((int) random);
	}
}