package com.comodin.fleet.api.servlet;

import com.comodin.fleet.core.bean.DepartmentBean;
import com.comodin.fleet.core.bean.RoleBean;
import com.comodin.fleet.service.IDepartmentService;
import com.comodin.fleet.service.IRoleService;
import com.comodin.fleet.util.FleetBasiUtil;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.List;


public class InitServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(InitServlet.class);

    private static WebApplicationContext wc;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        //初始化spring的工厂
        wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        BeanFactoryContext.setWc(wc);

        //初始化，initRole 将Role表中所有的，角色记录，全部写入到 Redis缓存越来。
        initDepartmentList();
        initRolesList();
    }

    private void initRolesList() {

        IRoleService roleService = (IRoleService) BeanFactoryContext.getService("roleService");
        //读取，所有角色数据，放入redis中缓存起来
        List<RoleBean> roleBeenList = roleService.selectAll();
        FleetBasiUtil.setRedisRoleList(roleBeenList);

        logger.info("init ===============>  all Role Data，To cache Redis total: " + roleBeenList.size());
    }

    private void initDepartmentList() {

        IDepartmentService departmentService = (IDepartmentService) BeanFactoryContext.getService("departmentService");
        //读取，所有角色数据，放入redis中缓存起来
        List<DepartmentBean> departmentBeanList = departmentService.selectAll();
        FleetBasiUtil.setRedisDepartmentBeanList(departmentBeanList);

        logger.info("init ===============>  all Department Data，To cache Redis total: " + departmentBeanList.size());
    }
}
