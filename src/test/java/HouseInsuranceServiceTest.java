import gr.hd.houseinsurance.HouseInsuranceService;
import gr.hd.houseinsurance.HouseInsuranceServiceImpl;
import uk.co.floodwatch.PostcodeNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

import java.math.BigDecimal;

public class HouseInsuranceServiceTest {

	HouseInsuranceService houseInsuranceService = new HouseInsuranceServiceImpl();

	@Test(expected = PostcodeNotFoundException.class)
	public void getCalculatedPremiumWithNullPostCodeShouldThrowException() throws SubsidenceAreaCheckerTechnicalFailureException {
		houseInsuranceService.getCalculatedPremium(null, 1, false);
	}

	@Test(expected = SubsidenceAreaCheckerTechnicalFailureException.class)
	public void getCalculatedPremiumWithInvalidPostCodeShouldThrowException() throws SubsidenceAreaCheckerTechnicalFailureException {
		houseInsuranceService.getCalculatedPremium("Z1A 0B1", 1, false);
	}

	@Test()
	public void getCalculatedPremiumWithThatchedRoofShouldReturnZero() throws SubsidenceAreaCheckerTechnicalFailureException {
		BigDecimal premium = houseInsuranceService.getCalculatedPremium("PO16 7GZ", 1, true);
		Assert.assertEquals(BigDecimal.ZERO, premium);
	}

	@Test()
	public void getCalculatedPremiumWithPostCodeInSubsidenceAreaShouldReturnZeroAmount() throws SubsidenceAreaCheckerTechnicalFailureException {
		BigDecimal premium = houseInsuranceService.getCalculatedPremium("SO16 7GZ", 1, true);
		Assert.assertEquals(BigDecimal.ZERO, premium);
	}

	@Test()
	public void getCalculatedPremiumWithMoreThanFourBedroomsShouldReturnZeroAmount() throws SubsidenceAreaCheckerTechnicalFailureException {
		BigDecimal premium = houseInsuranceService.getCalculatedPremium("PO16 7GZ", 6, false);
		Assert.assertEquals(BigDecimal.ZERO, premium);
	}

	@Test()
	public void getCalculatedPremiumWithLowFloodRiskShouldReturnPremiumAmount() throws SubsidenceAreaCheckerTechnicalFailureException {
		BigDecimal premium = houseInsuranceService.getCalculatedPremium("LO16 7GZ", 2, false);
		Assert.assertEquals(BigDecimal.valueOf(110), premium);
	}

	@Test()
	public void getCalculatedPremiumWithMediumFloodRiskShouldReturnPremiumAmount() throws SubsidenceAreaCheckerTechnicalFailureException {
		BigDecimal premium = houseInsuranceService.getCalculatedPremium("MO16 7GZ", 2, false);
		Assert.assertEquals(BigDecimal.valueOf(126.50), premium);
	}

	@Test()
	public void getCalculatedPremiumWithHighFloodRiskShouldReturnZeroAmount() throws SubsidenceAreaCheckerTechnicalFailureException {
		BigDecimal premium = houseInsuranceService.getCalculatedPremium("HO16 7GZ", 2, false);
		Assert.assertEquals(BigDecimal.ZERO, premium);
	}








}
