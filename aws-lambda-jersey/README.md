created with [`aws-serverless-java-container`](https://github.com/awslabs/aws-serverless-java-container).

详情可参见 [在 aws lambda 中应用 jersey](https://hfcherish.github.io/2018/06/27/aws-lambda-jersey/)

本系统保留了 `sam.yaml` 和 `template.yaml`，可以自由选择采用 [`aws-sam-cli`](https://github.com/awslabs/aws-sam-cli) 进行本地调试和部署；或者使用 `

# 测试运行

1. clone 代码到本地
2. 根据 [在 aws lambda 中应用 jersey](https://hfcherish.github.io/2018/06/27/aws-lambda-jersey/) 安装 `aws-cli` 和 `aws-sam-cli`
3. `mvn clean package`
3. `sam local start-api`
4. `curl http://localhost:3000/ping`

能 ping 通，则代码可运行。如果 ping 不通。可能是 docker 的问题，可以重装解决。

# api

包含以下 API：

* `GET` /ping
* `GET` /orders?limit={number}
* `POST` /orders 
    * `user`: string
    * `amount`: number
* `GET` /orders/{oid} 
