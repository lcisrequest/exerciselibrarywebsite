package com.example.lcdemo.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 股票相关操作方法
 * Created by tsy
 */
public class StockUtil {

    public static final String ZSJC_ORDER_CODE_DATE_FORMAT="yyyyMMddHHmm";



    /**
     * 获取股票下一个开盘格式化时间
     * @return
     */
    public static Date getNextKPSJ(){
        Calendar nowCal = Calendar.getInstance();
        Date nowDate = nowCal.getTime();

        Date nowFormatDate = null;
        Date d1 = null;//0:00
        Date d2 = null;//09:30
        Date d3 = null;//13:00
        Date d4 = null;//24:00
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            nowFormatDate = sdf.parse(sdf.format(nowDate));
            d1 = sdf.parse("00:00");
            d2 = sdf.parse("11:30");
            d3 = sdf.parse("15:00");
            d4 = sdf.parse("24:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nowFormatDate.after(d2) && nowFormatDate.before(d3)) {
            //开盘时间为下午
            //如果是周六和周日 则取周一早上
            if (nowCal.get(Calendar.DAY_OF_WEEK) == 7) {
                nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 2);
                nowCal.set(Calendar.HOUR_OF_DAY, 11);
                nowCal.set(Calendar.MINUTE, 30);
            } else if (nowCal.get(Calendar.DAY_OF_WEEK) == 1) {
                nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 1);
                nowCal.set(Calendar.HOUR_OF_DAY, 11);
                nowCal.set(Calendar.MINUTE, 30);
            }else{
                nowCal.set(Calendar.HOUR_OF_DAY, 15);
                nowCal.set(Calendar.MINUTE, 00);
            }
        }else{
            nowCal.set(Calendar.HOUR_OF_DAY, 11);
            nowCal.set(Calendar.MINUTE, 30);
            //开盘时间为上午
            if (nowFormatDate.after(d1) && nowFormatDate.before(d2)) {
                //早上开盘前
            }else{
                //下午闭盘后
                if (nowCal.get(Calendar.DAY_OF_WEEK) == 6) {
                    nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 3);
                } else if (nowCal.get(Calendar.DAY_OF_WEEK) == 7) {
                    nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 2);
                } else {
                    nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 1);
                }
            }
            //判断是周几
            //周五\六\日 取周一

        }
        return nowCal.getTime();
    }

    /**
     * 获取竞猜时间和开盘时间字符串
     * @return 0竞猜时间 1开盘时间
     */
    public static String[] getNextJCSJ(){
        String[] re = new String[2];
        Calendar nowCal = Calendar.getInstance();
        Date nowDate = nowCal.getTime();

        Date nowFormatDate = null;
        Date d1 = null;//0:00
        Date d2 = null;//09:30
        Date d3 = null;//13:00
        Date d4 = null;//24:00
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            nowFormatDate = sdf.parse(sdf.format(nowDate));
            d1 = sdf.parse("00:00");
            d2 = sdf.parse("09:30");
            d3 = sdf.parse("13:00");
            d4 = sdf.parse("24:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nowFormatDate.after(d2) && nowFormatDate.before(d3)) {
            //开盘时间为下午
            nowCal.set(Calendar.HOUR_OF_DAY, 15);
            nowCal.set(Calendar.MINUTE, 00);
            re[0] = "11:30 - 13:00";
            re[1] = new SimpleDateFormat("yyyy-MM-dd").format(nowCal.getTime()) + "下午大盘走势";
        }else{
            //开盘时间为上午
            //判断是周几
            nowCal.set(Calendar.HOUR_OF_DAY, 11);
            nowCal.set(Calendar.MINUTE, 30);

            //周五\六\日 取周一
            if (nowFormatDate.after(d1) && nowFormatDate.before(d2)) {
                //早上开盘前
            }else{
                //下午闭盘后
                if (nowCal.get(Calendar.DAY_OF_WEEK) == 6) {
                    nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 3);
                } else if (nowCal.get(Calendar.DAY_OF_WEEK) == 7) {
                    nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 2);
                } else {
                    nowCal.set(Calendar.DATE, nowCal.get(Calendar.DATE) + 1);
                }
            }
            re[0] = "15:00 - 09:30";
            re[1] = new SimpleDateFormat("yyyy-MM-dd").format(nowCal.getTime()) + "上午大盘走势";
        }
        return re;
    }

    /**
     * 判断是否为竞猜时间
     * @return
     */
    public static boolean isJcSj() {
        Date d1 = null;//09:30
        Date d2 = null;//11:30
        Date d3 = null;//13:00
        Date d4 = null;//15:00
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        Date nowFormatDate = null;
        try {
            nowFormatDate = sdf.parse(sdf.format(new Date()));
            d1 = sdf.parse("09:30");
            d2 = sdf.parse("11:30");
            d3 = sdf.parse("13:00");
            d4 = sdf.parse("15:00");
        } catch (Exception e) {
            return false;
        }
        //如果是周末,返回true
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        if ((nowFormatDate.after(d1) && nowFormatDate.before(d2)) || (nowFormatDate.after(d3) && nowFormatDate.before(d4))) {
            return false;
        }
        return true;
    }

    /**
     * 是否为开盘时间
     * @return
     */
    public static boolean isKpSj(){
        Date d1 = null;//09:30
        Date d2 = null;//11:30
        Date d3 = null;//13:00
        Date d4 = null;//15:00
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        Date nowFormatDate = null;
        try {
            nowFormatDate = sdf.parse(sdf.format(new Date()));
            d1 = sdf.parse("09:30");
            d2 = sdf.parse("11:30");
            d3 = sdf.parse("13:00");
            d4 = sdf.parse("15:00");
        } catch (Exception e) {
            return false;
        }
        //如果是周末,返回true
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        }
        if ((nowFormatDate.after(d1) && nowFormatDate.before(d2)) || (nowFormatDate.after(d3) && nowFormatDate.before(d4))) {
            return true;
        }
        return false;
    }

    /**
     * 获取指数竞猜单号
     * @param userId
     */
    public static String getOrderCode(Integer userId) {
        return new SimpleDateFormat(ZSJC_ORDER_CODE_DATE_FORMAT).format(getNextKPSJ()) + userId;
    }
    /**
     * 获取指数竞猜单号前缀
     */
    public static String getOrderCode() {
        return new SimpleDateFormat(ZSJC_ORDER_CODE_DATE_FORMAT).format(getNextKPSJ());
    }

    /**
     * 获取当前正在结算的订单号前缀
     * @return
     */
    public static String getNowJSSJCode(){
        Calendar nowCal = Calendar.getInstance();
        Date nowDate = nowCal.getTime();

        Date nowFormatDate = null;
        Date d1 = null;//0:00
        Date d2 = null;//09:30
        Date d3 = null;//13:00
        Date d4 = null;//24:00
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            nowFormatDate = sdf.parse(sdf.format(nowDate));
            d1 = sdf.parse("11:30");
            d2 = sdf.parse("11:40");
            d3 = sdf.parse("15:00");
            d4 = sdf.parse("15:10");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nowFormatDate.after(d1) && nowFormatDate.before(d2)) {
            nowCal.set(Calendar.HOUR_OF_DAY,11);
            nowCal.set(Calendar.MINUTE,30);
        }else{
            nowCal.set(Calendar.HOUR_OF_DAY,15);
            nowCal.set(Calendar.MINUTE,00);
        }
        return new SimpleDateFormat(ZSJC_ORDER_CODE_DATE_FORMAT).format(nowCal.getTime());
    }

    /**
     * 股票根据收盘价画MA
     * function calculateMA(dayCount, data) {
     var result = [];
     for (var i = 0, len = data.length; i < len; i++) {
     if (i < dayCount) {
     result.push('-');
     continue;
     }
     var sum = 0;
     for (var j = 0; j < dayCount; j++) {
     sum += data[i - j][1];
     }
     result.push(sum / dayCount);
     }
     return result;
     }
     */
//    public static

}
