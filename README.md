# 멋쟁이사자처럼 12기 2팀 백엔드 

### 1. 커밋 유형 지정
| 이모티콘 | 문자 | 커밋 유형 | 의미 |
| --- | --- | --- | --- |
|  ✨ | : sparkles : | feat | 새로운 기능 추가 |
| 🐛 | : bug : | fix | 버그 수정 |
| 📝 | : memo : | docs | 문서 수정 (md 파일) |
| ♻️ | : recycle : | refactor | 코드 리팩토링 |
| 💄 | : lipstick : | style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| ✅ | : white_check_mark : | test | 테스트 코드, 리팩토링 테스트 코드 추가 |
| 🚀 | : rocket : | chore | 패키지 매니저 수정 (Dockerfile, gradle, sh, yml) |
| 🚑 | : ambulance : | !hotfix | 급하게 치명적인 버그를 고쳐야 하는 경우 |

### 2. 커밋 메시지
```
✨ feat: 로그인 개발 (#issue_number)
```

### 3. 브랜치 전략
```
|-- main
    |-- develop ✅ gitaction target
        |-- feature/<# issue_number>
```
