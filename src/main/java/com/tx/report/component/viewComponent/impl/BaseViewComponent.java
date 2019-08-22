package com.tx.report.component.viewComponent.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by SEELE on 2016/9/23.
 */
@Component
public class BaseViewComponent extends AbstractViewComponent {
    @Override
    public String supportViewType() {
        return null;
    }

    @Override
    public boolean isSupport(String typeStr) {
        return StringUtils.isEmpty(typeStr);
    }
}
