import groovy.json.JsonSlurper

def jobConfigsDir = new File("${WORKSPACE}/seed-jobs/config/appservice")
jobConfigsDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    println "Processing YAML: ${file.name}"
    def jobConfig = readYaml file: file.path

    jobConfig.jobs.each { job ->
        pipelineJob(job.name) {
            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url(job.repo)
                            }
                            branches(job.branch)
                        }
                        scriptPath(job.script_path)
                    }
                }
            }
        }
    }
}
