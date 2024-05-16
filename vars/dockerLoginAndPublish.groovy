def loginAndPushDockerImage(String repository, String imageName, String credentialsId) {
    // Docker login using credentials
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
        sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"

        // Generate unique build ID
        def buildId = env.BUILD_ID
        
        // Tag and push the image with build ID
        def imageNameWithTag = "$repository/$imageName:$buildId"
        sh "docker tag $imageName $imageNameWithTag"
        sh "docker push $imageNameWithTag"

        // Tag and push the image with 'latest'
        def imageNameWithLatest = "$repository/$imageName:latest"
        sh "docker tag $imageName $imageNameWithLatest"
        sh "docker push $imageNameWithLatest"
    }
}
