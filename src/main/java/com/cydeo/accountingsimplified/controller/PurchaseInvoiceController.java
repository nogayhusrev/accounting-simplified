package com.cydeo.accountingsimplified.controller;

import com.cydeo.accountingsimplified.dto.InvoiceDto;
import com.cydeo.accountingsimplified.dto.InvoiceProductDto;
import com.cydeo.accountingsimplified.enums.ClientVendorType;
import com.cydeo.accountingsimplified.enums.InvoiceType;
import com.cydeo.accountingsimplified.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/purchaseInvoices")
public class PurchaseInvoiceController {

    private final InvoiceService invoiceService;

    public PurchaseInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/list")
    public String navigateToPurchaseInvoiceList(Model model) throws Exception {
        model.addAttribute("invoices", invoiceService.getAllInvoicesOfCompany(InvoiceType.PURCHASE));
        return "/invoice/purchase-invoice-list";
    }

    @GetMapping("/create")
    public String navigateToPurchaseInvoiceCreate(Model model) throws Exception {
        model.addAttribute("newPurchaseInvoice", invoiceService.getNewInvoice(InvoiceType.PURCHASE));
        model.addAttribute("vendors", invoiceService.getAllClientVendorsOfCompany(ClientVendorType.VENDOR));
        return "/invoice/purchase-invoice-create";
    }

    @PostMapping("/create")
    public String createNewPurchaseInvoice(InvoiceDto invoiceDto) {
        invoiceService.create(invoiceDto, InvoiceType.PURCHASE);
        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping(value = "/actions/{invoiceId}", params = {"action=update"})
    public String navigateToPurchaseInvoiceUpdate(@PathVariable("invoiceId") Long invoiceId){
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    @GetMapping("/update/{invoiceId}")
    public String navigateToPurchaseInvoiceUpdate(@PathVariable("invoiceId") Long invoiceId, Model model) throws Exception {
        model.addAttribute("invoice", invoiceService.findInvoiceById(invoiceId));
        model.addAttribute("vendors", invoiceService.getAllClientVendorsOfCompany(ClientVendorType.VENDOR));
        model.addAttribute("products", invoiceService.getProductsOfCompany());
        model.addAttribute("newInvoiceProduct", new InvoiceProductDto());
        model.addAttribute("invoiceProducts", invoiceService.getInvoiceProductsOfInvoice(invoiceId));
        return "/invoice/purchase-invoice-update";
    }

    @PostMapping("/update/{invoiceId}")
    public String updatePurchaseInvoice(@PathVariable("invoiceId") Long invoiceId, InvoiceDto invoiceDto) {
        invoiceService.update(invoiceId, invoiceDto);
        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping("/addInvoiceProduct/{invoiceId}")
    public String addInvoiceProductToPurchaseInvoice(@PathVariable("invoiceId") Long invoiceId, InvoiceProductDto invoiceProductDto) {
        invoiceService.addInvoiceProduct(invoiceId, invoiceProductDto);
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    @PostMapping("/removeInvoiceProduct/{invoiceId}/{invoiceProductId}")
    public String removeInvoiceProductFromPurchaseInvoice(@PathVariable("invoiceId") Long invoiceId, @PathVariable("invoiceProductId") Long invoiceProductId) {
        invoiceService.removeInvoiceProduct(invoiceProductId);
        return "redirect:/purchaseInvoices/update/" + invoiceId;
    }

    @PostMapping(value = "/actions/{invoiceId}", params = {"action=approve"})
    public String approvePurchaseInvoice(@PathVariable("invoiceId") Long invoiceId){
        invoiceService.approve(invoiceId);
        return "redirect:/purchaseInvoices/list";
    }

    @PostMapping(value = "/actions/{invoiceId}", params = {"action=delete"})
    public String deletePurchaseInvoice(@PathVariable("invoiceId") Long invoiceId){
        invoiceService.delete(invoiceId);
        return "redirect:/purchaseInvoices/list";
    }




}