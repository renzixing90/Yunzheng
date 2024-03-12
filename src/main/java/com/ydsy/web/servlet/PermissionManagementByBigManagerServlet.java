package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.ManagerRequest;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限管理的接口
 */
@WebServlet("/permissionManagementByBigManagerServlet")
public class PermissionManagementByBigManagerServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ManagerRequest managerRequest = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, ManagerRequest.class);

        User user = userService.selectByManagerRequest(managerRequest);

        if (user == null) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("输入此人信息错误或不存在")));
        } else {
            user.setJobId(managerRequest.getJobId());
            userService.updateAll(user);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("权限成功更改", user.getJobId())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
