package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.StoreSteps;
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
public class StoreCrud extends TestBase {
    static String name = "Prime";
    static String type = "Testing";
    static String address = "Offshore";
    static String city = "London";
    static String state = "UK";
    static String zip = "HA00AA";
    static int storeID;
    @Steps
    StoreSteps steps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        ValidatableResponse response = steps.createStore(name, address, city, state, zip);
        response.statusCode(201);
    }

    @Title("Verify if the store was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> storeName = steps.getStore(name);
        Assert.assertThat(storeName, hasValue(name));
        storeID = (int) storeName.get("id");
    }

    @Title("Update the store and verify updated store information")
    @Test
    public void test003() {
        ValidatableResponse response = steps.updateStore(name, address, city, state, zip + "_update", storeID);
        Assert.assertThat(response.extract().path("[0].zip"), equalTo(zip + "_update"));
    }

    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004() {
        steps.deleteStore(storeID).statusCode(200);

        ValidatableResponse response = steps.getStoreById(storeID).statusCode(200);
        Assert.assertThat(response.extract().path("total"), equalTo(0));
    }
}
