package stomt4j.entities;

import com.google.gson.JsonObject;

public class Agreed {
	private boolean positive;

	/**
	 * Constructor - used to handle Stomt-Objects in json-format.
	 *
	 * @param agreed The AgreedEntity as Json-Object
	 */
	public Agreed(JsonObject agreed) {
		this.positive = agreed.get("positive").getAsBoolean();
	}

	/**
	 * A toString() method for the agreed entity - used for unit tests.
	 *
	 * @return A String representation of the agreed entity
	 */
	@Override
	public String toString() {
		return "Agreed [positive=" + positive + "]";
	}

}