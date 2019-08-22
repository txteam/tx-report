package com.tx.report.context;

import com.tx.report.component.reportRenderComponent.impl.AbstractReportRenderHandler;
import com.tx.report.exceptions.util.AssertUtils;
import org.springframework.ui.ModelMap;

/**
 * Created by SEELE on 2016/6/24.
 */
public class StatisticalContext extends StatisticalContextBuilder {
    private static StatisticalContext context;

    public static StatisticalContext getContext() {
        AssertUtils.notNull(context, "context is null.not init.");
        StatisticalContext res = context;
        return res;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        StatisticalContext.context = this;
    }


    public AbstractReportRenderHandler getReportRenderHandler(String type) {
        return  resolveReportRenderHandler(type);
    }



}
