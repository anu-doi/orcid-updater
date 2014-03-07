#orcid-updater
=============

Application to update researchers profiles in ORCiD.

__Please note that this currently is a proof of concept/demonstrator application.  As such there will be errors and missing functionality__

This system was developed to allow The Australian National University to generate ORCiD Id's for its' researchers and to allow researchers to add/update their publications in their ORCid profiles.
In addition it allows users to link their existing ORCiD account to ANU records.  The assumption is that if we know the researchers ORCiD Id then it will be possible to extract their publication information if desired.

##Notes on Modules

The modules are:
-  __auth__ - this module has the parts that authenticate and send data to orcid
-  __data__ - this module holds the orcid message components and the objects and parts to save to the database.
-  __metadatastores__ - this module is an ANU specific module that retrieves and shapes data to input into the orcid database.
-  __orcid__ - this module is the web application
-  __process__ - this module contains the base classes with logic around retrieval of data
-  __util__ - contains utility classes/methods

##Personal Customisation
To customise this application you will need to as a minimum perform the following steps:

1. Set your own client id and client secret in orcid.properties (found in "/auth/src/main/resources")
2. Make an equivalent of the metadatastores module and adjust the "/orcid/pom.xml" accordingly.  Specifically you will need to have your own equivalents for ObtainMSPersonInformation and ObtainMSPublicationInformation (i.e. extending ObtainPersonInformation and ObtainPublicationInformation with the fetch method) such that you can get the data from your own systems to save in the database.
You will then need to update the personOI and pubOI with the appropriate class implementations.
3. Modify ldap.properties (found in "/orcid/src/main/resources") to your own environment.
4. Modify the jsp files (found in "/orcid/src/main/webapp/jsp").  These will need to be skinned appropriately for your organisation.

##Schema
This version supports the ORCiD message schema 1.1
