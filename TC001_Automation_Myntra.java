package autoSeleniumPrac;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TC001_Automation_Myntra {
	public static RemoteWebDriver driver;
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions opt = new ChromeOptions();
		opt.addArguments("--disable-notifications");
		driver = new ChromeDriver(opt);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//1. Open https://www.myntra.com/
		driver.get("https://www.myntra.com/");

		//2. Mouse over on WOMEN (Actions -> moveToElement
		WebElement ele = driver.findElementByXPath("(//a[text()='Women'])[1]");
		Actions builder = new Actions(driver);
		builder.moveToElement(ele).perform();

		//3. Click Jackets & Coats
		driver.findElementByXPath("//a[text()='Jackets & Coats']").click();

		//4.Find the total count of item (top) -> getText -> String
		String str = driver.findElementByClassName("title-count").getText();
		int count = Integer.parseInt(str.replaceAll("\\D",""));
		System.out.println(count);

		//5. Validate the sum of categories count matches
		int expCount = 0;
		List <WebElement> list = driver.findElementsByXPath("//span[@class='categories-num']");
		for(int i =0;i<list.size();i++) {
			String text2 = list.get(i).getText();
			System.out.println(text2.replaceAll("\\D",""));
			int count2 = Integer.parseInt(text2.replaceAll("\\D",""));
			expCount = expCount+ count2;
		}
		if(count == expCount) {
			System.out.println("The Count matches successfully");

		}else
			System.out.println("The count  is not matching");

		//6. Check Coats
		driver.findElementByXPath("(//ul[@class='categories-list']//div)[2]").click();

		//7. Click + More option under BRAND
		driver.findElementByClassName("brand-more").click();

		//8. Type MANGO and click checkbox
		driver.findElementByClassName("FilterDirectory-searchInput").sendKeys("MANGO");
		driver.findElementByXPath("//ul[@class='FilterDirectory-list']//div").click();

		//9. Close the pop-up x
		driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();

		//10. Confirm all the Coats are of brand MANGO
		List<WebElement> listItem = driver.findElementsByXPath("//h3[@class='product-brand']");
		for (int j=1;j<listItem.size();j++) {
			String Str3 = listItem.get(j).getText();
			if(Str3.equals("MANGO")) 
				System.out.println(Str3+" It is Matching.");
			else
				System.out.println("The fetched result is not matching");
		}

		//11. Sort by Better Discount
		WebElement sort = driver.findElementByXPath("//div[@class='sort-sortBy']");
		builder.moveToElement(sort).perform();
		Thread.sleep(2000);
		driver.findElementByXPath("//ul[@class='sort-list']//label[text()='Better Discount']").click();

		//12. Find the price of first displayed item
		List<WebElement> listPrice = driver.findElementsByXPath("(//div[@class='product-price']/span/span[1])[1]");
		int price = Integer.parseInt(listPrice.get(0).getText().replaceAll("\\D",""));
		System.out.println("The Price of the first item is: "+price);

		//13. Mouse over on size of the first item
		WebElement firstItem = listPrice.get(0);	
		builder.moveToElement(firstItem).perform();

		//14. Click on WishList Now
		driver.findElementByXPath("(//div[@class='product-price']/../../following::div[2]/span)[1]").click();
		Thread.sleep(5000);

		//15. Close Browser
		driver.close();

	}

}
