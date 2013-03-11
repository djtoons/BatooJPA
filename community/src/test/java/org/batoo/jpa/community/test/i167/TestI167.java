package org.batoo.jpa.community.test.i167;

import java.util.List;

import junit.framework.Assert;

import org.batoo.jpa.community.test.BaseCoreTest;
import org.batoo.jpa.community.test.NoDatasource;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestI167 extends BaseCoreTest {
	@Test
	@NoDatasource
	public void test() {
		final EntityWithVersionAndAutogeneratedValueId entity = new EntityWithVersionAndAutogeneratedValueId(1l);
		final EntityWithVersionAndAutogeneratedValueId entity2 = new EntityWithVersionAndAutogeneratedValueId(2l);
		persist(entity);
		persist(entity2);
		this.commit();
		this.close();

		this.begin();

		final List<?> resultList = this.cq("select a from EntityWithVersionAndAutogeneratedValueId a").getResultList();

		Assert.assertEquals(2, resultList.size());

		for (final Object e : resultList) {
			((EntityWithVersionAndAutogeneratedValueId) e).setValue(10l);
			this.persist(e);
		}
		this.commit();
		this.close();

		this.begin();

		final List<?> resultList2 = this.cq("select a from EntityWithVersionAndAutogeneratedValueId a").getResultList();

		Assert.assertEquals(2, resultList2.size());
		for (final Object e : resultList2) {
			Assert.assertEquals(10l, ((EntityWithVersionAndAutogeneratedValueId) e).getValue().longValue());
		}
	}
}
