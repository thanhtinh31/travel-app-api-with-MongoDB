package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.repository.AccountRepository;
import com.google.firebase.auth.hash.Bcrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    public List<Account> listAcount(){

        return accountRepository.findAll();
    }
    public Account getAccountById(String id){return accountRepository.findById(id).get();}
    public Map<String,Object> login(String email,String password){
        Map<String,Object> m=new HashMap<>();
        Account account=accountRepository.getAcountByEmail(email);
        if(account!=null){
            if(account.isStatus()){
                if(accountRepository.login(email,password)!=null){
                    m.put("account",accountRepository.login(email,password));
                    m.put("message","Đăng nhập thành công");
                    m.put("status","1");
                    return m;
                }
                else{
                    m.put("message","Mật khẩu không chính xác");
                }

            }else{
                m.put("message","Tài khoản đã bị khóa");

            }
        }
        else{
            m.put("message","Tài khoản không tồn tại");
        }
        m.put("status","0");
        return m;
    }

    public Map<String,Object> handleLoginFB(Account account){
        Map<String,Object> m=new HashMap<>();
        if(accountRepository.getAccountByIDFacebook(account.getIdFacebook())!=null)
        {
            m.put("message","Tài khoản đã có");
            Account account1 = accountRepository.getAccountByIDFacebook(account.getIdFacebook());
            account1.setImage(account.getImage());
            account1.setNameAccount(account.getNameAccount());
            accountRepository.save(account1);
            m.put("account",accountRepository.getAccountByIDFacebook(account.getIdFacebook()));
        }
        else{

            accountRepository.save(account);
            m.put("message","Tai khoan moi");
            m.put("account",account);
        }
        return m;
    }
    public List<Account> getActive(){
        return accountRepository.getAcountActive();
    }
    public Account addAccount(Account account){
        return accountRepository.save(account);
    }
    public Account editAccount(Account account){
        Account account1=accountRepository.findById(account.getId()).get();
        account1.setAddress(account.getAddress());
        account1.setPhoneNumber(account.getPhoneNumber());
        account1.setPassword(account.getPassword());
        return accountRepository.save(account1);
    }
    public String deleteAccount(String idAccount){
        accountRepository.deleteById(idAccount);
        return "Deleted";
    }
    public Account findAccountByEmail(String email){
        if(accountRepository.getAcountByEmail(email)==null){
           return null;
        }
        return accountRepository.getAcountByEmail(email);
    }
    public int verify(String email,String name){
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        String b="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\">\n" +
                " <head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">\n" +
                "  <meta name=\"x-apple-disable-message-reformatting\">\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "  <meta content=\"telephone=no\" name=\"format-detection\">\n" +
                "  <title>Trigger newsletter 4</title>\n" +
                "  <link href=\"https://fonts.googleapis.com/css?family=Open+Sans:400,400i,700,700i\" rel=\"stylesheet\"><!--<![endif]-->\n" +
                "  <style type=\"text/css\">\n" +
                "#outlook a {\n" +
                "\tpadding:0;\n" +
                "}\n" +
                ".ExternalClass {\n" +
                "\twidth:100%;\n" +
                "}\n" +
                ".ExternalClass,\n" +
                ".ExternalClass p,\n" +
                ".ExternalClass span,\n" +
                ".ExternalClass font,\n" +
                ".ExternalClass td,\n" +
                ".ExternalClass div {\n" +
                "\tline-height:100%;\n" +
                "}\n" +
                ".es-button {\n" +
                "\tmso-style-priority:100!important;\n" +
                "\ttext-decoration:none!important;\n" +
                "}\n" +
                "a[x-apple-data-detectors] {\n" +
                "\tcolor:inherit!important;\n" +
                "\ttext-decoration:none!important;\n" +
                "\tfont-size:inherit!important;\n" +
                "\tfont-family:inherit!important;\n" +
                "\tfont-weight:inherit!important;\n" +
                "\tline-height:inherit!important;\n" +
                "}\n" +
                ".es-desk-hidden {\n" +
                "\tdisplay:none;\n" +
                "\tfloat:left;\n" +
                "\toverflow:hidden;\n" +
                "\twidth:0;\n" +
                "\tmax-height:0;\n" +
                "\tline-height:0;\n" +
                "\tmso-hide:all;\n" +
                "}\n" +
                "@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:30px!important; text-align:center } h2 { font-size:26px!important; text-align:center } h3 { font-size:20px!important; text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:26px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:20px!important; display:inline-block!important; border-width:15px 25px 15px 25px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }\n" +
                "</style>\n" +
                " </head>\n" +
                " <body style=\"width:100%;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;padding:0;Margin:0\">\n" +
                "  <div class=\"es-wrapper-color\" style=\"background-color:#F6F6F6\">\n" +
                "   <table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#F6F6F6\">\n" +
                "     <tr style=\"border-collapse:collapse\">\n" +
                "      <td valign=\"top\" style=\"padding:0;Margin:0\">\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-content\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "         <tr style=\"border-collapse:collapse\">\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "             <tr style=\"border-collapse:collapse\">\n" +
                "              <td align=\"left\" style=\"Margin:0;padding-top:15px;padding-bottom:15px;padding-left:20px;padding-right:20px\"><!--[if mso]><table style=\"width:600px\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"width:290px\" valign=\"top\"><![endif]-->\n" +
                "               <table class=\"es-left\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left\">\n" +
                "                 <tr style=\"border-collapse:collapse\">\n" +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:290px\">\n" +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td class=\"es-infoblock es-m-txt-c\" align=\"left\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica\\ neue', helvetica, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\">Put your preheader text here<br></p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table>\n" +
                "               <table class=\"es-right\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right\">\n" +
                "                 <tr style=\"border-collapse:collapse\">\n" +
                "                  <td align=\"left\" style=\"padding:0;Margin:0;width:290px\">\n" +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"right\" class=\"es-infoblock es-m-txt-c\" style=\"padding:0;Margin:0;line-height:14px;font-size:12px;color:#CCCCCC\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;line-height:14px;color:#CCCCCC;font-size:12px\"><a href=\"https://viewstripo.email\" target=\"_blank\" class=\"view\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#CCCCCC;font-size:12px;font-family:arial, 'helvetica neue', helvetica, sans-serif\">View in browser</a></p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table class=\"es-header\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "         <tr style=\"border-collapse:collapse\">\n" +
                "          <td style=\"padding:0;Margin:0;background-image:url(https://pioneer.com.ph/wp-content/uploads/2022/04/Pioneer-Corp-Website-Personal-Travel-copy.jpg);background-position:center top;background-repeat:no-repeat;background-size:cover\" bgcolor=\"#3d4c6b\" align=\"center\" background=\"https://pioneer.com.ph/wp-content/uploads/2022/04/Pioneer-Corp-Website-Personal-Travel-copy.jpg\">\n" +
                "           <table class=\"es-header-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;border-top:10px solid transparent;border-right:10px solid transparent;border-left:10px solid transparent;width:640px;border-bottom:10px solid transparent\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "             <tr style=\"border-collapse:collapse\">\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-top:10px;padding-left:20px;padding-right:20px\">\n" +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr style=\"border-collapse:collapse\">\n" +
                "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:582px\">\n" +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"padding:40px;Margin:0;font-size:0px\"><a target=\"_blank\" href=\"https://viewstripo.email\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#B7BDC9;font-size:20px\"><img class=\"adapt-img\" src=\"https://hoctot.hocmai.vn/wp-content/uploads/2020/02/dang-ky-ngay.gif\" alt=\"Newsletter article #1\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;border-radius:3px 3px 0px 0px\" title=\"Newsletter article #1\" width=\"502\"></a></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\">\n" +
                "         <tr style=\"border-collapse:collapse\">\n" +
                "          <td align=\"center\" background=\"https://vjbnkp.stripocdn.email/content/guids/CABINET_156905ad6083aef21fe904cbe00aded6/images/vector_cci.png\" style=\"padding:0;Margin:0;background-image:url(https://vjbnkp.stripocdn.email/content/guids/CABINET_156905ad6083aef21fe904cbe00aded6/images/vector_cci.png);background-repeat:no-repeat;background-position:left top\">\n" +
                "           <table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:transparent;width:640px\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "             <tr style=\"border-collapse:collapse\">\n" +
                "              <td align=\"left\" style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px\">\n" +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr style=\"border-collapse:collapse\">\n" +
                "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n" +
                "                   <table style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:separate;border-spacing:0px;border-radius:4px;background-color:#f9f5f5\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#f9f5f5\" role=\"presentation\">\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"Margin:0;padding-left:15px;padding-right:15px;padding-bottom:20px;padding-top:30px\"><h2 style=\"Margin:0;line-height:48px;mso-line-height-rule:exactly;font-family:'comic sans ms', 'marker felt-thin', arial, sans-serif;font-size:40px;font-style:normal;font-weight:bold;color:#ff0000\">ĐĂNG KÝ TÀI KHOẢN</h2></td>\n" +
                "                     </tr>\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0\"><img class=\"adapt-img\" src=\"https://atpsoftware.vn/wp-content/uploads/2020/02/Nh%E1%BA%ADn-bi%E1%BA%BFt-Register-l%C3%A0-g%C3%AC.jpg\" alt width=\"600\" style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\"></td>\n" +
                "                     </tr>\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"Margin:0;padding-top:10px;padding-bottom:15px;padding-left:20px;padding-right:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'comic sans ms', 'marker felt-thin', arial, sans-serif;line-height:27px;color:#0b0b0b;font-size:18px\">Xin chào,<span style=\"color:#FF0000\"><strong>&nbsp;"+name+"</strong></span>&nbsp;đã đến với website của chúng tôi.<br></p><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'comic sans ms', 'marker felt-thin', arial, sans-serif;line-height:27px;color:#0b0b0b;font-size:18px\">Để kích hoạt tài khoản vui lòng sử dụng mã:&nbsp;<strong><span style=\"color:#FF0000\">"+code+"</span></strong>.&nbsp;<strong>TUYỆT ĐỐI KHÔNG CHIA SẺ MÃ NÀY CHO BẤT KỲ AI TRONG MỌI&nbsp;TRƯỜNG HỢP</strong></p></td>\n" +
                "                     </tr>\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0;font-size:0px\"><img class=\"adapt-img\" src=\"https://youmatter.world/app/uploads/sites/2/2019/11/travel-world.jpg\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"600\"></td>\n" +
                "                     </tr>\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"Margin:0;padding-top:10px;padding-bottom:15px;padding-left:20px;padding-right:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'comic sans ms', 'marker felt-thin', arial, sans-serif;line-height:27px;color:#000000;font-size:18px\">Nếu như bạn có bất kỳ câu hỏi nào hoặc không thực hiện yêu cầu này có thể bỏ qua hoặc liên hệ với chúng tôi qua địa chỉ email:&nbsp;<em><strong><a href=\"mailto:hotelhimara@gmail.com\" target=\"_blank\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#ff0000;font-size:16px\">thanhtinhtrinhtk123@gmail.com</a></strong></em><br>Đây là địa chỉ email tự động, vui lòng không trả lời qua email này!.</p></td>\n" +
                "                     </tr>\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"right\" style=\"Margin:0;padding-top:10px;padding-bottom:15px;padding-left:20px;padding-right:20px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'comic sans ms', 'marker felt-thin', arial, sans-serif;line-height:27px;color:#000000;font-size:18px\"><em><strong>TRAVEL APP VERIFY</strong></em></p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table>\n" +
                "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"es-footer\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\">\n" +
                "         <tr style=\"border-collapse:collapse\">\n" +
                "          <td align=\"center\" style=\"padding:0;Margin:0\">\n" +
                "           <table class=\"es-footer-body\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#F6F6F6;width:640px\">\n" +
                "             <tr style=\"border-collapse:collapse\">\n" +
                "              <td align=\"left\" style=\"Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px\">\n" +
                "               <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                 <tr style=\"border-collapse:collapse\">\n" +
                "                  <td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:600px\">\n" +
                "                   <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\">\n" +
                "                     <tr style=\"border-collapse:collapse\">\n" +
                "                      <td align=\"center\" style=\"padding:0;Margin:0\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:'open sans', 'helvetica neue', helvetica, arial, sans-serif;line-height:21px;color:#999999;font-size:14px\"><u><a target=\"_blank\" href=\"https://viewstripo.email\" class=\"view\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:14px\">View Online</a></u>&nbsp;&nbsp; • &nbsp;&nbsp;<u><a class=\"unsubscribe\" target=\"_blank\" href=\"\" style=\"-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:none;color:#999999;font-size:14px\">Unsubscribe</a></u></p></td>\n" +
                "                     </tr>\n" +
                "                   </table></td>\n" +
                "                 </tr>\n" +
                "               </table></td>\n" +
                "             </tr>\n" +
                "           </table></td>\n" +
                "         </tr>\n" +
                "       </table></td>\n" +
                "     </tr>\n" +
                "   </table>\n" +
                "  </div>\n" +
                "  <div class=\"banner-toolbar\"></div>\n" +
                " </body>\n" +
                "</html>\n";

        String emailBody=b;
        String subjectEmail="Travel app Verify";
        emailSenderService.sendMailHtml(email,subjectEmail,emailBody);
        return code;
    }
    public Map<String,Object> register(Account account){
        Map<String,Object> m=new HashMap<>();
        if (accountRepository.getAcountByEmail(account.getEmail())==null){
            int code=verify(account.getEmail(),account.getNameAccount());
            m.put("status","1");
            m.put("account",account);
            m.put("code",code);
        }
        else{
            m.put("message","Email đã được đăng ký");
            m.put("status","0");
        }
        return m;
    }
    public Map<String,Object> forgetPassword(String email){
        Map<String,Object> m=new HashMap<>();
        if(accountRepository.getAcountByEmail(email)!=null){
            int code=verify(email,"");
            m.put("status","1");
            m.put("code",code);
            m.put("account",accountRepository.getAcountByEmail(email));
        }else{
            m.put("message","Tài khoản không tồn tại");
            m.put("status","0");
        }
        return m;
    }
    public Map<String,Object> changePassword(String email,String oldPass,String newPass){
        Map<String,Object> m=new HashMap<>();
        Account account=accountRepository.getAcountByEmail(email);
        if(oldPass.equals(account.getPassword())){
            if(oldPass.equals(newPass)){
                m.put("message","Mật khẩu không được giống cũ");
            }
            else{
                //changePass
                account.setPassword(newPass);
                accountRepository.save(account);
                emailSenderService.sendMail(email,"Travel app thông báo","Mật khẩu tài khoản của bạn đã thay đổi vào lúc "+java.time.LocalDateTime.now());
                m.put("message","Thay đổi mật khẩu thành công, vui lòng đăng nhập lại");
                m.put("status","1");
                return m;
            }
        }
        else{
            m.put("message","Mật khẩu không trùng khớp");
        }
        m.put("status","0");
        return m;
    }


}
