package stomt4j.entities;

/**
 * An image has a specific context. This enumeration contains all possibilities.
 *
 * @author Christoph Weidemeyer - c.weidemeyer at gmx.de
 */
public enum ImageContext {
	avatar, cover, stomt, profile;

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
