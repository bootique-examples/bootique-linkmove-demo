package io.bootique.linkmove.demo;

import com.nhl.link.move.connect.URIConnector;
import com.nhl.link.move.runtime.LmRuntimeBuilder;
import io.bootique.linkmove.LinkMoveBuilderCallback;

import java.net.URI;

public class ArticleCallBack implements LinkMoveBuilderCallback {

    @Override
    public void build(LmRuntimeBuilder builder) {
        //define source for articles
        //Note: set absolute URI to csv file instead of path placeholder as a temporal workaround
        builder.withConnector("articleSourceConnector",
                new URIConnector(URI.create("file:///path/article.csv")));
    }
}
