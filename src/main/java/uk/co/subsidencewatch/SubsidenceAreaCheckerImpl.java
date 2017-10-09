package uk.co.subsidencewatch;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubsidenceAreaCheckerImpl implements SubsidenceAreaChecker {

	private static String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
	private static Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	private static final Logger LOGGER = Logger.getLogger(SubsidenceAreaChecker.class.getName());


	public Boolean isPostcodeInSubsidenceArea(String postcode) throws SubsidenceAreaCheckerTechnicalFailureException {

		Matcher matcher = pattern.matcher(postcode);
		if (!matcher.matches()) {
			return null;
		}
		return postcode.startsWith("S") || postcode.startsWith("s");
	}

}
