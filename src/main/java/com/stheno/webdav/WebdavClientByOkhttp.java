package com.stheno.webdav;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class WebdavClientByOkhttp {
	public static void main(String[] args) throws Throwable {

		byte[] bytes = Files.readAllBytes(Paths.get("test.txt"));
		RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), bytes);

		Request request = new Request.Builder()
			.url("https://example.domain.com/dav/public/WebdavClientByOkhttp.txt")
			.put(body)
			.build();

		OkHttpClient client = new OkHttpClient().newBuilder().authenticator(new Authenticator() {
			@Override
			public Request authenticate(Route route, Response response) throws IOException {
				String credential = Credentials.basic("<id>", "<password>");
				return response.request().newBuilder().header("Authorization", credential).build();
			}
		}).build();

		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());

	}

}
