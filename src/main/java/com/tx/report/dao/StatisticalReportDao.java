package com.tx.report.dao;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by SEELE on 2016/9/21.
 */
public interface StatisticalReportDao<T> {
    List<T> queryList(String statementId, Map<String, Object> params);

    T selectOne(String statementId, Map<String, Object> params);

    PageInfo<T> queryPagedList(String statementId, Map<String, Object> params,
                               Integer pageSize, Integer pageIndex);

    int count(String statementId, Map<String, Object> params);
}
