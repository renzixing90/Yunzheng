package com.ydsy.mapper;

import com.ydsy.pojo.ManagerRequest;
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

    /**
     * 更新所有数据
     * @param user
     */
    void updateAll(User user);

    /**
     * 查看所有用户
     * @return
     */
    List<User> selectAll();

    /**
     * 重置密码更新为123456
     * @param userId
     */
    void resetPassword(int userId);

    /**
     * 查看某一个方向的所有学员
     * @param directionId
     * @return
     */
    List<User> selectAllStuByDirection(int directionId);

    /**
     * 通过name查找用户
     * @param name
     * @return
     */
    User selectByName(String name);

    /**
     * 查找id最大的(刚刚注册登入的)用户
     * @return
     */
    User selectMaxUser();

    /**
     * 查看某一职业的所有用户
     * @return
     */
    List<User> selectAllByJobId(int jobId);

    User selectByManagerRequest(ManagerRequest managerRequest);
}
