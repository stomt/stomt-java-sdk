package stomt4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.utils.URIBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import stomt4j.auth.*;
import stomt4j.entities.*;

// TODO: check class and method scopes

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

	/*
	 *  Authentification 
	 */
		
	/**
	 * Register an user.
	 * 
	 * @param username The desired username - used to login
	 * @param email The users email
	 * @param password The desired password
	 * @param displayname The desired displayname - used to display on stomt-platform
	 * @return The user-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target registerAnUser(String username, String email, String password, String displayname)
			throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("username", username);
		bodyParameters.put("email", email);
		bodyParameters.put("password", password);
		bodyParameters.put("displayname", displayname);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target verifyEmail(String verification_code) throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("verification_code", verification_code);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.PUT, root + authentication,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target login(String emailUsername, String password) throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("login_method", "normal");
		bodyParameters.put("emailusername", emailUsername);
		bodyParameters.put("password", password);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
				
		return new Target(data.getAsJsonObject("user"));
	}

	/**
	 * Normal authentication with Facebook-Connect. 
	 * <br>For FB-Connect you need to sign-in client-side to receive an accesstoken from Facebook.
	 * 
	 * @param fb_access_token The accesstoken the user receive from Facebook
	 * @param fb_user_id The user id the user receive from Facebook
	 * @return The user-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target loginFacebook(String fb_access_token, String fb_user_id) throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("login_method", "facebook");
		bodyParameters.put("fb_access_token", fb_access_token);
		bodyParameters.put("fb_user_id", fb_user_id);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}
	

	// TODO: Not tested!
	/**
	 *  Normal authentication with reddit.
	 * <br>For Connect with reddit you need to sign-in client-side to receive {@code code} and {@code state}.
	 * 
	 * @param code A one-time use code that may be exchanged for a bearer token. (c.f {@link "https://github.com/reddit/reddit/wiki/OAuth2"})
	 * @param state A string of your choosing. (c.f {@link "https://github.com/reddit/reddit/wiki/OAuth2"})
	 * @return The user-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target loginReddit(String code, String state) throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("login_method", "reddit");
		bodyParameters.put("code", code);
		bodyParameters.put("state", state);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}
	
	// TODO: Not tested!
	/**
	 *  Normal authentication with Twitter.
	 * <br>For Connect with twitter you need to sign-in client-side to receive {@code oauth_token} and {@code oauth_verifier}.
	 * 
	 * @param oauth_token A one-time use code that may be exchanged for a bearer token. (c.f {@link "https://github.com/reddit/reddit/wiki/OAuth2"})
	 * @param oauth_verifier A string of your choosing. (c.f {@link "https://github.com/reddit/reddit/wiki/OAuth2"})
	 * @return The user-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target loginTwitter(String oauth_token, String oauth_verifier) throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("login_method", "twitter");
		bodyParameters.put("oauth_token", oauth_token);
		bodyParameters.put("oauth_verifier", oauth_verifier);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	//TODO: Implement oAuth Login!
	
	/**
	 * Logout existing session.
	 * 
	 * @return true if logout succeed
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public boolean logout() throws IOException, StomtException {
		// No Accesstoken? -> User not logged in
		if (!this.auth.hasAccesstoken()) {
			throw new StomtException("User is not logged in - no accesstoken.");
		}
		
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.DELETE, root + authentication + login,
				httpClient.getRequestHeaders(), null, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		return Boolean.valueOf(data.get("success").getAsString());
	}

	/**
	 * Check if {@code displayname} or {@code email} is still available.
	 * 
	 * @param property Check for existing {@code username} or {@code email}
	 * @param value Value to check
	 * @return true if {@code displayname} or {@code email} is still available
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public boolean checkAvailability(String property, String value) throws IOException, StomtException {
		URIBuilder builder = new URIBuilder();
		builder.setPath(root + authentication + checkAvailability);
		builder.setParameter("property", property);
		builder.setParameter("value", value);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, builder.toString(),
				httpClient.getRequestHeaders(), null, this.auth);


		JsonObject data = httpClient.executeAndParseData(request);
		return Boolean.valueOf(data.get("success").getAsString());
	}
	
	/**
	 * Suggest {@code usernames} based on {@code fullname}
	 * 
	 * @param fullname Suggest usernames based on {@code fullname}
	 * @return An {@code ArrayList} with all suggested usernames
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public ArrayList<String> suggestUsernames(String fullname) throws IOException, StomtException {
		URIBuilder builder = new URIBuilder();
		builder.setPath(root + authentication + suggestedUsernames);
		builder.setParameter("fullname", fullname);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, builder.toString(),
				httpClient.getRequestHeaders(), null, this.auth);

		HttpResponse response = httpClient.execute(request);
		JsonObject o = httpClient.parseResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		JsonArray data = o.getAsJsonArray("data");				
		ArrayList<String> suggestions= new ArrayList<String>();
		
		for (int i = 0; i < data.size(); i++) {
			suggestions.add(data.get(i).getAsString());
		}
			
		return suggestions;
	}

	/**
	 * Sends the user an email to reset his password.
	 * 
	 * @param usernameOrEmail The users username or email
	 * @return true if username or email is valid
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public boolean forgotPassword(String usernameOrEmail) throws IOException, StomtException {

		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("usernameoremail", usernameOrEmail);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + forgotPassword,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		return Boolean.valueOf(data.get("success").getAsString());
	}

	/**
	 * Resets the users password and responds with a new session.
	 * 
	 * @param resetcode The submitted resetcode
	 * @param newPassword The new password
	 * @return The user-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Target resetPassword(String resetcode, String newPassword) throws IOException, StomtException {
		Map<String, Object> bodyParameters = new HashMap<String, Object>();
		bodyParameters.put("resetcode", resetcode);
		bodyParameters.put("newpassword", newPassword);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + resetPassword,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	/*
	 *  Stomts
	 *  
	 *  A single stomt object. The stomt resource is the central resource in the stomt API. 
	 *  It represents one paste - a single stomt.
	 */
	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	private Stomt createStomt(boolean positive, String target_id, String text, URL url, boolean anonym, String img_name, LonLat lonlat) throws IOException, StomtException {
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
		
		bodyParameters.put("positive", positive);
		bodyParameters.put("target_id", target_id);
		bodyParameters.put("text", text);
		bodyParameters.put("anonym", anonym);
		
		StomtHttpRequest request;
		
		if (anonym) {
			request = new StomtHttpRequest(RequestMethod.POST, root + stomts,
				httpClient.getRequestHeaders(), bodyParameters, null); 
		} else {
			setAccesstoken();
			request = new StomtHttpRequest(RequestMethod.POST, root + stomts,
					httpClient.getRequestHeaders(), bodyParameters, this.auth); 
		}

		JsonObject data = httpClient.executeAndParseData(request);
		return new Stomt(data);
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url, String img_name, LonLat lonlat) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url, String img_name) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url, LonLat lonlat) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomt(boolean positive, String target_id, String text, URL url) throws IOException, StomtException {
		return createStomt(positive, target_id, text, url, false, null, null);
	}
	
	/**
	 * Create non-anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @return The created stomt-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomt(boolean positive, String target_id, String text) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, String data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, String data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, File data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createStomtWithImage(boolean positive, String target_id, String text, String context, File data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url, String img_name, LonLat lonlat) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url, String img_name) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url, LonLat lonlat) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text, URL url) throws IOException, StomtException {
		return createStomt(positive, target_id, text, url, true, null, null);
	}
	
	/**
	 * Create anonym stomt.
	 * 
	 * @param positive “I wish” (positive=false) / “I like”(positive=true)
	 * @param target_id To whom the stomt is adressed
	 * @param text The text of a stomt
	 * @return The created stomt-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomt(boolean positive, String target_id, String text) throws IOException, StomtException {
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, URL imgUrl) throws IOException, StomtException {	
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
	 * @throws IOException
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, URL imgUrl, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, String data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, String data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, String data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data, LonLat lonlat) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, URL url, String context, File data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, File data) throws IOException, StomtException {	
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
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt createAnonymStomtWithImage(boolean positive, String target_id, String text, String context, File data, LonLat lonlat) throws IOException, StomtException {	
		Image img = uploadImage(context, data);
		return createStomt(positive, target_id, text, null, true, img.getName(), lonlat);	
	}
	
	/**
	 * Read a single stomt.
	 * 
	 * @param stomt_id The {@code stomt_id} of the stomt which is requested
	 * @return The requested stomt-object
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public Stomt readStomt(String stomt_id) throws IOException, StomtException {
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + stomts + "/" + stomt_id,
				httpClient.getRequestHeaders(), null, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		return new Stomt(data);
	}
	
	/**
	 * Delete a single stomt.
	 * 
	 * @param stomt_id of the stomt which should be deleted
	 * @return {@code true} if the stomt was deleted, otherwise {@code false}
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public boolean deleteStomt(String stomt_id) throws IOException, StomtException {
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.DELETE, root + stomts + "/" + stomt_id,
				httpClient.getRequestHeaders(), null, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		return data.get("success").getAsBoolean();
	}

	/*
	 * Stomt Agreements
	 * 
	 * A single agreement object. In case the user already agreed and wants to
	 * revoke his (dis-)agreement, we do not want you to update the
	 * positive-attribute of the existing agreement via PUT. Just DELETE the old
	 * (dis-)agreement and POST a new one.
	 */
	
	// TODO: Implement!
	public Agreement createAgreement(String stomt_id, boolean positive, boolean anonym) {
		return null;
	}

	// TODO: Implement!
	public boolean deleteAgreement(String stomt_id) {
		return false;
	}
	
	// TODO: Implement!
	public Collection getVoters(String stomt_id) {
		return null;
	}

	/*
	 * Stomt Comments
	 * 
	 * A stomt can have comments, which themselves can have comments again if a
	 * user replied to a comment. They all can be voted and at most one comment
	 * to a stomt can be marked as official reaction.
	 */
	
	// TODO: Implement!
	public Collection createComment(String stomt_id, String parent_id, String text, boolean reaction) {
		// Überprüfen ob reaction auf true gesetzt werden darf weil nur target
		// owner reagieren können
		return null;
	}

	// TODO: Implement!
	public Collection readComments(String stomt_id) {
		return null;
	}
	
	// TODO: Implement!
	public boolean editComment(String stomt_id, String comment_id, boolean anonym, String text, boolean reaction) {
		return false;
	}


	// TODO: Implement!
	public boolean deleteComment(String stomt_id, String comment_id) {
		return false;
	}

	/*
	 * Stomt Comment Votes
	 * 
	 * A single vote on a comment. In case the user already voted and wants to
	 * revoke his voting, we do not want you to update the positive-attribute of
	 * the existing comment via PUT. Just DELETE the old voting and POST a new
	 * one.
	 */

	// TODO: Implement!
	public boolean voteComment(String stomt_id, String comment_id, boolean positive) {
		return false;
	}

	// TODO: Implement!
	public boolean revokeVote(String stomt_id, String comment_id) {
		return false;
	}
	
	/*
	 * Stomt Labels
	 * 
	 * stomts can have labels which can be managed in the targets profile.
	 * Labels can be archived or unarchived and they may be public or private.
	 * Users can create labels, too, which are only visible to them except they
	 * share the labels explicit URL.
	 */
	
	// label a stomt
	
	// TODO: Implement!
	public boolean labelStomt(String stomt_id, String name, String color, boolean as_target_owner) {
		return false;
	}
	
	// TODO: Implement!
	public boolean labelStomt(String stomt_id, String name, String color) {
		return false;
	}
	
	// TODO: Implement!
	public boolean labelStomt(String stomt_id, String name, boolean as_target_owner) {
		return false;
	}
	
	// TODO: Implement!
	public boolean labelStomt(String stomt_id, String name) {
		return false;
	}
	
	// unlabel a stomt

	// TODO: Implement!
	public boolean unlabelStomt(String stomt_id, String label, boolean as_target_owner) {
		return false;
	}
	
	// TODO: Implement!
	public boolean unlabelStomt(String stomt_id, String label) {
		return false;
	}

	/*
	 * Feeds
	 */
	
	// TODO: Change return type: An Array of stomt-objects. Write Test case!
	
	/**
	 * Get a specific feed.
	 * 
	 * @param type The requested feed
	 * @return A Json-String of stomt-objects
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	public String getFeed(String type) throws IOException, StomtException {
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + feeds + type.toLowerCase(),
				httpClient.getRequestHeaders(), null, this.auth);

		JsonObject data = httpClient.executeAndParseData(request);
		return data.getAsString();
	}
	
	/*
	 * Targets
	 * 
	 * Targets are the most important and complex resources. stomt is built to
	 * feedback anything, even friends and general things (e.g. I wish life had
	 * a pause button). Therefore nearly anything is saved as a Target and
	 * depending on its type additional type specific data.
	 */


	// TODO: Implement!
	/**
	 * Create a new Target.
	 * 
	 * @param displayname The name of the new Target (required)
	 * @param username An username for the new Target (optional, unique)
	 * @param image An image for the new Target (optional, name of an uploaded image)
	 * @param category_id A category for the new Target (required, must be an existing {@code category_id})
	 * @param parent_id The {@code target_id} of the parent if the new target is a sub-target (optional)
	 * @param isPrivate {@code true} if the new Target is private, otherwise false (optional, default: false)
	 * @return Target
	 */
	public Target createTarget(String displayname, String username, String image, String category_id, String parent_id, boolean isPrivate) {
		return null;
	}
	
	// TODO: Implement!
	/**
	 * 
	 * Currently only for Twitter (shorthandle = tw).
	 *  
	 * @param src Providers Shorthandle (e.g. "tw")
	 * @param src_id {@code username} at the providers platform: e.g. twitter.com/stomt -> "stomt"
	 * @return Target
	 */
	public Target preflight(String src, String src_id) {
		return null;
	}

	// TODO: Implement!
	/**
	 * Get a specific Target.
	 * 
	 * @param target_id The specific identifier of a target (required)
	 * @return The requested Target as object
	 */
	public Target getTarget(String target_id) {
		return null;
	}
	
	// TODO: Implement!
	/**
	 * Get the stomts of an specific Target.
	 * 
	 * @param target_id The specific identifier of a target (required)
	 * @param type Which type of stomts should be shown? Possibilities: received, created, top (optional)
	 * @param secondType All of one specific {@code type} or just the negative or positive ones? Possibilities: positive, negative (optional)
	 * @param newer_than Only stomts newer than the unix-timestamp will be returned (optional)
	 * @return An {@code ArrayList} of stomts will be returned
	 */
	public ArrayList<Stomt> getTargetStomts(String target_id, String type, String secondType, String newer_than) {
		return null;
	}

	// TODO: Implement!
	/**
	 * Update a specific Target.
	 * 
	 * @param target_id The specific identifier of a target (required)
	 * @param displayname The new displayname
	 * @param passsword The new password
	 * @return The updated Target as object
	 */
	public Target updateTarget(String target_id, String displayname, String passsword) {
		// Login Required
		return null;
	}

	/*
	 * Target Following
	 */
	

	// TODO: Implement!
	/**
	 * Get Followers of a specific Target.
	 * 
	 * @param target_id The specific identifier of a target (required)
	 * @return An {@code ArrayList} with all Followers (ArrayList of Targets)
	 */
	public ArrayList<Target> getFollowers(String target_id) {
		return null;
	}

	// TODO: Implement!
	/**
	 * Follow a specific Target.
	 * 
	 * @param target_id The specific identifier of a target (required)
	 * @return {@code true} if the follow of a specific target was successful, otherwise false
	 */
	public boolean followTarget(String target_id) {
		// Login Required
		return false;
	}

	// TODO: Implement!
	/**
	 * Unfollow a specific Target.
	 * 
	 * @param target_id The specific identifier of a target (required)
	 * @return {@code true} if the unfollow of a specific target was successful, otherwise false
	 */
	public boolean unfollowTarget(String target_id) {
		// Login Required
		return false;
	}
	
	// TODO: Same like getFollowers???? - otherwise implement!
	public ArrayList<Target> getFollows(String target_id) {
		return null;
	}
	
	/*
	 * Categories
	 */
	
	/**
	 * Get all categories.
	 * 
	 * @return An {@code Arraylist} of all categories.
	 */
	public ArrayList<Category> getAllCategories() {
		return null;
	}

	/* 
	 * Notifications 
	 */

	// TODO: Implement!
	/**
	 * Get notifications.
	 * 
	 * @param unseen Set true to get only unseen notifications (optional)
	 * @param last_notification Add a timestamp to get only notifications created after the provided timestamp (optinal)
	 * @param offset Load more notifications for pagination (default: 0) (optional)
	 * @param limit Load more notifications at once (default: 7) (optional)
	 * @return An {@code ArrayList} of notifications
	 */
	public ArrayList<Notification> getNotifications(boolean unseen, String last_notification, int offset, int limit) {
		// Login Required
		return null;
	}

	// TODO: Implement!
	/**
	 * Update notifications.
	 * 
	 * If notifications are seen by the user (e.g. the user views a notification overview) set the seen-attribute to {@code true}. 
	 * If the user clicks/taps on a notification set the clicked-attribute to {@code true}.
	 * 
	 * @param id notification id
	 * @param seen set true to mark as seed
	 * @param clicked set true to mark as clicked
	 * @return {@code true} if the update was successful, otherwise {@code false}
	 */
	public boolean updateNotifications(String id, boolean seen, boolean clicked) {
		// Login Required
		return false;
	}

	/*
	 * Images
	 * 
	 * Images are uploaded in a specific context and Base64 encoded.
	 */

	/**
	 * Upload an image for a specific {@code context}.
	 * 
	 * @param id Target-id to upload image for owned target
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data Base64 encoded image
	 * @param url External image URL - enables image-upload via URL
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	private Image uploadImage(String id, String context, String data, URL url) throws StomtException, IOException {
		
		if (data == null && url == null || data != null && url != null) {
			throw new StomtException("You can not set data and url!");
		}
		
		if (!ImageContext.contains(context.toLowerCase())) {	// Does context match enum?
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
		
		Map[] contextArray = new Map[1];
		contextArray[0] = contextInner;
		
		Map<String, Object> imagesInner = new HashMap<String, Object>();
		imagesInner.put(context, contextArray);
		
		bodyParameters.put("images", imagesInner);		

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + images,
				httpClient.getRequestHeaders(), bodyParameters, this.auth);

		JsonObject responseData = httpClient.executeAndParseData(request);

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
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	private Image uploadImageAsFile(String id, String context, File data, URL url) throws StomtException, IOException {
		
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
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	public Image uploadImageViaUrl(String id, String context, URL url) throws StomtException, IOException {
		return uploadImage(id, context, null, url);
	}
	
	/**
	 * Upload an image for a specific {@code context} via URL.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param url External image URL.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	public Image uploadImageViaUrl(String context, URL url) throws StomtException, IOException {
		return uploadImage(null, context, null, url);
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param id Target-id to upload image for owned target.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data Base64 encoded image.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	public Image uploadImage(String id, String context, String data) throws StomtException, IOException {
		return uploadImage(id, context, data, null);	
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data Base64 encoded image.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	public Image uploadImage(String context, String data) throws StomtException, IOException {
		return uploadImage(null, context, data, null);
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param id Target-id to upload image for owned target.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The image file.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	public Image uploadImage(String id, String context, File data) throws StomtException, IOException {
		return uploadImageAsFile(id, context, data, null);	
	}
	
	/**
	 * Upload an image for a specific {@code context}.
	 * @param context The specific context  (cf. {@code ImageContext.java})
	 * @param data The image file.
	 * @return The uploaded image as stomt related image-object
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	public Image uploadImage(String context, File data) throws StomtException, IOException {
		return uploadImageAsFile(null, context, data, null);
	}
	
	/*
	 * Search
	 * 
	 * To use the result you need the src and src_id. Currently all results have
	 * src = s, because all results represent existing targets in stomt. The
	 * src_id is the targets id.
	 */

	// TODO: Implement!
	public Collection searchTarget(String query) {
		return null;
	}
	
	// TODO: Implement!
	/**
	 * Search stomt
	 * 
	 * @param q Term to search for in the stomt text
	 * @param at {@code target-id} - Only show stomts that belong in some way to the given target (Please us at instead of an @-sign as request parameter)
	 * @param to Filter for stomts the targets have received directly (list of target-ids)
	 * @param from Filter for stomts created by these users (list of target-ids)
	 * @param has Filter for stomts matching the criteria, the keywords can be negated by prefixing them with a “!”. Following keywords are available: votes, comments, reaction, image, labels, url 
	 * @param is Either filter for likes (like) or wishes (wish)
	 * @param label Filter for stomts that contain all given labels, labels can be negated
	 * @param hashtag  Filter for stomts that contain all given hashtags, hashtags can be negated
	 * @return An {@code ArrayList} of stomts which fulfilled the criteria (Current NULL - not implemented!)
	 */
	public ArrayList<Stomt> searchStomts(String q, String at, String to, String from, String has, String is, String label, String hashtag) {
		return null;
	}	

	// TODO: Implement!
	/**
	 * Search hashtags similar to query.
	 * 
	 * @param query For which query this method should search similar hashtags
	 * @return An {@code ArrayList} of hashtags.
	 */
	public ArrayList<String>[] searchHashtags(String query) {
		return null;
	}
	
	// TODO: Implement!
	/**
	 * Hashtag usage.
	 * 
	 * @param hashtag The hashtag you are looking for
	 * @return An {@code ArrayList} of stomts which used {@code hashtag}
	 */
	public ArrayList<Stomt> searchHashtag(String hashtag) {
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
