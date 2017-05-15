
import generator.Generator;
import mongoDB.MongoDB;

public class Runner {
public static void main(String[]args)
{
    MongoDB.linke();
    Generator generator=new Generator();
    generator.startMovieCrawler();
    generator.startCommentCrawler();
}

}
