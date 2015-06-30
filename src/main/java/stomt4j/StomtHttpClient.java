package stomt4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class StomtHttpClient implements HttpVariables {

	private final Map<String, String> requestHeaders;

	public StomtHttpClient(StomtClient client) {
		requestHeaders = new HashMap<String, String>();
		requestHeaders.put("appid", client.getAppid());

		// Evtl nur wo login required abfragen oder garnicht handlen? -> MAX:
		// Exception fliegen lassen falls login req und nicht gesetzt
		// if (client.getAuthorization().getAccesstoken() != null) {
		// requestHeaders.put("accesstoken",
		// client.getAuthorization().getAccesstoken());
		// }
	}

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
	 * @param request
	 *            The {@code StomtHttpRequest}
	 * @return The {@code HttpResponse}
	 */
	protected HttpResponse execute(StomtHttpRequest request) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = null;

		try {
			if (request.getMethod() == RequestMethod.GET) {
				HttpGet httpRequest = get(request); // StomtHttpRequest ->
													// HttpRequest
				response = httpClient.execute(httpRequest);
				// To do: HttpResponse -> StomtHttpResponse
				// ... -> return type: StomtHttpResponse
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
	 * Generate get request.
	 */
	private HttpGet get(StomtHttpRequest request) {
		HttpGet getRequest = new HttpGet(request.getPath());

		setHeaders(getRequest, request);
		return getRequest;
	}

	/**
	 * Generate post request.
	 */
	private HttpPost post(StomtHttpRequest request) {
		HttpPost httpRequest = new HttpPost(request.getPath());
		setHeaders(httpRequest, request);
		// set request-body

		try {
			httpRequest.setEntity(new StringEntity(request.bodyToJSON()));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Invalid Http body.");
			e.printStackTrace();
		}

		// try {
		// httpRequest.setEntity(new
		// StringEntity(request.toString().toString()));
		// } catch (UnsupportedEncodingException e) {
		// System.out.println("Can not set entity for HTTP-Request!");
		// e.printStackTrace();
		// }
		// // Evtl.? UrlEncodedFormEntity entity = buildEntityBody(params);
		return httpRequest;
	}

	/**
	 * Generate put request.
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

		// try {
		// httpRequest.setEntity(new StringEntity(request.toString()));
		// } catch (UnsupportedEncodingException e) {
		// System.out.println("Can not set entity for HTTP-Request!");
		// e.printStackTrace();
		// }
		// Evtl.? UrlEncodedFormEntity entity = buildEntityBody(params);
		return httpRequest;
	}

	/**
	 * Generate delete request.
	 */
	private HttpDelete delete(StomtHttpRequest request) {
		HttpDelete httpRequest = new HttpDelete(request.getPath());
		setHeaders(httpRequest, request);
		return httpRequest;
	}

	/**
	 * Set all headers defined in StomtHttpRequest.
	 * 
	 * @param methodRequest
	 *            The valid {@code HttpRequest}
	 * @param request
	 *            The {@code StomtHttpRequest}
	 */
	public void setHeaders(HttpRequestBase methodRequest, StomtHttpRequest request) {
		for (Map.Entry<String, String> entry : request.getRequestHeaders().entrySet()) {
			methodRequest.setHeader(entry.getKey(), entry.getValue());
		}
	}

}
