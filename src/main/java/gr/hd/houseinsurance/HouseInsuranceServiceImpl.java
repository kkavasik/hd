package gr.hd.houseinsurance;

import uk.co.floodwatch.FloodAreaChecker;
import uk.co.floodwatch.FloodAreaCheckerImpl;
import uk.co.floodwatch.FloodAreaCheckerTechnicalFailureException;
import uk.co.floodwatch.PostcodeNotFoundException;
import uk.co.subsidencewatch.SubsidenceAreaChecker;
import uk.co.subsidencewatch.SubsidenceAreaCheckerImpl;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HouseInsuranceServiceImpl implements HouseInsuranceService {

	private static final Logger LOGGER = Logger.getLogger(HouseInsuranceService.class.getName());

	private String message = "Insurance is not available";
	private SubsidenceAreaChecker subsidenceAreaChecker = new SubsidenceAreaCheckerImpl();
	private FloodAreaChecker floodAreaChecker = new FloodAreaCheckerImpl();

	/**
	 * Calculates the base annual premium for a house
	 *
	 * @param postcode,numBedrooms,hasThatchedRoof
	 * @return BigDecimal the base annual premium , Zero is returned in any case of no
	 * insurance and a warning message is logged
	 * @throws FloodAreaCheckerTechnicalFailureException if any technical fault occurs
	 * @throws PostcodeNotFoundException                 if the postcode is unknown
	 */
	public BigDecimal getCalculatedPremium(String postcode, int numBedrooms, boolean hasThatchedRoof)
					throws SubsidenceAreaCheckerTechnicalFailureException, PostcodeNotFoundException {

		if (postcode == null) {
			LOGGER.log(Level.WARNING, message);
			throw new PostcodeNotFoundException();
		}

		//number of bedroom validations and thatched roof
		if (numBedrooms > 4 || numBedrooms < 0 || hasThatchedRoof) {
			LOGGER.log(Level.WARNING, message);
			return BigDecimal.ZERO;
		}
		if (subsidenceAreaChecker.isPostcodeInSubsidenceArea(postcode) == null) {
			LOGGER.log(Level.WARNING, message);
			throw new SubsidenceAreaCheckerTechnicalFailureException();
		}

		if (subsidenceAreaChecker.isPostcodeInSubsidenceArea(postcode)) {
			LOGGER.log(Level.WARNING, message);
			return BigDecimal.ZERO;
		}

		return calculateFloodAreaPremium(postcode);
	}

	/**
	 * Calculates the premium for a house based on the flood risk for that area
	 *
	 * @param postcode
	 * @return BigDecimal the base annual premium
	 */
	private BigDecimal calculateFloodAreaPremium(String postcode) {

		BigDecimal premium = BigDecimal.ZERO;

		switch (floodAreaChecker.isPostcodeInFloodArea(postcode)) {
		case NO_RISK:
		case LOW_RISK:
			premium = BigDecimal.valueOf(110);
			break;
		case MEDIUM_RISK:
			premium = BigDecimal.valueOf(126.50);
			break;
		case HIGH_RISK:
			premium = BigDecimal.ZERO;
			LOGGER.warning(message);
			break;
		}
		return premium;
	}

}
