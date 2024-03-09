package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.LeaveRequest;
import com.ydsy.pojo.Participation;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.LeaveRequestService;
import com.ydsy.service.impl.ParticipationService;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/approveLeaveRequestServlet")
public class ApproveLeaveRequestServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final LeaveRequestService leaveRequestService = new LeaveRequestService();
    private final ParticipationService participationService = new ParticipationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 获取session中的user数据
         */
        //HttpSession session = request.getSession();
        //User manager = (User) session.getAttribute("user");

        User manager = userService.verifyUser("2023002222");

        /**
         * 将审批数据存储在pojo中
         */
        LeaveRequest leaveRequestFromReq = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, LeaveRequest.class);

        int approveId = manager.getUserId();

        int approveStatus = leaveRequestFromReq.getApproveStatus();
        LeaveRequest leaveRequest = leaveRequestService.selectByLeaveRequestId(leaveRequestFromReq.getLeaveRequestId());
        leaveRequest.setApproveStatus(approveStatus);
        leaveRequest.setApproveId(approveId);

        /**
         * 将pojo中的审批数据更新到数据库中
         */
        leaveRequestService.UpdateLeaveRequestApproval(leaveRequest);

        /**
         * 获取并更新参会情况
         */
        Participation participation = participationService.selectByMeetingIdAndParticipantId
            (leaveRequest.getLeaveRequestMeeting(), leaveRequest.getApplicantId());
        if (approveStatus == 1)         // 批准
        {
            participation.setLeaveStatus("Approved");
        } else if (approveStatus == 0)            // 不批准
        {
            participation.setLeaveStatus("Rejected");
        }
        participationService.updateLeaveStatus(participation);

        /**
         * 向前端返回成功码和审批后的假条数据
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("审批成功", leaveRequest)));

        /**
         * 重定向回查询总假条页面
         */
        //response.sendRedirect(request.getContextPath() + "/selectAllLeaveRequestServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}