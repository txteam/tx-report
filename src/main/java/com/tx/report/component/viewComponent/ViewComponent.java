package com.tx.report.component.viewComponent;

import com.tx.report.mapping.BaseAttr;
import com.tx.report.mapping.ViewItem;

import java.util.Map;

/**
 * Created by SEELE on 2016/9/23.
 */
public interface ViewComponent {
    /**
     * 支持的类型
     * @return
     */
    String supportViewType();

    /**
     * 是否支持
     * @param typeStr
     * @return
     */
    boolean isSupport(String typeStr);


    Map<String, BaseAttr> rebuildNodeAttr(ViewItem viewItem);
}
