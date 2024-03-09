package com.ydsy.mapper;

import com.ydsy.pojo.JobApplication;

import java.util.List;

public interface JobApplicationMapper {

    /**
     * 获得所有未处理的职位申请
     */
    List<JobApplication> selectAllUntreatedApplications();

    /**
     * 添加未处理申请到数据库
     */
    void insertNeoApplication(JobApplication jobApplication);

}
