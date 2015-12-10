package stomt4j.entities;

import com.google.gson.JsonObject;

public class Image {
	
	String context = null;
	String name = null;
//	String data = null;
	String url = null;
	String thumb = null;
//	int width = -1;
//	int height = -1;
	
	
	// Reason: Unit Test
	public Image() {
	}
	
	public Image (JsonObject image) {
		
		if (image.has(ImageContext.avatar.toString())) {
			this.context = "avatar";
		} else if (image.has(ImageContext.cover.toString())) {
			this.context = "cover";
		} else if (image.has(ImageContext.stomt.toString())) {
			this.context = "stomt";
		}
		
		JsonObject contextAsJson = image.get(context).getAsJsonObject();
		
		this.name = contextAsJson.get("name").getAsString();
		
		if (image.has("thumb")) {
			this.thumb = contextAsJson.get("thumb").getAsString();
		}
		
		this.url = contextAsJson.get("url").getAsString();
	}

	public String getContext() {
		return context;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getThumb() {
		return thumb;
	}
	
	public void setContext(String context) {
		this.context = context;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	@Override
	public String toString() {
		
		String contextString;
		if (context == null) {
			contextString = "";
		} else {
			contextString = context;
		}
		
		String nameString;
		if (name == null) {
			nameString = "";
		} else {
			nameString = name;
		}
		
		String urlString;
		if (url == null) {
			urlString = "";
		} else {
			urlString = url;
		}
		
		String thumbString;
		if (thumb == null) {
			thumbString= "";
		} else {
			thumbString = thumb;
		}
		
		return "Image [context=" + contextString + ", name=" + nameString + ", url=" + urlString + ", thumb=" + thumbString + "]";
	}

}