## Servlet의 Load on startup

1. `load-on-startup` 은 정수 값을 지정할 수 있다. 값이 0 이상이면 서버가 시작될 때 서블릿이 로드된다. 0보다 큰 정수 값은 서블릿이 로드되는 순서를 나타내며, 번호가 낮은 서블릿부터 먼저 로드된다.
2. `load-on-startup` 의 값이 없거나 음의 정수이면 서블릿 컨테이너는 해당 서블릿을 최초로 요청 받았을 때 초기화하게 된다.

<br>
2번 방식으로 서블릿을 초기화하게 되면 해당 서블릿의 최초 요청자는 서블릿 생성 시간으로 인해 요청이 지연된다. 이러한 지연되는 시간을 방지하고자 만든 옵션으로 보여진다.

## Servlet Filter

**필터**는 클라이언트로부터 오는 요청과 최종 자원 (JPS, 서블릿, 기타 자원) 사이에 위치하며 클라이언트의 요청 정보를 변경할 수 있다. <br>
<br>
필터로 구현하면 좋은 기능들
1. 인증(사용자 인증) 필터
2. 로깅 및 감시 필터
3. 이미지 변환 및 데이터 압축 필터
4. 암호화 필터
5. XML 컨텐츠를 변환하는 XSLT 필터
6. URL 및 기타 정보들을 캐싱하는 필터

### Filter interface

필터는 필터를 설정하는 FilterConfig 객체, FilterChain 객체, Filter 객체가 필요하다. <br>
이 중 FilterConfig 인터페이스와 FilterChain 인터페이스를 상속받은 클래스는 웹 컨테이너가 구현해준다. Filter를 구현하는데 필요한 건 사용자 정의 필터 클래스이다. 사용자 정의 필터 클래스는 javax.servlet.Filter 인터페이스를 구현한다. <br>
<br>
Filter 인터페이스는 init(), doFilter(), destroy() 메서드가 있다. <br>

> init(FilterConfig config)
>> 서블릿 컨테이너가 필터 인스턴스를 초기화 하기 위해 호출하는 메서드

> doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
>> 필터에서 구현해야 하는 로직을 작성하는 메서드

> destroy(): void
>> 필터 인스턴스를 종료시키기 전에 호출하는 메서드

### Filter의 라이프 사이클

필터는 서블릿과 유사한 라이프사이클을 가진다. 생성, 초기화, 필터, 종료의 4단계로 이루어지며 **서블릿 컨테이너는 필터 객체가 초기화 파라미터에 접근하는데 사용하는 환경설정 객체(FilterConfig)의 레퍼런스를 제공한다.** 서블릿 컨테이너가 필터의 init() 메서드를 호출하면 필터 인터페이스는 요청을 처리할 수 있는 상태가 된다.

### FilterChain

필터는 연속적인 수행이 가능하다. 필터 객체가 수행해야할 부분인 doFilter() 메서드로 인자가 전달되는 것이 FilterChain 객체이다. FilterChain 객체는 필터의 연속적인 수행이 가능하게 한다. 웹 컨테이너가 FilterConfig 객체와 함께 FilterChain 인터페이스를 구현한 객체를 생성한다.

### Filter 흐름

> HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러

필터를 적용하면 필터가 호출된 후 서블릿이 호출된다

## Dispatcher Servlet

모든 클라이언트 요청은 DispatcherServlet이 받은 후 요청 URL에 따라 해당 컨트롤러에 작업을 위임한다. @WebServlet으로 URL을 매핑할 때 urlPatterns="/" 와 같이 설정하면 모든 요청 URL이 DispatcherServlet으로 연결된다. <br>
<br>
단, CSS, js, image와 같은 정적 자원은 컨트롤러가 필요하지 않다. 하지만 urlPatterns="/" 와 같이 매핑할 경우 컨트롤러가 필요없는 CSS, js, image에 대한 요청까지 DispatcherServlet으로 매핑이 되는 상황이 발생한다. <br>
<br>
이와 같은 문제점을 해결하기 위해 **CSS, js, image를 처리하는 서블릿 필터를 추가해 해결할 수 있다.**
