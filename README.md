# MicroService Expression

## Motivation

Human cells contain a full copy of the entire dna in their nuclei.  e.g. A Skin cell has the blueprints for a red blood cell, for bone cells, for liver cells, for eye cells.

Why should a microservice artifact be any different?  i.e. Why shouldn't its deployment artifact contain the entire system's codebase?  Storage is cheap.  Can't we simply use some means of configuring an app to enable certain features and disable others?  Sort of like how our cells turn genes on and off?

## Merits

1. Package a single jar.  means simpler packaging and deployment/distribution.
2. Can flip an app instance from microservice A to microservice B without having to scale down A and scale up B.
3. Adds flexibility:  can turn an app that's one microservice into two by flipping a toggle, and vice versa.

## Description

This project is an experiment to vet whether it's possible to build a codebase that can be deployed either as a monolith or as a system of microservices *via configuration*, without altering the codebase.  I.e. a single artifact (jar) is produced and deployed irrespective of whether the running instance ends up running a single service or all the services represented in the codebase.

Beans are conditionally added to the application context if their corresponding service is enabled.

The current implementation leverages Spring profiles as a mechanism to turn a particular service on or off.

The example shows two services:  projects, and allocations.  There is a dependency from allocations to projects.  When creating an allocation, the allocation service calls the project service in order to lookup the associated project and determine if it is active.

Three profiles are exposed: projects, allocations, and monolith (both).

When deploying the monolith (spring.profiles.active=monolith), all services are turned on and in the same process, and so the calls from the allocation service to the project service are direct, in-process calls.

When deploying the same jar differently:
- once using the projects profile (spring.profiles.active=projects), only the project service is enabled, and only the project-related beans are turned on.
- once using the allocations profile (spring.profiles.active=allocations), only the allocation service is enabled, and a feign proxy/client for the project service is created and autowired into the allocation service, and service to service calls are performed using REST over http.

This "switching" from direct in-process calls to calls over REST is done transparently.  If the target service is local, the application configuration ensures that the local bean is autowired.  Conversely, when the service is remote, Spring just autowires the feign client.

The beauty of feign shines here in that both implementations of ProjectClient (ProjectService and the Feign client) implement the same interface.  So the service clients (AllocationService in this case) are non the wiser.

Services find each other via a Eureka discovery server.

There's a slight difference in how Spring MVC endpoints for a service are exposed:  the root url is preferred if the service is deployed in a stand-alone way.  If however the running process contains more than a single microservice, then each service's endpoints are namespaced.  i.e. the base url/path to the controller endpoints for the project service becomes `/projects`.

### Scenario #1:  Monolith

1. Start up a eureka server.  One way to do this is with the [Spring Cloud CLI](http://cloud.spring.io/spring-cloud-cli/single/spring-cloud-cli.html), as follows:

    ```bash
    spring cloud eureka
    ```

2. Launch the application with:

    ```bash
    SPRING_PROFILES_ACTIVE=monolith ./gradlew bootRun
    ```

3. Visit the eureka dashboard at `http://localhost:8761/`.  The application name is set to `monolith`, the port is `8080`.

4. Perform the following REST API calls (see https://httpie.org/) to create a project, look it up, and then create an allocation:

    ```bash
    http post :8080/projects name="Project A"
    http :8080/projects/1
    http post :8080/allocations projectId=1 userId=1 start=2019-01-01 end=2019-01-02
    ```

5. Attempting to create an allocation for an invalid project should return a 400 (bad request):

    ```bash
    http post :8080/allocations projectId=2 userId=1 start=2019-01-01 end=2019-01-02
    ```

6. Visit the actuator endpoints `env`, `beans`, and `mappings` to review how your application is configured and what beans are active.  Note that the ProjectController's route is `/projects`, and the AllocationController's route is `/allocations`.


### Scenario 2: MicroServices

1. Again, begin with a running eureka server.

2. Start the project service:

    ```bash
    SPRING_PROFILES_ACTIVE=projects ./gradlew bootRun
    ```

3. Start the allocations service:

    ```bash
    SPRING_PROFILES_ACTIVE=allocations ./gradlew bootRun
    ```

4. Visit the Eureka dashboard and verify that the `projects` service is registered, and running on port `8081`, and that the `allocations` service is registered, and running on port `8082`.

5. Perform the following REST API calls to create a project, look it up, and then create an allocation:

    ```bash
    http post :8081 name="Project A"
    http :8081/1
    http post :8082 projectId=1 userId=1 start=2019-01-01 end=2019-01-02
    ```

6. Attempting to create an allocation for an invalid project should return a 400 (bad request):

    ```bash
    http post :8082 projectId=2 userId=1 start=2019-01-01 end=2019-01-02
    ```

7. Visit the actuator endpoints for each application.  Note that the project service has no allocation-related beans (other than a feign client) at its disposal.  Likewise for the allocation service:  the only project-related bean is the feign client, which is autowired into the allocation service.  Also, conveniently both domain objects are in the classpath, facilitating the unmarshalling of the Json.  This last point technically could cause microservices with too strong a degree of coupling and should be explored further.

   
