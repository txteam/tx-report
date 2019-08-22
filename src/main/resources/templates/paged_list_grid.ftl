<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${reportName}</title>

    <link rel="stylesheet" href="${request.contextPath}/js/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="${request.contextPath}/js/bootstrap-table/bootstrap-table.min.css" type="text/css">


    <style>


    </style>


    <script type="text/javascript" src="${request.contextPath}/js/jquery-1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/js/datePicker/WdatePicker.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript" src="${request.contextPath}/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="${request.contextPath}/js/bootstrap-table/bootstrap-table.min.js"></script>

    <script type="text/javascript" src="${request.contextPath}/js/tableExport/tableExport.min.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/js/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
    <script type="text/javascript"
            src="${request.contextPath}/js/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>

    <script>
        var currentPageStatisticalRecord = {};
        var totalStatisticalRecord = {};
        $(document).ready(function () {




           var $myTable= $("#myTable").bootstrapTable({
                url: '${request.contextPath}/statisticalReport/queryData/${reportCode}',
                method: 'get',
                toolbar: '#toobar',//工具列
                striped: true,//隔行换色
                cache: false,//禁用缓存
                pagination: true,//启动分页
                sidePagination: 'server',//分页方式  client | server
                pageNumber: 1,//初始化table时显示的页码
                pageSize: 10,//每页条目
                showFooter: false,//是否显示列脚
                showPaginationSwitch: true,//是否显示 数据条数选择框
                sortable: true,//排序
                search: true,//启用搜索
                showColumns: true,//是否显示 内容列下拉框
                showRefresh: true,//显示刷新按钮
                // idField: 'sn',//key值栏位
                clickToSelect: true,//点击选中checkbox
                singleSelect: true,//启用单行选中
                dataField: "list",
                // classes: "table table-bordered table-striped table-sm",
                showFooter: true,
                showExport: true,  //是否显示导出按钮
                buttonsAlign: "right",  //按钮位置
                exportTypes: ['excel'],  //导出文件类型
                exportDataType: "all", //basic', 'all', 'selected'.
                // Icons: 'glyphicon-export',
                exportOptions: {
                    // ignoreColumn: [0, 1],  //忽略某一列的索引
                    fileName: '${reportName}',  //文件名称设置
                    worksheetName: 'sheet1',  //表格工作区名称
                    tableName: '${reportName}',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],

                },
                // totalRows:2,
                // paginationVAlign:"top",
                showColumns: {},
                queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
                    return {
                        pageSize: params.limit, // 每页要显示的数据条数
                        pageNumber: params.offset / params.limit + 1, // 每页显示数据的开始行号
                        sort: params.sort, // 要排序的字段
                        sortOrder: params.order, // 排序规则
                        search: params.search, //搜索框内容
                    }
                },
                // queryParamsType:"pageSize",
                totalField: "count",
                onLoadSuccess: function (res) {

                },
                responseHandler: function (res) { //数据请求之后  responseHandler  -- 渲染数据 --- onLoadSuccess
                    currentPageStatisticalRecord = res.currentPageStatisticalRecord;
                    totalStatisticalRecord = res.totalStatisticalRecord;
                    return res;
                },

                // columns: [
                //     {
                //         field: 'AMOUNT',
                //         title: '系统代码',
                //         titleTooltip: 'young for you'
                //     },
                //     {
                //         field: 'SystemDesc',
                //         title: '系统名称'
                //     },
                //     {
                //         field: 'Isvalid',
                //         title: '是否有效'
                //     },
                //     {
                //         field: 'UUser',
                //         title: '更新人'
                //     },
                //     {
                //         field: 'UDate',
                //         title: '更新时间'
                //     }],
                onClickCell: function (field, value, row, $element) {
                    //alert(row.SystemDesc);
                }
            });


            $("#queryBtn").click(function () {

            });


        });


        function footerFormatter(rows, field) {
            return currentPageStatisticalRecord[field];
        }

        <#list viewMapItems as tmp>
        function footerFormatter_${tmp.column}(rows) {
            return footerFormatter(rows, "${tmp.column}");
        }

        </#list >

        function showNumber(value, row, index, field) {
            return index + 1;
        }

    </script>


</head>
<body>

<#--<div class="panel-body" >-->
<#--<div class="panel panel-default">-->
<#--<div class="panel-heading">-->
<#--查询条件-->
<#--</div>-->

<#--<div class="panel-body">-->
<#--<div style="">-->


<#--</div>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->

<form class="form-inline" id="queryForm">
    <#list  conditionItems as tmp >
        <div class="input-group" style="padding: 2px;">
        <div class="input-group-addon">${tmp.name}</div>
          <input type="text" class="form-control"  name="${tmp.id}" />
        </div>
    </#list>
    <button type="button" id="queryBtn" class="btn btn-success">查询</button>
</form>

<table id="myTable" data-striped="true">
    <thead>
    <tr>
        <th data-field="no" data-formatter="showNumber" data-footer-formatter="本页统计">序号</th>
        <#list viewMapItems as tmp>
            <th data-field="${tmp.column}" data-title-tooltip=""
                                           data-sortable="true" data-footer-formatter="footerFormatter_${tmp.column}"
                                           searchable="true"  >${tmp.name}</th>
        </#list>
    </tr>
    </thead>

    <#--<tfoot>-->
    <#--<tr>-->
    <#--&lt;#&ndash;<#list viewMapItems as tmp>&ndash;&gt;-->
    <#--&lt;#&ndash;<td  >&ndash;&gt;-->
    <#--&lt;#&ndash;<label data-total-field="${tmp.column}""></label>&ndash;&gt;-->
    <#--&lt;#&ndash;<#if tmp_index == 0>&ndash;&gt;-->
    <#--&lt;#&ndash;总 体 统  计&ndash;&gt;-->
    <#--&lt;#&ndash;</#if>&ndash;&gt;-->
    <#--&lt;#&ndash;</td>&ndash;&gt;-->
    <#--&lt;#&ndash;</#list>&ndash;&gt;-->
    <#--</tr>-->
    <#--</tfoot>-->
</table>


</body>
</html>