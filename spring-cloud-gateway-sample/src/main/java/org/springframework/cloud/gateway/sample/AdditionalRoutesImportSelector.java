package org.springframework.cloud.gateway.sample;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * DeferredImportSelector
 * → 延迟导入器
 * → 等到所有普通配置类加载完之后再执行
 * → 常用于网关、路由这种后加载的组件
 */

class AdditionalRoutesImportSelector implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		if (ClassUtils.isPresent("org.springframework.cloud.gateway.sample.AdditionalRoutes", null)) {
			return new String[] { "org.springframework.cloud.gateway.sample.AdditionalRoutes" };
		}
		return new String[0];
	}

}
