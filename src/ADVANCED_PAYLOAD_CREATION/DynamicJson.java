package ADVANCED_PAYLOAD_CREATION;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import FILES.Payloads;

//import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DynamicJson {

	@Test(dataProvider = "addBookDateProvider",enabled = true)
public void addBook(String isbn,String aisle)
{
RestAssured.baseURI = "http://216.10.245.166/";
String Response = given().header("Content-Type", "application/json").body(Payloads.addBookPayload(isbn, isbn))
.when().post("Library/Addbook.php")
.then().assertThat().statusCode(200).
extract().body().asString();
JsonPath json = new JsonPath(Response);
//System.out.println("Response :: "+Response);
String id = json.get("ID");
System.out.println("id is :: "+id);
}
	
	
	@Test
	public void addBookThruStaticJson() throws IOException
	{
		String PayloadLocation = System.getProperty("user.dir")+"\\AddBook.json";
		System.out.println(PayloadLocation);
		String payload = new String(Files.readAllBytes(Paths.get(PayloadLocation)));
	RestAssured.baseURI = "http://216.10.245.166/";
	String Response = given().header("Content-Type", "application/json").body(payload)
	.when().post("Library/Addbook.php")
	.then().assertThat().statusCode(200).
	extract().body().asString();
	JsonPath json = new JsonPath(Response);
	//System.out.println("Response :: "+Response);
	String id = json.get("ID");
	System.out.println("id is :: "+id);
	}
	
	
	
	
	@DataProvider(name = "addBookDateProvider")
	public Object[][] addBookDateProvider()
	{
		Object[][] o1 = new Object[2][2];
		o1[0][0] = "kuhu";
		o1[0][1] = "kdhu";
		o1[1][0] = "kshu";
		o1[1][1] = "kuh34";
		return o1;
		
	}
	
	
}
