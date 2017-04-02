package org.mboyz.holidayplanner.user

import org.apache.ibatis.annotations.*
import org.mboyz.holidayplanner.user.User

@Mapper
interface UserMapper {

    @Select("SELECT id as id FROM users WHERE id = #{id}")
    @Results(value = *arrayOf(
            Result(property = "id", column = "id")
    ))
    fun findById(@Param("id") id: Int): User

    @Insert("INSERT INTO users (id) VALUES (#{id})")
    fun insert(user: User)

}