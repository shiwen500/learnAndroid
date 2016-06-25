package www.seven.com.meiapp.net;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Seven on 2016/6/14.
 */
public class OkHttpUtil {

    private static final OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static String forMatParams(Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return "";
        }

        String formatResult = "";

        Set<String> keySet = params.keySet();

        for (String key : keySet) {
            formatResult += (key + "=" + params.get(key));
        }

        return formatResult;
    }

    public static class MeiResponse {

        public int statuCode;

        public String res;
    }

    public interface CallBack {

        void onSuccess(MeiResponse response);

        void onFail(MeiResponse response);
    }

    public static void simpleGet(String url, Map<String, String> params, final CallBack callBack) {

        String absUrl = Apis.BASE_URL + url + "?" + forMatParams(params);
        Request request = new Request.Builder()
                .url(absUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    MeiResponse meiResponse = new MeiResponse();
                    callBack.onFail(meiResponse);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack != null) {
                    MeiResponse meiResponse = new MeiResponse();
                    meiResponse.statuCode = response.code();
                    meiResponse.res = response.body().string();
                    callBack.onSuccess(meiResponse);
                }
            }
        });
    }

    public static void simplePost(String url, Map<String, String> params, final CallBack callBack) {
        String absUrl = Apis.BASE_URL + url;

        FormBody formBody;
        FormBody.Builder formBuilder = new FormBody.Builder();

        if (params == null || params.size() == 0) {
            formBody = null;
        } else {

            Set<String> keySet = params.keySet();
            for (String key : keySet) {

                formBuilder.add(key, params.get(key));
            }

            formBody = formBuilder.build();
        }

        Request request = new Request.Builder()
                .url(absUrl)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    MeiResponse meiResponse = new MeiResponse();
                    callBack.onFail(meiResponse);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack != null) {
                    MeiResponse meiResponse = new MeiResponse();
                    meiResponse.statuCode = response.code();
                    meiResponse.res = response.body().string();
                    callBack.onSuccess(meiResponse);
                }
            }
        });
    }
}
