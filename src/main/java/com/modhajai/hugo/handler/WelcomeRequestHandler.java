package com.modhajai.hugo.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WelcomeRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("WelcomeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String accessToken = handlerInput.getRequestEnvelope().getContext().getSystem().getUser().getAccessToken();

        if (accessToken != null) {
            // TODO Validate the token an retrieve user claims

            DecodedJWT jwt = JWT.decode(accessToken);
            return handlerInput.getResponseBuilder()
                    .withSpeech("Welcome to Hugo -> " + jwt.getClaim("given_name") + "!")
                    .build();
        } else {
            String speechText = "You must have a Humana account. "
                    + "Please use the Alexa app to link your Amazon account "
                    + "with your Humana Account.";
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechText)
                    .withLinkAccountCard()
                    .build();
        }
    }
}

