package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.Announcement;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.AnnouncementService;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/issueAnnouncementServlet")
public class IssueAnnouncementServlet extends HttpServlet {

    private final AnnouncementService announcementService = new AnnouncementService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Announcement announcement = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, Announcement.class);

        /**
         * 获取session中的user数据
         */
        //HttpSession session = request.getSession();
        //User manager = (User) session.getAttribute("user");
        User manager = userService.verifyUser("2023002222");

        announcement.setCreatorId(manager.getUserId());

        announcementService.insertAll(announcement);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("下发通知成功", announcement)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
