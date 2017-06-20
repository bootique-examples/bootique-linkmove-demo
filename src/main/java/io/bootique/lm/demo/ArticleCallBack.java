package io.bootique.lm.demo;

import com.nhl.link.move.connect.StreamConnector;
import com.nhl.link.move.runtime.LmRuntimeBuilder;
import io.bootique.linkmove.LinkMoveBuilderCallback;
import io.bootique.resource.ResourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ArticleCallBack implements LinkMoveBuilderCallback {

    @Override
    public void build(LmRuntimeBuilder builder) {

        //define data source file for articles
        builder.withConnector("articleSourceConnector",
                new StreamConnector() {
                    @Override
                    public InputStream getInputStream() throws IOException {
                        URL url = ResourceFactory.class.getClassLoader().getResource("etl/article.csv");

                        return url.openStream();
                    }

                    @Override
                    public void shutdown() {

                    }
                });
    }
}
