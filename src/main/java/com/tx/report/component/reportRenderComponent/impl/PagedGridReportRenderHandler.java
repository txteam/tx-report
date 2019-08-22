package com.tx.report.component.reportRenderComponent.impl;

import com.tx.report.component.reportRenderComponent.ReportTypeEnum;
import com.tx.report.mapping.ReportStatement;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/20.
 */
@Component("pagedGridReportRenderHandler")
public class PagedGridReportRenderHandler
        extends AbstractReportRenderHandler<Map<String, Object>> {

    public ReportTypeEnum supportType() {
        return ReportTypeEnum.PAGED_LIST_GRID;
    }

    public Object queryPageData(Map params,
            ReportStatement reportStatement) {
        int pageSize = 0;
        int pageIndex = 1;
        try {
             pageSize = params.get("pageSize") == null ? null
                    : Integer.parseInt((String) params.get("pageSize"));
             pageIndex = params.get("pageNumber") == null ? 1
                    : Integer.parseInt((String) params.get("pageNumber"));
        }catch (Exception e){

        }


        
        return super.queryPagedList(params,
                reportStatement,
                pageSize,
                pageIndex);
    }
    
}
