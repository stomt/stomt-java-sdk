package stomt4j.entities;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Report {
	
	private String report_type;		// Possible: target, stomt, comment
	private String report_type_id;	// ID of report_type
	private String report_weight;	// Possible: spam, annoying, inappropriate

	public Report(String report_type, String report_type_id, String report_weight) {
		this.report_type = report_type;
		this.report_type_id = report_type_id;
		this.report_weight = report_weight;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getReport_type_id() {
		return report_type_id;
	}

	public void setReport_type_id(String report_type_id) {
		this.report_type_id = report_type_id;
	}

	public String getReport_weight() {
		return report_weight;
	}

	public void setReport_weight(String report_weight) {
		this.report_weight = report_weight;
	}
}
