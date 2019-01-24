# -*- coding: utf-8 -*

#提取 ## XX ## 作索引 [XX](#XX), 直接打印出来

import re

chapterName = 'initnclean'

fileName = 'C:\eclipse-workspace\ThinkingInJava\src\main\java\com\lun\\'+ chapterName+'\README.md'

textFile = open(fileName, 'r')

indexRegex = re.compile(r'^## (.*) ##$')

for line in textFile.readlines():
    if indexRegex.match(line):
        
        mo = indexRegex.search(line)

        # line
        indexName = mo.group(1).decode('utf-8')#decode解码，encode编码
        print '[%s](#%s)\n'%(indexName, indexName.lower())
