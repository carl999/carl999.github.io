package content;

/**
 * Created by little star on 2017/5/12.
 */
public class CommentRecord implements Content {
    private String urlStr;//评论url
    private String movieId;
    private boolean commentSucceed;//评论界面是否成功获得
    private boolean hasError;//解析评论是否出错
    private int failCount;

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public boolean isCommentSucceed() {
        return commentSucceed;
    }

    public void setCommentSucceed(boolean commentSucceed) {
        this.commentSucceed = commentSucceed;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
