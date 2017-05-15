package exception;

/**
 * Created by little star on 2017/5/9.
 */
public class CommentHandException extends Exception {
    private String errorAttr;
    public CommentHandException(String error)
    {
        this.errorAttr=error;
    }
    @Override
    public void printStackTrace() {
       System.out.println(errorAttr+"is error");
    }
}
