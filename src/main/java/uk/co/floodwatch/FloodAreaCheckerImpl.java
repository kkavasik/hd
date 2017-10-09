package uk.co.floodwatch;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FloodAreaCheckerImpl implements FloodAreaChecker {

	private static final Logger LOGGER = Logger.getLogger(FloodAreaChecker.class.getName());
	private static String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
	private static Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

	public FloodRisk isPostcodeInFloodArea(String postcode) {

		try {
			if(!isPostcodeValid(postcode)){
				throw new PostcodeNotFoundException();
			}

			if (postcode.startsWith("L") || postcode.startsWith("l")) {
				return FloodRisk.LOW_RISK;
			}

			if (postcode.startsWith("M") || postcode.startsWith("m")) {
				return FloodRisk.MEDIUM_RISK;
			}

			if (postcode.startsWith("H") || postcode.startsWith("h")) {
				return FloodRisk.HIGH_RISK;
			}
		} catch (FloodAreaCheckerTechnicalFailureException e) {
			LOGGER.log(Level.INFO, "Error in flood area checker");
		}
		return FloodRisk.NO_RISK;
	}

	/**
	 * Checks if the post code is according to UK post codes
	 *
	 * @param postcode
	 * @return true or false is post code is valid
	 * @throws PostcodeNotFoundException if post code is unknown
	 */
	private boolean isPostcodeValid(String postcode) {

		try {
			Matcher matcher = pattern.matcher(postcode);
			return matcher.matches();
		}
		catch (PostcodeNotFoundException e){
			LOGGER.log(Level.INFO, "Error in post code checker");
		}
		return false;
	}
}
