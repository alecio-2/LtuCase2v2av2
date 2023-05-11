package example;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LtuCase2v2av2 {
    //Starting from the LTU.Se page, one test case to verify that you can get to the button to create your transcript
    // (certificate of Registration) and then create one.

    //Adding logger functionality
    private static final Logger LOGGER = LoggerFactory.getLogger(LtuCase2v2av2.class);

    @Test
    @Order(1)
    public void setUp() {
        // Set the download folder
        Configuration.downloadsFolder = System.getProperty("user.dir") + "\\target\\Files";

        // Open the LTU website
        LOGGER.info("Starting program");
        try {
            Configuration.browser = "chrome";
            open("https://www.ltu.se");

            if (title().isEmpty()) {
                LOGGER.error("Failed to open the web page: empty title");
            } else {
                LOGGER.info("Successfully opened the web page: " + title());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to open the web page: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void clickCookiesButton() {
        // Click the "Accept" button on the cookies notification

        try {
            if ($(Selectors.byId("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).exists()) {
                $(Selectors.byId("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();

                LOGGER.info("The cookies notification is closed");
            } else {
                LOGGER.info("The cookies notification is not displayed");
            }
        } catch (ElementNotFound e) {
            LOGGER.error("The cookies button not found");
        }
    }

    @Test
    @Order(3)
    public void clickStudentLink() {
        //Click on the Student link
        try {
            $(Selectors.byXpath("/html/body/header/div[2]/div[1]/div[1]/div[3]/div/a[1]")).shouldBe(Condition.visible)
                    .click();
            LOGGER.debug("The STUDENT link is clicked");

        } catch (ElementNotFound e) {
            LOGGER.error("The STUDENT link is not displayed");
        }
    }

    @Test
    @Order(4)
    public void clickMittLTULink() {
        //Click on the Student link
        try {
            //Press the Mitt LTU element a[onclick*='group']
           $(byCssSelector("a[onclick*='group']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Mitt LTU link is clicked");

        } catch (ElementNotFound e) {
            LOGGER.error("The Mitt LTU link is not displayed");
        }
    }

    @Test
    @Order(5)
    public void credentials() {
        //Get credentials from Json file and enter them in the login form
        //Json file with username and password to login
        File jsonFile = new File("C:\\temp\\ltu.json");

        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            username = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();

            LOGGER.info("Json file is read");
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading the Json file: " + e.getMessage());
        }

        //Enter the username
        try {
            $(byId("username")).sendKeys(username);
            LOGGER.debug("The username is entered");
        } catch (Exception e) {
            LOGGER.error("The username  is not entered");
        }

        //Enter the password
        try {
            $(byId("password")).sendKeys(password);
            LOGGER.debug("The password is entered");
        } catch (Exception e) {
            LOGGER.error("The password is not entered");
        }

        //Click on submit button
        try {
            $(Selectors.byName("submit")).shouldBe(Condition.visible).click();
            LOGGER.debug("The login button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The login button is not found");
        }
    }

    @Test
    @Order(6)
    public void clickIntygLink() {
        //Go to ladok
        //Press the Intyg link element with id starting with something like "yui_patched_v3_11_0_1" and ending with
        // something like "271"
        try {
            $(byCssSelector("a[id^='yui_patched_v3_11_0_1'][id$='271']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Intyg link is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Intyg link is not found");
        }
    }

    @Test
    @Order(7)
    public void changeFocus() {
        //Change focus from one windows to another
        try {
            Selenide.switchTo().window(1);
            LOGGER.debug("The focus is changed to another window");
        } catch (Exception e) {
            LOGGER.error("The focus is not changed to another window");
        }
    }

    @Test
    @Order(8)
    public void choosingLTUinLadok() {
        //Here from ladok
        //Press the button to accept cookies
        try {
            $(byCssSelector("button.btn.btn-light")).shouldBe(Condition.visible).click();
            LOGGER.debug("The cookies notification is closed");
        } catch (ElementNotFound e) {
            LOGGER.error("The cookies notification button is not found");
        }

        //Press to choose the university
        try {
            $(byCssSelector("a[aria-label='Inloggning via ditt lärosäte / Login via your university']"))
                    .shouldBe(Condition.visible).click();
            LOGGER.debug("The button to login is pressed");
        } catch (ElementNotFound e) {
            LOGGER.error("The button to login is not found");
        }

        //Press on the search field
        try {
            $(byId("searchinput")).shouldBe(Condition.visible).click();
            LOGGER.debug("The search field is pressed");
        } catch (ElementNotFound e) {
            LOGGER.error("The search field is not found");
        }

        //Input LTU in the searchbar
        try {
            $(byId("searchinput")).sendKeys("LTU");
            LOGGER.debug("LTU text is entered");
        } catch (ElementNotFound e) {
            LOGGER.error("LTU text is not entered");
        }

        //Choose LTU from the list
        try {
            $(byCssSelector("div.primary")).shouldBe(Condition.visible).click();
            LOGGER.debug("Choosing LTU");
        } catch (ElementNotFound e) {
            LOGGER.error("LTU is not found");
        }
    }

    @Test
    @Order(9)
    public void credentials2() {
        //Get credentials from Json file and enter them in the login form
        //Json file with username and password to login
        File jsonFile = new File("C:\\temp\\ltu.json");

        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            username = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();

            LOGGER.info("Json file is read");
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading the Json file: " + e.getMessage());
        }

        //Enter the username
        try {
            $(byId("username")).sendKeys(username);
            LOGGER.debug("The username is entered");
        } catch (ElementNotFound e) {
            LOGGER.error("The username element is not found");
        } catch (Exception e) {
            LOGGER.error("An error occurred while entering the username: " + e.getMessage());
        }

        //Enter the password
        try {
            $(byId("password")).sendKeys(password);
            LOGGER.debug("The password is entered");
        } catch (ElementNotFound e) {
            LOGGER.error("The password is not entered");
        } catch (Exception e) {
            LOGGER.error("An error occurred while entering the username: " + e.getMessage());
        }

        //Click on submit button
        try {
            $(Selectors.byName("submit")).shouldBe(Condition.visible).click();
            LOGGER.debug("The login button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The login button is not found");
        }
    }

    @Test
    @Order(10)
    public void clickMenuButton() {
        //Click on the Menu button
        try {
            $(byCssSelector("svg[data-icon='bars']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Menu button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Menu button is not found");
        }
    }

    @Test
    @Order(11)
    public void clickTranscriptButton() {
        //Click on the Transcript/Intyg button
        try {
            $(byCssSelector("a[href*='intyg']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Transcript/Intyg button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Transcript/Intyg button is not found");
        }
    }


    @Test
    //@Disabled
    @Order(12)
    public void clickCreateButton() {
        //Click on the Create button with class="btn-ladok-brand"
        try {
            $(byCssSelector(".btn.btn-ladok-brand")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Create button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Create button is not found");
        }
    }

    @Test
    //@Disabled
    @Order(13)
    public void openTheList(){
        //Press to see the list of options for the transcript
        try {
            $(byId("intygstyp")).shouldBe(Condition.visible).click();
            LOGGER.debug("The list is shown");
        } catch (ElementNotFound e) {
            LOGGER.error("The list is not found");
        }
    }

    @Test
    //@Disabled
    @Order(14)
    public void chooseTranscript () {
        //Press on the second option with value="2: Object"
        try {
            $(byCssSelector("option[value='2: Object']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The second option is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The option is not found");
        }
    }

    @Test
    //@Disabled
    @Order(15)
    public void clickCreateButton2() {
        //Press on the button to create the transcript
        try {
            $(byCssSelector(".btn-ladok-brand.text-nowrap.me-lg-3")).shouldBe(Condition.visible).click();
            LOGGER.debug("The button to create the transcript is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The button to create the transcript is not found");
        }
    }

    @Test
    @Order(16)
    public void takeAScreenshot() throws IOException {
        //Wait and then take a screenshot and save it to the target/Files folder
        try {
        Selenide.sleep(2000);
        File screenshot = Screenshots.takeScreenShotAsFile();
        String path = System.getProperty("user.dir") + "\\target\\Files\\screenshot.png";
        Files.move(screenshot.toPath(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);
        LOGGER.debug("Screenshot taken and saved to: " + path);
        } catch (IOException e) {
            LOGGER.error("An error occurred while taking a screenshot: " + e.getMessage());
        }
    }

    @Test
    @Order(17)
    public void result() {
        LOGGER.info("The test is finished");
        LOGGER.info("-");
        Selenide.sleep(4000);
    }
}
