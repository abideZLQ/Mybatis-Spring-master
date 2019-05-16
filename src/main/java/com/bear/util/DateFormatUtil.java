package com.bear.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class DateFormatUtil {
	

	 /**
	  * 获取现在时间
	  * 
	  * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	  */
	 public static String getStringDate() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  return dateString;
	 }

	
	 /**
	  * 获取现在时间
	  * 
	  * @return 返回短时间字符串格式yyyy-MM-dd
	  */
	 public static String getStringDateShort() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String dateString = formatter.format(currentTime);
	  System.out.println(dateString);
	  return dateString;
	 }
	 /**
	  * 获取今年
	  * 
	  * @return 返回短时间字符串格式yyyy-MM-dd
	  */
	 public static String getDateYear() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String dateString = formatter.format(currentTime);
	  
	  return dateString.substring(0, 4);
	 }
	 
	 /**
	  * 时间格式替换
	  * 
	  * @return 返回短时间字符串格式yyyy_MM_dd
	  */
	 public static String DateReplace() {
	  String  time = getStringDateShort();
	  int i=  getTimeShort().lastIndexOf(":")+1;
	  time =  time.replace("-", "_");
	  return time;
	 }
	 /**
	  * 获取传入时间是星期几
	  * 
	  * @return 星期日
	  */
	public static String getWeekOfDate(Date date) {      
	    String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};        
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    
	}
	/**
	  * 获取传入时间是星期几
	  * 
	  * @return 1、2
	  */
	public static int getWeekOfDate1(Date date) {      
	    int[] weekOfDays = {7, 1, 2, 3, 4, 5, 6};        
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    
	}
	 /**
	  * 获取传入时间是上午几点 或者下午几点
	  * 
	  * @return 2018年11月03日 (星期六)  下午16:09:46
	  */
	public static String getTime(Date date) {  
		 SimpleDateFormat formatterl = new SimpleDateFormat("yyyy年MM月dd日");
		 String dateStringl = formatterl.format(date);
		 String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};        
		    Calendar calendar = Calendar.getInstance();      
		    if(date != null){        
		         calendar.setTime(date);      
		    }        
		    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
		    if (w < 0){        
		        w = 0;      
		    }      
		    
		    
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateString = formatter.format(date);
		  Integer hour = Integer.parseInt(dateString.substring(11,13));
		  String time="";
		if(hour<=12){
			time= dateStringl+" ("+weekOfDays[w]+")" + "  上午"+dateString.substring(11);
		}else{
			time=dateStringl+" ("+weekOfDays[w]+")" + "  下午"+dateString.substring(11);
		}
	    return time;    
	}
	
	/**
	 * 获取当前时间是上午还是下午
	 * @param date
	 * @return 下午
	 *
	 */
	public static String getDateTime(Date date) {  //获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateString = formatter.format(date);
		  Integer hour = Integer.parseInt(dateString.substring(11,13));
		  String time="";
		if(hour<=12){
			time="上午";
		}else{
			time="下午";
		}
		return time;
	}
	 public final static char[] upper = "零一二三四五六七八九十".toCharArray();

	    /**
	     * 根据小写数字格式的日期转换成大写格式的日期
	     * @param date
	     * @return
	     */
	    public static String getUpperDate(String date) {
	        //支持yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd等格式
	        if(date == null) return null;
	        //非数字的都去掉
	        date = date.replaceAll("\\D", "");
	        if(date.length() != 8) return null;
	        StringBuilder sb = new StringBuilder();
	        for (int i=0;i<4;i++) {//年
	            sb.append(upper[Integer.parseInt(date.substring(i, i+1))]);
	        }
	        sb.append("年");//拼接年
	        int month = Integer.parseInt(date.substring(4, 6));
	        if(month <= 10) {
	            sb.append(upper[month]);
	        } else {
	            sb.append("十").append(upper[month%10]);
	        }
	        sb.append("月");//拼接月

	        int day = Integer.parseInt(date.substring(6));
	        if (day <= 10) {
	            sb.append(upper[day]);
	        } else if(day < 20) {
	            sb.append("十").append(upper[day % 10]);
	        } else {
	            sb.append(upper[day / 10]).append("十");
	            int tmp = day % 10;
	            if (tmp != 0) sb.append(upper[tmp]);
	        }
	        sb.append("日");//拼接日
	        return sb.toString();
	    }

		 /**
		  * 将短时间格式时间转换为字符串 yyyy年MM月dd日
		  * 
		  * @param dateDate
		  * @param k
		  * @return
		  */
		 public static String dateToStringl(Date dateDate) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		  String dateString = formatter.format(dateDate);
		  return dateString;
		 }
	 /**
	  * 获取时间 小时:分;秒 HH:mm:ss
	  * 
	  * @return
	  */
	 public static String getTimeShort() {
	  SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	  Date currentTime = new Date();
	  String dateString = formatter.format(currentTime);
	  System.out.println(dateString);
	  return dateString;
	 }
	 
	 /**
	  * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	  * 
	  * @param strDate
	  * @return
	  */
	 public static Date strToDateLong(String strDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = formatter.parse(strDate, pos);
	  System.out.println(strtodate);
	  return strtodate;
	 }
	 /**
	  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	  * 
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStrLong(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }
	 /**
	  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm
	  * 
	  * @param dateDate
	  * @return
	  */
	 public static String dateToStrLongl(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }
	 /**
	  * 将短时间格式时间转换为字符串 yyyy-MM-dd
	  * 
	  * @param dateDate
	  * @param k
	  * @return
	  */
	 public static String dateToStr(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }

	 
	 /**
	  * 将短时间格式时间转换为字符串 yyyy-MM-dd
	  * 
	  * @param dateDate
	  * @param k
	  * @return
	  */
	 public static String dateTo(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }

	 /**
	  * 将短时间格式时间转换为字符串 yyyy年-MM月-dd日
	  * 
	  * @param dateDate
	  * @param k
	  * @return
	  */
	 public static String dateToString(Date dateDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月-dd日");
	  String dateString = formatter.format(dateDate);
	  return dateString;
	 }

	 /**
	  * 将短时间格式字符串转换为时间 yyyy-MM-dd 
	  * 
	  * @param strDate
	  * @return
	  */
	 public static Date strToDate(String strDate) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  ParsePosition pos = new ParsePosition(0);
	  Date strtodate = formatter.parse(strDate, pos);
	  return strtodate;
	 }
	 
	 
	 /**
	  * 获取当前年
	  * 
	  * @param strDate
	  * @return
	  */
	 public static String  getYear() {
		 Date dateDate = new Date();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  String dateString = formatter.format(dateDate);
		  String[] years = dateString.split("-");
	  return years[0];
	 }
	 

	 /**
	  * 得到现在时间
	  * 
	  * @return
	  */
	 public static Date getNow() {
	  Date currentTime = new Date();
	  return currentTime;
	 }

	

	 /**
	  * 得到现在小时
	  */
	 public static String getHour() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  String hour;
	  hour = dateString.substring(11, 13);
	  return hour;
	 }
	 
	 /**
	  * 得到现在小时
	  */
	 public static String getHour1(Date currentTime ) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  String hour;
	  hour = dateString.substring(11, 13);
	  return hour;
	 }

	/**
	 * 得到现在小时
	 */
	public static String getHour2(Date currentTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(0, dateString.indexOf(":"));
		if("0" == hour.substring(0,1) || "0".equals( hour.substring(0,1) )){
			hour = hour.substring(1);
		}
		return hour;
	}
	 /**
	  * 得到现在分钟
	  * 
	  * @return
	  */
	 public static String getTime() {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  String min;
	  min = dateString.substring(14, 16);
	  return min;
	 }
	 
	 /**
	  * 得到现在分钟
	  * 
	  * @return
	  */
	 public static String getTime1(Date currentTime ) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String dateString = formatter.format(currentTime);
	  String min;
	  min = dateString.substring(14, 16);
	  return min;
	 }

	 /**
	  * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	  * 
	  * @param sformat
	  *            yyyyMMddhhmmss
	  * @return
	  */
	 public static String getUserDate(String sformat) {
	  Date currentTime = new Date();
	  SimpleDateFormat formatter = new SimpleDateFormat(sformat);
	  String dateString = formatter.format(currentTime);
	  System.out.println(dateString);
	  return dateString;
	 }
	 
	/* public static void main(String[] args) {
		 getUserDate("yyyyMMddhhmmss");
	}*/
	 /**
	  * 获取随机数
	  * @return UUID
	  * 下午3:55:34
	  *
	  */
	 public static UUID getRandomUUID(){
		 UUID randomUUID = UUID.randomUUID();
		 return randomUUID;
	 }
	 
	 /**
	  * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	  * 
	  * @param sformat
	  *            yyyyMMddhhmmssSSS
	  * @return
	  */
	 public static String getDate(String sformat) {
		 SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		 String formatStr =formatter.format(new Date());
		 System.out.println(formatStr);
		 return formatStr;
	 }
	 
	 
	/* public static void main(String[] args) {
		 //getDate("yyyyMMddhhmmssSSS");
		 tenToSixtheen(1);
	}*/
	 /**
	  * 将10进制转换为5位的16进制字符串，位数不够前面补0,多余5位不做处理
	  * @param ten
	  * @return
	  * 下午2:26:06
	  *
	  */
	public static String tenToSixtheen(Integer ten){
		
		String a = Integer.toHexString(ten);//转十六进制
		String c1 = "0000";
		String c2 = "000";
		String c3 = "00";
		String c4 = "0";
		System.out.println(a.length());
		a = a.length()==1?(c1+=a):(a.length()==2?(c2+=a):(a.length()==3?(c3+=a):(a.length()==4?(c4+=a):a)));
		System.out.println(a);
		return a;
	}
	/**
	  * 将10进制转换为4位的16进制字符串，位数不够前面补0,多余5位不做处理
	  * @param ten
	  * @return
	  * 下午2:26:06
	  *
	  */
	public static String tenToSixtheen1(Integer ten){
		
		String a = Integer.toHexString(ten);//转十六进制
		String c2 = "000";
		String c3 = "00";
		String c4 = "0";
		System.out.println(a.length());
		a = a.length()==1?(c2+=a):(a.length()==2?(c3+=a):(a.length()==3?(c4+=a):a));
		System.out.println(a);
		return a;
	}
	/**
	 * 当前时间加一天
	 * @param date
	 * @return
	 * 下午1:59:20
	 *
	 */
	   public static Date getNextDay(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
	        date = calendar.getTime();
	        return date;
	    }
	   /**
		 * 当前时间加几天
		 * @param date
		 * @return
		 * 下午1:59:20
		 *
		 */
		   public static Date getYesterDay(Date date,Integer num) {
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_MONTH, num);//num今天的时间加num天
		        date = calendar.getTime();
		        return date;
		    }
		   /**
		    * 获取当前时间加几分钟
		    * @param date
		    * @param num
		    * @return
		    * 下午1:39:45
		    *
		    */
		   public static Date addMDate(Date date,Integer num){
			   Calendar calendar = Calendar.getInstance ();
		       calendar.add(Calendar.MINUTE, num);
		       Date time = calendar.getTime ();
		       return time;
		   }
		   /**
		    * 获取当前时间加几秒
		    * @param date
		    * @param num
		    * @return
		    * 下午1:41:06
		    *
		    */
		   public static Date addSDate(Date date,Integer num){
			   Calendar calendar = Calendar.getInstance ();
		       System.out.println (calendar.getTime ());
		       calendar.add (Calendar.SECOND, num);
		       Date time = calendar.getTime ();
		       return time;
		   }
		   /**
		    * 字符串非空判断
		    * @param str
		    * @return Boolean 空：true；非空：false
		    *
		    */
	 public static Boolean isNull(String str){
		 Boolean flag = true;
		 if(str != null)
			 if(!str.trim().equals("") )
				 if(!str.equals("") && !str.equals("null") && !str.equals("undefined"))
					 flag = false;
				 else
					 flag = true;
			 else
					 flag = true;
		 else
			 flag = true;
		 
		 return flag;
	 }
	 
	 /**
	  * 两个日期间的天数计算
	  * @param date1 大
	  * @param date2 小
	  * @return
	 * @throws ParseException 
	  */
	 public static int compare(String startDate,String endDate) throws ParseException{
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		 Date date1 = sdf.parse(startDate);
		 Date date2 = sdf.parse(endDate);
		 long days = (date2.getTime() - date1.getTime()) / (24*3600*1000); 
		 return (int)days;
	 }

	 
   /** 
	* 获得指定日期的后一天 
	* @param specifiedDay yyyy-MM-dd HH:mm:ss
	* @return 
	*/ 
	public static String getSpecifiedDayAfter(String specifiedDay,int num){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+num); 
	
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	} 
	/** 
	* 获得指定日期的后一月 
	* @param specifiedDay yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd 
	* @return 
	*/ 
	public static String getSpecifiedMonthAfter(String specifiedDay,int num){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int month=c.get(Calendar.MONTH); 
		c.set(Calendar.MONTH,month+num); 
	
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	}
/**
 * 根据时间返回时上午还是下午：0是上午，1是下午	
 * @param date
 * @return
 * 下午8:38:22
 *
 */
	public static int isAm(Date date){
		Calendar cal=Calendar.getInstance();  
	    cal.setTime(date); 
	    int flag = cal.get(GregorianCalendar.AM_PM);
	    return flag;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

  
    /** 
     * 测试数据的数据类型 
     */  
    public static class Test_Data{  
        int number;  
        String chnNum;  
        public Test_Data(int number,String chnNum){  
            this.chnNum=chnNum;  
            this.number=number;  
        }  
    }  
  
 /*-----------------------------------阿拉伯数字转换大写数字-----------------------------------------------------------*/   
    
    // 将数字转化为大写  
    public static String numToUpper(int num) {  
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
        String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };  
        char[] str = String.valueOf(num).toCharArray();  
        String rstr = "";  
        for (int i = 0; i < str.length; i++) {  
            rstr = rstr + u[Integer.parseInt(str[i] + "")];  
        }  
        return rstr;  
    } 
	
    
    
    
    
    static String CHN_NUMBER[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};  
    static String CHN_UNIT[] = {"", "十", "百", "千"};          //权位  
    static String CHN_UNIT_SECTION[] = {"", "万", "亿", "万亿"}; //节权位  
    /** 
     * 阿拉伯数字转换为中文数字的核心算法实现。 
     * @param num为需要转换为中文数字的阿拉伯数字，是无符号的整形数 
     * @return 
     */  
    public static String NumberToChn(int num) {  
        StringBuffer returnStr = new StringBuffer();  
        Boolean needZero = false;  
        int pos=0;           //节权位的位置  
        if(num==0){  
            //如果num为0，进行特殊处理。  
            returnStr.insert(0,CHN_NUMBER[0]);  
        }  
        while (num > 0) {  
            int section = num % 10000;  
            if (needZero) {  
                returnStr.insert(0, CHN_NUMBER[0]);  
            }  
            String sectionToChn = SectionNumToChn(section);  
            //判断是否需要节权位  
            sectionToChn += (section != 0) ? CHN_UNIT_SECTION[pos] : CHN_UNIT_SECTION[0];  
            returnStr.insert(0, sectionToChn);  
            needZero = ((section < 1000 && section > 0) ? true : false); //判断section中的千位上是不是为零，若为零应该添加一个零。  
            pos++;  
            num = num / 10000;  
        }  
        return returnStr.toString();  
    }  
  
    /** 
     * 将四位的section转换为中文数字 
     * @param section 
     * @return 
     */  
    public static String SectionNumToChn(int section) {  
        StringBuffer returnStr = new StringBuffer();  
        int unitPos = 0;       //节权位的位置编号，0-3依次为个十百千;  
  
        Boolean zero = true;  
        while (section > 0) {  
  
            int v = (section % 10);  
            if (v == 0) {  
                if ((section == 0) || !zero) {  
                    zero = true; /*需要补0，zero的作用是确保对连续的多个0，只补一个中文零*/  
                    //chnStr.insert(0, chnNumChar[v]);  
                    returnStr.insert(0, CHN_NUMBER[v]);  
                }  
            } else {  
                zero = false; //至少有一个数字不是0  
                StringBuffer tempStr = new StringBuffer(CHN_NUMBER[v]);//数字v所对应的中文数字  
                tempStr.append(CHN_UNIT[unitPos]);  //数字v所对应的中文权位  
                returnStr.insert(0, tempStr);  
            }  
            unitPos++; //移位  
            section = section / 10;  
        }  
        return returnStr.toString();  
    }  
    /** 
     * 中文转换成阿拉伯数字，中文字符串除了包括0-9的中文汉字，还包括十，百，千，万等权位。 
     * 此处是完成对这些权位的类型定义。 
     * name是指这些权位的汉字字符串。 
     * value是指权位多对应的数值的大小。诸如：十对应的值的大小为10，百对应为100等 
     * secUnit若为true，代表该权位为节权位，即万，亿，万亿等 
     */  
    public static class Chn_Name_value{  
        String name;  
        int value;  
        Boolean secUnit;  
        public Chn_Name_value(String name,int value,Boolean secUnit){  
            this.name=name;  
            this.value=value;  
            this.secUnit=secUnit;  
        }  
    }  
  
    static Chn_Name_value chnNameValue[]={  
            new Chn_Name_value("十",10,false),  
            new Chn_Name_value("百",100,false),  
            new Chn_Name_value("千",1000,false),  
            new Chn_Name_value("万",10000,true),  
            new Chn_Name_value("亿",100000000,true)  
    };  
  
    /** 
     * 返回中文数字汉字所对应的阿拉伯数字，若str不为中文数字，则返回-1 
     * @param str 
     * @return 
     */  
    public static int ChnNumToValue(String str){  
        for(int i=0;i<CHN_NUMBER.length;i++){  
            if(str.equals(CHN_NUMBER[i])){  
                return i;  
            }  
        }  
        return -1;  
    }  
  
    /** 
     * 返回中文汉字权位在chnNameValue数组中所对应的索引号，若不为中文汉字权位，则返回-1 
     * @param str 
     * @return 
     */  
    public static int ChnUnitToValue(String str){  
        for(int i=0;i<chnNameValue.length;i++){  
            if(str.equals(chnNameValue[i].name)){  
                return i;  
            }  
        }  
        return -1;  
    }  
  
    /** 
     * 返回中文数字字符串所对应的int类型的阿拉伯数字 
     * @param str 
     * @return 
     */  
    public static int ChnStringToNumber(String str){  
        int returnNumber=0;  
        int section=0;  
        int pos=0;  
        int number=0;  
        while (pos<str.length()){  
            int num=ChnNumToValue(str.substring(pos,pos+1));  
            //若num>=0，代表该位置（pos），所对应的是数字不是权位。若小于0，则表示为权位  
            if(num>=0){  
                number=num;  
                pos++;  
                //pos是最好一位，直接将number加入到section中。  
                if(pos>=str.length()){  
                    section+=number;  
                    returnNumber+=section;  
                    break;  
                }  
            }else{  
                int chnNameValueIndex=ChnUnitToValue(str.substring(pos,pos+1));  
                //chnNameValue[chnNameValueIndex].secUnit==true，表示该位置所对应的权位是节权位，  
                if(chnNameValue[chnNameValueIndex].secUnit){  
                    section=(section+number)*chnNameValue[chnNameValueIndex].value;  
                    returnNumber+=section;  
                    section=0;  
                }else{  
                    section+=number*chnNameValue[chnNameValueIndex].value;  
                }  
                pos++;  
                number=0;  
                if(pos>=str.length()){  
                    returnNumber+=section;  
                    break;  
                }  
            }  
        }  
        return returnNumber;  
    }  
    
    /** 
     * 获得某一天是周几
    *@param date是为则默认今天日期、可自行设置“2013-06-03”格式的日期 
    *@return  返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六 
    */  
    public static int getDayofweek(String date){  
      Calendar cal = Calendar.getInstance();  
      if (date == null || date.equals("")) {  
       cal.setTime(new Date(System.currentTimeMillis()));  
      }else {  
       cal.setTime(new Date(strToDate(date).getTime()));  
      }  
       return cal.get(Calendar.DAY_OF_WEEK);  
     }
    /**
     * 获得某个月的天数
     * @param dateStr：格式2015-02-11
     * @return
     * 上午11:45:25
     *
     */
    public static int getDaysOfMonth(String dateStr) {
    	Calendar calendar = Calendar.getInstance();
    	Date date = new Date(System.currentTimeMillis());
    	if (!"".equals(dateStr)) { 
    		date = new Date(strToDate(dateStr).getTime());
    	}
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
    /**
     * 获取当前时间的前几个月
     * @param date
     * @return
     * 下午3:37:08
     *
     */
    public static String getPreMonth(Date date,int num){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        Date m = c.getTime();
        String mon = format.format(m);
        System.out.println("过去一个月："+mon);
        return mon;
    }
    /**
     * 获取某一个月的第一个周一
     * @param date 
     * @return yyyy-MM-dd
     *
     */
    public static String getFirstMonday(Date date){
    	//Date date = DateFormatUtil.strToDateLong("2018-12-15 15:12:02");
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	int i = 1;
    	while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
    		cal.set(Calendar.DAY_OF_MONTH, i++);
    	}
    	Date firstMonday = cal.getTime();
    	String dtStr = new SimpleDateFormat("yyyy-MM-dd").format(firstMonday);
    	System.out.println(dtStr);
    	return dtStr;
    }
    /**
     * 获取这周的周一
     * @param date
     * @return 2017-03-12
     *
     */
    public static String getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		Date time = cal.getTime();
		String dateToStr = dateToStr(time);
		return dateToStr;
	}

    
    public static void main(String[] args) { 

		System.out.println(getStringDateShort());

    }  
  
	
	
	
	
	
	
	
}
