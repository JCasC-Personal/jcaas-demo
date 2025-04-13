import groovy.yaml.YamlSlurper

def configDir = new File("seed-jobs/config/appservice")
def yamlParser = new YamlSlurper()

configDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    def config = yamlParser.parse(file)
    config.jobs.each { jobDef ->
        def jobName = jobDef.name
        def repo = jobDef.repo
        def branch = jobDef.branch
        def scriptPath = jobDef.script_path

        pipelineJob(jobName) {
            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url(repo)
                            }
                            branches(branch)
                        }
                        scriptPath(scriptPath)
                    }
                }
            }
        }
    }
}