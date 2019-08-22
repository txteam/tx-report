package com.tx.report.component.conditionCompent.impl;

import com.tx.report.type.ConditionTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by SEELE on 2016/9/22.
 */
@Component("inputConditionComponment")
public class InputConditionComponment extends AbstractConditionComponent {
    @Override
    public ConditionTypeEnum supportType() {
        return ConditionTypeEnum.input;
    }

    @Override
    public String tagName() {
        return "input";
    }

    @Override
    public boolean isSupport(String typeStr) {
        if(StringUtils.isEmpty(typeStr)){
            return true;
        }
        return super.isSupport(typeStr);
    }
}
