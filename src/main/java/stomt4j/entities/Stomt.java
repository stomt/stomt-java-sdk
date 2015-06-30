package stomt4j.entities;
import java.util.Locale;

public class Stomt {
	
	private String id;
	private boolean negative;
	private String text;
	private Locale lang;
	private String created_at;
	private boolean anonym;

	private Image[] images;

	private Target creator;

	private Target target;

	private int amountAgreements;
	private int amountComments;

	private AgreementEntity agreed;

	private Boolean agreementNegative;
	private String visible_at;

	public Stomt(String id, boolean negative, String text, Locale lang,
			String created_at, boolean anonym) {

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
			if (agreed.negative) {
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
		return negative;
	}

	public String getCreatorId() {
		return creator.getId();
	}

	public boolean isAgreed() {
		return agreed != null;
	}

	public Boolean isAgreementNegative() {
		if (agreed != null) {
			return agreed.negative;
		}

		return null;
	}

	public String getId() {
		return id;
	}

	public void setAgreement(String id, boolean negative) {
		if (agreed != null) {
			if (agreed.negative) {
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

	public Locale getLang() {
		return lang;
	}

	private class AgreementEntity {
		private String id;
		private boolean negative;

		private AgreementEntity(String id, boolean negative) {
			this.id = id;
			this.negative = negative;
		}
	}
}
