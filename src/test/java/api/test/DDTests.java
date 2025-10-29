package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	@Test(priority = 1, dataProvider= "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userId, String userName, String fname, String lname, String useremail, String pwd, String ph) {
		
		User userpayload = new User();
		
		userpayload.setId(Integer.parseInt(userId));
		userpayload.setUsername(userName);
		userpayload.setFirstName(fname);
		userpayload.setLastName(lname);
		userpayload.setEmail(useremail);
		userpayload.setPassword(pwd);
		userpayload.setPhone(ph);

		Response response = UserEndPoints.CreateUser(userpayload);
		
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class, dependsOnMethods = "testPostUser")
	public void testReadUserByName(String UserName) {
		
		Response response = UserEndPoints.readUser(UserName);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class, dependsOnMethods = "testReadUserByName")
	public void testDeleteUserByName(String UserName) throws InterruptedException {
		
		Response response = UserEndPoints.deleteUser(UserName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
