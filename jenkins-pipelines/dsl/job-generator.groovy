@Grab(group='org.yaml', module='snakeyaml', version='1.28') // Add this line to grab the SnakeYAML library

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

def jobConfigsDir = new File("${WORKSPACE}/seed-jobs/config/appservice")
jobConfigsDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    println "Processing YAML: ${file.name}"
    
    // Create Yaml parser
    def yaml = new Yaml()
    def jobConfig = yaml.load(new FileReader(file))
    
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
