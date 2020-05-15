package okhttp;

import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/9/1 0001.
 */
public class BaseOkHttpClient {
    private Builder mBuilder;

    private BaseOkHttpClient(Builder builder) {
        this.mBuilder = builder;
    }

    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(buildGetRequestParam());
        if (mBuilder.method == "GET") {
           /* if(SharedPreferencesUtils.contains(App.Companion.getApp(), OkHttpData.TOKEN)) {
              String token = (String) SharedPreferencesUtils.getBean(App.Companion.getApp(), OkHttpData.TOKEN);
                LogUtil.v("GET=="+token);
            builder.addHeader("token",token);
            }else{
              //  builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            }*/
            builder.get();
        } else if (mBuilder.method == "POST") {
            builder.url(mBuilder.url);
            try {
               /* if(SharedPreferencesUtils.contains(App.Companion.getApp(), OkHttpData.TOKEN)){
                   String token = (String) SharedPreferencesUtils.getBean(App.Companion.getApp(), OkHttpData.TOKEN);
                   LogUtil.v("POST=="+token);
                    builder.addHeader("token",token);
                }else {
                  //  builder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                }*/
                builder.post(buildPostRequestParam());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }

    /**
     * GET拼接参数
     *
     * @return
     */
    private String buildGetRequestParam() {
        if (mBuilder.params.size() <= 0) {
            return this.mBuilder.url;
        }
        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();
        for (RequestParameter p : mBuilder.params) {
            builder.appendQueryParameter(p.getKey(), p.getObj() == null ? "" : p.getObj().toString());
        }
        String url = builder.build().toString();
        return url;
    }

    /**
     * POST拼接参数
     *
     * @return
     */
    private RequestBody buildPostRequestParam()throws UnsupportedEncodingException {
        if (mBuilder.isJsonParam) {
            StringBuffer sb = new StringBuffer();
            for (RequestParameter p : mBuilder.params){
                sb.append(p.getKey()+"="+ URLEncoder.encode(p.getObj().toString(),"utf-8")+"&");
            }
            return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), sb.toString());
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (RequestParameter p : mBuilder.params) {
            builder.add(p.getKey(), p.getObj() == null ? "" : p.getObj().toString());
        }
        return builder.build();
    }

    /**
     * 回调调用
     *
     * @param callBack
     */
    public void enqueue(BaseCallBack callBack) {
        OkHttpManage.getInstance().request(this, callBack);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private String method;
        private List<RequestParameter> params;
        private boolean isJsonParam;

        public BaseOkHttpClient build() {
            return new BaseOkHttpClient(this);
        }

        private Builder() {
            method = "GET";
            params = new ArrayList<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * GET请求
         *
         * @return
         */
        public Builder get() {
            method = "GET";
            return this;
        }

        /**
         * POST请求
         *
         * @return
         */
        public Builder post() {
            method = "POST";
            isJsonParam = true;
            return this;
        }

        /**
         * JSON参数
         *
         * @return
         */
        public Builder json() {
            isJsonParam = true;
            return post();
        }

        /**
         * Form请求
         *
         * @return
         */
        public Builder form() {
            return this;
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         * @return
         */
        public Builder addParam(String key, Object value) {
            if (params == null) {
                params = new ArrayList<>();
            }
            params.add(new RequestParameter(key, value));
            return this;
        }
    }

}
