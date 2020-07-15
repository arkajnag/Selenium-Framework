package com.ecommerce.testcases;

import java.util.Map;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.ecommerce.pages.EcommerceAuthenticatePage;
import com.ecommerce.pages.EcommerceLaunchPage;

public class EcommerceLaunchPageTest extends BaseTest{

	EcommerceLaunchPage eLaunchPage=null;
	SoftAssert sftAssert=new SoftAssert();
	
	
	@Test(priority=1, description="To verify Customer click on Sign In Link navigates him/her to Authentication page")
	public void tc_VerifyClickOnSignInLink(){
		eLaunchPage=new EcommerceLaunchPage();
		Map<String,Object> responseObjects=eLaunchPage.fnc_ClickOnSignInLinkText.get();
		sftAssert.assertEquals(responseObjects.get("CurrentPageUrl"), "http://automationpractice.com/index.php?controller=authentication&back=my-account");
		sftAssert.assertTrue((responseObjects.get("AuthenticatePageObject") instanceof EcommerceAuthenticatePage)?true:false, "Customer is not navigated to Ecommerce Authenticate Page");
		sftAssert.assertAll();
	}
}
