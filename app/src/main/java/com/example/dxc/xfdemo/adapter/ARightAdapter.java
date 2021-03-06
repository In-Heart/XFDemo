package com.example.dxc.xfdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dxc.xfdemo.R;
import com.example.dxc.xfdemo.model.StockMDL;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * @Class:
 * @Description:
 * @Author: haitaow(haitaow @ hpe.com)
 * @Date: 3/30/2018-9:02 PM.
 * @Version 1.0
 */

public class ARightAdapter extends BaseAdapter {
    private Context context;
    private HashMap<Integer, Object> list;
    private boolean isReverse;
    private DecimalFormat decimalFormat;

    public ARightAdapter(Context context, HashMap<Integer, Object> models, boolean isReverse) {
        super();
        this.context = context;
        this.list = models;
        this.isReverse = isReverse;
        decimalFormat = new DecimalFormat("#0.00");
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_right_item_a, parent, false);
            viewHold.textView1 = (TextView) convertView.findViewById(R.id.right_item_textview0);
            viewHold.textView2 = (TextView) convertView.findViewById(R.id.right_item_textview1);
            viewHold.textView3 = (TextView) convertView.findViewById(R.id.right_item_textview2);
            viewHold.textView4 = (TextView) convertView.findViewById(R.id.right_item_textview3);
            viewHold.textView5 = (TextView) convertView.findViewById(R.id.right_item_textview4);
            viewHold.textView6 = (TextView) convertView.findViewById(R.id.right_item_textview5);
            viewHold.textView7 = (TextView) convertView.findViewById(R.id.right_item_textview6);
            viewHold.textView8 = (TextView) convertView.findViewById(R.id.right_item_textview7);
            viewHold.textView9 = (TextView) convertView.findViewById(R.id.right_item_textview8);
            viewHold.textView10 = (TextView) convertView.findViewById(R.id.right_item_textview9);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        StockMDL stock;
        if (isReverse) {
            stock = (StockMDL) list.get(list.size() - 1 - position);
        } else {
            stock = (StockMDL) list.get(position);
        }
        if ((int) (stock.getOpen() * 100) == 0) {
            viewHold.textView3.setText("停牌");
            viewHold.textView1.setText(decimalFormat.format(stock.getSettlement()));//最新价
            viewHold.textView2.setText("0.00");//涨跌额
            viewHold.textView4.setText(decimalFormat.format(stock.getSettlement()));//昨收
            viewHold.textView5.setText("--");//今开
            viewHold.textView6.setText("--");//最高
            viewHold.textView7.setText("--");//最低
            viewHold.textView8.setText("--");//换手率
            viewHold.textView9.setText("--");//成交额
            viewHold.textView10.setText("--");//成交量
        } else {
            viewHold.textView1.setText(decimalFormat.format(stock.getTrade()));//最新价
            viewHold.textView2.setText(decimalFormat.format(stock.getPricechange()));//涨跌额
            viewHold.textView3.setText(decimalFormat.format(stock.getChangepercent())+"%");//涨跌幅
            viewHold.textView4.setText(decimalFormat.format(stock.getSettlement()));//昨收
            viewHold.textView5.setText(decimalFormat.format(stock.getOpen()));//今开
            viewHold.textView6.setText(decimalFormat.format(stock.getHigh()));//最高
            viewHold.textView7.setText(decimalFormat.format(stock.getLow()));//最低
            viewHold.textView8.setText(decimalFormat.format(stock.getTurnoverratio()));//换手率
            viewHold.textView9.setText(decimalFormat.format(stock.getAmount()));//成交额
            viewHold.textView10.setText(decimalFormat.format(stock.getVolume()));//成交量
        }
        if ((int) (stock.getPricechange() * 100) < 0) {
            viewHold.textView1.setTextColor(Color.GREEN);
            viewHold.textView2.setTextColor(Color.GREEN);
            viewHold.textView3.setTextColor(Color.GREEN);
        } else if ((int) (stock.getPricechange() * 100) > 0) {
            viewHold.textView1.setTextColor(Color.RED);
            viewHold.textView2.setTextColor(Color.RED);
            viewHold.textView3.setTextColor(Color.RED);
        } else {
            viewHold.textView1.setTextColor(Color.BLACK);
            viewHold.textView2.setTextColor(Color.BLACK);
            viewHold.textView3.setTextColor(Color.BLACK);}

        Double d1 = stock.getHigh();
        Double d2 = stock.getLow();
        Double d3 = stock.getOpen();
        Double d4 = stock.getSettlement();
        if ((int) (stock.getOpen() * 100) != 0) {
            if (d1.compareTo(d4) > 0) {
                viewHold.textView6.setTextColor(Color.RED);
            } else if (d1.compareTo(d4) < 0) {
                viewHold.textView6.setTextColor(Color.GREEN);
            }else {
                viewHold.textView6.setTextColor(Color.BLACK);
            }

            if (d2.compareTo(d4) > 0) {
                viewHold.textView7.setTextColor(Color.RED);
            } else if (d2.compareTo(d4) < 0) {
                viewHold.textView7.setTextColor(Color.GREEN);
            }else {
                viewHold.textView7.setTextColor(Color.BLACK);
            }

            if (d3.compareTo(d4) > 0) {
                viewHold.textView5.setTextColor(Color.RED);
            } else if (d3.compareTo(d4) < 0) {
                viewHold.textView5.setTextColor(Color.GREEN);
            }else {
                viewHold.textView5.setTextColor(Color.BLACK);
            }

        }else {
            viewHold.textView7.setTextColor(Color.BLACK);
            viewHold.textView6.setTextColor(Color.BLACK);
            viewHold.textView5.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    static class ViewHold {
        TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
    }
}
