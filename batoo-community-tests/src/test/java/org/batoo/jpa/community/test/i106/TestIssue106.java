/*
 * Copyright (c) 2012-2013, Batu Alp Ceylan
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */

package org.batoo.jpa.community.test.i106;

import junit.framework.Assert;

import org.batoo.jpa.community.test.BaseCoreTest;
import org.junit.Test;

/**
 * Test for https://github.com/BatooOrg/BatooJPA/issues/105
 * 
 * @author hceylan
 */
@SuppressWarnings("javadoc")
public class TestIssue106 extends BaseCoreTest {

	@Test
	public void testIssue103() {
		Orders order = new Orders();
		this.persist(order);

		this.commit();
		this.close();

		order = this.find(Orders.class, order.getOrderId());
		Assert.assertNotNull(order.getOrderCustomerLeaders());
	}
}
