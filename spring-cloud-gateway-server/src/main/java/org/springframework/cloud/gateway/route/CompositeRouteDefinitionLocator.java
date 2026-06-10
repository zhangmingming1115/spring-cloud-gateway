package org.springframework.cloud.gateway.route;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;

/**
 * @author Spencer Gibb
 */
public class CompositeRouteDefinitionLocator implements RouteDefinitionLocator {

	private static final Log log = LogFactory.getLog(CompositeRouteDefinitionLocator.class);

	/**
	 * delegates 委托
	 */
	private final Flux<RouteDefinitionLocator> delegates;

	private final IdGenerator idGenerator;

	public CompositeRouteDefinitionLocator(Flux<RouteDefinitionLocator> delegates) {
		this(delegates, new AlternativeJdkIdGenerator());
	}

	public CompositeRouteDefinitionLocator(Flux<RouteDefinitionLocator> delegates, IdGenerator idGenerator) {
		this.delegates = delegates;
		this.idGenerator = idGenerator;
	}

	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return this.delegates.flatMapSequential(RouteDefinitionLocator::getRouteDefinitions)
				.flatMap(routeDefinition -> {
					if (routeDefinition.getId() == null) {
						return randomId().map(id -> {
							routeDefinition.setId(id);
							if (log.isDebugEnabled()) {
								log.debug("Id set on route definition: " + routeDefinition);
							}
							return routeDefinition;
						});
					}
					return Mono.just(routeDefinition);
				});
	}

	protected Mono<String> randomId() {
		return Mono.fromSupplier(idGenerator::generateId).map(UUID::toString).publishOn(Schedulers.boundedElastic());
	}

}
