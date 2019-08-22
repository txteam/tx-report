package com.tx.report.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by SEELE on 2016/9/21.
 */
public class StatisticalReportMultiDaoImpl<T> {

    @Resource
    private Map<String,SqlSessionTemplate> sqlSessionTemplate;

    private SqlSessionTemplate getSqlSessionTemplate(String datasourceId){
        return sqlSessionTemplate.get(datasourceId);
    }

    public List<T> queryList(String datasourceId, String statementId, Map<String, Object> params) {
        return getSqlSessionTemplate(datasourceId).<T>selectList(statementId, params);
    }

    public T selectOne(String datasourceId, String statementId, Map<String, Object> params) {
        return getSqlSessionTemplate(datasourceId).<T>selectOne(statementId, params);
    }


    public PageInfo<T> queryPagedList(String datasourceId, String statementId, Map<String, Object> params, Integer pageSize, Integer pageIndex) {
        PageHelper.startPage(pageIndex, pageSize);
        List<T> list = getSqlSessionTemplate(datasourceId).<T>selectList(statementId, params);
        PageInfo<T> page = new PageInfo(list);
        return page;
    }

    public int count(String datasourceId, String statementId, Map<String, Object> params) {
        return getSqlSessionTemplate(datasourceId).<Integer>selectOne(statementId,
                params);
    }
}
