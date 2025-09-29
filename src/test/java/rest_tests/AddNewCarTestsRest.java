package rest_tests;

import api_rest.CarController;
import dto.CarDto;
import dto.ErrorMessageDtoString;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class AddNewCarTestsRest extends CarController {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void addNewCarPositiveTest(){
        int i = new Random().nextInt(1000)+1000;
        CarDto car = CarDto.builder()
                .serialNumber("231-"+i)
                .manufacture("vaz")
                .model("lada")
                .year("1996")
                .fuel("Electric")
                .seats(5)
                .carClass("high")
                .pricePerDay(55.40)
                .city("Haifa")
                .build();
        Response response = addNewCar(car);
        softAssert.assertEquals(response.getStatusCode(), 200, "validate status code");
        System.out.println(response.getBody());
        softAssert.assertTrue(response.getBody().print().contains("added successfully"), "validate message");
        softAssert.assertAll();
    }
    @Test
    public void addNewCarNegativeTest_WrongAuthotization(){
        int i = new Random().nextInt(1000)+1000;
        CarDto car = CarDto.builder()
                .serialNumber("231-"+i)
                .manufacture("vaz")
                .model("lada")
                .year("1996")
                .fuel("Electric")
                .seats(5)
                .carClass("high")
                .pricePerDay(55.40)
                .city("Haifa")
                .build();
        Response response = addNewCarNegative_WrongToken(car, "qodekcvmfkfkvvkvkvcvkclclkdxlkd10kdd");
        softAssert.assertEquals(response.getStatusCode(), 401, "validate status code");
        System.out.println(response.getBody());
        softAssert.assertTrue(response.getBody().print().contains("strings must contain exactly 2"), "validate message");
        softAssert.assertAll();
    }

    @Test
    public void addNewCarNegativeTest_WithoutSerialNumber(){
               CarDto car = CarDto.builder()
                //.serialNumber("231-"+i)
                .manufacture("vaz")
                .model("lada")
                .year("1996")
                .fuel("Electric")
                .seats(5)
                .carClass("high")
                .pricePerDay(55.40)
                .city("Haifa")
                .build();
        Response response = addNewCar(car);
        softAssert.assertEquals(response.getStatusCode(), 400, "validate status code");
        System.out.println(response.getBody());
        softAssert.assertTrue(response.getBody().print().contains("must not be blank"), "validate message");
        ErrorMessageDtoString errorMessageDtoString = response.getBody().as(ErrorMessageDtoString.class);
        softAssert.assertTrue(errorMessageDtoString.getError().equals("Bad Request"), "validate error");
        softAssert.assertAll();
    }
}
