package engmusa.Services;

import engmusa.Models.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    String saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getToken(String token);

}
