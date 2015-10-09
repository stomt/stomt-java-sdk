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
	private StomtHttpClient httpClient;

	public StomtClient(String appid) {
		this.appid = appid;
		this.auth = new Authorization();
		this.httpClient = new StomtHttpClient(this);
		this.httpClient.setTypeHeader();
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

	public Target registerAnUser(String username, String email, String password, String displayname)
			throws ParseException, IOException, StomtException {
		Map<String, String> bodyParameters = new HashMap<String, String>();
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

	
	// Aktuell noch nicht getestet!
	public Target verifyEmail(String userid, String verification_code) throws ParseException, IOException, StomtException {
		Map<String, String> bodyParameters = new HashMap<String, String>();
		bodyParameters.put("userid", userid);
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
		//return data.get("user").toString();
	}

	public Target login(String emailUsername, String password) throws ParseException, IOException, StomtException {
		Map<String, String> bodyParameters = new HashMap<String, String>();
		bodyParameters.put("login_method", "normal");
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

		return new Target(data.getAsJsonObject("user"));
	}

	public Target loginFacebook(String fb_access_token, String fb_user_id) throws ParseException, IOException, StomtException {
		Map<String, String> bodyParameters = new HashMap<String, String>();
		bodyParameters.put("login_method", "facebook");
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

	public boolean logout() throws ParseException, IOException, StomtException {
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

	public Target refreshAccessToken() throws ParseException, IOException, StomtException {
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());
		httpClient.addRequestHeader("refreshtoken", this.auth.getRefreshtoken());

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + authentication + refresh,
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
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		return new Target(data.getAsJsonObject("user"));
	}

	public boolean checkAccessToken() throws ParseException, IOException, StomtException {
		httpClient.addRequestHeader("accesstoken", this.auth.getAccesstoken());

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.GET, root + authentication + check,
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
		return Boolean.valueOf(data.get("valid").getAsString());
	}

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

	public boolean forgotPassword(String usernameOrEmail) throws ParseException, IOException, StomtException {

		Map<String, String> bodyParameters = new HashMap<String, String>();
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

	public String/* Target */resetPassword(String resetcode, String newPassword) throws ParseException, IOException {
		Map<String, String> bodyParameters = new HashMap<String, String>();
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
			return o.getAsJsonObject("error").get("0").getAsString();
		}

		JsonObject data = o.getAsJsonObject("data");
		auth.setAccesstoken(data.get("accesstoken").getAsString());
		auth.setRefreshtoken(data.get("refreshtoken").getAsString());

		// return new Target(data.getAsJsonObject("user"));
		return data.get("user").toString();
	}

	/* Feeds */

	public/* Feeds[] */Stomt[] getFeed(String type) {
		return null;
	}

	/* Stomts */
	
	public String createStomt(boolean anonym, String img_name, boolean positive, boolean prefetched, String target_id, String text, String url,  String lonlat) throws ParseException, IOException, StomtException {
		
		Map<String, String> bodyParameters = new HashMap<String, String>();
		bodyParameters.put("anonym", Boolean.toString(anonym));
		bodyParameters.put("img_name", img_name);
		bodyParameters.put("positive", Boolean.toString(positive));
		bodyParameters.put("prefetched", Boolean.toString(prefetched));
		bodyParameters.put("target_id", target_id);
		bodyParameters.put("text", text);
		bodyParameters.put("url", url);
		bodyParameters.put("lonlat", lonlat);

		StomtHttpRequest request = new StomtHttpRequest(RequestMethod.POST, root + stomt,
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


		// return new Target(data.getAsJsonObject("user"));
		return data.getAsString();

	}

	// undefined
	public Collection readStomt(String stomt_id) {
		return null;
	}

	public boolean deleteStomt(String stomt_id) {
		return false;
	}

	/* Agreements */

	// Wofür im Request "negative" mitschicken??
	public Collection readAgreement(String stomt_id, boolean negative) {
		return null;
	}

	public boolean deleteAgreement(String stomt_id) {
		return false;
	}

	/* Comments */

	public Collection readComments(String stomt_id) {
		return null;
	}

	public Collection createComment(String parent_id, String text, boolean reaction) {
		// Überprüfen ob reaction auf true gesetzt werden darf weil nur target
		// owner reagieren können
		return null;
	}

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

	// public boolean voteComment(String stomt_id, String comment_id) {
	//
	// }
	//
	// public boolean revokeVote(String stomt_id, String comment_id) {
	//
	// }

	/* Comment Reaction */

	public boolean markCommentAsReaction(String stomt_id, String comment_id) {
		return false;
	}

	public boolean unmarkCommentAsReaction(String stomt_id, String comment_id) {
		return false;
	}

	/* Targets */

	// undefined
	public Target createTarget() {
		return null;
	}

	public Target getTarget(String target_id) {
		return null;
	}

	public Target updateTarget(String target_id) {
		return null;
	}

	/* Target Following - Version 2 */

	// public Target[] getFollows(String target_id) {
	//
	// }
	//
	// public Target[] getFollowers(String target_id) {
	//
	// }
	//
	// public boolean followTarget(String target_id) {
	//
	// }
	//
	// public boolean unfollowTarget(String target_id) {
	//
	// }

	/* Target Stomts */

	public Collection getTargetStomts(String target_id, String type, String secondType) {
		return null;
	}

	/* Categories */

	public Category[] getAllCategories() {
		return null;
	}

	/* Reports */

	public boolean createReport(String report_type, String repor_type_id, String report_weight) {
		return false;
	}

	/* Notifications */

	public Notification[] getNotifications(boolean unseen, String last_notification, int offset, int limit) {
		return null;
	}

	public boolean updateNotifications(String id, boolean seen, boolean clicked) {
		return false;
	}

	/* Images - Version 2 */

	// undefined
	// public Images uploadImage() {
	//
	// }

	/* Search */

	public Target[] searchTarget(String searchItem) {
		return null;
	}

	public Stomt[] hashtag(String tag) {
		return null;
	}

	/* Target Invitations */

	public Invitation createInvitation(String target_id, String invite_to_type, boolean invite_as_owner) {
		return null;
	}

	public Invitation[] getInvitations(String target_id) {
		return null;
	}

	public Invitation checkValidity(String target_id, String code) {
		return null;
	}

	/* Helper functions */

	// private String getError(JsonObject json) {
	// JsonObject error = json.getAsJsonObject("error");
	// return error.get("0").getAsString();
	// }

}
