DESCRIPTION = "Elisa extended AGL Cluster Demo Platform image currently contains a simple cluster interface."

require recipes-platform/images/agl-cluster-demo-platform.bb

LICENSE = "MIT"

IMAGE_FEATURES_append = " \
    "

# add packages for cluster demo platform (include demo apps) here
IMAGE_INSTALL_append = " \
    packagegroup-elisa-cluster-demo-platform \
    "

