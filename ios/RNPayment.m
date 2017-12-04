
#import "RNPayment.h"
#import <AlipaySDK/AlipaySDK.h>
@interface RNPayment ()
@property(nonatomic,copy) RCTResponseSenderBlock successCallback;
@property(nonatomic,copy) RCTResponseSenderBlock failureCallback;
@end

@implementation RNPayment

+(instancetype)shareInstance{
    static RNPayment *instance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        if (instance==nil) {
            instance=[[self alloc]init];
        }
        
    });
    return instance;
}
RCT_EXPORT_MODULE()

/***
 * 支付
 * @param type       1:支付宝    2,微信
 * @param orderParam 2:订单参数
 */
RCT_EXPORT_METHOD(pay:(NSInteger)type orderInfo:(NSDictionary *)orderInfo successCallback:(RCTResponseSenderBlock)successCallback  failureCallback:(RCTResponseSenderBlock)failureCallback){
    RNPayment *payment = [RNPayment shareInstance];
    payment.successCallback=successCallback;
    payment.failureCallback=failureCallback;
    if(type==1){
        NSArray * schemes =[[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleURLTypes"];
        NSAssert(schemes && [schemes isKindOfClass:[NSArray class]] && schemes.count>0, @"请在info.plist中增加scheme");
        
        NSString *appScheme = [[[schemes objectAtIndex:0] objectForKey:@"CFBundleURLSchemes"] objectAtIndex:0];
        // NOTE: 将签名成功字符串格式化为订单字符串,请严格按照该格式
        NSString *orderString = [orderInfo objectForKey:@"order"];
        // NOTE: 调用支付结果开始支付
        [[AlipaySDK defaultService] payOrder:orderString fromScheme:appScheme callback:^(NSDictionary *resultDic) {
            [RNPayment aliPayCallBack:resultDic];
        }];
    }else if(type==2){
        
    }
    
}
+(void)aliPayCallBack:(NSDictionary *)resultDic{
    NSLog(@"reslut = %@",resultDic);
    NSInteger resultStatus = [[resultDic objectForKey:@"resultStatus"] integerValue];
    if(resultStatus==9000||resultStatus==8000||resultStatus==6004){
        RNPayment *payment = [RNPayment shareInstance];
        if(payment.successCallback){
            payment.successCallback(@[resultDic]);
        }
        
    }else{
        RNPayment *payment = [RNPayment shareInstance];
        if(payment.failureCallback){
            payment.failureCallback(@[resultDic]);
        }
    }
}
+(void)application:(UIApplication *)application handleOpenURL:(NSURL *)url{
    if ([url.host isEqualToString:@"safepay"]) {
        //跳转支付宝钱包进行支付，处理支付结果
        [[AlipaySDK defaultService] processOrderWithPaymentResult:url standbyCallback:^(NSDictionary *resultDic) {
            [RNPayment aliPayCallBack:resultDic];
        }];
    }
}
@end
  
