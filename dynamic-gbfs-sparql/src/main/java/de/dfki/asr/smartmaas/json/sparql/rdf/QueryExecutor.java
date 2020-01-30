/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmaas.json.sparql.rdf;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

/**
 *
 * @author tosp01
 */
public class QueryExecutor {

	public void queryModel(Model model, String query) {
		Repository tempRepo = new SailRepository(new MemoryStore());
		RepositoryConnection connection = tempRepo.getConnection();
		connection.add(model);
		connection.prepareQuery(query);
		/* TODO : Pass type of performed query */
	}
}
