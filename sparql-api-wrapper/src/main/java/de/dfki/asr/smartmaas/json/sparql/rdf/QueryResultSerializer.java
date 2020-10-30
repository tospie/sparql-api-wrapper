/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.rdf;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.QueryResult;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.resultio.TupleQueryResultWriter;
import org.eclipse.rdf4j.query.resultio.sparqljson.SPARQLResultsJSONWriter;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;

/**
 *
 * @author tosp01
 */
public class QueryResultSerializer {

	public <T extends QueryResult> byte[] serialize(T result) {
		if (result instanceof TupleQueryResult) {
			return serialize((TupleQueryResult) result);
		} else if (result instanceof GraphQueryResult) {
			return serialize((GraphQueryResult) result);
		}

		throw new UnsupportedOperationException("Not Yet Implemented");
	}

	private byte[] serialize(TupleQueryResult result) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			TupleQueryResultWriter writer = new SPARQLResultsJSONWriter(output);
			QueryResults.report((TupleQueryResult) result, writer);

			return output.toByteArray();
	}

	private byte[] serialize(GraphQueryResult result) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		RDFWriter rdfWriter = Rio.createWriter(RDFFormat.TURTLE,
				output);

		rdfWriter.startRDF();
		result.getNamespaces().entrySet().forEach((ns) -> {
			rdfWriter.handleNamespace(ns.getKey(), ns.getValue());
		});
		while (result.hasNext()) {
			rdfWriter.handleStatement(result.next());
		}
		rdfWriter.endRDF();
		return output.toByteArray();
	}

}
