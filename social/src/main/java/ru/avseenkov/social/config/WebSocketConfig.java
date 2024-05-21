package ru.avseenkov.social.config;


import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.avseenkov.social.service.JwtService;
import ru.avseenkov.social.service.user.UserService;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/init").setAllowedOrigins("null").withSockJS();
    }

    private final JwtService jwtService;

    private final UserService userService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/queue").setRelayHost("localhost")
                .setRelayPort(61613).setClientLogin("guest").setClientPasscode("guest");
        registry.enableSimpleBroker("/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> msg, MessageChannel mc) {

                StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(msg, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {

                    String authorizationHeader = headerAccessor.getFirstNativeHeader("Authorization");
                    assert authorizationHeader != null;
                    String jwt = authorizationHeader.substring(7);

                    String userEmail = jwtService.extractUserName(jwt);

                    if (StringUtils.isNotEmpty(userEmail)) {
                        UserDetails userDetails = userService
                                .loadUserByUsername(userEmail);
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        headerAccessor.setUser(authToken);
                    }
                }
                return msg;
            }

        });
    }
}
