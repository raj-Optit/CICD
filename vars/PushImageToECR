def ImageToECR(awsRegion, awsAccountId, imageName, imageTag) {

    sh "aws ecr get-login-password --region ${awsRegion} | docker login --username AWS --password-stdin ${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com"
    sh "docker tag ${imageName}:${imageTag} ${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com/${imageName}:{imageTag}"
    sh "docker push ${awsAccountId}.dkr.ecr.${awsRegion}.amazonaws.com/${imageName}:{imageTag}"
}

return this
