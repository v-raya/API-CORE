def notifyBuild(String buildStatus, Exception e) {
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = """*${buildStatus}*: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':\nMore detail in console output at <${env.BUILD_URL}|${env.BUILD_URL}>"""
  def details = """${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':\n
    Check console output at ${env.BUILD_URL} """
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
    details +="<p>Error message ${e.message}, stacktrace: ${e}</p>"
    summary +="\nError message ${e.message}, stacktrace: ${e}"
  }

  // Send notifications

  slackSend channel: "#cals-api", baseUrl: 'https://hooks.slack.com/services/', tokenCredentialId: 'slackmessagetpt2', color: colorCode, message: summary
  emailext(
      subject: subject,
      body: details,
      attachLog: true,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']],
      to: "Leonid.Marushevskiy@osi.ca.gov, Alex.Kuznetsov@osi.ca.gov, Oleg.Korniichuk@osi.ca.gov, alexander.serbin@engagepoint.com, vladimir.petrusha@engagepoint.com"
    )
}

node ('tpt2-slave'){
   def serverArti = Artifactory.server 'CWDS_DEV'
   def rtGradle = Artifactory.newGradleBuild()
   properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), disableConcurrentBuilds(), [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
   parameters([
      string(defaultValue: 'SNAPSHOT', description: '', name: 'APP_VERSION'),
      string(defaultValue: 'development', description: '', name: 'branch')
      ]), pipelineTriggers([pollSCM('H/5 * * * *')])])
  try {
   stage('Preparation') {
		  git branch: '$branch', url: 'https://github.com/ca-cwds/api-core.git'
		  rtGradle.tool = "Gradle_35"
		  rtGradle.resolver repo:'repo', server: serverArti
		  rtGradle.useWrapper = true
   }
   stage('Build'){
     if (params.APP_VERSION != "SNAPSHOT" ) {
         echo "!!!! BUILD RELEASE VERSION ${params.APP_VERSION}"
         def buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'jar -Dversion=${APP_VERSION}'
     } else {
         echo "!!!! BUILD SNAPSHOT VERSION"
         def buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'jar'
     }
   }
   stage('Unit Tests') {
       buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'test jacocoTestReport', switches: '--stacktrace'
	     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-common/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report Common', reportTitles: 'JUnit Report Common'])
	     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-rest/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report REST', reportTitles: 'JUnit Report REST'])
	     publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'api-core-cms/build/reports/tests', reportFiles: 'index.html', reportName: 'JUnit Report CMS', reportTitles: 'JUnit Report CMS'])
   }
   stage('License Report') {
      		buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'libLicenseReport'
      		publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/dependency-license', reportFiles: 'licenses.html', reportName: 'License Report', reportTitles: 'License summary'])
      }
   stage('SonarQube analysis'){
		withSonarQubeEnv('Core-SonarQube') {
			buildInfo = rtGradle.run buildFile: 'build.gradle', switches: '--info', tasks: 'sonarqube'
        }
    }

	stage ('Push to artifactory'){

	  if (params.APP_VERSION != "SNAPSHOT") {
	      echo "!!!! PUSH RELEASE VERSION ${params.APP_VERSION}"
        rtGradle.deployer repo:'libs-release', server: serverArti
        rtGradle.deployer.deployArtifacts = true
        buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'publish'
	  } else {
	      echo "!!!! PUSH SNAPSHOT VERSION"
	      rtGradle.deployer repo:'libs-snapshot', server: serverArti
	      rtGradle.deployer.deployArtifacts = true
        buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'publish'
	  }
	  rtGradle.deployer.deployArtifacts = false
	}
 } catch (Exception e)    {
 	   errorcode = e
  	   currentBuild.result = "FAIL"
  	   notifyBuild(currentBuild.result,errorcode)
  	   throw e;
 } finally {
	   cleanWs()
 }
}

