package exception;

/**
 * Created by little star on 2017/5/8.
 */
public class MovieHandException extends Exception {
    private String errorParseAttr;
    public MovieHandException(String errorParseAttr)
    {
        this.errorParseAttr=errorParseAttr;
    }
    public String getErrorParseAttr() {
        return errorParseAttr;
    }

    @Override
    public void printStackTrace() {
        System.out.println(errorParseAttr);
    }
}
