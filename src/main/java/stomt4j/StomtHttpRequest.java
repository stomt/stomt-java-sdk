package stomt4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.google.gson.Gson;
import stomt4j.auth.Authorization;

/**
 * A {@code StomtHttpRequest} handles the components of a HTTP-Request
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class StomtHttpRequest {

	private final RequestMethod method;
	private final String path;
	private final Map<String, Object> bodyParameters;
	private final Authorization authorization;
	private final Map<String, String> requestHeaders;
	
	/**
	 * Constructor of a StomtHttpRequest.
	 * 
	 * @param method Specifies the HTTP method
	 * @param path The URL of an API request
	 * @param requestHeaders Header for the HTTP-Request
	 * @param bodyParameters Body Parameter of the HTTP-Request
	 * @param authorization {@code Authorization} handles Access- and Refreshtoken
	 */
	public StomtHttpRequest(RequestMethod method, String path,
			Map<String, String> requestHeaders,
			Map<String, Object> bodyParameters, Authorization authorization) {
		this.method = method;
		this.path = path;
		this.bodyParameters = bodyParameters;
		this.authorization = authorization;
		this.requestHeaders = requestHeaders;
	}

	/**
	 * @return The HTTP-Method of the HTTP-Request
	 */
	public RequestMethod getMethod() {
		return this.method;
	}

	/**
	 * @return The Body Parameters of the HTTP-Request
	 */
	public Map<String, Object> getBodyParameters() {
		return this.bodyParameters;
	}
	
	/**
	 * @return The Body Parameters of the HTTP-Request as String
	 */
	private String getBodyParametersAsString() {
		
		StringBuffer buf = new StringBuffer("bodyParameters: [");
		
		 Iterator<Entry<String, Object>> it = bodyParameters.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
		        buf.append(pair.getKey().toString());
		        buf.append(" : ");
		        buf.append(pair.getValue().toString());
		        buf.append(",");

		        
		       
		        it.remove(); // avoids a ConcurrentModificationException
		    }
		
		buf.deleteCharAt(buf.length() - 1);
		buf.append("]");
		
		
		
		return buf.toString();
	}

	/**
	 * @return The URL of the HTTP-Request
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @return The {@code Authorization} to handle Accesstoken
	 */
	public Authorization getAuthorization() {
		return this.authorization;
	}

	/**
	 * @return The Headers of the HTTP-Request
	 */
	public Map<String, String> getRequestHeaders() {
		return this.requestHeaders;
	}

	/**
	 * @return The Headers of the HTTP-Request as String
	 */
	private String getRequestHeadersAsString() {
		// TODO: String Buffer ...
		return null;
	}

	/**
	 * Transform {@code bodyParameters} to valid JSON
	 * 
	 * @return {@code bodyParameters} as JSON String
	 */
	public String bodyToJSON() {
		Gson gson = new Gson();
		return gson.toJson(bodyParameters);
	}

	/**
	 * A toString() method for the StomtHttpRequest.
	 * 
	 * @return {@code StomtHttpRequest} as String
	 */
	@Override
	public String toString() {
		return "StomtHttpRequest [method=" + getMethod() + ", path=" + getPath()
				+ ", bodyParameters=" + getBodyParametersAsString()
				+ ", authorization=" + getAuthorization().toString()
				+ ", requestHeaders=" + getRequestHeadersAsString() + "]";
	}

}
