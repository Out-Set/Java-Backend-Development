package com.savan.quartz.scheduler.constants;

public enum ChainedExecutionFaultTreatmentEnum {
	TERMINATE_CHAIN_ON_FAULT(0, "Terminate Chain on Fault", "Terminate Chain on Fault"), DIGEST_AND_MOVE_ON(1,
			"Digest And Move To Next Chained Job", "Digest And Move To Next Chained Job");

	private Integer enumValue;
	private String description;
	private String i18nCode;

	private ChainedExecutionFaultTreatmentEnum(Integer enumValue, String description, String i18nCode) {
		this.enumValue = enumValue;
		this.description = description;
		this.i18nCode = i18nCode;
	}

	public boolean equalsValue(Integer theEnumValue) {
		return theEnumValue != null ? this.enumValue.equals(theEnumValue) : false;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getEnumValue() {
		return this.enumValue;
	}

	public String getI18nCode() {
		return this.i18nCode;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	protected void setEnumValue(Integer enumValue) {
		this.enumValue = enumValue;
	}

	protected void setI18nCode(String i18nCode) {
		this.i18nCode = i18nCode;
	}
}
