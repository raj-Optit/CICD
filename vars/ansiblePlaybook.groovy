def call(Map pipelineParams) {
    try {
            stage('ansible-run') {
                println pipelineParams
                dir("$pipelineParams.currentWs/private") {
                    if (!env.private_repo_url || !env.private_repo_branch || !env.private_repo_credentials) {
                        println('''\
                               Uh Oh! Please create Jenkins environment variables named
                               private_repo_url, private_repo_branch, private_repo_credentials
                               '''.stripIndent().replace("\n", " ") )
                        error 'Please resolve errors and rerun..'
                    }

                    if (params.private_branch != null && params.private_branch != "") {
                        env.private_repo_branch = params.private_branch
                        println('Info: Branch override is enabled')
                    } else
                        println('Info: Branch override is disabled')

                    checkout scm: [$class: 'GitSCM', branches: [[name: private_repo_branch]], extensions: [[$class: 'CloneOption', depth: 1, noTags: true, reference: '', shallow: true]], userRemoteConfigs: [[credentialsId: private_repo_credentials, url: private_repo_url]]]
                }

                inventory_path = "${pipelineParams.currentWs}/ansible/inventory/env"
                sh """
                        rsync -Lkr ${pipelineParams.currentWs}/private/ansible/inventory/${pipelineParams.env}/${pipelineParams.module}/* ${pipelineParams.currentWs}/ansible/inventory/env/
                        //if [ -f ${pipelineParams.currentWs}/ansible/inventory/env/kubernetes.yaml ]; then
                           // cat ${pipelineParams.currentWs}/ansible/inventory/env/kubernetes.yaml >> ${pipelineParams.currentWs}/ansible/inventory/env/common.yml
                        //fi
                        ansible-playbook -i ${inventory_path}/hosts $pipelineParams.ansiblePlaybook $pipelineParams.ansibleExtraArgs
                     """
            }
        }
    }
    catch (err){
        throw err
    }
}
