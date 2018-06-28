/*将日期字符串转换为“yyyy-MM-dd”格式（以便于录入数据库）*/
public class TransDateString {
	
	public static String transDateString(String string) {
		// TODO Auto-generated method stub
		char[] strArray = string.toCharArray();
		StringBuffer tempStr = new StringBuffer("");
		StringBuffer transStr = new StringBuffer("");
	    transStr.append("2018");
		transStr.append('-');
		for(int i = 4; i <= 6; i++) {
			tempStr.append(strArray[i]);
		}
		switch(tempStr.toString()) {
		case "Jan":
			transStr.append("01");
			break;
		case "Feb":
			transStr.append("02");
			break;
		case "Mar":
			transStr.append("03");
			break;
		case "Apr":
			transStr.append("04");
			break;
		case "May":
			transStr.append("05");
			break;
		case "Jun":
			transStr.append("06");
			break;
		case "Jul":
			transStr.append("07");
			break;
		case "Aug":
			transStr.append("08");
			break;
		case "Sep":
			transStr.append("09");
			break;
		case "Oct":
			transStr.append("10");
			break;
		case "Nov":
			transStr.append("11");
			break;
		case "Dec":
			transStr.append("12");
			break;
		default:
			break;
		}
		transStr.append("-");
		for(int i = 8; i < 10; i++) {
			transStr.append(strArray[i]);
		}
		return transStr.toString().trim();
	}

}
