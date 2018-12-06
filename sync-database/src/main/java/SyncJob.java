import com.google.inject.Inject;
import com.nhl.link.move.Execution;
import com.nhl.link.move.LmTask;
import com.nhl.link.move.annotation.AfterTargetsMerged;
import com.nhl.link.move.runtime.LmRuntime;
import com.nhl.link.move.runtime.task.createorupdate.CreateOrUpdateSegment;
import com.nhl.link.move.runtime.task.SourceTargetPair;
import io.bootique.job.BaseJob;
import io.bootique.job.JobMetadata;
import io.bootique.job.runnable.JobResult;
import io.bootique.lm.cayenne.TArticle;
import io.bootique.lm.cayenne.TDomain;
import io.bootique.lm.cayenne.TTag;

import java.time.LocalDateTime;
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
                .createOrUpdate(TDomain.class)
                .sourceExtractor("domain-extractor.xml")
                .matchBy(TDomain.NAME)
                .task();

        LmTask articleTask = lmRuntime
                .getTaskService()
                .createOrUpdate(TArticle.class)
                .batchSize(50)
                .stageListener(new ArticleListener())
                .sourceExtractor("article-extractor.xml")
                .matchBy(TArticle.TITLE)
                .task();

        LmTask tagTask = lmRuntime
                .getTaskService()
                .createOrUpdate(TTag.class)
                .sourceExtractor("tag-extractor.xml")
                .matchBy(TTag.NAME)
                .task();

        Execution domainResult = domainTask.run();
        Execution articleResult = articleTask.run();
        Execution tagResult = tagTask.run();

        System.out.println("Domains:" + domainResult.createReport());
        System.out.println("Articles:" + articleResult.createReport());
        System.out.println("Tags:" + tagResult.createReport());

        return JobResult.success(getMetadata());
    }

    public static class ArticleListener {

        @AfterTargetsMerged
        public void fixArticles(Execution e, CreateOrUpdateSegment<TArticle> segment) {
            segment.getMerged().stream()
                    .map(SourceTargetPair::getTarget)
                    .forEach(g -> {
                        g.setPublishedOn(LocalDateTime.now());
                        g.setQuoteIndex(0);
                    });
        }

    }
}
