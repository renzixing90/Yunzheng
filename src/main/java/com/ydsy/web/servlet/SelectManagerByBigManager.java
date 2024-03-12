package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.JobEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 大总管查看方向管理员的接口
 */
@WebServlet("/selectManagerByBigManager")
public class SelectManagerByBigManager extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 从数据库获得所有管理员对象
         */
        List<User> users = userService.selectByJobId(JobEnum.JOB_MANAGER.getJobId());

        /**
         * 向前端返回所有的管理员对象
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("查询所有管理员成功", users)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
