import com.google.inject.Binder;
import com.google.inject.Module;
import io.bootique.Bootique;
import io.bootique.job.runtime.JobModule;
import io.bootique.linkmove.LinkMoveModule;
import io.bootique.lm.demo.ArticleCallBack;
import io.bootique.lm.demo.SyncJob;

public class Application implements Module {

    public static void main(String[] args) {
        Bootique.app(args).autoLoadModules().module(Application.class).run();
    }

    @Override
    public void configure(Binder binder) {

        //extend LinkMove stack with call back
        LinkMoveModule.extend(binder).addLinkMoveBuilderCallback(ArticleCallBack.class);

        //register synchronization job running batch of tasks
        JobModule.extend(binder).addJob(SyncJob.class);
    }
}
