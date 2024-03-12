package com.ydsy.mapper;

import com.ydsy.pojo.SignInOut;

import java.util.List;

public interface SignInOutMapper {

    /**
     * 查找本人所有签到信息
     */
    List<SignInOut> selectAllBySignTypeAndSignUserId(SignInOut signInOut);

    /**
     * 存入数据
     * @param signInOut
     * @return
     */
    int insert(SignInOut signInOut);


}
