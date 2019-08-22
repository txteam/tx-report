package com.tx.report.component.viewComponent.impl;

import com.tx.report.mapping.ViewItem;
import com.tx.report.mapping.ViewItemAttr;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/23.
 */
@Component
public  class DateTypeViewComponent extends  AbstractViewComponent{
    @Override
    public String supportViewType() {
        return "date";
    }

    @Override
    protected Map<String, ViewItemAttr> addDefaultAttrs(ViewItem viewItem) {
        Map<String, ViewItemAttr> map =  super.addDefaultAttrs(viewItem);

        String format = viewItem.getRefValue();
        format = StringUtils.isEmpty(format)?"yyyy-MM-dd hh:mm:ss":format;

        StringBuffer value = new StringBuffer();
        value.append("function(cellvalue, options, rowObject){");
        value.append(" if(cellvalue==null){return \"\";} ")
                .append(" var date = new Date(); ")
                .append(" date.setTime(cellvalue); ")
                .append(" return date.format('"+format+"'); ")
                .append("}");

        map.put("formatter",new ViewItemAttr("formatter",value.toString(),true));

        return map;
    }
}

