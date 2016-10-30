# Candid Camera
As part of the Bezirk Hackathon Fall 2016, we created Candid Camera, a home security platform that uses the Bezirk Middleware.

## What is it?
Candid Camera monitors a door to catch unsoliciated strangers by sending you a video whenever an stranger opens it.  
This technology could also be used to answer the age-old question, "[Who stole the cookies from the cookie jar?](https://en.wikipedia.org/wiki/Who_Stole_the_Cookie_from_the_Cookie_Jar%3F)"

##Advantages:
 - This Internet of Things system is significantly lower cost than other comparables.  You only need:  
   - An inexpensive Arduino/Raspberry Pi, simple reed switch, and magnet  
   - A low-power Bluetooth beacon ([Estimote](http://estimote.com/#get-beacons-section) used in this prototype; can also change the code for another proximity sensor such as RFID)
   - An Android smartphone (cost estimates assume you already have this)
   - A camera device.  With current code, this could be a Windows computer with a webcam attached; a wide range of cameras and IP cameras are supported.  In a future edition, you can replace this with an old Android device like those you can buy inexpensively on eBay, that doesn’t even need a carrier plan.  
   - Some tape or adhesive tack to hold the reed switch, camera, and/or wires (if your devices need any) in place
 - You don’t have to pay a monitoring company, and you don’t have to have the camera directly accessible from the public Internet or directly visible to monitors 24/7.
 - Fewer innocuous alarms than simpler activation systems:  You don't need alerts every time you (or others you wish to authorize) are opening or passing through the door. 
 - Home-scalable: In a more configurable future edition, you can have multiple cameras and associate each with one or more specific door sensors.  You can also have multiple cameras, and even different types of cameras running on different platforms, triggered on any specific door sensor, and add or remove (subscribe/unsubscribe) them without having to change anything on the controllers. 

###Future improvements:
 - Better UI for configuring parameters and control
 - Create a configurable authorization list (with revocation capabilities) of specific phones that count as "recognized" instead of relying solely on Wi-Fi authentication controls
 - Improved hardware (e.g. real reed switch instead of button prototype) and conversion of Arduino + serial sensor piece to wifi-enabled Raspberry Pi to send Bezirk events directly and improve the form factor as well as reduce cost.  The Arduino + serial sensor hardware simulation used here already exceeds hackathon specs allowing software simulation of devices.
 - Write a camera client for Android that uses the camera on an Android device, and/or other adapters that can process camera events.  Again, writing the adapter allowing us to control real Windows camera devices exceeded hackathon specs.
 - Enhance notifications to add options for SMS, and include a direct link to the video (which may be stored on a cloud platform like Dropbox, Google Drive, etc. accessible to only authenticated users) and/or a still image ([MMS](https://en.wikipedia.org/wiki/Multimedia_Messaging_Service) or e-mail).


