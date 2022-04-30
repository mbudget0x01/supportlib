# Changelog

Starting from Version 1.1.1

## Version 1.1.1
* fixes a bug in `location.FusedLocationSourceBuilder.build()`

## Version 1.2.0
* Added `widget.AppWidgetCoordinator`
* Added `widget.CallbackAppWidgetProvider`
* Added `widget.AppWidgetProviderUtil`
* Added `app.AppPackageUtil`
* Added some integration tests

## Version 1.2.1
* Lifted `androidx.appcompat:appcompat:1.2.0` to `androidx.appcompat:appcompat:1.3.0`
* Added Source Jar to maven package for Javadoc support
* Minor changes on the build procedure

## Version 1.2.2
* Lifted `androidx.appcompat:appcompat:1.3.0` to `androidx.appcompat:appcompat:1.3.1`
* Lifted `com.google.android.material:material:1.3.0` to `com.google.android.material:material:1.4.0`
* Lifted `androidx.test.ext:junit:1.1.2` to `androidx.test.ext:junit:1.1.3`
* Lifted `androidx.test.espresso:espresso-core:3.3.0` to `androidx.test.espresso:espresso-core:3.4.0`

## Version 1.3.0
* Replaced `RingtonePickerUtil` with `RingtonePicker` to use the ` Activity Result API` 
* Replaced `ContactPickerUtil` with `ContactPicker` to use the ` Activity Result API`

## Version 1.3.1
* Added `ContactUtil`

## Version 1.3.2
* Added `androidx.preference:preference` as dependency
* Added `IntEditTextPreference`
* Added `PhoneNumberPreference`
* Added `PhoneNumberContactPreference`

## Version 1.3.3
* Added `RingtonePreference`
* Added `RingtonePickerPreference`
* Minor Bugfix in `PhoneNumberPreference`
* Lifted `com.google.android.gms:play-services-maps:17.0.1` to `com.google.android.gms:play-services-maps:18.0.0`

## Version 1.3.4
* Updated Target SDK to 31
* Lifted `androidx.appcompat:appcompat:1.3.1` to `androidx.appcompat:appcompat:1.4.1`
* Lifted `com.google.android.material:material:1.4.0` to `com.google.android.material:material:1.5.0`
* Lifted `androidx.preference:preference:1.1.1` to `androidx.preference:preference:1.2.0`
* Lifted `com.google.android.gms:play-services-location:18.0.0` to `com.google.android.gms:play-services-location:19.0.1`
* Lifted `com.google.android.gms:play-services-maps:18.0.0` to `com.google.android.gms:play-services-maps:18.0.2`
* `FusedLocationSource` supports now optional Loopers
* `FusedLocationSourceBuilder` has now the option to run in a separate Thread

## Version 1.3.5
* Fixes an error occurring on Android 12 (S+) concerning `PendingIntent` Flags

## Version 1.3.6
* Added `RingtoneProviderS` as silent will be treated as dnd