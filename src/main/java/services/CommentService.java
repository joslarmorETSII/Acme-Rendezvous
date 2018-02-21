package services;

import domain.Administrator;
import domain.Comment;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.CommentRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class CommentService {

    // Managed Repository -----------------------------------------------------

    @Autowired
    private CommentRepository commentRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    // Constructors -----------------------------------------------------------

    public CommentService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Comment create(){

        Comment result;
        User user;
        Collection<Comment> childrenComments;

        user = this.userService.findByPrincipal();
        childrenComments = new ArrayList<Comment>();

        result = new Comment();

        result.setUser(user);
        result.setMoment(new Date(System.currentTimeMillis() - 1000));
        result.setChildrenComments(childrenComments);

        user.getComments().add(result);

        return result;

    }

    public Comment save(Comment comment){

        Assert.notNull(comment);
        checkByPrincipal(comment);
        Comment res = comment;
        Comment parentComment = comment.getParentComment();

        res.setMoment(new Date(System.currentTimeMillis()-1000));

        if(comment.getId() == 0){
            this.commentRepository.save(comment);
        }else{
            parentComment.getChildrenComments().add(comment);
            this.commentRepository.save(parentComment);
            this.commentRepository.save(res);
        }

        return res;
    }

    /*public void delete(Comment comment){

        Assert.notNull(comment);
        checkByPrincipal(comment);

        if(comment.getChildrenComments() != null){
            deleteComments(comment);
            this.commentRepository.delete(comment);
        }else if(comment.getParentComment() != null){
            Comment saved = comment.getParentComment();
            saved.getChildrenComments().remove(comment);
            this.commentRepository.save(saved);
            this.commentRepository.delete(comment);
        }else if(comment.getParentComment() == null){
            this.commentRepository.delete(comment);
        }
    }*/

    public void delete(Comment comment){
        Assert.notNull(comment);
        checkByPrincipal(comment);

        if(comment.getChildrenComments().size() == 1) {
            this.commentRepository.delete(comment.getChildrenComments().iterator().next());
            this.commentRepository.delete(comment);

        }else if(comment.getChildrenComments().size() > 1){
            for(Comment c : comment.getChildrenComments()){
                if(c.getChildrenComments().size() != 0) {
                    c.setParentComment(null);
                    this.commentRepository.save(c);
                    this.commentRepository.delete(c.getChildrenComments());
                }
                c.setParentComment(null);
                this.commentRepository.save(c);
            }
            commentRepository.delete(comment.getChildrenComments());
            this.commentRepository.delete(comment);

        } else if(comment.getChildrenComments().size() == 0) {
            this.commentRepository.delete(comment);
        }

    }


    public Comment findOne(int commentId){
        Assert.notNull(commentId);
        return this.commentRepository.findOne(commentId);
    }

    public Comment findOneToEdit(int commentId){
        Assert.notNull(commentId);
        Comment comment = this.commentRepository.findOne(commentId);
        checkByPrincipal(comment);
        return this.commentRepository.findOne(commentId);
    }

    public Collection<Comment> findAll(){
        return this.commentRepository.findAll();
    }
    // Other business methods -------------------------------------------------

    public void checkByPrincipal(final Comment comment) {

        User user = this.userService.findByPrincipal();
        Administrator administrator = this.administratorService.findByPrincipal();
        if(user==null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            Assert.isTrue(authority.equals("ADMINISTRATOR"));
        }else {
            Assert.isTrue(comment.getUser().equals(user));
        }
    }

    /*public void deleteComments(Comment comment) {
        Collection<Comment> comments = comment.getChildrenComments();
        for (Comment c : comments) {
            c.setParentComment(null);
            c.setRendezvous(null);
            c.setUser(null);
            comments.remove(c);
           // this.commentRepository.save(comment);
        }
    }*/
}
