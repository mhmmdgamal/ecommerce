package com.ecommerce.dao;

import com.ecommerce.bean.Comment;
import java.util.List;

public interface CommentDao {

    public boolean updateComment(Comment comment);

    public boolean addComment(Comment comment);

    public boolean deleteComment(long id);

    public List<Comment> getAllComments(String sort);

    public List<Comment> getItemComments(long itemId, String sort);
    
    public Comment getCommentById(long id);

    public int getNumComments();

    public List<Comment> getLatestComments(int num);
    
    public boolean approveComment(long id);
}
