package service;

import entity.Comment;

import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/28
 */
public interface CommentService {
    public List<Comment> getAllComments(Integer museumId, Integer start, Integer Length);
    public Integer getCommentsCount(Integer museumId);
    public Integer addOneComment(Comment comment);
}
