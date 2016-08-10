package com.method76.faultnotification.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.method76.faultnotification.util.BaseUtil;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduledService extends Service {

    // constant
    private static final long NOTIFY_INTERVAL   = 10 * 60 * 1000; // 5 min
    private static final String URL_TEST        = "https://wallpaper-inc-method76.c9users.io/wallpapers/get/bmw+x1";

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    private Context ctx;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        ctx = this;
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new Scheduler(), 0, NOTIFY_INTERVAL);
    }

    /**
     *
     */
    class Scheduler extends TimerTask {

        private RequestQueue que = Volley.newRequestQueue(ScheduledService.this);

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // display toast
                    que.add(req);
                }
            });
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }

    }

    /**
     * Call REST API for Health check
     */
    private JsonArrayRequest req = new JsonArrayRequest(URL_TEST,
        new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray json) {
                BaseUtil.showNoti(ctx, true);
            }
        },
        new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
                BaseUtil.showNoti(ctx, false);
        }
    }
    );
}