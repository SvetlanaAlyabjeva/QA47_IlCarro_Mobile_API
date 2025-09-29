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

}public Response addNewCarNegative_WrongToken(CarDto car, String token){
        return given()
                .body(car)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post(BASE_URL+ADD_NEW_CAR_URL)
                .thenReturn();
}
public  Response getUserCars(){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .get(BASE_URL+GET_ALL_USER_CARS_URL)
                .thenReturn();

}public  Response getUserCarsNegative_WrongUrl(String url){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .get(BASE_URL+url)
                .thenReturn();
}
public Response deleteCarBySerialNumber(String serialNumber){
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", tokenDto.getAccessToken())
                .when()
                .delete(BASE_URL+DELETE_CAR_URL+serialNumber)
                .thenReturn();

}
}
