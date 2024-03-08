package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/selectAllPersonInformationServlet")
public class SelectAllPersonInformationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取更新后的大总管数据
         */
        User updateBigManager = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, User.class);

        /**
         * 更新数据库
         */
        userService.updateAll(updateBigManager);

        /**
         * 更新session中的user数据
         */
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user", updateBigManager);

        /**
         * 向前端返回更新后的大总管数据
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("更新大总管个人信息成功", updateBigManager)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
