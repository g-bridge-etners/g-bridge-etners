package com.gbridge.etners.ui.example;

public class ExampleActivity {
    /*
    ui 패키지에는 UI와 관련된 것들을 모아둘 생각입니다.
    Activity 클래스들, Fragement 클래스들,
    만약 mvp, mvc 등의 디자인 패턴을 적용한다면 디자인 패턴에 사용되는 view관련 클래스들도 여기에 들어갈 예정입니다.

    예를 들어 MVP패턴을 사용한다면 하나의 뷰에 최소 Activity 클래스, Presenter 클래스, Contract 인터페이스 하나가 필요합니다.
    view의 이름이 example이라고 한다면

    com.gbridge.etners.ui.example
      - ExampleActivity
      - ExamplePresenter
      - ExampleContract

    와 같은 구조로 만들면 됩니다. example2라는 이름의 뷰가 또 필요하다면,
    com.gbridge.etners.ui.example2 처럼 ui패키지 아래에 하위 패키지를 만들면 됩니다.

    만약 뷰 안에서 ListView나 RecyclerView등 어댑터가 필요한 경우에
    Adapter 클래스도 함께 모아둡니다.
     */
}
