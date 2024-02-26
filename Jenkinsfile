pipeline {
  agent {
    node {
      label 'docker-agent'
    }
  }
  parameters {
    gitParameter branchFilter: 'origin/(.*)', defaultValue: 'main', name: 'BRANCH', type: 'PT_BRANCH'
  }
  stages {
    stage('Source') {
      steps {
        git branch: "${params.BRANCH}", url: 'https://github.com/jenkinsci/git-parameter-plugin.git'
      }
    }
    stage('Build') {
      steps {
        echo "Building.."
        sh '''
          ./gradlew clean bootJar
          '''
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploy....'
        sh '''
        docker compose up -d
        '''
      }
    }
  }
}