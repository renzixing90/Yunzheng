package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.JobApplication;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.JobApplicationService;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/JobRequestServlet")
public class JobRequestServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final JobApplicationService jobApplicationService = new JobApplicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 使用自定的工具类接收数据保存到对应的pojo类中
         */
        JobApplication jobApplication = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, JobApplication.class);
        System.out.println(jobApplication);

        /**
         * 获取session中的user数据
         */
        //HttpSession session = request.getSession();
        //User student = (User) session.getAttribute("user");
        User student = userService.verifyUser("2023004444");

        jobApplication.setJobApplicantId(student.getUserId());

        if (jobApplicationService.judgeAndHandleJobApplication(jobApplication)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("发送职务申请成功", jobApplication)));
        } else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("发送失败", jobApplication)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
