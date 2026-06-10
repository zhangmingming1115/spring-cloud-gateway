package org.springframework.cloud.gateway.route;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.validation.annotation.Validated;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @author Spencer Gibb
 *
 * 一下是 的 yaml 的配置
 *   cloud:
 *     # Spring Cloud Gateway 配置项，对应 GatewayProperties 类
 *     gateway:
 *       # 路由配置项，对应 RouteDefinition 数组
 *       routes:
 *         ## system-server 服务
 *         - id: system-admin-api # 路由的编号
 *           uri: grayLb://system-server
 *           predicates: # 断言，作为路由的匹配条件，对应 RouteDefinition 数组
 *             - Path=/admin-api/system/**
 *           filters:
 *               - RewritePath=/admin-api/system/v3/api-docs, /v3/api-docs # 配置，保证转发到 /v3/api-docs
 *         - id: system-app-api # 路由的编号
 *           uri: grayLb://system-server
 *           predicates: # 断言，作为路由的匹配条件，对应 RouteDefinition 数组
 *             - Path=/app-api/system/**
 *           filters:
 *               - RewritePath=/app-api/system/v3/api-docs, /v3/api-docs
 *
 */
@Validated
public class RouteDefinition {

	private String id;

	/**
	 * 谓语定义数组
	 * 请求通过 predicates 判断是否匹配。在 Route 里，PredicateDefinition 转换成 Predicate 。
	 */
	@NotEmpty
	@Valid
	private List<PredicateDefinition> predicates = new ArrayList<>();

	/**
	 * 过滤器定义数组
	 * 在 Route 里，FilterDefinition 转换成 GatewayFilter 。
	 */
	@Valid
	private List<FilterDefinition> filters = new ArrayList<>();

	/**
	 * 路由向的 URI
	 */
	@NotNull
	private URI uri;

	private Map<String, Object> metadata = new HashMap<>();

	private int order = 0;

	public RouteDefinition() {

	}

	public RouteDefinition(String text) {
		int eqIdx = text.indexOf('=');
		if (eqIdx <= 0) {
			throw new ValidationException(
					"Unable to parse RouteDefinition text '" + text + "'" + ", must be of the form name=value");
		}

		setId(text.substring(0, eqIdx));

		String[] args = tokenizeToStringArray(text.substring(eqIdx + 1), ",");

		setUri(URI.create(args[0]));

		for (int i = 1; i < args.length; i++) {
			this.predicates.add(new PredicateDefinition(args[i]));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<PredicateDefinition> getPredicates() {
		return predicates;
	}

	public void setPredicates(List<PredicateDefinition> predicates) {
		this.predicates = predicates;
	}

	public List<FilterDefinition> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterDefinition> filters) {
		this.filters = filters;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RouteDefinition that = (RouteDefinition) o;
		return this.order == that.order && Objects.equals(this.id, that.id)
				&& Objects.equals(this.predicates, that.predicates) && Objects.equals(this.filters, that.filters)
				&& Objects.equals(this.uri, that.uri) && Objects.equals(this.metadata, that.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.predicates, this.filters, this.uri, this.metadata, this.order);
	}

	@Override
	public String toString() {
		return "RouteDefinition{" + "id='" + id + '\'' + ", predicates=" + predicates + ", filters=" + filters
				+ ", uri=" + uri + ", order=" + order + ", metadata=" + metadata + '}';
	}

}
