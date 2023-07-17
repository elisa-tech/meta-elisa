# What is meta-elisa?

------------

The meta-elisa layer is an extension set for the Yocto-based AGL (Automotive Grade Linux) Linux Distribution.
It supports the addition of software packages for safety critical systems to the AGL distribution.

This repo should contain the Automotive Work Group cluster display demo and extend it with dummy safety functionality.
Currently it just contains the configuration and build information for the demo and links to a dummy safety app.
The source for the dummy safety functionality can be found in the 
[safety app repo](https://github.com/elisa-tech/wg-automotive-safety-app).

Both the ELISA [Automotive](https://lists.elisa.tech/g/automotive) and 
[Safety Architecture](https://lists.elisa.tech/g/safety-architecture) Work Groups
maintain the meta-elisa layer.

# How to build and run the ELISA Cluster demo

There are three different ways to optain an image of the cluster demo:
1. Prebuild Image
   - if you just want to run the demo without building  
   [click here for the instructions.](#accessing-prebuild-images)
2. Build via Docker container
   - if you want to build the demo  
   [click here for the instructions.](https://github.com/elisa-tech/wg-automotive/tree/master/Docker_container)
3. Manual Build
   - if the docker approach does not work for you or you want to get to know the build process  
   [click here for the instructions.](#manual-build)

## Accessing Prebuild Images

Prebuild images of the demo are available in the [meta-elisa-ci package registry](https://gitlab.com/elisa-tech/meta-elisa-ci/-/packages/13411445).
To download and extract the most recent image execute the following steps:
```sh
> wget https://gitlab.com/elisa-tech/meta-elisa-ci/-/package_files/80033203/download -O elisa-automotive-wg-demo.tar.gz
> tar -xvf elisa-automotive-wg-demo.tar.gz
> gunzip elisa-automotive-wg-demo/*
```
The `elise-automotive-wg-demo` directory now contains a kernel image `bzImage` as well as a file system image `.ext4`.
Given this, you can now run the demo. [Click here for the instructions.](#running-the-demo)

In case you would like to know more about the CI that has generated this image, reference [this blog post](https://elisa.tech/blog/2023/04/05/elisa-ci-enablement-automation-tools-for-easier-collaboration/).

## Manual Build
**NOTE:** Following the agl installation instructions' terminology (see below),
the build environment will be installed in the current AGL_RELEASE directory of new workspace named AGL_TOP.
That is, in
	```
	~/whatever/AGL_TOP/AGL_RELEASE.
	```
Currently, that would be
	```
	~/whatever/AGL_TOP/needlefish.
	```
The working directory for a command will be denoted with an
	**```
	AGL_RELEASE/working_directory>
	```**
prompt.

0) Start with a stable [supported Linux distribution](https://docs.yoctoproject.org/ref-manual/system-requirements.html#supported-linux-distributions).
Consider creating a stable environment with a chroot or virtualisation solution if the Linux distribution being used is a rolling release version.
Ensure the packages required for your distribution have been installed (reference yocto's
[build host requirements](https://docs.yoctoproject.org/ref-manual/system-requirements.html#required-packages-for-the-build-host) page).
Depending on the distribution, the following packages may also have to be installed: curl, python-is-python3, tree

1) The AGL gerrit site requires a git user name and e-mail address.  Should they not be set, execute the following commands.
	<pre><code>
	<b>AGL_RELEASE></b> git config --global user.name "Firstname Lastname"
	<b>AGL_RELEASE></b> git config --global user.email "YourId@YourEmailProvider"
	</code></pre>

	Configure the build tools and install the source according to the
	[AGL instructions](https://docs.automotivelinux.org/en/needlefish/#0_Getting_Started/2_Building_AGL_Image/2_Downloading_AGL_Software/).

2) Clone meta-elisa into the workspace.  Note that the meta-elisa.git must be cloned from a forked GitHub repo,
	not the ELISA meta-elisa (this) repo, in order to issue pull requests to the ELISA meta-elisa.
	Refer to this
	[GitHub Workflow Summary](https://gist.github.com/Chaser324/ce0505fbed06b947d962) for details.
	<pre><code>
	<b>AGL_RELEASE></b> git clone https://github.com/elisa-tech/meta-elisa.git
	</code></pre>

3) For building and rebuilding, the shell enviroment must be set.
	<pre><code>
	<b>AGL_RELEASE></b> source meta-agl/scripts/aglsetup.sh -f elisa-cluster-demo
	</code></pre>

4) [Optional, but recommended] Configure the build to utilize ELISA's sstate mirror for prebuild packages.
	<pre><code>
	<b>AGL_RELEASE/build></b> echo 'SSTATE_MIRRORS += "file://.* https://elisa-builder-00.iol.unh.edu/sstate/needlefish/PATH"' >> config/local.conf
	</code></pre>
	Using the sstate can reduce the build time considerably.

5) Build the elisa-cluster-demo-platform target.
	<pre><code>
	<b>AGL_RELEASE/build></b> bitbake elisa-cluster-demo-platform   (to generate the image)
	</code></pre>
	Be advised, building for the first time takes 10-20 hours depending on the machine, rebuilding around 10 minutes.
	In either case 100-150 GB of disc space are required.


## Running the Demo

1) To run the demo with QEMU: Refer to the 
	[AGL instruction](https://docs.automotivelinux.org/en/needlefish/#0_Getting_Started/2_Building_AGL_Image/5_1_x86_Emulation_and_Hardware/#3-deploying-the-agl-demo-image)
	to install the distribution's qemu package and set the runtime envrironment.

	Contrary to those instructions, the demo uses the following shell command rather than calling runqemu.
	Note that simulated hardware watchdog i6300 must be activated for the demo to work properly.
	Also note that if you have not build the image yourself, you need to change the path of the `-drive` flag to point to the `.ext4` file and the the path of the `-kernel` flag to point to the `bzImage` file.
	<pre><code>
	<b>AGL_RELEASE></b> qemu-system-x86_64 -snapshot -device virtio-net-pci,netdev=net0,mac=52:54:00:12:35:02 \
	-device i6300esb \
	-netdev user,id=net0,hostfwd=tcp::2222-:22,hostfwd=tcp::2323-:23 \
	-drive file=./build/tmp/deploy/images/qemux86-64/elisa-cluster-demo-platform-qemux86-64.ext4,if=virtio,format=raw \
	-usb -device usb-tablet -device virtio-rng-pci -vga virtio -soundhw hda \
	-machine q35 -cpu kvm64 -cpu qemu64,+ssse3,+sse4.1,+sse4.2,+popcnt -enable-kvm -m 4096  -smp 4 -m 2048 \
	-serial mon:stdio -serial null -kernel ./build/tmp/deploy/images/qemux86-64/bzImage \
	-append 'root=/dev/vda rw  console=ttyS0 mem=4096M ip=dhcp oprofile.timer=1 console=ttyS0,115200n8 quiet '
	</code></pre>
	
2) Interacting with the Demo:
	The demo offers an interface via a named pipe to trigger safe state from within the safety signal source, or corrupt the communication between signal source and safety app to test the mechanisms.
	Alternatively to writing to the pipe directly, an ncurses based control panel can be used as more convenient alternative.
	To access the control panel, log into the running QEMU instance as user "root" (no password needed) and start the control panel application 
	```
	Signalsource-control-panel
	```

# Troubleshooting

There is a [Troubleshooting wiki page](https://github.com/elisa-tech/meta-elisa/wiki/Troubleshooting) in this repo listing known warnings and potential hurdles when interacting with the demo. The provided information enhances the documentation. As it will not impact majority of users, they are listed there outside of the formal meta-elisa repo. If you face any issue or bug not listed there, consider to create a [new issue](https://github.com/elisa-tech/meta-elisa/issues/new/choose).

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



