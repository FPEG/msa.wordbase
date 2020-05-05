package net.fpeg.msa.wordbase.filter;

import net.fpeg.msa.common.dao.UserDao;
import net.fpeg.msa.common.entity.Authority;
import net.fpeg.msa.common.entity.User;
import net.fpeg.msa.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局JWT权限检查
 */
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    final
    JwtUtil newJwtUtil;

    final
    UserDao userDao;

    @Autowired
    public JwtSecurityFilter(JwtUtil jwtUtil, UserDao userDao) {
        this.newJwtUtil = jwtUtil;
        this.userDao = userDao;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new ArrayList<String>() {{
            add("/actuator/health");
        }}.stream()
                .anyMatch(p -> new AntPathMatcher().match(p, request.getServletPath()));
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization").substring(7);
        String username = newJwtUtil.getUserName(token);
        User user = userDao.getUserByUsername(username);
        List<Authority> authorities = user.getAuthorities();
        UsernamePasswordAuthenticationToken utoken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        utoken.setDetails(user.getId());
        SecurityContextHolder.getContext().setAuthentication(utoken);
        filterChain.doFilter(request, response);
    }
}
