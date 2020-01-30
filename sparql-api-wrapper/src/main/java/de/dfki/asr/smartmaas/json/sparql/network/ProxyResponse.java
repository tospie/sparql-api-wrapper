/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.network;

import lombok.Getter;

/**
 *
 * @author tosp01
 */
public class ProxyResponse {

	@Getter
	int statusCode;
	@Getter
	String content;

	public ProxyResponse(int statusCode, String content) {
		this.statusCode = statusCode;
		this.content = content;
	}
}
