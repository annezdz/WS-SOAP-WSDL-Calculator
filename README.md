# WS-SOAP-WSDL-Calculator
This WebService use the WSDL from https://www.dataaccess.com/webservicesserver/numberconversion.wso to convert numbers to word and dollars.

Show how Maven can be used to generate Java classes for a SOAP Web Service from a SOAP WSDL file.

## SOAP

SOAP stands for Simple Object Access Protocol. It is an XML based protocol to exchange messages with a Web Service. 
It primarily uses HTTP for the communication to Web services, and since it is XML based, the request and response exchanged are in XML.
Let’s understand this by a diagram below.

![](https://www.thecodejournal.tech/images/2021/01/soap.png)

Client would make the request, which will be in the form of XML data over the protocol(HTTP in most cases), 
and then server processes the request, and sends back the response, which again is in XML.

It’s like any other HTTP request-response flow, 
just that, adhering to SOAP provides clearly defines interfaces that can be called by client, and exchange between client and server has to be in XML.

## WSDL

WSDL or Web Services Description Language is the XML based description language for Web Services. 
WSDL is used to define the web service interfaces that provides information about what services are provided by the server and how can they be called.

## A typical WSDL had these sections.

- Service - Defines the Service itself
- Endpoint - Defines the Address or Connection Point for the Service
- Binding - specifies the interface and binding style and transport mechanism
- Interface - Defines the web service operation and message used to execute the operation
- Operation - is the actual SOAP method that gets called on the Server
- Message - defines the structure of the message
- Types - XML Schema of the message indicating which properties are of which type

Usually, the WSDL files are not written, but generated from the code. 
When a Web Service is configured, JAXB annotations are used to indicate the service, 
endpoint, operation, etc and the server returns the generated WSDL for the web service.

And then this WSDL is given to teams or clients who wish to communicate with the web service. 
We will be configuring this use case in this article - You already have a WSDL and now you want to make calls to the Web Service.

If you go about configuring the various classes for a WSDL yourself, then it is quite likely that you will miss something and you can spend hours 
and hours finding the bugs. So, never ever try to configure that.

Everywhere, this is accomplished with the help of tools and plugins. This not only saves you a lot of time, but it also reduces the errors.

In this project we will be using NumberConversion web service. This service has two methods as described below:

* NumberToWords - Returns the word corresponding to the positive number passed as parameter. Limited to quadrillions.
* NumberToDollars - Returns the non-zero dollar amount of the passed number.

The actual wsdl file can be obtained from here. 
You can download this file and copy it at src/main/resources/dataaccess-numberconversion.wsdl.

## About Maven CXF Plugin

CXF includes apache-cxf-plugin which can generate java artifacts from WSDL. The plugin provides wsdl2java goal that is used to generated java classes from wsdl.

## Java-EE to Jakarta-EE

Java-EE has been a prominent framework in developing enterprise applications. However, Oracle decided to move Java-EE to open source foundation, 
and Eclipse put up its hand to continue supporting it. Under the Eclipse foundation umbrella, this will be known as Jakarta-EE. 
Thus, along with other changes, this makes the javax.xml.*, javax.jws.* packages to be set to phase out and replaced with jakarta.xml.*, 
jakarta.jws.* packages.

With Java 11, the packages javax.xml.*, javax.jws.* were removed.

With Apache CXF plugin - 3.4.x version, code generated still uses javax.xml.* and javax.jws.* packages. This will change with Apache CXF plugin version 3.5+, and this is the change on cxf side to support jakarta-ee apis.

So, with Java 11, we need to additionally import jaxws-rt dependency. We are NOT using the latest 3.0.0(at the time of writing), since it has jakarta.* package structure. So, we use the next latest one - 2.3.3, which still uses javax.* package structure.

## Making SOAP Calls

Now, that our classes are generating and the build is compiling, let’s make calls to the service itself.

Let’s understand the classes that are generated.

- NumberConversion.java - Service class which abstracts away how calls are made to the service
- NumberConversionSoapType.java - Interface for defines the SOAP service methods
- NumberToDollars.java - Request class for numberToDollars operation
- NumberToDollarsResponse.java - Response class for numberToDollars operation
- NumberToWords.java - Request class for numberToWords operation
- NumberToWordsResponse.java - Response class for numberToWords response
- ObjectFactory.java - Object Factory class to create request and response classes.
