package stomt4j;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class RegisterAnUser {
	
	// TODO: Check for valid Email (Status = 400 - Message = E-Mail invalid.)
	
//	private String username;
//	private String email;
//	private String target;
//	private String expected;
//	private StomtClient testClient;
//	private double random = Math.random() * 100000;
//	private String randomAsString = Integer.toString((int) random);
//	
//	@Test
//	public void registerAnUserAccept() throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: Register an user - accept.");
//		
//		testClient = new StomtClient(StomtClientTest.appid);
//		
//		username = "test" + randomAsString;
//		email = random + "@web.de";
//		expected = "Target [id=" + username
//				+ ", images=null, ownedTargets=null, email=null, name=null, lang=null, influencestatus=null, roles=null, category=Category [id=users, displayname=Users], amIFollowing=false, details=null, type=null, stomts=null, stats=null, displayname="
//				+ username + ", follows=null, follower=null, influencelevel=0, memberSince=null]";
//
//		System.out.println("Expect: " + expected);
//		
//		target = testClient.registerAnUser(username, email, username, username).toString();
//		
//		System.out.println("Get: " + target);
//
//		assertEquals(expected, target);
//		reset();
//	}
//
//	// Username already exists
//	@Test(expected = StomtException.class)
//	public void registerAnUserFailExist() throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: Register an user - fail: Username already exists.");
//
//		testClient = new StomtClient(StomtClientTest.appid);
//		username = "test";
//		email = random + username + "@web.de";
//		expected = "Status = 409 - Message = Username already exists.";
//
//		System.out.println("Expect: " + expected);
//
//		target = testClient.registerAnUser(username, email, username, username).toString();
//		System.out.println("Get: " + target);
//
//		assertEquals(expected, target);
//		reset();
//	}
//
//	// Wrong parameters
//	@Test(expected = StomtException.class)
//	public void registerAnUserFailIllegal() throws ParseException, IOException, StomtException {
//		System.out.println("-> TEST: Register an user - fail: Bad Request.");
//
//		testClient = new StomtClient(StomtClientTest.appid);
//		email = random + "123456789" + "@web.de";
//		expected = "Status = 400 - Message = Bad request: displayname does not exist.";
//
//		System.out.println("Expect: " + expected);
//
//		target = testClient.registerAnUser(username, email, username, username).toString();
//		System.out.println("Get: " + target);
//
//		assertEquals(expected, target);
//		reset();
//	}
//	
//	private void reset() {
//		username = null;
//		email = null;
//		target = null;
//		expected = null;
//		testClient = null;
//	}

}
