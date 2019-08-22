package com.tx.report.component.reportRenderComponent.impl;

import com.tx.report.component.reportRenderComponent.ReportTypeEnum;
import com.tx.report.mapping.ReportStatement;
import com.tx.report.model.ChartInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/20.
 */
@Component("echartReportRenderHandler")
public class EchartReportRenderHandler
        extends AbstractReportRenderHandler<ChartInfo> {

    @Override
    protected ReportTypeEnum supportType() {
        return ReportTypeEnum.ECHART;
    }


    public Object queryPageData(Map params,
            ReportStatement reportStatement) {
        return super.queryList(params, reportStatement);
    }

    public Object exeSqlStatement(){
        return "queryChartList";
    }
    
}
