# drools-ios
A simple iOS application that runs Drools rule.

* [compile-rule](https://github.com/takawitter/drools-ios/tree/master/compile-rule) generates statically compiled classes from a rule.

* [ios-sample](https://github.com/takawitter/drools-ios/tree/master/ios-sample) builds iOS application that runs compiled rule using RoboVM.

## Run Application

1. You need Eclipse and RoboVM plugin.
2. Clone https://github.com/takawitter/drools-ios.git and import projects.
3. Run ios-sample as Simulator App or Device App.

## Build your own rule

1. Edit compile-rule/src/main/resources/rule.drl
2. Run compile-rule/src/main/java/jp/takawitter/drools/ios/compile/Compile
3. Open console
1. > cd path/to/compile-rule
1. > cd out
1. > jar cf rules.jar defaultpkg
1. > cp rules.jar ../../ios-sample/lib/
1. > cp KiePackages.cache ../../ios-sample/resources/
1. Refresh ios-sample
1. Add the name of classes generated at 2. as the content of pattern tag inside ios-sample/robovm.xml
1. Run ios-sample as Simulator App or Device App
