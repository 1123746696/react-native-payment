package com.payment.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import java.util.Map;

/**
 * Created by szy on 16/9/26.
 */
public class AliPayment {
    private static String TAG = "pay";
    private Callback sucCallback;
    private Callback failCallback;
    private Activity mContext;

    private static final int SDK_PAY_FLAG = 1;

    public AliPayment(Activity act) {
        mContext = act;
        if (act == null) {
            throw new IllegalArgumentException("不在activity中");
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
//                    Log.v(TAG, "支付结果 " + resultInfo);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000") || TextUtils.equals(resultStatus, "8000") || TextUtils.equals(resultStatus, "6004")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        sucCallback.invoke(resultStatus);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        failCallback.invoke(resultStatus);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void pay(ReadableMap info, Callback suc, Callback fail) {
        sucCallback = suc;
        failCallback = fail;
        if (suc == null || fail == null) {
            throw new IllegalArgumentException("没有处理回调!");
        }
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        if (info == null) {
            failCallback.invoke("获取订单错误!");
            return ;
        }

        final String orderInfo = info.getString("order");

        if (TextUtils.isEmpty(orderInfo)) {
            failCallback.invoke("获取订单信息错误!");
            return ;
        }

//        Log.v(TAG, "get info " + orderInfo);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i(TAG, result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
