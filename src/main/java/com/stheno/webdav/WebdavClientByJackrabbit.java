package com.stheno.webdav;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;

public class WebdavClientByJackrabbit {
	public static void main(String[] args) throws Throwable {

		HostConfiguration hostConfig = new HostConfiguration();
		hostConfig.setHost("example.domain.com");

		HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setMaxConnectionsPerHost(hostConfig, 20);
		connectionManager.setParams(params);

		HttpClient client = new HttpClient(connectionManager);
		Credentials creds = new UsernamePasswordCredentials("<id>", "<password>");
		client.getState().setCredentials(AuthScope.ANY, creds);
		client.setHostConfiguration(hostConfig);

		PutMethod method = new PutMethod("https://example.domain.com/dav/public/WebdavClientByJackrabbit.txt");
		FileInputStream fis = new FileInputStream(new File("test.txt"));
		RequestEntity entity = new InputStreamRequestEntity(fis);
		method.setRequestEntity(entity);
		client.executeMethod(method);

		System.out.println(method.getStatusCode());

	}

}
