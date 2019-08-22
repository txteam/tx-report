package com.tx.report.directive;

import com.tx.report.mapping.ReportStatement;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ConditionDirective implements TemplateDirectiveModel {
    /**
     * 变量名称
     */
    private static final String VARIABLE_NAME = "conditions";

    private static final String REPORT_CODE = "reportCode";


    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        String reportCode = map.get(REPORT_CODE).toString();
        ReportStatement reportStatement = ReportStatement.getReportStatement(reportCode);

        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_28);
        TemplateModel tm = builder.build().wrap(reportStatement.getConditionMap());
        environment.setVariable(REPORT_CODE, tm);
        templateDirectiveBody.render(environment.getOut());
//        environment.getCurrentNamespace().put();
    }

}
