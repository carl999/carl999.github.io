package content;

/**
 * Created by little star on 2017/5/6.
 */
public class Movie implements Content {
    private String movieId;//电影Id
    private String movieName;//电影名称
    private String director;//导演
    private String editors;//编剧们
    private String actors;//主演们
    private String tags;//标签们
    private float score;//电影评分
    private String country;//出品国家
    private String language;//语言
    private int duration;//时长
    private String summary;//简介
    private String imdb;//imdb号
    private String httpImage;//海报图片
    private int people;//评论的人数
    private String fiveStar;
    private String fourStar;
    private String threeStar;
    private String twoStar;
    private String oneStar;
    private String releaseTime;//上映日期


    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getFiveStar() {
        return fiveStar;
    }

    public void setFiveStar(String fiveStar) {
        this.fiveStar = fiveStar;
    }

    public String getFourStar() {
        return fourStar;
    }

    public void setFourStar(String fourStar) {
        this.fourStar = fourStar;
    }

    public String getThreeStar() {
        return threeStar;
    }

    public void setThreeStar(String threeStar) {
        this.threeStar = threeStar;
    }

    public String getTwoStar() {
        return twoStar;
    }

    public void setTwoStar(String twoStar) {
        this.twoStar = twoStar;
    }

    public String getOneStar() {
        return oneStar;
    }

    public void setOneStar(String oneStar) {
        this.oneStar = oneStar;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getHttpImage() {
        return httpImage;
    }

    public void setHttpImage(String httpImage) {
        this.httpImage = httpImage;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }






}
