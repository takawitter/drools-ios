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
2. Add codes to Compile.fireAllRules() method so as all rules to be fired and all Java classes for the action part of rules to be generated.
2. Run compile-rule/src/main/java/jp/takawitter/drools/ios/compile/Compile
3. Open console
1. > cd path/to/compile-rule
1. > cd out
1. > cp KiePackages.cache ../../ios-sample/resources/
1. > pushd classes
1. > jar cf ../../../ios-sample/lib/rules.jar *
1. > popd
1. Refresh ios-sample
1. Add the name of classes generated at 2. as the content of pattern tag inside ios-sample/robovm.xml
1. Run ios-sample as Simulator App or Device App

## How it works

This project realizes running Drools rule on iOS using RoboVM. Two key components have following feature:
 * Drools has efficient code generation mechanism that generates and load bytecode at runtime, even at the time a rule fired.
 * RoboVM compiles Java bytecodes to iOS native code and support most of Java functions except run-time class definition.

That is, RoboVM needs classes pre-compiled while Drools generates class at run-time. So we need to ..
 1. Run Drools beforehand and fire all rules.
 2. Get bytecode Drools generated.
 1. Pass the bytecode to RoboVM to generate iOS native code of rules.

The compile-rule project do 1. and 2. and ios-sample do 3. Those projects replaces some Drools internal classes to hook Drools bytecode generation and write codes to file, or prevent run-time class creation that causes crash on iOS.
