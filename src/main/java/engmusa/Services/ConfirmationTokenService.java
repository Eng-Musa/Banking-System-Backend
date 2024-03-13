package engmusa.Services;

import engmusa.Models.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    Optional<ConfirmationToken> getToken(String token);
}
