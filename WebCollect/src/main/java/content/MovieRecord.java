package content;

/**
 * Created by little star on 2017/5/10.
 */
public class MovieRecord implements Content {
    private String movieId;//电影Id
    private boolean movieSucceed;//电影信息 是否爬去成功
    private boolean commentSucceed;//电影评论url是否生成
    private boolean photosSucceed;//海报是否成功
    private int photosFailCount;
    private int movieFailCount;//电影信息失败次数
    private int commentAmount;//评论数目

    public int getPhotosFailCount() {
        return photosFailCount;
    }

    public void setPhotosFailCount(int photosFailCount) {
        this.photosFailCount = photosFailCount;
    }

    public boolean isPhotosSucceed() {
        return photosSucceed;
    }

    public void setPhotosSucceed(boolean photosSucceed) {
        this.photosSucceed = photosSucceed;
    }

    public int getMovieFailCount() {
        return movieFailCount;
    }

    public void setMovieFailCount(int movieFailCount) {
        this.movieFailCount = movieFailCount;
    }

    public boolean isCommentSucceed() {
        return commentSucceed;
    }

    public void setCommentSucceed(boolean commentSucceed) {
        this.commentSucceed = commentSucceed;
    }

    public boolean isMovieSucceed() {
        return movieSucceed;
    }

    public void setMovieSucceed(boolean movieSucceed) {
        this.movieSucceed = movieSucceed;
    }

    public int getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }



}
