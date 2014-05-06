package testautomation;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import java.io.FileReader;
import java.io.IOException;
import au.com.bytecode.opencsv.*;

public class suite2 {
	
	//Declaration of Public variables
		public String BrowserConfig="Firefox";
		public WebDriver driver;
		String [] nextLine;

		//Launches the selected browser
		@BeforeSuite	
		public void StartUp() throws IOException
		{  
			//driver = new FirefoxDriver();	
			if (BrowserConfig=="InternetExplorer")
			{
				//Fetching the driver for Internet Explorer
				
				System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer_x64_2.27.0\\IEDriverServer.exe");
				
				//Launching the browser
				driver = new InternetExplorerDriver();	
				
				//Maximizing the window
				
				driver.manage().window().maximize();		
				
				//Entering the URL
				
				driver.get("https://www.cloudtest.com");
			    driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);   
			}
			else if (BrowserConfig=="Firefox")			
			{
				//Launching the browser
				
				driver = new FirefoxDriver();
				
				//Maximizing the window
				
				driver.manage().window().maximize();
				
				//Entering the URL
				driver.get("https://www.cloudtest.com");
			    driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			 
			}
			else if (BrowserConfig=="Chrome")
			{   
				//Fetching the driver for Chrome
				System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriverServer_x32\\chromedriver.exe");
				
				//Launching the browser
				driver = new ChromeDriver();	
				
				//Maximizing the window
				driver.manage().window().maximize();		
				
				//Entering the URL
				driver.get("https://www.cloudtest.com");
			    driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			}
			else
			{
				//Launching the browser
				driver = new SafariDriver();	
				
				//Maximizing the window
				driver.manage().window().maximize();		
				
				//Entering the URL
				driver.get("https://www.cloudtest.com");
			    driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			}
		}
			
		//Test to login with Invalid Credentials
		//Validates the message displayed		
		@Test(priority=0)
	    public void InvalidLogin()throws IOException
		{	
		    
			CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\UserInvalidLoginParameter.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{
	            //Entering values in username and password field
				
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				driver.findElement(By.name("username")).sendKeys(nextLine[0]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				driver.findElement(By.name("password")).sendKeys(nextLine[1]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking the login button
				
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				driver.findElement(By.className("btn" )).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Checking if the error message is displayed
				
				    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
					if(driver.findElement(By.xpath("//*[@id='confirm-modal']/div[1]")).isDisplayed())
					{
						driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						String NotificationExpect="Username and/or password is incorrect";
						driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						String NotificationActual=driver.findElement(By.xpath("//*[@id='confirm-modal']/div[2]/span")).getText();
						Assert.assertEquals(NotificationActual, NotificationExpect);		
						driver.findElement(By.xpath("//*[@id='confirm-modal']/div[3]/button")).click();
					}
					else
					{
						Assert.fail();
					} 
				//Clears the Username and password field	
					
				driver.manage().timeouts().implicitlyWait(220, TimeUnit.SECONDS);
				driver.findElement(By.name("username")).clear();
				driver.manage().timeouts().implicitlyWait(220, TimeUnit.SECONDS);
				driver.findElement(By.name("password")).clear();
			}
			reader.close();
		}

		
		//Test to login with valid Credentials	
		@Test(priority=1)
		public void ValidCredentialLogin() throws IOException
		{
			CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\UserValidLoginParameter.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{	
				//Entering values in Username and Password field
				
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				driver.findElement(By.name("username")).sendKeys(nextLine[0]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				driver.findElement(By.name("password")).sendKeys(nextLine[1]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on login button
				
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				driver.findElement(By.className("btn" )).click();
				
				//Verifies correct user is logged into the system
				
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				String ExpectedUserText="admin";
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				String ActualUserText=driver.findElement(By.xpath("//*[@id='header-toolbar']/ul/li[1]/a/span")).getText();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				Assert.assertEquals(ActualUserText, ExpectedUserText);
			  }
				    reader.close();
		}
			
		//Test to change and save password    
		//Validates the message displayed		  
		/*@Test(priority=2)
		public void PasswordChange()
		{
			
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);  
			driver.findElement(By.xpath(".//*[@id='header-toolbar']/ul/li[1]/a/span")).click();
			
			
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);  
			driver.findElement(By.xpath(".//*[@id='header-toolbar']/ul/li[1]/ul/li[1]")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id='confirm-modal']/div[2]/span/form/div/div/div/div[1]/div/input")).sendKeys("password");	    
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id='confirm-modal']/div[2]/span/form/div/div/div/div[2]/div/input")).sendKeys("passwords");
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id='confirm-modal']/div[2]/span/form/div/div/div/div[3]/div/input")).sendKeys("passwords");
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id='confirm-modal']/div[3]/button[2]")).click();
		    driver.manage().timeouts().implicitlyWait(260, TimeUnit.SECONDS);	
		    
		    
		    String NotificationExpect="Successfully changed password";
			String NotificationActual=driver.findElement(By.xpath("//*[@id='confirm-modal']/div[2]/span")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(260, TimeUnit.SECONDS);
			driver.findElement(By.xpath(".//*[@id='confirm-modal']/div[3]/button")).click();
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);						
		 }*/
		
		//Test to cancel password change		  
		@Test(priority=3)
		public void CancelPasswordChange()
		{
			driver.manage().timeouts().implicitlyWait(220, TimeUnit.SECONDS);			
			
			//Clicking on Admin toolbar
			
			driver.findElement(By.xpath(".//*[@id='header-toolbar']/ul/li[1]/a/span")).click();			
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Clicking on Change password option
			
			driver.findElement(By.xpath("/html/body/div/div/nav/div/div[2]/ul/li/ul/li/span")).click();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
			//Entering the current password in old password field
			
			driver.findElement(By.xpath("//*[@id='old_password']")).sendKeys("password");	    
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    
		    //Entering the new password in new password field
		    
		    driver.findElement(By.xpath("//*[@id='new_password']")).sendKeys("password1");
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    
		    //Entering the new password in confirm password field
		    
		    driver.findElement(By.xpath("//*[@id='new_password_confirm']")).sendKeys("password1");
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    
		    //Clicking on Cancel button
		    driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
		    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		    
		    //Verifying if user is landed in home page
		    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			String ExpectedUserText="admin";
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			String ActualUserText=driver.findElement(By.xpath("/html/body/div/div/nav/div/div[2]/ul/li/a/span")).getText();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			Assert.assertEquals(ActualUserText, ExpectedUserText);     
		 }
		
	      //Test to Change Default Group in Actions menu	
		  //Validates the pop up message
		  @Test(priority=4)
		  public void ChangeDefaultGroup()
		  {
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);		
			
			//Clicking on Admin toolbar
			driver.findElement(By.xpath("//*[@id='group_current']")).click();			 
		    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		    
		    //Clicking on Change Default option
		    
	        driver.findElement(By.xpath("/html/body/div/div/nav/div/div[2]/ul/li[2]/ul/li[17]/span")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on OK button
	        driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();

		  }

		  // Test to verify list of available settings
		 //Validates the Settings page
		 @Test(priority=5)
		 public void Settings()
		 {              
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Clicking on settings button
			
			driver.findElement(By.xpath("/html/body/div/header/nav/div/div[2]/ul/li[2]/a/span")).click(); 
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Checking if 'Settings' is displayed on the page
	        
	        if(driver.findElement(By.xpath("//*[@id='admin_section']/div/div[1]")).isDisplayed())
			{
	        	String NotificationExpect="Settings";
				String NotificationActual=driver.findElement(By.xpath("//*[@id='settings_heading']")).getText();
				Assert.assertEquals(NotificationActual, NotificationExpect);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on Manage Home in Settings
				driver.findElement(By.xpath("//*[@id='admin_items']/li[1]/a/span[2]")).click();
			}
	        else
			{
	        	Assert.fail();
			}
		 }
	              
	     // Test to Edit Home Page Message		  
		 @Test(priority=6)
		 public void EditHomePage()
		 {              
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Clicking on Edit button
			driver.findElement(By.id("edit-btn")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //To clear the displayed home message
	        driver.findElement(By.id("home-message")).clear();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);    
	        
	        //To type a Welcome message
	        driver.findElement(By.id("home-message")).sendKeys("Welcome");
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on save button
	        driver.findElement(By.id("edit-btn")).click();
	        
	        //Verifying if the welcome message is displayed in the landing page
	        driver.findElement(By.xpath("/html/body/div/header/nav/div/div[2]/ul/li/a/span")).click(); 
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Message";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/h3")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);      
		 }              
	           
		// Test to get the list of Manage Security options		  
		@Test(priority=7)
		public void ManageSecurity()
		{         
	        
	        //Clicking on settings button
	        driver.findElement(By.xpath("/html/body/div/header/nav/div/div[2]/ul/li[2]/a/span")).click(); 
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Manage Security in Settings
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/ul/li[2]/a")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	    }
	              
		
		// Test to add and save a new role              
	/*	@Test(priority=8)
		public void SaveRole() throws IOException
		{	
			CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\ManageRolesParameter.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{        
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Add a New role option
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/h3/button")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering the role name
	        By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Selecting Permissions from list of permissions
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[3]/div/select/option[2]")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
	        
	        //Clicking on the right arrow
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[3]/div[2]/button")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS); 
	        
	        //Clicking on save button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button[2]")).click(); 
	        
	        //Verify if user is navigated to Manage Security home page
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);     
			}
			reader.close();
		 } 
		*/
		
			  
		//Test to add and cancel a new role
		@Test(priority=9)
		public void CancelRole() throws IOException
		{	
			CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\ManageRolesParameter.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{        
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Add a New role option
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/h3/button")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering the role name
	        By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Selecting Permissions from list of permissions
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[3]/div/select/option[2]")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
	        
	        //Clicking on the right arrow
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[3]/div[2]/button")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS); 
	        
	        //Clicking on cancel button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click(); 
	        
	        //Verify if user is navigated to Manage Security home page
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);     
			}
			reader.close();
		 }
		
		//Test to refresh role
		@Test(priority=11)
		public void RefreshRole()
		{
			//Clicking on Refresh button 
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/h3/span[2]")).click();		
			
			//Verifying if the page is refreshed
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  		
		}
				  
		//Test to View role details
		@Test(priority=12)
		public void ViewDetails()
		{
			//Clicking on View Role Details button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/div/table/tbody/tr/td[2]/span[2]")).click();
			
			//Clicking on OK button
			driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
			
			//Verifying if user is landing in Manage Security home page
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  			
		}		
		
		//Test to Edit and Cancel the role
		@Test(priority=13)
		public void EditDetails()
		{
			//Clicking on Edit button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/div/table/tbody/tr/td[2]/span")).click();
			
			//Editing the role name
			By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys("test123");
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
	        
	        //Clicking on Cancel button
	        driver.findElement(By.xpath("//*[@id='rbac-form-actions']/button[1]")).click();       
	        
	        //Verifying if user is navigated to Roles home page
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  		      
		}
			
		//Test to add and cancel Groups
		@Test(priority=14)
		 public void CancelManageGroups() throws IOException
			{	
			    
				CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\ManageGroupsParameter.csv"));
				while ((nextLine = reader.readNext()) != null) 
				{
					
			//Clicking on Add a new group in Manage Groups
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[2]/h3/button")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Entering the group name
			By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Choosing the parent group
	        driver.findElement(By.xpath("//*[@id='assignable_parent_groups']")).click();
	        
	        //Choosing the Role
	        driver.findElement(By.xpath("//*[@id='role_data']")).click();
	        
	        //Clicking on Cancel button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();		
	        
	        //Verifying if the user is navigated to the Manage groups home page
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);          
				}
				reader.close();
		}
		
		//Test to Refresh Groups
		@Test(priority=15)
		public void RefreshGroup()
		{
			//Clicking on Refresh button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[2]/h3/span[2]")).click();		
		}
		
		//Test to view group details in Groups
		@Test(priority=16)
		public void ViewGroupDetails()
		{
			//Clicking on View Group Details button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
			
			//Verifying the pop up message
			if(driver.findElement(By.xpath("/html/body/div[4]/div")).isDisplayed())
			{
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				String NotificationExpect="Parent Group";
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				String NotificationActual=driver.findElement(By.xpath("/html/body/div[4]/div[2]/span/h3")).getText();
				Assert.assertEquals(NotificationActual, NotificationExpect);
				
				//Clicking OK button
				driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
			}
			else
			{
				Assert.fail();
			} 
						
			   //Verifying if the user is navigated to the Manage Security home page
	           driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	           String NotificationExpect="Manage Security";
			   String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			   Assert.assertEquals(NotificationActual, NotificationExpect);
			   driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);    		
		 }
			

		//Test to Edit group in Groups
		@Test(priority=17)
		public void EditGroup()
		{
			//Clicking on Edit button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[2]/div/table/tbody/tr/td[2]/span")).click();	
				
			//Editing the name field
			By.name("col-sm-4");
			driver.findElement(By.name("name")).sendKeys("Group123");
		    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        
		    //Clicking on Cancel button
		    driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();       
		}		
			
		//Test to add and cancel Users
		@Test(priority=18)
	    public void CancelManageUsers() throws IOException
		{	
		    
			CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\ManageUsersParameter.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{
				
			//Clicking on Add new user in Manage Users
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[3]/h3/button")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Entering the first name
			By.name("col-sm-4");
		    driver.findElement(By.name("first_name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering the last name
	        By.name("col-sm-4");
		    driver.findElement(By.name("last_name")).sendKeys(nextLine[1]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering the username
	        By.name("col-sm-4");
		    driver.findElement(By.name("user_name")).sendKeys(nextLine[2]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering the password
	        By.name("col-sm-4");
		    driver.findElement(By.name("password")).sendKeys(nextLine[3]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering Confirm password
	        By.name("col-sm-4");
		    driver.findElement(By.name("password_confirm")).sendKeys(nextLine[4]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Selecting the Groups
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[11]/div/select/option")).click();
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[11]/div[2]/button")).click();
	        
	        //Clicking on Cancel button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();		
	        
	        //Verifying if user is navigated to Manage Users home page
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}
			reader.close();
		}
		
		//Test to Refresh Manage Users
		@Test(priority=19)
		public void RefreshUsers()
		{
			//Clicking on Refresh button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[3]/h3/span[2]")).click();		
		}
		
		//Test to view user details in Manage Users
			@Test(priority=20)
			public void ViewUserDetails()
			{
				//Clicking on View User Details button
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[3]/div/table/tbody/tr/td[2]/span[2]")).click();
					
				//Verifying the pop up message
				if(driver.findElement(By.xpath("/html/body/div[4]/div")).isDisplayed())
				{
				   driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				   String NotificationExpect="Default Group";
				   driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				   String NotificationActual=driver.findElement(By.xpath("/html/body/div[4]/div[2]/span/h3")).getText();
				   Assert.assertEquals(NotificationActual, NotificationExpect);
						
					//Clicking OK button
					driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
				}
				else
				{
					Assert.fail();
				} 
			}
			
		//Test to Edit in Users
		@Test(priority=21)
		public void EditUsers()
		{
			//Clicking on Edit button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[3]/div/table/tbody/tr/td[2]/span")).click();		
				
			//Editing the name field
			By.name("col-sm-4");
			driver.findElement(By.name("last_name")).sendKeys("User123");
		    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        
		    //Clicking on Cancel button
		    driver.findElement(By.xpath("//*[@id='rbac-form-actions']/button[1]")).click();       
		}		
		
		//Test to add and cancel Projects
		@Test(priority=22)
		public void CancelManageProjects() throws IOException
		{	
		    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\ManageProjectsParameter.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{
			
			//Clicking on add Projects
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[4]/h3/button")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Entering Name in Add Projects
			By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Cancel button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();	
	        
	        //Verifying if user is navigated to Manage Projects home page
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Manage Security";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);      
			}
			reader.close();
		}
		
		//Test to Refresh Projects
		@Test(priority=23)
		public void RefreshManageProjects()
		{
			//Clicking on Refresh button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[4]/h3/span[2]"));		
		}
			
		//Test to view user details in Manage Projects
		@Test(priority=24)
		public void ViewProjectDetails()
		{
			//Clicking on View User Details button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[4]/div/table/tbody/tr/td[2]/span[2]")).click();
						
			//Verifying the pop up message
			if(driver.findElement(By.xpath(".//*[@id='confirm-modal']/div[1]")).isDisplayed())
			{
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				String NotificationExpect="Details for Project Web Testing";
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				String NotificationActual=driver.findElement(By.xpath("/html/body/div[4]/div")).getText();
				Assert.assertEquals(NotificationActual, NotificationExpect);
							
				//Clicking OK button
				driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
			}
			else
			{
				Assert.fail();
			} 
		}
			
		//Test to Edit in Manage Projects
		@Test(priority=25)
		public void EditProject()
		{
			//Clicking on Edit button
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[4]/div/table/tbody/tr/td[2]/span")).click();		
					
			//Editing the name field
			By.name("col-sm-4");
			driver.findElement(By.name("name")).sendKeys("Project123");
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			        
			 //Clicking on Cancel button
			 driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();       
		}		
		
		//Test to Manage Cloud Credential
		@Test(priority=26)
		public void ManageCloudCredential()
		{ 
			//Clicking on Manage Cloud Credentials
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/ul/li[3]/a/span[2]")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Verifying if user is navigated to Manage Cloud Credential home page
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Credentials";
			String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
			Assert.assertEquals(NotificationActual, NotificationExpect);
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			
		}
		
		//Test to add and cancel AWS Cloud Credential
		@Test(priority=27)
		public void AWSCloudCredential() throws IOException
		{	
		    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\AWSCloudCredential.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{
			//Clicking on AWS Credential
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/button")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Entering the label name		
			By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Selecting the Groups
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div/select/option")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Right arrow
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div[2]/button")).click();
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering Secret Access key           
	        By.className("col-sm-4");
		    driver.findElement(By.name("secret_access_key")).sendKeys("147852");
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Entering the Access key ID
	        By.className("col-sm-4");
		    driver.findElement(By.name("access_key_id")).sendKeys("12365478910");
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Cancel button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();
	        
	        //Verifying if user is navigated to Manage Cloud Credential home page
	      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Credentials";
	      	String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
	      	Assert.assertEquals(NotificationActual, NotificationExpect);
	      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);         
			}
			reader.close();
		}
		
		//Test to add and cancel Azure Cloud Credential
		@Test(priority=28)
		public void AzureCloudCredential() throws IOException
		{	
		    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\AzureCloudCredential.csv"));
			while ((nextLine = reader.readNext()) != null) 
			{
			//Clicking on Azure Cloud Credential
			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/button[2]")).click();
			driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			
			//Entering the Label name
			By.name("col-sm-4");
		    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Selecting Groups
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div/select/option")).click();
	        
	        //Clicking on right arrow
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div[2]/button")).click();
	        
	        //Entering the subscription ID
	        By.className("col-sm-4");
		    driver.findElement(By.name("subscription_id")).sendKeys("147852");
	        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        
	        //Clicking on Cancel button
	        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();	
	        
	        //Verifying if user is navigated to Manage Cloud Credential home page
	      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
	        String NotificationExpect="Credentials";
	      	String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
	      	Assert.assertEquals(NotificationActual, NotificationExpect);
	      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}
			reader.close();
		}
		
		//Test to add and cancel Eucalyptus Cloud Credential
			@Test(priority=29)
			public void EucalyptusCloudCredential() throws IOException
			{	
			    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\EucalyptusCloudCredential.csv"));
				while ((nextLine = reader.readNext()) != null) 
				{ 
				//Clicking on Eucalyptus Cloud Credential
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/button[3]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Entering the Label name
				By.className("col-sm-4");
			    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
		        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        
		        //Selecting the Groups
		        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div/select/option")).click();
		        
		        //Selecting the right arrow
		        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div[2]/button")).click();
		        
		        //Entering the Host name
		        By.className("col-sm-4");
			    driver.findElement(By.name("host")).sendKeys(nextLine[1]);
		        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        
		        //Entering the Secret Access Key
		        By.className("col-sm-4");
			    driver.findElement(By.name("secret_access_key")).sendKeys(nextLine[2]);
		        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        
		        //Entering the Access Key ID
		        By.name("col-sm-4");
			    driver.findElement(By.name("access_key_id")).sendKeys(nextLine[3]);
		        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        
		        //Clicking on Cancel button
		        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();		
		        
		        //Verifying if user is navigated to Manage Cloud Credential home page
		      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        String NotificationExpect="Credentials";
		      	String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
		      	Assert.assertEquals(NotificationActual, NotificationExpect);
		      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}
				reader.close();
			}
			
			//Test to add and cancel Openstack Cloud Credential
			@Test(priority=30)
			public void OpenstackCloudCredential() throws IOException
			{	
			    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\OpenstackCloudCredential.csv"));
				while ((nextLine = reader.readNext()) != null) 
				{ 
				//Clicking on Openstack Cloud Credential
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/button[5]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						
				//Entering the Label name
				By.name("col-sm-4");
			    driver.findElement(By.name("name")).sendKeys(nextLine[0]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Selecting Groups 
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div/select/option")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Click on right arrow
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div[2]/button")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Entering the Password
				By.className("col-sm-4");
			    driver.findElement(By.name("password")).sendKeys(nextLine[1]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				        
				//Entering the Tenant name
				By.name("col-sm-4");
			    driver.findElement(By.name("tenantname")).sendKeys(nextLine[2]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				        
				//Entering the User
				By.name("col-sm-4");
	            driver.findElement(By.name("user")).sendKeys(nextLine[3]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Entering the Identity URL
				By.name("col-sm-4");
	            driver.findElement(By.name("identity")).sendKeys(nextLine[4]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				        
				//Clicking on Cancel button
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();		
				
				//Verifying if user is navigated to Manage Cloud Credential home page
		      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
		        String NotificationExpect="Credentials";
		      	String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
		      	Assert.assertEquals(NotificationActual, NotificationExpect);
		      	driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}	
				reader.close();
			}
			
			//Test to add and cancel Rackspace Cloud Credential
			@Test(priority=31)
			public void RackspaceCloudCredential() throws IOException
			{	
			    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\RackspaceCloudCredential.csv"));
				while ((nextLine = reader.readNext()) != null) 
				{ 
				//Clicking on Rackspace Cloud Credential
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/button[6]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						
				//Entering the Label name
				By.name("col-sm-4");
				driver.findElement(By.name("name")).sendKeys(nextLine[0]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Selecting groups
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div/select/option[2]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on right arrow
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div/div[2]/div[2]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Entering the Account name
				By.name("col-sm-4");
			    driver.findElement(By.name("account")).sendKeys(nextLine[1]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				        
				//Entering the API Key
				By.name("col-sm-4");
	            driver.findElement(By.name("apikey")).sendKeys(nextLine[2]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				        
			    //Entering the User
				By.name("col-sm-4");
	            driver.findElement(By.name("user")).sendKeys(nextLine[3]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				        
				//Clicking on Cancel button
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();		
				 
				//Verifying if user is navigated to Manage Cloud Credential home page
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			    String NotificationExpect="Credentials";
			    String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
			    Assert.assertEquals(NotificationActual, NotificationExpect);
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}
				reader.close();
			}
			//Test to add and cancel VMWare Cloud Credential
			@Test(priority=32)
			public void VMWareCloudCredential() throws IOException
			{	
			    CSVReader reader = new CSVReader(new FileReader("C:\\InputData\\VMWareCloudCredential.csv"));
				while ((nextLine = reader.readNext()) != null) 
				{  
				//Clicking on VMWare Cloud Credential
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/button[10]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
								
				//Entering the Label name
				By.name("col-sm-4");
				driver.findElement(By.name("name")).sendKeys(nextLine[0]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						        
				//Entering the Password
				By.name("col-sm-4");
				driver.findElement(By.name("password")).sendKeys(nextLine[1]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						        
				//Entering the User
				By.name("col-sm-4");
			    driver.findElement(By.name("user")).sendKeys(nextLine[2]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						        
			    //Entering the Host
				By.name("col-sm-4");
			    driver.findElement(By.name("location")).sendKeys(nextLine[3]);
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						        
				//Clicking on Cancel button
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/button")).click();		
				
				//Verifying if user is navigated to Manage Cloud Credential home page
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			    String NotificationExpect="Credentials";
			    String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
			    Assert.assertEquals(NotificationActual, NotificationExpect);
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}
				reader.close();
			}
			
			//Test to refresh credentials in Manage Cloud Credentials
			@Test(priority=33)
			public void RefreshCredential()
			{
			  driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			  
			  //Clicking on Refresh button
			  driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div[2]/ul/li/span")).click();
			  
			}
			
			//Test to view credential details
			@Test(priority=34)
			public void ViewCredential()
			{
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on View Details button in the listed credentials
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/span[2]")).click();
				
				//Verifying the pop up message
				if(driver.findElement(By.xpath("/html/body/div[4]/div")).isDisplayed())
				{
					driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
					String NotificationExpect="Groups";
					driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
					String NotificationActual=driver.findElement(By.xpath("/html/body/div[4]/div[2]/span/h3")).getText();
					Assert.assertEquals(NotificationActual, NotificationExpect);
					
					//Clicking OK button
					driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
				}
				else
				{
					Assert.fail();
				} 
				
			}
			
			//Test to Edit and save a listed cloud credential
			@Test(priority=35)
			public void EditandSaveCredential()
			{
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on Edit button in the listed cloud credential
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/span")).click();
				
				//Editing the credential details
				By.name("col-sm-4");
				driver.findElement(By.name("account")).sendKeys("User123456");
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			    
			    //Clicking on Cancel button
			    driver.findElement(By.xpath("//*[@id='rbac-form-actions']/button[1]")).click(); 			
			}
			
			//Test to ingest and cancel Manage Cloud Services
			@Test(priority=36)
			public void IngestCloudServices()
			{
				//Clicking on Ingest Cloud Services 
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/ul/li[4]/a/span[2]")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on Magic button in Manage Cloud Services
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/div/p[2]/button")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Verifying the pop up message
				if(driver.findElement(By.xpath("/html/body/div[4]/div")).isDisplayed())
				{
					driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
					String NotificationExpect="Are you sure you want to ingest cloud compute services? This could take some time.";
					driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
					String NotificationActual=driver.findElement(By.xpath("/html/body/div[4]/div[2]/span")).getText();
					Assert.assertEquals(NotificationActual, NotificationExpect);
					
					//Clicking the Cancel button
					driver.findElement(By.xpath("/html/body/div[4]/div[3]/button")).click();
				}
				else
				{
					Assert.fail();
				} 
					
			}
			
			//Test to ingest and save Manage Cloud Services
		/*	@Test(priority=37)
					public void IngestCloudService()
					{
						//Clicking on Ingest Cloud Services 
						driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/ul/li[4]/a/span[2]")).click();
						driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						
						//Clicking on Magic button in Manage Cloud Services
						driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/div/p[2]/button")).click();
						driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
						
						//Verifying the pop up message
						if(driver.findElement(By.xpath("/html/body/div[4]/div")).isDisplayed())
						{
							driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
							String NotificationExpect="Are you sure you want to ingest cloud compute services? This could take some time.";
							driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
							String NotificationActual=driver.findElement(By.xpath("/html/body/div[4]/div[2]/span")).getText();
							Assert.assertEquals(NotificationActual, NotificationExpect);
							
							//Clicking the Save button
							//driver.findElement(By.xpath("/html/body/div[4]/div[3]/button[2]")).click();
						}
						else
						{
							Assert.fail();
						} 
							
					}*/
			
			//Test to refresh Ingest Cloud Services
			@Test(priority=38)
			public void RefreshIngestCloudServices()
			{
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on refresh button
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]/div/ul/li/span")).click();
			}
			
			//Test to click on Manage Marketplace
			@Test(priority=39)
			public void ManageMarketplace()
			{
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on Manage Marketplace
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/ul/li[5]/a")).click();
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Verifying if user is navigated to Marketplace page
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
			    String NotificationExpect="Overview";
			    String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/h3/a")).getText();
			    Assert.assertEquals(NotificationActual, NotificationExpect);
			    driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);  
			}
			
			//Test to create AWS product
			
			
			//Test to click on Manage SLAs
			@Test(priority=40)
			public void ManageSLAs()
			{
				driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				
				//Clicking on Manage Reports
				driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/ul/li[6]/a")).click();
				
				//Verifying the pop up
				if(driver.findElement(By.xpath(".//*[@id='confirm-modal']/div[1]")).isDisplayed())
				{
				   driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				   String NotificationExpect="Service Level Agreements";
				   driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
				   String NotificationActual=driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div/div/div/h2")).getText();
				   Assert.assertEquals(NotificationActual, NotificationExpect);
				}
				else
				{
					Assert.fail();
				} 		
			}
			 //Close the browser
		     @AfterSuite
		     public void tearDown() throws Exception 
		     {
		    	 driver.quit(); 
		     }
		}
			

