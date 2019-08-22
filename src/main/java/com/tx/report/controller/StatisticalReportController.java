package com.tx.report.controller;

import com.tx.report.ExcelView;
import com.tx.report.component.reportRenderComponent.impl.AbstractReportRenderHandler;
import com.tx.report.context.StatisticalContext;
import com.tx.report.mapping.ReportStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by SEELE on 2016/6/27.
 */
@Controller
@RequestMapping("statisticalReport")
public class StatisticalReportController {
    protected Logger logger = LoggerFactory
            .getLogger(StatisticalReportController.class);


    @RequestMapping("reloadAllReport/{reportCode}")
    public String reloadAllReport(HttpServletRequest request,
                                  @PathVariable("reportCode") String reportCode, ModelMap response) {
        StatisticalContext statisticalContext = StatisticalContext.getContext();

        try {
            statisticalContext.reRegisterResource();
        } catch (Exception e) {
            logger.error("重载报表失败", e);
        }
        return toViewReport(request, reportCode, response);
    }


    @RequestMapping("reload/{reportCode}")
    public String toViewAndReloadReport(HttpServletRequest request,
                                        @PathVariable("reportCode") String reportCode, ModelMap response) {

        StatisticalContext statisticalContext = StatisticalContext.getContext();

        try {
            statisticalContext.reloadReport(reportCode);
        } catch (Exception e) {
            logger.error("重载报表失败", e);
        }
        return toViewReport(request, reportCode, response);
    }

    @RequestMapping("toView/{reportCode}")
    public String toViewReport(HttpServletRequest request,
                               @PathVariable("reportCode") String reportCode, ModelMap response) {
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportTypeHandler = statisticalContext
                .getReportRenderHandler(reportCode);

        response.put("conditionMap",
                reportStatement.getConditionMap().getItems());
        response.put("viewMap", reportStatement.getViewMap().getItems());
        response.put("paramMap", getParameterMap(request));
        response.put("reportCode", reportCode);
        abstractReportTypeHandler.initPageParams(response, reportStatement);

        String res = "/" + abstractReportTypeHandler.renderReportPage();
        return res;
    }

    @ResponseBody
    @RequestMapping("queryData/{reportCode}")
    public Object queryData(HttpServletRequest request,
                            @PathVariable("reportCode") String reportCode,
                            ModelMap response) {
        Map params = getParameterMap(request);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportRenderHandler = statisticalContext
                .getReportRenderHandler(reportCode);
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        Object object = abstractReportRenderHandler.queryPageData(params,
                reportStatement);
        return object;
    }

    /**
     * 参数转换
     *
     * @param request
     * @return
     */
    private Map<String, Object> getParameterMap(HttpServletRequest request) {
//        AssertUtils.notNull(request, "request is null.");
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> result = new HashMap<>();
        result.putAll(params);
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
//            if (entry.getValue() instanceof String[]) {
            String[] strs = entry.getValue();
            if (strs.length == 1) {
                result.put(entry.getKey(), strs[0]);
            }
//            }
        }
        return result;
    }

    /**
     * 数据统计
     *
     * @param request
     * @param reportCode
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("viewStatistical/{reportCode}")
    public Object viewStatistical(HttpServletRequest request,
                                  @PathVariable("reportCode") String reportCode, ModelMap response) {
        Map params = getParameterMap(request);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportRenderHandler = statisticalContext
                .getReportRenderHandler(reportCode);
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        return abstractReportRenderHandler.statisticalData(params,
                reportStatement);
    }

    @RequestMapping("download/report/{reportCode}")
    public ModelAndView downloadReport(HttpServletRequest request,
                                       @PathVariable("reportCode") String reportCode, ModelMap response, ModelMap model) {

        Map params = getParameterMap(request);
        StatisticalContext statisticalContext = StatisticalContext.getContext();
        AbstractReportRenderHandler abstractReportRenderHandler = statisticalContext
                .getReportRenderHandler(reportCode);
        ReportStatement reportStatement = ReportStatement
                .getReportStatement(reportCode);
        Object object = abstractReportRenderHandler.queryList(params,
                reportStatement);

        model.addAttribute("data", object);

        String filename = "报表文件";

        return new ModelAndView(new ExcelView("/download.xls", filename), model);

    }


//    @RequestMapping("downloadReportFile")
//    public void downloadReportFile(HttpServletRequest request,
//                                   HttpServletResponse response,
//                                   @RequestParam(value = "fileName", required = false) String fileName) {
//        response.reset(); // 非常重要
//        String filePath = request.getSession()
//                .getServletContext()
//                .getRealPath("/");
//        File file = new File(filePath + fileName);
//        if (!file.exists()) {
//            // 失败
//        }
//
//        response.setContentType("application/vnd.ms-excel");
//        String codedFileName = null;
//        try {
//            codedFileName = "" + file.getName();
//            // response.setContentType("application/vnd.ms-excel");
//            codedFileName = new String(codedFileName.getBytes(), "ISO8859-1");
//            response.setHeader("content-disposition",
//                    "attachment;filename=" + codedFileName);
//
//            BufferedInputStream br = new BufferedInputStream(
//                    new FileInputStream(file));
//            byte[] buf = new byte[1024];
//            int len = 0;
//            OutputStream out = response.getOutputStream();
//            while ((len = br.read(buf)) > 0) {
//                out.write(buf, 0, len);
//            }
//            out.flush();
//            br.close();
//            out.close();
//
//            // 清理已经下载的文件
//            file.delete();
//
//        } catch (Exception e) {
//            logger.error("文件生成失败", e);
//
//        }
//    }

}
