package org.springframework.cloud.gateway.route;

import reactor.core.publisher.Mono;

/**
 * @author Spencer Gibb
 * 路由配置写入接口。该接口定义了保存与删除两个方法
 */
public interface RouteDefinitionWriter {

	Mono<Void> save(Mono<RouteDefinition> route);

	Mono<Void> delete(Mono<String> routeId);

}
