
package com.socialmedia.service;

import com.socialmedia.dao.CommentDao;
import com.socialmedia.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    
    private CommentDao commentDao;
    
    @Autowired
    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    
    public Comment getCommentById(Long id) {
        return commentDao.findById(id).orElse(null);
    }
    
    public Comment createComment(Comment comment) {
        return commentDao.save(comment);
    }
    
    public Comment updateComment(Long id, Comment comment) {
        comment.setId(id);
        return commentDao.save(comment);
    }
    
    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }
    
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentDao.findByPostId(postId);
    }
}
