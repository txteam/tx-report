/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-4-2
 * <修改描述:>
 */
package com.tx.report.exceptions.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.Assert;

/**
 * 断言工具类<br/>
 * <功能详细描述>
 *
 * @author brady
 * @version [版本号, 2013-4-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class AssertUtils {


    public static void notEmpty(Object obj, String s) {
        Assert.notNull(obj, s);
    }

    public static void notNull(Object obj, String s) {
        Assert.notNull(obj, s);
    }

    public static void isTrue(boolean b, String s, Object[] objects) {
        Assert.isTrue(b, s);

    }

    public static void isTrue(boolean b, String s) {
        Assert.isTrue(b, s);
    }

    public static void isInstanceOf(Class clazz, Object obj, String s) {
        Assert.isInstanceOf(clazz, obj, s);
    }
}
