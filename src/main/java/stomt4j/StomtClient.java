package stomt4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import stomt4j.auth.*;
import stomt4j.entities.*;

/**
 * The StomtClient is needed to use the Stomt Java SDK.
 * After generate an instance of the StomtClient the user can execute the main methods of this SDK with {@code INSTANCE_OF_STOMTCLIENT.NAME_OF_METHOD()}.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class StomtClient implements HttpVariables {

	private String appid;
	private Authorization auth;
	private StomtHttpClient httpClient;
	private static final String base64 = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

	/**
	 * Constructor of the stomt client.
	 * 
	 * @param appid The specific application identifier.
	 */
	public StomtClient(String appid) {
		this.appid = appid;
		this.auth = new Authorization();
		this.httpClient = new StomtHttpClient(this);
		this.httpClient.setTypeHeader();
	}

	/**
	 * @return The Application identifier
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @return The {@code Authorization}-object
	 */
	public Authorization getAuthorization() {
		return auth;
	}

	/**
	 * @param appid The Application identifier
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/* Authentification */
	
	/**
	 * Register an user.
	 * 
	 * @param username The desired username - used to login
	 * @param email The users email
	 * @param password The desired password
	 * @param displayname The desired displayname - used to display on stomt-platform
	 * @return The user-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Target registerAnUser(String username, String email, String password, String displayname)
			throws ParseException, IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("username", username);
		bodyParameters.put("email", email);
		bodyParameters.put("password", password);
		bodyParameters.put("displayname", displayname);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	
	// TODO: Not tested!
	
	/**
	 * Verify the users email address. 
	 * <br>The user gets a link with /verify/{verification_code}.
	 * 
	 * @param verification_code The verification code the user gets
	 * @return The user-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Target verifyEmail(String verification_code) throws ParseException, IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("verification_code", verification_code);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.PUT, root + authentication,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	/**
	 * Normal authentication with credentials.
	 * 
	 * @param emailUsername The users email or username
	 * @param password The users password
	 * @return The user-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Target login(String emailUsername, String password) throws ParseException, IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("login_method", new String("normal"));
		bodyParameters.put("emailusername", emailUsername);
		bodyParameters.put("password", password);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		
		System.out.println(data.getAsJsonObject("user"));
		
		return new Target(data.getAsJsonObject("user"));
	}

	/**
	 * Normal authentication with Facebook-Connect. 
	 * <br>For FB-Connect you need to sign-in client-side to receive an accesstoken from Facebook.
	 * 
	 * @param fb_access_token The accesstoken the user receive from Facebook
	 * @param fb_user_id The user id the user receive from Facebook
	 * @return The user-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Target loginFacebook(String fb_access_token, String fb_user_id) throws ParseException, IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("login_method", new String("facebook"));
		bodyParameters.put("fb_access_token", fb_access_token);
		bodyParameters.put("fb_user_id", fb_user_id);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	/**
	 * Logout existing session.
	 * 
	 * @return true if logout succeed
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public boolean logout() throws ParseException, IOException, StomtException {
		
		// No Accesstoken? -> User not logged in
		if (!this.auth.hasAccesstoken()) {
			throw new StomtException("User is not logged in - no accesstoken.");
		}
		
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.DELETE, root + authentication + login,
				httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("success").getAsString());
	}

	/**
	 * Check if displayname or email is still available.
	 * 
	 * @param property Check for existing "username" or "email"
	 * @param value Value to check
	 * @return true if displayname or email is still available
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public boolean checkAvailability(String property, String value) throws ParseException, IOException, StomtException {

		URIBuilder builder = new URIBuilder();
		builder.setPath(root + authentication + checkAvailability);
		builder.setParameter("property", property);
		builder.setParameter("value", value);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, builder.toString(),
				httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("success").getAsString());
	}

	/**
	 * Sends the user an email to reset his password.
	 * 
	 * @param usernameOrEmail The users username or email
	 * @return true if username or email is valid
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public boolean forgotPassword(String usernameOrEmail) throws ParseException, IOException, StomtException {

		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("usernameoremail", usernameOrEmail);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + forgotPassword,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("success").getAsString());
	}

	/**
	 * Resets the users password and responds with a new session.
	 * 
	 * @param resetcode The submitted resetcode
	 * @param newPassword The new password
	 * @return The user-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Target resetPassword(String resetcode, String newPassword) throws ParseException, IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("resetcode", resetcode);
		bodyParameters.put("newpassword", newPassword);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + resetPassword,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	/* Feeds */
	
	// TODO: Change return type: An Array of stomt-objects.
	
	/**
	 * Get a specific feed.
	 * 
	 * @param type The requested feed
	 * @return A Json-String of stomt-objects
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public String getFeed(String type) throws ParseException, IOException, StomtException {
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + feeds + type.toLowerCase(),
				httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}
		JsonArray data = o.getAsJsonArray("data");
		return data.getAsString();
	}
	
	/* Stomts */
	
	/**
	 * Create a stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param anonym Create anonym (anonym=true) stomt - no login required. Otherwise login required to send accesstoken (anonym = false)
	 * @param img_name The name of the appended image
	 * @param lonlat The GPS coordinates
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	private Stomt createStomt(boolean positive, String target_id, String text, URL url, boolean anonym, String img_name, LonLat lonlat) throws ParseException, IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		if (target_id == null) {
			throw new StomtException("Target ID is required!");
		}
		if (text == null) {
			throw new StomtException("Text is required!");
		}
		if (url != null) {
			if (isUrl(url.toString())) {
				bodyParameters.put("url", url);
			} else {
				throw new StomtException("URL is invalid!");
			}
		}
		if (img_name != null) {
			 bodyParameters.put("img_name", img_name);
		}
		if (lonlat != null) {
			// TODO: Does stomt support Lonlat already? Expand LonLat class.
			// bodyParameters.put("lonlat", lonlat.toJson());
		}
		
		bodyParameters.put("positive", new Boolean(positive));
		bodyParameters.put("target_id", target_id);
		bodyParameters.put("text", text);
		bodyParameters.put("anonym", new Boolean(anonym));
		
		StomtHttpRequest request;
		
		if (anonym == true) {
			request = new StomtHttpRequest(RequestMethod.POST, root + stomts,
				httpClient.getRequestHeaders(), bodyParameters, null); 
		} else {
			setAccesstoken();
			request = new StomtHttpRequest(RequestMethod.POST, root + stomts,
					httpClient.getRequestHeaders(), bodyParameters, this.auth); 
		}
		
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}
		
		return new Stomt(o.getAsJsonObject("data"));
	}
	
	/*
	 * Create non-anonym stomt
	 */
	
	/**
	 * Create non-anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param img_name The name of the appended image
	 * @param lonlat The GPS coordinates
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url, String img_name, LonLat lonlat) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, false, img_name, lonlat);
	}
	
	/**
	 * Create non-anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL 
	 * @param img_name The name of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url, String img_name) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, false, img_name, null);
	}
	
	/**
	 * Create non-anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param lonlat The GPS coordinates
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url, LonLat lonlat) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, false, null, lonlat);
	}
	
	/**
	 * Create non-anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, false, null, null);
	}
	
	/**
	 * Create non-anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomt(boolean positive, String target_id, String text) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, null, false, null, null);
	}
	
	// Upload via URL
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, url, false, img.getName(), lonlat);	
	}
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, url, false, img.getName(), null);	
	}
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, null, false, img.getName(), null);	
	}
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, null, false, img.getName(), lonlat);	
	}
	
	// Upload via Base64 String
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, false, img.getName(), lonlat);	
	}

	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, false, img.getName(), null);	
	}

	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, String data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, false, img.getName(), null);	
	}

	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, String data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, false, img.getName(), lonlat);	
	}
	
	// Upload via File
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, false, img.getName(), lonlat);	
	}

	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, false, img.getName(), null);	
	}
	
	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, File data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, false, img.getName(), null);	
	}

	/**
	 * Create stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, File data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, false, img.getName(), lonlat);	
	}
	
	/*
	 * Create anonym stomt
	 */
	
	/**
	 * Create anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param img_name The name of the appended image
	 * @param lonlat The GPS coordinates
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url, String img_name, LonLat lonlat) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, true, img_name, lonlat);
	}
	
	/**
	 * Create anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL 
	 * @param img_name The name of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url, String img_name) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, true, img_name, null);
	}
	
	/**
	 * Create anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL 
	 * @param lonlat The GPS coordinates
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url, LonLat lonlat) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, true, null, lonlat);
	}
	
	/**
	 * Create anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, url, true, null, null);
	}
	
	/**
	 * Create anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text) throws ParseException, IOException, StomtException {
		return createStomt(positive, target_id, text, null, true, null, null);
	}
	
	// Upload via URL
		
	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, url, true, img.getName(), lonlat);	
	}
	
	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, url, true, img.getName(), null);	
	}
	
	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, null, true, img.getName(), null);	
	}

	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via URL.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param imgUrl The URL of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImageViaUrl(context, imgUrl);
		return createStomt(positive, target_id, text, null, true, img.getName(), lonlat);	
	}
	
	// Upload via Base64 String
	
	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, true, img.getName(), lonlat);	
	}

	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, true, img.getName(), null);	
	}

	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, String data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, true, img.getName(), null);	
	}

	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via Base64 String.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The Base64 String of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, String data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, true, img.getName(), lonlat);	
	}
	
	// Upload via File
	
	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, true, img.getName(), lonlat);	
	}

	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param url The appended URL
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, url, true, img.getName(), null);	
	}
	
	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, File data) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, true, img.getName(), null);	
	}

	/**
	 * Create anonym stomt with an appended image for a specific {@code context}.
	 * This method uses image upload via file.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The file of the appended image
	 * @param lonlat The GPS - latitude, longitude
	 * @return The created stomt-object
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, File data, LonLat lonlat) throws ParseException, IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, true, img.getName(), lonlat);	
	}
	
	// TODO: Not tested!
	/**
	 * Read a single stomt
	 * 
	 * @param stomt_id Slug of a stomt.
	 * @return The requested stomt-object.
	 * @throws ParseException
	 * @throws IOException
	 * @throws StomtException
	 */
	public Stomt readStomt(String stomt_id) throws ParseException, IOException, StomtException {

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + stomts + "/" + stomt_id,
				httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}
		
		return new Stomt(o.getAsJsonObject("data"));
	}

	// TODO: Implement!
	public boolean deleteStomt(String stomt_id) {
		return false;
	}

	/* Agreements */

	// TODO: Implement!
	// Wofür im Request "negative" mitschicken??
	public Collection readAgreement(String stomt_id, boolean negative) {
		return null;
	}

	// TODO: Implement!
	public boolean deleteAgreement(String stomt_id) {
		return false;
	}

	/* Comments */

	// TODO: Implement!
	public Collection readComments(String stomt_id) {
		return null;
	}

	// TODO: Implement!
	public Collection createComment(String parent_id, String text, boolean reaction) {
		// Überprüfen ob reaction auf true gesetzt werden darf weil nur target
		// owner reagieren können
		return null;
	}

	// TODO: Implement!
	public boolean deleteComment(String stomt_id, String comment_id) {
		return false;
	}

	/*
	 * Comment Votes - Version 2
	 * 
	 * A single vote on a comment
	 * 
	 * In case the user already voted and wants to revoke his voting, we do not
	 * want you to update the negative-attribute of the existing comment via
	 * PUT. Just DELETE the old voting and POST a new one.
	 */

//	// TODO: Implement!
//	public boolean voteComment(String stomt_id, String comment_id) {
//
//	}
//
//	// TODO: Implement!
//	public boolean revokeVote(String stomt_id, String comment_id) {
//
//	}

	/* Comment Reaction */

	// TODO: Implement!
	public boolean markCommentAsReaction(String stomt_id, String comment_id) {
		return false;
	}

	// TODO: Implement!
	public boolean unmarkCommentAsReaction(String stomt_id, String comment_id) {
		return false;
	}

	/* Targets */

	// TODO: Implement!
	public Target createTarget() {
		return null;
	}

	// TODO: Implement!
	public Target getTarget(String target_id) {
		return null;
	}

	// TODO: Implement!
	public Target updateTarget(String target_id) {
		return null;
	}

	/* Target Following - Version 2 */

//	// TODO: Implement!
//	public Target[] getFollows(String target_id) {
//
//	}
//
//	// TODO: Implement!
//	public Target[] getFollowers(String target_id) {
//
//	}
//
//	// TODO: Implement!
//	public boolean followTarget(String target_id) {
//
//	}
//
//	// TODO: Implement!
//	public boolean unfollowTarget(String target_id) {
//
//	}

	/* Target Stomts */

	// TODO: Implement!
	public Collection getTargetStomts(String target_id, String type, String secondType) {
		return null;
	}

	/* Categories */

	// 1
	public Category[] getAllCategories() {
		return null;
	}

	/* Reports */

	// TODO: Implement!
	public boolean createReport(String report_type, String repor_type_id, String report_weight) {
		return false;
	}

	/* Notifications */

	// TODO: Implement!
	public Notification[] getNotifications(boolean unseen, String last_notification, int offset, int limit) {
		return null;
	}

	// TODO: Implement!
	public boolean updateNotifications(String id, boolean seen, boolean clicked) {
		return false;
	}

	/* Images */

	/**
	 * Upload an image for a specific {@code context}.
	 * 
	 * @param id Target-id to upload image for owned target
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data Base64 encoded image
	 * @param url External image URL - enables image-upload via URL
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private Image uploadImage(String id, String context, String data, URL url) throws StomtException, ParseException, IOException {
		
		if (data == null && url == null || data != null && url != null) {
			throw new StomtException("You can not set data and url!");
		}
		
		if (!ImageContextWork.contains(context.toLowerCase())) {	// Does context match enum?
			throw new StomtException("The attribut context is invalid!");
		} 
		
		if (context.toLowerCase().matches(ImageContext.avatar.toString())
				|| context.toLowerCase().matches(ImageContext.cover.toString())
				|| this.auth.hasAccesstoken()) {
			setAccesstoken();
		}
		
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		
		if (id != null) {		
			bodyParameters.put("id", id);		
		}
		
		Map<String, Object> contextInner = new HashMap<String, Object>();
		if (url == null) {
			
			if (!data.matches(base64)) {
				throw new StomtException("The image is not Base64 encoded!");			
			}
			
			contextInner.put("data", data);
			
		} else if(isUrl(url.toString())) {
			contextInner.put("url", url);
		} else {
			throw new StomtException("URL is invalid!");
		}
		
		Map<String, Object>[] contextArray = new Map[1];
		contextArray[0] = contextInner;
		
		Map<String, Object> imagesInner = new HashMap<String, Object>();
		imagesInner.put(context, contextArray);
		
		bodyParameters.put("images", imagesInner);		

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + images,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);
		
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonObject responseData = o.getAsJsonObject("data");		
			
		Image responseImage = new Image(responseData.getAsJsonObject("images").getAsJsonObject(context));
		responseImage.setContext(context);
		
		return responseImage;
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * 
	 * @param id Target-id to upload image for owned target
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The image file
	 * @param url External image URL - enables image-upload via URL
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private Image uploadImageAsFile(String id, String context, File data, URL url) throws StomtException, ParseException, IOException {
		
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

		return uploadImage(id, context, imageDataString, url);
	}
	
	/**
	 * Upload an image for a specific {@code context} via URL.
	 * @param id Target-id to upload image for owned target.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param url External image URL.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Image uploadImageViaUrl(String id, String context, URL url) throws StomtException, ParseException, IOException {
		return uploadImage(id, context, null, url);
	}
	
	/**
	 * Upload an image for a specific {@code context} via URL.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param url External image URL.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Image uploadImageViaUrl(String context, URL url) throws StomtException, ParseException, IOException {
		return uploadImage(null, context, null, url);
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param id Target-id to upload image for owned target.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data Base64 encoded image.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Image uploadImage(String id, String context, String data) throws StomtException, ParseException, IOException {
		return uploadImage(id, context, data, null);	
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data Base64 encoded image.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Image uploadImage(String context, String data) throws StomtException, ParseException, IOException {
		return uploadImage(null, context, data, null);
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param id Target-id to upload image for owned target.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The image file.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Image uploadImage(String id, String context, File data) throws StomtException, ParseException, IOException {
		return uploadImageAsFile(id, context, data, null);	
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The image file.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public Image uploadImage(String context, File data) throws StomtException, ParseException, IOException {
		return uploadImageAsFile(null, context, data, null);
	}
	
	


	/* Search */

	// TODO: Implement!
	public Collection searchTarget(String searchItem) {
		return null;
	}

	// TODO: Implement!
	public Stomt[] hashtag(String tag) {
		return null;
	}

	/* Target Invitations */

	// TODO: Implement!
	public Invitation createInvitation(String target_id, String invite_to_type, boolean invite_as_owner) {
		return null;
	}

	// TODO: Implement!
	public Invitation[] getInvitations(String target_id) {
		return null;
	}
	
	// TODO: Implement!
	public Invitation checkValidity(String target_id, String code) {
		return null;
	}
	
	/**
	 * Check if there is a {@code accesstoken} in {@code Authorization}.
	 * 
	 * @throws StomtException if {@code accesstoken} is {@code null}
	 */
	private void setAccesstoken() throws StomtException {
		if (this.getAuthorization().hasAccesstoken()) {
			 httpClient.addRequestHeader("accesstoken", this.getAuthorization().getAccesstoken());
		} else {
			// Get new Accesstoken! Login required!
			throw new StomtException("Login required - no valid Accesstoken.");
		}
	}
	
	/**
	 * Check if the {@code url} is valid.
	 * 
	 * @param url The url which this method has to check.
	 * @return {@code true} if the {@code url} is valid, otherwise {@code false}
	 */
	private boolean isUrl(String url) {
		if (url.contains("http://") || url.contains("https://")) {
			try {
			    URL tryUrl = new URL(url);
			} catch (MalformedURLException e) {
			   return false;
			}
		}
		return true;
	}
	
}
