#!/bin/bash
docker rm keycloak
docker run -e KEYCLOAK_USER=admin \
    -p 18080:8080 \
    -p 18443:8443 \
    -e KEYCLOAK_PASSWORD=admin \
    --name keycloak \
    jboss/keycloak:3.3.0.CR2