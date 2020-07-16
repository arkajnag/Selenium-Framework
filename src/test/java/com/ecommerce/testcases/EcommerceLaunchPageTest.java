package com.ecommerce.testcases;

import java.util.Map;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.ecommerce.pages.EcommerceAuthenticatePage;
import com.ecommerce.pages.EcommerceLaunchPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Ecommerce Selenium Framework")
@Feature("Ecommerce Launch Page Test Coverage")
public class EcommerceLaunchPageTest extends BaseTest{

	EcommerceLaunchPage eLaunchPage=null;
	SoftAssert sftAssert=new SoftAssert();
	
	@Story("Customer SignIn Navigation Verification")
	@Description("To verify Customer click on Sign In Link navigates him/her to Authentication page")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=1, description="To verify Customer click on Sign In Link navigates him/her to Authentication page")
	public void tc_VerifyClickOnSignInLink(){
		eLaunchPage=new EcommerceLaunchPage();
		Map<String,Object> responseObjects=eLaunchPage.fnc_ClickOnSignInLinkText.get();
		sftAssert.assertEquals(responseObjects.get("CurrentPageUrl"), "http://automationpractice.com/index.php?controller=authentication&back=my-account");
		sftAssert.assertTrue((responseObjects.get("AuthenticatePageObject") instanceof EcommerceAuthenticatePage)?true:false, "Customer is not navigated to Ecommerce Authenticate Page");
		sftAssert.assertAll();
	}
}
