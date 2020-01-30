/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.data;

import de.dfki.asr.smartmaas.json.sparql.network.RequestProxy;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author tosp01
 */
public abstract class Builder {

	public static String getContent(String parameter) {
		try {
			URI targetUrl = new URI(parameter);
			return RequestProxy.Request(targetUrl).getContent();
		} catch (MalformedURLException e) {
			// for now, we assume that whenever a parameter is not a valid URI,
			// it is validly submitted data and pass it as such
			return "[[BUILDER ERROR (de.dfki.asr.smartmaas.json.sparql.data.Builder)] Failed to Request Content due to malformed target URL '"
					+ parameter + "': "
					+ e.getMessage();
		} catch (IOException e) {
			return "[BUILDER ERROR (de.dfki.asr.smartmaas.json.sparql.data.Builder)] Failed to Request Content due to following reason : "
					+ e.getMessage();
		} catch (URISyntaxException ex) {
			return "[BUILDER ERROR (de.dfki.asr.smartmaas.json.sparql.data.Builder)] Malformed URI: " + ex.getMessage();
		} catch (InterruptedException ex) {
			return "[BUILDER ERROR (de.dfki.asr.smartmaas.json.sparql.data.Builder)] Interrupt while performin HTTP Request: " + ex.getMessage();
		}
	}
}
