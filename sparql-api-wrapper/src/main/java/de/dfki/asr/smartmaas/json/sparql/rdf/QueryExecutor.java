/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.rdf;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.query.QueryResult;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

/**
 *
 * @author tosp01
 */
public class QueryExecutor {

	public QueryResult queryModel(Model model, String query) {
		/* TODO : Pass type of performed query and add support for Queries beyond SELECT*/

		Repository tempRepo = new SailRepository(new MemoryStore());
		RepositoryConnection connection = tempRepo.getConnection();
		connection.add(model);
		TupleQuery sparqlQuery = connection.prepareTupleQuery(query);
		TupleQueryResult result = sparqlQuery.evaluate();
		return result;
	}
}
