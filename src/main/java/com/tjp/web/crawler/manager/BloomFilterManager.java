package com.tjp.web.crawler.manager;

import java.nio.LongBuffer;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.tjp.web.crawler.config.Config;
import com.tjp.web.crawler.util.RedisUtil;

public class BloomFilterManager {
	
	/* BitSet初始分配2^24个bit */  
    private static final int DEFAULT_SIZE = 1 << 25;  
      
    /* 不同哈希函数的种子，一般应取质数 */  
    private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };  
      
    private BitSet bits ;  
      
    /* 哈希函数对象 */  
    private SimpleHash[] func = new SimpleHash[seeds.length];  
	
	
	private static BloomFilterManager hashManager=new BloomFilterManager();
	
	private BloomFilterManager()
	{
	}
	
	public void init()
	{
		for (int i = 0; i < seeds.length; i++) {  
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);  
        } 
		String data=RedisUtil.getInstance().get(Config.REDIS_HASH_KEY);
		if(Strings.isNullOrEmpty(data))
		{
			bits = new BitSet(DEFAULT_SIZE);
		}else
		{
			List<String> list=Splitter.on(",").trimResults().splitToList(data);
			long[] longData=new long[list.size()];
			for(int i=0;i<list.size();i++)
			{
				longData[i]=Long.parseLong(list.get(i));
			}
			bits=BitSet.valueOf(longData);
		}
	}
	
	
	public BitSet getBits() {
		return bits;
	}




	public void setBits(BitSet bits) {
		this.bits = bits;
	}




	public static BloomFilterManager getInstance()
	{
		return hashManager;
	}
	
	// 将字符串标记到bits中  
    public void add(String value) {  
        for (SimpleHash f : func) {  
            bits.set(f.hash(value), true);  
        }  
    }  
  
    // 判断字符串是否已经被bits标记  
    public synchronized boolean containsWithAdd(String value) {  
        if (value == null) {  
            return false;  
        }  
        int[] index=new int[func.length];
        boolean ret = true;  
        for (int i=0;i<func.length;i++) {  
        	index[i]=func[i].hash(value);
            ret = ret && bits.get(index[i]);  
        }
        if(!ret)
        {
        	for (int i=0;i<index.length;i++) {  
                bits.set(index[i], true);  
            }  
        	String t=getInstance().getBits().toString();
        	t=t.substring(1, t.length()-1);
        	RedisUtil.getInstance().set(Config.REDIS_HASH_KEY, t);
        }
        return ret;  
    }  
	
	
	/* 哈希函数类 */  
    public static class SimpleHash {  
        private int cap;  
        private int seed;  
  
        public SimpleHash(int cap, int seed) {  
            this.cap = cap;  
            this.seed = seed;  
        }  
  
        // hash函数，采用简单的加权和hash  
        public int hash(String value) {  
            int result = 0;  
            int len = value.length();  
            for (int i = 0; i < len; i++) {  
                result = seed * result + value.charAt(i);  
            }  
            return (cap - 1) & result;  
        }  
    }  
    
    public static void main(String[] args) {
    	String[] URLS = {  
                "http://www.csdn.net/",  
                "http://www.csdn.net/",  
                "http://www.baidu.com/",  
                "http://www.google.com.hk",  
                "http://www.cnblogs.com/",  
                "http://www.zhihu.com/",  
                "https://www.shiyanlou.com/",  
                "http://www.google.com.hk",  
                "https://www.shiyanlou.com/",  
                "http://www.csdn.net/"
        };  
    	
    	for(String url : URLS)
    	{
    		if(!BloomFilterManager.getInstance().containsWithAdd(url))
    		{
    			System.out.println(url);
    		}
    	}
    	String t=getInstance().getBits().toString();
    	System.out.println(t);
    	t=t.substring(1, t.length()-1);
    	System.out.println(t);
    	
    	System.out.println(BitSet.valueOf(getInstance().getBits().toLongArray()));
	}
}
