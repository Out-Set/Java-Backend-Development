package com.tcl.messageService.pankajSir.email.service.constants;

import java.util.HashMap;
import java.util.Map;

public interface CommunicationConstants {
	
	
	public static final String NEW = "N";
	public static final String ERROR = "E";
	public static final String PROCESSED = "P";
	public static final String TO = "TO_CUST";
	public static final String COMMUNICATION_STATUS_ACTIVE = "A";
	public static final String COMMUNICATION_TYPE_SMS = "SMS";
	public static final String COMMUNICATION_TYPE_EMAIL = "EMAIL";
	public static final String COMMUNICATION_TYPE_WHATSAPP = "WHATSAPP";
	public static final String RESPONSE_TYPE_OK = "OK";
	public static  Map<String, String>  TEMPID_WITH_TEMP_STRING = new HashMap<String, String>();
	public static final String FINNONE ="FINNONE";
	public static final String COLLECTFLOW = "COLLECTFLOW";
	public static final String EXPERIAN = "EXPERIAN";
	public static final String INTEGRATION= "INTEGRATION";
	
}