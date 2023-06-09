/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云Mongo
 Source Server Type    : MongoDB
 Source Server Version : 40421 (4.4.21)
 Source Host           : 101.42.35.130:27017
 Source Schema         : yun

 Target Server Type    : MongoDB
 Target Server Version : 40421 (4.4.21)
 File Encoding         : 65001

 Date: 09/06/2023 13:15:52
*/


// ----------------------------
// Collection structure for courierSub
// ----------------------------
db.getCollection("courierSub").drop();
db.createCollection("courierSub",{
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: [
                "cpCode",
                "logisticsFullTraceList",
                "logisticsStatus",
                "logisticsStatusDesc",
                "mailNo"
            ],
            properties: {
                cpCode: {
                    bsonType: "string",
                    description: "快递公司编码"
                },
                logisticsFullTraceList: {
                    bsonType: "array",
                    description: "物流轨迹列表",
                    items: {
                        bsonType: "object",
                        required: [
                            "logisticsStatus",
                            "time"
                        ],
                        properties: {
                            areaCode: {
                                bsonType: "string",
                                description: "路由节点所在地区行政编码"
                            },
                            areaName: {
                                bsonType: "string",
                                description: "路由节点所在地区"
                            },
                            subLogisticsStatus: {
                                bsonType: "string",
                                description: "物流子状态"
                            },
                            logisticsStatus: {
                                bsonType: "string",
                                description: "物流状态"
                            },
                            time: {
                                bsonType: "string",
                                description: "物流变更时间"
                            },
                            desc: {
                                bsonType: "string",
                                description: "物流信息描述内容"
                            }
                        }
                    }
                },
                logisticsStatus: {
                    bsonType: "string",
                    description: "物流状态"
                },
                logisticsStatusDesc: {
                    bsonType: "string",
                    description: "物流状态描述"
                },
                mailNo: {
                    bsonType: "string",
                    description: "快递单号"
                }
            }
        }
    }
});
db.getCollection("courierSub").createIndex({
    mailNo: NumberInt("1")
}, {
    name: "mailNo_1",
    unique: true
});

// ----------------------------
// Documents of courierSub
// ----------------------------
db.getCollection("courierSub").insert([ {
    _id: ObjectId("646190e58698012ea7a288c8"),
    cpCode: "SF",
    mailNo: "SF1337445138878",
    logisticsFullTraceList: [
        {
            subLogisticsStatus: "WAIT_ACCEPT",
            logisticsStatus: "WAIT_ACCEPT",
            time: "1623254842000",
            desc: "商品已经下单"
        },
        {
            subLogisticsStatus: "WAIT_ACCEPT",
            logisticsStatus: "WAIT_ACCEPT",
            time: "1623309645000",
            desc: "包裹正在等待揽收"
        },
        {
            subLogisticsStatus: "ON_THE_WAY",
            logisticsStatus: "TRANSPORT",
            time: "1623318480000",
            desc: "顺丰速运 已收取快件"
        },
        {
            subLogisticsStatus: "ON_THE_WAY",
            logisticsStatus: "TRANSPORT",
            time: "1623354360000",
            desc: "快件在【漯河东城中转场】完成分拣,准备发往 【衡阳雁峰中转场】"
        },
        {
            subLogisticsStatus: "ON_THE_WAY",
            logisticsStatus: "TRANSPORT",
            time: "1623444120000",
            desc: "快件在【衡阳雁峰中转场】完成分拣,准备发往 【衡阳市雁峰区】"
        },
        {
            subLogisticsStatus: "ON_THE_WAY",
            logisticsStatus: "TRANSPORT",
            time: "1623454320000",
            desc: "快件到达 【衡阳市雁峰区】"
        }
    ],
    logisticsStatusDesc: "快件到达 【衡阳市雁峰区】",
    logisticsStatus: "TRANSPORT",
    _class: "net.totime.mail.dto.CourierCallBackDTO"
} ]);

// ----------------------------
// Collection structure for logistics
// ----------------------------
db.getCollection("logistics").drop();
db.createCollection("logistics",{
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: [
                "mailNo",
                "cpCode"
            ],
            properties: {
                mailNo: {
                    bsonType: "string",
                    description: "必须是字符串并且是必须的"
                },
                cpCode: {
                    bsonType: "string",
                    description: "必须是字符串并且是必须的"
                },
                logisticsStatus: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                logisticsStatusDesc: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                logisticsCompanyName: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                theLastMessage: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                theLastTime: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                courier: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                courierPhone: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                takeTime: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                cpMobile: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                cpUrl: {
                    bsonType: "string",
                    description: "必须是字符串"
                },
                logisticsTraceDetailList: {
                    bsonType: "array",
                    description: "必须是数组"
                }
            }
        }
    }
});
db.getCollection("logistics").createIndex({
    mailNo: NumberInt("1")
}, {
    name: "mailNo_1",
    unique: true
});

// ----------------------------
// Documents of logistics
// ----------------------------
db.getCollection("logistics").insert([ {
    _id: ObjectId("645dddd7114a60245bbf8373"),
    mailNo: "JT3031691704847",
    cpCode: "JT",
    logisticsStatus: "DELIVERING",
    logisticsStatusDesc: "派件中",
    logisticsCompanyName: "极兔速递",
    theLastMessage: "您的包裹已存放至【兔喜生活+】，记得早点来【下邓路201号赣江城二期南昌南昌县赣江城店】取它回家！如有问题请联系：兔喜生活+ 15170032602/邓紫雯 15170032602，投诉电话：15879336845",
    theLastTime: "2023-05-09 16:37:26",
    takeTime: "1天5小时27分",
    cpMobile: "956025",
    cpUrl: "http://www.jtexpress.com.cn/index.html",
    logisticsTraceDetailList: [
        {
            time: NumberLong("1683515420000"),
            logisticsStatus: "ACCEPT",
            subLogisticsStatus: "ACCEPT",
            desc: "【泉州清濛网点】的吴建福(18150553613)已取件。投诉电话：0595-23141723",
            areaCode: "CN350502000000",
            areaName: "福建省,泉州市,鲤城区"
        },
        {
            time: NumberLong("1683541536000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "快件离开【泉州清濛网点】已发往【泉州清濛集散点】",
            areaCode: "CN350502000000",
            areaName: "福建省,泉州市,鲤城区"
        },
        {
            time: NumberLong("1683542192000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "快件离开【泉州清濛集散点】已发往【泉州转运中心】",
            areaCode: "CN350502000000",
            areaName: "福建省,泉州市,鲤城区"
        },
        {
            time: NumberLong("1683550575000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: " 快件到达【泉州转运中心】",
            areaCode: "CN350582000000",
            areaName: "福建省,泉州市,晋江市"
        },
        {
            time: NumberLong("1683550766000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "快件离开【泉州转运中心】已发往【南昌转运中心】",
            areaCode: "CN350582000000",
            areaName: "福建省,泉州市,晋江市"
        },
        {
            time: NumberLong("1683596425000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: " 快件到达【南昌转运中心】",
            areaCode: "CN360111000000",
            areaName: "江西省,南昌市,青山湖区"
        },
        {
            time: NumberLong("1683597886000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "快件离开【南昌转运中心】已发往【南昌象湖新城网点】",
            areaCode: "CN360111000000",
            areaName: "江西省,南昌市,青山湖区"
        },
        {
            time: NumberLong("1683618174000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: " 快件到达【南昌象湖新城网点】",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683618295000"),
            logisticsStatus: "DELIVERING",
            subLogisticsStatus: "DELIVERING",
            desc: "【南昌象湖新城网点】的兔兔快递员邓紫雯(15170032602)正在派件（可放心接听952300专属热线），投诉电话:0791-82068784。今天的兔兔，怀揣包裹，卯足干劲，为您“加吉”派送中",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683618295000"),
            logisticsStatus: "DELIVERING",
            subLogisticsStatus: "DELIVERING",
            desc: "【南昌象湖新城网点】的兔兔快递员邓紫雯(15170032602)正在派件（可放心接听952300专属热线），投诉电话:15879336845。今天的兔兔，怀揣包裹，卯足干劲，为您“加吉”派送中",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683621446000"),
            logisticsStatus: "DELIVERING",
            subLogisticsStatus: "STA_INBOUND",
            desc: "您的包裹已存放至【兔喜生活+】，记得早点来【下邓路201号赣江城二期南昌南昌县赣江城店】取它回家！如有问题请联系：兔喜生活+ 15170032602/邓紫雯 15170032602，投诉电话：0791-82068784",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683621446000"),
            logisticsStatus: "DELIVERING",
            subLogisticsStatus: "STA_INBOUND",
            desc: "您的包裹已存放至【兔喜生活+】，记得早点来【下邓路201号赣江城二期南昌南昌县赣江城店】取它回家！如有问题请联系：兔喜生活+ 15170032602/邓紫雯 15170032602，投诉电话：15879336845",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        }
    ],
    _class: "com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse$LogisticsTraceBean"
} ]);
db.getCollection("logistics").insert([ {
    _id: ObjectId("645e018a90225668a0978cb0"),
    mailNo: "773219955004555",
    cpCode: "STO",
    logisticsStatus: "SIGN",
    logisticsStatusDesc: "已签收",
    logisticsCompanyName: "申通快递",
    theLastMessage: "已签收，签收人凭取货码签收。",
    theLastTime: "2023-05-06 18:14:42",
    courier: "南昌洪大赣江城1栋2单元3号店",
    courierPhone: "13229662988",
    takeTime: "22小时37分",
    cpMobile: "95543",
    cpUrl: "http://www.sto.cn",
    logisticsTraceDetailList: [
        {
            time: NumberLong("1683105364000"),
            logisticsStatus: "ACCEPT",
            subLogisticsStatus: "ACCEPT",
            desc: "[孝感市]【湖北汉川马口公司】(17702761661)的蜗牛001(17702765728)已揽收",
            areaCode: "CN420984000000",
            areaName: "湖北省,孝感市,汉川市"
        },
        {
            time: NumberLong("1683106372000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[孝感市]快件离开【湖北汉川马口公司】已发往【湖北武汉转运中心】",
            areaCode: "CN420984000000",
            areaName: "湖北省,孝感市,汉川市"
        },
        {
            time: NumberLong("1683132089000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[武汉市]快件已到达【湖北武汉转运中心】",
            areaCode: "CN420100000000",
            areaName: "湖北省,武汉市"
        },
        {
            time: NumberLong("1683132980000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[武汉市]快件离开【湖北武汉转运中心】已发往【江西南昌转运中心】",
            areaCode: "CN420100000000",
            areaName: "湖北省,武汉市"
        },
        {
            time: NumberLong("1683158844000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[南昌市]快件已到达【江西南昌转运中心】",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683159671000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[南昌市]快件已到达【江西南昌转运中心】",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683159725000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[南昌市]快件离开【江西南昌转运中心】已发往【江西南昌象湖公司】",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683181744000"),
            logisticsStatus: "TRANSPORT",
            subLogisticsStatus: "TRANSPORT",
            desc: "[南昌市]快件已到达【江西南昌象湖公司】咨询电话：0791-82326161",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683182332000"),
            logisticsStatus: "DELIVERING",
            subLogisticsStatus: "DELIVERING",
            desc: "[南昌市]【江西南昌象湖公司】0791-82326161的申通小哥（超区洪大赣江城/18779132418）正在为您派送（可放心接听95089申通专属派送号码）,投诉电话：079182326161",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683186828000"),
            logisticsStatus: "DELIVERING",
            subLogisticsStatus: "STA_INBOUND",
            desc: "快件已暂存至南昌洪大赣江城1栋2单元3号店菜鸟驿站，如有取件码问题或找不到包裹等问题，请联系13229662988",
            areaCode: "CN360121000000",
            areaName: "江西省,南昌市,南昌县"
        },
        {
            time: NumberLong("1683368082000"),
            logisticsStatus: "SIGN",
            subLogisticsStatus: "SIGN",
            desc: "已签收，签收人凭取货码签收。",
            areaCode: "CN360100000000",
            areaName: "江西省,南昌市"
        }
    ],
    _class: "com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse$LogisticsTraceBean"
} ]);
