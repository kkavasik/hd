Feature: Venture into house insurance for UK market

  Background: Configuration steps

  Scenario: Postcode is not in UK post codes
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | Z1A 0B1  |     1          | No            | No                 | NO_RISK         |
    When I make a request for house insurance in UK market
    Then Insurance is not available

  Scenario: House has thatched roof
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | PO16 7GZ |     1          | Yes           | No                 | NO_RISK         |
    When I make a request for house insurance in UK market
    Then Insurance is not available

  Scenario: House is in subsidence area
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | SO16 7GZ |     1          | No            | Yes                | NO_RISK         |
    When I make a request for house insurance in UK market
    Then Insurance is not available

  Scenario: House is in flood area with low risk
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | LO16 7GZ |     1          | No            | No                 | LOW_RISK        |
    When I make a request for house insurance in UK market
    Then Insurance base annual premium is 110₤

  Scenario: House is in flood area with medium risk
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | MO16 7GZ |     1          | No            | No                 | MEDIUM_RISK     |
    When I make a request for house insurance in UK market
    Then Insurance base annual premium is 126.50₤

  Scenario: House is in flood area with high risk
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | HO16 7GZ |     1          | No            | No                 | HIGH_RISK       |
    When I make a request for house insurance in UK market
    Then Insurance is not available

  Scenario: House has six bedrooms
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | PO16 7GZ |     6          | No            | No                 | NO_RISK         |
    When I make a request for house insurance in UK market
    Then Insurance is not available


  Scenario: House has no bedrooms
    Given My house has the following criteria:
    | Postcode | No of bedrooms | Thatched roof | In subsidence area | Flood area risk |
    | PO16 7GZ |     0         | No             | No                 | NO_RISK         |
    When I make a request for house insurance in UK market
    Then Insurance base annual premium is 110₤