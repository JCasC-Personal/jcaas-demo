def call(env) {
    currentBuild.displayName = "#${BUILD_NUMBER} - ${env}"
    echo "Updated build metadata for environment: ${env}"
}