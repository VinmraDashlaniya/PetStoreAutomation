package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {

	Faker faker;
	User userpayload;
	
	public Logger logger; // for logs
	
	@BeforeClass
	public void setup() {
		faker = new Faker();
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().emailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	
	@Test(priority = 1)
	public void testPostUser()
	{
		logger.info("************ Creating user **************");
		logger.info("User Name from Post request "+this.userpayload.getUsername());
		Response response = UserEndPoints2.CreateUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User is created **************");
	}
	
	
	@Test(priority = 2)
	public void testGetUserByName() throws InterruptedException {
		
		logger.info("************ Reading user info **************");
		
	    Thread.sleep(8000); // wait 8 second for data to persist

	    logger.info("User Name from Get request "+this.userpayload.getUsername());
	    
	    Response response = UserEndPoints2.readUser(this.userpayload.getUsername());
	    response.then().log().all();
	    Assert.assertEquals(response.getStatusCode(), 200);
	    
	    logger.info("************ User info is displayed **************");
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		
		logger.info("************ Updating user **************");
		
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints2.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User is updated **************");
		
		//Checking data after update
		 Response responseAfterUpdate = UserEndPoints2.readUser(this.userpayload.getUsername());
		 Assert.assertEquals(response.getStatusCode(), 200);
		 
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		
		logger.info("************ Deleting user **************");
		
		Response response = UserEndPoints2.deleteUser(this.userpayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User is deleted **************");
	}
}
