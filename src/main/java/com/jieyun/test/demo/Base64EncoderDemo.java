package com.jieyun.test.demo;

import com.jieyun.test.utils.base64.Base64Utils;

/**
 * @CalssName Base64EncoderDemo
 * @Desc Base64编码解码工具类测试
 * @Author huike <link>754873891@qq.com</link>
 * @Date 2019-12-21 21:56:14
 * @Version 1.0
 **/
public class Base64EncoderDemo {

    public static void main(String[] args) {

        String s = "莲花绽放，濠江情动。12月18日至20日，习近平主席赴澳门出席庆祝澳门回归祖国20周年大会暨澳门特别行政区第五届政府就职典礼并视察澳门。\n" +
                "\n" +
                "12月18日下午，习近平乘专机抵达澳门时，数百名澳门青少年和各界群众在机场迎接。习近平主席朝他们挥手致意，并同站在前排的来自澳门教业中学国际部小学生朱玥、张博琳亲切地握手。\n" +
                "\n" +
                "朱玥说：“我本来只在电视上见过习爷爷，这次见到习爷爷，我很开心。习爷爷很慈祥。”握手的照片迅速在教业中学校友群传播，师生、家长们兴奋不已。\n" +
                "\n" +
                "\n" +
                "\n" +
                "△12月18日，澳门中小学师生在机场欢迎习近平主席的到来，这是教业中学国际部P5C班学生朱玥（左）、P4A班学生张博琳（右）在欢迎的人群中。\n" +
                "\n" +
                "12月19日上午，习近平来到濠江中学附属英才学校。学校礼堂里，濠江中学师生代表，澳门非高等教育学校校长、历史科研组长、德育主任正在观摩以“‘一国两制’与澳门”为题的中国历史公开课。习近平主席走进礼堂，同大家一起就座观摩。公开课结束后，在师生们热烈掌声中，习近平走到学生中间即席发表重要讲话。\n" +
                "\n" +
                "在现场聆听了习近平主席讲话的教业中学校长贺诚说：“习主席的演讲为澳门的教育指明了一个新的方向。他指出，要打牢爱国主义的基础，就要了解历史，一定要重视中国历史的教育。他说，对我们5000年历史的认识，足以影响孩子们的人生观、价值观。”\n" +
                "\n" +
                "“习主席说，你要知道从哪里来，现在在哪里，然后走什么样的路，去到什么样的目标，都应该贯彻落实体现在我们教育的全过程当中。他就是要告诉澳门的教育工作者和青少年，你们要忠于国家，担当起民族复兴的责任。”听完习近平的讲话，贺诚深有感触。\n" +
                "\n" +
                "\n" +
                "\n" +
                "△12月16日，濠江中学附属英才学校举行升旗仪式。现在，澳门全部大中小学都已经实现了升挂国旗，并且每周都会举行升国旗仪式。（摄影：央视新闻记者 吴璇）\n" +
                "\n" +
                "同样是在19日上午，习近平主席还视察了澳门黑沙环政府综合服务中心。黑沙环政府综合服务中心于2009年正式投入使用，目前可提供26个政府部门共335项服务。\n" +
                "\n" +
                "当时正在现场的澳门特区政府社会保障基金技术辅导员陈殷桁说，看见习近平主席来了，在场的人们都很开心地鼓掌。\n" +
                "\n" +
                "在24小时身份证明自助服务区，习近平观看护照业务等办理演示，对通过信息科技手段实现社会治理精细化和公共服务高效化的做法表示肯定。现场跟习近平主席交谈的澳门居民叶翠萍在接受央视新闻采访时说：“习近平主席很关心澳门的情况。特区护照能去140多个国家免签证，证明澳门在国际上的声誉越来越好。”澳门居民郑伟宏说：“澳门作为祖国的一个地区，习近平主席这么关注我们，我们觉得非常高兴。祖国越来越强大，澳门的明天一定会更好。”\n" +

                "习近平在庆祝澳门回归祖国20周年大会暨澳门特别行政区第五届政府就职典礼上的讲话中说，澳门回归祖国20年来取得的成就举世瞩目。澳门地方虽小，但在“一国两制”实践中作用独特。我们坚信，包括港澳同胞在内的中国人民完全有智慧、有能力把“一国两制”实践发展得更好，把“一国两制”制度体系完善得更好，把特别行政区治理得更好。中华民族伟大复兴的前进步伐势不可挡，香港、澳门与祖国内地同发展、共繁荣的道路必将越走越宽广！\n" +
                "\n" +
                "深情关怀，情暖濠江。澳门同胞将牢记习近平主席的谆谆话语，在“爱国爱澳”“一国两制”的道路上笃定前行。";
        String planText = s.replaceAll("\n", "");
        String encoderBase = Base64Utils.getEncoder(planText);
        String decoderBase = Base64Utils.getDecoder(encoderBase);

        String encoderUrlBase = Base64Utils.getUrlEncoder(planText);
        String decoderUrl = Base64Utils.getDecoderUrl(encoderUrlBase);

        String encoderMime = Base64Utils.getMimeEncoder(planText);
        String decoderMime = Base64Utils.getDecoderMime(encoderMime);


        System.out.println("=====base========");
        System.out.println(encoderBase);
        System.out.println("---------------");
        System.out.println(decoderBase);

        System.out.println("=====url========");
        System.out.println(encoderUrlBase);
        System.out.println("---------------");
        System.out.println(decoderUrl);
        System.out.println("======mime=======");
        System.out.println(encoderMime);
        System.out.println("---------------");
        System.out.println(decoderMime);


    }
}
