		Testing dress selection and addition to shopping cart on an Automation Demo web site

Purpose: 

	This is done as a skills evaluation project for LookingGlass. 

Getting Started: 
	
	Java needs to be installed in a system to run this program. 

	Code can be compiled first before execution. Or it can be executed from an IDE such as Eclipse with Selenium Webdriver installed as an external library. 

Prerequisites: 

	Firefox needs to have geckodriver installed. 
	Chrome needs to have ChromeDriver installed. 

What things you need to install the software and how to install them: 

	Java, Selenium Webdriver, Firefox, geckodriver, Chrome, ChromeDriver can be downloaded from their free repositories respcetively. 
	
Steps for setting up the testing environment: 

	Install Java in testing server (JRE or JDK). 
	Install Eclipse. 
	Download and install Firefox and geckodriver. 
	Download and install Chrome and ChromeDriver. 
	Download project files and add project in Eclipse. 
	Setup Selenium in Eclipse as the following: 

		Right-click on test project and select Properties.
		On the Properties window, click on “Java Build Path”.
		Click on the Libraries tab, and then click “Add External Archives...”
		Navigate to selenium Webdriver folder. 
		Select and add the JAR files.	

Testing steps: 

	1. Navigate to Dresses page. 
	2. Add each dress to shopping cart. 
		Check each dress price while on this page. 
		Close the popup window after adding a dress. 
	3. Navigate to shopping cart. 
	4. Check each selected dress is in the cart. 
	5. Check each dress has the correct price. 
	6. Check the total price is the sum of each individual dresses. 

Purpose: 
	
	These steps check the complete flow for a customer to select and then check out the correct items. 

To run the test: 

	From Eclipse, select the Java class with the main method and select Run. 

Built With: 

	Eclipse, Selenium, JUnit. 

Credit: 

	Design of this test case was provided by Todd Sackett of LookingGlass. 
	
Author: 

	Jianguo Li

License: 

	This project can be used for academic purpose at no cost where proper reference of the original work is provided. Free use is limited to non-commercial purpose only, however. 

Acknowledgments: 

	Tremendous resources online! 
	Patience from Todd. 
	Checkups and nudges from Nicholas Grasso, the recruiter for this position. 