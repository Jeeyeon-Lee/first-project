//1 박스 2개
//2 드롭다운 리스트 2개
//3 환율정보 들고 오기
//4 드롭다운에서 선택하면 아이템 바뀜
//5 금액을 입력하면 환전이 된다
//6 드랍다운에서 아이템 선택시 각 나라 기준으로 금액기준 바뀜
//7 유저가 보기 숫자를 한국어로 편하게 읽어주는 서비스
// 밑에서 숫자 입력해도 위에가 적용된다.
let currencyRatio= {
  USD : {
    KRW : 1339.60, 
    USD : 1,
    VND : 24395.00,
    BAHT : 36.15,
    unit : "달러"
  },
  KRW : {
    KRW : 1,
    USD : 0.00075,
    VND : 18.21,
    BAHT : 0.027,
    unit : "원"
  },
  VND : {
    KRW : 0.055,
    USD : 0.000041,
    VND : 1,
    BAHT :0.0015,
    unit : "동"
  },
  BAHT : {
    KRW : 37.08,
    USD : 0.028,
    VND : 674.89,
    BAHT :1,
    unit : "바트"
  }
}
let FromCurrency = 'USD'
let ToCurrency = 'USD'

//1 console.log(currencyRatio.use.unit);
//2 console.log(currencyRatio["VND"]["unit"]);

document.querySelectorAll("#from-list a").forEach((menu) => 
  menu.addEventListener("click",function () {
  //버튼 가져오기 -> 값 바꾸기 -> 변수에 저장하기
  document.getElementById("from-button").textContent = this.textContent;
  FromCurrency = this.textContent;
})
);

//1 console.log(currencyRatio.use.unit);
//2 console.log(currencyRatio["VND"]["unit"]);

document.querySelectorAll("#to-list a").forEach((menu) => 
  menu.addEventListener("click",function () {
  //버튼 가져오기 -> 값 바꾸기 -> 변수에 저장하기
  document.getElementById("to-button").textContent = this.textContent;
  ToCurrency = this.textContent;
})
);