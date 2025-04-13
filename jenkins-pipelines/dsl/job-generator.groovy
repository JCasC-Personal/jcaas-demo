import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

def jobConfigsDir = new File("${WORKSPACE}/seed-jobs/config/appservice")
jobConfigsDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    println "Processing YAML: ${file.name}"
    
    // Create Yaml parser
    def yaml = new Yaml()
    def jobConfig = yaml.load(new FileReader(file))
    
    jobConfig.jobs.each { job ->
        // Define the job correctly using the 'job' method from Job DSL
        job(job.name) {  // 'job' should be the DSL method for creating a job, not a map.
            description("This is the job for ${job.name}")
            scm {
                git {
                    remote {
                        url(job.repo)
                    }
                    branches(job.branch)
                }
            }
            triggers {
                // Define any triggers here (optional)
            }
            steps {
                // Use the pipeline step to call a Jenkinsfile or pipeline script
                pipeline {
                    scriptPath(job.script_path)
                }
            }
        }
    }
}
