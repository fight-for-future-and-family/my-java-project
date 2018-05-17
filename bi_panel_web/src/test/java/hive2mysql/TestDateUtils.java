package hive2mysql;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;


public class TestDateUtils {

	public static void main(String[] args) {
		Iterator<Calendar> it=DateUtils.iterator(Calendar.getInstance(),DateUtils.RANGE_WEEK_MONDAY);
		while (it.hasNext()) {
			Calendar type = (Calendar) it.next();
			System.out.println(DateFormatUtils.format(type, "yyyy-MM-dd"));
		}
		Calendar  cal=Calendar.getInstance() ;
		System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
		
		cal.set(Calendar.WEEK_OF_YEAR, 3);
		System.out.println(DateFormatUtils.format(cal, "yyyy-MM-dd"));
		
		Iterator<Calendar> it2=DateUtils.iterator(cal,DateUtils.RANGE_WEEK_MONDAY);
		while (it2.hasNext()) {
			Calendar type = (Calendar) it2.next();
			System.out.println(DateFormatUtils.format(type, "yyyy-MM-dd"));
		}

	}

}
