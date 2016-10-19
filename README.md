# stomt Java-SDK [![Build Status](https://travis-ci.org/stomt/stomt-java-sdk.svg?branch=master)](https://travis-ci.org/stomt/stomt-java-sdk) [![Stomt API](https://img.shields.io/badge/stomt-v2.1.X-brightgreen.svg)](https://rest.stomt.com/) [![Current Version](https://img.shields.io/badge/version-beta-blue.svg)](https://github.com/stomt/sdk-java)

Java-based SDK for the [stomt API](https://rest.stomt.com/) to support the integration in Android applications and any other Java based systems. Our SDK allows you to add the feedback solution [www.stomt.com](https://www.stomt.com/) to your Android or Java-based app. 

## Installation

To install the stomt-java-SDK you have multiple choices.

### Maven


### Manually

Done!

## Configuration

## Documentation

###Common Usages

####Create an anonym stomt
The most common action while using the SDK is to send a Stomt.
```Java
// Create a stomt client object with your assigned application id (https://www.stomt.com/dev/apps).
StomtClient stomtClient = new StomtClient("YOUR APPID");
// Use the method `` `createAnonymStomt(...)` ``. This method is overloaded and can be used with different arguments.
// The following example only uses these arguments: boolean positive(“I wish”: positive=false and “I like”: positive=true), String targetID, String stomtText.
stomtClient.createStomt(true, "sdk", "What I like about the target");
```

####Create a non-anonym stomt
The most common action while using the SDK is to send a Stomt.
```Java
// Create a stomt client object with your assigned application id (https://www.stomt.com/dev/apps).
StomtClient stomtClient = new StomtClient("YOUR APPID");
// Login to your existing stomt account.
stomtClient.login("YOUR USERNAME", "YOUR PASSWORD");
// Use the method `` `createStomt(...)` ``. This method is overloaded and can be used with different arguments.
// The following example only uses these arguments: boolean positive(“I wish”: positive=false and “I like”: positive=true), String targetID, String stomtText.
stomtClient.createStomt(false, "sdk", "What I wish for the target"");
```
For more examples check out the JUnit test cases (src/test/java) - there are examples for all implemented methods.

## Contribution

We would love to see you contributing to our Java SDK. Feel free to fork it and we're also awaiting your pull-requests! Please visit the [project on stomt](https://www.stomt.com/sdk) to support with your ideas and wishes.

## Author

[Christoph Weidemeyer](https://github.com/ChrisWe)

## More about stomt

* On the web [www.stomt.com](https://www.stomt.com)
* [stomt for iOS](http://stomt.co/ios)
* [stomt for Android](http://stomt.co/android)
