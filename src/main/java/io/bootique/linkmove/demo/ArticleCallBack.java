package io.bootique.linkmove.demo;

import com.nhl.link.move.connect.URIConnector;
import com.nhl.link.move.runtime.LmRuntimeBuilder;
import io.bootique.linkmove.LinkMoveBuilderCallback;

import java.net.URI;

public class ArticleCallBack implements LinkMoveBuilderCallback {
    @Override
    public void build(LmRuntimeBuilder builder) {
        {
            builder.withConnector("articleSourceConnector",
                    new URIConnector(URI.create("file:///path/article.csv")));
        }
    }
}
