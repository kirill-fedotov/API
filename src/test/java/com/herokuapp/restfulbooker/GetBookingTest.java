package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest{

    @Test
    public void getBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking id of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");

        //Set path parameter
        spec.pathParam("bookingId", bookingId);

        //Get response with booking IDs
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Mark", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Wilson", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice, 150, "totalprice in response is not expected");

        boolean actualDepositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-08-01", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-08-10", "checkout in response is not expected");

        String actualAdditionalNeeds = response.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();

    }

    @Test
    public void getBookingXMLTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking id of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");

        //Set path parameter
        spec.pathParam("bookingId", bookingId);

        //Get response with booking IDs
        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);

        Response response = RestAssured.given(spec).get("/booking/{bookingId}");
        response.print();

        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Mark", "firstname in response is not expected");

        String actualLastName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Wilson", "lastname in response is not expected");

        int actualTotalPrice = response.xmlPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 150, "totalprice in response is not expected");

        boolean actualDepositPaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-08-01", "checkin in response is not expected");

        String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-08-10", "checkout in response is not expected");

        String actualAdditionalNeeds = response.xmlPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();

    }

}

