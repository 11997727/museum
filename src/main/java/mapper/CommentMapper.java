package mapper;

import entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/28
 */
public interface CommentMapper {
    public List<Comment> selectAllComments(@Param("museumId") Integer museumId,
                                           @Param("start") Integer start,
                                           @Param("length") Integer Length);
    public Integer selectCommentsCount(Integer museumId);
    public Integer insertOneComment(Comment comment);
}
