// import org.sql2o.*;

// public class DB {
//  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_saalon", "moringa", "options");
// }
import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;
public class DB {
    private static URI dbUri;
    public static Sql2o sql2o;
    static {
        
        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://mgnpvglzbkzwan:214bb49b83eb24e529a6eabca04434bda16a52f2906f0d6e44f390cbe418cf30@ec2-50-19-127-115.compute-1.amazonaws.com:5432/d96mtljtr82d09@localhost:5432/hair_salon");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL")); 
            }
            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? null : dbUri.getUserInfo().split(":")[1];
            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
        } catch (URISyntaxException e ) {

        }
    }
}