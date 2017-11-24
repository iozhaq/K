<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- 搜索表单，不需要删除即可 -->
        <!--<form action="#" method="get" class="sidebar-form">
          <div class="input-group">
            <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                  <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                  </button>
                </span>
          </div>
        </form>-->
        <!-- /.search form -->
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="${param.menu == 'home' ? 'active' : ''}"><a href="../../documentation/index.html"><i
                    class="fa fa-home"></i> <span>首页</span></a></li>
            <li class="header">系统功能</li>
            <!-- 客户管理 -->
            <li class="treeview ${fn:startsWith(param.menu, 'customer_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-address-book-o"></i> <span>客户管理</span>
                    <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'customer_my' ? 'active' : ''}"><a href="/customer/my"><i class="fa fa-circle-o"></i> 我的客户</a></li>
                    <li class="${param.menu == 'customer_public' ? 'active' : ''}"><a href="/customer/public"><i class="fa fa-circle-o"></i> 公海客户</a></li>
                </ul>
            </li>
            <!-- 工作记录 -->
            <li class="treeview ${fn:startsWith(param.menu, 'sales_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-bars"></i> <span>销售机会</span>
                    <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'sales_my' ? 'active' : ''}"><a href="/sales/my"><i class="fa fa-circle-o"></i> 我的机会</a></li>
                </ul>
            </li>
            <!-- 待办事项 -->
            <li class="${param.menu == 'task' ? 'active' : ''}"><a href="/task"><i class="fa fa-calendar"></i> <span>待办事项</span></a></li>
            <!-- 统计报表 -->
            <li class="treeview ${fn:startsWith(param.menu, 'charts_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-pie-chart"></i> <span>统计报表</span>
                    <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'charts_demo' ? 'active' : ''}"><a href="/charts/demo"><i class="fa fa-circle-o"></i> 静态演示</a></li>
                    <li class="${param.menu == 'charts_customer' ? 'active' : ''}"><a href="/charts"><i class="fa fa-circle-o"></i> 客户统计</a></li>
                </ul>
            </li>


            <li class="${param.menu == 'disk' ? 'active' : ''}"><a href="/disk"><i class="fa fa-share-alt"></i> <span>公司网盘</span></a></li>
            <shiro:hasRole name="系统管理部">
                <li class="header">系统管理</li>
                <!-- 部门员工管理 -->
                <li class="${param.menu == 'employee' ? 'active' : ''}"><a href="/employee"><i class="fa fa-users"></i>
                    <span>员工管理</span></a>
                </li>
            </shiro:hasRole>
            <!--<li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
            <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>-->
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>