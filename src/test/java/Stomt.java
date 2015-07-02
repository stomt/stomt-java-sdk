import java.io.IOException;

import org.apache.http.ParseException;

import stomt4j.StomtClient;
import stomt4j.StomtException;


public class Stomt {
	
	public void createStomt(StomtClient client) throws ParseException, IOException, StomtException {
		
		System.out.println(client.createStomt("stomt", true, "would ABCDEFGH1111111", null, true, null, null));
		
		
		//{"anonym":false,"negative":true,"prefetched":false,"text":"would abcdefgh1234","creator":{"id":"test1337","displayname":"test1337","category":{"id":"users","displayname":"Users"},"images":{"avatar":{"url":"https://test.rest.stomt.com/placeholders/30/4.png","w":30,"h":42}},"ownedTargets":[],"roles":[],"lang":"en","ownedTargetsArray":[]},"target_id":"stomt"}
		
		
	}

}
