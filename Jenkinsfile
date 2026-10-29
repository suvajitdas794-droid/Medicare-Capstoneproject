pipeline {
  agent any
  environment {
    REGISTRY = credentials('docker-registry-credentials')
    DOCKER_REGISTRY = 'docker.io'
    IMAGE_NAMESPACE = 'replace-with-your-dockerhub-username'
    IMAGE_TAG = "${BUILD_NUMBER}"
  }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Backend tests') {
      steps {
        dir('backend') {
          sh 'mvn -B clean test'
        }
      }
    }
    stage('Frontend build') {
      steps {
        dir('frontend') {
          sh 'npm install'
          sh 'npm run build'
        }
      }
    }
    stage('Build images') {
      steps {
        sh 'docker build -t $DOCKER_REGISTRY/$IMAGE_NAMESPACE/medicare-api:$IMAGE_TAG ./backend'
        sh 'docker build -t $DOCKER_REGISTRY/$IMAGE_NAMESPACE/medicare-web:$IMAGE_TAG ./frontend'
      }
    }
    stage('Push images') {
      when { branch 'main' }
      steps {
        sh 'echo "$REGISTRY_PSW" | docker login $DOCKER_REGISTRY -u "$REGISTRY_USR" --password-stdin'
        sh 'docker push $DOCKER_REGISTRY/$IMAGE_NAMESPACE/medicare-api:$IMAGE_TAG'
        sh 'docker push $DOCKER_REGISTRY/$IMAGE_NAMESPACE/medicare-web:$IMAGE_TAG'
      }
    }
    stage('Deploy to AWS EC2') {
      when { branch 'main' }
      steps {
        input message: 'Deploy these tested images to the configured EC2 host?', ok: 'Deploy'
        echo 'Configure your organization-approved SSH/SSM deployment command here. Do not place private keys or passwords in source control.'
      }
    }
  }
  post { always { cleanWs() } }
}
