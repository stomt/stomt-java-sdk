package stomt4j.auth;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Authorization {
	
    private String accesstoken;
    private String refreshtoken;
    
    public Authorization() {
    	accesstoken = null;
    	refreshtoken = null;
    }

    public Authorization(String access, String refresh) {
        this.accesstoken = access;
        this.refreshtoken = refresh;
    }
    
    public boolean hasAccesstoken() {
    	if (this.accesstoken == null) {
    		return false;
    	} else {
    		return true;
    	}
    }

    public void setAccesstoken(String token) {
    	accesstoken = token;
    }
    
    public String getAccesstoken() {
    	return this.accesstoken;
    }

    public void setRefreshtoken(String token) {
    	refreshtoken = token;
    }
    
    public String getRefreshtoken() {
    	return this.refreshtoken;
    }
    
    public boolean isEnabled() {
    	if (accesstoken == null) {
    		return false;
    	}
    	return true;
    }
    
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
