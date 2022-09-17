package com.codifi.cp2.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.codifi.cp2.configuration.ApplicationProperties;
import com.codifi.cp2.entity.RequestResponseLogEntity;
import com.codifi.cp2.repository.RequestResponseLogRepository;
import com.codifi.cp2.util.MessageConstants;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static String userId;
    private static RequestResponseLogRepository requestResponseLogRepository;
    ApplicationProperties props;

    public JwtRequestFilter(RequestResponseLogRepository requestResponseLogRepository) {
        JwtRequestFilter.requestResponseLogRepository = requestResponseLogRepository;
    }

    /**
     * visible Media type declaration
     */
    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"), MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA);

    /**
     * Method to save event logs
     *
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            if (isAsyncDispatch(request)) {
                chain.doFilter(request, response);
            } else {
                doFilterWrapped(wrapRequest(request), wrapResponse(response), chain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            afterRequest(request, response);
            response.copyBodyToResponse();
        }
    }

    protected void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        String origin = request.getRequestURL().toString();
        logResponse(request, response, origin);
    }

    private static void logResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
            String origin) {
        byte[] content = response.getContentAsByteArray();
        byte[] requestContent = request.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, requestContent, response.getContentType(), response.getCharacterEncoding(), request,
                    response, origin);
        }
    }

    /**
     * Methos to save Request and response in database
     */
    private static void logContent(byte[] content, byte[] requestContent, String contentType, String contentEncoding,
            ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, String origin) {
        MediaType mediaType = MediaType.valueOf(contentType);
        String contentString;
        String requestContentString;
        StringBuffer contentLine = new StringBuffer();
        StringBuffer requestContentLine = new StringBuffer();
        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {

                contentString = new String(content, contentEncoding);
                Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> contentLine.append(line));

                requestContentString = new String(requestContent, contentEncoding);
                Stream.of(requestContentString.split("\r\n|\r|\n")).forEach(line -> requestContentLine.append(line));
            } catch (UnsupportedEncodingException e) {
            }
        }
        RequestResponseLogEntity requestResponseLogEntity = new RequestResponseLogEntity();
        if (request != null && !request.getRequestURL().toString().contains("/translate/getByPageAndLangIds")) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                contentLine.append(queryString);
                requestContentLine.append(queryString);
            }
            requestResponseLogEntity.setDeviceIp(request.getRemoteAddr());
            requestResponseLogEntity.setUrl(request.getRequestURL().toString());
            requestResponseLogEntity.setContentType(contentType);
            requestResponseLogEntity.setRequestInput(requestContentLine.toString());
            requestResponseLogEntity.setEntryTime(new Date());
            requestResponseLogEntity.setUserId(request.getHeader("Authorization"));
            requestResponseLogEntity.setUserAgent(request.getHeader(MessageConstants.USER_AGENT));
        }
        if (response != null && !request.getRequestURL().toString().contains("/translate/getByPageAndLangIds")) {
            requestResponseLogEntity.setElapsedTime(new Date().toString());
            requestResponseLogEntity.setUrl(origin);
            requestResponseLogEntity.setStatus(response.getStatus());
            requestResponseLogEntity.setResponseData(contentLine.toString());
            requestResponseLogEntity.setUserId(request.getHeader("Authorization"));
            requestResponseLogRepository.save(requestResponseLogEntity);
        }
    }

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }

}
