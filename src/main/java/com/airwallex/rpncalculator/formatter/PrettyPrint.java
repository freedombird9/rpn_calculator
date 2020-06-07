package com.airwallex.rpncalculator.formatter;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Output formatting class.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/1 21:29
 * @since V1.0
 */
public class PrettyPrint {

    // Round up to 10 decimal places at most
    private static DecimalFormat df = new DecimalFormat("#.##########");

    static {
        df.setRoundingMode(RoundingMode.HALF_UP);
    }

    public static String format(double d) {
        return df.format(d);
    }

    public static void main(String[] args) {
        int in = Integer.parseInt("99999999999999999999999999999999999999999999999999999999999999999999999");

        System.out.println(in);
    }

}
