package content;




public class Comment implements Content {
    private String commentId;//评论ID
    private String userName;//用户名称
    private int score;//评分
    private String startDate;//评论日期
    private String content;//内容
    private int praise=0;//点赞数目
    private String movieId;//评论的电影Id

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public boolean hasNullAttr()
    {
        return commentId==null||movieId==null|| userName ==null|| startDate ==null||content==null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

}
