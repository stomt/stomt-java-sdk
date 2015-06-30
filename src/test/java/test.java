import java.io.IOException;

import org.apache.http.ParseException;

import stomt4j.*;

public class test {

	public static void main(String[] args) throws ParseException, IOException, StomtException {

		// Set 'appid'
		String appid = "stzmtmrEU3Kp42XJbX2eYUsYE";

		// Create StomtClient
		StomtClient client = new StomtClient(appid);

		// Authentification

		/*
		 * Register an user
		 */

		double random = Math.random() * 100000;
		String randomnum = Integer.toString((int) random);

		String username = "test" + randomnum;
		String email = username + "@iwas.de";

		System.out.println("--- Register an user ---");
		// System.out.println(client.registerAnUser("test", "test", "test",
		// "test"));
		System.out.println(client.registerAnUser(username, email, username, username).toString());

		/*
		 * Verify E-Mail
		 */

		System.out.println("--- Verify email ---");
		System.out.println(client.verifyEmail("21312", "124124"));

		/*
		 * Login
		 */

		// normal
		System.out.println("--- normal login ---");
		System.out.println(client.login("testd3d", "test3d23d"));
		System.out.println(client.login("test", "test"));

		System.out.println(client.getAuthorization().getAccesstoken());
		System.out.println(client.getAuthorization().getRefreshtoken());

		/*
		 * Login FB
		 */

		/*
		 * Refresh access token
		 */

		// client.getAuthorization().setAccesstoken("ex6HxIjtoL69hXoFlcNNMx10iHAAbYAQ0tRbCfY5");
		// client.getAuthorization().setRefreshtoken("9EqzRnxy6D2WReJ5Uo6bxLVKXTYn96XyaJsBPQd8");

		System.out.println("--- Refresh accesstoken ---");
		System.out.println(client.refreshAccessToken());

		/*
		 * Check access token
		 */

		// System.out.println("--- Check accesstoken ---");
		// System.out.println(client.checkAccessToken());

		/*
		 * Logout
		 */

		// System.out.println("--- Logout ---");
		// System.out.println(client.logout());

		/*
		 * Check Availability
		 */
		System.out.println("--- Check Availability ---");

		System.out.println(client.checkAvailability("username", "test213"));
		System.out.println(client.checkAvailability("username", "test"));

		/*
		 * Forgot password
		 */

		// 403 wenn accesstoken gesetzt ist!
		System.out.println("--- Forgot Password ---");

		System.out.println(client.forgotPassword("test89811198"));
		System.out.println(client.forgotPassword("test1"));

		/*
		 * Reset password
		 */

		System.out.println("--- Reset Password ----------------------");

		System.out.println(client.resetPassword("resetcode", "newPassword"));

	}

}
