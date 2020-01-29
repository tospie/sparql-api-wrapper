/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.asr.smartmass.json.sparql.mapping;

import com.taxonic.carml.engine.RmlMapper;
import com.taxonic.carml.engine.RmlMapper.Builder;
import com.taxonic.carml.logical_source_resolver.CsvResolver;
import com.taxonic.carml.logical_source_resolver.JsonPathResolver;
import com.taxonic.carml.logical_source_resolver.XPathResolver;
import com.taxonic.carml.model.TriplesMap;
import com.taxonic.carml.util.RmlMappingLoader;
import com.taxonic.carml.vocab.Rdf;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;
import org.eclipse.rdf4j.model.Model;

/**
 *
 * @author tosp01
 */
public class CARMLMapper {

	public Model map(Mapping mapping, Data data) {
		InputStream dataStream = inputAsStream(data);

		RmlMapper mapper = buildMapper(data.getFormat());
		mapper.bindInputStream(dataStream);

		return mapper.map(buildMapping(mapping));
	}

	private <T extends Input> InputStream inputAsStream(T input) {
		return new ByteArrayInputStream(input.getText().getBytes());
	}

	private RmlMapper buildMapper(Format dataFormat) {

		Builder builder = RmlMapper.newBuilder();

		switch (dataFormat) {
			case CSV:
				builder.setLogicalSourceResolver(Rdf.Ql.Csv, new CsvResolver());
				break;
			case JSON:
				builder.setLogicalSourceResolver(Rdf.Ql.JsonPath, new JsonPathResolver());
				break;
			case XML:
				builder.setLogicalSourceResolver(Rdf.Ql.XPath, new XPathResolver());
				break;
		}

		return builder.build();
	}

	private Set<TriplesMap> buildMapping(Mapping mapping) {
		InputStream mappingStream = inputAsStream(mapping);
		return RmlMappingLoader.build().load(mapping.getFormat(), mappingStream);
	}
}
