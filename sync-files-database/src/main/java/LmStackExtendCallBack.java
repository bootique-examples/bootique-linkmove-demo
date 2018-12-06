import com.google.inject.Inject;
import com.nhl.link.move.connect.StreamConnector;
import com.nhl.link.move.runtime.LmRuntimeBuilder;
import io.bootique.jdbc.DataSourceFactory;
import io.bootique.linkmove.LinkMoveBuilderCallback;
import io.bootique.resource.ResourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LmStackExtendCallBack implements LinkMoveBuilderCallback {

    @Override
    public void build(LmRuntimeBuilder builder) {

        builder.withConnector("domainSourceConnector", new StreamConnector() {
            @Override
            public InputStream getInputStream() throws IOException {
                return ResourceFactory.class.getClassLoader().getResource("etl/domain.json").openStream();
            }

            @Override
            public void shutdown() {

            }
        });
    }
}
