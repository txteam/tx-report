<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${reportName}</title>

    <link rel="stylesheet" href="${request.contextPath}/js/bootstrap-3.3.7/css/bootstrap.min.css" type="text/css">
    <script type="text/javascript" src="${request.contextPath}/js/jquery-1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="${request.contextPath}/js/datePicker/WdatePicker.js" type="text/javascript"
            charset="utf-8"></script>

    <script type="text/javascript" src="${request.contextPath}/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>


    <#--
    <script type="text/javascript" src="${request.contextPath}/js/echarts/echarts-all.js"></script>
    --%>
    <%--
    <script type="text/javascript" src="${request.contextPath}/js/echarts/asset/js/esl/esl.js"></script>
    -->
    <script type="text/javascript" src="${request.contextPath}/js/echarts/echarts.js"></script>
    <#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/dark.js"></script>-->
    <#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/shine.js"></script>-->
    <#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/roma.js"></script>-->
    <#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/macarons.js"></script>-->
    <script type="text/javascript" src="${request.contextPath}/js/echarts/theme/infographic.js"></script>
    <#--<script type="text/javascript" src="${request.contextPath}/js/echarts/theme/vintage.js"></script>-->

    <script type="text/javascript" src="${request.contextPath}/js/echarts/EchartTools.js"></script>

</head>
<body>

<#--<form id="queryForm" class="form-inline" method="post"  action="${request.contextPath}/statisticalReport/toView/${reportCode}">-->

<#--</form>-->


<div class="container">
    <form id="queryForm" class="form-inline" method="post"  action="${request.contextPath}/statisticalReport/toView/${reportCode}">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <#list  conditionItems as tmp >
                    <div class="input-group" style="padding: 2px;">
                    <div class="input-group-addon">${tmp.name}</div>
                <input type="text" class="form-control"  name="${tmp.id}" />
                    </div>
                </#list>
                    <button type="submit" id="queryBtn" class="btn btn-success">查询</button>
                <#--<#assign conditionItemPage_index = 0/>-->
                <#--<#if  (conditionItemPage?size>0) >-->
                <#--<div class="form-group">-->
                    <#--<#list  conditionItemPage as key,value >-->
                    <#--<div class="col">-->
                        <#--<label class="sr-only" for="inlineFormInputGroup"></label>-->
                    <#--<div class="input-group mb-2">-->
                        <#--<div class="input-group-prepend">-->
                            <#--<div class="input-group-text">${key}</div>-->
                        <#--</div>-->
                        <#--${value}-->
                    <#--</div>-->

                    <#--</div>-->
                    <#--<#if (conditionItemPage_index % conditionMap.cols?number == (conditionMap.cols?number-1))>-->
                    <#--<div class="w-100"></div>-->
                    <#--</#if>-->
                    <#--<#assign conditionItemPage_index = conditionItemPage_index+1/>-->
                    <#--</#list>-->
                    <#--<div class="col">-->
                        <#--<button type="button" onclick="renderPage();" class="btn btn-outline-primary ">查询</button>-->
                    <#--</div>-->
                    <#--</#if>-->
                <#--</div>-->
            </div>
            <div class="card-body" style="height: 600px;">
                <div id="echart" style="width: 100%;height: 500px;"></div>
            </div>
        </div>

    </form>
</div>


<script type="text/javascript">
    $(document).ready(function(){
        $("input").addClass("form-control")
    });


    renderPage();

    function getParams() {
        var params = {};
    <#list conditionItems as item>
        params["${item.id}"] = $("#${item.id}").val();
    </#list>
        return params;
    }
    function renderPage() {
        // alert(JSON.stringify($("#queryForm").serialize()))

        $.post(
                '${request.contextPath}/statisticalReport/queryData/${reportCode}',

                getParams(),
                function (data) {
                    var echartData = new Array();
                    for (var o in data) {
                        var tempData = data[o];
                        var echartObj = {name: tempData.NAME, value: tempData.VALUE, group: tempData.GROUP};
                        echartData.push(echartObj);
                    }
                    var option = ECharts.ChartOptionTemplates.${reportSubType}(echartData, "${reportName}", true);
                    var container = $("#echart")[0];
                    opt = ECharts.ChartConfig(container, option);

                    ECharts.Charts.RenderChart(opt, "infographic");

                    //初始化页面的值
                    loadParamValue();
                }
        )

    }


    function loadParamValue() {
        var els;
    <#if (paramMap?? && paramMap ?size>0)>
        <#list paramMap as tempParam>
            els = document.getElementsByName('${tempParam.key}');
            for (inx in els) {
                $(els[inx]).val("${tempParam.value}");
            }
        </#list>
    </#if>
    }

</script>


</body>
</html>