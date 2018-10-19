package com.ecommerce.general.comment;

import java.util.List;

public interface CommentDao {

    public boolean updateComment(Comment comment);

    public boolean addComment(Comment comment);

    public boolean deleteComment(long id);

    public int getLastCommentId();
    
    public List<Comment> getAllComments(String sort);

    public List<Comment> getItemComments(long itemId, String sort);
    
    public Comment getCommentById(long id);

    public int getNumComments();

    public List<Comment> getLatestComments(int num);
    
}
