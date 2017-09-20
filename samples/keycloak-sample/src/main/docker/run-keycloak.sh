#!/usr/bin/env bash
docker run -e KEYCLOAK_USER=admin \
    -p 18443:8443 \
    -e KEYCLOAK_PASSWORD=KeycloakAdmin \
    --name keycloak \
    jboss/keycloak