package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.JobApplication;
import com.ydsy.service.impl.JobApplicationService;
import com.ydsy.util.BasicResultVO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllJobApplicationServlet")
public class SelectAllJobApplicationServlet extends HttpServlet {

    private final JobApplicationService jobApplicationService = new JobApplicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取所有职务申请
         */
        List<JobApplication> jobApplications = jobApplicationService.jobApplications();

        /**
         * 向前端返回成功码和未审批的所有职务申请数据
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("查看所有职务申请成功", jobApplications)));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
