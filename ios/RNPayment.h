
#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif
#import <UIKit/UIKit.h>
@interface RNPayment : NSObject <RCTBridgeModule>
//支付宝在appdelegate 中的回调
+(void)aliPayCallBack:(NSDictionary *)resultDic;
+(void)application:(UIApplication *)application handleOpenURL:(NSURL *)url;
@end
  
