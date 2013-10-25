# API交互说明
## 登录
### GET `/api/login?pid={pid}&password={password}&token={xxxxx}`
例

    http://180.168.35.37:8080/p11y/api/login?pid=20131015023&password=1234&deviceId=asdfasdfasdf

### 返回值
    {
        "success":1, //成功返回1，失败返回0
        "name":"病人姓名",
        "sex":"男",
        "height":175, //身高，厘米
        "weight":120, //体重，斤
        "surgery":"xxx修复术", //手术名称
        "age":40, //
        "phone":"12341234", //
        "surgeryDate":"2013-10-11", //手术日期
        "dischargeDate":"2013-10-11", //出院日期
    }

## 获取计划
### GET `/api/plan/{pid}`
例

    http://180.168.35.37:8080/p11y/api/plan/20131015023

### 返回值

    {
        "stage":1, //训练阶段
        "days":7, //阶段持续天数
        "times":3, //每天所需训练次数
        "steps":20, //目标步数
        "pressure":35, //目标压力
        "startedOn":"2013-10-15", //当前阶段开始日期，为空时返回 ""
    }

当前没有计划时返回 `{}`

## [上传训练结果](id:upload)
### POST JSON `/api/upload/{pid}`
例

    http://180.168.35.37:8080/p11y/api/upload/20131015023

### JSON

    {
        "startedAt":"2013-10-15 08:20:00", //采集开始时间
        "duration":1, //训练持续时间，单位（秒）
        "pressures":[20,21,25,23], //压力值整型数组
        "feeling":0, //总体感觉,1轻松,2适量,3过度
        "reaction":[1,2,3], //不良反应,0无，1肿胀，2酸痛，3弹响，4麻木，5淤青
        "memo":"其他", //其他说明
    }
    
### 返回值

    {
        "errors":[] //成功返回空数组，失败返回失败原因
    }
    
## 医生留言
### GET `/api/messages/{pid}`
例

    http://180.168.35.37:8080/p11y/api/messages/20131015023
    
### 返回值

    [
        {
            id:1, //消息id, Long
            text:"消息内容",
            time:"2013-10-11 08:20" //消息发送时间
        },
        ....
    ]
    
## 训练历史纪录
### GET `/api/histories/{pid}`
例

    http://180.168.35.37:8080/p11y/api/histories/20131015023
    
### 返回值
[上传训练结果](#upload)数组

    [
        {
            "startedAt":"2013-10-15 08:20:00", //采集开始时间
            "duration":1, //训练持续时间，单位（秒）
            "pressures":[20,21,25,23], //压力值整型数组
            "feeling":0, //总体感觉,0舒适,1轻度不适,2中度不适,3重度不适,4紧急情况
            "effect":1, //训练强度，0效果不明显，1轻松，2适量，3可忍受，4过度
            "reaction":0, //不良反应,0无，1肿胀，2酸痛，3弹响，4麻木，5淤青
            "memo":"其他", //其他说明
        },
        ....
    ]
    
