/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmass.json.sparql.mapping;

import lombok.Getter;
import org.eclipse.rdf4j.rio.RDFFormat;

/**
 *
 * @author tosp01
 */
public class Mapping extends Input {

	@Getter
	private RDFFormat format;

	public Mapping(String text, RDFFormat format) {
		this.text = text;
		this.format = format;
	}
}
