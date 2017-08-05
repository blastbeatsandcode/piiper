# Piiper

This project is in tandem with the efforts of [Open Learning Exchange](https://ole.org) in setting up the RaspberryPi server via USB tethering from an Android device. It is but one leg on the greater project which will be [here](https://github.com/kylemathias/Mobile-Team-Projects).

[Current documentation](https://github.com/open-learning-exchange/take-home/issues/66) until it gets added here verbosely.

>**RaspberryPi scripts are available inside [/extra](https://github.com/zeivhann/piiper/tree/master/extra)**

<p align="center">
     <img src="https://raw.githubusercontent.com/zeivhann/piiper/master/app/src/main/res/drawable/piiper_icon.png" height="100" width="79" />
</p>

___
## :arrow_forward: Turn On Automatic USB tethering

**You will want your RaspberryPi to be configured to automatically accept USB tethering. This will enable you to headlessly use your device.**

>**NOTE**: You may also download [this script](https://sourceforge.net/projects/automate-usb-tethering-rpi/files/auto-usb-tethering/download) to automatically do these steps for you. Just ```cd``` into where you downloaded the file and run ```sudo ./auto-usb-tethering```

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

## :arrow_forward: Retrieve RaspberryPi IP

>**NOTE**: You may also download [this script](https://sourceforge.net/projects/automate-usb-tethering-rpi/files/piip-install/download) to automatically do these steps for you. Just ```cd``` into where you downloaded the file and run ```sudo ./piip-install```.

This should return proper ip address, or if the third part is left out ( the `grep` call), we can generate a list of each one:

```bash
# Creates the RPi IP and assigns it to a variable
PIIP=$(ifconfig | perl -nle 's/dr:(\S+)/print $1/e' | grep 192)

# Make the variable an environmental variable so that child shells may use it
export PIIP
```


Alternatively, create a script:

```bash
#piip.sh
PIIP=$(ifconfig | perl -nle 's/dr:(\S+)/print $1/e' | grep 192)
echo $PIIP
```

And then to make the system available on the system, we can do the following commands:
```
sudo cmod 755 piip
sudo mv /path/to/script.sh /usr/local/bin/piip
```

You can now universally use the ```piip``` command to return the proper IP address of the Pi you need for SSH. This can be very beneficial when testing.
___

## :arrow_forward: Goals
<span style="color: green">:ballot_box_with_check: Detect if USB tethering is turned ON</span>

<span style="color: green">:ballot_box_with_check: Obtain IP address of the RaspberryPi</span>

<span style="color: yellow">:white_square_button: Connect automatically to it with the push of a button via SSH</span>

<span style="color: gray">:white_square_button: Automate prescripted SSH commands to complete setup process</span>

<span style="color: gray">:white_square_button: Add more sophisticated listener for tethering detection</span>

<span style="color: gray">:white_square_button: Improve and clean up simple UI</span>


### :arrow_forward: Related Items
[OLE Take-Home App](https://github.com/open-learning-exchange/take-home)
