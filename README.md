---
title: java批处理之微博图床链接加标签
date: 2020-07-25 14:34:37
tags:
categories: Java

---

微博图床加了防盗链，有一个缓兵之计是给所有`<img>`标签加上一个属性：`referrerpolicy="no-referrer"`。

博客里插入了非常多图片，手动添加是不可能的，我们来用java批处理：

```java
package sample;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkDownModify {
    private final static Pattern PATTERN = Pattern.compile("(.*?)(<img.+>)(.*?)");

    public static void addReferrerPolicy(File directory) throws IOException {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            modify(file);
        }
    }

    private static void modify(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            line = regex(line);
            content.append(line).append("\n");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content.toString());
        writer.flush();
    }

    private static String regex(String line) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.matches()) {
            String s1 = matcher.group(1);
            String s2 = matcher.group(2);
            String s3 = matcher.group(3);
            s2 = "<img " + "referrerpolicy=\"no-referrer\" " + s2.substring(5);
            return s1 + s2 + s3;
        } else {
            return line;
        }
    }
}

```

我用javaFX给这段批处理程序写了一个图形界面：

<img src="http://ww1.sinaimg.cn/large/007FpgxTly1ghdlyoyzkwj30dy06bweg.jpg"/>

<img src="http://ww1.sinaimg.cn/large/007FpgxTly1ghdlzcsac3j30en09w0t2.jpg"/>

先选择md文件所在的目录，之后点击Execute按钮执行批处理，点击help查看帮助。
