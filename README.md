# nurimplus
하이미디어 아카데미, 최종프로젝트 코드입니다. Spring Boot를 활용하여 "신혼부부관련 서비스제공" 모바일 애플리케이션의 프로토타입을 구현하였습니다.

## 👨‍🏫 프로젝트 소개
"신혼부부를 위한 정부 정책과 놓치면 아까운 지원 정보를 친절히 알려주며, 이러한 혜택들에 대한 정보를 문의하고 공유할 수 있는 커뮤니티 제공"

## ⏲️ 개발 기간 
- 2024.04.03(목) ~ 2023.05.01(수)
- 아이디어의 이해, DevOps 특강
- 아이디어 노트 작성
- 코딩
- 아이디어 발표
- 
- 발표평가
  
## 🧑‍🤝‍🧑 개발자 소개 
- **박채연** : 팀장, Server 개발자
- **백승용** : 데이터 분석
- **유윤경** : 데이터 분석
- **김성종** : Android 개발자
- **송가람** : UX UI Designer
- **이한조** : Android 개발자
- **이재형** : UX UI Designer
  
![개발자 소개]

## 💻 개발환경
- **Version** : Java 17
- **IDE** : IntelliJ, vue.js
- **Framework** : SpringBoot3
- **ORM** : JPA

## ⚙️ 기술 스택
- **Server** : AWS EC3
- **DataBase** : AWS RDS, Datagrip, JPQL, ERD AqueryTool
- **WS/WAS** : Nginx, Tomcat
- **OCR** : AWS Textract, AWS S3
- **아이디어 회의** : Slack, Zoom, Notion

## 📝 프로젝트 아키텍쳐
![프로젝트 아키텍쳐](d)

## 📌 주요 기능
-흩어진 정보를 한 곳에서 제공
  - 공공데이터포털에서 제공하는 가전기기의 업체명, 모델명, 소비전력량, 에너지 효율등급, 시간당 이산화탄소 배출량를 csv 파일로 가져온다.
  - 가격필드를 추가한 후 데이터 크롤링을 이용해 가격 정보를 csv파일에 입력한다.
  - csv파일을 Json으로 변환하여 DB에 입력한다.
  - 전기냉장고, 전기냉방기, 김치냉장고, 전기세탁기 품목에 한정하여 진행하였다.
- 지역/키워드별 맞춤 정보 제공
   - 텍스트로 모델명을 검색하면 텍스트와 일치하는 모델에 대한 정보가 사용자에게 반환된다. Like 연산자를 활용하여 모델명이 부분일치하는 경우도 반환할 수 있게 하였다.
   - 에너지소비효율 등급을 사진으로 찍으면 좌표값을 이용하여 모델명을 추출하여 해당 모델에 대한 정보를 사용자에게 반환한다.
- 언제든지 문의하는 커뮤니티 제공
    - 기존의 에너지효율 등급과 탄소배출량 등을 고려하여 매긴 자체점수를 이용해 가전제품의 랭킹을 사용자에게 보여준다.
    - 기존의 에너지효율 등급만으로는 동일한 등급 간의 비교가 어려웠고, 얼마만큼의 이득이 생기는지가 수치적으로 드러나지 않는다는 단점이 존재했다. 이러한 점을 개선하기 위해 자체 점수 제도를 도입하였다.
    - 또한 등급간의 비율도 가전기기의 종류마다 제각각이었기 때문에 인공지능을 활용해 공평하게 등급을 구분하였다.

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
"# nurim-plus-view" 
"# nurim-plus-view" 
