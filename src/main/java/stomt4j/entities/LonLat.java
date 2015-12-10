package stomt4j.entities;

/**
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class LonLat {
	
	private float latitude;
	private float longitude;

	/**
	 * Constructor for GPS coordinates.
	 * 
	 * @param latitude Latitude of GPS-object.
	 * @param longitude Longitude of GPS-object.
	 */
	public LonLat(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Getter for the latitude.
	 * 
	 * @return The latitude.
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Getter for the longitude.
	 * 
	 * @return The longitude.
	 */
	public float getLongitude() {
		return longitude;
	}
	
	/**
	 * Represents String representation of LonLat-Object.
	 * 
	 * @return The String representation.
	 */
	@Override
	public String toString() {
		return "LonLat [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
