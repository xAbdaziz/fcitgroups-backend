package dev.abdaziz.kaugroups.config;

import dev.abdaziz.kaugroups.exception.BusinessRuleViolationException;
import dev.abdaziz.kaugroups.model.Gender;
import dev.abdaziz.kaugroups.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GenderCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Skip OPTIONS requests (CORS preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() 
                && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            
            if (user.getGender() == Gender.UNKNOWN) {
                throw new BusinessRuleViolationException(
                    "Please set your gender in your profile before performing this action"
                );
            }
        }

        return true;
    }
}

