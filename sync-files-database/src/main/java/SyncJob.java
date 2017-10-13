import com.google.inject.Inject;
import com.nhl.link.move.Execution;
import com.nhl.link.move.LmTask;
import com.nhl.link.move.runtime.LmRuntime;
import io.bootique.job.BaseJob;
import io.bootique.job.JobMetadata;
import io.bootique.job.runnable.JobResult;
import io.bootique.lm.cayenne.SArticle;
import io.bootique.lm.cayenne.SDomain;
import io.bootique.lm.cayenne.STag;

import java.util.Map;

public class SyncJob extends BaseJob {
    @Inject
    private LmRuntime lmRuntime;

    public SyncJob() {
        super(JobMetadata.build(SyncJob.class));
    }

    @Override
    public JobResult run(Map<String, Object> map) {
        LmTask domainTask = lmRuntime
                .getTaskService()
                .createOrUpdate(SDomain.class)
                .sourceExtractor("domain-extractor.xml")
                .matchBy(SDomain.NAME)
                .task();

        LmTask articleTask = lmRuntime
                .getTaskService()
                .createOrUpdate(SArticle.class)
                .batchSize(50)
                .sourceExtractor("article-extractor.xml")
                .matchBy(SArticle.TITLE)
                .task();

        LmTask tagTask = lmRuntime
                .getTaskService()
                .createOrUpdate(STag.class)
                .sourceExtractor("tag-extractor.xml")
                .matchBy(STag.NAME)
                .task();

        Execution domainResult = domainTask.run();
        Execution articleResult = articleTask.run();
        Execution tagResult = tagTask.run();

        System.out.println("Domains:" + domainResult.createReport());
        System.out.println("Articles:" + articleResult.createReport());
        System.out.println("Tags:" + tagResult.createReport());

        return JobResult.success(getMetadata());
    }

}
