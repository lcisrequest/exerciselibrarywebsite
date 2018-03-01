package com.example.lcdemo.base.util;

import java.text.DecimalFormat;

/**
 * 数值格式化工具
 * Created by tsy
 */
public class NumberUtil {

    private static DecimalFormat twoDecimalFormat = new DecimalFormat("0.00");

    /**
     * 保留两位小数格式化
     * @param number
     * @return
     */
    public static String floatToFormat(double number) {
        return twoDecimalFormat.format(number);
    }

    public static String floatStrToFormat(String number) {
        return floatToFormat(Double.valueOf(number));
    }

    /**
     * 格式化数值，并将分转化为元
     * @param number
     * @return
     */
    public static String pointAmountTrans(String number) {
        Double num = Double.valueOf(number);
        return floatToFormat(num / 100);
    }

    /**
     * 格式化数值，并将分转化为元
     * @param number
     * @return
     */
    public static String pointAmountTrans(Object number) {
        Double num = Double.valueOf(number.toString());
        return floatToFormat(num / 100);
    }

    /**
     * 格式化数据，将小数转化为百分数，并带上百分号
     * @param point
     * @return
     */
    public static String point2Rate(String point) {
        Double p = Double.valueOf(point);
        return floatToFormat(p * 100) + " %";
    }

    /**
     * 格式化数据，将小数转化为百分数，并带上百分号
     * @param point
     * @return
     */
    public static String point2Rate(Object point) {
        Double p = Double.valueOf(point.toString());
        return floatToFormat(p * 100) + " %";
    }

}
