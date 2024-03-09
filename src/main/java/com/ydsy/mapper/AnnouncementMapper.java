package com.ydsy.mapper;

import com.ydsy.pojo.Announcement;
import com.ydsy.pojo.User;

import java.util.List;

public interface AnnouncementMapper {

    /**
     * 存入公告通知
     */
    void insertAll(Announcement announcement);

    /**
     * 查询本方向的所有通知
     * @param student
     * @return
     */
    List<Announcement> selectByLocalDirection(User student);
}
