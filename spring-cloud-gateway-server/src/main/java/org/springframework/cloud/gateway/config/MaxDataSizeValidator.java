package org.springframework.cloud.gateway.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

import org.springframework.util.unit.DataSize;

// https://in.relation.to/2017/03/02/adding-custom-constraint-definitions-via-the-java-service-loader/
public class MaxDataSizeValidator implements ConstraintValidator<Max, DataSize> {

	private long maxValue;

	@Override
	public boolean isValid(DataSize value, ConstraintValidatorContext context) {
		// null values are valid
		if (value == null) {
			return true;
		}
		return value.toBytes() <= maxValue;
	}

	@Override
	public void initialize(Max maxValue) {
		this.maxValue = maxValue.value();
	}

}
