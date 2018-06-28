import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/*计算两个日期的天数差*/
public class DateDiff {

	public static int Datediff(String startDate, String endDate) {
		
		int startYear = Integer.valueOf(startDate.substring(0, 4));
		int startMonth = Integer.valueOf(startDate.substring(5,7));
    	int startDay = Integer.valueOf(startDate.substring(8,10));

    	LocalDate standard_startDate = LocalDate.of(1, Month.JANUARY, 1);
    	
    	switch (startMonth) {
    	case 1:
    		standard_startDate = LocalDate.of(startYear, Month.JANUARY, startDay);
    		break;
    	case 2:
    		standard_startDate = LocalDate.of(startYear, Month.FEBRUARY, startDay);
    		break;
    	case 3:
    		standard_startDate = LocalDate.of(startYear, Month.MARCH, startDay);
    		break;
    	case 4:
    		standard_startDate = LocalDate.of(startYear, Month.APRIL, startDay);
    		break;
    	case 5:
    		standard_startDate = LocalDate.of(startYear, Month.MAY, startDay);
    		break;
    	case 6:
    		standard_startDate = LocalDate.of(startYear, Month.JUNE, startDay);
    		break;
    	case 7:
    		standard_startDate = LocalDate.of(startYear, Month.JULY, startDay);
    		break;
    	case 8:
    		standard_startDate = LocalDate.of(startYear, Month.AUGUST, startDay);
    		break;
    	case 9:
    		standard_startDate = LocalDate.of(startYear, Month.SEPTEMBER, startDay);
    		break;
    	case 10:
    		standard_startDate = LocalDate.of(startYear, Month.OCTOBER, startDay);
    		break;
    	case 11:
    		standard_startDate = LocalDate.of(startYear, Month.NOVEMBER, startDay);
    		break;
    	case 12:
    		standard_startDate = LocalDate.of(startYear, Month.DECEMBER, startDay);
    		break;
    	}
    	
    	int endYear = Integer.valueOf(endDate.substring(0, 4));
    	int endMonth = Integer.valueOf(endDate.substring(5,7));
    	int endDay = Integer.valueOf(endDate.substring(8,10));
    	
    	LocalDate standard_endDate = LocalDate.of(1, Month.JANUARY, 1);
    	
    	switch (endMonth) {
    	case 1:
    		standard_endDate = LocalDate.of(endYear, Month.JANUARY, endDay);
    		break;
    	case 2:
    		standard_endDate = LocalDate.of(endYear, Month.FEBRUARY, endDay);
    		break;
    	case 3:
    		standard_endDate = LocalDate.of(endYear, Month.MARCH, endDay);
    		break;
    	case 4:
    		standard_endDate = LocalDate.of(endYear, Month.APRIL, endDay);
    		break;
    	case 5:
    		standard_endDate = LocalDate.of(endYear, Month.MAY, endDay);
    		break;
    	case 6:
    		standard_endDate = LocalDate.of(endYear, Month.JUNE, endDay);
    		break;
    	case 7:
    		standard_endDate = LocalDate.of(endYear, Month.JULY, endDay);
    		break;
    	case 8:
    		standard_endDate = LocalDate.of(endYear, Month.AUGUST, endDay);
    		break;
    	case 9:
    		standard_endDate = LocalDate.of(endYear, Month.SEPTEMBER, endDay);
    		break;
    	case 10:
    		standard_endDate = LocalDate.of(endYear, Month.OCTOBER, endDay);
    		break;
    	case 11:
    		standard_endDate = LocalDate.of(endYear, Month.NOVEMBER, endDay);
    		break;
    	case 12:
    		standard_endDate = LocalDate.of(endYear, Month.DECEMBER, endDay);
    		break;
    	}
    	
        int daysDiff = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(standard_startDate, standard_endDate)));
        
        return daysDiff;
       
	}

}