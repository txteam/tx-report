package com.tx.report.service.impl;

import com.github.pagehelper.PageInfo;
import com.tx.report.dao.StatisticalReportDao;
import com.tx.report.mapping.ReportStatement;
import com.tx.report.mapping.ViewItem;
import com.tx.report.service.StatisticalReportService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2016/9/21.
 */

public class StatisticalReportServiceImpl
        implements StatisticalReportService {

    @Resource(name = "StatisticalReport.statisticalReportDao")
    private StatisticalReportDao statisticalReportDao;


    /**
     * 查询数据列表
     *
     * @param sqlMapId
     * @param reportCode
     * @param params     @return
     */
    @Override
    public List queryList(String sqlMapId, String reportCode, Map<String, Object> params) {
        String statementId = reportCode + "." + sqlMapId;
        return statisticalReportDao.queryList(statementId, params);
    }

//    @Override
//    public List queryList(String sqlMapId, String reportCode, Map<String, Object> params, Order... orders) {
//        String statementId = reportCode + "." + sqlMapId;
//        return statisticalReportDao.queryList(statementId, params, orders);
//    }

    @Override
    public List queryList(String statementId, Map<String, Object> params) {
        return statisticalReportDao.queryList(statementId, params);
    }

    /**
     * 查询分页数据
     *  @param sqlMapId
     * @param reportCode
     * @param params
     * @param pageSize
     * @param pageIndex  @return
     */
    @Override
    public PageInfo queryPagedList(String sqlMapId, String reportCode, Map<String, Object> params, Integer pageSize, Integer pageIndex) {
        String statementId = reportCode + "." + sqlMapId;

//        if(params!=null){
//            params.put("statisticalColumns",)
//
//            for (ViewItem viewItem : reportStatement.getViewMap().getItems()) {
//
//            }
//        }

        return statisticalReportDao.queryPagedList(statementId, params, pageSize, pageIndex);
    }

//    @Override
//    public PageInfo queryPagedList(String sqlMapId, String reportCode, Map<String, Object> params, int pageSize, int pageIndex, Order... orders) {
//        String statementId = reportCode + "." + sqlMapId;
//        return statisticalReportDao.queryPagedList(statementId, params, pageSize, pageIndex, orders);
//
//    }

    @Override
    public int count(String sqlMapId, String reportCode, Map<String, Object> params) {
        String statementId = reportCode + "." + sqlMapId + "Count";
        return statisticalReportDao.count(statementId, params);

    }

    @Override
    public Object statisticalData(ReportStatement reportStatement, Map params) {
        String statementId = reportStatement.getCode() + "." + reportStatement.getViewMap().getSqlMapperId() + "Statistical";
        params.put("statisticalColumn", generatorStatisticalColumn(reportStatement));

        List list = statisticalReportDao.queryList(statementId, params);
        if (list!=null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Object statisticalData(ReportStatement reportStatement, final  Map params, Integer pageSize, Integer pageNumber) {
        Map<String,Object> newParams = new HashMap<>();
        String statementId = reportStatement.getCode() + "." + reportStatement.getViewMap().getSqlMapperId() + "Statistical";

        newParams.putAll(params);
        if(pageSize!=null && pageSize>0) {
            newParams.put("statisticalColumn", generatorStatisticalColumn(reportStatement));
            int limitStart = (pageSize) * (pageNumber - 1);
            int limitSize = pageSize;

            newParams.put("limitStart", limitStart);
            newParams.put("limitSize", limitSize);
        }

        List list = statisticalReportDao.queryList(statementId, newParams);
        if (list!=null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }


    private String generatorStatisticalColumn(ReportStatement reportStatement) {
        StringBuffer statisticalSql = new StringBuffer();
        statisticalSql.append("count(1) as cnt , ");
        for (ViewItem viewItem : reportStatement.getViewMap().getItems()) {
            String statisticalType = viewItem.getStatisticalType();
            if (StringUtils.isEmpty(statisticalType)) {
                continue;
            }
            statisticalSql.append(statisticalType + "(")
                    .append(viewItem.getColumn())
                    .append(") as ")
                    .append(viewItem.getColumn());
            statisticalSql.append(" ,");
        }
        statisticalSql = new StringBuffer(
                statisticalSql.substring(0, statisticalSql.length() - 1));
        return statisticalSql.toString();
    }
}
