package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.Path;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class StoreSteps {
    @Step("Creating the Store information with name : {0} , address : {1}, city : {2}, state : {3} and zip : {4}")
    public ValidatableResponse createStore(String name, String address, String city, String state, String zip) {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setAddress(address);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post(Path.STORES)
                .then().log().all();
    }

    @Step("Getting the store information with name : {0}")
    public HashMap<String, Object> getStore(String name) {
        String s1 = "data.findAll{it.name == '";
        String s2 = "'}.get(0)";
        HashMap<String, Object> storeMap = SerenityRest.given().log().all()
                .queryParam("name", name)
                .when()
                .get(Path.STORES)
                .then().log().all().statusCode(200)
                .extract()
                .path(s1 + name + s2);
        return storeMap;
    }

    @Step("Updating the Store information with name : {0} , address : {1}, city : {2}, state : {3}, zip : {4} and storeId: {5}")
    public ValidatableResponse updateStore(String name, String address, String city, String state,String zip, int storeId) {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setAddress(address);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);

        return SerenityRest.given().log().all()
                .queryParam("id", storeId)
                .contentType(ContentType.JSON)
                .when()
//                .body("{\"zip\":\""+storePojo.getZip()+"\"}")
                .body(storePojo)
                .patch(Path.STORES)
                .then().log().all();
    }

    @Step("Delete the store information with id : {0}")
    public ValidatableResponse deleteStore(int storeId) {
        return SerenityRest.given().log().all()
                .queryParam("id", storeId)
                .when()
                .delete(Path.STORES)
                .then().log().all();
    }

    @Step("Getting store information with storeId: {0}")
    public ValidatableResponse getStoreById(int storeId) {
        return SerenityRest.given().log().all()
                .queryParam("id", storeId)
                .when()
                .get(Path.STORES)
                .then().log().all();
    }

}
