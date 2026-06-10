package org.springframework.cloud.gateway.route;

import reactor.core.publisher.Flux;

/**
 * 路由定义定位器接口，定义获得路由定义数组的方法。
 *
 * @author Spencer Gibb
 * 常用实现的接口
 * PropertiesRouteDefinitionLocator  		==> 从配置文件( 例如，YML / Properties 等 ) 读取
 * RedisRouteDefinitionRepository			==> 从存储器( 例如，内存 / Redis / MySQL 等 )读取
 * DiscoveryClientRouteDefinitionLocator	==> 从注册中心( 例如，Eureka / Consul / Zookeeper / Etcd 等 )读取
 * CompositeRouteDefinitionLocator			==> 组合多种 RouteDefinitionLocator 的实现，为 RouteDefinitionRouteLocator 提供统一入口
 */
public interface RouteDefinitionLocator {

	/**
	 * Flux 是 Project Reactor 中的 “异步流” 工具类，代表 0...N 个元素的响应式序列。
	 * 简单理解：
	 * 异步 + 批量 + 非阻塞 + 流式处理 的升级版 List / 迭代器。
	 * Flux<T> = 0 ~ N 个元素（可以是无限）
	 * Mono<T> = 0 ~ 1 个元素
	 * Spring Cloud Gateway、WebFlux、Netty 全栈底层全是 Flux/Mono。
	 *
	 *
	 * 类型	    	数量	   		同步 / 异步		阻塞 / 非阻塞			用途
	 * 普通对象 T	1 个		同步				阻塞					普通返回
	 * List<T>		N 个		同步				阻塞					批量返回
	 * Mono<T>		0/1 个		异步				非阻塞				Web 接口返回单个对象
	 * Flux<T>		0...N 个	异步				非阻塞				网关、流式、高并发
	 */
	Flux<RouteDefinition> getRouteDefinitions();

}
