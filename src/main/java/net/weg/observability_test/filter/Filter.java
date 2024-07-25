package net.weg.observability_test.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
//import net.weg.observability_test.service.LogService;
import net.weg.observability_test.util.SetConverter;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@AllArgsConstructor
public class Filter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String logLevel = request.getHeader("X-LOG-LEVEL");
        String logUsecase = request.getHeader("X-LOG-USECASE");

        Set<String> usecase = SetConverter.convertStringToSet(logUsecase);
        usecase.add("MD001");
        usecase.add("CS001");
        usecase.add("CF001");

        MDC.put("usecase", SetConverter.convertSetToString(usecase));
        MDC.put("X-LOG-LEVEL", logLevel);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
