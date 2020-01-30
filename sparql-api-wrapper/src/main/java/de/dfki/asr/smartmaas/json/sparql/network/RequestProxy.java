/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
	public static ProxyResponse Request(URL target) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private void performRequest(URL target, String contentType) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) target.openConnection();
		connection.setRequestMethod("GET");
	}
}
