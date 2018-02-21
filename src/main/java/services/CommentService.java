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
    private AdministratorService adminService;

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

        res.setMoment(new Date(System.currentTimeMillis()-1000));

        if(res.getParentComment() == null){
            this.commentRepository.save(comment);
        }else{
            res.getParentComment().getChildrenComments().add(comment);
            this.commentRepository.save(res);
        }

        return res;
    }

    public void delete(Comment comment){

        Assert.notNull(comment);
        checkByPrincipal(comment);

        if(!(comment.getChildrenComments().isEmpty())){
            this.commentRepository.delete(comment.getChildrenComments());
            this.commentRepository.delete(comment);
        } if(comment.getParentComment() != null){
            Comment saved = comment.getParentComment();
            saved.getChildrenComments().remove(comment);
            this.commentRepository.save(saved);
            this.commentRepository.delete(comment);
        } else{
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
        Administrator administrator = this.adminService.findByPrincipal();
        if(user==null) {
            Collection<Authority> authorities = administrator.getUserAccount().getAuthorities();
            String authority = authorities.toArray()[0].toString();
            Assert.isTrue(authority.equals("ADMIN"));
        }else {
            Assert.isTrue(comment.getUser().equals(user));
        }
    }

}
