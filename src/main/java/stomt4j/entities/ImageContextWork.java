package stomt4j.entities;

public class ImageContextWork {
	
	public static boolean contains(String context) {
		
		if (context == null) {
			return false;
		}

	    for (ImageContext c : ImageContext.values()) {
	        if (c.name().equals(context)) {
	            return true;
	        }
	    }
	    return false;
	}

}
