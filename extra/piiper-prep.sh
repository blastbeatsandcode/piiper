#!/bin/bash
# Download and automatically enable USB tethering with RaspberryPi

# Download files
wget 'https://raw.githubusercontent.com/zeivhann/piiper/master/extra/auto-usb-tethering'
wget 'https://raw.githubusercontent.com/zeivhann/piiper/master/extra/piip-install'

# Run scripts
bash auto-usb-tethering
bash piip-install

# Remove script files
rm auto-usb-tethering && rm piip-install

# Delete this file
rm -- "$0"