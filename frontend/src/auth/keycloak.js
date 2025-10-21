import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "http://localhost:8080/",
    realm: "todo-project-realm",
    clientId: "todo-project-client",
});

export default keycloak;