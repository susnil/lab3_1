import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.sales.domain.invoicing.*;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {
    BookKeeper bookKeeper;
    private InvoiceFactory invoiceFactory;
    private TaxPolicy taxPolicy;
    private InvoiceRequest invoiceRequest;
    @Mock
    private ClientData client;
    @Mock
    InvoiceLine invoiceLine;
    @Before
    public void setUp() {
        bookKeeper = new BookKeeper(invoiceFactory);
        invoiceFactory= new InvoiceFactory();
    }
    @Test
    public void oneInvoiceTest(){
        bookKeeper = new BookKeeper(invoiceFactory);
        Invoice invoice = invoiceFactory.create(client);
        invoice.addItem(invoiceLine);
        invoice.getItems();
        invoice.getItems().size();
                assertEquals(invoice.getItems().size(), 1);
        //Item mockedItem = new Item("it1", "Item 1", "This is item 1", 2000, true);
        //when(itemRepository.findById("it1")).thenReturn(mockedItem);

        //
        // When
        //
        //String result = itemService.getItemNameUpperCase("it1");

        //
        // Then
        //
        //verify(itemRepository, times(1)).findById("it1");
        //assertThat(result, is("ITEM 1"));
    }
    public void twoInvoiceTest(){

    }
}
