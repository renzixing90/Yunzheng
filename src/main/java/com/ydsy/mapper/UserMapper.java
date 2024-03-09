package com.ydsy.mapper;

import com.ydsy.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    /**
     * 根据用户名和密码查询用户对象
     *
     * @param account
     * @param password
     * @return
     */
    @Select("select * from management.users where account = #{account} and password = #{password}")
    User select(@Param("account") String account, @Param("password") String password);

    /**
     * 根据用户名查询用户对象
     *
     * @param account
     * @return
     */

    User selectByAccount(String account);

    /**
     * 根据邮箱查询用户对象
     *
     * @param email
     * @return
     */
    @Select("select * from management.users where email= #{email}")
    User selectByEmail(String email);

    /**
     * 添加用户
     *
     * @param user
     */
    @Insert("insert into management.users (account, password, email, name, awards, personal_signature, student_id, major_class, stage, check_code)" +
        " values(#{account},#{password},#{email},null,null,null,null,null,null,#{checkCode})")
    void add(User user);

    @Select("select * from management.users where user_id = #{userId}")
    User selectByUserId(int userId);

    @Update("update management.users  set password = #{password}  WHERE account = #{account};")
    int updatePassword(@Param("account") String account, @Param("password") String password);

    void updateAll(User user);

    List<User> selectAll();

    void resetPassword(int userId);

    List<User> selectAllStuByDirection(int directionId);

    User selectByName(String name);

    User selectMaxUser();

}
