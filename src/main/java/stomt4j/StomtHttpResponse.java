package stomt4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;


public class StomtHttpResponse {

	private HttpResponse response;
    protected int statusCode;
    protected String responseAsString = null;
    protected InputStream is;
    private boolean streamConsumed = false;
    
    public StomtHttpResponse(HttpResponse response) {
    	this.response = response;
    	this.statusCode = response.getStatusLine().getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }


    /**
     * Returns the response stream.<br>
     * This method cannot be called after calling asString() or asDcoument()<br>
     * It is suggested to call disconnect() after consuming the stream.
     * <p/>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body stream
     * @see #disconnect()
     */
    public InputStream asStream() {
        if (streamConsumed) {
            throw new IllegalStateException("Stream has already been consumed.");
        }
        return is;
    }

    /**
     * Returns the response body as string.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body
     * @throws StomtException
     */
    public String asString() throws StomtException {
        if (null == responseAsString) {
            BufferedReader br = null;
            InputStream stream = null;
            try {
                stream = asStream();
                if (null == stream) {
                    return null;
                }
                br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    buf.append(line).append("\n");
                }
                this.responseAsString = buf.toString();
                stream.close();
                streamConsumed = true;
            } catch (IOException ioe) {
                throw new StomtException(ioe.getMessage(), ioe);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ignore) {
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ignore) {
                    }
                }
            }
        }
        return responseAsString;
    }

    private JSONObject json = null;

    /**
     * Returns the response body as Stomt4j.JSONObject.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body as Stomt4j.JSONObject
     * @throws StomtException
     */
    public JSONObject asJSONObject() throws StomtException {
        if (json == null) {
            Reader reader = null;
            try {
                if (responseAsString == null) {
                    reader = asReader();
                    json = new JSONObject(new JSONTokener(reader));
                } else {
                    json = new JSONObject(responseAsString);
                }
           
            } catch (JSONException jsone) {
                if (responseAsString == null) {
                    throw new StomtException(jsone.getMessage(), jsone);
                } else {
                    throw new StomtException(jsone.getMessage() + ":" + this.responseAsString, jsone);
                }
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ignore) {
                    }
                }
            }
        }
        return json;
    }

//    private JSONArray jsonArray = null;
//
//    /**
//     * Returns the response body as Stomt4j.JSONArray.<br>
//     * Disconnects the internal HttpURLConnection silently.
//     *
//     * @return response body as Stomt4j.JSONArray
//     * @throws StomtException
//     */
//    public JSONArray asJSONArray() throws StomtException {
//        if (jsonArray == null) {
//            Reader reader = null;
//            try {
//                if (responseAsString == null) {
//                    reader = asReader();
//                    jsonArray = new JSONArray(new JSONTokener(reader));
//                } else {
//                    jsonArray = new JSONArray(responseAsString);
//                }
//                if (CONF.isPrettyDebugEnabled()) {
//                    logger.debug(jsonArray.toString(1));
//                } else {
//                    logger.debug(responseAsString != null ? responseAsString :
//                            jsonArray.toString());
//                }
//            } catch (JSONException jsone) {
//                if (logger.isDebugEnabled()) {
//                    throw new StomtException(jsone.getMessage() + ":" + this.responseAsString, jsone);
//                } else {
//                    throw new StomtException(jsone.getMessage(), jsone);
//                }
//            } finally {
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException ignore) {
//                    }
//                }
//                disconnectForcibly();
//            }
//        }
//        return jsonArray;
//    }

    public Reader asReader() {
        try {
            return new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException uee) {
            return new InputStreamReader(is);
        }
    }
//
//    private void disconnectForcibly() {
//        try {
//            disconnect();
//        } catch (Exception ignore) {
//        }
//    }
//
//    public abstract void disconnect() throws IOException;

//    public String toString() {
//        return "HttpResponse{" +
//                "statusCode=" + statusCode +
//                ", responseAsString='" + responseAsString + '\'' +
//                ", is=" + is +
//                ", streamConsumed=" + streamConsumed +
//                '}';
//    }
}
