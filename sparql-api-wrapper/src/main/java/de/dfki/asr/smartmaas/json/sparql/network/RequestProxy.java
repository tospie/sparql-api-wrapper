/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.network;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author tosp01
 *
 *
 */
public class RequestProxy {

	/**
	 *
	 * @return ProxyResponse object containing error code and response content
	 * as returned by the remote endpoint
	 */
	public static ProxyResponse Request(URI target) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest
				.newBuilder()
				.GET()
				.uri(target)
				.build();
		HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
		return new ProxyResponse(response.statusCode(), response.body());
	}
}
