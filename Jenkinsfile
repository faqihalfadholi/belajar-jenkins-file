pipeline {
    agent any
    stages {
        stage('Preparation') {
            steps {
                sh 'chmod +x mvnw'
                echo 'Preparing the environment.......'
                sh 'java -version'
                sh './mvnw -version'
            }
        }
        stage('Environment Info') {
            steps {
                script {
                    echo "Job Name: ${env.JOB_NAME}"
                    echo "Build Number: ${env.BUILD_NUMBER}"
                    echo "Workspace: ${env.WORKSPACE}"
                    echo "Node Name: ${env.NODE_NAME}"
                }
            }
        }
        stage('Build') {
            steps {
                echo 'Building the project...'
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests........'
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Build Status') {
            steps {
                script {
                    currentBuild.displayName = "#${env.BUILD_NUMBER} - Custom Build Name"
                    echo "Current Build Status: ${currentBuild.currentResult}"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                sleep(5)
                sh 'echo "Deploying application... after 5s"'
            }
        }
        stage('Custom Groovy Step') {
            steps {
                script {
                    def message = "Custom Groovy step executed successfully!"
                    echo message

                    def jobName = env.JOB_NAME
                    def buildNumber = env.BUILD_NUMBER
                    echo "Current job: ${jobName}, Build number: ${buildNumber}"

                    def currentDate = new Date()
                    def formattedDate = currentDate.format("yyyy-MM-dd HH:mm:ss")
                    echo "Current date and time: ${formattedDate}"

                    def fileName = "build_info.txt"
                    writeFile file: fileName, text: "Build ${buildNumber} ran on ${formattedDate}"
                    echo "Created file: ${fileName}"

                    def shouldRunTests = true
                    if (shouldRunTests) {
                        echo "Running tests..."
                    } else {
                        echo "Skipping tests..."
                    }

                    try {
                        sh 'exit 1'
                    } catch (Exception e) {
                        echo "Caught exception: ${e.message}"
                    }

                    def fruits = ['apple', 'banana', 'orange']
                    fruits.each { fruit ->
                        echo "Processing fruit: ${fruit}"
                    }


                    def a = 5
                    def b = 3
                    def sum = a + b
                    echo "The sum of ${a} and ${b} is ${sum}"
                }
            }
        }
        stage('Write JSON') {
            steps {
                script {
                    def data = [
                        name: 'Muhammad Faqih Al Fadholi',
                        university : 'CCIT FTUI',
                        major : 'Software Engineering'
                    ]
                    writeJSON file: 'data_operator.json', json: data
                    echo 'JSON file created: data_operator.json'
                }
            }
        }
    }
        post {
            success {
                echo 'Pipeline completed successfully!'
                script {
                    def currentDate = new Date()
                    def formattedDate = currentDate.format("yyyy-MM-dd HH:mm:ss")

                    // Slack notification
                    try {
                        slackSend(channel: '#jenkins-notification', message: "Pipeline success: ${env.JOB_NAME} #${env.BUILD_NUMBER} ${formattedDate}")
                    } catch (Exception e) {
                        echo "Error sending Slack notification: ${e.message}"
                    }

                    // Email notification
                    try {
                        mail to: 'faqihalfadholi@gmail.com',
                             subject: "Jenkins Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                             body: "The build was successful! Check it out at ${env.BUILD_URL} #${formattedDate}"
                    } catch (Exception e) {
                        echo "Error sending email: ${e.message}"
                    }
                }
            }
            failure {
                echo 'Pipeline failed. Please check the logs.'
                script {
                    def currentDate = new Date()
                    def formattedDate = currentDate.format("yyyy-MM-dd HH:mm:ss")

                    // Slack notification
                    try {
                        slackSend(channel: '#jenkins-notification', message: "Pipeline failed: ${env.JOB_NAME} #${env.BUILD_NUMBER} #${formattedDate}")
                    } catch (Exception e) {
                        echo "Error sending Slack notification: ${e.message}"
                    }

                    // Email notification
                    try {
                        mail to: 'faqihalfadholi@gmail.com',
                             subject: "Jenkins Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                             body: "The build has failed. Please check the logs at ${env.BUILD_URL} #${formattedDate}"
                    } catch (Exception e) {
                        echo "Error sending email: ${e.message}"
                    }
                }
            }
            always {
                echo 'Cleaning up...'
                cleanWs()
            }
        }
}