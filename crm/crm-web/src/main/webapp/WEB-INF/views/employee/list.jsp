<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM | 账号管理</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/tree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="home"/>
    </jsp:include>

    <!-- =============================================== -->


    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-2">
                    <div class="box">
                        <div class="box-body">
                            <button class="btn btn-default" id="addDept">添加部门</button>
                            <ul id="ztree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-10">
                    <!-- Default box -->
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">员工管理</h3>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool"  title="Collapse">
                                    <i class="fa fa-plus"></i> 添加员工</button>
                            </div>
                        </div>
                        <div class="box-body">
                            <table class="table" id="dataTable">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>姓名</th>
                                        <th>部门</th>
                                        <th>手机</th>
                                        <th>#</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.box -->
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/tree/js/jquery.ztree.all.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/datatables/jquery.dataTables.js"></script>
<script>
    $(function(){

        $("#dataTable").DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": "/employee/load.json",
            "lengthChange": false,
            "pageLength": 25,
            "columns":[
                {"data":"id"},
                {"data":"userName"},
                {"data":function(row){
                    return "XX";
                }},
                {"data":"mobile"},
                {"data":function(row){
                    return "<a href=''>禁用</a>";
                }}
            ],
            "columnDefs": [
                {
                    "targets": [2,3,4],
                    "orderable": false
                },
                {
                    "targets": [0],
                    "visible": false
                }
            ],
            language:{
                "search":"账号:",
                "info": "显示从 _START_ 到 _END_ 条数据，共 _TOTAL_ 条",
                "infoEmpty":"没有任何数据",
                "emptyTable":"暂无数据",
                "processing":"加载中...",
                "paginate": {
                    "first":      "首页",
                    "last":       "末页",
                    "next":       "上一页",
                    "previous":   "下一页"
                }
            }
        });

















        //左侧树
        $("#addDept").click(function () {
           layer.prompt({title:"请输入部门名称"},function(text,index){
               layer.close(index);
               $.post("/employee/dept/new",{"deptName":text}).done(function(data) {
                   if(data.state == "success") {
                       layer.msg("添加部门成功");
                       //刷新树
                       var treeObj = $.fn.zTree.getZTreeObj("ztree");
                       treeObj.reAsyncChildNodes(null, "refresh");
                   } else {
                       layer.msg(data.message);
                   }
               }).error(function () {
                   layer.msg("服务器忙，请稍后");
               });
           });
        });


        var setting = {
            data: {
                simpleData: {
                    enable: true
                },
                key:{
                    name:"deptName"
                }
            },
            async:{
                enable:true,
                url:"/employee/dept.json",
                type:"get",
                dataFilter:ajaxDataFilter
            },
            callback:{
                onClick:function(event,treeId,treeNode,clickFlag){
                    alert(treeNode.id + treeNode.name + treeNode.pId);
                }
            }
        };

        function ajaxDataFilter(treeId, parentNode, responseData) {
            if (responseData) {
                for(var i =0; i < responseData.length; i++) {
                    if(responseData[i].id == 1) {
                        responseData[i].open = true;
                        break;
                    }
                }
            }
            return responseData;
        }
        /*var zNodes =[
            { id:1, pId:0, name:"凯盛软件", open:true},
            { id:11, pId:1, name:"开发部"},
            { id:111111, pId:11, name:"华北开发部"},
            { id:111, pId:1, name:"销售部"},
            { id:112, pId:1, name:"经理办公室"}
        ];*/
        $.fn.zTree.init($("#ztree"), setting);
    });
</script>
</body>
</html>
