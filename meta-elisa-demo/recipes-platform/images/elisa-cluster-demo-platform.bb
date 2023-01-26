DESCRIPTION = "Elisa extended AGL Cluster Demo Platform image currently contains a simple cluster interface."

require recipes-platform/images/agl-cluster-demo-platform.bb

LICENSE = "MIT"

IMAGE_FEATURES:append = " \
    "

# add packages for cluster demo platform (include demo apps) here
IMAGE_INSTALL:append = " \
    packagegroup-elisa-cluster-demo-platform \
    strace \
    perf \
    stress-ng \
    "
