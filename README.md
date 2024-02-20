## What is this project about?

This project offers foundational Starter Modules for Spring Boot, enabling the quick setup of services needed by various modules

<p>
  <a href="https://mvnrepository.com/search?q=cn.iosd">
    <img alt="maven" src="https://img.shields.io/badge/maven-repository-blue?style=flat-square&logo=apachemaven">
  </a>

  <a href="https://central.sonatype.com/search?q=g%3Acn.iosd+a%3Asimple-starter">
    <img alt="maven" src="https://img.shields.io/maven-central/v/cn.iosd/simple-starter.svg?style=flat-square&logo=apachemaven">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square&logo=apache">
  </a>

  <a href="https://github.com/ok1996/ok-system-simple/releases">
    <img alt="releases" src="https://img.shields.io/github/release/ok1996/ok-system-simple.svg?style=flat-square&logo=semanticrelease">
  </a>

  <a href="https://app.codacy.com/gh/ok1996/ok-system-simple/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade">
    <img src="https://app.codacy.com/project/badge/Grade/32f59a4b8afd4035a0da527009690541"/>
  </a>
</p>

## How to get started?

Find the corresponding starter module for your needs and import the dependencies.

For example, to quickly integrate a service with a web application and integrated API documentation, import the following dependencies:

~~~
    <dependencies>
        <dependency>
            <groupId>cn.iosd</groupId>
            <artifactId>simple-starter-web</artifactId>
        </dependency>
    </dependencies>
~~~

## Core Dependencies

| Dependency           | Version             |
|----------------------|---------------------|
| Java                 | 17                  |
| Spring Boot          | 3.2.2               |
| Spring Cloud         | 2023.0.0            |
| Spring Cloud Alibaba | 2022.0.0.0          |
| Ok System Simple     | 2024.1.2.0-SNAPSHOT |

## Starter Modules

| Module Name               | Use Cases                              | Description                                                                                                                                                                    |
|---------------------------|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| simple-starter-web        | Web Application                        | SpringBoot Web, integrated API documentation, serialization format conversion, response body, global exception handler, Jackson utility class                                  |
| simple-starter-redisson   | Locks and Caching                      | Annotation usage: distributed locks, distributed idempotency, distributed rate limiter; Cacheable annotation common configurations and custom CacheName expiration time        |
| simple-starter-s3         | File Management Client                 | Initialize client, provide basic service methods                                                                                                                               |
| simple-starter-socket     | Long Polling Communication and Cluster | Cluster uses redis subscribe and broadcast mechanism, automatic standalone if no redis                                                                                         |
| simple-starter-grpc       | Remote Invocation Protocol Interface   | Custom Grpc annotation simplifies usage: client, server                                                                                                                        |
| simple-starter-datasource | Database Connection Application        | HikariCP, P6spy, auto-create database tables, generic CURD controller                                                                                                          |
| simple-starter-freemarker | Template Engine Application            | Simplify importing configuration parameters                                                                                                                                    |
| simple-starter-cloud      | Microservices Application              | Integration of common dependencies for microservices: service discovery, remote configuration center, etc.                                                                     |
| simple-starter-gateway    | Microservices Gateway                  | Microservices API gateway: integrates microservices documentation gateway                                                                                                      |
| simple-starter-encrypt    | Encryption                             | Annotations: encryption and desensitization (common templates or custom rules); Annotations: RSA encryption and decryption for request or response parameters                  |
| simple-starter-dict       | Dictionary                             | Annotation dictionary translation (supports JSON files, API calls, or custom implementations); (Service class return parameters support single fields, lists, nested entities) |
| simple-starter-email      | Email                                  | SMTP email delivery                                                                                                                                                            |
| simple-starter-package    | Build and Package                      | Custom Maven build and package logic, consolidates common scripts and executable files                                                                                         |

## Base Modules

| Module Name           | Use Cases                       | Description                                                                                         |
|-----------------------|---------------------------------|-----------------------------------------------------------------------------------------------------|
| simple-base-config    | Basic Configuration             | Automatically initialize configuration on startup, optionally generate overrides, expose interfaces |
| simple-base-generator | Code Generation                 | Generate basic project code based on database tables                                                |
| simple-base-s3        | File Storage Management Service | Provide file management service and interface                                                       |
| simple-base-dict      | Dictionary Management Service   | Dictionary management service and interface                                                         |


## Utils Modules

| Module Name          | Use Cases        | Description                                                         |
|----------------------|------------------|---------------------------------------------------------------------|
| simple-utils-jackson | Json Utilities   | Provides utility classes for Json serialization and deserialization |
| simple-utils-common  | Common Utilities | Provides commonly used and general utility classes                  |

## Basic Modules
| Module Name         | Use Cases                     | Description                                                                                                                                                                                           |
|---------------------|-------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| simple-parent       | Project Parent Management     | Used to manage common configurations and dependency versions for the project. Unified management of project build configuration, dependency versions, and plugin versions                             |
| simple-dependencies | Project Dependency Management | Used to centrally manage dependency versions for all modules in the project. Defines the required dependency versions for each module and manages them uniformly through the dependencyManagement tag |

## Thanks

Thanks to JetBrains for providing support through [OpenSourceSupport](https://jb.gg/OpenSourceSupport) .

<div>
<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.svg" width="200" height="200"/>

<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/IntelliJ_IDEA_icon.svg" width="175" height="175"/>
</div>

