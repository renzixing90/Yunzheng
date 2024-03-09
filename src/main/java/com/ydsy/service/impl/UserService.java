package com.ydsy.service.impl;

import com.ydsy.mapper.UserMapper;
import com.ydsy.pojo.User;
import com.ydsy.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserService {
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

    /**
     * 登陆方法
     *
     * @param account
     * @param password
     * @return
     */
    public User login(String account, String password) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.select(account, password);
        sqlSession.close();
        return user;
    }
    /**
     * 查询方法
     *
     * @param account
     * @return
     */
    public User verifyUser(String account) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByAccount(account);
        sqlSession.close();
        return user;
    }
    public boolean updatePassword(String account, String setPassword) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.updatePassword(account, setPassword);
        sqlSession.commit();
        return user != null;
    }

    /**
     * 注册方法
     *
     * @return
     */

    public boolean register(User user) {
        //2. 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3. 获取UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //4. 判断用户名是否存在
        User u = mapper.selectByAccount(user.getAccount());
        User s = mapper.selectByEmail(user.getEmail());
        boolean flag = false;
        if (u == null) {
            if (s == null) {
                // 用户名不存在，注册
                mapper.add(user);
                sqlSession.commit();
                flag = true;
            }
        }
        sqlSession.close();

        return flag;
    }

    /**
     * 充值密码
     *
     * @param userId
     */
    public void resetPassword(int userId) {
        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        mapper.resetPassword(userId);

        sqlSession.commit();
        sqlSession.close();
    }

    public List<User> selectAllStuByDirection(int directionId) {
        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> students = mapper.selectAllStuByDirection(directionId);

        sqlSession.close();

        return students;
    }

    public User selectByName(String name) {
        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.selectByName(name);

        sqlSession.close();

        return user;
    }
    public void updateAll(User user) {
        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        mapper.updateAll(user);

        sqlSession.commit();
        sqlSession.close();

    }
    public List<User> selectAll() {
        // 获取SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // UserMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.selectAll();

        sqlSession.close();

        return users;
    }
}
