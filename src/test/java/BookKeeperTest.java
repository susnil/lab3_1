import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.*;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType.FOOD;

@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {
    BookKeeper bookKeeper;
    private InvoiceFactory invoiceFactory;
    @Mock
    private TaxPolicy taxPolicy;
    private InvoiceRequest invoiceRequest;
    @Mock
    private Money money;
    @Mock
    InvoiceLine invoiceLine;
    ProductData productData;
    Tax tax;

    @Before
    public void setUp() {
        bookKeeper = new BookKeeper(invoiceFactory);
        invoiceFactory = new InvoiceFactory();
        invoiceRequest = new InvoiceRequest(new ClientData(Id.generate(), "Blab sa"));
        money = new Money(12);
        Date snapshotDate = new Date();
        productData = new ProductData(Id.generate(), money, "nazwa", FOOD, snapshotDate);
        ProductData product = new ProductData(Id.generate(), new Money(100), "Awocado", FOOD, snapshotDate);
        int quantity = 3;
        Money net = new Money(250);
        tax = new Tax(new Money(23), "VAT");
        invoiceLine = new InvoiceLine(product, quantity, net, tax);
    }

    @Test
    public void oneElementAtInvoiceTest() {
        bookKeeper = new BookKeeper(invoiceFactory);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        invoice.addItem(invoiceLine);
        assertEquals(invoice.getItems().size(), 1);
    }

    @Test
    public void twoElementsAtInvoiceTest() {
        bookKeeper = new BookKeeper(invoiceFactory);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        invoice.addItem(invoiceLine);
        invoice.addItem(new InvoiceLine(new ProductData(Id.generate(), new Money(0), "Awocado GRATIS", FOOD, new Date()), 1, new Money(BigDecimal.ZERO), tax));
        assertEquals(invoice.getItems().size(), 2);
    }
}
