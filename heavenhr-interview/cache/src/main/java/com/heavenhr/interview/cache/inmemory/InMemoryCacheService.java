package com.heavenhr.interview.cache.inmemory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.heavenhr.interview.cache.CacheService;

@Service
public class InMemoryCacheService implements CacheService {

	private final Map<String, Object> cache = new HashMap<>();
	
	@Override
	public void setKey(String key, Object value) {
		cache.put(key, value);
	}

	@Override
	public Object getValue(String key) {
		if(cache.containsKey(key))
			return cache.get(key);
		return null;
	}

	@Override
	public void resetKey(String key) {
		if(cache.containsKey(key))
			cache.remove(key);
	}

}
