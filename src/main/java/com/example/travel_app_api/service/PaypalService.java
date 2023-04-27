package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Invoice;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaypalService {
    @Autowired
    private APIContext apiContext;
    @Autowired
    private  InvoiceService invoiceService;
    @Autowired
    EmailSenderService emailSenderService;

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException{
        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));
        Transaction transaction = new Transaction();

        transaction.setDescription(description);
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());
        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        apiContext.setMaskRequestId(true);
        return payment.create(apiContext);
    }
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
    public String successPayPal(Payment payment, String id){
        System.out.println(payment.getState());
        if (payment.getState().equals("approved")) {
            Invoice invoice=invoiceService.getInvoiceById(id);
            invoice.setPayments("paypal");
            invoice.setStatus(2);
            invoice.setPayDay(payment.getUpdateTime());
            invoice.setIdPayment(payment.getId());
            invoiceService.updateInvoice(invoiceService.updateInvoice(invoice));
            String name=payment.getPayer().getPayerInfo().getFirstName()+payment.getPayer().getPayerInfo().getLastName();
            String emailThanhtoan=payment.getPayer().getPayerInfo().getEmail();
            emailSenderService.sendMailHtml(invoice.getEmail(),"Giao dịch thành công","<h2>Thanh toán hóa đơn thành công</h2><br/>" +
                    "Mã hóa đơn:"+invoice.getId()+"<br/>" +
                    "Người đặt:"+invoice.getFullName()+"<br/>" +
                    "Địa chỉ:"+invoice.getAddress()+"<br/>" +
                    "Ngày lập hóa đơn:"+invoice.getDateInvoice()+"<br/>" +
                    "Ngày thanh toán:"+invoice.getPayDay()+"<br/>" +
                    "Hình thức thanh toán:"+invoice.getPayments()+"<br/>" +
                    "Tên tài khoản thanh toán:"+name+"<br/>" +
                    "Email tài khoản:"+emailThanhtoan+"<br/>" +
                    "Số tiền thanh toán:"+invoice.getAmount()+" vnđ <br/>" +
                    "<h3>Chúc bạn có một chuyến đi vui vẻ</h3>");
            return
                    "<HTML><body>Thanh toán thành công <a href=\"http://localhost:3000/history\">Link clik to go</a></body></HTML>";
            // return payment.toJSON();
        }
        else {
            return "thanh toan khong thanh cong";
        }
    }
}
