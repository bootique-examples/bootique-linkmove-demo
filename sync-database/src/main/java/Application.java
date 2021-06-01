import io.bootique.Bootique;
import io.bootique.di.BQModule;
import io.bootique.di.Binder;
import io.bootique.job.runtime.JobModule;

public class Application implements BQModule {

    public static void main(String[] args) {
        Bootique.app(args)
                .autoLoadModules()
                .module(Application.class)
                .exec()
                .exit();
    }

    @Override
    public void configure(Binder binder) {
        //register job running batch of tasks
        JobModule.extend(binder).addJob(SyncJob.class);
    }
}
