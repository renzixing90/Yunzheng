package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllStudentByManager")
public class SelectAllStudentByManager extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取session中的user数据
         */
        /*HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");*/
        User user = userService.verifyUser("2023002222");

        /**
         * 从数据库获得所有用户对象
         */
        List<User> students = userService.selectAllStuByDirection(user.getDirectionId());

        /**
         * 向前端返回所有的用户对象
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("查询所有本方向用户成功", students)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
