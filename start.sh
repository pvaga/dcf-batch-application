
#!/bin/bash
# Script to start the DCF Batch Application

export DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
export JAVA_HOME=/c/installed_software/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
export MAVEN_HOME=/C/devtools/apache-maven-3.9.11
export PATH=$MAVEN_HOME/bin:$PATH

main() {
    while [ "$1" != "" ]; do
        case $1 in
            'debug')
                JAVA_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
                shift
                ;;
            *)
                break
                ;;
        esac
    done

    echo "Starting DCF Batch Application from $DIR"
    echo "Building project with Maven (skipping tests)..."
    mvn -f "$DIR/pom.xml" install -DskipTests

    JAR_FILE=$(ls ${DIR}/target/dcf-batch-application-*.jar 2>/dev/null | head -n 1)
    if [ ! -f "$JAR_FILE" ]; then
        echo "Build failed or JAR not found. Exiting."
        exit 1
    fi

    cp "$JAR_FILE" "$DIR/.dcf-batch-application-copy.jar"

    # Set the Spring profile (default to 'local' if not set)
    SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}

    echo "Running: java -Xms1g -Xmx1g $JAVA_DEBUG_OPTS -jar $DIR/.dcf-batch-application-copy.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE"
    java -Xms1g -Xmx1g $JAVA_DEBUG_OPTS -jar "$DIR/.dcf-batch-application-copy.jar" --spring.profiles.active=$SPRING_PROFILES_ACTIVE
}

main "$@"
