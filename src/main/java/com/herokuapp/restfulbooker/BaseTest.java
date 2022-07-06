package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
    }

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
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
                .post("/booking");
        return response;
    }

}
