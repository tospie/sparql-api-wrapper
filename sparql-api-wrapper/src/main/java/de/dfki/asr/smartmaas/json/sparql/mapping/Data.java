/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.mapping;

import lombok.Getter;

/**
 *
 * @author tosp01
 */
public class Data extends Input {

	@Getter
	private final Format format;

	public Data(String text, Format format) {
		this.text = text;
		this.format = format;
	}
}
