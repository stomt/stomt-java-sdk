package stomt4j;

import static org.junit.Assert.*;

import java.io.IOException;
import org.apache.http.ParseException;
import org.junit.Test;

import stomt4j.entities.Stomt;

public class ReadStomt {
	
	String stomt_id = "java-sdk-test-81637";
	String stomt = "Java-SDK test 81637";

	@Test
	public void readStomt_exists() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: readStomt_exists() - stomt exists");
		Stomt response = StomtClientTest.client.readStomt(stomt_id);
		String expect = "Stomt [id=" + stomt_id + ", positive=false, text=" + stomt + ", images=Images [avatar=, stomt=, profile=], lang=en, created_at=" + response.getCreated_at() + ", amountAgreements=1, amountComments=0, labels=, agreements=, anonym=false, target=" + response.getTarget().toString() + ", creator=" + response.getCreator().toString() + ", url=, agreed=]";

		
		System.out.println("Expect: " + expect);
		System.out.println("Get: " + response.toString());

		assertEquals(response.getText(), stomt);
	}
	
	@Test(expected=StomtException.class)
	public void readStomt_notExists() throws ParseException, IOException, StomtException {
		System.out.println("->TEST: readStomt_exists() - stomt does not exist");
		
		System.out.println("Expect: No query results for model [Stomt\\Components\\Stomt\\Models\\Stomt].");
		System.out.print("Get: ");
		StomtClientTest.client.readStomt("java123456789123456789");
	}

}
