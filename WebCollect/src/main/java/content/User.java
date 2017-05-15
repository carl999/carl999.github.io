package content;

/**
 * Created by little star on 2017/5/13.
 */
public class User implements Content {
    private String userName;
    private String portraitHttp;
    private String userHttp;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPortraitHttp() {
        return portraitHttp;
    }

    public void setPortraitHttp(String portraitHttp) {
        this.portraitHttp = portraitHttp;
    }

    public String getUserHttp() {
        return userHttp;
    }

    public void setUserHttp(String userHttp) {
        this.userHttp = userHttp;
    }
}
