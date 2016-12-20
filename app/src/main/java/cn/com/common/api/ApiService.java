package cn.com.common.api;

import org.json.JSONObject;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {
    @GET("book/search")
    Flowable<String> login(@Query("q") String name, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);
    @GET("queryLesson")
    Flowable<String> query(@Query("classId") int classId);
   // @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @GET("queryTeacher")
    Flowable<String> getTeaacher(@Query("data") JSONObject jsonObject);

}
