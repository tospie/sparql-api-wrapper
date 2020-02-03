/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.rdf;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.QueryResult;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;

/**
 *
 * @author tosp01
 */
public class QueryResultSerializer {

	public <T extends QueryResult> String serialize(T result) {
		if (result instanceof TupleQueryResult) {
			return serialize((TupleQueryResult) result);
		} else if (result instanceof GraphQueryResult) {
			return serialize((GraphQueryResult) result);
		}

		throw new UnsupportedOperationException("Not Yet Implemented");
	}

	private String serialize(TupleQueryResult result) {
		String output = "";
		while (result.hasNext()) {
			BindingSet next = result.next();
			output += "\n";
			for (String n : next.getBindingNames()) {
				output += "[" + n + ":" + next.getValue(n) + "]";
			}
		}
		return output;
	}

	private String serialize(GraphQueryResult result) {
		OutputStream output = new ByteArrayOutputStream();
		RDFWriter rdfWriter = Rio.createWriter(RDFFormat.TURTLE,
				output);

		rdfWriter.startRDF();
		for (Map.Entry<String, String> ns : result.getNamespaces().entrySet()) {
			rdfWriter.handleNamespace(ns.getKey(), ns.getValue());
		}
		while (result.hasNext()) {
			rdfWriter.handleStatement(result.next());
		}
		rdfWriter.endRDF();
		return output.toString();
	}

}
