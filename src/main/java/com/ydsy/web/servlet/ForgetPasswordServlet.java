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
import java.util.Objects;

@WebServlet("/forgetPasswordServlet")
public class ForgetPasswordServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user01 = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, User.class);
        String account = user01.getAccount();
        String password = user01.getPassword();
        String checkCode = user01.getCheckCode();

        // 调用 service 查询
        User user = service.verifyUser(account);
        if (user == null) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("用户信息不存在，请注册用户")));
            return;
        }
        String checkCodeGen = user.getCheckCode();
        if (Objects.equals(checkCode, checkCodeGen)) {

            // 存储新密码到数据库或缓存中，以便用户下次登录时验证
            int i = service.updatePassword(account, password);
            if (i != 0) {
                // 向用户返回成功消息
                response.setContentType("application/json;charset=utf-8");
                // 返回成功的 JSON 响应
                response.getWriter().write(JSON.toJSONString(BasicResultVO.success("修改密码成功")));
            } else {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("修改密码失败，请重试")));
            }

        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("重置密码错误，请重新输入")));
        }



    }
}
