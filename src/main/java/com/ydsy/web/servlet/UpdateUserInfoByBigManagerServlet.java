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
import java.io.IOException;

/**
 * 大总管修改学生信息
 */
@WebServlet("/updateUserInfoByBigManagerServlet")
public class UpdateUserInfoByBigManagerServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取修改后的用户
         */
        User updatedUser = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, User.class);

        /**
         * 更新用户
         */
        userService.updateAll(updatedUser);

        /**
         * 向前端返回更新后的用户数据
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("更新用户个人信息成功", updatedUser)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
