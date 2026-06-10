package org.springframework.cloud.gateway.route;

import java.util.Map;

import org.assertj.core.util.Maps;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Stefan Stus
 */
public class RouteDefinitionTest {

	@Test
	public void addRouteDefinitionKeepsExistingMetadata() {
		Map<String, Object> originalMetadata = Maps.newHashMap("key", "value");
		Map<String, Object> newMetadata = Maps.newHashMap("key2", "value2");

		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setMetadata(originalMetadata);
		routeDefinition.getMetadata().putAll(newMetadata);

		assertThat(routeDefinition.getMetadata()).hasSize(2).containsAllEntriesOf(originalMetadata)
				.containsAllEntriesOf(newMetadata);
	}

	@Test
	public void setRouteDefinitionReplacesExistingMetadata() {
		Map<String, Object> originalMetadata = Maps.newHashMap("key", "value");
		Map<String, Object> newMetadata = Maps.newHashMap("key2", "value2");

		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setMetadata(originalMetadata);
		routeDefinition.setMetadata(newMetadata);

		assertThat(routeDefinition.getMetadata()).isEqualTo(newMetadata);
	}

	@Test
	public void addSingleMetadataEntryKeepsOriginalMetadata() {
		Map<String, Object> originalMetadata = Maps.newHashMap("key", "value");

		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.setMetadata(originalMetadata);
		routeDefinition.getMetadata().put("key2", "value2");

		assertThat(routeDefinition.getMetadata()).hasSize(2).containsAllEntriesOf(originalMetadata)
				.containsEntry("key2", "value2");
	}

}
