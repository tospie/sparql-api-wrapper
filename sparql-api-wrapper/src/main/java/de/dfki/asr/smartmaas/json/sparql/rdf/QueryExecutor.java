/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.rdf;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.parser.ParsedBooleanQuery;
import org.eclipse.rdf4j.query.parser.ParsedGraphQuery;
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

	QueryResultSerializer serializer = new QueryResultSerializer();

	public String queryModel(Model model, String query) {
		RepositoryConnection connection = connectToTempRepo(model);
		ParsedOperation operation = QueryParserUtil.parseOperation(QueryLanguage.SPARQL, query, null);
		if (operation instanceof ParsedTupleQuery) {
			return serializer.serialize(performTupleQuery(connection, query));
		} else if (operation instanceof ParsedGraphQuery) {
			return serializer.serialize(performGraphQuery(connection, query));
		} else if (operation instanceof ParsedBooleanQuery) {
			boolean result = performBooleanQuery(connection, query);
			return result ? "true" : "false";
		}

		// tempRepo.shutDown();
		throw new UnsupportedOperationException("[QUERY EXECUTOR ERROR] (package de.dfki.asr.smartmaas.json.sparql.rdf.QueryExecutor)"
				+ " Unsupported SPARQL Query type.");

	}

	private RepositoryConnection connectToTempRepo(Model model) {
		Repository tempRepo = new SailRepository(new MemoryStore());
		tempRepo.initialize();
		RepositoryConnection connection = tempRepo.getConnection();
		connection.add(model);
		return connection;
	}

	private TupleQueryResult performTupleQuery(RepositoryConnection connection, String query) {
		TupleQuery sparqlQuery = connection.prepareTupleQuery(query);
		return sparqlQuery.evaluate();
	}

	private GraphQueryResult performGraphQuery(RepositoryConnection connection, String query) {
		GraphQuery sparqlQuery = connection.prepareGraphQuery(query);
		return sparqlQuery.evaluate();
	}

	private boolean performBooleanQuery(RepositoryConnection connection, String query) {
		BooleanQuery sparqlQuery = connection.prepareBooleanQuery(query);
		return sparqlQuery.evaluate();
	}
}
