import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Paths

def yaml = new Yaml()
def jobFiles = new File("/var/jenkins_home/seed-jobs/config/appservice").listFiles()

jobFiles.each { file ->
    def config = yaml.load(Files.newInputStream(Paths.get(file.getAbsolutePath())))

    config.jobs.each { job ->
        job(job.name) {
            description("Generated job from YAML")
            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url(job.repo)
                            }
                            branch(job.branch)
                        }
                        scriptPath(job.script_path)
                    }
                }
            }
        }
    }
}
