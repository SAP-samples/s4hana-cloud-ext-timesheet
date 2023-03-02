# S/4HANA Cloud extensions - Quick Time Entry for SAP S/4HANA Time Recording (SAP S/4HANA Cloud SDK)

This web application showcases an extension to an SAP S/4HANA Cloud system.
It allows users to read and write their working time in a fast and efficient way.

> **NOTE:** This app is based on and explained in detail in the [Set-upGuide_Timesheet](https://help.sap.com/viewer/11a6aeea94214fae9fe26afbdb9291fc/SHIP/en-US). There, you will find more details on the end to end steps of the business event handling scenario example, e.g.:
> * Connection setup of the SAP S/4HANA Cloud system and SAP Cloud Platform (via a communication arrangement)
> * Adaption and deployment
> * Identity Provider
>
> The following README only shows a basic overview

Set-up Instructions Guide
-------------
https://help.sap.com/viewer/11a6aeea94214fae9fe26afbdb9291fc/SHIP/en-US

Requirements
-------------
1. We assume that you have access to an SAP S/4HANA Cloud system and an SAP Business Technology Platform account.

2. Install [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/index.html), [Maven](http://maven.apache.org/download.cgi) and [Git](https://git-scm.com/downloads).

3. You've downloaded and installed the Node Js Command Line Interface, which is available on the [Node JS](https://help.sap.com/docs/link-disclaimer?site=https%3A%2F%2Fnodejs.org%2Fen%2F).
4. You've downloaded and installed the service management plug-in for [Cloud Foundry tools](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html).
5. You've downloaded and installed the Cloud MTA Build Tool (MBT), which is available on SAP Build solutions for multitarget applications [MBT](https://github.com/SAP/cloud-mta-build-tool).
6. (Optional) To use an integrated development environment such as Eclipse, refer to the tutorial about how to configure an Eclipse IDE for Java development on [SAP Business Technology Platform](https://developers.sap.com/tutorial-navigator.html).


Lastly, Prepare your S/4HANA Cloud system according to the [Set-upGuide_Timesheet](https://help.sap.com/viewer/11a6aeea94214fae9fe26afbdb9291fc/SHIP/en-US).

Connect it to your S/4HANA Cloud system
---------------------------------------

In this scenario, a Java application in front with a SAPUI5 application it is used to read and write timesheet data from SAP S/4HANA Cloud System. For that purpose, we use standard, resource-based APIs of SAP S/4HANA.

To allow inbound communication to the SAP S/4HANA tenant, we need to create a communication arrangement first. The communication arrangement defines which system (communication system) and which user can call which APIs (communication scenarios). 
In this example, you create a communication arrangement and allow access to the standard Manage Workforce Timesheet API (SAP_COM_0027) using a technical user.

> **NOTE:** Please follow the steps described in the [Set-upGuide_Timesheet](https://help.sap.com/viewer/11a6aeea94214fae9fe26afbdb9291fc/SHIP/en-US) to set up a communication system, communication arrangements and communication user in your S/4HANA tenant.

## Downloading the Code

To download this project run this command.
```
git clone https://github.com/SAP-samples/timesheet-cf.git  
cd timesheet-cf
git checkout timesheet-cf
```

Or if you want to clone the single branch only:
```
git clone -b timesheet-cf --single-branch git://github.com/SAP-samples/timesheet-cf.git  
cd timesheet-cf
```


## Build it

To build this project to a deployable .mtar, run the below command in timesheet-cf folder.

```mbt build```

You can adopt the project deployment using ```mta.yaml``` file. It has  deployment details of each module.

Build Result: "../timesheet-cf/mta_archives/time-sheet_0.0.1.mtar" is created.

Deploy to SAP Business Technology Platform
----------------------------
1.	Go to the command console.
2.	Switch to the application folder (..\timesheet-cf) of the project structure. 
3.	Use the Cloud Foundry command cf login to log in to your Cloud Foundry account:
4.	Enter the API endpoint you want to connect to.
You can see this value in the API Endpoint field in your subaccount.
5.	Enter your user name (your p-user) and password.
6.	Push the mtar build applications to the cloud using this command:
```cf deploy mta_archives/time-sheet_0.0.1.mtar```
7.	you can get the application's URL using the command 	```cf apps```.


> **NOTE:** Before you can start the application, you need to create a destination first. Refer to the next section.

Create a Destination
----------------------------
Destinations are used for the outbound communication of your application to a remote system (which is, in this case, the SAP S/4HANA Cloud system). To create a destination, you enter a name, the URL of the SAP S/4HANA Cloud system, the authentication type, and some other configuration data.	Maintain the properties as follows:

Property | Value
------------ | -------------
Name | S4HANA_CLOUD
Type | http
Description | <e.g. the name of your communication arrangement>
URL | <the base URL to your S/4HANA Cloud system; note the “-api”; e.g. https://myXXXXXX-api.s4hana.ondemand.com>
Proxy type | Internet
Authentication | BasicAuthentication
User | <the user you created; e.g. TIMESHEET>
Password | \<the password you created\>


> **NOTE:** The name of the destination should be exactly “S4HANA_CLOUD”.
> The user and password depend on the communication system and user that have been created in your SAP S/4HANA Cloud system.
> The URL depends on the communication arrangement created in your SAP S/4HANA Cloud system.


Troubleshooting
------------

    In case you hit the error page, please go through the exception
    trace for more details. Exception trace can be found under SAP
    CloudPlatform Cockpit > Java applications > time-sheet-backend >
    Logging > **Default Traces** .
 
    
    **Known Exceptions:**
    ODataException connectivity : Re-check your Destinations S4HANA_CLOUD and the S/4HANA System.
    FileNotFoundException : Make sure that you don't have directory names that contain whitespaces in the project path. eg. "s4 cld ext timesheet"
    
    If you work with your [SAP Business Technology Platform Trial account](https://account.hanatrial.ondemand.com/), you must add the following 2 properties to the destination so that the connection to SAP S/4HANA Cloud works:  

      proxyHost =	proxy-trial.od.sap.biz  
      proxyPort =	8080
    


Limitations / Disclaimer
------------------------
Note: This sample code is primarily for illustration purposes and is not intended for productive usage. It solely shows basic interaction with an S/4HANA Cloud system. Topics like authentication, error handling, transactional correctness, security, caching, tests were omitted on purpose for the sake of simplicity. For detailed information on development on the SAP Cloud Platform, please consult https://www.sap.com/developer.html

How to obtain support
---------------------
SAP does not offer any official support for the sample code (see the SAP SAMPLE CODE LICENSE AGREEMENT on GitHub). However, if you have any problems, use the Issues section on the GitHub to report an incident.

### Copyright and License

Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE](LICENSE) file

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
