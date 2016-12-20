package cn.com.common.ui.guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.common.R;
import cn.com.common.ui.main.MainActivity;
import cn.com.common.widget.splash.PreviewIndicator;
import cn.com.common.widget.splash.PreviewVideoView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    private CompositeDisposable disposables = new CompositeDisposable();
    private PreviewVideoView mVideoView;
    private ViewPager mVpImage;
    private PreviewIndicator mIndicator;
    private TextView tv_startTiYan;

    private List<View> mViewList = new ArrayList<>();
    private int[] mImageResIds = new int[]{R.mipmap.intro_text_1, R.mipmap.intro_text_2, R.mipmap.intro_text_3};
    private CustomPagerAdapter mAdapter;

    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();
        mVideoView = (PreviewVideoView) findViewById(R.id.vv_preview);
        mVpImage = (ViewPager) findViewById(R.id.vp_image);
        mIndicator = (PreviewIndicator) findViewById(R.id.indicator);
        tv_startTiYan = (TextView) findViewById(R.id.tv_startTiYan);
        tv_startTiYan.setOnClickListener(this);

        mVideoView.setVideoURI(Uri.parse(getVideoPath()));

        for (int i = 0; i < mImageResIds.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.preview_item, null, false);
            ((ImageView) view.findViewById(R.id.iv_intro_text)).setImageResource(mImageResIds[i]);
            mViewList.add(view);
        }

        mAdapter = new CustomPagerAdapter(mViewList);
        mVpImage.setAdapter(mAdapter);
        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                mIndicator.setSelected(mCurrentPage);
                doSomeWork(mCurrentPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        doSomeWork(mCurrentPage);

    }

    /**
     * 获取video文件的路径
     *
     * @return 路径
     */
    private String getVideoPath() {
        return "android.resource://" + this.getPackageName() + "/" + R.raw.guivideo;
    }


    private void doSomeWork(int mCurrentPage) {
         disposables.clear(); // clearing it : do not emit after destroy
        disposables.add(getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
        if(mCurrentPage==mViewList.size()-1){
            tv_startTiYan.setVisibility(View.VISIBLE);

        }else{
            tv_startTiYan.setVisibility(View.GONE);
        }
    }

    private Observable<? extends Long> getObservable() {
        return Observable.interval(0, 4 * 1000, TimeUnit.MILLISECONDS);
    }

    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {

            @Override
            public void onNext(Long value) {
                mVideoView.seekTo(mCurrentPage * 4 * 1000);
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }


            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        disposables.clear(); // clearing it : do not emit after destroy
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public static class CustomPagerAdapter extends PagerAdapter {

        private List<View> mViewList;

        public CustomPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
