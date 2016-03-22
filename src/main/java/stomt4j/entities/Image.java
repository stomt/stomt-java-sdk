package stomt4j.entities;

import com.google.gson.JsonObject;

/**
 * The entity Image.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class Image {
	
	private String url = null;
	private String name = null;
	private int width = -1;
	private int height = -1;
	private String context = null;
	private String thumb = null;
	
	/**
	 * Default Constructor for image-object
	 */
	public Image() {	
	}
	
	/**
	 * Constructor for image-object
	 * 
	 * @param image A image-object in json.
	 */
	public Image(JsonObject image) {
		if (image.has("url")) {
			
			this.url= image.get("url").getAsString();
		}
		if (image.has("w")) {
			this.width= image.get("w").getAsInt();
		}
		if (image.has("h")) {
			this.height= image.get("h").getAsInt();
		}
		
		if (image.has("name")) {
			this.name = image.get("name").getAsString();
		}

		if (image.has("thumb")) {
			this.thumb = image.get("thumb").getAsString();
		}
	}

	/**
	 * @return The image URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return The width of the image
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return The height of the image
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return The context of the image (cover, stomt, profile, avatar - cf. ImageContext.java)
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @return The name of the image - needed to append it to a stomt
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The URL of the thumbnail - only if it exists (e.g. for GIFs)
	 */
	public String getThumb() {
		return thumb;
	}
	
	/**
	 * Used for unit tests
	 * @param url The image URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param context The context of the image (cover, stomt, profile, avatar - cf. ImageContext.java)
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @param name The name of the image - needed to append it to a stomt
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param thumb The URL of the thumbnail - only if it exists (e.g. for GIFs)
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	/**
	 * An asString() method for the entity Image - used for unit tests.
	 * 
	 * @return An image object as String.
	 */
	public String asString() {
		return "Image [url=" + url + ", width=" + width + ", height=" + height + ", name=" + name + ", context=" + context + ", thumb=" + thumb +"]";
	}
	
	
	
	/**
	 * A toString() method for the entity Image - used for unit tests.
	 * 
	 * @return A condensed form of the image object as String representation.
	 */
	@Override
	public String toString() {
		return "Image [url=" + url + ", width=" + width + ", height=" + height + "]";
	}
	
}