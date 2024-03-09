package com.ydsy.service.impl;

import com.ydsy.mapper.AnnouncementMapper;
import com.ydsy.pojo.Announcement;
import com.ydsy.pojo.User;
import com.ydsy.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class AnnouncementService {

    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    /**
     * 将会议通知存入数据库
     */
    public void insertAll(Announcement announcement) {
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // AnnouncementMapper
        AnnouncementMapper mapper = sqlSession.getMapper(AnnouncementMapper.class);

        mapper.insertAll(announcement);

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 查看本方向的通知信息
     */
    public List<Announcement> selectByLocalDirection(User student) {
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // AnnouncementMapper
        AnnouncementMapper mapper = sqlSession.getMapper(AnnouncementMapper.class);

        List<Announcement> announcements = mapper.selectByLocalDirection(student);

        sqlSession.close();

        return announcements;
    }
}
