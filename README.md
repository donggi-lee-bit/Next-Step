# next step

## forward 와 redirect 의 차이

### forward 방식

`Forward` 는 web container 차원에서 페이지 이동만 존재
<br>
실제로 웹 브라우저는 다른 페이지로 이동했음을 알 수 없음

- 이동할 url 요청 정보를 그대로 전달
  - 그렇기 떄문에 사용자가 최초로 요청한 요청 정보는 다음 url에서도 유효함
- 시스템 변화가 생기지 않는 단순 조회 요청의 경우 forward로 응답하는 것이 바람직하다

### redirect 방식

`redirect` 는 web container로 명령이 들어오면 웹 브라우저에게 다른 페이지로 이동하라고 명령을 내린다. <br>
그러면 웹 브라우저는 url을 지시된 주소로 바꾸고 해당 주소로 이동. <br>
다른 웹 컨테이너에 있는 주소로 이동하며 새로운 페이지에는 request, response 객체가 새롭게 생성됨. <br>

- `redirect` 의 경우 최초 요청을 받은 url1 에서 클라이언트에게 redirect 할 url2 를 반환
- 클라이언트는 새로운 요청을 생성하여 url2 에 다시 요청을 보냄
  - 그렇기에 최초의 request, response 객체는 유효하지 않고 새롭게 생성됨
- 시스템 변화가 생기는 요청 (create, update) 의 경우에 redirect 응답이 바람직하다

출처 : https://mangkyu.tistory.com/51

## Content-Type

Content-Type이란 HTTP 통신에서 전송되는 데이터의 타입을 나타내는 header 정보 중 하나이다. <br>
Content-Type에 따라 데이터를 받는 측에서는 데이터를 어떻게 처리할지 판단한다. <br>
데이터를 받는 측은 주로 request 를 보내는 웹 브라우저와 response 를 받는 웹서버 둘 다 포함된다. <br>

- 예로 서버에서 브라우저로 이미지 파일을 보낼 경우 `reponse header` 에 `content-type` 은 `image/svg` 를 지정해서 보낸다
- 데이터를 받는 측에서는 content-type 을 확인 후 데이터를 어떻게 분석, 파싱할지 정하고 처리

### 특징

- 특정 data(image, video) 를 content-type 없이 보내면 데이터를 받는 쪽에서는 단순 텍스트 데이터로 받는다
- GET 요청시 value=text 형식으로 보내지기 떄문에 content-type 은 필요없다
- POST, PUT 처럼 body에 데이터를 담아 보낼 때 content-type 필요

### MIME

MIME이란, Multipurpose Internet Mail Extensions 의 약자

전자 우편을 위한 인터넷 표준 포맷이다. 전자우편은 7비트 ASCII 문자를 사용하여 전송되기 때문에, 8비트 이상의 코드를 사용하는 문자나 이진 파일들은 MIME 포맷으로 변환되어 SMTP로 전송된다. 실질적으로 SMTP로 전송되는 대부분의 전자 우편은 MIME 형식이다. MIME 표준에 정의된 content types은 HTTP와 같은 통신 프로토콜에서 사용되며, 점차 그 중요성이 커지고 있다.
- 위키백과 -

### 종류

`type/subtype`
`/` 로 구분되며 type은 카테고리, subtype 은 개별 혹은 멀티파트 타입으로 나타낸다 <br>
type은 주분류, subtype은 주분류에서 나눈 종류로 생각하면 됨 <br>
또한 스페이스는 허용되지 않고 대소문자를 구분하지 않지만 통상적으로 소문자를 사용함 <br>

주요 content-type 만 살펴보자

**content-type: application/octet-stream**
- 이진 파일의 기본 타입
- 브라우저에서 자동으로 실행하지 않거나 실행할지 묻는다

**content-type: text/plain**
- 텍스트 파일 기본 타입

**content-type: image/**
- content-type: image/png
- content-type: image/Jpeg
- content-type: image/gif
- content-type: image/webp
브라우저는 해당 컨텐트를 이미지로 취급한다

**content-type: text/javascript**
- javascript 문서로 취급

**content-type: multipart/form-data**
- <form> 태그를 사용해 브라우저에서 서버로 전송할 때 사용

출처 : https://pygmalion0220.tistory.com/entry/HTTP-Content-Type

## 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리 제거

RequestHandler 의 run() 의 문제점은 기능이 추가될 때마다 <br>
새로운 else if 절이 추가되는 구조로 구현되어 있다. <br>
<br>
이는 객체지향 설계 원칙 중 OCP (개방폐쇄의 원칙) 원칙을 위반하고 있다. <br>
새로운 기능이 추가되거나 수정이 발생하더라도 변화의 범위를 최소화하도록 설계를 개선해보자. <br>
<br>
각 분기문을 메서드로 분리하는 리팩토링을 진행하고 보니 각 메서드는 HttpRequest, HttpResponse 만 인자로 받는 것을 확인할 수 있다. <br>
<br>
이와 같이 메서드 원형이 같을 때 Java의 인터페이스로 추출하는 것이 가능하다. <br>
`Controller` 라는 이름의 인터페이스를 추가한다. <br>
<br>
Controller 인터페이스를 추가한 후 분기문에서 분리했던 메서드의 구현 코드를 <br>
Controller 인터페이스에 대한 구현 클래스로 이동한다. <br>
<br>
이제 각 요청 URL에 대응하는 Controller를 연결하는 `RequestMapping` 이라는 클래스를 추가한다. <br>
RequestMapping은 웹 애플리케이션에서 서비스하는 모든 URL과 Controller를 관리하고 있으며, <br>
요청 URL에 해당하는 Controller를 반환하는 역할을 한다. <br>
<br>
앞으로 새로운 기능이 추가되면 Controller 인터페이스를 구현하는 새로운 클래스를 추가한 후 RequestMapping Map에 요청 URL과 Controller 클래스를 추가하면 모든 작업이 끝난다. <br>
<br>
각 클래스 간에는 어떠한 영향도 미치지 않으면서 새로운 기능을 추가하는 것이 가능하다. <br>
또한 변경 사항이 발생하면 다른 클래스에 영향을 미칮 않으면서 해당 Controller 클래스의 service() 메서드만 수정하면 된다.

## HTTP 메서드에 따라 다른 처리 할 수 있도록 Abstract class 를 추가

AbstractController를 추가하여 각 Controller는 Controller 인터페이스를 직접 구현하는 것이 아닌 AbstractController를 상속해 각 HTTP 메서드에 맞는 메서드를 오버라이드하도록 구현. <br>
<br>
이렇게 할 경우 요청 URL이 같더라도 HTTP 메서드가 다른 경우 새로운 Controller 클래스를 추가하지 않고 Controller 하나로 GET, POST 요청을 모두 지원하는 것이 가능해짐 <br>

