import groovy.yaml.YamlSlurper

def generateJobFromYaml(File yamlFile) {
    def yaml = new YamlSlurper().parse(yamlFile)
    yaml.jobs.each { job ->
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
