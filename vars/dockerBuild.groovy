def dockerBuild(String imageName) {
    return {
        stage('Docker Build') {
            steps {
                script {
                    docker.build(imageName, ".")
                }
            }
        }
    }
}
