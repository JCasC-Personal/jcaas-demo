import groovy.yaml.YamlSlurper

def jobConfigsDir = new File("${WORKSPACE}/seed-jobs/config/appservice")
jobConfigsDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    println "Processing YAML: ${file.name}"
    
    // Use YamlSlurper to parse the YAML file
    def yaml = new YamlSlurper().parse(file)
    
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
