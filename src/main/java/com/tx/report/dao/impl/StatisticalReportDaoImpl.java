package com.tx.report.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tx.report.dao.StatisticalReportDao;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by SEELE on 2016/9/21.
 */
public class StatisticalReportDaoImpl<T> implements StatisticalReportDao<T> {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public List<T> queryList(String statementId, Map<String, Object> params) {
        return sqlSessionTemplate.<T>selectList(statementId, params);
    }

    @Override
    public T selectOne(String statementId, Map<String, Object> params) {
        return sqlSessionTemplate.<T>selectOne(statementId, params);
    }


    public PageInfo<T> queryPagedList(String statementId, Map<String, Object> params, Integer pageSize, Integer pageIndex) {
        PageHelper.startPage(pageIndex, pageSize);
        List<T> list = sqlSessionTemplate.<T>selectList(statementId, params);
        PageInfo<T> page = new PageInfo(list);
        return page;
    }

    @Override
    public int count(String statementId, Map<String, Object> params) {
        return sqlSessionTemplate.<Integer>selectOne(statementId,
                params);
    }
}
