
import { NativeModules } from 'react-native';

const { RNPayment } = NativeModules;
export default class Payment{
    /***
     * 支付
     * @param type       1:支付宝    2,微信
     * @param orderParam 2:订单参数
     */
    static pay(type,orderInfo,successCallback,failureCallback){
        RNPayment.pay(type,orderInfo,successCallback,failureCallback)
    }
}
