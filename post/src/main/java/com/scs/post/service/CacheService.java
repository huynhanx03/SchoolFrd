package com.scs.post.service;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CacheService {
    Cache<String, Object> localCache;
    RedisTemplate<String, Object> redisTemplate;

    public Optional<Object> get(String key) {
        Object value = localCache.getIfPresent(key);
        if (value != null) return Optional.of(value);

        value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            localCache.put(key, value);
            return Optional.of(value);
        }

        return Optional.empty();
    }

    public void put(String key, Object value, long ttlInMinutes) {
        localCache.put(key, value);
        redisTemplate.opsForValue().set(key, value, ttlInMinutes, TimeUnit.MINUTES);
    }

    public void evict(String key) {
        localCache.invalidate(key);
        redisTemplate.delete(key);
    }
}
