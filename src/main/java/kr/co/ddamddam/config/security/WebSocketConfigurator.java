//package kr.co.ddamddam.config.security;
//
//import javax.websocket.HandshakeResponse;
//import javax.websocket.server.HandshakeRequest;
//import javax.websocket.server.ServerEndpointConfig;
//import java.util.List;
//
//public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {
//
//    private final TokenProvider tokenProvider;
//
//    public WebSocketConfigurator(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
//        String token = extractTokenFromRequest(request);
//        TokenUserInfo userInfo = tokenProvider.validateAndGetTokenUserInfo(token);
//
//        config.getUserProperties().put("userIdx", userInfo.getUserIdx());
//        config.getUserProperties().put("userEmail", userInfo.getUserEmail());
//        config.getUserProperties().put("userRole", userInfo.getUserRole());
//    }
//
//    public static String extractTokenFromRequest(HandshakeRequest request) {
//        // 토큰 추출 로직 구현
//        // HandshakeRequest 객체에서 토큰 값을 추출하여 반환
//
//        // 예시: HTTP 헤더에서 "Authorization" 헤더의 값에서 토큰 추출
//        List<String> authorizationHeaders = request.getHeaders().get("Authorization");
//        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
//            String authorizationHeader = authorizationHeaders.get(0);
//            // 예시: "Bearer [토큰값]"에서 "[토큰값]" 추출
//            String[] parts = authorizationHeader.split("\\s+");
//            if (parts.length > 1) {
//                return parts[1];
//            }
//        }
//
//        return null; // 토큰 추출 실패 시 null 반환
//    }
//}