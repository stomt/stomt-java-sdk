package stomt4j;

import org.apache.http.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class SuggestUsernames {

	@Test
	public void suggestUsernames() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: suggestUsernames()");
		ArrayList<String> suggestions = StomtClientTest.client.suggestUsernames("test");
		
		if (suggestions == null) {
			throw new StomtException("suggestions is null.");
		}

		for (String suggestion : suggestions) {
			if (suggestion == null || suggestion.getClass() != String.class) {
				throw new StomtException("suggestion is null or not type String.");
			}
		}
	}

}
