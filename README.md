# Rest api for تطبيق قنوات بسيط
# in application.proprtyes add db info (i use in the project mysql)

## Register
```http request
POST http://localhost:8000/register/?username=ahamed&password=atro123atro&email=atro@gmail.com
response => {"data":{"message","Register Successfully"},"status",200}
```
## Login
```http request
POST http://localhost:8000/login/?username=..&password=..&email=..
response => {
    "status": 200,
    "data": {
        "refresh": "0bee4b0f-ead8-424b-a0c2-ae6ff69fb977",
        "message": "Login Successfully",
        "access": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaGFtZWQiLCJpYXQiOjE3NjY3MDA2NzcsImV4cCI6MTc2NzA2MDY3N30.CcwNNGtWBefS3HR_Zjcp0rO9LRFbHQI8y-xci1bqjXM"
    }
}
```
## Logout 
```http request
POST http://localhost:8000/v1/authroztion/access/atro/user/account/logout/
header Authorization : Bearer access token
```
## كو جلب اكسس توكن من ريفرش وضع اكسس توكن بي بلاك ليست وقيرها تلكاها بل مجلد كونتلور

##  Channels use STOMP Protocole

# 📡 Spring WebSocket (STOMP + SockJS) API Documentation

هذا الملف يشرح **WebSocket API** في تطبيق Spring Boot الذي تستخدمه، مع توضيح الـ **Endpoints**، طريقة الإرسال والاستقبال، أنواع الرسائل، والأمثلة العملية.

---

## 1️⃣ نظرة عامة (Overview)

هذا التطبيق يستخدم:

* **Spring WebSocket**
* **STOMP Protocol**
* **SockJS** (fallback للنقل)
* **JWT Authorization (اختياري)**

الغرض الأساسي:

* إنشاء قنوات (Channels)
* إرسال رسائل (Messages)
* بث الرسائل لجميع المشتركين في الوقت الحقيقي (Real-time)

---

## 2️⃣ WebSocket Endpoint

### 🔗 Endpoint الأساسي

```
/ws
```

### 📌 التعريف في Spring

```java
registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .withSockJS();
```

### 🧠 ملاحظات

* هذا **ليس HTTP endpoint**
* لا يمكن استخدام GET أو POST عليه
* الاتصال يتم عبر WebSocket أو SockJS

---

## 3️⃣ STOMP Configuration

```java
registry.setApplicationDestinationPrefixes("/app");
registry.enableSimpleBroker("/topic");
```

### ماذا يعني ذلك؟

| Prefix   | الوظيفة                                   |
| -------- | ----------------------------------------- |
| `/app`   | لإرسال الرسائل من العميل إلى السيرفر      |
| `/topic` | لبث الرسائل من السيرفر إلى جميع المشتركين |

---

## 4️⃣ Message Flow (تدفق الرسائل)

```
Client
  │
  │  SEND /app/sendMessage
  ▼
Server (@MessageMapping)
  │
  │  convertAndSend
  ▼
/topic/channel/{id}
  │
  ▼
All Subscribed Clients
```

---

## 5️⃣ إرسال رسالة إلى السيرفر

### 📤 Destination

```
/app/sendMessage
```

### 📦 Request Payload (JSON)

```json
{
  "content": "Hello",
  "sender": "Ahmed",
  "channelId": 1
}
```

### 🧾 DTO المستخدم

```java
public class Messages {
    private String content;
    private String sender;
    private Long channelId;
}
```

---

## 6️⃣ استقبال الرسائل من السيرفر

### 📥 Subscription Destination

```
/topic/channel/{channelId}
```

### 📦 Response Payload

```json
{
  "id": 10,
  "content": "Hello",
  "sender": "Ahmed",
  "now": "12:30:44",
  "channel": {
    "id": 1,
    "name": "general"
  }
}
```

### 🧾 Entity المستخدم

```java
@Entity
public class MessgessssK {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Channels channel;

    private String content;
    private String sender;
    private LocalTime now;
}
```

---

## 7️⃣ مثال JavaScript Client

```javascript
var socket = new SockJS('http://localhost:8080/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function () {
    stompClient.subscribe('/topic/channel/1', function (msg) {
        console.log(JSON.parse(msg.body));
    });

    stompClient.send('/app/sendMessage', {}, JSON.stringify({
        content: 'Hello',
        sender: 'User',
        channelId: 1
    }));
});
```

---

## 8️⃣ مثال Java (OkHttp WebSocket)

> ⚠️ OkHttp لا يدعم STOMP، هذا مثال WebSocket خام

```java
webSocket.sendMessage(TEXT,
  new Buffer().writeUtf8(
    "{\"content\":\"Hello\",\"sender\":\"User\",\"channelId\":1}"
));
```

---

## 9️⃣ Authentication (JWT)

### 📌 عند الاتصال

يمكن إرسال JWT في الهيدر:

```
Authorization: Bearer <access_TOKEN>
```

ويتم التحقق منه داخل WebSocket Interceptor أو Filter.

---

## 🔟 أخطاء شائعة

| الخطأ             | السبب                              |
| ----------------- | ---------------------------------- |
| 403               | محاولة HTTP على WebSocket endpoint |
| No response       | عدم الاشتراك في /topic             |
| Connection failed | السيرفر غير شغال أو المنفذ خطأ     |

---

## ✅ الخلاصة

* `/ws` → WebSocket endpoint
* `/app/*` → إرسال للسيرفر
* `/topic/*` → استقبال من السيرفر
* لا يمكن استخدام HTTP مع WebSocket
* مناسب لتطبيقات الشات والتحديثات اللحظية

---

📌 **هذا الملف يمكن استخدامه كتوثيق رسمي (API Documentation)** لتطبيقك.
## خليت صطناعي يشرح لنو ماعندي حيل قسم بله
# Built With Intellij (تم انشاء لمشروع بي intellij)