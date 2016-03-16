package stomt4j.entities;

/**
 * The entity of a GPS-object.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class LonLat {
	
	private float latitude;
	private float longitude;

	/**
	 * Constructor for GPS coordinates.
	 * 
	 * @param latitude Latitude of GPS-object
	 * @param longitude Longitude of GPS-object
	 */
	public LonLat(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return The latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @return The longitude
	 */
	public float getLongitude() {
		return longitude;
	}
	
	/**
	 * Represents String representation of LonLat-Object.
	 * 
	 * @return The String representation
	 */
	@Override
	public String toString() {
		return "LonLat [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
