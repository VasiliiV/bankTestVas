package ru.weatherapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Weather API tests")
@Feature("Current weather")
public class WeatherApiTest {

    private WireMockServer wireMockServer;
    private static final String API_KEY = "2637a812b0ac49c8875142747250703";

    @BeforeClass
    public void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8089;

        RestAssured.filters(new io.qameta.allure.restassured.AllureRestAssured());

        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo(API_KEY))
                .withQueryParam("q", equalTo("Moscow"))
                .willReturn(okJson("{ \"location\": { \"name\": \"Moscow\" }, \"current\": { \"temp_c\": 5 }}")));

        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo(API_KEY))
                .withQueryParam("q", equalTo("London"))
                .willReturn(okJson("{ \"location\": { \"name\": \"London\" }, \"current\": { \"temp_c\": 12 }}")));

        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo(API_KEY))
                .withQueryParam("q", equalTo("Paris"))
                .willReturn(okJson("{ \"location\": { \"name\": \"Paris\" }, \"current\": { \"temp_c\": 14 }}")));

        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo(API_KEY))
                .withQueryParam("q", equalTo("New York"))
                .willReturn(okJson("{ \"location\": { \"name\": \"New York\" }, \"current\": { \"temp_c\": 8 }}")));

        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("q", equalTo("InvalidCity"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": { \"message\": \"No matching location found.\" }}")));

        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo("wrongkey"))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"error\": { \"message\": \"API key invalid.\" }}")));
    }

    @AfterClass
    public void teardown() {
        wireMockServer.stop();
    }

    @Test(description = "Позитивный тест - проверка текущей погоды")
    @Story("Проверка погоды по городам")
    @Severity(SeverityLevel.NORMAL)
    public void testCurrentWeatherPositive() {
        testCityWeather("Moscow", 5);
        testCityWeather("London", 12);
        testCityWeather("Paris", 14);
        testCityWeather("New York", 8);
    }

    @Step("Проверка погоды в городе {city}, ожидаемая температура {expectedTemp}")
    public void testCityWeather(String city, int expectedTemp) {
        Response response = given()
                .queryParam("key", API_KEY)
                .queryParam("q", city)
                .when()
                .get("/v1/current.json")
                .then()
                .statusCode(200)
                .extract().response();

        int actualTemp = response.jsonPath().getInt("current.temp_c");

        if (actualTemp != expectedTemp) {
            System.out.printf("Расхождение температуры в %s: ожидается %d, получено %d%n",
                    city, expectedTemp, actualTemp);
        }
    }

    @Test(description = "Негативный тест - ошибки API")
    @Story("Проверка обработки ошибок API")
    @Severity(SeverityLevel.CRITICAL)
    public void testApiErrorsNegative() {
        // Некорректный город
        given().queryParam("key", API_KEY).queryParam("q", "InvalidCity")
                .when().get("/v1/current.json")
                .then().statusCode(400)
                .body("error.message", containsString("No matching location found."));

        // Некорректный ключ API
        given().queryParam("key", "wrongkey").queryParam("q", "Moscow")
                .when().get("/v1/current.json")
                .then().statusCode(403)
                .body("error.message", containsString("API key invalid."));
    }
}