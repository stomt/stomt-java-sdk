package stomt4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * The {@code StomtHttpClient} handles the HTTP-Connection.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class StomtHttpClient implements HttpVariables {

	private final Map<String, String> requestHeaders;

	/**
	 * Constructor of the {@code StomtHttpClient}.
	 * 
	 * @param client The instance of {@code StomtClient} which handles all possible Requests.
	 */
	public StomtHttpClient(StomtClient client) {
		requestHeaders = new HashMap<String, String>();
		requestHeaders.put("appid", client.getAppid());
	}

	/**
	 * @return The {@code requestHeaders} of this {@code StomtHttClient}.
	 */
	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public void setTypeHeader() {
		requestHeaders.put("Content-Type", contentType);
	}

	public void addRequestHeader(String name, String value) {
		requestHeaders.put(name, value);
	}

	/**
	 * Transform StomtHttpRequest to a valid HttpRequest and executes it.
	 * 
	 * @param request The {@code StomtHttpRequest}
	 * @return The {@code HttpResponse}
	 */
	protected HttpResponse execute(StomtHttpRequest request) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;

		// StomtHttpRequest to HttpRequest and execute
		try {
			if (request.getMethod() == RequestMethod.GET) {
				HttpGet httpRequest = get(request); 
				response = httpClient.execute(httpRequest);
			} else if (request.getMethod() == RequestMethod.POST) {
				HttpPost httpRequest = post(request);
				response = httpClient.execute(httpRequest);
			} else if (request.getMethod() == RequestMethod.PUT) {
				HttpPut httpRequest = put(request);
				response = httpClient.execute(httpRequest);
			} else if (request.getMethod() == RequestMethod.DELETE) {
				HttpDelete deleteRequest = delete(request);
				response = httpClient.execute(deleteRequest);
			} else {
				throw new IllegalArgumentException("Unknown HTTP Method: " + request.getMethod());
			}
		} catch (ClientProtocolException e) {
			System.out.println("HTTP-Request can not be executed!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("HTTP-Request can not be executed!");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Transforms {@code HttpResponse} to a valid {@code JsonObject}.
	 * @param response The {@code HttpResponse} from the stomt-Server
	 * @return The {@code HttpResponse} as a valid {@code JsonObject}
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 */
	protected JsonObject parseResponse(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		JsonParser parser = new JsonParser();
		return (JsonObject) parser.parse(json);
	}

	/**
	 * Executes the {@code StomtHttpRequest} by transforming it into a valid {@code HttpRequest}.
	 * @param request The valid {@code StomtHttpRequest}
	 * @return The {@code HttpResponse} as {@code JsonObject}
	 * @throws IOException Throws an {@code IOException} if the {@code HttpResponse} is not valid Json
	 * @throws StomtException Throws a {@code StomtException} if the status code is != 200 (eg. invalid request)
	 */
	protected JsonObject executeAndParseData(StomtHttpRequest request) throws IOException, StomtException {
		HttpResponse response = execute(request);
		JsonObject o = parseResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new StomtException(o);
		}

		return o.getAsJsonObject("data");
	}
	
	/**
	 * Transform {@code StomtHttpRequest} to {@code HttpGet}.
	 * 
	 * @param request {@code StomtHttpRequest} as {@code HttpGet}
	 * @return The valid HTTP-Request
	 */
	private HttpGet get(StomtHttpRequest request) {
		HttpGet getRequest = new HttpGet(request.getPath());
		setHeaders(getRequest, request);
		return getRequest;
	}

	/**
	 * Transform {@code StomtHttpRequest} to {@code HttpPost}.
	 * 
	 * @param request {@code StomtHttpRequest} as {@code HttpPost}
	 * @return The valid HTTP-Request
	 */
	private HttpPost post(StomtHttpRequest request) {
		HttpPost httpRequest = new HttpPost(request.getPath());
		setHeaders(httpRequest, request);
		
		try {
			httpRequest.setEntity(new StringEntity(request.bodyToJSON()));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Invalid Http body.");
			e.printStackTrace();
		}
		return httpRequest;
	}

	/**
	 * Transform {@code StomtHttpRequest} to {@code HttpPut}.  .
	 * 
	 * @param request {@code StomtHttpRequest} as {@code HttpPut}
	 * @return The valid HTTP-Request
	 */
	private HttpPut put(StomtHttpRequest request) {
		HttpPut httpRequest = new HttpPut(request.getPath());
		setHeaders(httpRequest, request);

		try {
			httpRequest.setEntity(new StringEntity(request.bodyToJSON()));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Invalid Http body.");
			e.printStackTrace();
		}
		return httpRequest;
	}

	/**
	 * Transform {@code StomtHttpRequest} to {@code HttpDelete}.  .
	 * 
	 * @param request {@code StomtHttpRequest} as {@code HttpDelete}
	 * @return The valid HTTP-Request
	 */
	private HttpDelete delete(StomtHttpRequest request) {
		HttpDelete httpRequest = new HttpDelete(request.getPath());
		setHeaders(httpRequest, request);
		return httpRequest;
	}

	/**
	 * Set all headers which are defined in StomtHttpRequest.
	 * 
	 * @param methodRequest The HttpRequestMethod
	 * @param request The {@code StomtHttpRequest}
	 */
	private void setHeaders(HttpRequestBase methodRequest, StomtHttpRequest request) {
		for (Map.Entry<String, String> entry : request.getRequestHeaders().entrySet()) {
			methodRequest.setHeader(entry.getKey(), entry.getValue());
		}
	}

}
