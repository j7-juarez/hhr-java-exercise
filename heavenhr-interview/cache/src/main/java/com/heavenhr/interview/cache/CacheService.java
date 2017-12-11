package com.heavenhr.interview.cache;

public interface CacheService {

	public void setKey(String key, Object value);
	
	public Object getValue(String key);
	
	public void resetKey(String key);
	
}
