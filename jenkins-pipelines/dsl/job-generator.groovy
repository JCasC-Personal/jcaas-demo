import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

def jobConfigsDir = new File("${WORKSPACE}/seed-jobs/config/appservice")
jobConfigsDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    println "Processing YAML: ${file.name}"
    
    // Create Yaml parser
    def yaml = new Yaml()
    def jobConfig = yaml.load(new FileReader(file))
    
    jobConfig.jobs.each { job ->
        // Define the pipeline job with the correct structure
        job(job.name) { // Use the standard job() method in Job DSL
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
                // This assumes you're using pipeline as code. If you need to define pipeline logic here, you can specify steps.
                pipeline {
                    scriptPath(job.script_path)
                }
            }
        }
    }
}
