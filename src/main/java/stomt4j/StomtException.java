package stomt4j;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class StomtException extends Throwable {

	public StomtException(String s) {
		System.out.println(s);
	}

	public StomtException(JsonObject o) {
		JsonElement error = o.get("error");
		System.out.println(error.toString());
	}

}
