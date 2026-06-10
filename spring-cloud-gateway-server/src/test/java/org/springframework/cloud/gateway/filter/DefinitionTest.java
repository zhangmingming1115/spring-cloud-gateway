package org.springframework.cloud.gateway.filter;

import org.junit.Test;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

/**
 * @author mmzhang
 * @email braveheart1115@163.com
 * @date 2026/6/9/009 16:30
 * @Description:
 */
public class DefinitionTest {

	@Test
	public void testFilterDefinition(){
		String text = "RewritePath=/admin-api/system/v3/api-docs, /v3/api-docs";
		FilterDefinition filterDefinition=new FilterDefinition(text);
		filterDefinition.getArgs();
	}


	@Test
	public void testPredicateDefinition(){
		String text = "Path=/admin-api/system/**";
		PredicateDefinition predicateDefinition=new PredicateDefinition(text);
		predicateDefinition.getArgs();
	}



}
