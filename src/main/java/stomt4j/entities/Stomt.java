package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Stomt {
	
	private String id;
	private boolean positive;
	private String text;
	private Images images;
	// TODO: Change type from String to Locale
	private String lang;
	private String created_at;
	private int amountAgreements;
	private int amountComments;
	private Label[] labels;
	private Agreement[] agreements;
	private boolean anonym;
	private Target target;
	private Highlight[] highlights;
	private Target creator;
	// TODO: Expand to url array - Backend does not support at moment
	private String url;
	// only exists if agreed
	private AgreedEntity agreed;
	
	/**
	 * Constructor for stomt-object
	 * 
	 * @param stomt A stomt-object in json.
	 */
	public Stomt (JsonObject stomt) {
		
		if (stomt.has("agreed")) {
			this.agreed = new AgreedEntity(stomt.get("agreed").getAsJsonObject());
			//stomt.remove("agreed");
		}
		
		this.id = stomt.get("id").getAsString();
		this.positive = stomt.get("positive").getAsBoolean();
		this.text = stomt.get("text").getAsString();
		
		this.target = new Target(stomt.getAsJsonObject("target"));
		stomt.remove("target");
		
		
		if (stomt.get("images").isJsonObject()) {
			this.images = new Images(stomt.get("images").getAsJsonObject());
		} else {
			this.images = null;
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
		
		if (anonym == true) {
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

	public String getId() {
		return id;
	}

	public boolean isPositive() {
		return positive;
	}

	public String getText() {
		return text;
	}

	public Images getImages() {
		return images;
	}

	public String getLang() {
		return lang;
	}

	public String getCreated_at() {
		return created_at;
	}

	public int getAmountAgreements() {
		return amountAgreements;
	}

	public int getAmountComments() {
		return amountComments;
	}

	public Label[] getLabels() {
		return labels;
	}

	public Agreement[] getAgreements() {
		return agreements;
	}

	public boolean isAnonym() {
		return anonym;
	}

	public Target getTarget() {
		return target;
	}

	public Highlight[] getHighlights() {
		return highlights;
	}

	public Target getCreator() {
		return creator;
	}
	
	public String getUrl() {
		return url;
	}

	public AgreedEntity getAgreed() {
		return agreed;
	}
	
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
			labelsString = labels.toString();
		}
		
		String agreementsString;
		if (agreements == null) {
			agreementsString = "";
		} else {
			agreementsString = agreements.toString();
		}
		
		String highlightsString;
		if (highlights == null) {
			highlightsString= "";
		} else {
			highlightsString = highlights.toString();
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
	
	public static class AgreedEntity {
		private boolean positive;
		
		public AgreedEntity(JsonObject agreed) {
			this.positive = agreed.get("positive").getAsBoolean();
		}
		
		@Override
		public String toString() {
			return "AgreedEntity [positive=" + positive + "]";
		}
		
	}
}
