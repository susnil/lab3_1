import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.application.api.handler.AddProductCommandHandler;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;

import static org.mockito.Mockito.*;

public class AddProductCommandHandlerTest {
    private AddProductCommandHandler addProductCommandHandler;
    private ReservationRepository reservationRepository;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;
    private Reservation reservation;
    private Product product;
    private Client client;

    @Before
    public void setUp() {
        addProductCommandHandler = new AddProductCommandHandler();
        reservationRepository = mock(ReservationRepository.class);
        productRepository = mock(ProductRepository.class);
        Whitebox.setInternalState(addProductCommandHandler, "reservationRepository", reservationRepository);
        Whitebox.setInternalState(addProductCommandHandler, "clientRepository", clientRepository);
        Whitebox.setInternalState(addProductCommandHandler, "productRepository", productRepository);
        mock(SuggestionService.class);
        clientRepository = mock(ClientRepository.class);
        reservation = mock(Reservation.class);
        product = mock(Product.class);
        client = mock(Client.class);

        when(reservationRepository.load(new Id("0"))).thenReturn(reservation);
        when(productRepository.load(new Id("0"))).thenReturn(product);
        when(clientRepository.load(new Id("0"))).thenReturn(client);
    }

    @Test
    public void oneTimesHandleTest() {
        AddProductCommand addProductCommand = new AddProductCommand(new Id("0"), new Id("0"), 1);
        when(product.isAvailable()).thenReturn(true);
        addProductCommandHandler.handle(addProductCommand);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void twoTimesHandleTest() {
        AddProductCommand addProductCommand1 = new AddProductCommand(new Id("0"), new Id("0"), 1);
        AddProductCommand addProductCommand2 = new AddProductCommand(new Id("0"), new Id("0"), 1);
        when(product.isAvailable()).thenReturn(true);
        addProductCommandHandler.handle(addProductCommand1);
        addProductCommandHandler.handle(addProductCommand2);
        verify(reservationRepository, times(2)).save(reservation);
    }
}