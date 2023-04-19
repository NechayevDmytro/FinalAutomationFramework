package api.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseBookingTest {

    protected RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .build();

    protected RequestSpecification specWithAccept = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setAccept("application/json")
            .build();

    protected RequestSpecification specWithContentType = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setContentType("application/json")
            .build();

    protected RequestSpecification specWithBothHeaders = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setAccept("application/json")
            .setContentType("application/json")
            .build();
}
