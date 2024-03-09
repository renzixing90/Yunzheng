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

@WebServlet("/selectStudentFromAccountByBigManagerServlet")
public class SelectStudentFromAccountByBigManagerServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取账号
         */
        User user = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, User.class);

        /**
         * 查看这个账号是否存在
         */
        User user01 = userService.selectByName(user.getName());

        if (user01 == null) {
            /**
             * 向前端返回错误
             */
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("没有此用户")));
        } else if (user01.getJobId() == 3) {
            /**
             * 向前端返回错误
             */
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("无法修改大总管的信息")));
        } else {
            /**
             * 向前端返回正确
             */
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("查询到此用户", user)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
