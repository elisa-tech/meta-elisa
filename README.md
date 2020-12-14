# What is meta-elisa?

------------

The meta-elisa layer is a extension set for the Yocto based Linux Distribution AGL(Automotive Grade Linux), which allows adding software packages for safety critical systems.

This layer is maintained by the elisa automotive work group and elisa safety architecture work group.


# How to build elisa demo

------------

1) cd workspace_agl  (assuming 'workspace_agl' is the directory where AGL demo is cloned)  
   More detail please ref.  : https://docs.automotivelinux.org/en/jellyfish/#0_Getting_Started/2_Building_AGL_Image/2_Downloading_AGL_Software/

2) git clone https://github.com/elisa-tech/meta-elisa.git

3) source meta-agl/scripts/aglsetup.sh -f  elisa-cluster-demo

4) bitbake elisa-cluster-demo-platform   (to generate the image)


# Contribute

------------

If you are interested the work ELISA does, please join our mailing list "https://lists.elisa.tech/".

If you want to contribute to meta-elisa, please send your patches to "https://github.com/elisa-tech/meta-elisa" by GitHub pull requests.



# Maintainers

------------

|  Name  |  GitHub  |
| :---- | :---- |
|  Jochen Kall  |  https://github.com/Jochen-Kall  |
|  Gabriele Paoloni |  https://github.com/gabpaoloni  |
|  Naoto Yamaguchi  |  https://github.com/AGLExport  |



