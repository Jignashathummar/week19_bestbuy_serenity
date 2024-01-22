package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class ProductSteps {

    @Step("Creating the product information with name : {0} , type : {1}, upc : {2}, description : {3} and model : {4}")
    public ValidatableResponse createProduct(String name, String type, String upc, String description, String model) {

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setModel(model);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post(Path.PRODUCT)
                .then().log().all();
    }
    @Step("Getting the product information with name : {0}")
    public HashMap<String, Object> getProduct(String name) {
        String s1 = "data.findAll{it.name == '";
        String s2 = "'}.get(0)";
        HashMap<String, Object> productMap = SerenityRest.given().log().all()
                .queryParam("name", name)
                .when()
                .get(Path.PRODUCT)
                .then().log().all().statusCode(200)
                .extract()
                .path(s1 + name + s2);
        return productMap;
    }
    @Step("Updating the product information with name : {0} , type : {1}, upc : {2}, description : {3} and model : {4}")
    public ValidatableResponse updateProduct(String name, String type, String upc, String description,String model, int productId) {

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setModel(model);

        return SerenityRest.given().log().all()
                .queryParam("id", productId)
                .contentType(ContentType.JSON)
                .when()
//                .body("{\"zip\":\""+storePojo.getZip()+"\"}")
                .body(productPojo)
                .patch(Path.PRODUCT)
                .then().log().all();
    }
    @Step("Delete the product information with id : {0}")
    public ValidatableResponse deleteProduct(int productId) {
        return SerenityRest.given().log().all()
                .queryParam("id", productId)
                .when()
                .delete(Path.PRODUCT)
                .then().log().all();
    }
    @Step("Getting product information with productId: {0}")
    public ValidatableResponse getProductById(int productId) {
        return SerenityRest.given().log().all()
                .queryParam("id", productId)
                .when()
                .get(Path.PRODUCT)
                .then().log().all();
    }
}
