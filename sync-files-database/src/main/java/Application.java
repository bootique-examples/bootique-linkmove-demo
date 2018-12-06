import com.google.inject.Binder;
import com.google.inject.Module;
import io.bootique.Bootique;
import io.bootique.job.runtime.JobModule;
import io.bootique.linkmove.LinkMoveModule;

public class Application implements Module {

    public static void main(String[] args) {
        Bootique.app(args)
                .autoLoadModules()
                .module(Application.class)
                .exec()
                .exit();
    }

    @Override
    public void configure(Binder binder) {
        //extend LinkMove stack
        LinkMoveModule.extend(binder).addLinkMoveBuilderCallback(LmStackExtendCallBack.class);

        //register synchronization job running batch of tasks
        JobModule.extend(binder).addJob(SyncJob.class);
    }
}
