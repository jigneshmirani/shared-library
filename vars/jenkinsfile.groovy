import org.Constants

def call() {
 pipeline {
    agent any
    //triggers {
	//pollSCM('H/2 * * * *') 
    //}
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        jdk "JDK"
        maven "Maven"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
		    git poll: true, url:'https://github.com/jigneshmirani/Service-User.git', branch:"${env.BRANCH_NAME}"

                // Run Maven on a Unix agent.
                sh "mvn clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
               
                always
                {
			
			script{
				try {
					def var=10/0
					//sh "mvn clean package"
				    } catch (Exception e) {
					println("ERROR : " + e.getMessage())
					 echo "Branch Name : ${env.BRANCH_NAME}"
					//echo SendMail()
					echo "QA SERVER : " + Constants.QA_SERVER
					// mail to: 'jignesh.mirani@onlinepsbloans.com', subject: 'Failure!', body: "Build failed: ${e.getMessage()}"
					mail bcc: '', body: '' + SendFailureMail(e.getMessage()) , cc: '', charset: 'UTF-8', mimeType: 'text/html', replyTo: '', subject: "Build Status ${currentBuild.currentResult}: Project name -> ${env.JOB_NAME}", to: "jignesh.mirani@onlinepsbloans.com"
					//mail to: 'dest@domain', subject: "Failure of Jenkins", body: e.getMessage()+"\nTry harder the next time."
					//error(e.getMessage())
					//emailext attachLog: true, body: '' + SendMail() ,    mimeType: 'text/html', replyTo: '', subject: "Build Status ${currentBuild.currentResult}: Project name -> ${env.JOB_NAME}", to: "jignesh.mirani@onlinepsbloans.com"  // this commnad used to send logfile as attachemnt but this wont allow to use CC and BCC
				    }
				
			}
			       
			
                    //echo mailBody()
                    //mail bcc: '', body: '' + SendMail() , cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "Build Status ${currentBuild.currentResult}: Project name -> ${env.JOB_NAME}", to: "jignesh.mirani@onlinepsbloans.com"
			
                }
            }
         }
    }
}
}
