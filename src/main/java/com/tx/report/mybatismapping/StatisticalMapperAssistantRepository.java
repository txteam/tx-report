package com.tx.report.mybatismapping;

import com.tx.report.mapping.SqlMapperItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br/>
 *
 * @author XRX
 * @version [版本号, 2017/11/22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StatisticalMapperAssistantRepository {
    public final String countSuffix = "Count";
    public String statisticalSuffix = "Statistical";

    private static  String BASE_SCRIPT_FORMMATER =  "select * from ( %s ) B  \n" +
            "  where 1 = 1 \n"+
            " <if test=\"search !=null\">\n" +
            "<foreach collection=\"columns\"  item=\"tmp\" open=\" and (\" separator=\" or \" close=\")\" >\n" +
            "    ${tmp} like CONCAT('%%',#{search},'%%') \n" +
            " </foreach>\n" +
            "  </if>\n" +
            " <if test=\"sort !=null\">\n" +
            "   order by  ${sort} ${sortOrder}\n" +
            " </if>\n" ;



    private static String COUNT_SCRIPT_FORMATTER = "<script> SELECT COUNT(1) AS CNT FROM (%s) TB </script>";
    private static String STATISTICAL_SCRIPT_FORMATTER = "<script> SELECT ${statisticalColumn} FROM  ( \n %s " +
            " <if test=\"limitStart != null \"> \n" +
            " <![CDATA[ LIMIT #{limitStart} , #{limitSize} ]]> \n" +
            " </if> \n" +
            " ) TB " +

            " </script>";
    private static final String SCRIPT_FORMATTER = "<script> %s " +
//            " <if test=\"sort !=null\">\n" +
//            "   order by  ${sort} ${sortOrder}\n" +
//            " </if>\n" +
            " </script>";

    public static void main(String[] args) {
        System.out.println(SCRIPT_FORMATTER);
        String s = String.format(SCRIPT_FORMATTER,"ccc");
        System.out.println(s);
    }

    private Configuration configuration;

    private Map<String, StatisticalMapperAssistant> assistantMap = new HashMap<>();

    public StatisticalMapperAssistantRepository(Configuration configuration) {
        this.configuration = configuration;
    }

    public StatisticalMapperAssistant doBuilderAssistant(String namespace) {
        StatisticalMapperAssistant statisticalMapperAssistant = new StatisticalMapperAssistant(configuration, namespace);

        return statisticalMapperAssistant;
    }

    public void assistant(List<SqlMapperItem> srSqlItems) {
        for (SqlMapperItem temp : srSqlItems) {
            assistant(temp);
        }
    }

    public StatisticalMapperAssistant assistant(SqlMapperItem srSqlItem) {
        String namespace = srSqlItem.getNamespace();
        StatisticalMapperAssistant assistant = assistantMap.get(namespace);
        if (assistant == null) {
            assistant = doBuilderAssistant(namespace);
            assistantMap.put(namespace, assistant);
        }

        String baseSql = String.format(BASE_SCRIPT_FORMMATER, srSqlItem.getSqlScript());


        synchronized (assistantMap) {
            String scriptSql = String.format(SCRIPT_FORMATTER, baseSql);
            assistant = doBuilderAssistant(namespace);
            assistant.publishMapperStatement(srSqlItem.getSqlCommandType(),
                    srSqlItem.getId(),
                    scriptSql,
                    srSqlItem.getParameterType(),
                    srSqlItem.getReturnType());

            if (srSqlItem.isNeedStatisticalScript()) {
                String countSql = String.format(COUNT_SCRIPT_FORMATTER, baseSql);
                assistant.publishMapperStatement(srSqlItem.getSqlCommandType(),
                        srSqlItem.getId() +countSuffix,
                        countSql,
                        srSqlItem.getParameterType(),
                        Integer.class);

                String statistical = String.format(STATISTICAL_SCRIPT_FORMATTER,baseSql);
                assistant.publishMapperStatement(srSqlItem.getSqlCommandType(),
                        srSqlItem.getId() + statisticalSuffix,
                        statistical,
                        srSqlItem.getParameterType(),
                        srSqlItem.getReturnType());
            }

            assistantMap.put(namespace, assistant);
        }
        return assistant;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Map<String, StatisticalMapperAssistant> getAssistantMap() {
        return assistantMap;
    }

    public void setAssistantMap(Map<String, StatisticalMapperAssistant> assistantMap) {
        this.assistantMap = assistantMap;
    }
}
