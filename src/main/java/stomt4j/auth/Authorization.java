package stomt4j.auth;

/**
 * Handles the Accesstoken and Refreshtoken of each Connection.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Authorization {
	
    private String accesstoken;
    private String refreshtoken;
    
    /**
     * Constructor Authorization as data wrapper.
     */
    public Authorization() {
    	accesstoken = null;
    	refreshtoken = null;
    }

    /**
     * Constructor Authorization as data wrapper.
     * 
     * @param access The current {@code accesstoken}
     * @param refresh The current {@code refreshtoken}
     */
    public Authorization(String access, String refresh) {
        this.accesstoken = access;
        this.refreshtoken = refresh;
    }
    
    /**
     * Check if instance has set {@code accesstoken}.
     * 
     * @return {@code true} if instance has set an {@code accestoken}
     */
    public boolean hasAccesstoken() {
		return this.accesstoken != null;
    }

    /**
     * @param token Set {@code accesstoken}
     */
    public void setAccesstoken(String token) {
    	accesstoken = token;
    }
    
    /**
     * @return {@code accesstoken}
     */
    public String getAccesstoken() {
    	return this.accesstoken;
    }

    /**
     * @param token Set {@code refreshtoken}

     */
    public void setRefreshtoken(String token) {
    	refreshtoken = token;
    }
    
    /**
     * @return The {@code refreshtoken}
     */
    public String getRefreshtoken() {
    	return this.refreshtoken;
    }
    
    public boolean isEnabled() {
		return accesstoken != null;
	}
    
	/**
	 * A toString() method for {@link Authorization}.
	 * 
     * @return {@link Authorization} as String
     */
    @Override
    public String toString() {
    	String auth = "[Accesstoken: ";
    	if(accesstoken == null) {
    		auth += "null; Refreshtoken: ";
    	} else {
    		auth += "not null; Refreshtoken: ";
    	}
    	if(refreshtoken == null) {
    		auth += "null]";
    	} else {
    		auth += "not null]";
    	}
    	return auth;
    }
}
