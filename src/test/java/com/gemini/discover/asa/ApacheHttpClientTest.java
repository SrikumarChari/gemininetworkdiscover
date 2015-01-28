package com.gemini.discover.asa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Ignore;
import org.junit.Test;

public class ApacheHttpClientTest {

	@Test
	public void testHttpPostApple() {
		ApacheHttpClient httpClient = new ApacheHttpClient();
		String url = "https://selfsolve.apple.com/wcResults.do";
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
		urlParameters.add(new BasicNameValuePair("cn", ""));
		urlParameters.add(new BasicNameValuePair("locale", ""));
		urlParameters.add(new BasicNameValuePair("caller", ""));
		urlParameters.add(new BasicNameValuePair("num", "12345"));
		try {
			HttpEntity entity = new UrlEncodedFormEntity(urlParameters);
			String result = httpClient.post(url, entity);
			System.out.println(result.trim());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testHttpPostApple");
		}
	}


	@Test
	public void testHttpPostASA() {
		ApacheHttpClient httpClient = new ApacheHttpClient();
		String url = "https://asav/admin/config";
		String config = "show version";
		String data = ASA.makeConfigXml(config);
		StringEntity entity = new StringEntity(data, ContentType.create("text/xml"));
		try {
			String result = httpClient.post(url, "asadp", "dat@package", entity);
			System.out.println(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("testHttpPostASA");
		}
	}
}
