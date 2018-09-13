/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.dao;

import com.ecommerce.bean.Comment;
import java.util.List;

public interface CommentDao {

    public boolean updateComment(Comment comment);

    public boolean addComment(Comment comment);

    public boolean deleteComment(long id);

    public List<Comment> getAllComments(String sort);

    public Comment getCommentById(long id);

    public int getNumComments();

    public List<Comment> getLatestComments(int num);
    
    public boolean approveComment(long id);
}
