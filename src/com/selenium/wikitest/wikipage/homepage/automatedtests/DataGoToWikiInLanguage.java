package com.selenium.wikitest.wikipage.homepage.automatedtests;

import com.selenium.wikitest.data.SQLiteJDBC;
import com.selenium.wikitest.wikipage.homepage.HomePage;
import com.selenium.wikitest.wikipage.homepage.HomePageText;
import com.thoughtworks.selenium.SeleneseTestBase;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DataGoToWikiInLanguage {

	private static HomePage homePage = new HomePage();
	
	private String xPath;
	private String title;
	
	public DataGoToWikiInLanguage (String xPath, String title) {
		this.xPath = xPath;
		this.title = title;
	}
	
	@BeforeClass
	public static void testSetup() {
		homePage.openPage();
	}
	
	@Parameters
	public static ArrayList<String[]> getSearchData() {
		ArrayList<String[]> listStrings = null;
		try {
			listStrings = SQLiteJDBC.queryData(
					HomePageText.getString("LanguageLinks.TableName"),
					HomePageText.getString("LanguageLinks.Column1"),
					HomePageText.getString("LanguageLinks.Column2"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listStrings;
	}

	@Test
	public void testSearchData() {
		String actualResult = homePage.goToListLinkByXPath(xPath);
		try {
			SeleneseTestBase.assertTrue(actualResult.contains(title));
		} catch (Exception e) {
			System.out.println("xPath = " + xPath);
			System.out.println("title = " + title);
			e.printStackTrace();
		}
		homePage.openHomePage();
	}

	@After
	public void returnToWikipedia() {
		homePage.openHomePage();
	}

	@AfterClass
	public static void commonTearDown() throws Exception {
		homePage.closeBrowser();
	}

}
