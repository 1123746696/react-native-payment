
package com.payment;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.payment.alipay.AliPayment;

public class RNPaymentModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNPaymentModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNPayment";
  }

  @ReactMethod
  public void pay(int id, ReadableMap info, Callback suc, Callback fail) {
    if (info != null && suc != null && fail != null) {
      AliPayment payment = new AliPayment(getCurrentActivity());

      payment.pay(info, suc, fail);
    } else {
      throw new IllegalArgumentException("参数不能为空!");
    }
  }
}