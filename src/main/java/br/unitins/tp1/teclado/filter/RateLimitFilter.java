package br.unitins.tp1.teclado.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Provider
public class RateLimitFilter implements ContainerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {
        String path = context.getUriInfo().getPath();
        String method = context.getMethod();

        // Protegemos apenas as rotas e métodos especificados
        if (isProtectedEndpoint(path, method)) {
            String clientIp = getClientIp();

            Bucket bucket = buckets.computeIfAbsent(clientIp, k -> createNewBucket());

            if (!bucket.tryConsume(1)) {
                context.abortWith(
                        Response.status(429) // Too Many Requests
                                .entity("{\"code\": \"429\", \"message\": \"Muitas requisições detectadas originadas deste IP. Aguarde um minuto e tente novamente.\"}")
                                .type("application/json")
                                .build()
                );
            }
        }
    }

    private boolean isProtectedEndpoint(String path, String method) {
        if (path.endsWith("/usuarios/login") && "POST".equalsIgnoreCase(method)) {
            return true;
        }
        if (path.endsWith("/usuarios/recuperar-senha/gerar-codigo") && "PATCH".equalsIgnoreCase(method)) {
            return true;
        }
        return false;
    }

    private Bucket createNewBucket() {
        // Limite de 5 requisições por minuto
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    private String getClientIp() {
        if (request != null) {
            String forwardedFor = request.getHeader("X-Forwarded-For");
            if (forwardedFor != null && !forwardedFor.isEmpty()) {
                return forwardedFor.split(",")[0].trim();
            }
            if (request.remoteAddress() != null) {
                return request.remoteAddress().host();
            }
        }
        return "unknown";
    }
}
