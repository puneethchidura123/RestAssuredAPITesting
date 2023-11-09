package PARSING;

import org.testng.Assert;

import FILES.Payloads;
import io.restassured.path.json.JsonPath;

public class ParseJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath json = new JsonPath(Payloads.getCoursePrices());
		
//		1. Print No of courses returned by API
		int noOFCourses = json.getInt("courses.size()");
		System.out.println("noOFCourses :: "+ noOFCourses);
		
		
//		2.Print Purchase Amount
		int purchaseAmount = Integer.parseInt(json.getString("dashboard.purchaseAmount"));
		System.out.println("purchaseAmount :: "+ purchaseAmount);
		
		
//		3. Print Title of the first course
		String firstCourseProce = json.getString("courses[0].price");
		System.out.println("firstCoursePrice :: "+firstCourseProce);
		
		
//		4. Print All course titles and their respective Prices
		
		for(int i =0;i<noOFCourses;i++)
		{
			System.out.println("Course - "+i+" title is :: "+json.getString("courses["+i+"].title")+" and price is :: "+json.getInt("courses["+i+"].price"));
		}
		
		
//		5. Print no of copies sold by RPA Course
		
		for(int i =0;i<noOFCourses;i++)
		{
			if(json.getString("courses["+i+"].title").equals("RPA"))
			{
				System.out.println("no of copies sold by RPA course are :: "+json.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
		
//		6. Verify if Sum of all Course prices matches with Purchase Amount
		
		int sumOfAllCoursePrices = 0;
		for(int i =0;i<noOFCourses;i++)
		{
			sumOfAllCoursePrices = sumOfAllCoursePrices+json.getInt("courses["+i+"].price") * json.getInt("courses["+i+"].copies");
		}
		Assert.assertEquals(sumOfAllCoursePrices, purchaseAmount+1);

	}

}
