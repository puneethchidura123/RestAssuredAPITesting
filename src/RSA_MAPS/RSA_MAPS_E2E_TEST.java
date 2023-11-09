package RSA_MAPS;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static  io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import FILES.Payloads;

public class RSA_MAPS_E2E_TEST {

	public static String addPlace(String RequestBody)
	{
		RestAssured.baseURI = Payloads.BaseURL;
		String AddPlaceResponse = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(RequestBody)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).header("Server", "Apache/2.4.18 (Ubuntu)")
		.body("scope", equalTo("APP")).extract().response().asString();
		
		return AddPlaceResponse;
	}
	
	public static String getPlace(String place_id)
	{
		RestAssured.baseURI=Payloads.BaseURL;
		String GetPlaceResponse = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).header("Content-Type","application/json")
		.when().get("/maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		return GetPlaceResponse;
	}
	
	public static String updatePlace(String place_id,String Updated_Address)
	{
		RestAssured.baseURI=Payloads.BaseURL;
		String UpdatePlaceResponse = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payloads.getRSAMAPSUpdatePlaceRequestBody(place_id, Updated_Address))
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		return UpdatePlaceResponse;
	}
	
	public static String deletePlace(String place_id)
	{
		
		System.out.println(Payloads.getRSAMAPSDeletePlaceRequestBody(place_id));
		RestAssured.baseURI=Payloads.BaseURL;
		String DeletePlaceResponse = given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payloads.getRSAMAPSDeletePlaceRequestBody(place_id))
		.when().delete("/maps/api/place/delete/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		return DeletePlaceResponse;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//getting the add place response
		String Response = addPlace(Payloads.getRSAMAPSAddPlaceRequestBody());
		//System.out.println(Response);
		JsonPath AddPlaceResponseJson = new JsonPath(Response);
		String Place_ID = AddPlaceResponseJson.getString("place_id");
		String Place_Address = new JsonPath(getPlace(Place_ID)).getString("address");
		String Updated_Address = "332, side layout, cohen 09";
//		System.out.println(Place_Address);
//		System.out.println(Place_ID);
		
		String UpdatePlaceResponseJson = updatePlace(Place_ID, Updated_Address);
		//System.out.println("Updated Address :: "+new JsonPath(getPlace(Place_ID)).getString("address"));
		//System.out.println(deletePlace(Place_ID));
		Assert.assertEquals(deletePlace(Place_ID),"{\"status\":\"OK\"}");
		
	}

}
