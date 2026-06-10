package org.springframework.cloud.gateway.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.support.MvcFoundOnClasspathException;
import org.springframework.context.annotation.Configuration;

/**
 * 环境检查配置类，是启动网关前的必须
 *
 * @Configuration(proxyBeanMethods = false)
 * @Configuration (proxyBeanMethods = false) = 轻量级配置类（Lite 模式），不生成 CGLIB 代理，启动更快、内存占用更小，但不能直接调用本类里的 @Bean 方法。
 * @Configuration // 等价于 proxyBeanMethods = true
 * 为什么 Spring Cloud Gateway / Spring Boot 大量用 false？
 * 原因：
 * 性能：Boot 自动配置类非常多，每个都生成代理，启动会慢很多
 * 无内部调用：自动配置类里的 @Bean 方法很少互相直接调用，都是靠参数注入依赖
 * 轻量化：符合 Boot “快速启动、低内存” 的设计目标
 *
 *
 * @AutoConfigureBefore(GatewayAutoConfiguration.class)
 * 当前这个配置类，必须在 GatewayAutoConfiguration（网关核心自动配置）之前加载。
 * 我要比网关核心配置更早初始化，用来提前注册自定义组件（过滤器 / 路由 / 断言），确保生效。
 *
 * ConditionalOnProperty  条件注解，控制这个类 / 配置是否被 Spring 加载。
 * 检查配置项：spring.cloud.gateway.enabled。
 * matchIfMissing = true：如果配置文件里没写这个配置，就当作 “条件满足”。
 */


@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(GatewayAutoConfiguration.class)
@ConditionalOnProperty(name = "spring.cloud.gateway.enabled", matchIfMissing = true)
public class GatewayClassPathWarningAutoConfiguration {

	private static final Log log = LogFactory.getLog(GatewayClassPathWarningAutoConfiguration.class);

	private static final String BORDER = "\n\n**********************************************************\n\n";


	/**
	 * 禁止 Spring MVC！（硬拦截）
	 * 检查项目里有没有 Spring MVC ,[spring-boot-starter-web] 就会存在 DispatcherServlet → 条件成立。
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	protected static class SpringMvcFoundOnClasspathConfiguration {

		public SpringMvcFoundOnClasspathConfiguration() {
			throw new MvcFoundOnClasspathException();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingClass("org.springframework.web.reactive.DispatcherHandler")
	protected static class WebfluxMissingFromClasspathConfiguration {

		public WebfluxMissingFromClasspathConfiguration() {
			log.warn(BORDER + "Spring Webflux is missing from the classpath, "
					+ "which is required for Spring Cloud Gateway at this time. "
					+ "Please add spring-boot-starter-webflux dependency." + BORDER);
		}

	}

}
