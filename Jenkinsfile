pipeline {
  agent {
    node {
      label 'docker-agent-alpine-socat'
    }
  }
  parameters {
    gitParameter branchFilter: 'origin/(.*)', defaultValue: 'main', name: 'BRANCH', type: 'PT_BRANCH'
  }
  stages {
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