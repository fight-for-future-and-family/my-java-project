package com.hoolai.panel.web.processor;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.google.common.io.Files;

public class SQLScript {
    
    private static String tokenDels = ";\"'\\-/*\r\n\f";
    private static String delimiter = ";";
    
    public static List<String> parse(String text) {
        List<String> ret = new ArrayList<String>();
        
        StringTokenizer tokenizer = new StringTokenizer(text, tokenDels, true);
        String preToken = null;
        String token = null;
        
        boolean escape = false;
        boolean lineComments = false;
        boolean blockComments = false;
        LinkedList<Character> quotation = new LinkedList<Character>();
        
        StringBuilder tmp = new StringBuilder();
        while(tokenizer.hasMoreTokens()) {
            preToken = token;
            token = tokenizer.nextToken();
            
            if(blockComments) {
                if("*".equals(preToken) && "/".equals(token)) blockComments = false;
                continue;
            } else if(lineComments) {
                if("\n".equals(token) || "\r".equals(token) || "\f".equals(token)) lineComments = false;
                continue;
            } else if(quotation.isEmpty()) {
                if("/".equals(preToken) && "*".equals(token)){
                    tmp.deleteCharAt(tmp.length() - 1);
                    blockComments = true;//
                    continue;
                } else if("-".equals(preToken) && "-".equals(token)) {
                    lineComments = true;
                    tmp.deleteCharAt(tmp.length() - 1);
                    continue;
                }
            }
            
            if(lineComments || blockComments) continue;
            
            tmp.append(token);
            
            if(delimiter.equals(token) && quotation.isEmpty()) {
                addSql(ret, tmp.toString());
                tmp = new StringBuilder();
            }
            
            if(token.equals("\"") && !escape) {
                if(quotation.size() > 0 && quotation.getLast() == '"') quotation.removeLast(); else quotation.offer('"');
            }
            if(token.equals("'") && !escape) {
                if(quotation.size() > 0 && quotation.getLast() == '\'') quotation.removeLast(); else quotation.offer('\'');
            }
            
            escape = token.equals("\\");
        }
        
        addSql(ret, tmp.toString());
        return ret;
    }

    private static void addSql(List<String> ret, String sql) {
        sql = sql.trim();
        if(!sql.isEmpty() && !delimiter.equals(sql)) ret.add(sql);
    }
    
    public static void main(String[] args) throws IOException {
		List<String> lines = Files.readLines(new File(SQLScript.class.getClassLoader().getResource("a.sql").getFile()), Charset.forName("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (String string : lines) {
			sb.append(string).append("\n");
		}
		List<String> parse = parse(sb.toString());
		for (String string : parse) {
			System.out.println(string + "\n");
		}
	}

}