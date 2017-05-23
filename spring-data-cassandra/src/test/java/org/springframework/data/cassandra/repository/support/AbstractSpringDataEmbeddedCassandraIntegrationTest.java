/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.cassandra.repository.support;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.mapping.CassandraPersistentEntity;
import org.springframework.data.cql.AbstractEmbeddedCassandraIntegrationTest;

/**
 * Base class to support integration tests and provide a {@link CassandraOperations} instance.
 *
 * @author Mark Paluch
 */
public abstract class AbstractSpringDataEmbeddedCassandraIntegrationTest
		extends AbstractEmbeddedCassandraIntegrationTest {

	@Autowired private CassandraOperations template;

	/**
	 * Truncate table for all known {@link org.springframework.data.mapping.PersistentEntity entities}.
	 */
	public void deleteAllEntities() {

		Stream<CassandraPersistentEntity<?>> stream = template.getConverter().getMappingContext().getTableEntities()
				.stream();

		stream.map(CassandraPersistentEntity::getType).filter(type -> !type.isInterface()).forEach(template::truncate);
	}
}
