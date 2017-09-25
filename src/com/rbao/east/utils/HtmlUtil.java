package com.rbao.east.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @version 创建时间：2015-9-9 下午1:26:50
 * 
 */
public class HtmlUtil {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
       
    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签
   
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签
   
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
   
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
       
    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
    //    htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
        return htmlStr;
    }
       
    public static void main(String[] args) {
        String str = "<span style=\"color:#5d666d;\"><strong>项目背景：</strong></span><div style=\"margin-top:30px;\"><img src=\"http://localhost:8080/itoudian/common/front/images/ny/xq_03.jpg\" height=\"383\" width=\"576\" alt=\"\" /></div>    <div>一.借款人信息：<br /><br />借款人为某知名建材公司高管，工作稳定，月入壹万贰千元以上，具有还款能力。<br/><br/>二.资金用途：<br /><br />家庭房屋装修<br /><br />三.提供资料：<br /></div><div>1.身份证 2.户口本 3.银行流水清单 4.公司收入证明 5.房产证 6.结婚证 7.配偶财产<br />8.第三方公司出具的担保函<br /><br />四.还款来源：<br /><br />家庭固定收入还款<br /><br /><br />五.保证方式：<br /> <br />第三方公司担保 <br /><br />客户资料完整，公司经营状况良好。<br /><br />平台已对借款人进行实地考察，通过专业尽调认为可保证本息按时还款。</div>";
        System.out.println(getTextFromHtml(str));
    }
}
