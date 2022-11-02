package com.cydeo.accountingsimplified.service;

import com.cydeo.accountingsimplified.dto.InvoiceProductDto;
import com.cydeo.accountingsimplified.dto.ProductDto;
import com.cydeo.accountingsimplified.enums.InvoiceType;

import java.util.List;

public interface InvoiceProductService {

    InvoiceProductDto findInvoiceProductById(long id);
    List<InvoiceProductDto> getInvoiceProductsOfInvoice(Long invoiceId);
    void addInvoiceProduct(Long invoiceId, InvoiceProductDto invoiceProductDto);
    void removeInvoiceProduct(Long invoiceProductId);
    int getPriceOfInvoiceProduct(Long id);
    int getTaxOfInvoiceProduct(Long id);
    int getTotalOfInvoiceProduct(Long id);
    int getProfitLossOfInvoiceProduct(Long id);
    void update(InvoiceProductDto invoiceProductDto);
    List<InvoiceProductDto> findInvoiceProductsByInvoiceTypeAndProductRemainingQuantity(InvoiceType type, ProductDto product, Integer remainingQuantity);

}
