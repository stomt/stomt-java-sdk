package stomt4j.auth;

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
}
