<%-- 
    Document   : login
    Created on : 11-Feb-2020, 03:16:37
    Author     : JOSWAL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row">

    <div class="col-md-6 right-side">
        <div class="lb-header">
            <a href="#" class="login-link active" id="login-box-link">Login</a>
            <a href="#" class="login-link " id="register-box-link">Register</a>
        </div>
        <form class="email-login" name="loginForm" data-parsley-validate="">
            <h1 class="login-header">Welcome Back</h1>
            <p class="login-text">Fill in your details to register and start gofering</p>

            <div class="col-xl-6 col-md-6" style="margin-top: 60px;">
                <div class="section-headline margin-top-25       margin-bottom-12">
                    <h5 class="input-text">Email Address</h5>
                </div>
                <input class="input-box" placeholder="" id="email" required="">
            </div>

            <div class="col-xl-6 col-md-6" style="margin-top: 40px;">
                <div class="section-headline margin-top-25 margin-bottom-12">
                    <h5 class="input-text">Password</h5>
                </div>
                <input class="input-box" placeholder="" id="pass" required="" type="password">
            </div>
            <div class="login-btn-block">
                <button href="#" class="btn-login" type="submit" id="login">Login</button>
            </div>
            <p class="account">Don't have an account? <a href="#"><span class="login-span">Register</span></a>
            </p>
        </form>
        <form class="email-signup" name="registerForm" data-parsley-validate="">
            <h1 class="login-header">Create an account</h1>
            <p class="login-text">Fill in your details to register and start gofering</p>
            <div class="account-input">

                <div class="col-xl-3 col-md-3" style="margin-top: 20px;">
                    <div class="section-headline margin-top-25 margin-bottom-12">
                        <h5 class="input-text">First Name </h5>
                    </div>
                    <input class="input-box input-boxes" placeholder="" id="fname" required="">
                </div>

                <div class="col-xl-3 col-md-3 " style="margin-top: 20px;">
                    <div class="section-headline margin-top-25 margin-bottom-12 left-form">
                        <h5 class="input-text ">Last Name</h5>
                    </div>
                    <input class="input-box input-boxes left-form" placeholder="" id="lname">
                </div>

            </div>
            <div class="col-xl-6 col-md-6" style="margin-top: 20px;">
                <div class="section-headline margin-top-25 margin-bottom-12">
                    <h5 class="input-text">Phone Number</h5>
                </div>
                <input class="input-box" placeholder="" id="phone">
            </div>

            <div class="col-xl-6 col-md-6" style="margin-top: 20px;">
                <div class="section-headline margin-top-25 margin-bottom-12">
                    <h5 class="input-text">Email Address</h5>
                </div>
                <input class="input-box" placeholder="" id="emailAddress">
            </div>

            <div class="col-xl-6 col-md-6" style="margin-top: 20px;">
                <div class="section-headline margin-top-25 margin-bottom-12">
                    <h5 class="input-text">Password</h5>
                </div>
                <input class="input-box" placeholder="" id="password" type="password" required="">
            </div>

            <div class="col-xl-6 col-md-6" style="margin-top: 20px;">
                <div class="section-headline margin-top-25 margin-bottom-12">
                    <h5 class="input-text">Confirm Password</h5>
                </div>
                <input class="input-box" placeholder="" id="confirmPassword" type="password">
                <span id="chkpass"> </span>
            </div>
            <div class="reg-btn-block" style="margin-top: 20px;">
                <button class="btn-reg" type="submit" id="register">Register</button>
            </div>
            <p class="account1" style="margin-top: 20px;">Already registered <a href="#"><span class="register-span">Login</span></a></p>

            <div class="col-md-6 left-block">
                <div class="left-side">

                </div>
                <!-- <img src="img/logo.png" class="logo"> -->
            </div>

        </form>
    </div>
</div>
