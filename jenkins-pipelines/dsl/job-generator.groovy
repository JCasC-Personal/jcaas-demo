import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml

def jobConfigsDir = new File("${WORKSPACE}/seed-jobs/config/appservice")
println "Job Configs Directory: ${jobConfigsDir}"

jobConfigsDir.eachFileMatch(~/.*\.ya?ml/) { file ->
    println "Processing YAML: ${file.name}"

    try {
        // Create Yaml parser
        def yaml = new Yaml()
        def jobConfig = yaml.load(new FileReader(file))
        
        // Log the parsed YAML content for debugging
        println "Parsed YAML Content: ${jobConfig}"
        
        jobConfig.jobs.each { job ->
            println "Processing Job: ${job.name}"
            // Log job details to check if all values are correct
            println "Job Repo: ${job.repo}"
            println "Job Branch: ${job.branch}"
            println "Job Script Path: ${job.script_path}"

            // Define the job correctly using the 'job' method from Job DSL
            job(job.name) { 
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
                    // Log the script path being used in the pipeline step
                    println "Using script path for pipeline: ${job.script_path}"
                    
                    pipeline {
                        scriptPath(job.script_path)
                    }
                }
            }
        }
    } catch (Exception e) {
        // Log the exception to understand any errors in parsing or job creation
        println "Error processing YAML file: ${file.name}"
        println "Exception: ${e.getMessage()}"
        e.printStackTrace()  // This will print the full stack trace for debugging
    }
}
