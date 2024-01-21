package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCrud extends TestBase {
    static String name = "Samsung-mobile";
    static String type = "Electronics";

    static String upc = "12345678990";

    static String description = "Mobile company";

    static String model = "S24";

    static int productId;

    @Steps
    ProductSteps steps;
    @Title("This will create a new Product")
    @Test
    public void test001() {
       steps.createProduct(name,type,upc,description,model).statusCode(201);
    }
    @Title("Verify if the product was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> productName = steps.getProduct(name);
        Assert.assertThat(productName, hasValue(name));
        productId = (int) productName.get("id");
    }
    @Title("Update the product and verify updated product information")
    @Test
    public void test003() {
        ValidatableResponse response = steps.updateProduct(name+ "_update",type,upc,description,model,productId);
        Assert.assertThat(response.extract().path("[0].name"), equalTo(name + "_update"));
    }
    @Title("Delete the product and verify if the store is deleted")
    @Test
    public void test004() {
        steps.deleteProduct(productId).statusCode(200);

        ValidatableResponse response = steps.getProductById(productId).statusCode(200);
        Assert.assertThat(response.extract().path("total"), equalTo(0));
    }
}
