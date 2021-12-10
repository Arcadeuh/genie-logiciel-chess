package com.nullprogram.chess.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Serves cached images of requsted size.
 *
 * This will cache the recent requests so it's not hitting the disk every time
 * the display needs an image.
 */
public final class ImageServer {

	/** This class's Logger. */
	private static final Logger LOG = Logger.getLogger("com.nullprogram.chess.view.ImageServer");

	/** The image cache. */
	private static Map<String, Image> cache = new HashMap<>();

	/**
	 * Hidden constructor.
	 */
	private ImageServer() {
	}

	/**
	 * Return named image scaled to given size.
	 *
	 * @param name name of the image
	 * @return the requested image
	 */
	public static Image getTile(final String name) {
		Image cached = cache.get(name);
		if (cached != null) {
			return cached;
		}

		String directoryName = System.getProperty("user.dir");
		String fileSeparator = System.getProperty("file.separator");
		String file = directoryName + fileSeparator + "res" + fileSeparator + "img_png" + fileSeparator + name
				+ ".png";

		try {
			Image i = ImageIO.read(new File(file));
			cache.put(name, i);
			return i;
		} catch (java.io.IOException e) {
			String message = "Failed to read image: " + file + ": " + e;
			LOG.severe(message);
			System.exit(1);
		} catch (IllegalArgumentException e) {
			String message = "Failed to find image: " + file + ": " + e;
			LOG.severe(message);
			System.exit(1);
		}
		return null;
	}
}
