import com.nhl.link.move.connect.StreamConnector;
import com.nhl.link.move.runtime.LmRuntimeBuilder;
import io.bootique.linkmove.LinkMoveBuilderCallback;
import io.bootique.resource.ResourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class LmStackExtendCallBack implements LinkMoveBuilderCallback {

    @Override
    public void build(LmRuntimeBuilder builder) {

        builder.withConnector("domainSourceConnector", new StreamConnector() {
            @Override
            public InputStream getInputStream(Map<String, ?> var1) throws IOException {
                return ResourceFactory.class.getClassLoader().getResource("etl/domain.json").openStream();
            }

            @Override
            public void shutdown() {

            }
        });
    }
}
