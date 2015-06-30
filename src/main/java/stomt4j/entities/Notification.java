package stomt4j.entities;

/**
 * Representation of the Notification Entity
 * @author Christoph Weidemeyer
 */
public class Notification {
	
	private String id;
	private String text;
	private Goal goal;
	private long created_at;	// Timestamp in ISO 8601
	private boolean seen;
	private boolean clicked;

	/**
	 * Constructor for PUT-Request Update Notifications
	 * 
	 * @param id Identifier for a Notification
	 * @param seen {@code false} if the Notification is unseen | {@code true} if the Notification is seen
	 * @param clicked {@code false} if the Notification is unclicked | {@code true} if the Notification is clicked
	 */
	public Notification(String id, boolean seen, boolean clicked) {
		this.id = id;
		this.seen = seen;
		this.clicked = clicked;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	private class Goal {
		private String id;
		private String type;	// 'stomt' or 'target'
		private Target target;	// target object if type is stomt
		// ... other target or stomt data
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getType() {
			return type;
		}
		
		public void setType(String type) {
			this.type = type;
		}
		
		public Target getTarget() {
			return target;
		}
		
		public void setTarget(Target target) {
			this.target = target;
		}
		
	}
}
