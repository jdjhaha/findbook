package com.jdjhaha.findbook.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdjhaha.findbook.vo.SearchResultVO;

public class ApiHelper {
	public static Optional<SearchResultVO> requestApi(Map<String, String> paramMap ) {
		SearchResultVO searchResultVO = null;
		
		// f6e02a42dae6dce6e1c134b616d83242
		String requestUrl = "https://dapi.kakao.com/v3/search/book?" + mapToParams(paramMap);

		HttpsURLConnection conn;
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;

		try {
			final URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "KakaoAK " + "f6e02a42dae6dce6e1c134b616d83242");

			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");

			final int responseCode = conn.getResponseCode();
			/*
			 * System.out.println(String.format("\nSending '%s' request to URL : %s", "GET",
			 * requestUrl)); System.out.println("Response Code : " + responseCode);
			 */
			if (responseCode == 200)
				isr = new InputStreamReader(conn.getInputStream());
			else
				isr = new InputStreamReader(conn.getErrorStream());

			reader = new BufferedReader(isr);
			final StringBuffer buffer = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			//System.out.println(buffer.toString());
			ObjectMapper objMapper = new ObjectMapper();
			searchResultVO = objMapper.readValue(buffer.toString(), SearchResultVO.class);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (Exception ignore) {
				}
			if (reader != null)
				try {
					reader.close();
				} catch (Exception ignore) {
				}
			if (isr != null)
				try {
					isr.close();
				} catch (Exception ignore) {
				}
		}
		return Optional.ofNullable(searchResultVO);
	}
	
	public static String mapToParams(Map<String, String> map) {
		StringBuilder paramBuilder = new StringBuilder();
		for (String key : map.keySet()) {
			paramBuilder.append(paramBuilder.length() > 0 ? "&" : "");
			paramBuilder.append(String.format("%s=%s", urlEncodeUTF8(key), urlEncodeUTF8(map.get(key).toString())));
		}
		return paramBuilder.toString();
	}

	public static String urlEncodeUTF8(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}
}
