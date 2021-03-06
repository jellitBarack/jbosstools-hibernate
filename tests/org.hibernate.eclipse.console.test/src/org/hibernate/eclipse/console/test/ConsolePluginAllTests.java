package org.hibernate.eclipse.console.test;

import java.io.IOException;

import org.hibernate.eclipse.console.test.mappingproject.MappingTestsAnnotations;
import org.hibernate.eclipse.console.test.mappingproject.MappingTestsJpa;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ConsolePluginAllTests {

	public static Test suite() throws IOException {
		TestSuite suite = new TestSuite(
				ConsoleTestMessages.ConsolePluginAllTests_test_for );
		suite.addTestSuite(MappingTestsJpa.class);
		suite.addTestSuite(MappingTestsAnnotations.class);
		
		return suite;
	}

}
