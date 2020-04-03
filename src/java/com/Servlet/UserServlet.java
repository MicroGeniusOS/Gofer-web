/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Servlet;

import com.google.gson.Gson;
import goferengine.Managers.EmailManager;
import goferengine.Managers.UserManager;
import goferengine.Managers.UtilityManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class UserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, UnsupportedEncodingException, InterruptedException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(true);
            String caseType = request.getParameter("type").trim();
            String json = "";
            switch (caseType) {
                case "Login": {
                    String[] data = request.getParameterValues("data[]");
                    String Email_PhoneNumber = data[0];
                    String Password = data[1];
                    String result = "";
                    String res = "";
                    String UserStatus = "";
                    int UserID = 0;
                    String json1 = "";
                    String json2 = "";
                    if (UserManager.checkEmailAddressOrPhoneNumberExist(Email_PhoneNumber, Email_PhoneNumber)) {
                        UserID = UserManager.checkPasswordEmailMatch(Password, Email_PhoneNumber);
                        if (UserID != 0) {
                            UserStatus = UserManager.getUserStatus(UserID);
                            if (UserStatus.equalsIgnoreCase("Active")) {
                                String fName = UserManager.getUserFirstName(UserID);
                                String userName = UserManager.getUserName(UserID);
                                String email = UserManager.getUserEmail(UserID);
                                String phone = UserManager.getUserPhone(UserID);
                                    result = "success";
                                try {
//                                    EmailManager em = new EmailManager();
                                    int ses = UserManager.getUserID(email, phone);
                                    session.setAttribute("userName", userName);
                                    session.setAttribute("fName", fName);
                                    session.setAttribute("sess", ses);
                                    if (res.equalsIgnoreCase("success")) {
                                        result = "success";
                                    }
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                json1 = new Gson().toJson(result);
                                json2 = new Gson().toJson(userName);
                                json = "[" + json1 + "," + json2 + "]";
                                break;
                            } else {
                                result = "Account hasn't been activated, pls check your email to activate your account";
                                json = new Gson().toJson(result);
                                break;
                            }
                        } else {
                            result = "Incorrect Password";
                            json = new Gson().toJson(result);
                        }
                        break;
                    } else {
                        result = "Email or Phone Number Entered Doesn't Exist";
                        json = new Gson().toJson(result);
                        break;
                    }
                }

                case "SocialLogin": {
                    String Email = request.getParameter("email");
                    String result = "failed";
                    String UserStatus;
                    int UserID;
                    String json1;
                    String json2;
                    if (UserManager.checkEmailAddressExist(Email)) {
                        UserID = UserManager.getUserIdfromEmailAddress(Email);
                        UserStatus = UserManager.getUserStatus(UserID);
                        if (UserStatus.equalsIgnoreCase("Active")) {
                            String fName = UserManager.getUserFirstName(UserID);
                            String userName = UserManager.getUserName(UserID);
//                            String email = UserManager.getUserEmail(UserID);
                            result = "success";
                            String ses = UserManager.CreateSession(UserID);
                            session.setAttribute("userName", userName);
                            session.setAttribute("fName", fName);
                            session.setAttribute("sess", ses);
                            json1 = new Gson().toJson(result);
                            json2 = new Gson().toJson(userName);
                            json = "[" + json1 + "," + json2 + "]";
                            break;
                        } else {
                            result = "Account hasn't been activated, pls go activate your account";
                            json1 = new Gson().toJson(result);
                            json = "[" + json1 + "]";
                            break;
                        }
                    } else {
                        result = "Email Entered Doesn't Exist";
                        json1 = new Gson().toJson(result);
                        json = "[" + json1 + "]";
                        break;
                    }
                }

                case "MemberRegistration": {
                    String[] data = request.getParameterValues("data[]");
                    String FirstName = data[0];
                    String LastName = data[1];
                    String EmailAddress = data[2];
                    String PhoneNumber = data[3];
                    String Password = data[4];
                    String Subclass = "Member";
                    String Status = "Not Activated";
                    String res;
                    String result = "failed";
                    int UserID;
                    if (UserManager.checkUserInputs(EmailAddress, PhoneNumber, Password, FirstName, LastName)) {
                        if (!UserManager.checkEmailAddressOrPhoneNumberExist(EmailAddress, PhoneNumber)) {
                            res = UserManager.CreateUser(FirstName, LastName, EmailAddress, Password, PhoneNumber, Status);
                            if (res.equalsIgnoreCase("failed")) {
                                res = "Something went wrong, Please try again later";
                            } else {
                                UserID = UserManager.getUserID(EmailAddress, PhoneNumber);
                                session.setAttribute("userName", FirstName + " " + LastName);
                                session.setAttribute("fName", FirstName);
                                session.setAttribute("sess", res);
                                session.setAttribute("Id", UserID);
                                res = "success";
                            }
                        } else {
                            res = "Email or Phone Number already registered";
                        }
                    } else {
                        res = "Invalid Inputs";
                    }
//                    String json1 = new Gson().toJson(result);
                    json = new Gson().toJson(res);
//                    json = "[" + json1 + "]";
                    break;
                }

                case "MemberSocialRegistration": {
                    String FirstName = request.getParameter("firstname");
                    String LastName = request.getParameter("lastname");
                    String EmailAddress = request.getParameter("emailaddress");
                    String res;
                    String result = "failed";
                    if (UserManager.checkSocialData(EmailAddress, FirstName, LastName)) {
                        if (UserManager.checkEmailAddressExist(EmailAddress)) {
                            res = "Email already registered";
                        } else {
                            res = UserManager.CreateUserWithSocialData(FirstName, LastName, EmailAddress);
                            if (res.equalsIgnoreCase("success")) {
                                session.setAttribute("userName", FirstName + " " + LastName);
                                session.setAttribute("fName", FirstName);
                                session.setAttribute("sess", res);
                                result = "success";
                            }
                        }
                    } else {
                        res = "Invalid Inputs";
                    }
                    String json1 = new Gson().toJson(result);
                    String json2 = new Gson().toJson(res);
                    json = "[" + json1 + "," + json2 + "]";
                    break;
                }

                case "ResetPasswordRequest": {
                    String email = request.getParameter("data");
                    String res;
                    String result = "failed";
                    if (!UserManager.checkEmailAddressExist(email)) {
                        res = "Email not registered, please register first";
                    } else {
                        res = EmailManager.sendPasswordResetConfirmationEMail(email);
                        if (res.equalsIgnoreCase("success")) {
                            result = "success";
                        }
                    }
                    String json1 = new Gson().toJson(result);
                    String json2 = new Gson().toJson(res);
                    json = "[" + json1 + "," + json2 + "]";
                    break;
                }

                case "ResetPassword": {
                    String[] data = request.getParameterValues("data[]");
                    int Userid = Integer.parseInt(data[0]);
                    String password = data[1];
                    String res;
                    String result = "failed";
                    res = UserManager.changeUserPassword(Userid, password);
                    if (res.equalsIgnoreCase("success")) {
                        result = "success";
                    }
                    String json1 = new Gson().toJson(result);
                    String json2 = new Gson().toJson(res);
                    json = "[" + json1 + "," + json2 + "]";
                    break;
                }

            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
