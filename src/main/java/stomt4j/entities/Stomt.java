package stomt4j.entities;

import com.google.gson.JsonObject;

import java.util.Arrays;

/**
 * The entity Stomt.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Stomt {
	
	private String id = null;
	private boolean positive;
	private String text = null;
	private Images images = null;
	// TODO: Change type from String to Locale
	private String lang = null;
	private String created_at = null;
	private int amountAgreements = -1;
	private int amountComments = -1;
	private Label[] labels = null;
	private Agreement[] agreements = null;
	private boolean anonym = false;
	private Target target = null;
	private Highlight[] highlights = null;
	private Target creator = null;
	private String url = null;
	private Agreed agreed = null;	// only exists if agreed
	
	/**
	 * Constructor for stomt-objects
	 * 
	 * @param stomt A stomt-object in json-format
	 */
	public Stomt (JsonObject stomt) {
		
		if (stomt.has("agreed")) {
			this.agreed = new Agreed(stomt.get("agreed").getAsJsonObject());
			//stomt.remove("agreed");
		}
		
		this.id = stomt.get("id").getAsString();
		this.positive = stomt.get("positive").getAsBoolean();
		this.text = stomt.get("text").getAsString();
		
		this.target = new Target(stomt.getAsJsonObject("target"));
		stomt.remove("target");
		
		
		if (stomt.get("images").isJsonObject()) {
			this.images = new Images(stomt.get("images").getAsJsonObject());
		}
		
		this.lang = stomt.get("lang").getAsString();
		this.created_at = stomt.get("created_at").getAsString();
		this.amountAgreements = stomt.get("amountAgreements").getAsInt();
		this.amountComments = stomt.get("amountComments").getAsInt();
		
		// TODO: implement!
		this.labels = null;
		// TODO: implement!
		this.agreements = null;
		
		this.anonym = stomt.get("anonym").getAsBoolean();
		
		if (anonym) {
			this.creator = null;
		} else {
			this.creator = new Target(stomt.getAsJsonObject("creator"));
		}
		
		// TODO: implement!
		this.highlights = null;
	
		if (stomt.has("urls")) {
			this.url = stomt.get("urls").getAsString();
		}
	}

	/**
	 * @return The stomt identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return true for 'I like' and false for 'I wish'
	 */
	public boolean isPositive() {
		return positive;
	}

	/**
	 * @return The text of the stomt
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return An instance of the images collection
	 */
	public Images getImages() {
		return images;
	}

	/**
	 * @return The language of the stomt
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @return The timestamp of the stomt
	 */
	public String getCreated_at() {
		return created_at;
	}

	/**
	 * @return The amount of agreements of the stomt
	 */
	public int getAmountAgreements() {
		return amountAgreements;
	}

	/**
	 * @return The amount of comments of the stomt
	 */
	public int getAmountComments() {
		return amountComments;
	}

	/**
	 * @return The labels of the stomt as array
	 */
	public Label[] getLabels() {
		return labels;
	}

	/**
	 * @return The agreements of the stomt as array
	 */
	public Agreement[] getAgreements() {
		return agreements;
	}

	/**
	 * @return true if the stomt is anonym, otherwise false
	 */
	public boolean isAnonym() {
		return anonym;
	}

	/**
	 * @return The target of the stomt
	 */
	public Target getTarget() {
		return target;
	}

	/**
	 * @return The highlights of the stomt
	 */
	public Highlight[] getHighlights() {
		return highlights;
	}

	/**
	 * @return The creator of the stomt
	 */
	public Target getCreator() {
		return creator;
	}
	
	/**
	 * @return The added link of the stomt
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return The agreed-entity of the stomt
	 */
	public Agreed getAgreed() {
		return agreed;
	}
	
	/**
	 * A toString() method for the entity Stomt - used for unit tests.
	 * 
	 * @return A String representation of the stomt entity
	 */
	@Override
	public String toString() {
		
		String imagesString;
		if (images == null) {
			imagesString = "";
		} else {
			imagesString = images.toString();
		}
		
		String labelsString;
		if (labels == null) {
			labelsString = "";
		} else {
			labelsString = Arrays.toString(labels);
		}
		
		String agreementsString;
		if (agreements == null) {
			agreementsString = "";
		} else {
			agreementsString = Arrays.toString(agreements);
		}
		
		String highlightsString;
		if (highlights == null) {
			highlightsString= "";
		} else {
			highlightsString = Arrays.toString(highlights);
		}
		
		String creatorString;
		if (creator == null) {
			creatorString = "";
		} else {
			creatorString = creator.toString();
		}
		
		String agreedString;
		if (agreed == null) {
			agreedString = "";
		} else {
			agreedString = agreed.toString();
		}
		
		String urlString;
		if (url == null) {
			urlString = "";
		} else {
			urlString = url;
		}
		
		return "Stomt [id=" + id + ", positive=" + positive + ", text=" + text + ", images=" + imagesString + ", lang="
				+ lang + ", created_at=" + created_at + ", amountAgreements=" + amountAgreements + ", amountComments="
				+ amountComments + ", labels=" + labelsString + ", agreements=" + agreementsString + ", anonym="
				+ anonym + ", target=" + target.toString() + ", highlights=" + highlightsString + ", creator="
				+ creatorString + ", url=" + urlString + ", agreed=" + agreedString + "]";
	}

}
