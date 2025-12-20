package com.pk.development.util;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.text.DateFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;

public class ValidatorUtils {

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	private static boolean noNullElement(Object... targets) {
		boolean result = true;
		for (Object target : targets) {
			if (!notNull(target)) {
				result = false;
				break;
			}
		}
		return result;
	}

	private static boolean allNullElement(Object... targets) {
		boolean result = true;
		for (Object target : targets) {
			if (notNull(target)) {
				result = false;
				break;
			}
		}
		return result;
	}

	public static boolean noNullElements(Object... targets) {
		return notNull(targets) ? noNullElement(targets) : false;
	}

	public static boolean notNull(Object target) {
		return target != null ? true : false;
	}

	public static boolean isNull(Object target) {
		return target == null ? true : false;
	}

	public static final <T> boolean hasElements(Collection<T> collection) {
		return isNotEmpty(collection);
	}

	public static final <T> boolean hasNoElements(Collection<T> collection) {
		return isEmpty(collection);
	}

	public static boolean allNullElements(Object... targets) {
		return notNull(targets) ? allNullElement(targets) : true;
	}

	public static Boolean validateNullAndBlank(final String requestString) {
		return (requestString != null && !"".equals(requestString));
	}

	public static boolean isValidEmail(String email) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w\\-]*[\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(EMAIL_REGEX);
	}

	public static Date xmlGregorianDateToDate(XMLGregorianCalendar xmlGregorianCalendar) {
		Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
		// DateTime date1 = new DateTime(date);
		return date;
	}

	public static int isDateGreaterThenSysdate(DateTime comeingdate) {
		DateTime sysdate = new DateTime();
		return compareDate(comeingdate, sysdate);
	}
	
	public static int isDateGreaterThenSysdate(LocalDate comeingdate) {
		LocalDate sysdate = new LocalDate();
		return compareDate(comeingdate, sysdate);
	}

	public static int compareDate(DateTime fromDate,DateTime toDate) {
		
		if (isNull(fromDate) && isNull(toDate)) {
			return 0;
		}
		if (isNull(fromDate) && notNull(toDate)) {
			return -1;
		}
		if (notNull(fromDate) && isNull(toDate)) {
			return 1;
		}
		
		return fromDate.compareTo(toDate);
	}
	
	public static int compareDate(LocalDate fromDate,LocalDate toDate) {
		
		if (isNull(fromDate) && isNull(toDate)) {
			return 0;
		}
		if (isNull(fromDate) && notNull(toDate)) {
			return -1;
		}
		if (notNull(fromDate) && isNull(toDate)) {
			return 1;
		}
		
		return fromDate.compareTo(toDate);
	}

	public static Date formatDate(DateTime dateValue, String format) {
		Date formattedDate = null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		String formattedDateString = dateFormat.format(dateValue);
		try {
			formattedDate = dateFormat.parse(formattedDateString);
		} catch (ParseException e) {

		}
		return formattedDate;
	}

	public static boolean isNullOrEmpty(Object target) {
		if (target instanceof String) {
			return StringUtils.isEmpty((String) target) || "?".equals(target) ? true : false;
		}else if(target instanceof Collection){
		    return CollectionUtils.isEmpty((Collection) target) ? true : false;
		}else {
			return target == null ? true : false;
		}
	}
	
    public static boolean isStringEmpty(String target) {
       
        return target != null && target.equals("") ? true : false;
       
    }
    
    public static String replaceNull(String target) {
    	  return target == null ? EMPTY : target;
    }

}
