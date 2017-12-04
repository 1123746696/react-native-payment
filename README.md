
# react-native-payment

## Getting started

`$ npm install react-native-payment --save`

### Mostly automatic installation

`$ react-native link react-native-payment`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-payment` and add `RNPayment.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNPayment.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNPaymentPackage;` to the imports at the top of the file
  - Add `new RNPaymentPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-payment'
  	project(':react-native-payment').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-payment/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-payment')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNPayment.sln` in `node_modules/react-native-payment/windows/RNPayment.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Payment.RNPayment;` to the usings at the top of the file
  - Add `new RNPaymentPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNPayment from 'react-native-payment';

// TODO: What to do with the module?
RNPayment;
```
  