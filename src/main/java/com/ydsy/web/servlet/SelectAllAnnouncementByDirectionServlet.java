package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.Announcement;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.AnnouncementService;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllAnnouncementByDirectionServlet")
public class SelectAllAnnouncementByDirectionServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final AnnouncementService announcementService = new AnnouncementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*HttpSession session = request.getSession();
        User student = (User) session.getAttribute("user");*/
        User student = userService.verifyUser("2023004444");

        List<Announcement> announcements = announcementService.selectByLocalDirection(student);

        /**
         * 向前端返回成功码和本方向的通知
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("查看本方向下发的通知成功", announcements)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
