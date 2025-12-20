package com.savan.quartz.scheduler.constants;

public enum SchedulerJobStateEnum {
	INACTIVE(0, "Inactive", "Inactive"), ACTIVE(1, "Active", "Active");

	private int enumValue;
	private String description;
	private String i18nCode;

	private SchedulerJobStateEnum(int enumValue, String i18nCode, String description) {
		this.i18nCode = i18nCode;
		this.enumValue = enumValue;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public int getEnumValue() {
		return this.enumValue;
	}

	public String getI18nCode() {
		return this.i18nCode;
	}

	public static SchedulerJobStateEnum getEnumFromValue(int enumValue) {
		SchedulerJobStateEnum typeEnum = null;
		SchedulerJobStateEnum[] var2 = values();
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			SchedulerJobStateEnum supportedEnum = var2[var4];
			if (enumValue == supportedEnum.enumValue) {
				typeEnum = supportedEnum;
				break;
			}
		}

		return typeEnum;
	}
}
