package stomt4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
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
	 * Transform {@code StomtHttpRequest} to {@code HttpGet}.  .
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
	 * Transform {@code StomtHttpRequest} to {@code HttpPost}.  .
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
	public void setHeaders(HttpRequestBase methodRequest, StomtHttpRequest request) {
		for (Map.Entry<String, String> entry : request.getRequestHeaders().entrySet()) {
			methodRequest.setHeader(entry.getKey(), entry.getValue());
		}
	}

}
