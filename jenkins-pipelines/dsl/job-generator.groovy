def generateJobFromYaml(File yamlFile) {
    def jobConfig = readYaml file: yamlFile.path

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
return this
