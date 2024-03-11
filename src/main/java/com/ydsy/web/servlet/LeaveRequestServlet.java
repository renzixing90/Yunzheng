package com.ydsy.web.servlet;

import com.alibaba.fastjson.JSON;
import com.ydsy.pojo.LeaveRequest;
import com.ydsy.pojo.Meeting;
import com.ydsy.pojo.Participation;
import com.ydsy.pojo.User;
import com.ydsy.service.impl.LeaveRequestService;
import com.ydsy.service.impl.MeetingService;
import com.ydsy.service.impl.ParticipationService;
import com.ydsy.service.impl.UserService;
import com.ydsy.util.BasicResultVO;
import com.ydsy.util.PojoReceiveRequestDataUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学员发送请假申请
 */
@WebServlet("/leaveRequestServlet")
public class LeaveRequestServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final MeetingService meetingService = new MeetingService();
    private final LeaveRequestService leaveRequestService = new LeaveRequestService();
    private final ParticipationService participationService = new ParticipationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * 使用自定的工具类接收数据保存到对应的pojo类中
         */
        LeaveRequest leaveRequest = PojoReceiveRequestDataUtil.pojoReceiveRequestDataUtil(request, LeaveRequest.class);
        System.out.println(leaveRequest);

        /**
         * 获取session中的user数据
         */
        //HttpSession session = request.getSession();
        //User student = (User) session.getAttribute("user");
        User student = userService.verifyUser("2023004444");

        /**
         * 假条中的申请人数据存储
         */
        leaveRequest.setApplicantId(student.getUserId());

        int leaveRequestMeeting = leaveRequest.getLeaveRequestMeeting();
        String leaveRequestReason = leaveRequest.getLeaveRequestReason();

        /**
         * 判断会议id存不存在
         */

        int meetingSelect = 0;
        List<Meeting> meetings = meetingService.selectAll();
        for (Meeting meeting : meetings) {
            if (meeting.getMeetingId() == leaveRequestMeeting) {
                meetingSelect = 1;
                break;
            }
        }
        if (meetingSelect == 0) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("会议不存在", -1)));
            return;
        }

        /**
         * 判断此假条是否已经申请过
         */
        Participation participation = participationService.selectByMeetingIdAndParticipantId(leaveRequestMeeting, student.getUserId());
        if (participation != null) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(BasicResultVO.success("你已发送过此会议的请假申请", participation)));
            //response.sendRedirect(request.getRequestURI());
            return;
        }

        /**
         * 将未审批假条数据存储到数据库中
         */
        leaveRequestService.addNeoLeaveRequest(leaveRequest);

        /**
         * 向前端返回成功码和未审批假条数据
         */
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(BasicResultVO.success("提交申请成功", leaveRequest)));

        /**
         * 重定向回此界面
         */
        //response.sendRedirect(request.getRequestURI());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
