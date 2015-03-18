package org.gen.common;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class NumberValidator extends PlainDocument {  
	  
    private static final long serialVersionUID = 1L;  
  
    private int limit;  
  
    public NumberValidator(int limit) {  
        super();  
        this.limit = limit;  
    }  
  
    public void insertString(int offset, String str, AttributeSet attr)  
            throws javax.swing.text.BadLocationException {
        if (str == null) {
            return;
        } else if (getLength() + str.length() <= (getLength() > 0 && getText(0, 1).startsWith("-") ? limit + 1 : limit)) {  
            char[] upper = str.toCharArray();  
            int length = 0;  
            for (int i = 0; i < upper.length; i++) {  
                if (upper[i] >= '0' && upper[i] <= '9' || upper[i] == '-') {  
                    upper[length++] = upper[i];  
                }  
            }  
            // 插入数字  
            super.insertString(offset, new String(upper, 0, length), attr);  
        }  
    }  
  
}