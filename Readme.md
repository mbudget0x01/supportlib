# README

[![build master](https://github.com/mbudget0x01/supportlib/actions/workflows/build-gradle.yml/badge.svg)](https://github.com/mbudget0x01/supportlib/actions/workflows/build-gradle.yml)
[![publish master](https://github.com/mbudget0x01/supportlib/actions/workflows/publish-gradle.yml/badge.svg)](https://github.com/mbudget0x01/supportlib/actions/workflows/publish-gradle.yml)


This is a small lib for my android applications. The purpose of this lib is to keep
the whole boilerplate code together and maintain it in a central place.

Min api is 15.

## Add the Library (Gradle)

1. Add the package information from the Github Package to your `build.gradle`
2. add the following repository to yours.
```groovy

repositories {
    maven {
        url = "https://maven.pkg.github.com/mbudget0x01/supportlib"
        credentials {
            username = 'YourUserName'
            //This key is public until there is another solution
            password = 'get token from pkg description'
        }
    }
}
```

## Changelog

There is a Changelog available: [Link](Changelog.md)

## Noteworthy Classes and Modules

Here ou see some classes which need explanation

### com.linusba.support.widget

Containing Classes to handle AppWidgets.
The `AppWidgetCoordinator` distributes callbacks to all subscribed classes.
You can send Callbacks by using `AppWidgetCoordinator.sendOnPropertyChanged(context, property)`.

### com.linusba.support.location.source

This holds some ready to use Classes covering most use-cases. See Javadoc for mor information.
