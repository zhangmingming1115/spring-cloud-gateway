package org.springframework.cloud.gateway.filter;

import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;

/**
 * Contract for interception-style, chained processing of gateway requests that may be
 * used to implement cross-cutting, application-agnostic requirements such as security,
 * timeouts, and others.
 *
 * Only applies to matched gateway routes.
 *
 * Copied from framework WebFilter
 *
 * @author Rossen Stoyanchev
 * @since 5.0
 */
public interface GlobalFilter {

	/**
	 * Process the Web request and (optionally) delegate to the next {@code GatewayFilter}
	 * through the given {@link GatewayFilterChain}.
	 * @param exchange the current server exchange
	 * @param chain provides a way to delegate to the next filter
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);

}
