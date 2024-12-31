package com.jhipster.demo.pfe.domain;

import static com.jhipster.demo.pfe.domain.ArticleTestSamples.*;
import static com.jhipster.demo.pfe.domain.WorkOrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.pfe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = getArticleSample1();
        Article article2 = new Article();
        assertThat(article1).isNotEqualTo(article2);

        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);

        article2 = getArticleSample2();
        assertThat(article1).isNotEqualTo(article2);
    }

    @Test
    void workOrderTest() {
        Article article = getArticleRandomSampleGenerator();
        WorkOrder workOrderBack = getWorkOrderRandomSampleGenerator();

        article.addWorkOrder(workOrderBack);
        assertThat(article.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getArticles()).containsOnly(article);

        article.removeWorkOrder(workOrderBack);
        assertThat(article.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getArticles()).doesNotContain(article);

        article.workOrders(new HashSet<>(Set.of(workOrderBack)));
        assertThat(article.getWorkOrders()).containsOnly(workOrderBack);
        assertThat(workOrderBack.getArticles()).containsOnly(article);

        article.setWorkOrders(new HashSet<>());
        assertThat(article.getWorkOrders()).doesNotContain(workOrderBack);
        assertThat(workOrderBack.getArticles()).doesNotContain(article);
    }
}
