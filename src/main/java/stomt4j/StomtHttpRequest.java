package stomt4j;

import java.util.Map;

import com.google.gson.Gson;

import stomt4j.auth.Authorization;

public class StomtHttpRequest {

	private final RequestMethod method;

	private final String path;

	private final Map<String, String> bodyParameters;

	// zb. für update notifications
	// private final Collection bodyCollection;

	private final Authorization authorization;

	private final Map<String, String> requestHeaders;

	/**
	 * @param method
	 *            Specifies the HTTP method
	 * @param url
	 *            the request to request
	 * @param parameters
	 *            parameters
	 * @param authorization
	 *            Authentication implementation.
	 * @param requestHeaders
	 *            request headers
	 */
	public StomtHttpRequest(RequestMethod method, String path, Map<String, String> requestHeaders,
			Map<String, String> bodyParameters, Authorization authorization) {
		this.method = method;
		this.path = path;
		this.bodyParameters = bodyParameters;
		this.authorization = authorization;
		this.requestHeaders = requestHeaders;
	}

	public RequestMethod getMethod() {
		return this.method;
	}

	public Map<String, String> getBodyParameters() {
		return this.bodyParameters;
	}

	public String getPath() {
		return this.path;
	}

	public Authorization getAuthorization() {
		return this.authorization;
	}

	public Map<String, String> getRequestHeaders() {
		return this.requestHeaders;
	}

	public String bodyToJSON() {
		Gson gson = new Gson();
		return gson.toJson(bodyParameters);
	}

	@Override
	public String toString() {
		return "StomtHttpRequest [method=" + method + ", path=" + path + ", bodyParameters=" + bodyParameters
				+ ", authorization=" + authorization + ", requestHeaders=" + requestHeaders + "]";
	}

}
