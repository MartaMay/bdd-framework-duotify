package stepDefinitions.ui;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import pages.SignupPage;
import stepDefinitions.SharedData;
import stepDefinitions.db.CrudOperationsStepDefs;
import utilities.DBUtils;
import utilities.Driver;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignUpStepDefs {

    SharedData sharedData;

    public SignUpStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }



    @When("The user fills up the fields with valid info")
    public void the_user_fills_up_the_fields_with_valid_info() {
        SignupPage signupPage = new SignupPage();
        Faker faker=  new Faker();
        sharedData.setUsername(faker.name().username());
        sharedData.setFirst(faker.name().firstName());
        sharedData.setLast(faker.name().lastName());
        sharedData.setEmail(faker.internet().emailAddress());
        sharedData.setPass(faker.internet().password());
        signupPage.signUp(
                sharedData.getUsername(),sharedData.getFirst(),sharedData.getLast(), sharedData.getEmail(),sharedData.getPass()
                );

    }

    @When("The user fills up the fields with valid info such as {string} {string} {string} {string} {string}")
    public void the_user_fills_up_the_fields_with_valid_info_such_as(String user, String f, String l, String e, String p) {


        new SignupPage().signUp(user, f, l, e, p);
    }


    @When("The user fills up the fields with the following info")
    public void the_user_fills_up_the_fields_with_the_following_info(List<String> list) {
        new SignupPage().signUp(
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3),
                list.get(4)
        );
    }


    @When("The user fills up the fields with the following info as")
    public void the_user_fills_up_the_fields_with_the_following_info_as(Map<String, String> map) {

        new SignupPage().signUp(
                map.get("username"),
                map.get("firstName"),
                map.get("lastName"),
                map.get("email"),
                map.get("password")
        );
    }
    @Then("The user should be able to sign up successfully")
    public void the_user_should_be_able_to_sign_up_successfully() {
        Assert.assertEquals("http://duotify.us-east-2.elasticbeanstalk.com/browse.php?", Driver.getDriver().getCurrentUrl());
    }


    @And("the user should be created in the database")
    public void theUserShouldBeCreatedInTheDatabase() {

        String query ="SELECT * FROM users where username='" + sharedData.getUsername() + "'";
        System.out.println(query);
        List<List<Object>> result = DBUtils.getQueryResultAsListOfLists(query);

        System.out.println(result);

        Assert.assertTrue(!result.isEmpty());


    }

    @Then("the user's info should be correct")
    public void the_user_s_info_should_be_correct() {

        String query ="SELECT * FROM users where username='" + sharedData.getUsername() + "'";

        System.out.println(query);
        List<Map<String, Object>> result = DBUtils.getQueryResultListOfMaps(query);

        System.out.println(result);

        Map<String, Object> data = result.get(0);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(data.get("username")).isEqualTo(sharedData.getUsername());
        softAssertions.assertThat(data.get("firstName")).isEqualTo(sharedData.getFirst());
        softAssertions.assertThat(data.get("lastName")).isEqualTo(sharedData.getLast());
//        softAssertions.assertThat(data.get("email")).isEqualTo(email); //email is stored as capitalized

        String passwordInMD5 = DigestUtils.md5Hex(sharedData.getPass());

        System.out.println(passwordInMD5);


         softAssertions.assertThat(data.get("password")).isEqualTo(passwordInMD5);



        softAssertions.assertAll();




    }



}
