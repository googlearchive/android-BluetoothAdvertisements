
Android BluetoothAdvertisements Sample
===================================

Sample demonstrating how to advertise small amounts of data using the Bluetooth
Low Energy API. Also demonstrates how to scan for those Advertisements. (requires
2 devices to see full operation)

Introduction
------------

This sample demonstrates use of the Bluetooth Low Energy (BLE) [Advertisement][1] and [Scanning][2] APIs.
The functionality is split into two fragments - one for Advertising, one for Scanning.

ScannerFragment activates BLE Scanning for 5 seconds and displays a list of found devices which are advertising
using this sample. It shows the device type, Bluetooth address, and when it was last seen. User can
refresh to scan again and update the list.

AdvertiserFragment allows the user to toggle BLE Advertising of that device. It broadcasts basic
information about the device along with a UUID specific to this app so the ScannerFragment on other
devices can filter by it.

Note: A device cannot detect its own BLE advertisements. You will need two devices to see this
sample in action.

[1]:https://developer.android.com/reference/android/bluetooth/le/BluetoothLeAdvertiser.html
[2]:https://developer.android.com/reference/android/bluetooth/le/BluetoothLeScanner.html

Pre-requisites
--------------

- Android SDK v23
- Android Build Tools v23.0.0
- Android Support Repository

Screenshots
-------------

<img src="screenshots/1-main.png" height="400" alt="Screenshot"/> 

Getting Started
---------------

This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

Support
-------

- Google+ Community: https://plus.google.com/communities/105153134372062985968
- Stack Overflow: http://stackoverflow.com/questions/tagged/android

If you've found an error in this sample, please file an issue:
https://github.com/googlesamples/android-BluetoothAdvertisements

Patches are encouraged, and may be submitted by forking this project and
submitting a pull request through GitHub. Please see CONTRIBUTING.md for more details.

License
-------

Copyright 2014 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
