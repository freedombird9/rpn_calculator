package com.airwallex.rpncalculator.formatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/2 11:24
 * @since V1.0
 */
public class PrettyPrintTest {

    @Test
    public void testFormatter() {
        Assert.assertEquals("1", PrettyPrint.format(1.0000));
        Assert.assertEquals("1.1234", PrettyPrint.format(1.12340));
        Assert.assertEquals("1.1234", PrettyPrint.format(1.1234000));
        Assert.assertEquals("1.1234", PrettyPrint.format(1.1234000));

        // Test display up to 10 decimal places
        Assert.assertEquals("1.1234567899", PrettyPrint.format(1.12345678989));
        Assert.assertEquals("1.1234567898", PrettyPrint.format(1.12345678984));
        Assert.assertEquals("1.1234567898", PrettyPrint.format(1.12345678984434));
        Assert.assertEquals("1.1234567898", PrettyPrint.format(1.1234567898000));
    }
}
