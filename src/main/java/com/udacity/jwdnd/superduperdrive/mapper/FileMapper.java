package com.udacity.jwdnd.superduperdrive.mapper;

import com.udacity.jwdnd.superduperdrive.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid= #{userId}")
    File getFile(String fileName, Integer userId);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    List<String> getFileListings(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName} AND userid= #{userId}")
    void deleteFile(String fileName, Integer userId);
}
