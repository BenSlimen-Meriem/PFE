package com.jhipster.demo.pfe.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Article getArticleSample1() {
        return new Article().id(1L).designation("designation1");
    }

    public static Article getArticleSample2() {
        return new Article().id(2L).designation("designation2");
    }

    public static Article getArticleRandomSampleGenerator() {
        return new Article().id(longCount.incrementAndGet()).designation(UUID.randomUUID().toString());
    }
}
