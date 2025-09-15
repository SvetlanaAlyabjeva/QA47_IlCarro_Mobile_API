package api_rest;

import dto.CarDto;
import dto.RegistrationBodyDto;
import dto.TokenDto;
import interfaces.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import static io.restassured.RestAssured.given;

public class CarController implements BaseApi {
    public TokenDto tokenDto;

    @BeforeSuite
    public void  login(){
    RegistrationBodyDto user = RegistrationBodyDto.builder()
            .username("mingo_plum411@gmail.com")
            .password("Bbb22263!")
            .build();
    tokenDto = given()
            .body(user)
            .contentType(ContentType.JSON)
            .when()
            .post(BASE_URL+LOGIN_URL)
            .thenReturn()
            .getBody()
            .as(TokenDto.class);
        System.out.println(tokenDto.getAccessToken());
    }

public Response addNewCar(CarDto car){
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .post(BASE_URL+ADD_NEW_CAR_URL)
                .thenReturn();
}
}
