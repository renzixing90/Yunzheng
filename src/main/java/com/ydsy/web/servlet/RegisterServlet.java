package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 注册
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    private UserService service = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型为 JSON
        response.setContentType("application/json;charset=utf-8");

        /*// 使用 BufferedReader 读取请求体中的 JSON 数据
        BufferedReader reader = request.getReader();
        String jsonString = reader.readLine();
        // 使用 JSON 库解析 JSON 数据为 User 对象
        User user = JSON.parseObject(jsonString, User.class);*/

        User user = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, User.class);


        if (user != null) {

            // 调用 service 查询
            boolean registerUser = service.register(user);
            if (registerUser) {
                // 向用户返回成功消息
                response.setContentType("application/json;charset=utf-8");
                // 返回成功的 JSON 响应
                response.getWriter().write(JSON.toJSONString(BasicResultVO.success("注册成功")));

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            } else {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("注册失败，请重试")));
            }
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("未接收到数据，请重试")));
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
