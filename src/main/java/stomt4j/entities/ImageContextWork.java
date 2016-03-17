package stomt4j.entities;

/**
 * For the enumeration {@code ImageContext.java} needed methods.
 * 
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public class ImageContextWork {
	
	/**
	 * Check if enumeration contains String {@code context}.
	 * 
	 * @param context The String this method has to test for an valid image context
	 * @return {@code true} if the Enumeration {@code ImageContext.java} contains the String {@code context}, otherwise {@code false}
	 */
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
