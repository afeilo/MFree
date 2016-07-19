package com.xiefei.openmusicplayer.API;

import com.xiefei.openmusicplayer.entity.SongMenu;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xiefei on 16/7/10.
 */
public interface BaiduService {
    @GET("/v1/restserver/ting?method=baidu.ting.diy.gedan")
    Call<SongMenu> getSongMenus(@Query("page_size") int pageSize,@Query("page_no")int pageNo);
    @GET("/v1/restserver/ting?method=baidu.ting.diy.gedanInfo")
    Call<SongMenuInfo> getSongMenuInfo(@Query("listid") String listId);
}
