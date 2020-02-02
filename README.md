In real world scenario we face issue while executing TestNG methods parallel as everyone know Selenium is not a thread safe. No worry here is solution!!!

Scenario – You have one application and you cannot operate its functionality without authorization (Login). You prepared test methods and that need to execute parallel.

Challenge – Async issue occur while executing methods in parallel if one instance of WebDriver will get shared across all test methods. Each test will attempt to execute its script and override each other

Solution – Create instance of WebDriver equal to thread count and share them across test methods.

Challenge – Difficult to identify which driver is acquired by test method and which one is free to use.

Solution – Create a Service class which will manage and provide WebDriver instance for methods.

Challenge – Execute pre-requisite function (Login) before every method execution.

Solution – Supply the pre-requisite code to Service to initiate every WebDriver.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Find Sample Test Project
https://github.com/PatilRohit/WebDriverParallelTest

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Add dependency into project

    <dependency>
        <groupId>com.github.patilrohit</groupId>
        <artifactId>WebDriverParallelService</artifactId>
        <version>0.0.1</version>
    </dependency>

Create instance  of WebDriver service inside Test class

Constructor = WDService(threadcount, Driver Type, PreStep);

    WDService ws = new WDService(3, WDService.CHROME, new PreStep() {		

        public void initDriver(WebDriver wd) {

          // TODO – Steps to initialize driver		

        }
    });
    
You can pass null for PreStep if you don't have any pre-requisite code
    
Acquire WebDriver inside Test method

    @Test
    public void testMethod(){
        WebDriver wd = WDService.getDriver(); 
        // Write you code
    }

And release it after

    @AfterMethod
    public void tearDown(ITestResult result){
        WDService.releaseDriver(result. getName())
    }

Finally quite all driver at end

    @AfterSuite
    public void cleanAtEnd(){
        WDService.close();
    }
