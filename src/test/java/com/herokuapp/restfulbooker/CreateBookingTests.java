package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {

    @Test
    public void createBookingTest() {

        //Create booking
        Response response = createBooking();
        response.print();

        //Verifications
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Mark", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Wilson", "lastname in response is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 150, "totalprice in response is not expected");

        boolean actualDepositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-08-01", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-08-10", "checkout in response is not expected");

        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }

    @Test
    public void createBookingWithPOJOTest() {

        //Create booking using POJOs
        Bookingdates bookingdates = new Bookingdates("2022-08-01", "2022-08-10");
        Booking booking = new Booking("Mark", "Wilson", 150, true, bookingdates,"Baby crib");

        //Get response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking)
                .post("/booking");
        response.print();

        Bookingid bookingid = response.as(Bookingid.class);

        //Verifications
        //Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");

        System.out.println("Request booking: " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());

        //Verify all fields
        Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());
    }




}
