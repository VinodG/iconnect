//  Copyright 2012 Zonghai Li. All rights reserved.
//
//  Redistribution and use in binary and source forms, with or without modification,
//  are permitted for any project, commercial or otherwise, provided that the
//  following conditions are met:
//  
//  Redistributions in binary form must display the copyright notice in the About
//  view, website, and/or documentation.
//  
//  Redistributions of source code must retain the copyright notice, this list of
//  conditions, and the following disclaimer.
//
//  THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
//  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
//  PARTICULAR PURPOSE AND NONINFRINGEMENT OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THIS SOFTWARE.


package com.winit.common.httpmanager;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.winit.common.util.LogUtils;


/**
 * Basic memory cache implementation of BitmapCache 
 * 
 * @author zonghai@gmail.com
 */
public class LRUBitmapCache implements BitmapCache{

    private static final String TAG = "LRUBitmapCache";
    private static final boolean DEBUG = false;

    LruCache<String,Bitmap> mCache;

    private class Cache extends LruCache<String,Bitmap>{

        /**
         * @param maxSize for caches that do not override {@link #sizeOf}, this is
         *                the maximum number of entries in the cache. For all other caches,
         *                this is the maximum sum of the sizes of the entries in this cache.
         */
        public Cache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }

        @Override
        protected void entryRemoved( boolean evicted, String key, Bitmap oldValue, Bitmap newValue ) {
            synchronized (oldValue) {
                oldValue.recycle();
                LogUtils.debug(TAG,key+"");
                LogUtils.debug(TAG,mCache.maxSize()+"");
                LogUtils.debug(TAG,mCache.size()+"");
                LogUtils.debug(TAG,mCache.putCount()+"");
            }
        }
    }


    /**
     * max number of resource this cache contains
     * @param size
     */
    public LRUBitmapCache(int size) {
        mCache = new Cache(size);
    }
    
    
    @Override
    public synchronized boolean exists(String key){
       return mCache.get(key) != null;
    }

    
    @Override
    public synchronized void invalidate(String key){
        if(DEBUG) LogUtils.debug(TAG, key + " is invalidated from the cache");
    }

    
    @Override
    public synchronized void clear(){
    }

/*    @Override
    public void freeUp(int memory) {
        if(mCache.maxSize() < mCache.size()+memory){
        }
    }*/


    /**
     * If the cache storage is full, return an item to be removed. 
     * 
     * Default strategy:  oldest out: O(n)
     * 
     * @return item key
     */
    protected synchronized String findItemToInvalidate() {
        return "";
    }

    
    @Override
    public synchronized Bitmap loadData(String key) {
        return mCache.get(key);
    }


    @Override
    public synchronized void storeData(String key, Object data) {
        if(this.exists(key)) {
            return;
        }
        LogUtils.debug(TAG,mCache.maxSize()+"");
        LogUtils.debug(TAG,mCache.size()+"");
        LogUtils.debug(TAG,mCache.putCount()+"");
        mCache.put(key, (Bitmap) data);
    }

    @Override
    public synchronized void removeData(String key) {
        if(mCache != null)
            mCache.remove(key);
    }

}
