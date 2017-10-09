package gr.hd.houseinsurance;

import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

import java.math.BigDecimal;

public interface HouseInsuranceService
{
  public BigDecimal getCalculatedPremium(String postcode, int numBedrooms, boolean hasThatchedRoof)
                  throws SubsidenceAreaCheckerTechnicalFailureException;
}