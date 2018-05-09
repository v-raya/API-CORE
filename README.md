# CWDS Core API

The CWDS Core API project provides a common set of classes which are used across CWDS API Modules.

## Generating Licenses
The legal folder contains files listing the licenses for application dependencies API Core library contains several subproject each with it's own dependencies. Each subproject must generate license reports, then the individual reports are aggregated into a csv file in the root legal folder.

Each project contains a legal folder that contains a list of licenses. This list is created via a license report plugin. The plug in stores data in the build folder and copies over the csv files.

### Update license report
To generate report run:

```./gradlew  generateAllLicenseFiles libLicenseReport```

generateAllLicenseFiles updates submodules licenses
libLicenseReport creates the report file
