package com.jhipster.demo.pfe.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.jhipster.demo.pfe.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.jhipster.demo.pfe.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.jhipster.demo.pfe.domain.User.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Authority.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.User.class.getName() + ".authorities");
            createCache(cm, com.jhipster.demo.pfe.domain.Societe.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Societe.class.getName() + ".sites");
            createCache(cm, com.jhipster.demo.pfe.domain.Societe.class.getName() + ".departements");
            createCache(cm, com.jhipster.demo.pfe.domain.Societe.class.getName() + ".employees");
            createCache(cm, com.jhipster.demo.pfe.domain.Departement.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Departement.class.getName() + ".employees");
            createCache(cm, com.jhipster.demo.pfe.domain.Client.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Client.class.getName() + ".contacts");
            createCache(cm, com.jhipster.demo.pfe.domain.Client.class.getName() + ".affaires");
            createCache(cm, com.jhipster.demo.pfe.domain.Contact.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Site.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Site.class.getName() + ".workOrders");
            createCache(cm, com.jhipster.demo.pfe.domain.Affaire.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Affaire.class.getName() + ".workOrders");
            createCache(cm, com.jhipster.demo.pfe.domain.WorkOrder.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.WorkOrder.class.getName() + ".employees");
            createCache(cm, com.jhipster.demo.pfe.domain.WorkOrder.class.getName() + ".articles");
            createCache(cm, com.jhipster.demo.pfe.domain.WorkOrder.class.getName() + ".vehicules");
            createCache(cm, com.jhipster.demo.pfe.domain.WorkOrder.class.getName() + ".ordreTravailClients");
            createCache(cm, com.jhipster.demo.pfe.domain.WorkOrder.class.getName() + ".ordreFacturations");
            createCache(cm, com.jhipster.demo.pfe.domain.Employee.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Employee.class.getName() + ".workOrders");
            createCache(cm, com.jhipster.demo.pfe.domain.Executeur.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Planificateur.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.GestionCout.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.OrdreTravailClient.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Vehicule.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Vehicule.class.getName() + ".workOrders");
            createCache(cm, com.jhipster.demo.pfe.domain.Article.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Article.class.getName() + ".workOrders");
            createCache(cm, com.jhipster.demo.pfe.domain.Motif.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.Motif.class.getName() + ".workOrders");
            createCache(cm, com.jhipster.demo.pfe.domain.OrdreFacturation.class.getName());
            createCache(cm, com.jhipster.demo.pfe.domain.SST.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
