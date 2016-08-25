package stomt4j;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class StomtException extends Throwable {

	/**
	 * Quick fix for {@code interface Serializable} - implemented by {@code class Throwable}
	 */
	private static final long serialVersionUID = 1L;

	public StomtException(String exception) {
		System.out.println(exception);
	}

	public StomtException(JsonObject o) {
		JsonElement error = o.get("error");
		System.out.println(error.toString());
	}

}
