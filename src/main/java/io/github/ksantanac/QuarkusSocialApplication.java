package io.github.ksantanac;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title="API Quarkus Social",
        version = "1.0",
    contact = @Contact(
        name = "Kaue Matheus Santana",
        url = "https://github.com/ksantanac",
        email = "kauesantana_13@hotmail.com"),
    license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)

public class QuarkusSocialApplication extends Application {
}
