# Test Automation Framework with Selenium and Cucumber

[![License](https://img.shields.io/badge/license-GPLv3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0.html)

License: GPL v3. [GPL License](http://www.gnu.org/licenses)

## Introduction

This Framework wraps selenium's webdriver, removing the overhead of managing timing issues, frame selection, browsers support.

All elements of the framework have default realizations that would be used in most cases. 
You can override elements behavior on any level: just for this element or customize the scenario of actions for all elements.

The framework contains basic test scenario with cucumber and and Allure Report. 

## Browsers

All interactions with the WebDriver instance are wrapped into the Browsers.
Test framework is configured to support Chrome, Firefox, IE, Microsofat Edge locally and Selenium hub.
Also supported Chrome device emulator. 


To configure and start the driver:
```java
Browsers.newBrowser(BrowserType.CHROME).start();
```

### Managing

Browser has following opportunities:
   *  managing window;
   *  managing ConsoleLogs;
   *  managing NetworkLogs;
   *  managing Cookies;
   *  actions on WebDriver;
   *  and others. 

## Managing multiple browser sessions

Sometimes when running your tests, you need a separate browser independent of the session you have at the current moment.
```java
Browser one = Browsers.newBrowser(BrowserType.CHROME).start();
Browser two = Browsers.newBrowser(BrowserType.CHROME).start();
Browsers.addBrowser(two);
one.get(URL);

.....

Browsers.stopAllBrowsers();
```


## Web Elements

All selenium elements are created using an driver. The biggest advantage here is that we're able to determinate elements 
behavior before actions, such as scroll, waiting and others.

## Setup your PageObjects

```java
public class GooglePage extends BasePage<GooglePage>{

    @Getter
    @FindBy(css = "input#lst-ib")
    @Title(value = "This is search input element")
    TextInputElement searchInputElement;

    @Getter
    @FindAll({
        @FindBy(css = "input[name='btnK']"),
        @FindBy(xpath = "//*[@name='btnK']"),
        @FindBy(css = "button#_fZl")})
    @Title(value = "Search Google")
    ButtonElement searchButton;

    @Getter
    @FindBy(css = "input[name='btnI']")
    @Title(value = "I'm Feeling Lucky")
    ButtonElement luckyButton;

    @Getter
    @FindBy(css = "#rso > div > div > div")
    @Title(value = "The search results")
    List<SearchItem> searchItems;

    public GooglePage() {
        super();
    }

    @Override
    public boolean isOpen() {
        return searchInputElement.isDisplayed();
    }

    @Override
    public GooglePage waitUntilPageToBeOpen() {
        waitJSReady();
        searchInputElement.waitUntilDisplayed();
        return this;
    }
}

``` 

```java
public class SearchItem extends BaseComponent{

    @Getter
    @FindBy(css = "h3 > a")
    @Title(value = "Title of search result")
    LinkElement titleLink;

    @Getter
    @FindBy(xpath = ".//span[@class='st']")
    @Title(value = "Description")
    TextAreaElement description;


    protected SearchItem(WebElement baseElement) {
        super(baseElement);
    }

    protected SearchItem(Element baseElement) {
        super(baseElement);
    }
}

```

### Creating an element

You can get lists of elements or simple element relative to another element:
```java
TextInputElement textInputElement =  baseElement.createTextInputElement(By.tagName("input"));
List<TextInputElement> textInputElements = baseElement.createTextInputElements(By.tagName("input"));
```

##  Test properties

The parameters are defined in files framework.properties and in the Config.class.
In the Config.class defined parameters for browser, driver, selenium hub which are read from the Environment variables if exist, otherwise takes the default value.

## Results 

You can observe results in console log or in Allure report (`> mvn allure:report`).
Framework create screenshot after error, but also can do it after every action (`elements.reporting=true`).
Also framework print and attach simple description after error action on WebElement.

[Screenshot error](./images/Screenshot%20error.PNG)

[Text Error Description](./images/Text%20Error%20Description.PNG)
 