package stomt4j;

import com.google.gson.JsonObject;

public class StomtException extends Throwable {
		
	public StomtException(String s) {
		System.out.println(s);
	}
	
	public StomtException(JsonObject o) {
		
		JsonObject error = o.getAsJsonObject("error");
		
		System.out.println("Status = " + error.get("status").getAsString() 
				+ " - Message = " + error.get("message").getAsString());
	}

}
