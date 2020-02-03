/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.rdf;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResult;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.parser.ParsedOperation;
import org.eclipse.rdf4j.query.parser.ParsedTupleQuery;
import org.eclipse.rdf4j.query.parser.QueryParserUtil;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

/**
 *
 * @author tosp01
 */
public class QueryExecutor {

	public <T extends QueryResult> T queryModel(Model model, String query) {
		ParsedOperation operation = QueryParserUtil.parseOperation(QueryLanguage.SPARQL, query, null);
		if (operation instanceof ParsedTupleQuery) {
			return (T) performTupleQuery(model, query);
		}

		throw new UnsupportedOperationException("[QUERY EXECUTOR ERROR (de.dfki.asr.smartmaas.json.sparql.rdf.QueryExecutor)]"
				+ " Query Type not yet supported");
	}

	private TupleQueryResult performTupleQuery(Model model, String query) {
		/* TODO : Pass type of performed query and add support for Queries beyond SELECT*/

		Repository tempRepo = new SailRepository(new MemoryStore());
		tempRepo.initialize();
		RepositoryConnection connection = tempRepo.getConnection();
		connection.add(model);
		TupleQuery sparqlQuery = connection.prepareTupleQuery(query);
		TupleQueryResult result = sparqlQuery.evaluate();
		// tempRepo.shutDown();
		return result;
	}
}
