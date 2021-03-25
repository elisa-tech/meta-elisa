# What is meta-elisa?

------------

The meta-elisa layer is an extension set for the Yocto-based AGL (Automotive Grade Linux) Linux Distribution.
It supports the addition of software packages for safety critical systems to the AGL distribution.

This repo should contain the Automotive Work Group cluster display demo and extend it with dummy safety functionality.
Currently it just contains the configuration and build information for the demo and links to a dummy safety app.
The source for the dummy safety functionality can be found in the [safety app repo](https://github.com/elisa-tech/wg-automotive-safety-app.).

Both the ELISA [Automotive](https://lists.elisa.tech/g/automotive) and 
[Safety Architecture](https://lists.elisa.tech/g/safety-architecture) Work Groups
maintain the meta-elisa layer.

# How to build and run the elisa demo

------------

1) Set up an AGL workspce as described in, for this readme we assume it is called 'workspace_agl', containing all the AGL meta-repos.
   More detail please ref.  : https://docs.automotivelinux.org/en/jellyfish/#0_Getting_Started/2_Building_AGL_Image/2_Downloading_AGL_Software/


2) Clone meta-elisa into the AGL workspace directory:
   ```
   cd workspace_agl
   git clone https://github.com/elisa-tech/meta-elisa.git
   ```

4) Building and rebuilding
   Source aglsetup.sh with the agl feature elisa-cluster-demo:
   ```
   source meta-agl/scripts/aglsetup.sh -f  elisa-cluster-demo
   ```
   Build the elisa-cluster-demo-platform target:
   ```
   bitbake elisa-cluster-demo-platform   (to generate the image)
   ```
   Be advised, building for the first time takes 10-20 hours depending on the machine, rebuilding around 10 minutes, in either case 100-150 GB of disc space are required.
5) Running the demo with QEMU:
   example bashscript below: Note that simulated hardware watchdog i6300 has to be activated, for the demo to work properly:
   ```
   qemu-system-x86_64 -snapshot -device virtio-net-pci,netdev=net0,mac=52:54:00:12:35:02 \
   -watchdog i6300esb \
   -netdev user,id=net0,hostfwd=tcp::2222-:22,hostfwd=tcp::2323-:23 \
   -drive file=./build/tmp/deploy/images/qemux86-64/elisa-cluster-demo-platform-qemux86-64.ext4,if=virtio,format=raw \
   -show-cursor -usb -device usb-tablet -device virtio-rng-pci -vga virtio -soundhw hda \
   -machine q35 -cpu kvm64 -cpu qemu64,+ssse3,+sse4.1,+sse4.2,+popcnt -enable-kvm -m 4096  -smp 4 -m 2048 \
   -serial mon:stdio -serial null -kernel ./build/tmp/deploy/images/qemux86-64/bzImage \
   -append 'root=/dev/vda rw  console=ttyS0 mem=4096M ip=dhcp oprofile.timer=1 console=ttyS0,115200n8 quiet '
   ```

# Contributing

------------

If you are interested in what ELISA does, please join the appropriate [General or Work Group mailing lists](https://lists.elisa.tech/).

To contribute to meta-elisa, please send GitHub pull requests to [this repository](https://github.com/elisa-tech/meta-elisa)
or the [Automotive Work Group safety app repository](https://github.com/elisa-tech/wg-automotive-safety-app), as appropriate.



# Maintainers

------------

|  Name  |  GitHub  |
| :---- | :---- |
|  Jochen Kall  |  https://github.com/Jochen-Kall  |
|  Gabriele Paoloni |  https://github.com/gabpaoloni  |
|  Naoto Yamaguchi  |  https://github.com/AGLExport  |



