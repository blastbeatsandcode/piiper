# Piiper
This project is in tandem with the efforts of [Open Learning Exchange](https://ole.org) in setting up the RaspberryPi server via USB tethering from and Android device. It is but one leg on the greater project which will be [here](https://github.com/kylemathias/Mobile-Team-Projects).

[Current documentation](https://github.com/open-learning-exchange/take-home/issues/66) until it gets added here verbosely.
___
**You will want your RaspberryPi to be configured to automatically accept USB tethering.**

[Steps Taken from here](https://www.raspberrypi.org/forums/viewtopic.php?t=90728)

```
sudo nano /etc/network/interfaces
```
Add this to the end of the file to add in the entry for usb0 and allowing automatic USB tethering.

```
# Enable automatic USB tethering
allow-hotplug usb0
iface usb0 inet dhcp
```
Then restart the Pi or restart the networking service with:
```
sudo /etc/init.d/networking restart
```
Test your connection with ```ifconfig``` to check if usb0 is up and running with a valid IP and try a ```ping -c 3 google.com```.
Your RPi is now ready for automatic USB tethering.
___

## Goals
- Obtain IP address of the RaspberryPi
- Connect automatically to it with the push of a button via SSH
- Automate prescripted SSH commands to complete setup process


### Related Items
[OLE Take-Home App](https://github.com/open-learning-exchange/take-home)
