package org.springframework.cloud.gateway.config;

import reactor.core.publisher.Flux;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;

/**
 * @author Spencer Gibb
 */
public class PropertiesRouteDefinitionLocator implements RouteDefinitionLocator {

	private final GatewayProperties properties;

	public PropertiesRouteDefinitionLocator(GatewayProperties properties) {
		this.properties = properties;
	}

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return Flux.fromIterable(this.properties.getRoutes());
	}

}
