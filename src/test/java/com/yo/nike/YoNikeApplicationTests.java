package com.yo.nike;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YoNikeApplicationTests {

	private WebClient webClient;

	@Test
	public void contextLoads() {
	}

	@Before
	public void init() throws Exception {
		webClient = new WebClient();
	}

	@After
	public void close() throws Exception {
		webClient.close();
	}

	@Test
	public void homePage() throws Exception {

		try (final WebClient webClient = new WebClient()) {

			final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
			Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

			final String pageAsXml = page.asXml();
			Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

			final String pageAsText = page.asText();
			Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

		}
	}

	@Test
	public void retrieveWebPageBasics() throws Exception {

		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);

		String url = "http://www.baeldung.com/full_archive";
		HtmlPage page = webClient.getPage(url);
		String xpath = "(//ul[@class='car-monthlisting']/li)[1]/a";
		HtmlAnchor latestPostLink
				= (HtmlAnchor) page.getByXPath(xpath).get(0);
		HtmlPage postPage = latestPostLink.click();

		List<HtmlHeading1> h1 = postPage.getByXPath("//h1");

		for(HtmlHeading1 heading1s: h1){


			System.out.println("Headings Id: " + heading1s.getId());
			System.out.println("As Text: " + heading1s.asText());

		}

		Assert.assertTrue(h1.size() > 0);
	}


}
