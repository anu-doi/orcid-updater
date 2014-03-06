#orcid-updater
=============

Application to update researchers profiles in orcid.

Please note that this system is currently a demonstrator/prototype application.

This is a system allows users from the Australian National University to link their Orcid Id's, create a new Orcid Record, and Add to/Update publications in Orcid.

##Personal Customisation
To customise this application you will need to as a minimum perform the following steps:

1. Set your own client id and client secret in orcid.properties (found in "/auth/src/main/resources")
2. Make an equivalent of the metadatastores module.  Specifically you will need to have your own equivalents for ObtainMSPersonInformation and ObtainMSPublicationInformation (i.e. extending ObtainPersonInformation and ObtainPublicationInformation) such that you can get the data from your own systems to save in the database.
You will then need to update the personOI and pubOI with the appropriate class implementations.
3. Modify ldap.properties (found in "/orcid/src/main/resources") to your own environment.
4. Modify the jsp files (found in "/orcid/src/main/webapp/jsp").  These will need to be skinned appropriately for your organisation.
