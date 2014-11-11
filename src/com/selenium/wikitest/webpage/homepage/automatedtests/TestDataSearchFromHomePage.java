package com.selenium.wikitest.webpage.homepage.automatedtests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.selenium.wikitest.webpage.homepage.HomePage;
import com.selenium.wikitest.webpage.homepage.HomePageText;
import com.selenium.wikitest.webpage.homepage.SQLiteHomePage;
import com.thoughtworks.selenium.SeleneseTestBase;

import java.util.ArrayList;

@RunWith(Parameterized.class)
public class TestDataSearchFromHomePage {

	private static HomePage homePage = new HomePage();
	
	private String searchItem;
	
	public TestDataSearchFromHomePage(String searchItem1, String searchItem2) {
		searchItem = searchItem1;
		// searchItem2 is a duplicate of searchItem1
	}
	
	@BeforeClass
	public static void testSetup() {
		homePage.openPage();
	}
	
	@Parameters
	public static ArrayList<String[]> getSearchData() {
		ArrayList<String[]> listStrings = null;
		try {
			listStrings = SQLiteHomePage.queryData(
					HomePageText.getString("SearchStrings.TableName"),
					HomePageText.getString("SearchStrings.Column1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listStrings;
	}

	@Test
	public void testSearchData() {

		// Assert expected search result record, from list, matches actual result
		SeleneseTestBase.assertTrue(homePage.searchFor(searchItem).contains(searchItem));

		// Return to home page for next test
		homePage.openHomePage();

	}

	@After
	public void returnToWikipedia() {
		homePage.goToHomePage();
	}

	@AfterClass
	public static void commonTearDown() throws Exception {
		homePage.closeBrowser();
	}

}