#logger
[![](https://jitpack.io/v/kukuhyoniatmoko/logger.svg)](https://jitpack.io/#kukuhyoniatmoko/logger) [![CircleCI](https://circleci.com/gh/kukuhyoniatmoko/logger.svg?style=shield&circle-token=:circle-ci-badge-token)](https://circleci.com/gh/kukuhyoniatmoko/logger)


##Install
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.kukuhyoniatmoko:logger:1.0.0'
}
```
##Usage
1. Subclass Logger
```java
class MyLogger extends Logger {
  @Override public void println(Priority priority, @Nullable String tag, @Nullable String message) {
    //print the log ...
  }
}
```
2. Add Logger to Log
```java
Log.add(MyLogger());
```
3. Start Logging
```java
Log.d(exception, "Very bad error!");

Log.tag("MyTag").d(exception, "Error!");
```
You can subclass [DebugLogger](https://github.com/kukuhyoniatmoko/logger/blob/master/src/main/kotlin/com/foodenak/logger/DebugLogger.kt) which automatically use caller class name as tag
