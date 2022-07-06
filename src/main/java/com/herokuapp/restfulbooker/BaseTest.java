package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {

    protected Response createBooking() {

        //Create JSON body
        JSONObject body = new JSONObject();

        body.put("firstname", "Mark");
        body.put("lastname", "Wilson");
        body.put("totalprice", 150);
        body.put("depositpaid", false);
        body.put("additionalneeds", "Baby crib");


        JSONObject bookingDates = new JSONObject();

        bookingDates.put("checkin", "2022-08-01");
        bookingDates.put("checkout", "2022-08-10");
        body.put("bookingdates", bookingDates);

        //Get response
        Response response = RestAssured.given().contentType(ContentType.JSON).body(body.toString())
                .post("https://restful-booker.herokuapp.com/booking");
        return response;
    }

}
