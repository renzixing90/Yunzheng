package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 注册后的完善信息界面
 */
@WebServlet("/updateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = userService.selectMaxUser();

        // 如果用户对象不为null，则可以进行后续操作，比如显示用户信息并允许用户更新班级等
        if (user != null) {
            User updatedUser = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, User.class);
            user.setName(updatedUser.getName());
            user.setMajorClass(updatedUser.getMajorClass());
            user.setPersonalSignature(updatedUser.getPersonalSignature());
            user.setJobId(updatedUser.getJobId());
            user.setDirectionId(updatedUser.getDirectionId());
            user.setAwards(updatedUser.getAwards());
            userService.updateAll(user);

            User user1 = userService.verifyUser(user.getAccount());
            session.setAttribute("user", user1);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("完善信息成功")));
        } else {
            // 处理用户对象为null的情况，可能是session过期或其他错误
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("请重新登录")));
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
