package com.smk.cpp.sts.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月10日 19:07
 * @Description:
 */
public class DateUtils {

    private final static SimpleDateFormat DATE_FORMAT_TIME_LONG = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    private final static SimpleDateFormat DATE_FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat DATE_FORMAT_DAYS = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static Calendar calendar = Calendar.getInstance();

    /**
     *  获取当天的开始时间
     * @return
     */
    public static Date getDayBegin() {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     *  获取当天的结束时间
     * @return
     */
    public static Date getDayEnd() {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    /**
     *  获取昨天的开始时间
     * @return
     */
    public static Date getBeginDayOfYesterday() {
        calendar.setTime(getDayBegin());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }
    /**
     *  获取昨天的结束时间
     * @return
     */
    public static Date getEndDayOfYesterDay() {
        calendar.setTime(getDayEnd());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     *  获取明天的开始时间
     * @return
     */
    public static Date getBeginDayOfTomorrow() {
        calendar.setTime(getDayBegin());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    /**
     *  获取明天的结束时间
     * @return
     */
    public static Date getEndDayOfTomorrow() {
        calendar.setTime(getDayEnd());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取本周的开始时间
     * @return
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }
        calendar.add(Calendar.DATE, 2 - dayOfWeek);
        return getDayStartTime(calendar.getTime());
    }
    /**
     *  获取本周的结束时间
     * @return
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     *  获取本月的开始时间
     * @return
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }
    /**
     *  获取本月的结束时间
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     *  获取今年是哪一年
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }
    /**
     *  获取本月是哪一月
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

    /**
     *  获取某个日期的开始时间
     * @param date Date
     * @return
     */
    public static Date getDayStartTime(Date date) {
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(
                calendar.get(Calendar.YEAR), 
                calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH), 
                0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }
    /**
     *  获取某个日期的结束时间
     * @param d
     * @return
     */
    public static Date getDayEndTime(Date d) {
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(
                calendar.get(Calendar.YEAR), 
                calendar.get(Calendar.MONTH), 
                calendar.get(Calendar.DAY_OF_MONTH), 
                23,59,59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * 日期时间偏移
     * offset = 1,date=2018-11-02 16:47:00
     * 结果：2018-11-03 16:47:00
     * @param date
     * @param offset
     * @return
     */
    public static Date offset(Date date, int offset) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar.getTime();
    }

    /**
     * 日期偏移
     * @param date Date
     * @param offset int
     * @param cal int
     * @return java.util.Date
     */
    public static Date offset(Date date, int offset,int cal) {
        calendar.setTime(date);
        calendar.add(cal, offset);
        return calendar.getTime();
    }
    
}
