package api.defs;

import api.pojo.responses.Booking;
import api.pojo.responses.BookingResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;

import static api.RequestsData.*;
import static io.restassured.RestAssured.given;

public class APIStepDefinitions {
    private RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .build();

    private RequestSpecification specWithAccept = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setAccept("application/json")
            .build();

    private RequestSpecification specWithContentType = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setContentType("application/json")
            .build();

    private RequestSpecification specWithBothHeaders = new RequestSpecBuilder()
            .setBaseUri("https://restful-booker.herokuapp.com")
            .setAccept("application/json")
            .setContentType("application/json")
            .build();

    private String token;
    private String bookingId;
    private BookingResponse bookingResponse;
    private Booking response;
    private String responseString;

    @Given("I get token")
    public void iGetToken() {
        String responseBody = given().
                spec(specWithContentType).
                log().all().
                body(getTokenRequest()).
        when().
                post("/auth").
        then().
                log().body().
                assertThat().
                statusCode(200).
                body("token", Matchers.not(Matchers.blankOrNullString())).
                header("Server", "Cowboy").extract().body().asString();

        JsonPath jsonPath = new JsonPath(responseBody);
        token = jsonPath.getString("token");
    }

    @When("I send API request to create a booking")
    public void iSendAPIRequestToCreateABooking() {
        bookingResponse =
                given()
                        .spec(specWithBothHeaders)
                        .body(getCreatingBookingRequest()).
                when().
                        post("/booking").
                then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(BookingResponse.class);
        bookingId = bookingResponse.getBookingid();
    }

    @Then("I check existence of the booking")
    public void iCheckExistenceOfTheBooking() {
        String actualCheckin = bookingResponse.getBooking().getBookingdates().getCheckIn();
        Assert.assertEquals(actualCheckin, "2024-11-01", "Mistake!");
    }

    @And("I send API request to fully update the booking")
    public void iSendAPIRequestToFullyUpdateTheBooking() {
        response =
                given()
                        .log().all()
                        .spec(specWithBothHeaders)
                        .header("Cookie", "token=" + token)
                        .body(getFullyUpdatingBookingRequest()).
                when().
                        put("/booking/" + bookingId).
                then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Booking.class);
    }

    @Then("I check fields are updated")
    public void iCheckFieldsAreUpdated() {
        String actualCheckin = response.getBookingdates().getCheckIn();
        String additional = response.getAdditionalneeds();
        Assert.assertEquals(actualCheckin, "2025-11-01", "Mistake!");
        Assert.assertEquals(additional, "Cup of tea", "Mistake!");
    }

    @And("I send API request to partially update the booking")
    public void iSendAPIRequestToPartiallyUpdateTheBooking() {
        response =
                given()
                        .spec(specWithBothHeaders)
                        .header("Cookie", "token=" + token)
                        .body("{\"additionalneeds\" : \"I want soup\"}").
                when().
                        patch("/booking/" + bookingId).
                then()
                        .log().body()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Booking.class);
    }

    @Then("I check a field is updated")
    public void iCheckAFieldIsUpdated() {
        String additional = response.getAdditionalneeds();
        Assert.assertEquals(additional, "I want soup", "Mistake!");
    }

    @And("I delete created booking")
    public void iDeleteCreatedBooking() {
        responseString =
                given()
                        .spec(spec)
                        .header("Cookie", "token=" + token).
                when().
                        delete("/booking/" + bookingId).
                then()
                        .log().body()
                        .statusCode(201)
                        .extract()
                        .body()
                        .asString();
    }

    @Then("previously created booking is deleted")
    public void previouslyCreatedBookingIsDeleted() {
        Assert.assertEquals(responseString, "Created", "Mistake!");
        Assert.assertTrue(isBookingDeleted(bookingId), "Mistake!");
    }

    private boolean isBookingDeleted(String bookingId) {
        int statusCode = given()
                .spec(specWithAccept).
        when()
                .get("/booking/" + bookingId).
        then()
                .log().body()
                .assertThat()
                .extract()
                .statusCode();
        return statusCode == 404;
    }
}
