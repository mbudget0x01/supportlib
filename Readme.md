# README

![Publishing last Push](https://github.com/mbudget0x01/supportlib/actions/workflows/publish-gradle.yml/badge.svg)

This is a small lib for my android applications. The purpose of this lib is to keep
the whole boilerplate code together and maintain it in a central place.
Everything should be documented using Javadoc. As there is no good solution for me to generate it,
you have to generate it yourself.

Min api is 15.

## Noteworthy Classes and Modules

## com.linusba.support.widget

Containing Classes to handle AppWidgets.
The AppWidgetCoordinator distributes callbacks to all subscribed classes.
You can send Callbacks by using AppWidgetCoordinator.sendOnPropertyChanged(context, property).

## com.linusba.support.location.source

This holds some ready to use Classes covering most use-cases. See Javadoc for mor information.