package starter.cloudstorage.src.main.java.com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;
import starter.cloudstorage.src.main.java.com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES (#{noteTitle},#{noteDescription},#{userId});")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle},noteDescription=#{noteDescription}, userId=#{userId} " +
            "WHERE noteId=#{noteId};")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId};")
    Integer deleteNote(Integer noteId);

    @Select("SELECT noteId FROM NOTES INNER JOIN USERS on NOTES.userId=USERS.userId " +
            "WHERE USERS.username=#{username};")
    Integer getNoteId(String username);

    @Select("SELECT * FROM NOTES INNER JOIN USERS on NOTES.userId=USERS.userId " +
            "WHERE USERS.username=#{username};")
    List<Note> getAllNotes(String username);
}
