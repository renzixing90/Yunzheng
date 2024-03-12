package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.SignInOut;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.SignInOutService;
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
 * 实现签到签退的接口
 */
@WebServlet("/signInOutServlet")
public class SignInOutServlet extends HttpServlet {

    private final SignInOutService signInOutService = new SignInOutService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取信息
         */
        SignInOut signInOut = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, SignInOut.class);

        /**
         * 获取session中的user数据
         */
        //HttpSession session = request.getSession();
        //User user = (User) session.getAttribute("user");
        User user = userService.verifyUser("2023004444");

        signInOut.setSignUserId(user.getUserId());

        if (signInOutService.judgeSatisfiedAndInsert(signInOut)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("签入成功", signInOut)));
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("签入失败", signInOut)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
