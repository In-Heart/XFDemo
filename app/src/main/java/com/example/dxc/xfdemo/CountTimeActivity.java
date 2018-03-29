package com.example.dxc.xfdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dxc.xfdemo.common.BaseActivity;
import com.example.dxc.xfdemo.model.TimeCountMdl;
import com.example.dxc.xfdemo.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Class: CountTimeActivity
 * @Description: 模仿秒表计次功能；
 * @Author: haitaow(haitaow@hpe.com)
 * @Date: 3/28/2018-5:12 PM.
 * @Version 1.0
 */

public class CountTimeActivity extends BaseActivity implements View.OnClickListener {
    TextView tvTime, tvIntervalTime;
    Button btStart, btCount, btReset;
    RecyclerView rvCountTime;
    int timeCount = 0, intervalTimeCount = 0;
    Handler handler;
    List<TimeCountMdl> countedTimesList;
    TimeAdapter timeAdapter;
    boolean isStoped = false;
    Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentLayout(R.layout.activity_count_time_layout);
        setTitle("秒表");
        setBackVisible(true, "返回");
        setSettingVisible(false, "");

        initViews();
    }

    private void initViews() {
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvIntervalTime = (TextView) findViewById(R.id.tv_interval_time);
        btStart = (Button) findViewById(R.id.bt_start);
        btCount = (Button) findViewById(R.id.bt_count);
        btReset = (Button) findViewById(R.id.bt_reset);
        rvCountTime = (RecyclerView) findViewById(R.id.rv_times);

        btStart.setOnClickListener(this);
        btCount.setOnClickListener(this);
        btReset.setOnClickListener(this);

        btCount.setEnabled(false);
        btReset.setEnabled(false);

//        if (isStoped) {
//            btCount.setEnabled(false);
//            btStart.setText("暂停");
//        } else {
//            btCount.setEnabled(true);
//            if (timeCount > 0) {
//                btStart.setText("计时中");
//            }else {
//                btStart.setText("开始");
//            }
//        }

        handler = new Handler();
        timer = new Timer();

        countedTimesList = new ArrayList<>();
//        Collections.reverse(countedTimesList);
        timeAdapter = new TimeAdapter(CountTimeActivity.this, countedTimesList);
        rvCountTime.setLayoutManager(new LinearLayoutManager(CountTimeActivity.this, LinearLayoutManager.VERTICAL, true));
        ViewGroup.LayoutParams layoutParams = this.rvCountTime.getLayoutParams();
        int showSize = countedTimesList.size();
        layoutParams.height = showSize * ScreenUtil.dp2px(CountTimeActivity.this, 60);
        this.rvCountTime.setLayoutParams(layoutParams);

        rvCountTime.setAdapter(timeAdapter);
    }

    @Override
    public void onSettingClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                if (!isStoped) {
//                    handler.postDelayed(runnable, 100);
//                    handler.postDelayed(runnable, 25);
//                    if(timer == null){
                    timer = new Timer();
//                    }
                    timer.schedule(new MyTask(), 10, 10);

                    isStoped = true;
                    btStart.setText("计时中");
                    btCount.setEnabled(true);
                    btReset.setEnabled(true);
                } else {
//                    handler.removeCallbacks(runnable);
//                    if (timer != null) {
                    timer.cancel();//暂停
//                    }
                    isStoped = false;
                    btStart.setText("暂停");
                    btCount.setEnabled(false);
                }
                break;
            case R.id.bt_reset:
                //TODO
//                handler.removeCallbacks(runnable);
                timeCount = 0;
                intervalTimeCount = 0;
                countedTimesList.clear();
                timeAdapter.notifyDataSetChanged();

//                if (timer != null) {
                timer.cancel();//暂停
//                }
                tvTime.setText("00:00.00");
                tvIntervalTime.setText("00:00.00");
                btStart.setText("开始");
                btCount.setEnabled(false);
                btReset.setEnabled(false);
                isStoped = false;
                break;
            case R.id.bt_count:
                //TODO
                Message message = new Message();
                message.what = 2;
                timeHandler.sendMessage(message);
                break;
            default:
                break;
        }
    }

    /**
     * Runnable接口实现定时
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            handler.postDelayed(runnable, 100);
            handler.postDelayed(runnable, 25);
            Message message = new Message();
            message.what = 1;
            timeHandler.sendMessage(message);
        }
    };

    /**
     * Task和Timer实现定时
     */
    private class MyTask extends TimerTask {

        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            timeHandler.sendMessage(message);
        }
    }


    @SuppressLint("HandlerLeak")
    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    timeCount++;
                    intervalTimeCount++;
//                    mSecond = timeCount % 9;
//                    second = (timeCount / 10) % 60;
//                    minute = timeCount / 600;
//                    mSecond = timeCount % 100 ;
//                    second = (timeCount / 100) % 60;
//                    minute = timeCount / 6000;
//                    tvTime.setText(setTime(minute, second, mSecond));
//                    tvIntervalTime.setText(setTime(intervalTimeCount / 6000, (intervalTimeCount / 100) % 60, intervalTimeCount % 100));
                    tvTime.setText(getStringTime(timeCount));
                    tvIntervalTime.setText(getStringTime(intervalTimeCount));
                    break;
                case 2:
//                    intervalTimeCount = timeCount - intervalTimeCount;
//                    countedTimesList.add(new TimeCountMdl(setTime(minute, second, mSecond),
//                            setTime(intervalTimeCount / 6000, (intervalTimeCount / 100) % 60, intervalTimeCount % 100)));
//                    timeAdapter.notifyDataSetChanged();
                    countedTimesList.add(new TimeCountMdl(getStringTime(timeCount), getStringTime(intervalTimeCount)));
                    if (countedTimesList.size() > 0) {
                        timeAdapter.notifyItemInserted(countedTimesList.size() - 1);
                        ((LinearLayoutManager) rvCountTime.getLayoutManager()).scrollToPositionWithOffset(countedTimesList.size() - 1, 0);
                    }
//                    intervalTimeCount = timeCount;
                    intervalTimeCount = 0;
                    break;
                default:
                    break;
            }
        }
    };

    private class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
        private Context context;
        private List<TimeCountMdl> times;

        public TimeAdapter(Context context, List<TimeCountMdl> timeList) {
            this.context = context;
            this.times = timeList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_time_count, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvCount.setText("计次" + (position + 1));
            holder.tvTime.setText(times.get(position).getTime());
            holder.tvInterval.setText("间隔 " + times.get(position).getIntervalTime());
        }

        @Override
        public int getItemCount() {
            return times.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvCount, tvTime, tvInterval;

            public ViewHolder(View itemView) {
                super(itemView);
                tvCount = (TextView) itemView.findViewById(R.id.tv_count);
                tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                tvInterval = (TextView) itemView.findViewById(R.id.tv_interval);
            }
        }
    }

    /**
     * 返回格式为“MM:SS:mm”格式的时间
     *
     * @param minute  分
     * @param second  秒
     * @param mSecond 毫秒
     * @return “分钟：秒：毫秒”
     */
    private String getStringTime(int minute, int second, int mSecond) {
        String sMinute = "";
        String sSecond = "";
        String sMsecond = "";
        if (minute < 10) {
            sMinute = "0" + minute;
        } else if (minute == 0) {
            sMinute = "00";
        } else if (minute >= 60) {
            sMinute = minute / 60 + ":" + minute % 60;
        } else {
            sMinute = minute + "";
        }

        if (second < 10) {
            sSecond = "0" + second;
        } else if (second == 0) {
            sSecond = "00";
        } else {
            sSecond = second + "";
        }

        if (mSecond < 10) {
            sMsecond = "0" + mSecond;
        } else if (mSecond == 0) {
            sMsecond = "00";
        } else {
            sMsecond = mSecond + "";
        }
        return sMinute + ":" + sSecond + "." + sMsecond;
    }

    /**
     * 返回格式为“MM:SS:mm”格式的时间
     *
     * @param timeCount 总的计数
     * @return “分钟：秒：毫秒”
     */
    private String getStringTime(int timeCount) {
        int mSecond = timeCount % 100;// 计算毫秒
        int second = (timeCount / 100) % 60;//计算秒
        int minute = timeCount / 6000;//计算分钟

        Log.e("时间:timeCount", timeCount + "");
        Log.e("时间:mSecond", mSecond + "");
        Log.e("时间:second", second + "");
        Log.e("时间:minute", minute + "");

        String sMinute = "";
        String sSecond = "";
        String sMsecond = "";
        if (minute < 10) {
            sMinute = "0" + minute;
        } else if (minute == 0) {
            sMinute = "00";
        } else if (minute >= 60) {
            sMinute = minute / 60 + ":" + minute % 60;
        } else {
            sMinute = minute + "";
        }

        if (second < 10) {
            sSecond = "0" + second;
        } else if (second == 0) {
            sSecond = "00";
        } else {
            sSecond = second + "";
        }

        if (mSecond < 10) {
            sMsecond = "0" + mSecond;
        } else if (mSecond == 0) {
            sMsecond = "00";
        } else {
            sMsecond = mSecond + "";
        }

        return sMinute + ":" + sSecond + "." + sMsecond;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
