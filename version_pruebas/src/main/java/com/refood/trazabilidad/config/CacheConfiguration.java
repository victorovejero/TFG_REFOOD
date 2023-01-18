package com.refood.trazabilidad.config;

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

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
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
            createCache(cm, com.refood.trazabilidad.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.refood.trazabilidad.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.refood.trazabilidad.domain.User.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Authority.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.User.class.getName() + ".authorities");
            createCache(cm, com.refood.trazabilidad.domain.Nucleo.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Nucleo.class.getName() + ".donantes");
            createCache(cm, com.refood.trazabilidad.domain.Nucleo.class.getName() + ".benefs");
            createCache(cm, com.refood.trazabilidad.domain.Nucleo.class.getName() + ".voluntarios");
            createCache(cm, com.refood.trazabilidad.domain.Donante.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Donante.class.getName() + ".alEnts");
            createCache(cm, com.refood.trazabilidad.domain.Benef.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Benef.class.getName() + ".alSals");
            createCache(cm, com.refood.trazabilidad.domain.Benef.class.getName() + ".pBenefs");
            createCache(cm, com.refood.trazabilidad.domain.Benef.class.getName() + ".checkouts");
            createCache(cm, com.refood.trazabilidad.domain.Benef.class.getName() + ".intols");
            createCache(cm, com.refood.trazabilidad.domain.PBenef.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.PBenef.class.getName() + ".intols");
            createCache(cm, com.refood.trazabilidad.domain.AlEnt.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.AlEnt.class.getName() + ".alSals");
            createCache(cm, com.refood.trazabilidad.domain.AlSal.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.AlSal.class.getName() + ".checkouts");
            createCache(cm, com.refood.trazabilidad.domain.Checkout.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Checkout.class.getName() + ".alSals");
            createCache(cm, com.refood.trazabilidad.domain.TipoAl.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.TipoAl.class.getName() + ".alEnts");
            createCache(cm, com.refood.trazabilidad.domain.TipoAl.class.getName() + ".intols");
            createCache(cm, com.refood.trazabilidad.domain.Tupper.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Tupper.class.getName() + ".alSals");
            createCache(cm, com.refood.trazabilidad.domain.Tupper.class.getName() + ".alEnts");
            createCache(cm, com.refood.trazabilidad.domain.Intol.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Intol.class.getName() + ".benefs");
            createCache(cm, com.refood.trazabilidad.domain.Intol.class.getName() + ".pBenefs");
            createCache(cm, com.refood.trazabilidad.domain.Intol.class.getName() + ".tipoAls");
            createCache(cm, com.refood.trazabilidad.domain.Voluntario.class.getName());
            createCache(cm, com.refood.trazabilidad.domain.Socio.class.getName());
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
