# Candid Camera
As part of the Bezirk Hackathon Fall 2016, we created Candid Camera, a home security platform that uses the Bezirk Middleware.

## What is it?
Candid Camera monitors a door to catch unsoliciated strangers by sending you a video whenever an stranger opens it.  
This technology could also be used to answer the age-old question, "[Who stole the cookies from the cookie jar?](https://en.wikipedia.org/wiki/Who_Stole_the_Cookie_from_the_Cookie_Jar%3F)"

##Advantages:
 - This Internet of Things system is significantly lower cost than other comparables.  You only need:  
   - An inexpensive wifi-enabled Arduino/Raspberry Pi, simple reed switch (possibly also a resistor), and magnet.
   - A low-power Bluetooth beacon ([Estimote](http://estimote.com/#get-beacons-section) used in this prototype; can also change the code for another proximity sensor such as RFID).
   - An Android smartphone (cost estimates assume you already have this).
   - A camera device.  With current code, this could be a Windows computer with a webcam attached; a wide range of cameras and IP cameras are supported.  In a future edition, you can replace this with an old Android device like those you can buy inexpensively on eBay, that doesn’t even need a carrier plan.  
   - Mounting hardware such as tape, screws, or adhesive tack to hold up the door sensor, camera, and/or wires (if your devices need any), as well as wire-covering paper in a color that matches the walls for more asthetically pleasing installations.   
 - You don’t have to pay a monitoring company, and you don’t have to have the camera directly accessible from the public Internet or directly visible to monitors 24/7.
 - Fewer innocuous alarms than simpler activation systems:  You don't need alerts every time you (or others you wish to authorize) are opening or passing through the door. 
 - Home-scalable: In a more configurable future edition, you can have multiple cameras and associate each with one or more specific door sensors.  You can also have multiple cameras, and even different types of cameras running on different platforms, triggered on any specific door sensor, and add or remove (subscribe/unsubscribe) them without having to change anything on the controllers. 

###Future improvements:
 - Better UI for configuring parameters and control
 - Create a configurable authorization list (with revocation capabilities) of specific phones that count as "recognized" instead of relying solely on Wi-Fi authentication controls
 - Improved hardware (e.g. real reed switch instead of button prototype) and conversion of Arduino + serial sensor piece to wifi-enabled Raspberry Pi to send Bezirk events directly and improve the form factor as well as reduce cost.  The Arduino + serial sensor hardware simulation used here already exceeds hackathon specs allowing software simulation of devices.
 - Write a camera client for Android that uses the camera on an Android device, and/or other adapters that can process camera events.  Again, writing the adapter allowing us to control real Windows camera devices exceeded hackathon specs.
 - Enhance notifications to add options for SMS, and include a direct link to the video (which may be stored on a cloud platform like Dropbox, Google Drive, etc. accessible to only authenticated users) and/or a still image ([MMS](https://en.wikipedia.org/wiki/Multimedia_Messaging_Service) or e-mail).

------------------------
PLEASE NOTE:  This is a HACKATHON PROTOTYPE.  It is NOT yet well tested and has NO WARRANTY WHATSOEVER and you should NOT rely on it as-is.  If you try it, it’s at your own risk.
Copyright is retained by the authors.  

------------------
##PROTOTYPE INSTALLATION INSTRUCTIONS
The system has four main software components:
 1.  Door sensor: Detects and broadcasts when door opens or closes.
 2.  Smartphone: If in range and receiving event notification that “door opens,” broadcasts message indicating whether or not it was recently in the vicinity of the doorway.  
 3.  Controller: Listens for door events and starts recording; cancels recording if authorized phone sends notice it was in the vicinity of the door; stops recording a certain amount of time after the door is closed (subject to a maximum recording length in case no “door closed” signal is received within a few minutes after the “door open” event); notifies owner
 4.  Camera(s): Listens to recording instructions from controller


In a single-camera setup, it is suggested to run the controller on the same device as the camera.  In a multi-camera setup, the controller can be run on any camera.  It can also be run on the processor attached to the door sensor, if that processor has sufficient computing resources.  The controller should NOT be run on a mobile device that might be missing from the network at the time of a possible intrusion.  The software separation between controller and camera(s) permits use of multiple cameras, that may each have a different angle on the scene.  It is possible to eliminate the Bezirk interface between the controller and camera and have a separate controller for each camera, but this may lead to inconsistencies and extra notifications.

First, follow [these instructions](http://developer.bezirk.com/documentation/installation.php) for general system setup. 

### Door Sensor:
 1.  **Build the circuit.**  An Arduino schematic for the door sensor can be found [here](https://raw.githubusercontent.com/wbt/BezirkSec/master/ArduinoCode/Screen%20Shot%202016-10-29%20at%2011.18.12%20PM.png) where the switch shown should be a magnetic reed switch.  (A more detailed view of the pushbutton-switch prototyped is [here](https://raw.githubusercontent.com/wbt/BezirkSec/master/ArduinoCode/Screen%20Shot%202016-10-29%20at%209.58.07%20PM.png).  If power draw is a primary concern, "Normally Closed" switches will be open most of the time because the door will be in a "closed" state and the magnet attached to it will keep the reed switch open.  However, such switches are unable to distinguish between a closed door and a destroyed circuit, while "normally open" switches more appropriatey interpret a partially destroyed circuit similarly to "door open." 
 2.  Install [Arduino IDE](https://www.arduino.cc/en/Main/Software), if you haven't already.  This demo also uses [RXTX](http://rxtx.qbang.org/wiki/index.php/Download) for communication with the Arduino, which may require some separate installation.  Note that if you are using Windows and installing a 32-bit RXTX DLL (C:\Windows\System32\rxtxSerial.dll) that will not work with a 64-bit JVM; use a 32-bit JVM or try a 64-bit version of RXTX (potential source [here](http://jlog.org/rxtx-win.html)).
 3. Download [this file](https://github.com/wbt/BezirkSec/blob/master/ArduinoCode/SerialReadDigital/SerialReadDigital.ino) and open it in Arduino IDE.
 4. In Arduino IDE, select Tools->Board: “Arduino/Genuino Uno” (if you are following the tutorial closely and using such a board, otherwise select your board).
 5. In Arduino IDE, select Tools->Port: and pick the one with your board attached to it (e.g. "COM4" on Windows or "/dev/cu.usbmodem 1411" on Mac.)  It might say "(Arduino/Genuino Uno)" after the port name.  Make note of the port name. 
 6. Press the Upload button to upload the code to Arduino. 
 7. Download [this directory](https://github.com/wbt/BezirkSec/tree/master/DoorPublish).  In Android Studio, choose File -> New -> Import Project and select that directory on your local hard drive.
 8. Open the file "SerialTest.java" (under src/main/java) and look for the definition of `String PORT_NAMES[]`.  Change the one for your operating system to match the port you noted above. 
 9. Run the main() method of this file.  This should create a thread that monitors the door switch and sends events when the switch state changes.  
 
### Smartphone:
The smartphone software and installation instructions can be found [here](https://github.com/boyP/EstimoteAdapterBezirk).


