package com.commerce.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.commerce.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.commerce.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.commerce.domain.Currency.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Price.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Catalog.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Category.class.getName() + ".classificationAttributes", jcacheConfiguration);
            cm.createCache(com.commerce.domain.Category.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.commerce.domain.ClassificationClassAttribute.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Stock.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Warehouse.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Product.class.getName() + ".prices", jcacheConfiguration);
            cm.createCache(com.commerce.domain.Product.class.getName() + ".stocks", jcacheConfiguration);
            cm.createCache(com.commerce.domain.Product.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(com.commerce.domain.Product.class.getName() + ".media", jcacheConfiguration);
            cm.createCache(com.commerce.domain.Media.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.CustomFilter.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.OrderEntry.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Cart.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.Cart.class.getName() + ".entries", jcacheConfiguration);
            cm.createCache(com.commerce.domain.CustomerOrder.class.getName(), jcacheConfiguration);
            cm.createCache(com.commerce.domain.CustomerOrder.class.getName() + ".entries", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
