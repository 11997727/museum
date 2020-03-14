package service;

import entity.Comment;
import mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by teacher ZHANG on 2020/2/28
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getAllComments(Integer museumId, Integer start, Integer Length) {
        return commentMapper.selectAllComments(museumId, start, Length);
    }

    @Override
    public Integer getCommentsCount(Integer museumId) {
        return commentMapper.selectCommentsCount(museumId);
    }

    @Override
    public Integer addOneComment(Comment comment) {
        return commentMapper.insertOneComment(comment);
    }
}
