package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.DirectionApplication;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.DirectionApplicationService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户发送方向请求(功能不对接)
 */
@WebServlet("/directionRequestServlet")
public class DirectionRequestServlet extends HttpServlet {

    private final DirectionApplicationService directionApplicationService = new DirectionApplicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取session中的user数据
         */
        HttpSession session = request.getSession();
        User student = (User) session.getAttribute("user");

        /**
         * 判断学生是否已经加入方向
         */
        if (student.getDirectionId() != 0) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.fail("您已加入方向,不可重复加入")));
            response.sendRedirect(request.getRequestURI());
            return;
        }

        /**
         * 将方向申请存入pojo中
         */

        DirectionApplication directionApplication = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, DirectionApplication.class);
        directionApplication.setApplicantId(student.getUserId());

        /**
         * 将方向申请数据存储到数据库中
         */
        directionApplicationService.addNeoDirectionApplication(directionApplication);

        /**
         * 向前端返回成功码和未审批方向申请数据
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("提交申请成功", directionApplication)));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
