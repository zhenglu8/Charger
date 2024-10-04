package org.example;

public class APITesting {
    /*
    //@Test
    public void testGetChargerInfo1() {
        String jsonResponse = gievn()
                .when()
                .get("resources/")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        JsonPath js = new JsonPath(jsonResponse);
        String actualID = js.get("id");
        String actualType = js.get("type");
        long actualTime = js.get("time");

        Assert.assertEquals(actualID, "Expected ID");
        Assert.assertEquals(actualType, "Expected Type");
        Assert.assertEquals(actualTime, "Expected Time");
    }

    //@Test
    public void testGetChargerInfo2() {
        ChargerResponse jsonResponse = gievn()
                .when()
                .get("resources/")
                .then()
                .statusCode(200)
                .extract()
                .as(ChargerResponse.class);
        String actualID = jsonResponse.getID();
        String actualType = jsonResponse.getType();
        long actualTime = jsonResponse.getTime();

        Assert.assertEquals(actualID, "Expected ID");
        Assert.assertEquals(actualType, "Expected Type");
        Assert.assertEquals(actualTime, "Expected Time");
    }
    */
}
