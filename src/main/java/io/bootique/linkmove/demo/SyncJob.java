package io.bootique.linkmove.demo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.nhl.link.move.Execution;
import com.nhl.link.move.LmTask;
import com.nhl.link.move.annotation.AfterTargetsMerged;
import com.nhl.link.move.runtime.LmRuntime;
import com.nhl.link.move.runtime.task.createorupdate.CreateOrUpdateSegment;
import com.nhl.link.move.runtime.task.createorupdate.CreateOrUpdateTuple;
import io.bootique.job.BaseJob;
import io.bootique.job.JobMetadata;
import io.bootique.job.runnable.JobResult;
import io.bootique.lm.cayenne.Article;
import io.bootique.lm.cayenne.Domain;
import io.bootique.lm.cayenne.Tag;

import java.util.Date;
import java.util.Map;

public class SyncJob extends BaseJob {
    @Inject
    private Provider<LmRuntime> lmRuntimeProvider;

    public SyncJob() {
        super(JobMetadata.build(SyncJob.class));
    }

    @Override
    public JobResult run(Map<String, Object> map) {
        LmTask domainTask = lmRuntimeProvider.get()
                .getTaskService()
                .createOrUpdate(Domain.class)
                .sourceExtractor("domain-extractor.xml")
                .matchBy(Domain.NAME)
                .task();

        LmTask articleTask = lmRuntimeProvider.get()
                .getTaskService()
                .createOrUpdate(Article.class)
                .batchSize(50)
                .stageListener(new ArticleListener())
                .sourceExtractor("article-extractor.xml")
                .matchBy(Article.TITLE)
                .task();

        LmTask tagTask = lmRuntimeProvider.get()
                .getTaskService()
                .createOrUpdate(Tag.class)
                .sourceExtractor("tag-extractor.xml")
                .matchBy(Tag.NAME)
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
        public void fixArticles(Execution e, CreateOrUpdateSegment<Article> segment) {

            segment.getMerged().stream().map(CreateOrUpdateTuple::getTarget)
                    .forEach(g -> {
                        g.setPublishedOn(new Date());
                    });
        }

    }
}
