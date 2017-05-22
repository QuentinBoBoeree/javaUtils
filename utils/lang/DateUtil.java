package com.metaship.edu.utils.lang;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by guohuanmeng on 2017/4/19.
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String YYYYMMDD_SIMPLE = "yyyy/MM/dd";
    public static final String YYYYMMDDHHMISS_SIMPLE = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYYMMDDHH_SIMPLE = "yyyy/MM/dd HH";
    public static final String YYYYMMDDHHMI_SIMPLE = "yyyy/MM/dd HH:mm";

    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMISS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
    public static final String YYYYMMDDHHMI = "yyyy-MM-dd HH:mm";


    public static final String YYYYMMDD_NOSEP = "yyyyMMdd";
    public static final String YYYYMMDDHHMMSS_NOSEP = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHH_NOSEP = "yyyyMMddHH";
    public static final String YYYYMMDDHHMI_NOSEP = "yyyyMMddHHmm";

    /**
     * 获取当前时间.
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前时间.
     *
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 按照yyyy-MM-dd的格式转化日期.
     *
     * @param date
     * @return
     */
    public static String toStr(Date date) {
        return toStr(date, YYYYMMDD);
    }

    /**
     * 按照特殊格式转化日期为字符串.
     *
     * @param date
     * @param datePattern
     * @return
     */
    public static String toStr(Date date, String datePattern) {
        String dateStr = null;
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
            dateStr = simpleDateFormat.format(date);
        }
        return dateStr;
    }

    /**
     * 当前日期转str。
     *
     * @return
     */
    public static String sysdateToStr() {
        return sysdateToStr(YYYYMMDD);
    }

    /**
     * 当前日期转str。
     *
     * @param datePattern
     * @return
     */
    public static String sysdateToStr(String datePattern) {
        Date currentDate = getCurrentDate();
        return toStr(currentDate, datePattern);
    }

    /**
     * 将字符串转化为date类型.
     *
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static Date toDate(String dateStr, String datePattern) {
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期转化出错", e);
        }
        return date;
    }

    /**
     * 根据日期字符串，获取该日期的星期值。
     *
     * @param dateStr
     * @param datePattern
     * @return
     */
    public static String getWeekDayStr(String dateStr, String datePattern) {
        Date date = toDate(dateStr, datePattern);
        return getWeekDayStr(date);
    }

    /**
     * 根据日期获取该日期的星期值。
     *
     * @param date
     * @return
     */
    public static String getWeekDayStr(Date date) {
        int weekDayNum = getWeekDayNum(date);
        String week = null;
        switch (weekDayNum) {
            case 1:
                week = "日";
                break;
            case 2:
                week = "一";
                break;
            case 3:
                week = "二";
                break;
            case 4:
                week = "三";
                break;
            case 5:
                week = "四";
                break;
            case 6:
                week = "五";
                break;
            case 7:
                week = "六";
                break;
            default:
                week = "";
                break;
        }
        return week;
    }

    /**
     * 根据日期获取星期值。
     *
     * @param date
     * @return
     */
    public static int getWeekDayNum(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar c = getCalendar(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据日期获取日志对象.
     *
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date) {
        Calendar c = GregorianCalendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 获取某日期当年第一天.
     *
     * @param date
     * @return
     */
    public static Date getYearFirstDay(Date date) {
        Calendar c = getCalendar(date);
        c.set(Calendar.DAY_OF_YEAR, 1);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获取某日期当月第一天.
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar c = getCalendar(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获取某日期本周的周一.
     *
     * @param date
     * @return
     */
    public static Date getWeekFirstDay(Date date) {
        Calendar c = getCalendar(date);
        c.set(Calendar.DAY_OF_WEEK, 2);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获取某日期当月最后一天.
     *
     * @param date
     * @return
     */
    public static Date getMonthEndDay(Date date) {
        Calendar c = getCalendar(date);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.MILLISECOND, -1);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 获取日期当月的第一天和最后一天
     *
     * @param date
     * @param datePattern
     * @return
     */
    public static String[] getMonthFirstdayEndday(Date date, String datePattern) {
        Date monthFirstDay = getMonthFirstDay(date);
        Date monthEndDay = getMonthEndDay(date);
        String[] monthDay = {toStr(monthFirstDay, datePattern), toStr(monthEndDay, datePattern)};
        return monthDay;
    }

    /**
     * 年数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addYear(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.YEAR, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 月数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addMonth(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.MONTH, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 星期数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addWeek(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.WEEK_OF_MONTH, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 天数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addDay(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.DAY_OF_MONTH, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 小时数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addHour(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.HOUR_OF_DAY, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 分钟数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addMinute(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.MINUTE, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 秒数增加count.
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addSecond(Date date, int count) {
        Calendar c = getCalendar(date);
        c.add(Calendar.SECOND, count);
        return new Date(c.getTimeInMillis());
    }

    /**
     * 根据开始日期和结束日期，格式要求为yyyy-MM-dd，获取该格式的日期串列表.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDateList(String startDate, String endDate) {
        return getDateList(startDate, endDate, YYYYMMDD);
    }

    /**
     * 根据开始日期和结束日期，获取指定格式的日期串列表.
     * 按照开始日期和截止日期格式排列.
     *
     * @param startDate
     * @param endDate
     * @param datePattern
     * @return
     */
    public static List<String> getDateList(String startDate, String endDate, String datePattern) {
        return getDateList(toDate(startDate, datePattern), toDate(endDate, datePattern), datePattern);
    }

    /**
     * 根据开始日期和结束日期，获取指定格式的日期串列表.
     * 按照开始日期和截止日期格式排列.
     *
     * @param startDate
     * @param endDate
     * @param datePattern
     * @return
     */
    public static List<String> getDateList(Date startDate, Date endDate, String datePattern) {
        List<String> list = Lists.newArrayList();
        list.add(toStr(startDate, datePattern));
        if (startDate.equals(endDate)) {
            return list;
        }
        int add = 1;
        if (startDate.after(endDate)) {
            add = -1;
        }

        Date date = startDate;
        while (add == 1 ? date.before(endDate) : date.after(endDate)) {
            Date nextDate = addDay(date, add);
            date = nextDate;
            list.add(toStr(nextDate, datePattern));
        }
        return list;
    }

    /**
     * 生成时间信息
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date generateTime(int year, int month, int day, String hour, String minute, String second) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
        Date date = format.parse(time);
        return date;
    }

    /**
     * 获取当前时间的年份
     *
     * @return
     */
    public static int getYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     *
     * @return
     */
    public static int getMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取当前的calendar
     *
     * @return
     */
    public static Calendar getCalendar() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 获取当前月份的日期
     *
     * @return
     */
    public static int getDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间的前一天时间
     *
     * @param cl
     * @return
     */
    public static Calendar getBeforeDay(Calendar cl) {
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);
        return cl;
    }

    /**
     * 获取当前时间的后一天时间
     *
     * @param cl
     * @return
     */
    public static Calendar getAfterDay(Calendar cl) {
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        cl.setTime(new Date());
        cl.add(Calendar.DAY_OF_MONTH, 1);
        return cl;
    }

    /**
     * 根据日历查询年份
     *
     * @param calendar
     * @return
     */
    public static int getYearByCalendar(Calendar calendar) {
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonthByCalendar(Calendar calendar) {
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 根据日历查询日期
     *
     * @param calendar
     * @return
     */
    public static int getDayByCalendar(Calendar calendar) {
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Timestamp toTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Timestamp toTimestamp(String date, String datePattern) {
        return toTimestamp(DateUtil.toDate(date, datePattern));
    }


    public static void main(String[] args) {
        Calendar calendar = DateUtil.getCalendar();
        System.err.println(getDayByCalendar(DateUtil.getAfterDay(calendar)));
        System.out.println(DateUtil.toStr(DateUtil.addHour(DateUtil.getCurrentDate(), 6), DateUtil.YYYYMMDDHHMISS));
        System.err.println(toDate("2017-06-11 00:00:00", DateUtil.YYYYMMDDHHMISS));
    }
}
