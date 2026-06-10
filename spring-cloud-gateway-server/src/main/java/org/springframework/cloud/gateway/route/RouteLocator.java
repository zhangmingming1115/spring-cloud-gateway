package org.springframework.cloud.gateway.route;

import reactor.core.publisher.Flux;

/**
 * @author Spencer Gibb
 * RouteLocator 可以直接自定义路由( org.springframework.cloud.gateway.route.Route ) ，
 * 也可以通过 RouteDefinitionRouteLocator 获取 RouteDefinition ，并转换成 Route 。
 */
// TODO: rename to Routes?
public interface RouteLocator {

	Flux<Route> getRoutes();

}
