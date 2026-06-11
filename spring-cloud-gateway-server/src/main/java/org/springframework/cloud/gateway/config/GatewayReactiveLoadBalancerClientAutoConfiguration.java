package org.springframework.cloud.gateway.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledGlobalFilter;
import org.springframework.cloud.gateway.filter.LoadBalancerServiceInstanceCookieFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;

/**
 * AutoConfiguration for {@link ReactiveLoadBalancerClientFilter}.
 *
 * @author Spencer Gibb
 * @author Olga Maciaszek-Sharma
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ ReactiveLoadBalancer.class, LoadBalancerAutoConfiguration.class, DispatcherHandler.class })
@AutoConfigureAfter(LoadBalancerAutoConfiguration.class)
@EnableConfigurationProperties(GatewayLoadBalancerProperties.class)
public class GatewayReactiveLoadBalancerClientAutoConfiguration {

	@Bean
	@ConditionalOnBean(LoadBalancerClientFactory.class)
	@ConditionalOnMissingBean(ReactiveLoadBalancerClientFilter.class)
	@ConditionalOnEnabledGlobalFilter
	public ReactiveLoadBalancerClientFilter gatewayLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory,
			GatewayLoadBalancerProperties properties) {
		return new ReactiveLoadBalancerClientFilter(clientFactory, properties);
	}

	@Bean
	@ConditionalOnBean({ ReactiveLoadBalancerClientFilter.class, LoadBalancerClientFactory.class })
	@ConditionalOnMissingBean
	@ConditionalOnEnabledGlobalFilter
	public LoadBalancerServiceInstanceCookieFilter loadBalancerServiceInstanceCookieFilter(
			LoadBalancerClientFactory loadBalancerClientFactory) {
		return new LoadBalancerServiceInstanceCookieFilter(loadBalancerClientFactory);
	}

}
