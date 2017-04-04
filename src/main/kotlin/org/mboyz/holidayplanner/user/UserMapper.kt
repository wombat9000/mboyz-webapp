package org.mboyz.holidayplanner.user

import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper {

    @Select("SELECT id as id FROM \"User\" WHERE id = #{id}")
    @Results(value = *arrayOf(
            Result(property = "id", column = "id")
    ))
    fun findById(@Param("id") id: Int): User

    @Insert("INSERT INTO \"User\" (firstname, lastname, emailaddress, passwordhash) " +
            "VALUES (#{firstName}, #{lastName}, #{email}, #{pwHash})")
    fun insert(unpersistedUser: UnpersistedUser)
}