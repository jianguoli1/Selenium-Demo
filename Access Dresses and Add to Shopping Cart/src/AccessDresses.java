import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccessDresses {
	
	static WebDriver driver;
    static Wait<WebDriver> wait;
    static String url;
    static String browserType;


	public static void main(String[] args) throws InterruptedException {
		
		
		try {
			
			//URL path = java.lang.String.class.getResource("config.properties");
			//File f = new File(path.getFile());
			BufferedReader br = new BufferedReader(new FileReader("config.properties"));
			
		    String line;
		    
		    //read the url to use
		    if ((line = br.readLine()) != null) {
		    	// process the line
		    	url = line;
		    	System.out.println(url);
		    }
		    
		    ////read the browser to use
		    if ((line = br.readLine()) != null) {
		    	// process the lines
		    	browserType = line;
		    	System.out.println(browserType);
		    }
		    
		    List<Double> priceList = new ArrayList<Double>();
		    while ((line = br.readLine()) != null) {
		    	//process the lines
		    	String price= line;
		    	//add price to list
		    	priceList.add(Double.parseDouble(price)) ; 
		    	System.out.println(price);
		    }
		}
		//catch file not found exception and such
		catch (Exception e ) {
			
			e.printStackTrace();
		}
		
		switch (browserType) {
			case "Firefox": driver = new FirefoxDriver();
				break;
			case "Chrome": driver = new ChromeDriver();
				break;
			default: driver = new FirefoxDriver();
				break;
		}
		
        driver.get(url);
        // Maximize window
        driver.manage().window().maximize();
        
        // Sleep for 5 seconds
     	Thread.sleep(5000);
        
     	//Find all clothing links
        List<WebElement> Links = driver.findElements(By.className("sf-with-ul"));
        
        
        int linkID = 0;
		// print the total number of elements
		System.out.println("Total selected links are " + Links.size());
 
		// Now using Iterator we will iterate all elements
		Iterator<WebElement> iter = Links.iterator();
 
		// this will check whether list has some element or not
		int i = 0;
		while (iter.hasNext()) {
 
			// Iterate one by one
			WebElement item = iter.next();
			
			// get the text
			String label = item.getText();
			if (label.toString().equals("DRESSES")) {
				linkID = i;	
			}
 
			// print the text
			System.out.println("Link label is " + label);
			i++;
 
		}
		System.out.println("Link Index is " + linkID);
		System.out.println();
		//Clicking on the selected link
		Links.get(linkID).click();
		
		// Sleep for 5 seconds
     	Thread.sleep(5000);
     	
     	
     	//Check if we are new page
     	System.out.println("Current web page is: " + driver.getCurrentUrl());
     	System.out.println();
     	
     	Thread.sleep(5000);
		//Find all prices for clothing type on new page
		
		     	   	
		List<WebElement> dresses = driver.findElements(By.xpath("//span[contains(text(),'Add to cart')]"));
		//Check number of dresses found on page
		System.out.println("Found " +  dresses.size() + " dresses ready for check out. ");
		Iterator<WebElement> iterDresses = dresses.iterator();
		
		// Store the parent window
		String parentWindowHandler = driver.getWindowHandle(); 
		//Define a popup window
		String subWindowHandler = null;		
		
		List<WebElement> Prices = new ArrayList<WebElement>();
		
		//Perform options on each item
		while (iterDresses.hasNext()) {
 
			// Iterate one by one
			WebElement item = iterDresses.next();
			// get the tag info
			String label = item.getText();
			
			//Prices.add(driver.findElement(By.xpath("//span[contains(text(),'price product-price')]")));
			
			// print the text
			System.out.println("Dress label is " + label);
			
			//Click to add each dress to cart
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", item);
			
			
			
			//Get all window handles
			Set <String> handles = driver.getWindowHandles(); 
			Iterator<String> iterator = handles.iterator();
			//Locate popup window
			while (iterator.hasNext()){
			    subWindowHandler = iterator.next();
			}
			//Switch to popup window and wait to load
			driver.switchTo().window(subWindowHandler); 
		    Thread.sleep(5000);
			//Find and click Continue button
		    driver.findElements(By.xpath("//span[contains(text(),'Continue shopping')]"));
			//driver.findElement(By.className("cross")).click();
			//driver.close();
			
			//Switch back to parent window
			driver.switchTo().window(parentWindowHandler);  
		}
		
		// print the total number of elements
				System.out.println("Total found prices are " + Prices.size());
				System.out.println();
				 
				// Now using Iterator we will iterate all elements
				Iterator<WebElement> iterP = Prices.iterator();
				while (iterP.hasNext()) {
					 
					// Iterate one by one
					WebElement item = iterP.next();
					
					// get the text
					String price = item.getText();

					// print the text
					System.out.println("Item price is " + price);
					i++;
		 
				}
      
		//Switch to popup window and proceed to cart
		driver.switchTo().window(subWindowHandler); 
		driver.findElement(By.xpath("//span[contains(text(),'Proceed to checkout')]")).click();
		
		//Switch back to parent window
		driver.switchTo().window(parentWindowHandler);  
		driver.findElement(By.className("shopping_cart")).click();
		
		WebElement shoppingCart = driver.findElement(By.className("shopping_cart"));
		shoppingCart.click();
       /* boolean result;
        try {
            driver.quit();
        } catch(Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            driver.close();
        }
		*/
	}
	
}
