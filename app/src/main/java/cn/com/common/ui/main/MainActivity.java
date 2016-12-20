package cn.com.common.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.common.R;
import cn.com.common.api.Api;
import cn.com.common.api.ApiService;
import cn.com.common.api.HostType;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.tv_1,R.id.tv_2,R.id.tv_3})
    public void  onclick(View view){
        switch (view.getId()) {
            case R.id.tv_1:
                ApiService aDefault = Api.getDefault(HostType.REALSETYPE);
                aDefault.query(103665).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("wulafu", "onNext: ---------------------"+s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("wulafu", "onNext: ---------------------" + t.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case R.id.tv_2:

                ApiService aDefault1 = Api.getDefault(HostType.DEVELOPTYPE);
                aDefault1.login("小王子", "", 0, 10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("wulafu", "onNext: ---------------------" + s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case R.id.tv_3:

                ApiService aDefault2 = Api.getDefault(HostType.REALSETYPE);

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("pageIndex",0);
                    jsonObject.put("pageSize",10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aDefault2.getTeaacher(jsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("wulafu", "onNext: ---------------------" + s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;

        }



    }
}
