package sample.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.*;

import cn.hutool.cache.impl.TimedCache;
import okhttp3.*;
import sample.constant.AppConstant;
import sample.utils.LogUtils;
import sample.utils.MichaelUtils;
import sample.utils.StringUtils;

@SuppressWarnings("all")
public class HttpService {

    private OkHttpClient mOkHttpClient;
    private volatile static HttpService INSTANCE;
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36";
    private TimedCache<String, String> cache = new TimedCache<>(AppConstant.CACHE_ONE_HOUR);

    {
        cache.schedulePrune(1000);
    }

    public static HttpService getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpService();
                }
            }
        }
        return INSTANCE;
    }

    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    private HttpService() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            mOkHttpClient = builder.build();
        } catch (Exception e) {
            mOkHttpClient = new OkHttpClient();
        }


    }

    public String get(String url) {
        return get(url, null);
    }

    public String get(String url, HashMap<String, String> parameters) {
        return get(url, parameters, null);
    }

    public String get(String url, HashMap<String, String> parameters, String content_type) {
        StringBuilder builder = new StringBuilder();
        String line;
        String para = "";
        try {
            if (parameters != null) {
                Iterator<String> keys = parameters.keySet().iterator();
                Iterator<String> value = parameters.values().iterator();
                while (keys.hasNext()) {
                    if (StringUtils.isEmpty(para)) {
                        para += "?" + (keys.next() + "=" + value.next());
                    } else {
                        para += "&" + (keys.next() + "=" + value.next());
                    }
                }
            }

            URLConnection connection = new URL(url + para).openConnection();
            LogUtils.e(url + para);
            connection.setReadTimeout(60000);
            connection.setConnectTimeout(60000);
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Content-Type", content_type == null ? "application/json; charset=UTF-8" : content_type);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", USER_AGENT);
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8"/*"GB2312"*/);
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();
            isr.close();
            is.close();
        } catch (Exception e) {
            LogUtils.e(url + para + "\n" + MichaelUtils.getStackTrace(e));
            return "";
        }
        return builder.toString();
    }


    public void getByOkHttp( String url, HttpCallBack callBack) {

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).get().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.fail(e.getLocalizedMessage());
                }
                if (call != null) {
                    call.cancel();
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String res = response.body().string();
                if (AppConstant.IS_PRINT_NETWORK_LOG) {
                    System.out.println("response>>" + res);
                }
                if (callBack != null) {
                    callBack.success(res);
                }
                if (call != null) {
                    call.cancel();
                }
            }
        });
    }

    public void post(FormBody.Builder formBodyBuilder, String url, HttpCallBack callBack) {
        post(AppConstant.CACHE_ONE_HOUR, formBodyBuilder, url, callBack);
    }

    public void post(long cacheTime, FormBody.Builder formBodyBuilder, String url, HttpCallBack callBack) {

        String key = url + " ";
        FormBody formBody = formBodyBuilder.build();
        for (int i = 0; i < formBody.size(); i++) {
            key += formBody.name(i) + "_" + formBody.value(i) + ";";
        }
        if (StringUtils.isEmpty(cache.get(key, false)) && cacheTime >= -1) {
            String finalKey = key;
            mOkHttpClient.newCall(new Request.Builder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("UserAgent", USER_AGENT)
                    .url(url)
                    .post(formBody).build())
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            if (callBack != null) {
                                callBack.fail(e.getLocalizedMessage());
                            }
                            if (call != null) {
                                call.cancel();
                            }
                        }

                        @Override
                        public void onResponse(Call call, okhttp3.Response response) throws IOException {
                            String res = response.body().string();
                            LogUtils.e(finalKey, MichaelUtils.decodeUnicode(res));
                            cache.put(finalKey, res, cacheTime);
                            if (AppConstant.IS_PRINT_NETWORK_LOG) {
                                System.out.println("response>>" + res);
                            }
                            if (callBack != null) {
                                callBack.success(res);
                            }
                            if (call != null) {
                                call.cancel();
                            }
                        }
                    });
        } else {
            String res = cache.get(key);
            LogUtils.e(key, MichaelUtils.decodeUnicode(res));
            if (callBack != null) {
                callBack.success(res);
            }
        }
    }


}