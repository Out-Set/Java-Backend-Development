package com.savan.quartz.scheduler.constants;

public enum SchedulerStatusEnum {
	RUNNING('R', "Running", "Running"), PAUSED('P', "Paused", "Paused"), RESUMED('M', "Resumed", "Resumed"), INACTIVE(
			'I', "Inactive",
			"Inactive"), VETOED('V', "Vetoed", "Vetoed"), SCHEDULED('S', "Scheduled", "Scheduled"), UNSCHEDULED('U',
					"Unscheduled", "Unscheduled"), COMPLETED('C', "Completed", "Completed"), COMPLETED_WITH_EXCEPTION(
							'X', "Completed With Exception", "Completed With Exception"), FAILED('E', "Failed",
									"Failed"), QUEUED('Q', "Queued", "Queued"), BLOCKED('B', "Blocked",
											"Blocked"), NULL((Character) null, "NULL", "Null Enum"), NOT_SUPPORTED(' ',
													"Not Supported", "This Enum code is Not Supported");

	private Character enumValue;
	private String description;
	private String i18nCode;

	private SchedulerStatusEnum(Character enumValue, String i18nCode, String description) {
		this.i18nCode = i18nCode;
		this.enumValue = enumValue;
		this.description = description;
	}

	public boolean equalsValue(Character theEnumValue) {
		return theEnumValue != null ? this.enumValue.equals(theEnumValue) : false;
	}

	public String getDescription() {
		return this.description;
	}

	public Character getEnumValue() {
		return this.enumValue;
	}

	public String getI18nCode() {
		return this.i18nCode;
	}

	public static SchedulerStatusEnum getEnumFromValue(Character enumValue) {
		SchedulerStatusEnum typeEnum = null;
		if (enumValue == null) {
			return NULL;
		} else {
			SchedulerStatusEnum[] var2 = values();
			int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				SchedulerStatusEnum supportedEnum = var2[var4];
				if (enumValue.equals(supportedEnum.enumValue)) {
					typeEnum = supportedEnum;
				}
			}

			if (typeEnum == null) {
				typeEnum = NOT_SUPPORTED;
			}

			return typeEnum;
		}
	}
}