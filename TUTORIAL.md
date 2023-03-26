Tips for running app.

* Creating self-signed certificate for elasticsearch

## Creating self-signed certificate for elasticsearch

1. Change directory to ES_HOME

```shell
export ES_HOME=PATH_TO_ELASTICSEARCH_DIRECTORY
cd $ES_HOME
mkdir config/certs/
```

3. Create PKCS#12 keystore for storing CA key and cert. Follow online [doc](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-basic-setup.html#generate-certificates).  
This file will be used as keystore for _ElasticsearchIO_ in apache beam .

```shell
$ ./bin/elasticsearch-certutil ca
$ cp elastic-stack-ca.p12 config/certs/
```

You will have something like below.

```
$ unzip -l elastic-stack-ca.zip 
Archive:  elastic-stack-ca.zip
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  2023-03-26 13:00   ca/
     1200  2023-03-26 13:00   ca/ca.crt
     1679  2023-03-26 13:00   ca/ca.key
---------                     -------
     2879                     3 files
```

4. Create self-signed certificate for http endpoint. Follow online [doc](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/security-basic-setup-https.html#encrypt-http-communication).

a. Follow guidance and use CA cert and key created in previous step. Password protect PKCS#12 keystore is strongly recommended.

```shell
./bin/elasticsearch-certutil http
```

You will have something like below.

```shell
$ unzip -l elasticsearch-ssl-http.zip 
Archive:  elasticsearch-ssl-http.zip
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  2023-03-26 13:43   elasticsearch/
     1407  2023-03-26 13:43   elasticsearch/README.txt
     3604  2023-03-26 13:43   elasticsearch/http.p12
      892  2023-03-26 13:43   elasticsearch/sample-elasticsearch.yml
        0  2023-03-26 13:43   kibana/
     1306  2023-03-26 13:43   kibana/README.txt
     1200  2023-03-26 13:43   kibana/elasticsearch-ca.pem
     1057  2023-03-26 13:43   kibana/sample-kibana.yml
---------                     -------
     9466                     8 files
```

b. Extract and copy PEM file to application context.

```shell
unzip elasticsearch-ssl-http.zip
cp elasticsearch/http.p12 config/certs/
```

c.Configure _elasticsearch.yml_ and add password to elasticsearch keystore accordingly.
