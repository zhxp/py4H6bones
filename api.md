# API交互说明
## 病人注册
### `api/register`
post json

    {
        "pid":"p1", //病人标识
        "name":"姓名", //病人姓名
        "password":"123",
        "height":175, //身高，厘米，integer
        "weight":120, //体重，斤，integer
        "sex":1, //性别，女：0，男：1，integer
        "age":50, //年龄，integer
        "phone":"",
        "email":"",
        "address":""
    }

#### Result
TBD

## 获取当天训练计划
### `api/schedule/{pid}`
#### Result
    {
        "plan": {
                    "stage":1, //当前训练阶段
                    "days":7, //当前阶段持续天数
                    "times":3, //当天所需训练次数
                    "steps":20, //目标步数
                    "pressure":35, //目标压力
                }
    }

