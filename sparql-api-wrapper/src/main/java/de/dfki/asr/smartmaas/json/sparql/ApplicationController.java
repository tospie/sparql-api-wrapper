/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql;

import de.dfki.asr.smartmaas.json.sparql.data.Builder;
import de.dfki.asr.smartmaas.json.sparql.mapping.CARMLMapper;
import de.dfki.asr.smartmaas.json.sparql.mapping.Data;
import de.dfki.asr.smartmaas.json.sparql.mapping.Format;
import de.dfki.asr.smartmaas.json.sparql.mapping.Mapping;
import de.dfki.asr.smartmaas.json.sparql.rdf.QueryExecutor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tosp01
 */
@RestController
public class ApplicationController {

	CARMLMapper carmlMapper = new CARMLMapper();

	@GetMapping("/query")
	String Query(HttpServletRequest request,
			@RequestParam("query") String query,
			@RequestParam("mapping") String mappingUri,
			@RequestParam("source") String sourceUri) {

		String mapping = Builder.getContent(mappingUri);
		String source = Builder.getContent(sourceUri);

		Model mappedModel = carmlMapper.map(new Mapping(mapping, RDFFormat.TURTLE), new Data(source, Format.JSON));
		TupleQueryResult queryResult = new QueryExecutor().queryModel(mappedModel, query);

		return "<table border=2><tr>"
				+ "<td> Source<br><br> : " + source.replace("\n", "<br>").replace(" ", "&nbsp;") + "</td>"
				+ "<td> Mapping<br><br> : " + mapping.replace("\n", "<br>").replace(" ", "&nbsp;") + "</td>"
				+ "<td>Mapping Result : <textarea rows='10' cols='90'>" + modelToTtl(mappedModel) + "</textarea></td>"
				+ "</tr><tr>"
				+ "<td>Query: " + request.getParameter("query")
				+ "</td><td>Result : " + printResult(queryResult)
				+ "</td><td></td></tr></table>";
	}

	private OutputStream modelToTtl(final Model result) throws UnsupportedRDFormatException, RDFHandlerException {
		OutputStream output = new ByteArrayOutputStream();
		RDFWriter rdfWriter = Rio.createWriter(RDFFormat.TURTLE,
				output);
		rdfWriter.startRDF();
		for (Statement st : result) {
			rdfWriter.handleStatement(st);
		}
		rdfWriter.endRDF();
		return output;
	}

	private String printResult(TupleQueryResult result) {
		String output = "";
		while (result.hasNext()) {
			BindingSet next = result.next();
			output += "<br>";
			for (String n : next.getBindingNames()) {
				output += "[" + n + ":" + next.getValue(n) + "]";
			}
		}
		return output;
	}

	@GetMapping("/mapping")
	String getMapping() {
		try {
			return readFileContent("mapping.ttl");
		} catch (IOException ex) {
			return "[SERVER PATH ERROR(de.dfki.asr.smartmaas.json.sparql.ApplicationController)]"
					+ " Failed to Request Content due to following reason : " + ex.getMessage();
		}
	}

	@GetMapping("/source")
	String getSource() {
		try {
			return readFileContent("venue.json");
		} catch (IOException ex) {
			return "[SERVER PATH ERROR (de.dfki.asr.smartmaas.json.sparql.ApplicationController)]"
					+ " Failed to Request Content due to following reason : " + ex.getMessage();
		}
	}

	String readFileContent(String fileName) throws IOException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		String content = new String(Files.readAllBytes(file.toPath()));
		return content;
	}
}
