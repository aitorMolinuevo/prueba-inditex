Feature: Prices API

  Background:
    * url 'http://localhost:8080'

  Scenario: Test 1 - Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)
    Given path '/prices'
    And param applicationDate = '2020-06-14T10:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 35.50
    And match response.priceList == 1
    And match response.brandId == 1
    And match response.productId == 35455

  Scenario: Test 2 - Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)
    Given path '/prices'
    And param applicationDate = '2020-06-14T16:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 25.45
    And match response.priceList == 2

  Scenario: Test 3 - Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)
    Given path '/prices'
    And param applicationDate = '2020-06-14T21:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 35.50
    And match response.priceList == 1

  Scenario: Test 4 - Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)
    Given path '/prices'
    And param applicationDate = '2020-06-15T10:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 30.50
    And match response.priceList == 3

  Scenario: Test 5 - Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)
    Given path '/prices'
    And param applicationDate = '2020-06-16T21:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 200
    And match response.price == 38.95
    And match response.priceList == 4

  Scenario: Test 6 - Price Not Found
    Given path '/prices'
    And param applicationDate = '2030-01-01T00:00:00'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 404
    And match response.status == 404
    And match response.error == 'Not Found'

  Scenario: Test 7 - Bad Request (Missing param)
    Given path '/prices'
    And param productId = 35455
    And param brandId = 1
    When method get
    Then status 400
    And match response.status == 400
    And match response.error == 'Bad Request'
