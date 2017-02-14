package org.jboss.tools.hibernate.runtime.v_5_2.internal;

import org.hibernate.cfg.reveng.TableFilter;
import org.jboss.tools.hibernate.runtime.common.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.ITableFilter;
import org.junit.Assert;
import org.junit.Test;

public class TableFilterFacadeTest {

	private static IFacadeFactory FACADE_FACTORY = new FacadeFactoryImpl();
	
	@Test
	public void testSetExclude() {
		TableFilter tableFilter = new TableFilter();
		ITableFilter tableFilterFacade = FACADE_FACTORY.createTableFilter(tableFilter);
		Assert.assertNull(tableFilter.getExclude());
		tableFilterFacade.setExclude(true);
		Assert.assertTrue(tableFilter.getExclude());
	}
	
	@Test
	public void testSetMatchCatalog() {
		TableFilter tableFilter = new TableFilter();
		ITableFilter tableFilterFacade = FACADE_FACTORY.createTableFilter(tableFilter);
		Assert.assertNotEquals("foo", tableFilter.getMatchCatalog());
		tableFilterFacade.setMatchCatalog("foo");
		Assert.assertEquals("foo", tableFilter.getMatchCatalog());
	}
		
	@Test
	public void testSetMatchSchema() {
		TableFilter tableFilter = new TableFilter();
		ITableFilter tableFilterFacade = FACADE_FACTORY.createTableFilter(tableFilter);
		Assert.assertNotEquals("foo", tableFilter.getMatchSchema());
		tableFilterFacade.setMatchSchema("foo");
		Assert.assertEquals("foo", tableFilter.getMatchSchema());
	}
		
}
