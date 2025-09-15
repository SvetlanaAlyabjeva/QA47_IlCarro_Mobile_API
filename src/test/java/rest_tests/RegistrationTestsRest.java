package rest_tests;

import api_rest.AuthenticationController;
import dto.ErrorMessageDtoString;
import dto.RegistrationBodyDto;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class RegistrationTestsRest extends AuthenticationController {
SoftAssert softAssert = new SoftAssert();

    @Test
    public void registrationPositiveTest(){
int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("mingo_plum"+i+"@gmail.com")
                .password("Bbb22263!")
                .firstName("Mingo")
                .lastName("Plum")
                .build();
        Assert.assertEquals(registrationLogin(user, REGISTRATION_URL).getStatusCode(),200);
    }

    @Test
    public void registrationNegativeTest_WrongEmail(){
int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("mingo_plum"+i+"gmail.com")
                .password("Bbb22263!")
                .firstName("Mingo")
                .lastName("Plum")
                .build();
        Response response = registrationLogin(user, REGISTRATION_URL);
        softAssert.assertEquals(response.getStatusCode(), 400, "validate status code");
        ErrorMessageDtoString errorMessageDtoString = response.getBody().as(ErrorMessageDtoString.class);
        softAssert.assertEquals(errorMessageDtoString.getError(), "Bad Request", "validate error");
        System.out.println(errorMessageDtoString);
        softAssert.assertTrue(errorMessageDtoString.getMessage()
                .toString().contains("must be a well-formed email address"), "validate message");
        softAssert.assertAll();
    }

    @Test
    public void registrationNegativeTest_WrongPassword(){
int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("mingo_plum"+i+"@gmail.com")
                .password("Bbb22263")
                .firstName("Mingo")
                .lastName("Plum")
                .build();
        Response response = registrationLogin(user, REGISTRATION_URL);
        softAssert.assertEquals(response.getStatusCode(), 400, "validate status code");
        ErrorMessageDtoString errorMessageDtoString = response.getBody().as(ErrorMessageDtoString.class);
        softAssert.assertEquals(errorMessageDtoString.getError(), "Bad Request", "validate error");
        System.out.println(errorMessageDtoString);
        softAssert.assertTrue(errorMessageDtoString.getMessage()
                .toString().contains("Can contain special characters [@$#^&*!]"), "validate message");
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_EmptyFirstName(){
int i = new Random().nextInt(1000);
        RegistrationBodyDto user = RegistrationBodyDto.builder()
                .username("mingo_plum"+i+"@gmail.com")
                .password("Bbb22263!")
                .firstName("")
                .lastName("Plum")
                .build();
        Response response = registrationLogin(user, REGISTRATION_URL);
        softAssert.assertEquals(response.getStatusCode(), 400, "validate status code");
        ErrorMessageDtoString errorMessageDtoString = response.getBody().as(ErrorMessageDtoString.class);
        softAssert.assertEquals(errorMessageDtoString.getError(), "Bad Request", "validate error");
        System.out.println(errorMessageDtoString);
        softAssert.assertTrue(errorMessageDtoString.getMessage()
                .toString().contains("firstName=must not be blank"), "validate message");
        softAssert.assertAll();
    }

}
