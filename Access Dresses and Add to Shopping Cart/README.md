		Testing dress selection and addition to shopping cart on an Automation Demo web site

Purpose: 

	This is done as a skills evaluation project for LookingGlass. 

Getting Started: 
	
	Java needs to be installed in a system to run this program. 

	Code can be compiled first before execution. Or it can be executed from an IDE such as Eclipse with Selenium Webdriver installed as an external library. 

Prerequisites: 

	Firefox and Google Chrome web browsers. 
	Firefox needs to have geckodriver installed. 
	Chrome needs to have chromeDriver installed. 

Things you need to install and how to install them: 

	Java, Selenium Webdriver, Jsoup, Firefox, geckodriver, Chrome, ChromeDriver can be downloaded from their free repositories respectively. 
	
Steps for setting up the testing environment: 

	Install Java in testing server (JRE or JDK). 
	Install Eclipse. 
	Download and install Firefox and geckodriver. 
		Add path for geckodriver to config.properties, e.g. /home/plex/usr/geckodriver in unix. 
	Download and install Chrome and ChromeDriver. 
		Add path for chromedriver to config.properties, e.g. /home/plex/usr/chromedriver in unix. 
	Download project files and add project in Eclipse. 
	Download Selenium and Jsoup. Extract files if needed. 
	Setup Selenium and Jsoup files in Eclipse project as the following: 
		Copy and paste the .jar files to project folder. 
		Right-click on test project and select Properties.
		On the Properties window, click on �Java Build Path�.
		Click on the Libraries tab, and then click �Add JARs�. 
		Expand project folder and locate Selenium JAR file and Jsoup JAR file.  
		Select and add the JAR files, one at a time.	

Config file: 
	URL, browser type, geckodriver or chromedriver location are specified in the config.properties file. browser type and driver locations must be a matching set. For example:
http://automationpractice.com/ 
Chrome
/home/plex/usr/chromedriver	 

or 
http://automationpractice.com/
firefox
/home/plex/usr/geckodriver
	
Testing steps: 

	1. Navigate to Dresses page. 
	2. Add each dress to shopping cart. 
		Save each dress name on catalog. 
		Save each dress price on catalog. 
		Close the popup window after adding a dress. 
	3. Navigate to shopping cart. 
	4. Save each dress name in the cart.
	5. Save each dress price in the cart.
	6. Check each dress name matches between catalog page and cart. 
	7. Check each dress price matches between catalog page and cart. 
	8. Check the total price equals the sum of each individual dress in cart. 

Purpose: 
	
	These steps check that the complete flow for a customer to select and add the items to cart is handled correctly. 

To run the test: 

	From Eclipse, select the Java class with the main method and select Run. 

Built With: 

	Eclipse. 

Credit: 

	Design of this test case was provided by Todd Sackett of LookingGlass. 
	
Author: 

	Jianguo Li, MD MS

License: 

	This project can be used for academic purpose at no cost where proper reference to the original work is provided. Free use is limited to non-commercial purpose only, however. 

Acknowledgments: 

	Tremendous resources online!...But need to glean useful information out of this ocean. 
	Patience from Todd and his team. 
	Checkups and nudges from Nicholas Grasso, the recruiter for this position. 