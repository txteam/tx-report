package com.tx.report.utils;

import java.util.Random;

/**
 * <流水号生成类>
 *
 * @author
 * @version  [版本号, 2010-7-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NumberUtils {
    public static  boolean parseBoolean(String str){
        if (str==null){
            return false;
        }
        if("1".equals(str)){
            return true;
        }
        if("true".equalsIgnoreCase(str.trim())){
            return true;
        }
        if("yes".equalsIgnoreCase(str.trim())){
            return true;
        }
        if("是".equalsIgnoreCase(str.trim())){
            return true;
        }

        return false;
    }


}
