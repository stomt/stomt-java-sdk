package stomt4j.entities;

import java.util.Locale;
import com.google.gson.JsonObject;

public class Stomt {
	
	private String id;
	private boolean positive;
	private String text;
	// TODO: Change type from String to Locale
	private String lang;
	private String created_at;
	private boolean anonym;

	// optional
	// TODO: Only one picture per stomt?
	private Image images;

	private Target creator;

	private Target target;

	private int amountAgreements;
	private int amountComments;

	// only exists if agreed
	private AgreementEntity agreed;

	private Boolean agreementNegative;
	private String visible_at;

//	public Stomt(String id, boolean negative, String text, Locale lang,
//			String created_at, boolean anonym) {
//
//	}
	
	
//	{"meta":[],"data":{
//		"id":"test2",
//		"positive":false,
//		"text":"would test2.",
//		"images":{"stomt":{"url":"https:\/\/test.rest.stomt.com\/uploads\/42wQ\/s300x300\/42wQX5Ew7845CVBTfWjKOAtIsquCvj0WALaIxcgb_s300x300.png","w":300,"h":300}},
//		"lang":"en",
//		"created_at":"2015-10-05T17:05:38+0000",
//		"amountAgreements":1,
//		"amountComments":0,
//		"labels":[],
//		"agreements":[{"id":"5ctbakD9ApVDdPxegytbbLTPu","positive":true,"user":"test","avatar":{"url":"https:\/\/test.rest.stomt.com\/uploads\/F6uV\/s42x42\/F6uVZHmG3f27j8tYlO5ce0LbdsTbexLYX94Nl9Ji_s42x42.png","w":42,"h":42}}],
//		"anonym":false,
//		"target":{"id":"test2","displayname":"test2","category":{"id":"users","displayname":"Users"},"images":{"avatar":{"url":"https:\/\/test.rest.stomt.com\/placeholders\/30\/4.png","w":30,"h":42}},"verified":true},
//		"highlights":[],
//		"creator":{"id":"test","displayname":"test","category":{"id":"users","displayname":"Users"},"images":{"avatar":{"url":"https:\/\/test.rest.stomt.com\/uploads\/F6uV\/s42x42\/F6uVZHmG3f27j8tYlO5ce0LbdsTbexLYX94Nl9Ji_s42x42.png","w":42,"h":42}},"verified":false},
//		"urls":["www.test.com"],
//		"agreed":{"positive":true,"id":"5ctbakD9ApVDdPxegytbbLTPu"}
//		}}
	
	public Stomt (JsonObject stomt) {
		this.id = stomt.get("id").getAsString();
		this.positive = stomt.get("positive").getAsBoolean();
		this.text = stomt.get("text").getAsString();
		
		if (stomt.get("images").getAsString() == "") {
			this.images = null;
		} else {
			this.images = new Image(stomt.getAsJsonObject("images"));
		}
		
		this.lang = stomt.get("lang").getAsString(); //LocaleUtils.toLocale(stomt.get("lang").getAsString());
		this.created_at = stomt.get("created_at").getAsString();
		this.amountAgreements = stomt.get("amountAgreements").getAsInt();
		this.amountComments = stomt.get("amountComments").getAsInt();
		
		// TODO: implement!
//		this.labels = null;
//		this.agreements = null;
		
		this.anonym = stomt.get("anonym").getAsBoolean();
		this.target = new Target(stomt.getAsJsonObject("target"));
		
		// TODO: implement!
//		this.highlights = null;

		this.creator = new Target(stomt.getAsJsonObject("creator"));
	
		// TODO: implement!
//		this.urls = null;
		
		this.agreed = new AgreementEntity(stomt.getAsJsonObject("agreed"));
	}

	public String getText() {
		return text;
	}

	public String getTargetName() {
		return target.getName();
	}

	public String getPhotoTarget() {
		return target.getAvatarStomt();
	}

	public String getPhotoCreator() {
		return creator.getAvatarStomt();
	}

	public String getCreatorName() {
		return creator.getName();
	}

	public int getAmountComments() {
		return amountComments;
	}

	public void removeAgreement() {
		if (agreed != null) {
			if (agreed.positive) {
				amountAgreements++;
			} else { // positive
				amountAgreements--;
			}
			agreed = null;
		}
	}

	public int getAmountAgreements() {
		return amountAgreements;
	}

	public String getTargetId() {
		return target.getId();
	}

	public String getCreated_at() {
		return created_at;
	}

	public boolean isNegative() {
		return positive;
	}

	public String getCreatorId() {
		return creator.getId();
	}

	public boolean isAgreed() {
		return agreed != null;
	}

	public Boolean isAgreementNegative() {
		if (agreed != null) {
			return agreed.positive;
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public void setAgreement(String id, boolean negative) {
		if (agreed != null) {
			if (agreed.positive) {
				amountAgreements++;
			} else { // positive
				amountAgreements--;
			}
		}

		agreed = new AgreementEntity(id, negative);
		
		if (negative) {
			amountAgreements--;
		} else { // positive
			amountAgreements++;
		}
	}

	public String/*Locale*/ getLang() {
		return lang;
	}

	private class AgreementEntity {
		private String id;
		private boolean positive;
		
		private AgreementEntity (JsonObject agreed) {
			this.id = agreed.get("id").getAsString();
			this.positive = agreed.get("positive").getAsBoolean();
		}

		private AgreementEntity (String id, boolean positive) {
			this.id = id;
			this.positive = positive;
		}
	}
}
