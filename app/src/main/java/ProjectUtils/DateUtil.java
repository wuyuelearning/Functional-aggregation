package ProjectUtils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    public static final char[] KOR_WEEK = {'日', '一', '二', '三', '四', '五', '六'};
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
    private static SimpleDateFormat dateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy年MM月dd日");

    //by zhaowei
    public static String showCurrentDate(Date date) {
        if (isToday(date))
            return "今天";
        else if (isYesterday(date))
            return "昨天 ";
        else
            return dateFormat_1.format(date);
    }

    public static String convertDateToString(long seconds, String format) {
        if (StringUtil.equalsNullOrEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(seconds));
    }

    /**
     * 2016-07-14 lhw
     * yyyy.MM.dd HH:mm:ss ；yyyy-MM-dd HH:mm:ss ；yyyy/MM/dd HH:mm:ss
     */
    public static String toStringDateFormat(String datestr) {
        if (TextUtils.isEmpty(datestr)) {
            return "";
        }
        String mPattern = "yyyy.MM.dd";//
        SimpleDateFormat format;
        try {
            if (datestr.contains(".")) {
                mPattern = "yyyy.MM.dd";
            } else if (datestr.contains("-")) {
                mPattern = "yyyy-MM-dd";
            } else if (datestr.contains("/")) {
                mPattern = "yyyy/MM/dd";
            }
            format = new SimpleDateFormat(mPattern);
            String laststr = format.format(format.parse(datestr));
            if (laststr.contains("-")) {
                laststr = laststr.replace("-", ".");
            }
            return laststr;
        } catch (Exception e) {
            return "";
        }
    }

    public static Date toDate(String str) {
        String pattern;
        SimpleDateFormat format;
        try {
            if (str.contains(".")) {
                if (str.contains(" ") && str.contains(":")) {
                    pattern = "yyyy.MM.dd HH:mm:ss";
                } else {
                    pattern = "yyyy.MM.dd";
                }
            } else if (str.contains("-")) {
                if (str.contains(" ") && str.contains(":")) {
                    pattern = "yyyy-MM-dd HH:mm:ss";
                } else {
                    pattern = "yyyy-MM-dd";
                }
            } else if (str.contains("/")) {
                if (str.contains(" ") && str.contains(":")) {
                    pattern = "yyyy/MM/dd HH:mm:ss";
                } else {
                    pattern = "yyyy/MM/dd";
                }
            } else {
                long milliseconds = Long.parseLong(str);
                return new Date(milliseconds * 1000);
            }
            format = new SimpleDateFormat(pattern);
            return format.parse(str);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static long convertStringToDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null ? date.getTime() : 0;
    }

    static final long ONE_DAY_LONG = 24 * 60 * 60 * 1000;

    public static boolean isToday(Date date1) {
        return (date1.getTime() / ONE_DAY_LONG) == (System.currentTimeMillis() / ONE_DAY_LONG);
    }

    public static boolean isYesterday(Date date1) {
        return (date1.getTime() / ONE_DAY_LONG + 1) == (System.currentTimeMillis() / ONE_DAY_LONG);
    }

    public static String getCurrentDay(Date date) {
        return dateFormat_1.format(date);
    }

    public static String dataMonthDayYear(String data) {
        if (StringUtil.equalsNullOrEmpty(data)) {
            return "";
        }
        String year = data.substring(0, 4);
        String month = data.substring(5, 7);
        String day = data.substring(8, data.length());

        return month + "月" + day + "日    " + year + "年";
    }

    public static String dataShow(String data) {
        if (StringUtil.equalsNullOrEmpty(data)) {
            return "";
        }
        // String year = data.substring(0, 4);
        String month = data.substring(5, 7);
        String day = data.substring(8, data.length());

        return month + "月" + day + "日";
    }

    public static String dataYearMonthDAY(String data) {
        if (StringUtil.equalsNullOrEmpty(data)) {
            return "";
        }
        String year = data.substring(0, 4);
        String month = data.substring(5, 7);
        String day = data.substring(8, data.length());

        return year + "年" + month + "月" + day + "日";
    }

    public static int todayBookNotice(Calendar drawCal) {
        Calendar todayCal = Calendar.getInstance();
        int year = drawCal.get(Calendar.YEAR);
        int month = drawCal.get(Calendar.MONTH);
        int day = drawCal.get(Calendar.DAY_OF_MONTH);
        int ty = todayCal.get(Calendar.YEAR);
        int tm = todayCal.get(Calendar.MONTH);
        int td = todayCal.get(Calendar.DAY_OF_MONTH);
        if (year == ty && month == tm && day == td) {
            return 0;
        }
        todayCal.add(Calendar.DAY_OF_MONTH, -1);
        ty = todayCal.get(Calendar.YEAR);
        tm = todayCal.get(Calendar.MONTH);
        td = todayCal.get(Calendar.DAY_OF_MONTH);
        if (year == ty && month == tm && day == td) {
            return -1;
        } else if (drawCal.compareTo(todayCal) < 0) {
            return -2;
        } else {
            todayCal.add(Calendar.DAY_OF_MONTH, 2);
            ty = todayCal.get(Calendar.YEAR);
            tm = todayCal.get(Calendar.MONTH);
            td = todayCal.get(Calendar.DAY_OF_MONTH);
            if (year == ty && month == tm && day == td) {
                return 1;
            } else if (drawCal.compareTo(todayCal) > 0) {
                return 2;
            }
        }
        return -2;
    }

    /**
     * 获取两个日期相差几天
     *
     * @return
     */
    public static long getSubtractDays(String startTime, String endTime, String format) {
        if (StringUtil.equalsNullOrEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        long day = 0;
        if (StringUtil.equalsNullOrEmpty(startTime) || StringUtil.equalsNullOrEmpty(endTime)) {
            return day;
        }
        SimpleDateFormat sd = new SimpleDateFormat(format);
        try {
            //获得两个时间的毫秒时间差异
            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / (1000 * 24 * 60 * 60);//计算差多少天
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static int todayBookNotice() {
        Calendar cal = Calendar.getInstance();
        int nowTime = cal.get(Calendar.HOUR_OF_DAY);
        return 17 - nowTime;
    }

    public static String tomorrowDate() {
        return tomorrowDate(false);
    }

    public static String tomorrowDate(boolean isNoChinaFormat) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        if (isNoChinaFormat) {
            return dateFormat_1.format(date);
        } else {
            return dateFormat.format(date);
        }
    }

    public static String todayDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return dateFormat.format(date);
    }

    public static String yearMonthDay(int year, int month, int day) {
        String mmonth = month + 1 < 10 ? "0" + (month + 1) : Integer
                .toString(month + 1);
        String mday = day < 10 ? "0" + day : day + "";
        return year + "-" + mmonth + "-" + mday;
    }

    public static String getWeekValue(int week) {

        String weekText = "周";
        switch (week) {
            case 1:
                weekText = weekText + "日";
                break;
            case 2:
                weekText = weekText + "一";
                break;
            case 3:
                weekText = weekText + "二";
                break;
            case 4:
                weekText = weekText + "三";
                break;
            case 5:
                weekText = weekText + "四";
                break;
            case 6:
                weekText = weekText + "五";
                break;
            case 7:
                weekText = weekText + "六";
                break;

            default:
                weekText = weekText + String.valueOf(week);
                break;
        }
        return weekText;
    }

    /* 获得今天的日期 */
    public static String getCurrentDay() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return yearMonthDay(year, month, day);
    }

    /**
     * 获得明天的日期
     */
    public static String getTomorrowDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /* 获得当前的时间 */
    public static String getCurrentTime() {
        Date currentTime = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = df.format(currentTime);
        return dateString;
    }

    /* 获得随机的时间在一个时间范围内的  */
    public static String getRandomDate(String beginDate, String endDate) {
        Date randomDate = randomDate(beginDate, endDate);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(randomDate);

    }

    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);// 开始日期
            Date end = format.parse(endDate);// 结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }


    /* 判断是否在凌晨0-6点 */
    public static boolean isInMonning() {
        if (compare_Time(getCurrentTime(), (getCurrentDay() + " 00:00:00")) == 0) {
            return true;
        } else if (compare_Time(getCurrentTime(),
                (getCurrentDay() + " 00:00:00")) == 1
                && compare_Time(getCurrentTime(),
                (getCurrentDay() + " 06:00:00")) == -1) {// 现在时间在0点后6点前
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    public static long toLong(String timestr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = df.parse(timestr);
        } catch (ParseException e) {
            return 0;
        }
        return date.getTime();
    }


    /**
     * 获得指定日期的后的第3天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayThirdDay(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 3);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 获得指定日期是周几
     *
     * @param
     */
    @SuppressWarnings("deprecation")
    public static String dayForWeek(String pTime) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date tmpDate = format.parse(pTime, new ParsePosition(0));

        Calendar mCal = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);
        // cal.set(tmpDate.getYear(), tmpDate.getMonth(), tmpDate.getDay());

        if (compare_calendar(mCal, cal) == 0) {
            return "今天";
        }
        mCal.add(Calendar.DAY_OF_MONTH, 1);
        if (compare_calendar(mCal, cal) == 0) {
            return "明天";
        }

        mCal.add(Calendar.DAY_OF_MONTH, 1);
        if (compare_calendar(mCal, cal) == 0) {
            return "后天";
        }

        return getWeekValue(cal.get(Calendar.DAY_OF_WEEK));
    }

    public static String dayForHotelWeek(String pTime) throws Throwable {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date tmpDate = format.parse(pTime);

        Calendar mCal = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);
        // cal.set(tmpDate.getYear(), tmpDate.getMonth(), tmpDate.getDay());

        if (compare_calendar(mCal, cal) == 0) {
            return "今天";
        }
        mCal.add(Calendar.DAY_OF_MONTH, 1);
        if (compare_calendar(mCal, cal) == 0) {
            return "明天";
        }
        return getWeekValue(cal.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * 判断两个日期大小 第一个比第二个大返回(一在二后)-1，第二个比第一个大（一在二前）返回1，相等返回0
     */
    public static int compare_date(String date1, String date2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() == dt2.getTime()) {
                return 0;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断两个时间大小 第一个比第二个大返回(一在二后)-1，第二个比第一个大（一在二前）返回1，相等返回0
     */
    public static int compare_Time(String DATE1, String DATE2) {
        Date dt1 = null, dt2 = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dt1 = df.parse(DATE1);
            dt2 = df.parse(DATE2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {

            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {

            return -1;
        } else {
            return 0;
        }
    }

    public static int compare_calendar(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            return -1;
        }
        if ((cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                && (cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH))) {
            return 0;
        }
        return -1;

    }

    /**
     * 获得指定日期的日
     *
     * @param date
     * @return
     */
    public static String getDay(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String days;
        if (day < 10) {
            days = "0" + Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        } else {
            days = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        }
        return days;
    }

    /**
     * 获得指定日期的月
     *
     * @param date
     * @return
     */
    public static String getMonth(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        return Integer.toString(cal.get(Calendar.MONTH) + 1) + "月";
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int dayForWeekOther(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static String getWeekValueOther(int week) {

        String weekText = "周";
        switch (week) {
            case 1:
                weekText = weekText + "一";
                break;
            case 2:
                weekText = weekText + "二";
                break;
            case 3:
                weekText = weekText + "三";
                break;
            case 4:
                weekText = weekText + "四";
                break;
            case 5:
                weekText = weekText + "五";
                break;
            case 6:
                weekText = weekText + "六";
                break;
            case 7:
                weekText = weekText + "日";
                break;
            default:
                weekText = weekText + String.valueOf(week);
                break;
        }
        return weekText;
    }

    //分钟转换时分
    public static String remainTimeStr(int minutes) {
        String returnstr = "";
        if (minutes > 0) {
            int hour = minutes / 60;
            int minute = minutes % 60;
            returnstr = new StringBuilder().append(hour).append("时").append(minute).append("分").toString();
        }
        return returnstr;
    }


    public static Map<Integer, String> dateBellowKeyInteger(String date1, String date2) {
        HashMap<Integer, String> dateMap = new HashMap<Integer, String>();
        Calendar cal1 = getStringCal(date1);
        Calendar cal2 = getStringCal(date2);
        Calendar tempCal;
        if (cal1.after(cal2)) {
            tempCal = cal1;
            cal1 = cal2;
            cal2 = tempCal;
        }
        boolean flag = true;
        int i = 0;
        while (flag) {
            if (cal1.before(cal2) || compare_calendar(cal1, cal2) == 0) {
                dateMap.put(i, getCalString(cal1));
            } else {
                flag = false;
            }
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            i++;
        }


        return dateMap;
    }

    public static long getSumDay(String checkInDate, String leaveDate) {
        long day = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            day = (dateFormat.parse(leaveDate).getTime() - dateFormat.parse(checkInDate).getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static Map<String, String> datebellow(String date1, String date2) {
        HashMap<String, String> dateMap = new HashMap<String, String>();
        Calendar cal1 = getStringCal(date1);
        Calendar cal2 = getStringCal(date2);
        Calendar tempCal;
        if (cal1.after(cal2)) {
            tempCal = cal1;
            cal1 = cal2;
            cal2 = tempCal;
        }
        boolean flag = true;
        while (flag) {

            if (cal1.before(cal2) || compare_calendar(cal1, cal2) == 0) {
                // zzf add 去除依赖com.gift.android.Utils.Constant.TRAIN_MAP
                final String TRAIN_MAP = "train";
                dateMap.put(getCalString(cal1), TRAIN_MAP);
            } else {
                flag = false;
            }
            cal1.add(Calendar.DAY_OF_MONTH, 1);
        }


        return dateMap;
    }

    public static Calendar getStringCal(String date) {

        Calendar cal = Calendar.getInstance();
        if (TextUtils.isEmpty(date)) {
            return cal;
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, date.length()));
        cal.set(year, month - 1, day);

        return cal;
    }

    public static String getCalString(Calendar cal) {
        if (cal == null) {
            return "";
        }
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);


        return year + "-" + (month >= 10 ? month : "0" + month) + "-" + (day >= 10 ? day : "0" + day);
    }


    private final static long yearLevelValue = 365 * 24 * 60 * 60 * 1000;
    private final static long monthLevelValue = 30 * 24 * 60 * 60 * 1000;
    private final static long dayLevelValue = 24 * 60 * 60 * 1000;
    private final static long hourLevelValue = 60 * 60 * 1000;
    private final static long minuteLevelValue = 60 * 1000;
    private final static long secondLevelValue = 1000;

    /**
     * ****计算出时间差中的 时、分、秒******
     */
    public static String[] getDifference(String nowTime, String targetTime) {//目标时间与当前时间差

        long period = Long.parseLong(targetTime) - Long.parseLong(nowTime);
        String[] result = getDifference(period);
        if (result == null) {
            result = new String[6];
        }
        return result;
    }

    private static String[] getDifference(long period) {//根据毫秒差计算时间差
        String[] result = new String[6];


        /*******计算出时间差中的年、月、日、天、时、分、秒*******/
//        int year = getYear(period) ;  
//        int month = getMonth(period - year*yearLevelValue) ;  
//        int day = getDay(period - year*yearLevelValue - month*monthLevelValue) ;  
        int hour = getHour(period);
        int minute = getMinute(period - hour * hourLevelValue);
        int second = getSecond(period - hour * hourLevelValue - minute * minuteLevelValue);
        if (hour > 0 && hour < 10) {
            result[0] = "0";
            result[1] = Integer.toString(hour);
        } else {
            result[0] = Integer.toString(hour / 10);
            result[1] = Integer.toString(hour % 10);
        }
        if (minute > 0 && minute < 10) {
            result[2] = "0";
            result[3] = Integer.toString(minute);
        } else {
            result[2] = Integer.toString(minute / 10);
            result[3] = Integer.toString(minute % 10);
        }
        if (second > 0 && second < 10) {
            result[4] = "0";
            result[5] = Integer.toString(second);
        } else {
            result[4] = Integer.toString(second);
            result[5] = Integer.toString(second);
        }
        return result;
    }

    public static int getYear(long period) {
        return (int) (period / yearLevelValue);
    }

    public static int getMonth(long period) {
        return (int) (period / monthLevelValue);
    }

    public static int getDay(long period) {
        return (int) (period / dayLevelValue);
    }

    public static int getHour(long period) {
        return (int) (period / hourLevelValue);
    }

    public static int getMinute(long period) {
        return (int) (period / minuteLevelValue);
    }

    public static int getSecond(long period) {
        return (int) (period / secondLevelValue);
    }

    public static String getTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    /**
     * 日期转换月日
     */
    public static String getFormatTimetoMD(String times) {
        String formatStr = "";
        try {
            String[] tmp = times.split("-");
            if (tmp.length >= 2) {
                formatStr = tmp[1] + "月" + tmp[2] + "日";

            }
        } catch (Exception e) {
            formatStr = times;
        }
        return formatStr;
    }
}
