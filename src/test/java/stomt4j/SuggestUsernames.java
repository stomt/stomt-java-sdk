package stomt4j;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.ParseException;
import org.junit.Test;

public class SuggestUsernames {

	@Test
	public void suggestUsernames() throws ParseException, IOException, StomtException {
		ArrayList<String> suggestions = StomtClientTest.client.suggestUsernames("test");
		
		if (suggestions == null) {
			throw new StomtException("suggestions is null.");
		}
		
		for (int i = 0; i < suggestions.size(); i++) {
			if (suggestions.get(i) == null || suggestions.get(i).getClass() != String.class) {
				throw new StomtException("suggestion is null or not type String.");
			}
			System.out.println(suggestions.get(i));
		}
	}

}
