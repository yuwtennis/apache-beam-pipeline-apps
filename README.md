# apache-beam-pipeline-apps

* [Directory model](#directory-model)
* [Available apps](#available-apps)
  * [Java](#java)
  * [Python](#python)

## Target audience

People who use apache beam.

## Motivation

I would like to help people with pipeline implementation.

## Directory model
```
.
├── java                                                  # Java code
│   └── pipeline_apps                               # Maven Group Id
│       ├── pom.xml
│       └── src
│           ├── main
│           │   └── java
│           │       └── net
│           │           └── yuwtennis
│           │               └── app
│           │                   └── App.java
│           └── test
│               └── java
│                   └── net
│                       └── yuwtennis
│                           └── app
│                               └── AppTest.java
├── python                                                # Python code
│   ├── __main__.py
│   ├── pipeline_apps                               # Package for python
│   │   └── __init__.py
│   └── requirements.txt
└── README.md
```

## Available apps

### Java
| Application Name | Description | State |
| ---------------- | ----------- | ----- |
| FsToEs           | Send data from local filesystem to Elasticsearch | Doing |

### Python

tbc