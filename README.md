# 2022 오픈소스 SW 수업자료

## 파일 구조

```bash
├── README.md
├── bin
│   └── scripts
├── jars
└── src
    └── scripts
        ├── kuir.java
        ├── makeCollection.java
        └── makeKeyword.java
``` 

## 디렉토리 설명

**src/scrips** : .java 소스 파일이 저장되는 디렉토리
**bin/** : 컴파일된 .class 바이너리 파일이 저장되는 디렉토리
**jars/** : 외부 jar 파일이 저장되는 디렉토리

## 컴파일 명령어

javac -cp jars/(외부 jar 파일 이름 1):(외부 jar 파일 이름 2):,,,, src/scripts/*.java (args 1) (args 2) ,,, (args n)

## 실행 명령어

java -cp ./jars/j(외부 jar 파일 이름 1):(외부 jar 파일 이름 2):,,,,:bin scripts.kuir (args 1) (args 2) ,,, (args n)
