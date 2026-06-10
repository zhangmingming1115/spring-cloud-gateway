package org.springframework.cloud.gateway.config;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.style.ToStringCreator;
import org.springframework.validation.annotation.Validated;

/**
 * metrics
 * 1. 通用含义
 * 指标；度量标准；衡量体系
 * 2. 技术 / 编程 / 运维 / 后端（高频场景）
 * 监控指标、性能指标、指标数据
 * （工程、监控、告警、大数据、微服务、Prometheus/Grafana 主流译法）
 */
@ConfigurationProperties("spring.cloud.gateway.metrics")
@Validated
public class GatewayMetricsProperties {

	/**
	 * Default metrics prefix.
	 */
	public static final String DEFAULT_PREFIX = "spring.cloud.gateway";

	/**
	 * Enables the collection of metrics data.
	 */
	private boolean enabled;

	/**
	 * The prefix of all metrics emitted by gateway.
	 */
	private String prefix = DEFAULT_PREFIX;

	/**
	 * Tags map that added to metrics.
	 */
	@NotNull
	private Map<String, String> tags = new HashMap<>();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("enabled", enabled).append("prefix", prefix).append("tags", tags)
				.toString();

	}

}
