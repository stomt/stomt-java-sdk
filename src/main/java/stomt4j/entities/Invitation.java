package stomt4j.entities;

public class Invitation {
	
	private String code;
	private Object invited_to;
	private String invited_to_type;
	private boolean invited_as_owner;
	private Target invited_by;
	private boolean used;
	private Target used_by;	// if not used = null
	
	public Invitation(String invite_to_type, boolean invite_as_owner) {
		this.invited_to_type = invite_to_type;
		this.invited_as_owner = invite_as_owner;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Object getInvited_to() {
		return invited_to;
	}
	
	public void setInvited_to(Object invited_to) {
		this.invited_to = invited_to;
	}
	
	public String getInvited_to_type() {
		return invited_to_type;
	}
	
	public void setInvited_to_type(String invited_to_type) {
		this.invited_to_type = invited_to_type;
	}
	
	public boolean isInvited_as_owner() {
		return invited_as_owner;
	}
	
	public void setInvited_as_owner(boolean invited_as_owner) {
		this.invited_as_owner = invited_as_owner;
	}
	
	public Target getInvited_by() {
		return invited_by;
	}
	
	public void setInvited_by(Target invited_by) {
		this.invited_by = invited_by;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public Target getUsed_by() {
		return used_by;
	}
	
	public void setUsed_by(Target used_by) {
		this.used_by = used_by;
	}

}
