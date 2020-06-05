package com.airwallex.rpncalculator.enums;

import com.airwallex.rpncalculator.exception.UnknownOperatorException;
import org.junit.Assert;
import org.junit.Test;

/**
 * description.
 *
 * @author Yongfeng.Zhang
 * @date 2020/6/2 10:36
 * @since V1.0
 */
public class ControlTest {

    @Test
    public void testIsControl() {
        Assert.assertTrue(Control.isControl("undo"));
        Assert.assertTrue(Control.isControl("uNdo"));
        Assert.assertTrue(Control.isControl("UndO"));
        Assert.assertTrue(Control.isControl("clear"));
        Assert.assertTrue(Control.isControl("Clear"));
        Assert.assertTrue(Control.isControl("Clear"));

        Assert.assertTrue(!Control.isControl("+"));
        Assert.assertTrue(!Control.isControl("5"));
        Assert.assertTrue(!Control.isControl("abv"));
        Assert.assertTrue(!Control.isControl("set"));

        Assert.assertTrue(!Control.isControl(""));
        Assert.assertTrue(!Control.isControl(" "));

        Assert.assertTrue(!Operator.isOperator("\\"));
        Assert.assertTrue(!Operator.isOperator("\n"));
    }

    @Test
    public void testParseControl() {
        Assert.assertEquals(Control.UNDO, Control.parseControl("undo"));
        Assert.assertEquals(Control.CLEAR, Control.parseControl("Clear"));
        Assert.assertEquals(Control.CLEAR, Control.parseControl("clear"));
        Assert.assertEquals(Control.CLEAR, Control.parseControl("CLEAR"));
    }

    @Test(expected = UnknownOperatorException.class)
    public void testParseControlException() {
        Control.parseControl("");
        Control.parseControl(" ");
        Control.parseControl(".");
        Control.parseControl("set");
        Control.parseControl("+");
    }

}
