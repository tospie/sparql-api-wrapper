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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.eclipse.rdf4j.rio.turtle.TurtleParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	    HttpServletResponse response,
	    @RequestParam("query") String query,
	    @RequestParam("source") String sourceUri) {

	//String mapping = Builder.getContent(mappingUri);
	String source = Builder.getContent(sourceUri);

	//Model mappedModel = carmlMapper.map(new Mapping(mapping, RDFFormat.TURTLE), new Data(source, Format.JSON));
	Model model = parseSource(sourceUri, source);
	try {
	    byte[] queryResult = new QueryExecutor().queryModel(model, query);
	    return new String(queryResult, "UTF-8");
	} catch (Exception e) {

	    return "[QUERY EXECUTION ERROR][ApplicationController.Query.GetMapping] An error occurred while executing the query : <br>"
		    + e.getMessage() + ", <br>"
		    + ", Cause: " + e.getCause().getMessage();

	}
    }

    @GetMapping("/mapping/{id}")
    String getMapping(@PathVariable String id) {
	try {
	    switch (id) {
		case "gbfs":
		    return readFileContent("gbfs_free_bikes_mapping.ttl");
		case "station":
		    return readFileContent("gbfs_station_info.ttl");
		default:
		    return readFileContent("mapping.ttl");
	    }
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
	InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fileName);
	StringWriter writer = new StringWriter();
	IOUtils.copy(resourceStream, writer);
	String content = writer.toString();
	return content;
    }

    Model parseSource(String documentUrl, String input) {
	InputStream inputStream = new ByteArrayInputStream(input.getBytes());
	try {
	    return Rio.parse(inputStream, documentUrl.toString(), RDFFormat.TURTLE);
	} catch (IOException ex) {
	    Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
	} catch (RDFParseException ex) {
	    Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
	} catch (UnsupportedRDFormatException ex) {
	    Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }

}
