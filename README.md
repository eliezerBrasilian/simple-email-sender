# 📧 Simple Email Sender

A lightweight Java library for sending emails with minimal configuration, available through the Cryxie package manager.

## 📦 Package Information

**Name:** `simple_email_sender`  
**Version:** `0.0.1`  
**Install Command:**

```sh
cryxie install simple_email_sender@0.0.1
```

## 🚀 Quick Start

### 1. Installation

```sh
cryxie install simple_email_sender@0.0.1
```

### 2. Basic Usage

```java
var emailSender = new EmailSender(new GmailConfig());

        // Configure email
        emailSender.fromEmail = "your_email@gmail.com";
        emailSender.password = "your_app_password";
        emailSender.toEmail = "recipient@example.com";
        emailSender.subject = "Test Email";
        emailSender.messageBodyHtml = "<h1>Hello!</h1><p>This is a test email.</p>";
        emailSender.personalTitle = "Your Name";

        // Send with callbacks
        emailSender.sendEmail(
            () -> System.out.println("Email sent successfully!"),
            error -> System.err.println("Error: " + error)
        );
```
