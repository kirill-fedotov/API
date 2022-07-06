package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest {

    @Test
    public void updateBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get booking id of new booking
        int bookingId = responseCreate.jsonPath().getInt("bookingid");

        //Create updated JSON body
        JSONObject body = new JSONObject();

        body.put("firstname", "Olga");
        body.put("lastname", "Shyshkina");
        body.put("totalprice", 125);
        body.put("depositpaid", true);
        body.put("additionalneeds", "Breakfast");

        JSONObject bookingDates = new JSONObject();

        bookingDates.put("checkin", "2022-08-02");
        bookingDates.put("checkout", "2022-08-11");
        body.put("bookingdates", bookingDates);

        //Update booking
        Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin", "password123")
                .contentType(ContentType.JSON).body(body.toString())
                .put("https://restful-booker.herokuapp.com/booking/" + bookingId);
        responseUpdate.print();

        //Verifications
        //Verify response 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200, but it's not");

        //Verify all fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Olga", "firstname in response is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Shyshkina", "lastname in response is not expected");

        int actualTotalPrice = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice, 125, "totalprice in response is not expected");

        boolean actualDepositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(actualDepositPaid, "depositpaid should be true, but it's not");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-08-02", "checkin in response is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2022-08-11", "checkout in response is not expected");

        String actualAdditionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Breakfast", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }

}
