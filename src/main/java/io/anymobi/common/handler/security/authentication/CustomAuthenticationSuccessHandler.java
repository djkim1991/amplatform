package io.anymobi.common.handler.security.authentication;

import io.anymobi.domain.entity.sec.ActiveUserStore;
import io.anymobi.domain.entity.users.User;
import io.anymobi.services.jpa.security.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component("authenticationSuccessHandler")
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private String targetUrlParameter;

    private String defaultUrl;

    private boolean useReferer;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    private DeviceService deviceService;

    public CustomAuthenticationSuccessHandler() {
        useReferer = false;
        targetUrlParameter = "loginRedirect";
    }

    public boolean isUseReferer() {
        return useReferer;
    }

    public void setUseReferer(boolean useReferer) {
        this.useReferer = useReferer;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {

        handle(request, response, authentication);

        final HttpSession session = request.getSession(false);

        if (session != null) {
            session.setMaxInactiveInterval(30 * 60);

            String username;
            if (authentication.getPrincipal() instanceof User) {

                username = ((User) authentication.getPrincipal()).getEmail();
            } else {
                username = authentication.getName();
            }

            LoggedUser user = new LoggedUser(username, activeUserStore);
            session.setAttribute("loggedUser", user);
            request.setAttribute("authentication", authentication);

            ModelMap model = new ModelMap();
            model.addAttribute("authentication", authentication);
        }
        clearAuthenticationAttributes(request);
        loginNotification(authentication, request);
    }

    private void loginNotification(Authentication authentication, HttpServletRequest request) {
        try {
            if (authentication.getPrincipal() instanceof User) {
                deviceService.verifyDevice(((User)authentication.getPrincipal()), request);
            }
        } catch (Exception e) {
            log.error("An error occurred while verifying device or location", e);
            throw new RuntimeException(e);
        }

    }

    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {

        final String targetUrl = determineTargetUrl(authentication,request, response);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication, HttpServletRequest request, HttpServletResponse response)  throws IOException{

        int intRedirectStrategy = decideRedirectStrategy(request, response);
        String targetUrl;

        switch(intRedirectStrategy){
            case 1:
                targetUrl = useTargetUrl(request, response, authentication);
                break;
            case 2:
                targetUrl = useSessionUrl(request, response, authentication);
                break;
            case 3:
                targetUrl = useRefererUrl(request, response, authentication);
                break;
            default:
                targetUrl = useDefaultUrl(request, response, authentication);
        }

        return targetUrl;
    }

    protected void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private String useTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null){
            requestCache.removeRequest(request, response);
        }
        return request.getParameter(targetUrlParameter);
    }

    private String useSessionUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        return savedRequest.getRedirectUrl();
    }

    private String useRefererUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        return request.getHeader("REFERER");
    }

    private String useDefaultUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{

        boolean isUser = false;
        boolean isAdmin = false;
        String targetUrl = null;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            if (!grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isUser = true;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
                isUser = false;
                break;
            }
        }
        if (isUser) {
            String username;
            if (authentication.getPrincipal() instanceof User) {
                username = ((User) authentication.getPrincipal()).getEmail();
            } else {
                username = authentication.getName();
            }

            targetUrl = "/home.html?user=" + username;
        } else if (isAdmin) {
            targetUrl = "/users/console.html";
        } else {
            throw new IllegalStateException();
        }

        return targetUrl;
    }

    /**
     * 인증 성공후 어떤 URL로 redirect 할지를 결정한다
     * 판단 기준은 targetUrlParameter 값을 읽은 URL이 존재할 경우 그것을 1순위
     * 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을 2순위
     * 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을 3순위
     * 3순위 URL이 없을 경우 Default URL을 4순위로 한다
     * @param request
     * @param response
     * @return   1 : targetUrlParameter 값을 읽은 URL
     *            2 : Session에 저장되어 있는 URL
     *            3 : referer 헤더에 있는 url
     *            0 : default url
     */
    private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response){
        int result = 0;

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(!"".equals(targetUrlParameter)){
            String targetUrl = request.getParameter(targetUrlParameter);
            if(StringUtils.hasText(targetUrl)){
                result = 1;
            }else{
                if(savedRequest != null){
                    result = 2;
                }else{
                    String refererUrl = request.getHeader("REFERER");
                    if(useReferer && StringUtils.hasText(refererUrl)){
                        result = 3;
                    }else{
                        result = 0;
                    }
                }
            }

            return result;
        }

        if(savedRequest != null){
            result = 2;
            return result;
        }

        String refererUrl = request.getHeader("REFERER");
        if(useReferer && StringUtils.hasText(refererUrl)){
            result = 3;
        }else{
            result = 0;
        }

        return result;
    }

    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}