package com.ydsy.service.impl;

import com.ydsy.mapper.SignInOutMapper;
import com.ydsy.pojo.SignInOut;
import com.ydsy.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDate;
import java.util.List;

public class SignInOutService {

    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public int SelectAllSignInThisMonth(SignInOut signInOut) {
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // SignInOutMapper
        SignInOutMapper mapper = sqlSession.getMapper(SignInOutMapper.class);

        List<SignInOut> signInOuts = mapper.selectAllBySignTypeAndSignUserId(signInOut);
        int sum = 0;
        for (SignInOut sign : signInOuts) {
            if (sign.getSignDate().getMonthValue() == LocalDate.now().getMonthValue()) {
                sum++;
            }
        }
        sqlSession.close();

        return sum;
    }

    /**
     * 签到或签退
     *
     * @param signInOut
     * @return
     */
    public boolean judgeSatisfiedAndInsert(SignInOut signInOut) {
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // SignInOutMapper
        SignInOutMapper mapper = sqlSession.getMapper(SignInOutMapper.class);

        /**
         * 获取本人的签到或签退记录
         */
        List<SignInOut> list = mapper.selectAllBySignTypeAndSignUserId(signInOut);

        /**
         * 遍历检查是否已签过并查找签到或签退的数量
         */
        for (SignInOut sign : list) {
            if (sign.getSignDate().isEqual(LocalDate.now())) {
                sqlSession.close();
                return false;
            }
        }

        /**
         * 检查通过 存入数据
         */
        int insert = mapper.insert(signInOut);

        sqlSession.commit();
        sqlSession.close();

        if (insert != 0) {
            return true;
        } else {
            return false;
        }
    }

}
