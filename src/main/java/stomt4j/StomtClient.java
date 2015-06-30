package stomt4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import stomt4j.auth.*;
import stomt4j.entities.*;

public class StomtClient implements HttpVariables {
	
	private String appid;
	private Authorization auth;

	public StomtClient(String appid) {
		this.appid = appid;
		this.auth = new Authorization();
	}

	public String getAppid() {
		return appid;
	}

	public Authorization getAuthorization() {
		return auth;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	/* Authentification */
	
	
	// MAX: Target(JsonObject user) 
	public String/*Target*/ registerAnUser(String username, String email, String password, String fullname) throws ParseException, IOException {
		Map<String, String>  bodyParameters = new HashMap<String, String>();
		bodyParameters.put("username", username);
		bodyParameters.put("email", email);
		bodyParameters.put("password", password);
		bodyParameters.put("fullname", fullname);
		
		// Stomt Client Konstruktor -> httpclient + standard header
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication, null, httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			return o.getAsJsonObject("error").get("0").getAsString();
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		return data.get("user").toString();
	}
	
	public String/*Target*/ verifyEmail(String userid, String verification_code) throws ParseException, IOException {
		Map<String, String>  bodyParameters = new HashMap<String, String>();
		bodyParameters.put("userid", userid);
		bodyParameters.put("verification_code", verification_code);

		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.PUT, root + authentication, null, httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			return o.getAsJsonObject("error").get("0").getAsString();
		}
		
		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		return data.get("user").toString();
	}
	
	public String/*Target*/ login(String emailUsername, String password) throws ParseException, IOException {
		Map<String, String>  bodyParameters = new HashMap<String, String>();
		bodyParameters.put("login_method", "normal");
		bodyParameters.put("emailusername", emailUsername);
		bodyParameters.put("password", password);
		
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login, null, httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			return o.getAsJsonObject("error").get("0").getAsString();
		}
		
		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		return data.get("user").toString();
	}
	
	public String/*Target*/ loginFacebook(String fb_access_token, String fb_user_id) throws ParseException, IOException {
		Map<String, String>  bodyParameters = new HashMap<String, String>();
		bodyParameters.put("login_method", "facebook");
		bodyParameters.put("fb_access_token", fb_access_token);
		bodyParameters.put("fb_user_id", fb_user_id);
		
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + login, null, httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			return o.getAsJsonObject("error").get("0").getAsString();
		}
		
		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		return data.get("user").toString();
	}
	
	public boolean logout() throws ParseException, IOException, StomtException {
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.DELETE, root + authentication + login, null, httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o.getAsJsonObject("error").get("0").getAsString());
		}
		
		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("success").getAsString());
	}
	
	public String/*Target*/ refreshAccessToken() throws ParseException, IOException {
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());
		httpClient.addRequestHeader("refreshtoken", this.auth.getRefreshtoken());
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + refresh, null, httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			return o.getAsJsonObject("error").get("0").getAsString();
		}
		
		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		return data.get("user").toString();
	}
	
	public boolean checkAccessToken() throws ParseException, IOException, StomtException {
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + authentication + check, null, httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o.getAsJsonObject("error").get("0").getAsString());
		}
		
		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("valid").getAsString());
	}
	
	public boolean checkAvailability(String property, String value) throws ParseException, IOException, StomtException {
		// Evtl. Http-Parameter nicht speichern und nur der url anhängen? -> MAX: URL Builder
//		 Map<String, String>  parameters = new HashMap<String, String>();
//		 parameters.put("property", property);
//		 parameters.put("value", value);
		
		StomtHttpClient httpClient = new StomtHttpClient(this);
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + authentication + checkAvailability + "?property=" + property + "&value=" + value , null, httpClient.getRequestHeaders(), null, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o.getAsJsonObject("error").get("0").getAsString());
		}
		
		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("success").getAsString());
	}
		        
	public boolean forgotPassword(String usernameOrEmail) throws ParseException, IOException, StomtException {
		Map<String, String>  bodyParameters = new HashMap<String, String>();
		bodyParameters.put("usernameoremail", usernameOrEmail);
		
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + forgotPassword, null, httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o.getAsJsonObject("error").get("0").getAsString());
		}
		
		JsonObject data = o.getAsJsonObject("data");
		return Boolean.valueOf(data.get("success").getAsString());
	}
	
	public String/*Target*/ resetPassword(String resetcode, String newPassword) throws ParseException, IOException {
		Map<String, String>  bodyParameters = new HashMap<String, String>();
		bodyParameters.put("resetcode", resetcode);
		bodyParameters.put("newpassword", newPassword);
		
		StomtHttpClient httpClient = new StomtHttpClient(this);
		httpClient.setTypeHeader();
		
		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + resetPassword, null, httpClient.getRequestHeaders(), bodyParameters, this.auth);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(json);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			return o.getAsJsonObject("error").get("0").getAsString();
		}
		
		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());
		return data.get("user").toString();
	}
	
	/* Feeds */
	
	public /*Feeds[]*/Stomt[] getFeed(String type) {
		
	}
	
	/* Stomts */
	
	// undefined
	public Collection createStomt() {
		
	}
	
	// undefined
	public Collection readStomt(String stomt_id) {
		
	}
	
	public boolean deleteStomt(String stomt_id) {
		
	}
	
	/* Agreements */
	
	// Wofür im Request "negative" mitschicken??
	public Collection readAgreement(String stomt_id, boolean negative) {
		
	}
	
	public boolean deleteAgreement(String stomt_id) {
		
	}
	
	/* Comments */
	
	public Collection readComments(String stomt_id) {
		
	}
	
	public Collection createComment(String parent_id, String text, boolean reaction) {
		// Überprüfen ob reaction auf true gesetzt werden darf weil nur target owner reagieren können
	}
	
	public boolean deleteComment(String stomt_id, String comment_id) {
		
	}
	
	
	
	/* Comment Votes - Version 2 
	 * 
	 * A single vote on a comment
	 * 
	 * In case the user already voted and wants to revoke his voting, we do not want you to update the 
	 * negative-attribute of the existing comment via PUT. Just DELETE the old voting and POST a new one.
	 */

//	public boolean voteComment(String stomt_id, String comment_id) {
//		
//	}
//
//	public boolean revokeVote(String stomt_id, String comment_id) {
//		
//	}
	
	
	
	/* Comment Reaction */
	
	public boolean markCommentAsReaction(String stomt_id, String comment_id) {
		
	}
	
	public boolean unmarkCommentAsReaction(String stomt_id, String comment_id) {
		
	}
	
	/* Targets */
	
	// undefined
	public Target createTarget() {
		
	}
	
	public Target getTarget(String target_id) {
		
	}
	
	public Target updateTarget(String target_id) {
		
	}
	
	
	
	/* Target Following - Version 2 */
	
//	public Target[] getFollows(String target_id) {
//		
//	}
//	
//	public Target[] getFollowers(String target_id) {
//		
//	}
//	
//	public boolean followTarget(String target_id) {
//		
//	}
//	
//	public boolean unfollowTarget(String target_id) {
//		
//	}
	
	
	
	/* Target Stomts */
	
	public Collection getTargetStomts(String target_id, String type, String secondType) {
		
	}
	
	/* Categories */
	
	public Category[] getAllCategories() {
		
	}
	
	/* Reports */
	
	public boolean createReport(String report_type, String repor_type_id, String report_weight) {
		
	}
	
	/* Notifications */
	
	public Notification[] getNotifications(boolean unseen, String last_notification, int offset, int limit) {
		
	}
	
	public boolean updateNotifications(String id, boolean seen, boolean clicked) {
		
	}
	
	
	
	/* Images - Version 2 */
	
	// undefined
//	public Images uploadImage() {
//		
//	}
	
	
	
	/* Search */
	
	public Collection<Target> searchTarget(String searchItem) {
		
	}

	public Stomt[] hashtag(String tag) {
		
	}
	
	/* Target Invitations */
	
	public Invitation createInvitation(String target_id, String invite_to_type, boolean invite_as_owner) {
		
	}
	
	public Invitation[] getInvitations(String target_id) {
		
	}
	
	public Invitation checkValidity(String target_id, String code) {
		
	}
	
	/* Helper functions */
	
//	private String getError(JsonObject json) {
//		JsonObject error = json.getAsJsonObject("error");
//		return error.get("0").getAsString();
//	}
	
}
