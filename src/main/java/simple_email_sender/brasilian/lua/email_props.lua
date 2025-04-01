---
--- Created by Eliezer.
--- DateTime: 24/12/2024 11:21
---

--- Arquivo que contém propriedades relacionadas a servidor de email

Gmail = {
    ["mail.smtp.ssl.trust"] = "*",
    ["mail.smtp.ssl.starttls"] = true,
    ["mail.smtp.auth"] = true,
    ["mail.smtp.host"] = "smtp.gmail.com",
    ["mail.smtp.port"] = 465,
    ["mail.smtp.socketFactory.port"] = 465,
    ["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory",
    ["mail.smtp.socketFactory.fallback"] = false,
    ["mail.transport.protocol"] = "smtp",
    ["mail.debug"] = true
}
