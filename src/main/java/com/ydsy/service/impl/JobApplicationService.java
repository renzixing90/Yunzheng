package com.ydsy.service.impl;

import com.ydsy.mapper.JobApplicationMapper;
import com.ydsy.pojo.JobApplication;
import com.ydsy.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class JobApplicationService {

    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    /**
     * 检查是否此职位申请已申请过且未批准
     */
    public boolean judgeAndHandleJobApplication(JobApplication jobApplication) {
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取LeaveRequestMapper
        JobApplicationMapper mapper = sqlSession.getMapper(JobApplicationMapper.class);

        List<JobApplication> jobApplications = mapper.selectAllUntreatedApplications();

        for (JobApplication application : jobApplications) {
            if (application.getJobApplicationId() == jobApplication.getJobApplicationId()) {
                sqlSession.close();
                return false;
            }
        }

        mapper.insertNeoApplication(jobApplication);

        sqlSession.commit();
        sqlSession.close();

        return true;
    }

    /**
     * 查看所有未处理的职位申请
     */
    public List<JobApplication> jobApplications(){
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取LeaveRequestMapper
        JobApplicationMapper mapper = sqlSession.getMapper(JobApplicationMapper.class);

        List<JobApplication> jobApplications = mapper.selectAllUntreatedApplications();

        sqlSession.close();

        return jobApplications;
    }
}
