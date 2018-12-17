node {

     withCredentials([[$class: 'UsernamePasswordMultiBinding',
     credentialsId: 'onjsdnjs2',
     usernameVariable: 'DOCKER_USER_ID',
     passwordVariable: 'DOCKER_USER_PASSWORD']])

     {

         stage('Pull') {
             git 'https://github.com/onjsdnjs/amplatform.git'
         }

         stage('Unit Test') {
         }
         stage('Build') {
             sh(script: '/usr/local/bin/docker-compose build app')
         }

         stage('Tag') {
             sh(script: '''docker tag ${DOCKER_USER_ID}/amplatform \
             ${DOCKER_USER_ID}/amplatform:${BUILD_NUMBER}''')
         }

         stage('Push') {
             sh(script: 'docker login -u ${DOCKER_USER_ID} -p ${DOCKER_USER_PASSWORD}')
             sh(script: 'docker push ${DOCKER_USER_ID}/amplatform:${BUILD_NUMBER}')
             sh(script: 'docker push ${DOCKER_USER_ID}/amplatform:latest')
         }

         stage('Deploy') {
             sh(script: '/usr/local/bin/docker-compose up -d web')
         }
     }

}