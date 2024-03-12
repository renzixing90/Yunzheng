package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.SignInOut;
import com.ydsy.pojo.SignRespond;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.SignInOutService;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 查看签到签退天数的接口
 */
@WebServlet("/selectAllSignSumInThisMonthServlet")
public class SelectAllSignSumInThisMonthServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final SignInOutService signInOutService = new SignInOutService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取session中的user数据
         */
        //HttpSession session = request.getSession();
        //User user = (User) session.getAttribute("user");
        User user = userService.verifyUser("2023004444");

        SignInOut signInOut = new SignInOut();
        signInOut.setSignUserId(user.getUserId());

        /**
         * 获取签到和签退次数
         */
        signInOut.setSignType(1);

        int signInSum = signInOutService.SelectAllSignInThisMonth(signInOut);

        signInOut.setSignType(2);

        int signOutSum = signInOutService.SelectAllSignInThisMonth(signInOut);

        SignRespond signRespond = new SignRespond(user.getName(), signInSum, signOutSum);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("获取成功", signRespond)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
