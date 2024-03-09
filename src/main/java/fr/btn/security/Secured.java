package fr.btn.security;

import jakarta.ws.rs.NameBinding;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@SecurityRequirement(name="x-api-key")
public @interface Secured {
}
