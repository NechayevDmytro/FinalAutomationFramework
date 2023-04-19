package api.tests;

import api.pojo.responses.Booking;
import api.pojo.responses.BookingID;
import api.pojo.responses.BookingResponse;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static api.RequestsData.*;
import static io.restassured.RestAssured.given;

public class TestBookings extends BaseBookingTest {
    private String token;
    private String bookingId;

    @Test
    public void checkAllBookings() {
        BookingID[] bookings = given().
                spec(spec).
        when().
                get("/booking").
        then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(BookingID[].class);
        System.out.printf("There are %d bookings in the system.", bookings.length);
    }

    @Test
    public void createToken() {
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

    @Test
    public void createBooking() {
        BookingResponse response =
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
        bookingId = response.getBookingid();

        String actualCheckin = response.getBooking().getBookingdates().getCheckIn();
        Assert.assertEquals(actualCheckin, "2024-11-01", "Mistake!");
    }

    @Test(dependsOnMethods = {"createBooking"})
    public void checkBookingById() {
        Booking booking = given()
                .spec(specWithAccept).
        when()
                .get("/booking/" + bookingId).
        then()
                .log().body()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(Booking.class);

        Assert.assertEquals(booking.getFirstname(), "Bill", "Mistake!");
    }

    @Test(dependsOnMethods = {"createBooking"})
    public void checkBookingByFirstAndLastName() {
        BookingID[] bookings = given()
                .spec(specWithAccept)
                .queryParam("firstname", "Bill")
                .queryParam("lastname", "Gates").
        when()
                .get("/booking").
        then()
                .log().body()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(BookingID[].class);

        Assert.assertTrue(bookings.length >= 1, "Mistake!");
    }

    @Test(dependsOnMethods = {"createToken", "createBooking"})
    public void fullyBookingUpdate() {
        Booking response =
                given()
                        .spec(specWithBothHeaders)
                        .header("Cookie", "token=" + token)
                        .body(getFullyUpdatingBookingRequest()).
                when().
                        put("/booking/" + bookingId).
                then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(Booking.class);

        String actualCheckin = response.getBookingdates().getCheckIn();
        String additional = response.getAdditionalneeds();
        Assert.assertEquals(actualCheckin, "2025-11-01", "Mistake!");
        Assert.assertEquals(additional, "Cup of tea", "Mistake!");
    }

    @Test(dependsOnMethods = {"createToken", "createBooking"})
    public void partiallyBookingUpdate() {
        Booking response =
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

        String additional = response.getAdditionalneeds();
        Assert.assertEquals(additional, "I want soup", "Mistake!");
    }

    @Test(dependsOnMethods = {"createToken", "createBooking"})
    public void deleteBooking() {
        String responseString =
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
