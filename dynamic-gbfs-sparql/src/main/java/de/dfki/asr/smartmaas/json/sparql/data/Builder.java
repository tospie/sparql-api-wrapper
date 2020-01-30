/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.data;

import de.dfki.asr.smartmaas.json.sparql.network.RequestProxy;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author tosp01
 */
public abstract class Builder {

	public String getContent(String parameter) {
		try {
			URL targetUrl = new URL(parameter);
			return RequestProxy.Request(targetUrl).getContent();
		} catch (MalformedURLException e) {
			// for now, we assume that whenever a parameter is not a valid URI,
			// it is validly submitted data and pass it as such
			return parameter;
		}
	}
}
