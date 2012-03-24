/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.batoo.jpa.core.impl.mapping;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.batoo.jpa.core.MappingException;
import org.batoo.jpa.core.impl.instance.ManagedInstance;
import org.batoo.jpa.core.impl.jdbc.JoinTable;
import org.batoo.jpa.core.impl.types.AttributeImpl;
import org.batoo.jpa.core.impl.types.EntityTypeImpl;
import org.batoo.jpa.core.impl.types.PluralAttributeImpl;
import org.batoo.jpa.core.jdbc.adapter.JDBCAdapter;

/**
 * Implementation of {@link AssociationType#MANY} relational attributes.
 * <p>
 * This is a {@link OneToMany} type of mapping with the declaring attribute being the owner.
 * 
 * @author hceylan
 * @since $version
 */
public class OwnerOneToManyMapping<X, C, E> extends OwnedAssociationMapping<X, C> implements CollectionMapping<X, C>,
	PersistableAssociation<X, C> {

	private JoinTable joinTable;

	/**
	 * @param declaringAttribute
	 *            the attribute which declares the mapping
	 * @param path
	 *            the path to the declaringAttribute
	 * @param column
	 *            the column definition of the mapping
	 * @param orpanRemoval
	 *            if orphans should be removed
	 * @param eager
	 *            if association is annotated with {@link FetchType#EAGER}
	 * @throws MappingException
	 * 
	 * @since $version
	 * @author hceylan
	 */
	public OwnerOneToManyMapping(PluralAttributeImpl<X, C, E> declaringAttribute, Deque<AttributeImpl<?, ?>> path, boolean orphanRemoval,
		boolean eager) throws MappingException {
		super(AssociationType.MANY, declaringAttribute, path, orphanRemoval, eager);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PluralAttributeImpl<X, C, E> getDeclaringAttribute() {
		return (PluralAttributeImpl<X, C, E>) super.getDeclaringAttribute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public JoinTable getJoinTable() {
		return this.joinTable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public OwnerManyToOneMapping<C, X> getOpposite() {
		return (OwnerManyToOneMapping<C, X>) super.getOpposite();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EntityTypeImpl<C> getType() {
		return (EntityTypeImpl<C>) this.getDeclaringAttribute().getElementType();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean hasJoin() {
		return this.joinTable != null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void link(JDBCAdapter jdbcAdapter) throws MappingException {
		super.link(jdbcAdapter);

		if (this.getOpposite() == null) {
			this.joinTable = new JoinTable(this, jdbcAdapter);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void performInsert(Connection connection, ManagedInstance<X> managedInstance) throws SQLException {
		this.joinTable.performInsert(connection, managedInstance);
	}
}