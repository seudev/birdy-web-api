# Birdy

An app to search musics suggestion based in a given location.

This project is splitted in two repositories:

-   [birdy-web-ui](https://github.com/seudev/birdy-web-ui)
-   [birdy-web-api](#birdy-web-api)

## Why is the name Birdy?

Birds migrate acording to the temperature also they can change their pattern of singing in different stages of live, such as during the reproductive period that also has to do with the temperature (when is hotter).

Credits for the name to [@anakrdo](https://www.instagram.com/anakrdo)

## birdy-web-api

The Web API application of the **Birdy** project, created with [Quarkus](https://quarkus.io).

This API provide music suggestions for a given city name or a coordinate.
Based in the current weather of this location is determinate a music genre,
then it will search by music suggestions associate to this genre.

This application access the [Open Weather](https://openweathermap.org) Rest API to found the current weather temperature.
The musics suggestions are obtained from the [Spotify](https://spotify.com) Rest API.

## Screenshot

![birdy-screenshot](https://user-images.githubusercontent.com/8549602/102347888-d0b74400-3f7f-11eb-9524-bf9d33032677.png)

## API keys

### Create an Open Weather API key

To access the [Open Weather's current weather API](https://openweathermap.org/current) is necessary create an API key, follow the below steps to create one:

1. Access [https://home.openweathermap.org/api_keys](https://home.openweathermap.org/api_keys);
1. Type a name in the `API key name` input;
1. Click in the `Generate` button;
1. Copy and paste the generated key in the `OPENWEATHER_APPID` environment variable.

### Create a Spotify Client

To access the [Spotify's recommendation API](https://developer.spotify.com/documentation/web-api/reference/browse/get-recommendations) is necessary create an app client, follow the below steps to create one:

1. Access [https://developer.spotify.com/dashboard](https://developer.spotify.com/dashboard);
1. Click in the `Create an app` button;
1. Type a name and description;
1. Read and check the spotify terms;
1. Click in the `Create` button;
1. Copy and paste the generated `Client ID` in the `SPOTIFY_AUTH_CLIENT_ID` environment variable;
1. Click in the `Show client secret` button;
1. Copy and paste the generated `Client Secret` in the `SPOTIFY_AUTH_CLIENT_SECRET` environment variable.

**Note**: The application will create an [access token](https://developer.spotify.com/documentation/general/guides/authorization-guide/#client-credentials-flow) automatically to access the recommendations API.

## Development

This application can be develop in any _IDE_ and any environment with **java** and **maven**,
but with [VSCode](https://code.visualstudio.com/) + [VSCode Dev Container](https://code.visualstudio.com/docs/remote/containers-tutorial) all the dev environment already is configured and ready to use inside a [Docker](https://www.docker.com) container.

### Requirements

-   [VSCode](https://code.visualstudio.com) - Recommended _IDE_
-   [VSCode Dev Container](https://code.visualstudio.com/docs/remote/containers-tutorial) - Recommended to develop inside a container
-   [Docker](https://www.docker.com) - Recommended to run the **java** inside a container
-   [Java 11](https://openjdk.java.net) - Only install it if you will not be use Docker
-   [Maven 3.6.3+](https://maven.apache.org) - Only install it if you will not be use Docker

### Settings

#### Enviroment variables

Create a `.env` file in the root project folder, with the below environments:

```env
# GIT CONFIG ##########################################################################################################
GIT_USER_EMAIL="your@email"
GIT_USER_NAME="Your name"
#
# SPOTIFY CONFIG ######################################################################################################
SPOTIFY_AUTH_CLIENT_ID=29qrmm4z715pr7me5ncj8nuijpfmxnl0
SPOTIFY_AUTH_CLIENT_SECRET=t4997oda2qarzs5konlht4tz3xlhh8av
#
# OPEN WEATHER CONFIG #################################################################################################
OPENWEATHER_APPID=vdakpvfep7m7tn7c4dfgw1g84jmi16wb
#
```

**Notes**:

-   The above environment variable values are only examples. Replace it by correct value.
-   The `GIT_*` environment variables are used to config the git inside a docker container (Only necessary it if you will use the git inside a VSCode Dev Container).

### Scripts

-   `mvn quarkus:dev` - start the Quarkus in development mode, then open [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui) from your browser;
-   `mvn test` - run the [JUnit](https://junit.org) tests;
-   `mvn clean package` - clean, compile and package the application for production usage;
-   `mvn clean compile` - compile the application;
-   `mvn clean` - clean the application;
-   `mvn formatter:format` - format the code using the [Seudev's Style Guide](https://github.com/seudev/seudev-style-guide#java) (Used automatically in the build and VSCode Dev Container);
-   `mvn formatter:validate` - validate the format the code using the [Seudev's Style Guide](https://github.com/seudev/seudev-style-guide#java);
-   `mvn quarkus-bootstrap:build-tree` - show the dependency tree;

**Note**: All this scripts are configured as [VSCode tasks](https://code.visualstudio.com/docs/editor/tasks) and `bash` alias to facilitate development.

## Production

### Requirements

-   [Java 11](https://openjdk.java.net) - Only install it if you will not be use Docker

### Settings

To run this application is **required** define the below settings. This settings and many others are defined with [Eclipse MicroProfile Config](https://github.com/eclipse/microprofile-config),
then is possible override the setting value in runtime without rebuild the application.

The below settings are store sensitive data, then we decide define it out of the code instead in the `src/main/resources/application.properties` file.
It can be defined using environment variables or Java System properties. Use the mode that is most convenient.

#### Enviroment variables

```sh
SPOTIFY_AUTH_CLIENT_ID=29qrmm4z715pr7me5ncj8nuijpfmxnl0
SPOTIFY_AUTH_CLIENT_SECRET=t4997oda2qarzs5konlht4tz3xlhh8av
OPENWEATHER_APPID=vdakpvfep7m7tn7c4dfgw1g84jmi16wb
```

#### Java System properties

```sh
-Dspotify.auth.client.id=29qrmm4z715pr7me5ncj8nuijpfmxnl0
-Dspotify.auth.client.secret=t4997oda2qarzs5konlht4tz3xlhh8av
-Dopenweather.appId=vdakpvfep7m7tn7c4dfgw1g84jmi16wb
```

**Notes**:

-   The above environment variable values are only examples. Replace it by correct value.
-   See how to create these [API keys](#api-keys)

### Build and run

This application can be packaged in different ways. The main ways are detailed below, choose one.

**Note**: If you want **debug** a _containerized app_ expose the debug port (`-p 5005:5005`) and set `true` in the `JAVA_ENABLE_DEBUG` environment variable (`-e JAVA_ENABLE_DEBUG=true`).

#### Packaged as a fast-jar

Generate a more performant version than the default `jar` type.

##### Containerized app

Execute the below command to **build** the Docker image:

```sh
docker build -f src/main/docker/Dockerfile.fast-jar -t seudev/birdy-web-api-fast-jar .
```

Execute the below command to **run** the application:

```sh
docker run -i --rm -p 8080:8080 seudev/birdy-web-api-fast-jar
```

##### Non-containerized app

Execute the below command to **build** the application:

```sh
mvn package -Dquarkus.package.type=fast-jar
```

It will be generate a `target/quarkus-app` folder. Copy all internal files and folders from that folder to the desire folder,
then execute the below command to **run** the application:

```sh
java -jar quarkus-run.jar
```

#### Packaged as a jar

Generate the default `jar` type with the dependency libraries separated in a folder.

##### Containerized app

Execute the below command to **build** the Docker image:

```sh
docker build -f src/main/docker/Dockerfile.jvm -t seudev/birdy-web-api-jvm .
```

Execute the below command to **run** the application:

```sh
docker run -i --rm -p 8080:8080 seudev/birdy-web-api-jvm
```

##### Non-containerized app

Execute the below command to **build** the application:

```sh
mvn package -Dquarkus.package.type=jar
```

It will be generate a `target/*-runner.jar` file and a `target/lib` folder. Copy it to the desire folder,
then execute the below command to **run** the application:

```sh
java -jar *-runner.jar
```

#### Packaged as an uber-jar

Generate a single `jar` file with all necessary dependencies.

##### Non-containerized app

Execute the below command to **build** the application:

```sh
mvn package -Dquarkus.package.type=uber-jar
```

It will be generate a `target/*-runner.jar` file. Copy it to the desire folder,
then execute the below command to **run** the application:

```sh
java -jar *-runner.jar
```

#### Packaged as native code

Use **GraalVM** to generate a single `binary` file with all necessary dependencies.

##### Containerized app

Execute the below command to **build** the Docker image:

```sh
docker build -f src/main/docker/Dockerfile.native -t seudev/birdy-web-api-native .
```

Execute the below command to **run** the application:

```sh
docker run -i --rm -p 8080:8080 seudev/birdy-web-api-native
```

## OpenAPI

Use the below endpoints to get the API documentation:

-   [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)
-   [http://localhost:8080/openapi](http://localhost:8080/openapi)

**Notes**:

-   This endpoints are based in the [OpenAPI](https://github.com/OAI/OpenAPI-Specification) specification.
-   This documentation is defined using the [Eclipse MicroProfile OpenAPI](https://github.com/eclipse/microprofile-open-api).

## Monitoring

Use the below endpoints to monitor this application:

-   The below endpoints are based in the [Eclipse MicroProfile Health](https://github.com/eclipse/microprofile-health) specification:

    -   [http://localhost:8080/health-ui](http://localhost:8080/health-ui)
    -   [http://localhost:8080/health](http://localhost:8080/health)
    -   [http://localhost:8080/health/live](http://localhost:8080/health/live)
    -   [http://localhost:8080/health/ready](http://localhost:8080/health/ready)

-   The below endpoints are based in the [Eclipse MicroProfile Metrics](https://github.com/eclipse/microprofile-metrics) specification:
    -   [http://localhost:8080/metrics](http://localhost:8080/metrics)
    -   [http://localhost:8080/metrics/base](http://localhost:8080/metrics/base)
    -   [http://localhost:8080/metrics/vendor](http://localhost:8080/metrics/vendor)
    -   [http://localhost:8080/metrics/application](http://localhost:8080/metrics/application)

## Licensing

**seudev/birdy-web-api** is provided and distributed under the [Apache Software License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Refer to _LICENSE_ for more information.
