package com.pagatodo.network_manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.thoughtcrime.ssl.pinning.PinningSSLSocketFactory;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private String[] pins;
    public static String REQUEST_TAG = "VOLLEY_REQUEST_TAG";

    /**
     * Método para obtener una instancia de Volley
     *
     * @param context {@link Context}
     */
    private VolleySingleton(Context context, String[] pins) {
        this.pins = pins;
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized VolleySingleton getInstance(Context context, String[] pins) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context, pins);
        }
        return mInstance;
    }

    /**
     * Método para obtener una instancia de {@link RequestQueue}
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            HttpParams httpParameters = new BasicHttpParams();
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            try {
                schemeRegistry.register(new Scheme("https", new PinningSSLSocketFactory(mCtx.getApplicationContext(), pins, 0), 443));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
            DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpParameters);
            if (pins != null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HttpClientStack(httpClient));
            } else {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(REQUEST_TAG);
        getRequestQueue().add(req);
    }

    public void deleteQueue() {
        getRequestQueue().cancelAll(REQUEST_TAG);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
