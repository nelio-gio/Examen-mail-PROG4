package api.poja.app.service.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import api.poja.app.endpoint.event.model.SendEmailRequested;
import api.poja.app.mail.Mailer;
import org.junit.jupiter.api.Test;

class SendEmailRequestedServiceTest {

  private final Mailer mailer = mock(Mailer.class);
  private final SendEmailRequestedService service = new SendEmailRequestedService(mailer);

  @Test
  void accept_sendsEmail() throws Exception {
    var event = new SendEmailRequested();
    event.setTo("test@test.com");

    service.accept(event);

    verify(mailer).accept(any());
  }
}
