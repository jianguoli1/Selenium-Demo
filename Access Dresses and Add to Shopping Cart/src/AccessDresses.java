import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AccessDresses {
	
	static WebDriver driver;
    static Wait<WebDriver> wait;
    static String url;
    static String browserType;


	public static void main(String[] args) throws InterruptedException  {
		
		//need to handle file not found exception and such
		try {
			//read config file for url, browser type
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
		    br.close();
		    
		}
		catch (Exception e ) {
			
			e.printStackTrace();
		}
		
		//select browser type based on parameter provided
		switch (browserType.toUpperCase()) {
			case "FIREFOX": driver = new FirefoxDriver();
				break;
			case "CHROME": 
				ChromeOptions options = new ChromeOptions();
			    options.setExperimentalOption("excludeSwitches",Arrays.asList("ignore-certificate-errors"));
			    driver = new ChromeDriver(options);
			    
				break;
			default: driver = new FirefoxDriver();
				break;
		}
		
		//load initial url
        driver.get(url);
        // Maximize window
        driver.manage().window().maximize();
        
        // Sleep for 5 seconds for page to load
     	Thread.sleep(5000);
        
     	//find all clothing links
        List<WebElement> Links = driver.findElements(By.className("sf-with-ul"));
        
        //index for DRESSES link
        int linkID = 0;
		// print the total number of elements
        System.out.println();
		System.out.println("There are a total of " + Links.size() + " selected links:");
 
		//iterator through all links
		Iterator<WebElement> iter = Links.iterator();
 
		//this will check whether list has some element or not
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
			System.out.println("\t"+"Link label is " + label);
			i++;
 
		}
		System.out.println("Index is " + linkID + " for link of interest.");
		System.out.println();
		//Clicking on the selected link
		Links.get(linkID).click();
		
		// Sleep for 5 seconds
     	Thread.sleep(5000);
     	
     	
     	//Check if we are on new page
     	System.out.println("Current web page is: " + driver.getCurrentUrl());
     	System.out.println();
     	
     	Thread.sleep(5000);
		
     	//place to keep dress names from catalog
		ArrayList <String> dressNamesCatalog= new ArrayList <String>();
     	//Look up dress names on page
     	try {
     		Document doc = Jsoup.connect(driver.getCurrentUrl()).get();
			//select dress names found on page
	        Elements dressLinks = doc.getElementsByAttributeValueEnding("alt", "Dress");
	        System.out.println("There are " +  dressLinks.size() + " dress names in catalog:");

	        
	        //display each dress name in console
	        for (Element src : dressLinks) {
	        	//add each dress name to ArrayList
	        	dressNamesCatalog.add(src.attr("alt"));
	        	//display each new dress name in console
	        	System.out.println( "\t"+ dressNamesCatalog.get(dressNamesCatalog.size()-1));
	        }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
		
     	
     	//temporary place to keep dress prices from catalog (has duplicates)
     	ArrayList <BigDecimal> dressPricesTemp= new ArrayList <BigDecimal>();
     	//place to keep dress prices from catalog (to remove duplicates)
     	ArrayList <BigDecimal> dressPricesCatalog= new ArrayList <BigDecimal>();
     	try {
     		Document doc = Jsoup.connect(driver.getCurrentUrl()).get();
     		//Find all prices for clothing type on new page
	        Elements priceLinks = doc.getElementsByAttributeValue("itemprop", "price");
	        System.out.println();
	        
	      //add each dress price found to ArrayList
	        for (Element src : priceLinks) {
	        	String price = src.text();
	         	dressPricesTemp.add(new BigDecimal(price.replace("$", "")));
	        }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
     	//weed out duplicates to have one copy of each value
     	dressPricesCatalog = (ArrayList<BigDecimal>) dressPricesTemp.stream().distinct().collect(Collectors.toList());
        // print the total number of prices found on page
        System.out.println("There are " + dressPricesCatalog.size() + " prices found in catalog for DRESSES:");

        //display each dress price in console
        for (BigDecimal price : dressPricesCatalog) {
         	//display each new dress price in console
         	System.out.println( "\t" + "$" + price);
        }
    
		
     	//select dresses available for check out
		List<WebElement> dressSelect = driver.findElements(By.xpath("//span[contains(text(),'Add to cart')]"));		
		System.out.println();
		System.out.println("Found " +  dressSelect.size() + " dresses ready for checkout.");	
		System.out.println();
		
				
		// Store the parent window
		String parentWindowHandler = driver.getWindowHandle(); 
		//Define a popup window
		String subWindowHandler = null;				
		
		
		System.out.println("Adding items to shopping cart...please wait.");
		System.out.println();
		
		//iterate through each item and check it out
		Iterator<WebElement> iterDresses = dressSelect.iterator();		
		while (iterDresses.hasNext()) {
 
			// pick an item
			WebElement item = iterDresses.next();
						
			//click to add item to cart
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", item);			
			
			
			//with a popup window, get all window handles
			Set <String> handles = driver.getWindowHandles(); 
			Iterator<String> handleIterator = handles.iterator();
			
			//define popup window
			while (handleIterator.hasNext()){
			    subWindowHandler = handleIterator.next();
			}
			
			//switch to popup window and wait to load
			driver.switchTo().window(subWindowHandler); 
		    Thread.sleep(5000);
		    
			//locate and click Continue button
		    driver.findElements(By.xpath("//span[contains(text(),'Continue shopping')]"));
			//driver.findElement(By.className("cross")).click();
						
			//switch back to parent window
			driver.switchTo().window(parentWindowHandler);  
		}			
      
		//Switch to popup window and proceed to cart
		driver.switchTo().window(subWindowHandler); 
		driver.findElement(By.xpath("//span[contains(text(),'Proceed to checkout')]")).click();		
		
		//Switch back to parent window
		driver.switchTo().window(parentWindowHandler);  
		//click on cart (for demo only, may not need)
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement shoppingCart = driver.findElement(By.className("shopping_cart"));
		js.executeScript("arguments[0].click();", shoppingCart);        
		
		//display current url for browser
		System.out.println("Current URL is: " + driver.getCurrentUrl());
		
		
		//place to keep dress names from cart
		ArrayList <String> dressNamesCart= new ArrayList <String>();
		//look up items in cart
		try {
			//read in cart content
			Document doc = Jsoup.connect(driver.getCurrentUrl()).get();
			//select cart items
			Elements cartItems = doc.getElementsByAttributeValueEnding("alt", "Dress");
			System.out.println();
			System.out.println("There are " + cartItems.size() + " dress names in cart:");			
			
			//iterate through cart items and display values
			Iterator<Element> iterCI = cartItems.iterator();
			while (iterCI.hasNext()) {
					 
				//select an item
				Element item = iterCI.next();
				//add each dress name to ArrayList
				dressNamesCart.add(item.attr("alt"));
				//display each new dress name in console
				System.out.println( "\t"+ dressNamesCart.get(dressNamesCart.size()-1));

			}
		}
		
		catch (Exception e ) {
			e.printStackTrace();
		}       		
				
		//keep track of unit prices found in cart
		ArrayList <BigDecimal> dressPricesCart= new ArrayList <BigDecimal>();
		//keep track of total price found in cart
		BigDecimal totalPrice = new BigDecimal(0);
		//look up dress prices in cart
     	try {
     		
     		//check current url for browser
     		System.out.println();
     		System.out.println("Current URL is: " + driver.getCurrentUrl());
     		
     		//locate unit price for each dress in cart
     		List <WebElement> priceList = driver.findElements(By.xpath("//table[@id='cart_summary']//td[@class='cart_unit']//span[1]//span[1]"));
     		//display the total number of prices found in cart
     		System.out.println();
     		System.out.println("Found " + priceList.size() + " prices in cart:");
     		
     		Iterator<WebElement> priceIterator = priceList.iterator();
     		//display each unit price found in cart
    		while (priceIterator.hasNext()) {
     
    			//going through price item one at a time
    			WebElement price = priceIterator.next();	
    			
    			
    			//get the text
    			String priceLabel = price.getText();
    			//save unit price to list
    			
    			dressPricesCart.add(new BigDecimal(priceLabel.replace("$", "")));
    			
    			//display unit price
    			System.out.println("\t" + priceLabel);
    			
    		}  	        	        
        
	        //locate and save total price in cart"
	        WebElement totalPriceLink = driver.findElement(By.xpath("//table[@id='cart_summary']//td[@id='total_product']"));
     		totalPrice = new BigDecimal(totalPriceLink.getText().replace("$",""));
	        System.out.println();
	        // print the total price found in cart
	        System.out.println("Total price is $" + totalPrice + " as found in cart.");
	        
	        
        }
        catch (Exception e) {
        	e.printStackTrace();
        }    	
     	     	
     	//check if correct dresses are in cart
     	System.out.println();
     	System.out.print("Dress names in cart match user selection? If no failure message...Success!");     	
     	org.junit.Assert.assertEquals(dressNamesCatalog, dressNamesCart);     	
     	
     	//check if item prices are correct in cart  
     	System.out.println();
     	System.out.print("Dress prices in cart match user selection? If no failure message...Success!");
     	org.junit.Assert.assertEquals(dressPricesCatalog, dressPricesCart);     	
     	
     	//check if total price is the sum of unit prices in cart
     	System.out.println();
     	System.out.print("Dress prices in cart add up to the total? If no failure message...Success!");
     	org.junit.Assert.assertEquals(totalPrice,   dressPricesCart.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
     	
     	try {
     		driver.close();;
        } catch(Exception e) {
            e.printStackTrace();
        } 
     	
	}
	
}
