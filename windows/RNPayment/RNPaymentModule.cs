using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Payment.RNPayment
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNPaymentModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNPaymentModule"/>.
        /// </summary>
        internal RNPaymentModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNPayment";
            }
        }
    }
}
